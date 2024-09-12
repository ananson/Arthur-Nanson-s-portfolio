<?php

namespace App\Http\Controllers;

use Illuminate\Contracts\Support\Renderable;
use Illuminate\Http\Request;

class HomeController extends Controller
{
    /**
     * Create a new controller instance.
     *
     * @return void
     */
    public function __construct()
    {
        $this->middleware('auth');
    }

    /**
     * Show the application dashboard.
     *
     * @return Renderable
     */
    public function index(): Renderable
    {
        return view('home');
    }

    public function showGenerateKeyPairView(Request $request)
    {
        $publicKey = $request->input('publicKey');

        // Handle the public key as needed (e.g., store it in the database)
        // ...
        
        return response()->json(['message' => 'Public key stored successfully']);
    }
}
