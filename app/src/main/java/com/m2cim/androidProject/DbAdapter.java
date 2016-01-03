package com.m2cim.androidProject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * CLASSE DBADATER
 * Created by Emma on 23/12/2015.
 *
 * Création de la classe DbAdapter
 * Cette classe est un helper qui va nous permettre d'encapsuler toutes les complexités d'accès à la BDD
 * Dans cette classe, on va  définir une autre classe "DatabaseHelper" qui hérite de la classe SQLiteOpenHelper
 */


public class DbAdapter {

    // Définition des noms des colonnes de la table Student
    public static final String KEY_ROWID = "_id";
    public static final String KEY_NAME = "name";
    public static final String KEY_FORMATION = "formation";
    public static final String KEY_OPTION = "option";

    private static final String TAG = "StudentsDbAdapter"; // Déclaration d'un attribut de type String qui servira de TAG
    private DatabaseHelper mDbHelper; // Déclaration d'un attribut de type DatabaseHelper
    private SQLiteDatabase mDb; // Déclaration d'un attribut de type SQLiteDatabase

    private static final String DATABASE_NAME = "Master2CIM";  // Définition d'un attribut contenant le nom de la BDD
    private static final String SQLITE_TABLE = "Students"; // Définition d'un attribut contenant le nom de la table
    private static final int DATABASE_VERSION = 1; // Définition d'un attribut contenant la version de la BDD

    private final Context mCtx; // Déclaration d'un attribut de type Contect

    // Définition d'une requête SQL permettant la création de la BDD
    private static final String DATABASE_CREATE =
            "CREATE TABLE if not exists " + SQLITE_TABLE + " (" +
                    KEY_ROWID + " integer PRIMARY KEY autoincrement," +
                    KEY_NAME + "," +
                    KEY_FORMATION + "," +
                    KEY_OPTION + "," +
                    " UNIQUE (" + KEY_NAME +"));";

