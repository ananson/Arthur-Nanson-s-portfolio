<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Illuminate\Support\Facades\DB;

class MedicalCard extends Model
{
    protected $table = 'medical_card';
    protected $primaryKey = 'national_number';
    protected $fillable = [
        'national_number',
        'first_name',
        'last_name',
        'can_participate',
        'doctor',
        'tetanos_protected',
        'allergies',
        'medecins',
        'birth_date',
        'additional_infos',
        'street',
        'no',
        'mail_box',
        'postal_code',
        'city',
    ];
    protected $casts = [
        'can_participate' => 'boolean',
        'tetanos_protected' => 'boolean',
    ];

    public static function createMedicalCard($data)
    {
        $medicalCard = new self;
        $medicalCard->national_number = $data['national_number'];
        $medicalCard->fill($data);
        $medicalCard->save();
        return $medicalCard;
        //return self::create($data);
    }

    public static function updateMedicalCard($id, $data)
    {
        $medicalCard = self::find($id);
        if ($medicalCard) {
            $medicalCard->update($data);
            return $medicalCard;
        }
        return null;
    }

    public static function deleteMedicalCard($id)
    {
        $medicalCard = self::find($id);
        if ($medicalCard) {
            $medicalCard->delete();
            return true;
        }
        return false;
    }

    public static function getAllMedicalCards()
    {
        return self::all();
    }

    public static function getMedicalCardById($id)
    {
        return self::find($id);
    }

    public static function getUserEmail($email){
        $data = DB::table('medical_card as Mc')->join('parental_link as pt','pt.national_number','=','Mc.national_number')->where('parent_1',$email)->orWhere('parent_2',$email)->get();
        return $data;
    }
    public static function getAllMedicalCardsAllergies(){
        $data = DB::table('medical_card')->whereNotNull("allergies")->get();
        return $data;
    }

}
