<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class Infos_Pdf extends Model
{
    protected $table = 'infos_pdf';
    protected $primaryKey = 'id'; // Ajoutez cette ligne pour spécifier la clé primaire

    use HasFactory;
}
