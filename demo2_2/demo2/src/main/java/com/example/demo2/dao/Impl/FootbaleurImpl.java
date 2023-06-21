package com.example.demo2.dao.Impl;

import com.example.demo2.dao.daoFootbaleur;
import com.example.demo2.entities.Equipe;
import com.example.demo2.entities.Footbaleur;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Connection;
import java.util.List;

public class FootbaleurImpl implements daoFootbaleur {
    private Connection conn= DB.getConnection();


    @Override
    public void insert(Footbaleur footbaleur) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(
                    "INSERT INTO footbaleur (nom, prenom, dateNaissance, position, equipe_id) VALUES (?, ?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, footbaleur.getNom());
            ps.setString(2, footbaleur.getPrenom());
            ps.setDate(3, new java.sql.Date(footbaleur.getDateNaissance().getTime()));
            ps.setString(4, footbaleur.getPosition());
            ps.setInt(5, footbaleur.getEquipe().getId());


            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);

                    footbaleur.setId(id);
                }

                DB.closeResultSet(rs);
            } else {
                System.out.println("Aucune ligne renvoyé");;
            }
        } catch (SQLException e) {
            System.err.println("problème d'insertion d'un vendeur");;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void update(Footbaleur footbaleur) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(
                    "UPDATE footbaleur SET nom = ?, prenom = ?, dateNaissance = ?, Position = ? ,equipe_id = ? WHERE Id = ?");

            ps.setString(1, footbaleur.getNom());
            ps.setString(2, footbaleur.getPrenom());
            ps.setDate(3, new java.sql.Date(footbaleur.getDateNaissance().getTime()));
            ps.setString(4, footbaleur.getPosition());
            ps.setInt(5, footbaleur.getEquipe().getId());
            ps.setInt(5, footbaleur.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de mise à jour d'un vendeur");;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("DELETE FROM footbaleur WHERE id = ?");

            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("problème de suppression d'un vendeur");;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public Footbaleur findById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(
                    "SELECT s.*, e.nom AS equipenom FROM footbaleur s INNER JOIN equipe e ON s.equipe_id = e.id WHERE s.id = ?");

            ps.setInt(1, id);

            rs = ps.executeQuery();

            if (rs.next()) {
                Equipe equipe = instantiateEquipe(rs);
                Footbaleur footbaleur = instantiateFootbaleur(rs, equipe);

                return footbaleur;
            }

            return null;
        } catch (SQLException e) {
            System.err.println("problème de requête pour trouver le vendeur");
            return null;
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Footbaleur> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(
                    "SELECT f.*, e.nom as equipenom FROM footbaleur f INNER JOIN equipe e ON f.equipe_id = e.id ORDER BY f.nom");
            rs = ps.executeQuery();
            List<Footbaleur> list = new ArrayList<>();
            Map<Integer, Equipe> map = new HashMap<>();

            while (rs.next()) {
                Equipe equ = map.get(rs.getInt("equipe_id"));

                if (equ == null) {
                    equ = instantiateEquipe(rs);

                    map.put(rs.getInt("equipe_id"), equ);
                }

                Footbaleur footbaleur = instantiateFootbaleur(rs, equ);

                list.add(footbaleur);
            }

            return list;
        } catch (SQLException e) {
            System.err.println("problème de requête pour sélectionner les vendeurs");
            return null;
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }

    @Override
    public List<Footbaleur> findByEquipe(Equipe equipe) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(
                    "SELECT f.*, e.nom as equipenom FROM footbaleur f INNER JOIN equipe e ON f.DepartmentId = e.id WHERE f.equipe_id = ? ORDER BY f.nom");

            ps.setInt(1, equipe.getId());

            rs = ps.executeQuery();
            List<Footbaleur> list = new ArrayList<>();
            Map<Integer, Equipe> map = new HashMap<>();

            while (rs.next()) {
                Equipe eq = map.get(rs.getInt("DepartmentId"));

                if (eq == null) {
                    eq = instantiateEquipe(rs);

                    map.put(rs.getInt("DepartmentId"), eq);
                }

                Footbaleur footbaleur1 = instantiateFootbaleur(rs, eq);

                list.add(footbaleur1);
            }

            return list;
        } catch (SQLException e) {
            System.err.println("problème de requête pour sélectionner les vendeurs d'un département donné");
            return null;
        } finally {
            DB.closeStatement(ps);
            DB.closeResultSet(rs);
        }
    }



    private Footbaleur instantiateFootbaleur(ResultSet rs, Equipe equipe) throws SQLException {
        Footbaleur footbaleur = new Footbaleur();

        footbaleur.setId(rs.getInt("id"));
        footbaleur.setNom(rs.getString("nom"));
        footbaleur.setPrenom(rs.getString("prenom"));
        footbaleur.setDateNaissance(new java.util.Date(rs.getTimestamp("dateNaissance").getTime()));
        footbaleur.setPosition(rs.getString("position"));
        footbaleur.setEquipe(equipe);

        return footbaleur;
    }

    private Equipe instantiateEquipe(ResultSet rs) throws SQLException {
        Equipe equipe = new Equipe();

        equipe.setId(rs.getInt("id"));
        equipe.setNom(rs.getString("nom"));
        equipe.setDateCreation(new java.util.Date(rs.getTimestamp("dateCreation").getTime()));
        equipe.setEntreneur(rs.getString("Entreneur"));



        return equipe;
    }

}