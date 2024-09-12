<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Str;

class Group extends Model
{
    protected $primaryKey = 'group_id';
    public $incrementing = false;

    protected $fillable = ['group_id', 'name', 'owner_id'];

    public function owner(): \Illuminate\Database\Eloquent\Relations\BelongsTo
    {
        return $this->belongsTo(User::class, 'owner_id');
    }

    public function members()
    {
        return $this->belongsToMany(User::class, 'group_user', 'group_id', 'user_id');
    }
    public function users()
    {
        return $this->belongsToMany(User::class, 'group_user', 'group_id', 'user_id');
    }
    public function expenses()
    {
        return $this->hasMany(Expense::class, 'group_id');
    }

    protected static function boot()
    {
        parent::boot();

        static::creating(function ($group) {
            $group->group_id = Str::uuid();
        });
    }

}
