<?php

namespace App\Forms;

class RecordForm
{
    public static function rules()
    {
        return [
            'national_number' => ['required', 'string', 'regex:/^[0-9]{11}$/', 'max:11'],
            'last_name' => ['required', 'string', 'max:255'],
            'first_name' => ['required', 'string', 'max:255'],
            'birth_date' => ['required', 'date'],
            'can_participate' => ['boolean'],
            'doctor' => ['string', 'max:255'],
            'tetanos_protected' => ['boolean'],
            'medecins' => ['nullable', 'string'],
            'allergies' => ['nullable', 'string'],
            'street' => ['required', 'string', 'max:255'],
            'no' => ['required', 'string', 'max:4'],
            'mail_box' => ['nullable', 'string', 'max:4'],
            'postal_code' => ['required', 'int'],
            'city' => ['required', 'string', 'max:255'],
            'additional_infos' => ['nullable', 'string']
        ];
    }

    public static function getFormFields()
    {
        $formFields = [
            ['name' => 'national_number', 'label' => 'No. de registre nationale', 'type' => 'text', 'required' => true],
            ['name' => 'last_name', 'label' => 'Nom', 'type' => 'text', 'required' => true],
            ['name' => 'first_name', 'label' => 'Prénom', 'type' => 'text', 'required' => true],
            ['name' => 'birth_date', 'label' => 'Date de naissance', 'type' => 'date', 'required' => true],
            ['name' => 'can_participate', 'label' => 'Peut participer', 'type' => 'checkbox', 'required' => false],
            ['name' => 'doctor', 'label' => 'Médecin traitant', 'type' => 'text', 'required' => false, 'placeholder' => 'Entrez le médecin...'],
            ['name' => 'tetanos_protected', 'label' => 'Vaccin du tétanos fait ?', 'type' => 'checkbox', 'required' => false],
            ['name' => 'medecins', 'label' => 'Médicaments', 'type' => 'text', 'required' => false, 'placeholder' => 'Entrez le(s) médicament(s)...', 'isTextArea' => true],
            ['name' => 'allergies', 'label' => 'Allergies', 'type' => 'text', 'required' => false, 'placeholder' => 'Entrez les allergies...', 'isTextArea' => true],
            ['name' => 'street', 'label' => 'Rue', 'type' => 'text', 'required' => true, 'placeholder' => 'Entrez votre rue'],
            ['name' => 'no', 'label' => 'Numéro', 'type' => 'text', 'required' => true, 'placeholder' => 'Entrez votre numéro'],
            ['name' => 'mail_box', 'label' => 'Boite Postale', 'type' => 'text', 'required' => false, 'placeholder' => 'Entrez votre numéro de boite'],
            ['name' => 'postal_code', 'label' => 'Code Postal', 'type' => 'number', 'required' => true, 'placeholder' => 'Entrez votre code postal'],
            ['name' => 'city', 'label' => 'Ville', 'type' => 'text', 'required' => true, 'placeholder' => 'Entrez votre ville'],
            ['name' => 'additional_infos', 'label' => 'Informations additionnelles', 'type' => 'textarea', 'required' => false, 'placeholder' => 'Entrez des informations supplémentaires', 'isTextArea' => true],
        ];
        return $formFields;
    }
}
