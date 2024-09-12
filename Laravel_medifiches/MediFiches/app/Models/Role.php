<?php

namespace App;

use Illuminate\Database\Eloquent\Model;

class Role extends Model
{
    protected $table = 'role';
    protected $primaryKey = 'name';

    public function getAllRoles()
    {
        return Role::all();
    }
}
