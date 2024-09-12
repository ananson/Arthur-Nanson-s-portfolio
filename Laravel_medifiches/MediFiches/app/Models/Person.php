<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;

class Person extends Model
{
    protected $fillable = [
        'national_number', 'first_name', 'last_name', 'email', 'role'
    ];
    protected $primaryKey = 'national_number';
    protected $table = 'persons';
    public static function createPerson($data)
    {
        return self::create($data);
    }

    public static function updatePerson($id, $data)
    {
        $person = self::find($id);
        if ($person) {
            $person->update($data);
            return $person;
        }
        return null;
    }

    public static function deletePerson($id)
    {
        $person = self::find($id);
        if ($person) {
            $person->delete();
            return true;
        }
        return false;
    }

    public static function getAllPersons()
    {
        return self::all();
    }

    public static function getPersonById($id)
    {
        return self::find($id);
    }
}