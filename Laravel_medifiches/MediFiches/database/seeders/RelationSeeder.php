<?php

namespace Database\Seeders;

use Illuminate\Database\Console\Seeds\WithoutModelEvents;
use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;

class RelationSeeder extends Seeder
{
    /**
     * Run the database seeds.
     */
    public function run(): void
    {
        DB::table('relation')->insert([
            'name' => 'Mere'
        ]);
        DB::table('relation')->insert([
            'name' => 'Pere'
        ]);
        DB::table('relation')->insert([
            'name' => 'Tuteur Legal'
        ]);
    }
}
