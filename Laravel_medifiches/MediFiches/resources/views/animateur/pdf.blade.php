<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="fr" lang="fr">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Fiche santé individuelle</title>
    <meta name="author" content="sbz">
    <style type="text/css">
        body {
            font-family: Arial, sans-serif;
            margin-right: 1cm;
        }

        .container {
            padding: 20px;
            max-width: 800px;
            margin: 0 auto;
        }

        h1 {
            font-size: 24px;
            font-weight: bold;
        }

        h2,
        h3 {
            font-size: 18px;
            font-weight: bold;
            margin-top: 15px;
        }

        .section {
            margin-top: 15px;
        }

        p {
            font-size: 14px;
            margin: 0;
            margin-top: 10px;
        }
    </style>
</head>

<body>
    <div class="container">
        <h1>Fiche santé individuelle - Mouvement de jeunesse</h1>
        <div class="section">
            <h2>Objectif de la fiche</h2>
            <p>Cette fiche a pour objectif d'être un appui pour les animateurs ou le personnel soignant en cas de besoin. Il est essentiel que les renseignements que vous fournissez soient complets, corrects et à jour au moment des activités concernées.</p>
        </div>
        <div class="section">
            <h3>Identité de l'enfant:</h3>
            <p>Nom : Prénom : Né(e) le</p>
            <p>Adresse :</p>
            <p>Localité : CP : Tél. / GSM : </p>
            <p>Pays : E-mail :</p>
        </div>
        <div class="section">
            <h3>Personnes à contacter en cas d'urgence</h3>
            <p>Nom : </p>
            <p>Adresse : {{$medical_card->street}}</p>
            <p>Lien de parenté : </p>
            <p>Tél. / GSM :</p>
            <p>E-mail :</p>
        </div>
        <div class="section">
            <h3>Médecin traitant</h3>
            <p>Nom : </p>
            <p>Tél. / GSM : </p>
        </div>
        <div class="section">
            <h3>Informations confidentielles concernant la santé du participant</h3>
            <p>Le participant peut-il prendre part aux activités proposées ? (sport, excursions, jeux, natation…)?</p>
            <p></p>
            <p>Y a-t-il des données médicales spécifiques importantes à connaître pour le bon déroulement de l’activité/du camp ? (ex. : problèmes cardiaques, épilepsie, asthme, diabète, mal des transports, rhumatisme, somnambulisme, affections cutanées, handicap moteur ou mental…)
            <p> Indiquez la fréquence, la gravité et les actions à mettre en œuvre pour les éviter et/ou y réagir.</p>
            </p>
            <p>Le participant est-il en ordre de vaccination contre le tétanos ?</p>
            <p>Le participant est-il allergique à certaines substances, aliments ou médicaments ? Oui - Non</p>
            <p>Si oui, lesquels ?</p>
            <p>Le participant doit-il prendre des médicaments ? Si oui lesquels : En quelle quantité ?</p>
            <p>Quand ?</p>

        </div>
    </div>
</body>

</html>