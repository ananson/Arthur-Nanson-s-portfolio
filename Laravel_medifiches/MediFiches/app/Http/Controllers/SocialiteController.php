<?php


namespace App\Http\Controllers;

use Illuminate\Http\Request;

use Socialite; // <-- ne pas oublier 

class SocialiteController extends Controller
{
    // Les tableaux des providers autorisés
    protected $providers = ["google"];

    # La vue pour les liens vers les providers
    public function loginRegister () {
    	return view("socialite.login-register");
    }

    # redirection vers le provider
    public function redirect (Request $request) {

        $provider = $request->provider;

        // On vérifie si le provider est autorisé
        if (in_array($provider, $this->providers)) {
            return Socialite::driver($provider)->redirect(); // On redirige vers le provider, le moment ou je rentre ds la page google
        }
        abort(404); // Si le provider n'est pas autorisé
    }

    // Callback du provider
    public function callback (Request $request) {


        $provider = $request->provider;

        if (in_array($provider, $this->providers)) {

        	// Les informations provenant du provider
        	$data = Socialite::driver($request->provider)->user();

            // Les informations de l'utilisateur
            $user = $data->user;

            // voir les informations de l'utilisateur
            dd($user);
            // auth()->login($user);
            // return redirect('/dashboard');
         }
         abort(404);
    }
}