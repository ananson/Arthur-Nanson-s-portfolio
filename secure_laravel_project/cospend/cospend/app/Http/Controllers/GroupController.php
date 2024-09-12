<?php

namespace App\Http\Controllers;

use App\Models\GroupUser;
use App\Models\User;
use Couchbase\QueryException;
use Illuminate\Http\Request;
use App\Models\Group;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Storage;
use Illuminate\Support\Str;

class GroupController extends Controller
{
    public function createGroup(Request $request)
    {
        $request->validate(['name' => 'required|string']);

        $userId = auth()->id();
        $user = User::find($userId);

        if (!$user) {
            return redirect()->route('showUserGroups')->with('error', 'User not found.');
        }

        $groupUuid = Str::uuid();
        DB::table('groups')->insert([
            'group_id' => $groupUuid,
            'name' => $request->name,
            'owner_id' => $userId,
            'created_at' => now(),
            'updated_at' => now()
        ]);

        if (!$user->belongsToGroup($groupUuid)) {
            DB::table('group_user')->insert([
                'group_id' => $groupUuid,
                'user_id' => $userId
            ]);

            $encryptionKey = random_bytes(32);
            openssl_public_encrypt($encryptionKey, $encryptedKey, $user->public_key);
            Storage::put('public/cospend/' . $userId . $groupUuid . '.key', $encryptedKey);

            return redirect()->route('showUserGroups')->with('success', 'Group created successfully.');
        } else {
            return redirect()->route('showUserGroups')->with('error', 'User is already a member of the group.');
        }
    }

    public function addMember(Request $request, $group_id)
    {
        try {
            $group = Group::with('members')->findOrFail($group_id);

            if ($group->owner_id !== auth()->id()) {
                return redirect()->back()->with('error', 'Not authorized');
            }

            $userId = $request->user_id;

            DB::table('group_user')->insert([
                'group_id' => $group_id,
                'user_id' => $userId
            ]);

            $member = GroupUser::where('user_id', Auth::user()->id)->first();
            $encryptedKey = Storage::get('public/cospend/' . $userId . '.key');

            $decryptedKey = '';
            $memberPrivateKey = file_get_contents(Auth::user()->private_key);
            openssl_private_decrypt($encryptedKey, $decryptedKey, $memberPrivateKey);
            $newMember = GroupUser::where('user_id', $userId)->first();
            openssl_public_encrypt($decryptedKey, $encryptedKeyGroup, $newMember->user->public_key);
            Storage::put('public/cospend/' . $userId . $group->group_id . '.key', $encryptedKeyGroup);
        } catch (QueryException $e) {
            return redirect()->back()->with('error', 'Error adding the member');
        }

        return redirect()->route('showUserGroups')->with('success', 'Member added successfully!');
    }
    public function deleteMember(Request $request, $group_id)
    {
        $group = Group::findOrFail($group_id);

        if ($group->owner_id !== auth()->id()) {
            return response()->json(['message' => 'Non autorisé'], 403);
        }

        $userId = $request->user_id;

        try {
            DB::table('group_user')
                ->where('group_id', $group_id)
                ->where('user_id', $userId)
                ->delete();
            $members = GroupUser::where('user_id', Auth::user()->id)->get();
            foreach ($members as $member) {
                Storage::delete('public/cospend/' .$userId . $group->group_id  . '.key');
            }

        } catch (QueryException $e) {
            return redirect()->back()->with('error', 'Erreur lors de la suppression du membre');
        }

        return redirect()->route('showUserGroups');
    }

    public function showUserGroups()
    {
        $userId = Auth::id();
        $groups = Group::with('members')->whereHas('members', function ($query) use ($userId) {
            $query->where('user_id', $userId);
        })->get();

        $filteredUsers = [];
        foreach ($groups as $group) {
            $groupMemberIds = $group->members->pluck('id')->toArray();

            $nonMembers = DB::table('users')
                ->whereNotIn('id', $groupMemberIds)
                ->where('id', '!=', $group->owner_id)
                ->get();

            $filteredUsers[$group->id] = $nonMembers;
        }

        return view('groups.groups', compact('groups', 'filteredUsers'));
    }

    public
    function showCreateGroupForm()
    {
        return view('groups.group_create');
    }

}
