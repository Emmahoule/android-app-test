package com.m2cim.androidProject;

/**
 * CLASSE STUDENT
 * Created by Emma on 13/12/2015.
 *
 * Dans cette classe, on définit toutes les méthodes getter et setter
 * pour faire de chaque  étudiant unique  un objet
 */


public class Student {
    String name = null;
    String formation = null;
    String option = null;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFormation() {
        return formation;
    }
    public void setFormation(String formation) {
        this.formation = formation;
    }
    public String getOption() {
        return option;
    }
    public void setOption(String region) {
        this.option = option;
    }
}
