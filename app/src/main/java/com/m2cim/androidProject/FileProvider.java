package com.m2cim.androidProject;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * CLASSE FILE PROVIDER
 * Created by Emma on 06/12/2015.
 *
 * Création de la classe FileProvider (notre fournisseur de contenu)
 * qui hérite de la classe ContentProvider.
 * Cette classe va nous permettre de partager des données de façon sécuritaire.
 * Dans cette classe, nous redéfinierons les méthodes getType et openAssetFile()
 * pour permettre l'ouverture d'un asset
 */


public class FileProvider extends ContentProvider {

    private static final String PDF_PATH = "public_pdfs/"; // Attribut contenant le nom du dossier contenant nos PDF

    @Override
    /**
     * Méthode getType(Uri uri) - Lire le type des données
     *
     * Dans cette méthode, on retourne le type du fichier, ici, un PDF
     */
    public String getType(Uri uri) {
        return("application/pdf"); // Retourne le type du fichier
    }

    @Override
    /**
     * Méthode openAssetFile(Uri uri, String mode) - Donner l'accès aux assets
     *
     * Dans cette méthode, on va donner l'accès au dossier asset
     * pour autoriser le partage des données présentes dans le dossier "public_pdfs" des assets
     */
    public AssetFileDescriptor openAssetFile(Uri uri, String mode) throws FileNotFoundException {
        AssetManager assetManager = getContext().getAssets(); // Récupération de ce qui est stocké dans les assets
        String fileName = uri.getLastPathSegment(); // Récupération du dernier segment du chemin d'accès
        if (fileName == null) throw new FileNotFoundException(); // Si le nom du fichier est null, on capture l'exception
        AssetFileDescriptor assetFileDescriptor = null; // Déclaration d'un attribut de type AssetFileDescriptor
        try // Gestion des erreurs
        {
            assetFileDescriptor = assetManager.openFd(PDF_PATH + fileName); // Ouverture de l'asset
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return assetFileDescriptor; // Retourne l'asset
    }

    @Override
    public boolean onCreate() { return(true); }
    @Override public Cursor query(Uri url, String[] projection, String selection, String[] selectionArgs, String sort) { return null; }
    @Override public Uri insert(Uri uri, ContentValues initialValues) { return null; }
    @Override public int update(Uri uri, ContentValues values, String where, String[] whereArgs) { return 0; }
    @Override public int delete(Uri uri, String where, String[] whereArgs) { return 0; }
}