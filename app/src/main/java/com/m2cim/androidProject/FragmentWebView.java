package com.m2cim.androidProject;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * CLASSE FRAGMENT WEB VIEW
 * Created by Emma on 17/11/2015.
 *
 * Création de la classe FragmentWebView
 * qui hérite de la classe Fragment.
 * Dans cette classe, on va redéfinir la méthode onCreateView() pour créer une webview.
 */


public class FragmentWebView extends Fragment {

    private WebView webview; // Déclaration d'un attribut de type Webview

    @Override
    /**
     *  Méthode onCreate() - Initialisation de l'activité
     */
    public void onCreate(Bundle savedInstanceState) {
        Log.w("FRGWB", "*** onCreate ******************************************************");
        super.onCreate(savedInstanceState); // Appel de la méthode parente.
    }

    @Override
    /**
     * Méthode onCreateView() - Création de la vue
     *
     * Dans cette méthode, on créée la vue,
     * puis on créée une Webview pour laquelle on va définir plusieurs options
     * comme la une page web d'accueil par défault (ici, google) et l'activation ou non du javascript
     * */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.w("FRGWB", "*** onCreateView ******************************************************");
        View view = inflater.inflate(R.layout.fragment_three, container, false); // Création de la vue par expansion
        webview = (WebView)view.findViewById(R.id.webview);  // Création de la webview (avec récupération de l'id correspondant sur la vue)
        webview.getSettings().setJavaScriptEnabled(true); // Activation du Javascript
        webview.loadUrl("http://www.google.com"); // Chargement d'une nouvelle page dans la vue web.
        webview.setWebViewClient(new WebViewClient()); // Définition de WebViewClient qui va recevoir diverses notifications et requêtes. Remplace le gestionnaire de base.
        return view; // Retourne la vue
    }

    @Override
    public void onAttach(Context c) {
        super.onAttach(c);
        Log.w("FRGWB", "*** onAttach ******************************************************");
    }

}
