<?php

use App\Http\Controllers\BalanceController;
use App\Http\Controllers\ExpenseController;
use App\Http\Controllers\GroupController;
use App\Http\Controllers\UserController;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider and all of them will
| be assigned to the "web" middleware group. Make something great!
|
*/

Route::get('/', function () {
    if (Auth::check()) {
        return view('/home');
    } else {
        return view('auth/login');
    }
});

Auth::routes();

Route::get('/home', [App\Http\Controllers\HomeController::class, 'index'])->name('home');
Route::get('logout', '\App\Http\Controllers\Auth\LoginController@logout');
Route::get('/admin/assignRole', [UserController::class, 'showUserForm'])->name('showUsers')->middleware('auth', 'role:admin');
Route::post('/admin/assignRole', [UserController::class, 'updateUserRole'])->name('admin_change_role')->middleware('auth', 'role:admin');;
Route::delete('/admin/deleteRole',[UserController::class,'deleteUser'])->name('admin_delete_user')->middleware('auth', 'role:admin');;

Route::post('/group/create', [GroupController::class, 'createGroup'])->name('createGroup')->middleware('auth', 'role:user');;
Route::get('/group/create', [GroupController::class, 'showCreateGroupForm'])->name('showCreateGroupForm')->middleware('auth', 'role:user');;
Route::get('/groups', [GroupController::class, 'showUserGroups'])->name('showUserGroups')->middleware('auth', 'role:user');
Route::post('/group/{group_id}/addMember', [GroupController::class, 'addMember'])->name('addMember')->middleware('auth', 'role:user');
Route::delete('/delete-member/{group_id}/{user_id}', [GroupController::class, 'deleteMember'])->name('deleteMember')->middleware('auth', 'role:user');


Route::post('groups/{group_id}/expenses/create', [ExpenseController::class, 'createExpense'])->name('createExpense');
Route::get('groups/{group_id}/expenses/create', [ExpenseController::class, 'showCreateExpense'])->name('createExpense');
Route::get('groups/{group_id}/decryptedExpenses', [ExpenseController::class, 'showGroupExpenses'])->name('showGroupExpenses');
Route::get('expenses/{expense_id}/edit', [ExpenseController::class, 'showEditExpense'])->name('showEditExpense');
Route::put('expenses/{expense_id}', [ExpenseController::class, 'updateExpense'])->name('updateExpense');
Route::delete('expenses/{expense_id}', [ExpenseController::class, 'deleteExpense'])->name('deleteExpense');

Route::get('/groups/{group_id}/balances', [ExpenseController::class,'showGroupBalances'])->name('showGroupBalances');
