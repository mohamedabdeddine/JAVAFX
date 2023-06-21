package com.example.demo2.entities;

import java.util.Date;
import java.util.Objects;

public class Equipe {
    private int id;
    private String nom;
    private Date dateCreation;
    private String Entreneur;

    public Equipe() {

    }



    public Equipe(int id, String nom, Date dateCreation, String Entreneur) {
        this.id = id;
        this.nom = nom;
        this.dateCreation = dateCreation;
        this.Entreneur = Entreneur;
    }

    // Getters and setters
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

    @Override
    public String toString() {
        return "Equipe{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", dateCreation=" + dateCreation +
                ", Entreneur='" + Entreneur + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipe equipe = (Equipe) o;
        return id == equipe.id && Objects.equals(nom, equipe.nom) && Objects.equals(dateCreation, equipe.dateCreation) && Objects.equals(Entreneur, equipe.Entreneur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, dateCreation, Entreneur);
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getEntreneur() {
        return Entreneur;
    }

    public void setEntreneur(String Entreneur) {
        this.Entreneur = Entreneur;
    }
}

