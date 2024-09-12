<!DOCTYPE html>
<html lang="fr">
<head>
    <title>MediFiches @yield('title')</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.min.js" integrity="sha384-cuYeSxntonz0PPNlHhBs68uyIAVpIIOZZ5JqeqvYYIcEL727kskC66kF92t6Xl2V" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.6/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
    <link href="{{ asset('css/style.css') }}" rel="stylesheet">
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.js"></script>
    <script type="text/javascript">
        $(document).ready(function() {
            $('[data-toggle="collapse"]').on('click', function() {
                $('#navbarSupportedContent').toggleClass('show');
            })
        });
    </script>
</head>

<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light p-3 shadow fixed-top border-bottom border-dark">
        <a class="navbar-brand" href="/">MediFiches</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link" href="/fiches">Fiches médicale</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/formulaire">Créer une fiche</a>
                </li>
                
                <li class="nav-item">
                    <a class="nav-link" href="/formulaire">ajouter un organisateur</a>
                </li>
                
            </ul>
        </div>
    </nav>

    <div class="container">
        <br><br><br><br>
        @yield('content')
    </div>
</body>
</html>

