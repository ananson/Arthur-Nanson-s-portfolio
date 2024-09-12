<?php

namespace Database\Seeders;

use App\Models\User;
use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;

class UsersTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        $user = User::create([
            'name' => 'Admin',
            'email' => 'admin@gmail.com',
            'password' => Hash::make('admin', ['rounds' => 12]),
            'role' => 'admin',
        ]);
        $user->generateAndStoreKeyPair();



        $user1 = User::create([
            'name' => 'Billal',
            'email' => 'billal@gmail.com',
            'password' => Hash::make('billal', ['rounds' => 12]),
            'role' => 'user',
        ]);
        $user1->generateAndStoreKeyPair();

        $user2 = User::create([
            'name' => 'Arthur',
            'email' => 'Arthur@gmail.com',
            'password' => Hash::make('arthur', ['rounds' => 12]),
            'role' => 'user',
        ]);

        $user2->generateAndStoreKeyPair();

    }
}
