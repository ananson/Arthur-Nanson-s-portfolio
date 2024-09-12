<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Str;

class Expense extends Model

{

    protected $primaryKey = 'expense_id';

    protected $fillable = [
        'title',
        'date',
        'from',
        'to',
        'amount',
        'description',
        'group_id',

    ];

    public $incrementing = false;

    protected $casts = [
        'to' => 'array',
    ];

    protected static function boot()
    {
        parent::boot();

        static::creating(function ($expense) {
            $expense->expense_id = Str::uuid();
        });
    }
    public function users()
    {
        return $this->belongsToMany(User::class, 'expense_user');
    }
    public function createdByUser()
    {
        return $this->belongsTo(User::class, 'from');
    }

}
