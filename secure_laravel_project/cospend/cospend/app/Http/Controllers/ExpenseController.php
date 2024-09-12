<?php

namespace App\Http\Controllers;

use App\Models\Group;
use Illuminate\Http\Request;
use App\Models\Expense;
use App\Models\User;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Storage;

class ExpenseController extends Controller
{
    public function showCreateExpense($group_id)
    {
        $group = Group::find($group_id);

        if (!$group) {
            return redirect()->route('showUserGroups')->with('error', 'Le groupe n\'existe pas.');
        }

        if (!$group->members->contains(auth()->user())) {
            return redirect()->route('showUserGroups')->with('error',
                'Vous n\'êtes pas autorisé à créer une dépense pour ce groupe.');
        }

        return view('expenses.expense_create', compact('group_id', 'group'));
    }

    public function createExpense(Request $request, $group_id)
    {
        $group = Group::find($group_id);

        $request->validate([
            'title' => 'required|string',
            'date' => 'required|date',
            'amount' => 'required|numeric',
            'description' => 'nullable|string',
            'to' => 'required|array',
        ]);

        // Récupérer la clé de chiffrement du groupe
        $groupKeyPath = 'public/cospend/' . auth()->user()->id . $group->group_id . '.key';
        $encryptedGroupKey = Storage::get($groupKeyPath);

        // Déchiffrer la clé de groupe avec la clé privée de l'utilisateur
        $userPrivateKey = file_get_contents(Auth::user()->private_key);
        openssl_private_decrypt($encryptedGroupKey, $groupKey, $userPrivateKey);

        // Générer un IV aléatoire
        $iv = random_bytes(16);

        // Chiffrement des données
        $encryptedTitle = openssl_encrypt($request->input('title'), 'AES-256-CBC', $groupKey, 0, $iv);
        $encryptedDescription = openssl_encrypt($request->input('description'), 'AES-256-CBC', $groupKey, 0, $iv);
        $encryptedDate = openssl_encrypt($request->input('date'), 'AES-256-CBC', $groupKey, 0, $iv);
        $encryptedAmount = openssl_encrypt($request->input('amount'), 'AES-256-CBC', $groupKey, 0, $iv);

        // Enregistrement des données dans la base de données
        $expense = new Expense([
            'from' => auth()->user()->id,
            'to' => json_encode($request->input('to')),
            'group_id' => $group->group_id,
            'title' => $encryptedTitle,
            'description' => $encryptedDescription,
            'date' => $encryptedDate,
            'amount' => $request->input('amount'),//$encryptedAmount
            'iv' => base64_encode($iv), // Stockage de l'IV
        ]);

        $expense->save();


        return redirect()->route('showGroupExpenses', ['group_id' => $group_id])
            ->with('success', 'Dépense créée avec succès.');
    }

    private function decryptExpenseData($encryptedData, $groupKey, $iv)
    {
        return openssl_decrypt($encryptedData, 'AES-256-CBC', $groupKey, 0, base64_decode($iv));
    }

    public function showGroupExpenses($groupId)
    {
        $group = Group::find($groupId);

        if (!$group) {
            return redirect()->route('showUserGroups')->with('error', 'Le groupe n\'existe pas.');
        }

        $expenses = $group->expenses;

        return view('expenses.expenses', compact('group', 'expenses'));
    }

    public function updateExpense(Request $request, $expense_id)
    {
        $expense = Expense::find($expense_id);

        if (!$expense) {
            return redirect()->back()->with('error', 'La dépense n\'existe pas.');
        }

        if (auth()->user()->id !== $expense->from) {
            return redirect()->back()->with('error', 'Vous n\'êtes pas autorisé à modifier cette dépense.');
        }

        $request->validate([
            'title' => 'required|string',
            'date' => 'required|date',
            'amount' => 'required|numeric',
            'description' => 'nullable|string',
        ]);

        $expense->title = $request->input('title');
        $expense->date = $request->input('date');
        $expense->amount = $request->input('amount');
        $expense->description = $request->input('description');

        $expense->save();

        return redirect()->route('showGroupExpenses', ['group' => $expense->group_id])
            ->with('success', 'Dépense mise à jour avec succès.');
    }

    public function showEditExpense($expense_id)
    {
        $expense = Expense::find($expense_id);

        if (!$expense) {
            return redirect()->back()->with('error', 'La dépense n\'existe pas.');
        }

        if (auth()->user()->id !== $expense->from) {
            return redirect()->back()->with('error', 'Vous n\'êtes pas autorisé à modifier cette dépense.');
        }

        return view('expenses.expense_edit', compact('expense'));
    }

    public function deleteExpense($expense_id)
    {
        $expense = Expense::find($expense_id);

        if (!$expense) {
            return redirect()->back()->with('error', 'La dépense n\'existe pas.');
        }

        if (auth()->user()->id !== $expense->from) {
            return redirect()->back()->with('error', 'Vous n\'êtes pas autorisé à supprimer cette dépense.');
        }

        $expense->delete();

        return redirect()->back()->with('success', 'La dépense a été supprimée avec succès.');
    }


    public function showGroupBalances($group_id)
    {
        $group = Group::find($group_id);

        if (!$group) {
            return redirect()->route('showUserGroups')->with('error', 'Le groupe n\'existe pas.');
        }

        $balances = [];

        foreach ($group->members as $member) {
            $expensesPaid = Expense::where('from', $member->id)->sum('amount');
            $expensesOwed = $this->calculateExpensesOwed($group, $member);
            $balance = $expensesPaid - $expensesOwed;

            // Ensure $balance is treated as a number
            $balances[$member->id] = $balance;
        }

        return view('expenses.balances', compact('group', 'balances'));
    }

    private function calculateExpensesOwed(Group $group, User $user): float
    {
        // Get all expenses for the group where the user is among the recipients
        $expenses = $group->expenses->filter(function ($expense) use ($user) {
            $recipients = json_decode($expense->to);
            return in_array($user->id, $recipients) && $expense->from !== $user->id;
        });

        // Calculate and return the total amount owed by the user
        $totalOwed = 0;

        foreach ($expenses as $expense) {
            $recipients = json_decode($expense->to);
            $amountOwed = $expense->amount / count($recipients);

            if (in_array($user->id, $recipients)) {
                $totalOwed += $amountOwed;
            }
        }

        return $totalOwed;
    }
}
