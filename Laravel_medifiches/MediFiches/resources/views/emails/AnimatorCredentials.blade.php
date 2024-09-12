<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ajout en tant qu'organisateur</title>
</head>
<body>

    <p>Cher {{ $testMailData['last_name'] }} {{ $testMailData['first_name'] }},</p>

    <p>J'espère que ce message te trouve bien. Je m'adresse à toi au sujet de ta participation en tant qu'organisateur sur notre plateforme Medifiches.</p>

    <p>Pour commencer, je tiens à te remercier de ton engagement. Afin de faciliter le processus, voici les informations nécessaires pour que tu puisses accéder à ton compte :</p>

    <ul>
        <li><strong>Email :</strong> {{ $testMailData['email'] }}</li>
        <li><strong>Mot de passe :</strong> {{ $testMailData['password'] }}</li>
    </ul>

    <p>Te connecter est simple. Il te suffit de te rendre sur <a href="[https://gestproj2.alwaysdata.net/]">le lien de connexion</a> et d'utiliser les informations ci-dessus. Une fois connecté, n'oublie pas de personnaliser ton mot de passe dans ton profil pour des raisons de sécurité.</p>

    <p>De plus, après ta première connexion, tu recevras un email de vérification à l'adresse que tu as fournie. Merci de confirmer ta participation en suivant les instructions dans cet email.</p>

    <p>Si tu rencontres le moindre problème ou si tu as des questions, n'hésite pas à me contacter. Nous sommes là pour rendre ton expérience aussi fluide que possible.</p>

    <p>Cordialement,</p>
    <p>L'équipe Médifiches</p>

</body>
</html>
