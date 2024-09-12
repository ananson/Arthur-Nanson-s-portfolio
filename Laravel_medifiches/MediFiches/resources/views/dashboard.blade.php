<x-app-layout>
    <x-slot name="header">
        <h2 class="font-semibold text-xl text-gray-800 leading-tight">
            {{ __('Home') }}
        </h2>
    </x-slot>

    <div class="py-12 flex items-start justify-center h-screen overflow-hidden bg-gray-100">
        <div class="w-full">
            <div class="relative">
                <img src="{{ asset('images/medi-banner.png') }}" id="banner" alt="Banner" class="mx-auto max-h-3/4-screen object-cover rounded-lg">

                <div class="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 text-center w-full">
                    <h1 class="text-white text-4xl md:text-6xl font-bold">
                        <span>MediFiches</span>
                    </h1>
                </div>

                <x-animateur/accueil />
            </div>
        </div>
    </div>
</x-app-layout>
