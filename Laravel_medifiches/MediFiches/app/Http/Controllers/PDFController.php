<?php

namespace App\Http\Controllers;

use Barryvdh\DomPDF\Facade\Pdf as PDF;
use Illuminate\Http\Request;
use App\Models\Infos_Pdf;
use App\Models\MedicalCard;
use App\Models\Person;
use App\Models\User;
use Dompdf\Dompdf;
use Dompdf\Options;
use Illuminate\Support\Facades\DB;

class PDFController extends Controller
{
    public function generatePDFs()
    {
        // Récupérez les données de la table 'Infos_Pdf'
        $data = Infos_Pdf::find(2);

        // Récupérez les données de la table 'MedicalCard' en utilisant la méthode statique
        $medical_card = MedicalCard::find(123456789);

        if (!$medical_card) {
            return redirect()->route('animateur/fiche_medicale')->with('error', 'Fiche médicale non trouvée.');
        }

        if ($data) {
            $pdf = PDF::loadView('animateur/fiche_medicale', compact('data', 'medical_card'));
            // Configuration des options Dompdf
            $dompdf = $pdf->getDomPDF();
            $options = $dompdf->getOptions();
            $options->set('isHtml5ParserEnabled', true);
            $options->set('isPhpEnabled', true);
            $options->set('debug', true);
            // Chemin vers le répertoire public
            $webRoot = public_path();
            $html2 = view('animateur/pdf', compact('data', 'medical_card'))->render();
            // HTML avec arrière-plan d'image
            $html = '<html>
                <head>
                    <style>
                        body {
                            
                    }
                    .background {
                        background-image: url(' . public_path('/images/scout.png') . ');
                        background-size: 100px;
                        background-position: top left;
                        background-repeat: no-repeat;
                        width: 100px;
                        height: 100px; 
                    }
                    </style>
                </head>
                <body>
                    <div class="background"></div>' . $html2 . '
                </body>
            </html>';
            // Chargement de l'HTML dans Dompdf
            $dompdf->loadHtml($html);
            $dompdf->setBasePath($webRoot);
            // Rendu du PDF
            $dompdf->setPaper('A4', 'portrait');
            $dompdf->render();
            // Téléchargement du PDF
            $filename = $data->nom . '_' . $data->prénom . '.pdf';
            return $pdf->download($filename);
        } else {
            return redirect()->route('form')->with('error', 'Aucune donnée trouvée pour préremplir le formulaire.');
        }
    }

    public function generatePDF(Request $request)
    {
        $data = MedicalCard::find($request->national_number);
        //devoir faire ca pour l'élève récupéré via l'url pour chaque table

        if ($data) {
            $pdf = PDF::loadView('pdf', compact('data'));

            //accéder à l'instance Dompdf du PDF généré
            $dompdf = $pdf->getDomPDF();

            // Configuration des options Dompdf
            $options = $dompdf->getOptions();
            $options->set('isHtml5ParserEnabled', true);
            $options->set('isPhpEnabled', true);
            $options->set('debug', true);

            // Chemin vers le répertoire public
            $webRoot = public_path();

            $parent_infos = DB::table('parental_link')
                ->join('medical_card', 'national_number', '=', 'national_number')
                ->join('parent', 'parent_1', '=', 'user')
                ->join('parent', 'parent_2', '=', 'user')
                ->groupBy('national_number');

            $html2 = view('pdf', compact('data', 'parent_infos'))->render();
            $html = '<html>
<head>
    <style>
        body {
            
        }
        .background {
            background-image: url(' . public_path('/images/scout.png') . ');
            background-size: 100px;
            background-position: top left;
            background-repeat: no-repeat;
            width: 100px;
            height: 100px; 
            
        }
    </style>
</head>
<body>
    <div class="background"></div>' . $html2 . '
</body>
</html>';


            // Chargement de l'HTML dans Dompdf
            $dompdf->loadHtml($html);
            $dompdf->setBasePath($webRoot);

            // Rendu du PDF
            $dompdf->setPaper('A4', 'portrait');
            $dompdf->render();

            // Téléchargement du PDF
            $filename = $request->national_number . '.pdf';
            return $pdf->download($filename);
        } else {
            return redirect()->route('records')->with('error', 'Aucune donnée trouvée pour préremplir le formulaire.');
        }
    }
}
