package com.example.demo2.entities;

import java.util.Date;
import java.util.Objects;

public class Footbaleur {
    private int id;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String position;
    private Equipe equipe;


    public Footbaleur() {

    }


    public Footbaleur(int id, String nom, String prenom, Date dateNaissance, String position, Equipe equipe_id) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.position = position;
        this.equipe= equipe_id;
    }


    @Override
    public String toString() {
        return "Footbaleur{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", dateNaissance=" + dateNaissance +
                ", position='" + position + '\'' +
                ", equipe_id=" + equipe+
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Footbaleur that = (Footbaleur) o;
        return id == that.id && nom.equals(that.nom) && prenom.equals(that.prenom) && dateNaissance.equals(that.dateNaissance) && position.equals(that.position) && equipe.equals(that.equipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom, dateNaissance, position, equipe);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Equipe getEquipe() {
        return equipe;
    }

    public void setEquipe(Equipe equipe) {
        this.equipe = equipe;
    }
}
