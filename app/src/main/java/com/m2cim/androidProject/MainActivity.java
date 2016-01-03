package com.m2cim.androidProject;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

/**
 * MAIN ACTIVITY
 * Created by Emma on 17/11/2015.
 *
 * Création de la classe MainActivity (point d'entrée de l'application)
 * qui hérite de la classe ActionBarActivity ("depreciated" mais utilisé pour répondre à l'exercice 14-bis)
 * Dans cette classe, on va redéfinir la méthode onCreate(), appelée au lancement de l'activité,
 * pour créer notre ActionBar et attacher sur ses onglets des événements permettant de lancer nos différents fragments
 */


public class MainActivity extends ActionBarActivity {

    private Tab[] _tTab; // Déclaration d'un tableau de type Tab
    private final int nbTab = 4; // Déclaration d'un constante contenant le nombre de tab de l'ActionBar

    /**
     * Méthode onCreate() - Initialisation de l'activité
     *
     * Dans cette méthode, on va créer notre ActionBar et nos onglets associés
     * comme indiqué dans l'exercice 14-bis (malgré que cette méthode soit "depreciated").
     * On attachera ensuite des écouteurs sur les onglets pour appeler
     * les différents fragments au touch.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Appel de la méthode parente
        setContentView(R.layout.activity_main); // Placement de la vue dans le layout de l'activité
        Log.w("ACT", "*** onCreate ******************************************************");

        // Création de l'ActionBar
        ActionBar actionBar = getSupportActionBar(); // Création d'un objet de type ActionBar qui contient une référence à l'ActionBar de l'activité
        actionBar.setNavigationMode(actionBar.NAVIGATION_MODE_TABS); // Définition du mode de navigation de l'ActionBar, ici, navigation en Tab (en onglets)
        actionBar.setDisplayShowTitleEnabled(false); // Choix de l'affichage ou non du titre de l'activité

        // Création et définition des onglets (Tab) de l'ActionBar
        _tTab = new Tab[nbTab]; // Instanciation du tableau contenant nos tab (onglets)
        for (int i = 0; i < _tTab.length; i++) {
            _tTab[i] = actionBar.newTab(); // Remplissage du tableau _tTab avec un nouvel onglet dans chaque case
        }

        FragmentPhoto fragmentPhoto = new FragmentPhoto(); // Instanciation d'un objet de type FragmentPhoto
        _tTab[0].setText("Photo"); // Définition du texte de l'onglet
        _tTab[0].setTabListener(new TabListener<FragmentPhoto>(fragmentPhoto)); // Attachement d'un listener pour gérer les événements de sélection, qui est un objet de type TabListener utilisé avec le fragment associé à l'onglet

        FragmentPDF fragmentPDF = new FragmentPDF();
        _tTab[1].setText("PDF");
        _tTab[1].setTabListener(new TabListener<FragmentPDF>(fragmentPDF));

        FragmentWebView fragmentWebView = new FragmentWebView();
        _tTab[2].setText("Web");
        _tTab[2].setTabListener(new TabListener<FragmentWebView>(fragmentWebView));

        FragmentSQLite fragmentSQLite = new FragmentSQLite();
        _tTab[3].setText("SQL");
        _tTab[3].setTabListener(new TabListener<FragmentSQLite>(fragmentSQLite));

        // Ajout des différents onglets créés à l'ActionBar
        for (int i = 0; i < _tTab.length; i++) {
            actionBar.addTab(_tTab[i]); // Parcours du tableau pour ajouter chaque Tab à l'ActionBar
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.w("ACT", "*** onStart ******************************************************");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.w("ACT", "*** onRestart ******************************************************");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w("ACT", "*** onResume ******************************************************");
    }

    @Override
    protected void onPause() {
        Log.w("ACT", "*** onPause ******************************************************");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.w("ACT", "*** onStop ******************************************************");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.w("ACT", "*** onDestroy ******************************************************");
        super.onDestroy();
    }
}
