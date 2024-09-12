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
        Schema::create('medical_card', function (Blueprint $table) {
            $table->string('national_number', 11)->primary();
            $table->string('first_name',255);
            $table->string('last_name',255);
            $table->boolean('can_participate')->default(0);
            $table->string('doctor', 255)->nullable();
            $table->boolean('tetanos_protected')->default(0);
            $table->text('allergies')->nullable();
            $table->text('medecins')->nullable();
            $table->date('birth_date');
            $table->text('additional_infos')->nullable();
            $table->string('street', 255);
            $table->string('no', 4);
            $table->string('mail_box', 4);
            $table->integer('postal_code');
            $table->string('city', 255);
            $table->timestamps();
            // Vous pouvez également ajouter d'autres contraintes si nécessaire
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('medical_card');
    }
};
