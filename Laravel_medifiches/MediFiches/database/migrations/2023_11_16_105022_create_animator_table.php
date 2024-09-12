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
        Schema::create('animator', function (Blueprint $table) {
            $table->string('user')->primary();
            $table->string('animation_group')->nullable();

            $table->foreign('user')->references('email')->on('users')->cascadeOnDelete();
            $table->foreign('animation_group')->references('name')->on('group')->nullOnDelete();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('animator');
    }
};
