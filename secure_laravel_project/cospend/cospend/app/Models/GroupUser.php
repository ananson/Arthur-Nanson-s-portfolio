<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Str;

class GroupUser extends Model
{
    use HasFactory;

    protected $table = 'group_user';
    protected $primaryKey = ['group_id', 'user_id'];
    public $incrementing = false;
    public $timestamps = true;


    public function group()
    {
        return $this->belongsTo(Group::class, 'group_id', 'group_id');
    }

    public function user()
    {
        return $this->belongsTo(User::class, 'user_id', 'id');
    }
}
