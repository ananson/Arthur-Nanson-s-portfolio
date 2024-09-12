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
        Schema::create('additional_field', function (Blueprint $table) {
            $table->string('medical_card');
            $table->string('field_name', 255);
            $table->text('field_value');
            
            // Clé primaire composée
            $table->primary(['medical_card', 'field_name']);
            $table->foreign('medical_card')->references('national_number')->on('medical_card')->cascadeOnDelete();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('additional_field');
    }
};
