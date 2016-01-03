package com.m2cim.androidProject;

import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * CLASSE TAB LISTENER
 * Created by Emma on 17/11/2015.
 *
 * Création de la classe générique TabListener,
 * qui peut être utilisée avec des objets de type Fragment
 * et qui hérite de l'interface ActionBar.TabListener.
 * Cette classe est instanciée au touch sur un onglet,
 * et associe des événement à chaque action sur l'onglet.
 * Ici, on a choisi de redéfinir la méthode onTabSelected()
 * appellée lors de la sélection d'un onglet.
 */


public class TabListener<T extends Fragment> implements android.support.v7.app.ActionBar.TabListener {

    private Fragment fragment; // Déclaration d'un attribut de type Fragment

    /**
     * Constructeur
     *
     * On remplit l'attribut fragment avec la valeur du fragment
     * associé à l'onglet sur lequel l'écouteur a été attaché
     */
    public TabListener(Fragment fragment) {
        this.fragment = fragment;
    }

    /**
     * Méthode onTabSelected() - Appelée quand l'onglet est sélectionné
     *
     * Dans cette classe, on va remplacer le fragment courant
     * par le celui associé à l'onglet sélectionné
     */
    @Override
    public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        Log.w("ACT", "*** onTabSelected ******************************************************");
        ft.replace(R.id.container, fragment, null);
    }

    @Override
    public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        Log.w("ACT", "*** onTabUnselected ******************************************************");
    }

    @Override
    public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, android.support.v4.app.FragmentTransaction ft) {
        Log.w("ACT", "*** onTabReselected ******************************************************");
    }

}
