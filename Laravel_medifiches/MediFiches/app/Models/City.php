<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class City extends Model
{
    protected $table = 'cities';
    protected $primaryKey = 'name';

    public function getAllCities()
    {
        return City::all();
    }
}
