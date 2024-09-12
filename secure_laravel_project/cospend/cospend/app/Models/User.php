<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Foundation\Auth\User as Authenticatable;
use Illuminate\Notifications\Notifiable;
use Illuminate\Support\Str;
use Laravel\Sanctum\HasApiTokens;
use App\Models\Group;
use phpseclib3\Crypt\RSA;


class User extends Authenticatable
{
    use HasApiTokens, HasFactory, Notifiable;
    private static $paths_to_private_key;
    public $incrementing = false;
    protected $primaryKey = 'id';


    /**
     * The attributes that are mass assignable.
     *
     * @var array<int, string>
     */
    protected $fillable = [
        'name',
        'email',
        'password',
        'role',
        'private_key',
        'public_key',
    ];

    /**
     * The attributes that should be hidden for serialization.
     *
     * @var array<int, string>
     */
    protected $hidden = [
        'password',
        'remember_token',
    ];

    /**
     * The attributes that should be cast.
     *
     * @var array<string, string>
     */
    protected $casts = [
        'email_verified_at' => 'datetime',
    ];

    /**
     * Verify if the user is an admin.
     */
    public function isAdmin()
    {
        return $this->role === 'admin';
    }

    public function groups()
    {
        return $this->belongsToMany(Group::class, 'group_user', 'user_id', 'group_id');
    }
    public function belongsToGroup($group)
    {
        return $this->groups->contains($group);
    }

    protected static function boot()
    {
        parent::boot();

        static::creating(function ($user) {
            $user->id = Str::uuid();
        });
    }

 
    public function generateAndStoreKeyPair()
    {
        self::$paths_to_private_key = env('PATH_TO_PRIVATE_KEY');
        $path = self::$paths_to_private_key . $this->email . '.pem';
        $opensslPath = 'C:\Program Files\OpenSSL-Win64\bin\openssl.exe';
        $commands = '"' . $opensslPath . '" genpkey -algorithm RSA -out ' . $path . ' -pkeyopt rsa_keygen_bits:2048 2>&1';

        exec($commands);
        

        $publicKeyPath = self::$paths_to_private_key . $this->email . '.pub';
        exec('"' . $opensslPath . '" rsa -in ' . $path . ' -pubout -out ' . $publicKeyPath);

        $publicKeyContent = file_get_contents($publicKeyPath);
        $this->private_key = self::$paths_to_private_key . $this->email . '.pem';

        $this->public_key = $publicKeyContent;
        $this->save();
    }




}
