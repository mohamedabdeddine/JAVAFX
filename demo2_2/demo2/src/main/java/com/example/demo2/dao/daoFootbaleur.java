package com.example.demo2.dao;
import com.example.demo2.entities.Equipe;
import com.example.demo2.entities.Footbaleur;

import java.util.List;

public interface daoFootbaleur {

    void insert(Footbaleur footbaleur);

    void update(Footbaleur footbaleur);

    void deleteById(Integer id);

    Footbaleur findById(Integer id);

    List<Footbaleur> findAll();

    List<Footbaleur> findByEquipe(Equipe equipe);
}
