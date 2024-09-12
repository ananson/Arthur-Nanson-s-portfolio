<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateGroupTable extends Migration
{
    public function up()
    {
        Schema::create('group', function (Blueprint $table) {
            $table->string('name', 255)->primary();
        });
    }

    public function down()
    {
        Schema::dropIfExists('group');
    }
}