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
        Schema::create('employees', function (Blueprint $table) {
            $table->id();
            $table->string('name')->nullable();
            $table->string('google_id')->nullable(false);
            $table->string('email')->unique();
            $table->string('avatar')->nullable();
            $table->string('address')->nullable();
            $table->string('phone_number')->nullable();
            $table->string('role')->default("employee");
            $table->string('active')->default("false");
            $table->timestamps();
        });
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        Schema::dropIfExists('employees');
    }
};
