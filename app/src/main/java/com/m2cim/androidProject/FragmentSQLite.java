package com.m2cim.androidProject;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.database.Cursor;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.FilterQueryProvider;
import android.widget.SimpleCursorAdapter;
import android.widget.AdapterView.OnItemClickListener;


/**
 * CLASSE FRAGMENT SQLITE
 * Created by Emma on 13/12/2015.
 *
 * Création de la classe FragmentSQLite
 * qui hérite de la classe Fragment.
 *
 */

public class FragmentSQLite extends Fragment {

    private DbAdapter dbHelper; // Déclaration d'un attribut de type DbAdapter
    private SimpleCursorAdapter dataAdapter; // Déclaration d'un attribut de type SimpleCursorAdapter, qui nous permettra de naviguer dans le TextView
    private ListView listView; // Déclaration d'un attribut de type ListView
    private EditText filter; // Déclaration d'un attribut de type EditText

    /**
     * Méthode onCreate() - Initialisation de l'activité
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.w("FRGSQLite", "*** onCreate ******************************************************");
        super.onCreate(savedInstanceState); // Appel de la méthode parentes
    }

    /**
     * Méthode onCreateView() - Création de la vue
     * Dans cette méthode, on créée la vue,
     * puis, on créée :
     * - une listeView qui nous permettra d'afficher nos données
     * - un EditText qui contiendra notre input pour filtrer nos données
     * - notre base de donnée
     *
     * Dans cette méthode, on va lancer notre BDD, la remplir
     * et afficher les données dans une ListView.
     * Suivant les événements, on filtrera la BDD.
     * */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.w("FRGSQLite", "*** onCreateView ******************************************************");
        View view = inflater.inflate(R.layout.fragment_four, container, false);  // Création de la vue par expansion

        listView = (ListView) view.findViewById(R.id.listView); // Création d'une ListView (avec récupération de l'id correspondant sur la vue)
        filter = (EditText) view.findViewById(R.id.filter); // Création d'un EditText (avec récupération de l'id correspondant sur la vue)

        dbHelper = new DbAdapter(getActivity()); // Création d'un objet de type DbAdapter - Création de la BDD
        dbHelper.open(); // Ouverture de la BDD
        dbHelper.deleteAll(); // Suppression de toutes les données
        dbHelper.insert(); // Insertion des données
        displayListView(); // Génération de la ListView contenant les entrées de la BDD

        return view; // Retourne la vue
    }

    /**
     * Méthode displayListView() - Création de la ListeView
     *
     * Dans cette méthode, on va créer un SimpleCursorAdapter
     * et l'attacher au curseur "cursor" fourni en résultat de la requête de sélection de la base de données.
     * Les colonnes de données retournées par le curseur sont alors mappées à notre vue personnalisée pour l'affichage.
     * */
    private void displayListView() {
        Cursor cursor = dbHelper.fetchAll(); // Sélection de toutes les données
        String[] columns = new String[] { // Tableau qui contient les colonnes de la BDD à afficher
                DbAdapter.KEY_NAME,
                DbAdapter.KEY_FORMATION,
                DbAdapter.KEY_OPTION,
        };
        int[] to = new int[] { // Tableau qui contient les id des champs définis dans le  XML
                R.id.name,
                R.id.formation,
                R.id.option,
        };
        dataAdapter = new SimpleCursorAdapter(getActivity(), R.layout.layout_student_info, cursor, columns, to, 0); // Création de l'adapter  en utilisant le curseur pointant sur les données que l'on veut afficher ainsi que les informations de mise en page
        listView.setAdapter(dataAdapter); // Assignation de l'adapter à la ListView
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView listView, View view,
                                    int position, long id) { // Au clic sur la ListView
                Cursor cursor = (Cursor) listView.getItemAtPosition(position); // Récupération du curseur - Positionnement du cruseur à la ligne correspondante dans la liste des résultats
                String studentCode = cursor.getString(cursor.getColumnIndexOrThrow("name")); // Récupération du nom de l'étudiant dans la BDD
                Toast.makeText(getActivity().getApplicationContext(),studentCode, Toast.LENGTH_SHORT).show(); // Affichage de la capitale de l'état
            }
        });

        // Attachement d'un listerner à notre EditText pour écouter le changement de texte
        filter.addTextChangedListener(new TextWatcher() {
            // Méthode afterTextChanged(Editable s) - Après le changement de texte
            public void afterTextChanged(Editable s) {
            }
            // Méthode beforeTextChanged(CharSequence s, int start, int count, int after) - Avant le changement de texte
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            // Méthode onTextChanged(CharSequence s, int start, int before, int count) - Au  changement de texte
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dataAdapter.getFilter().filter(s.toString()); // On lance la méthode de filtre
            }
        });

        // Création de la requête pour filtrer la selection
        dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return dbHelper.fetchByName(constraint.toString()); // retourne la sélection filtrée par nom
            }
        });

    }

    @Override
    public void onAttach(Context c) {
        super.onAttach(c);
        Log.w("FRGSQLite", "*** onAttach ******************************************************");
    }

}
