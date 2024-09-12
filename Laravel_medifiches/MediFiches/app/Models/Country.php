<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Country extends Model
{
    protected $table = 'countries';
    protected $primaryKey = 'name';

    public function getAllCountry()
    {
        return Country::all();
    }
}
