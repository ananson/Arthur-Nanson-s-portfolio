<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Mail;
use App\Mail\SendMail;

class EmailController extends Controller
{
    public function index(String $password,String $email)
    {
        $testMailData = [
            'title' => $email,
            'body' => $password,
        ];

        Mail::to($email)->send(new SendMail($testMailData));
        dd('Success! Email has been sent successfully.');
    }
}