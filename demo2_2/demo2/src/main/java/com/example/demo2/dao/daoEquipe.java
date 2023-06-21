package com.example.demo2.dao;


import com.example.demo2.entities.Equipe;

import java.util.List;

public interface daoEquipe {
    void insert(Equipe equipe);

    void update(Equipe equipe);

    Equipe deleteById(Integer id);

    Equipe findById(Integer id);

    List<Equipe> findAll();
}
