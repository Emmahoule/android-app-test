package com.m2cim.androidProject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import java.io.File;

/**
 * FRAGMENT PHOTO
 * Created by Emma on 17/11/2015.
 *
 * Création de la classe FragmentPhoto
 * qui hérite de la classe Fragment.
 * Dans cette classe, on va redéfinir la méthode la méthode onCreateView() pour créer un intent photo permettant de prendre une photo
 * et l'enregistrer dans le systeme de fichier de l'application.
 */


public class FragmentPhoto extends Fragment {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1888; // Déclaration d'une constante statique de type entier contenant un code qui permettra de tester la requête
    private File imageFile; // Déclaration d'un attribut de type File qui stockera un fichier temporaire de stockage

    /**
     * Méthode onCreate() - Initialisation de l'activité
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.w("FRGPHOTO", "*** onCreate ******************************************************");
        super.onCreate(savedInstanceState); // Appel de la méthode parente.
    }

    /**
     * Méthode onCreateView() - Création de la vue
     *
     * Dans cette méthode, on va appeler la vue correspondant au fragment,
     * puis, au clic sur un bouton,
     * on créera un intent pour demander la fonctionnalité "Appareil Photo" de notre smarthpone.
     * Une fois la photo prise, on l'enregistrera dans le système de fichier de l'application
     * */
    @Override
    // Méthode onCreateView(LayoutInflater, ViewGroup, Bundle) - Création de la vue
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.w("FRGPHOTO", "*** onCreateView ******************************************************");
        View view = inflater.inflate(R.layout.fragment_one, container, false); // Création de la vue par expansion
        Button button = (Button) view.findViewById(R.id.takePicture); // Création du bouton (avec récupération de l'id correspondant sur la vue)

        // Au clic sur le bouton
        button.setOnClickListener(new View.OnClickListener() { //  Attache d'un écouteur - Au clic sur le bouton
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); // Création d'un intent avec l'action ACTION_IMAGE_CAPTURE qui correspond à la prise de photos
                imageFile = new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), "photo.jpg"); // Création un fichier temporaire de stockage sur la carte SD
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imageFile)); // Transmission du fichier à l'intent qui appelle l'application photo
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE); // Réaction au retour de l'activité, le résultat de l'activité se trouve dans la méthode onActivityResult()
            }
        });

        return view; // Retourne la vue
    }

    @Override
    /**
     * Méthode onActivityResult() - Résultat de l'activité
     *
     * Dans cette méthode, on va effectuer plusieurs tests
     * et afficher des messages d'erreur en cas de problème
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.w("FRGPHOTO", "*** onActivityResult ******************************************************");
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) { // Vérification du code de résultat envoyé par la requête
            if (resultCode == Activity.RESULT_OK) { // Seconde vérification du code de résultat envoyé par la requête
                if (imageFile.exists()){ // Si le fichier photo existe
                    Toast.makeText(getActivity(), "Le fichier a été enregistré dans" + imageFile.getAbsolutePath(), Toast.LENGTH_LONG).show(); // Message OK
                } else {
                    Toast.makeText(getActivity(), "Erreur lors de l'enregistrement du fichier", Toast.LENGTH_LONG).show(); // Message d'erreur
                }
            }
        }
    }

    @Override
    public void onAttach(Context c) {
        super.onAttach(c);
        Log.w("FRGPHOTO", "*** onAttach ******************************************************");
    }

}
