<?php
use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateParentTable extends Migration
{
    public function up()
    {
        Schema::create('parent', function (Blueprint $table) {
            $table->string('user')->primary();
            $table->string('phone_number', 255);
            $table->foreign('user')->references('email')->on('users')->cascadeOnDelete();
        });
    }

    public function down()
    {
        Schema::dropIfExists('parent');
    }
}