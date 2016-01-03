package com.m2cim.androidProject;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * CLASSE FRAGMENT PDF
 * Created by Emma on 17/11/2015.
 *
 * Création de la classe FragmentPDF
 * qui hérite de la classe Fragment.
 * Dans cette classe, on va redéfinir la méthode la méthode onCreateView() pour créer un intent
 * qui nous permettra de visualiser un fichier PDF.
 * En parallèle, on crée une classe FileProvider (un fournisseur de contenu)
 * pour partager des données de façon sécuritaire.
 */


public class FragmentPDF extends Fragment {

    @Override
    /**
     * Méthode onCreate() - Initialisation de l'activité
     */
    public void onCreate(Bundle savedInstanceState) {
        Log.w("FRGPDF", "*** onCreate ******************************************************");
        super.onCreate(savedInstanceState); // Appel de la méthode parente.
    }

    /**
     * Méthode onCreateView() - Création de la vue
     *
     * Dans cette méthode, on va appeler la vue correspondant au fragment,
     * puis on va créer un intent qui permet de visionner des données (ici un fichier PDF)
     * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.w("FRGPDF", "*** onCreateView ******************************************************");
        View view = inflater.inflate(R.layout.fragment_two, container, false); // Création de la vue par expansion
        Button button = (Button) view.findViewById(R.id.btnPdf); // Création du bouton (avec récupération de l'id correspondant sur la vue)
        button.setOnClickListener(new View.OnClickListener() { // Attache d'un écouteur - Au clic sur le bouton
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW); // Création d'un intent avec l'action ACTION_VIEW qui permet (entre autres) de visualiser un fichier PDF
                Uri uri = Uri.parse("content://com.m2cim.androidProject/" + "ajax.pdf"); // Récupération d'un objet Uri à partir d'une chaîne contenant le chemin d'accès au fichier
                intent.setDataAndType(uri, "application/pdf"); // Spécification l'URI du fichier et son type MIME
                try { // try catch - Permet de capturer une erreur
                    startActivity(intent); // Si il n'y a pas d'erreur, on lance l'activité
                }
                catch (ActivityNotFoundException e) { // Si une erreur est capturée
                    Toast.makeText(getActivity(), "Aucune application disponible pour visualiser le fichier PDF", Toast.LENGTH_LONG).show(); // Message d'erreur
                }
            }
        });
        return view; // On retourne la vue
    }

    @Override
    public void onAttach(Context c) {
        super.onAttach(c);
        Log.w("FRGPDF", "*** onAttach ******************************************************");
    }

}
