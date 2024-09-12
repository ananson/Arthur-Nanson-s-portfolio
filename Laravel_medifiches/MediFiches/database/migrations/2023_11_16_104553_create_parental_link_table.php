<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

return new class extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        Schema::create('parental_link', function (Blueprint $table) {
            $table->string('national_number')->primary();
            $table->string('parent_1');
            $table->string('parent_2')->nullable();
            $table->string('relation_1')->nullable();
            $table->string('relation_2')->nullable();
            $table->string('group')->nullable();
            $table->foreign('national_number')->references('national_number')->on('medical_card')->cascadeOnDelete();
            $table->foreign('parent_1')->references('email')->on('users')->cascadeOnDelete();
            $table->foreign('parent_2')->references('email')->on('users')->cascadeOnDelete();
            $table->foreign('relation_1')->references('name')->on('relation')->nullOnDelete();
            $table->foreign('relation_2')->references('name')->on('relation')->nullOnDelete();
            $table->foreign('group')->references('name')->on('group')->nullOnDelete();
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('parental_link');
    }
};
