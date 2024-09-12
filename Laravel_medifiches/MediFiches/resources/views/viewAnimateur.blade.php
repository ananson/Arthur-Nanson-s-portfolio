<x-app-layout>
    <x-slot name="header">
        <h2 class="font-semibold text-xl text-gray-800 leading-tight">
            {{ __('Ajouter un animateur') }}
        </h2>
    </x-slot>

    <div style="background-image: url('{{ asset('images/medi-banner.png') }}'); background-size: cover; background-position: center; height: 100vh; position: relative; background-attachment: fixed; margin: 0;">
        <div class="container" style="padding-top: 5rem;">
            <form action="{{ route('create_animateur') }}" method="post">
                @csrf
                <div class="mb-3">
                    <div class="row">
                        <div class="col-md-6">
                            <div class="bg-white p-4">
                                <div class="mb-3">
                                    <x-label for="email" value="Email" />
                                    <x-input id="email" class="block mt-1 w-full" type="email" name="email" :value="old('email')"/>
                                    <x-label for="first_name" value="Prénom" />
                                    <x-input id="first_name" class="block mt-1 w-full" type="text" name="first_name" :value="old('first_name')"/>
                                    <x-label for="last_name" value="Nom" />
                                    <x-input id="last_ame" class="block mt-1 w-full" type="text" name="last_name" :value="old('last_name')"/>
                                </div>  
                                <button class="btn btn-outline-primary" type="submit">{{ __('Envoyer') }}</button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</x-app-layout>
