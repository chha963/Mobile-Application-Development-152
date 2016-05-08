<?php

use Illuminate\Database\Schema\Blueprint;
use Illuminate\Database\Migrations\Migration;

class CreateUsersTable extends Migration
{
    /**
     * Run the migrations.
     *
     * @return void
     */
    public function up()
    {
        Schema::create('users', function (Blueprint $table) {
            $table->increments('user_id');
            $table->rememberToken();
            $table->timestamps();
            $table->string('name')->unique();
            $table->string('password');
			$table->integer('type')->default(0);
            $table->string('email')->unique();
			$table->string('address');
			$table->string('latitude');
			$table->string('longitude');
			$table->string('first_name');
			$table->string('last_name');
			$table->string('phone');
			$table->integer('birthday');
			$table->string('avatar');
			$table->string('banner');
			
			$table->primary('user_id');
        });
    }

    /**
     * Reverse the migrations.
     *
     * @return void
     */
    public function down()
    {
        Schema::drop('users');
    }
}