    /**
     * CLASSE DATABASEHELPER
     *
     * Création de la classe DatabaseHelper qui hérite de la classe SQLiteOpenHelper.
     * Dans cette classe, on va définir toutes les méthodes nous permettant d'agir sur la BDD
     * (ouverture, fermeture, upgrade)
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {

        /**
         * Constructeur
         *
         * Création d'un objet helper pour créer, ouvrir, et gérer la BDD.
         */
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION); // Appel de la méthode parente
        }

        /**
         * Méthode onCreate() - Création de la base de donnée
         *
         * A la création de la BDD, on lance la requête SQL DATABASE_CREATE
         * pour créer la table student
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            Log.w(TAG, DATABASE_CREATE);
            db.execSQL(DATABASE_CREATE); // Exécution de la requête SQL de création de la base
        }

        /**
         * Méthode onUpgrade() - Mise à jour de la BDD
         *
         * A la mise à jour de la BDD, on lance un message pour prévenir qu'il y a une mise à jour
         * puis on supprimer toutes les données de la table
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w(TAG, "Mise à jour de la BDD de la version " + oldVersion + " à la version "
                    + newVersion + ", qui va détruire toutes vos anciennes données");
            db.execSQL("DROP TABLE IF EXISTS " + SQLITE_TABLE); // Exécution d'une requête de suppression de la table
            onCreate(db);
        }
    }

    /**
     * Constructeur
     */
    public DbAdapter(Context ctx) {
        this.mCtx = ctx;
    }

    /**
     * Méthode open()
     *
     * Création et ouverture de la BDD
     */
    public DbAdapter open() throws SQLException {
        mDbHelper = new DatabaseHelper(mCtx); // Instancation d'un objet de type DatabaseHelper qui permet de gérer l'ouverture, la fermeture et la mise à jour de la BDD
        mDb = mDbHelper.getWritableDatabase(); // Créaton et ouverture la BDD

        return this;
    }

    /**
     * Méthode close()
     *
     * Fermeture de la BDD
     */
    public void close() {
        if (mDbHelper != null) {
            mDbHelper.close(); // Fermeture de la BDD
        }
    }

    /**
     * Méthode create()
     *
     * Création et ajout des colonnes nom, formation et option dans la table student
     */
    public long create(String name, String formation, String option) {
        ContentValues initialValues = new ContentValues(); // instanciation d'un objet de type ContentValues utilisé pour stocker un ensemble de valeurs qui pourront être traitées par le modèle
        initialValues.put(KEY_NAME, name); // Ajout d'une valeur
        initialValues.put(KEY_FORMATION, formation);
        initialValues.put(KEY_OPTION, option);

        return mDb.insert(SQLITE_TABLE, null, initialValues); // Insertion des valeurs dans la table Student
    }

    /**
     * Méthode deleteAll()
     *
     * Suppression de toutes les données de la BDD
     */
    public boolean deleteAll() {
        int doneDelete = 0; // Définition d'un attribut doneDelete à 0
        doneDelete = mDb.delete(SQLITE_TABLE, null , null); // Suppression des colonnes de la table Student
        Log.w(TAG, Integer.toString(doneDelete));
        return doneDelete > 0; // Retourne le nombre de valeurs supprimées
    }

    /**
     * Méthode fetchByName() de type cursor
     *
     * Affichage des données en fonction de la recherche effectuée (via l'input de recherche créé précédemment)
     */
    public Cursor fetchByName(String inputText) throws SQLException {
        Log.w(TAG, inputText);
        Cursor mCursor = null;
        if (inputText == null  ||  inputText.length () == 0)  { // Si il n'y a pas d'entrée dans l'input
            mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID, // On effectue une requête pour afficher toutes les données de la base
                            KEY_NAME, KEY_FORMATION, KEY_OPTION},
                    null, null, null, null, null);

        }
        else { // Sinon
            mCursor = mDb.query(true, SQLITE_TABLE, new String[] {KEY_ROWID, // On effectue une requête pour afficher les données commençant par la chaine de caracètre présente dans l'input
                            KEY_NAME, KEY_FORMATION, KEY_OPTION},
                    KEY_NAME + " like '%" + inputText + "%'", null,
                    null, null, null, null);
        }
        if (mCursor != null) { // si on arrive au bout de la colonne
            mCursor.moveToFirst(); // On replace le cursor à la première colonne
        }
        return mCursor; // retour des données du cursor

    }

    /**
     * Méthode fetchAll() de type cursor
     *
     * Affichage de toutes les données
     */
    public Cursor fetchAll() {
        Cursor mCursor = mDb.query(SQLITE_TABLE, new String[] {KEY_ROWID, // On effectue une requête pour afficher toutes les données de la base
                        KEY_NAME, KEY_FORMATION, KEY_OPTION},
                null, null, null, null, null);
        if (mCursor != null) { // si on arrive au bout de la colonne
            mCursor.moveToFirst(); // On replace le cursor à la première colonne
        }
        return mCursor; // retour des données du cursor
    }

    /**
     * Méthode insert()
     *
     * Insertion des données dans la BDD grâce à la méthode create()
     */
    public void insert() {
        create("Charlotte Massanes", "Master 2 CIM", "DA");
        create("Pauline Champavier", "Master 2 CIM", "DA");
        create("Jessica Louvel", "Master 2 CIM", "DA");
        create("Lucas Grossot", "Master 2 CIM", "DA");
        create("Charles Monier", "Master 2 CIM", "DA");
        create("Sebastien Annoni", "Master 2 CIM", "DA");
        create("Camille Cousin", "Master 2 CIM", "DT");
        create("Yassin Bakiri", "Master 2 CIM", "DT");
        create("Yassin Ghandri", "Master 2 CIM", "DT");
        create("Jérémy Ballot", "Master 2 CIM", "DT");
        create("Quentin Boudet", "Master 2 CIM", "DT");
        create("Emma Houlé", "Master 2 CIM", "DT");
    }
}
