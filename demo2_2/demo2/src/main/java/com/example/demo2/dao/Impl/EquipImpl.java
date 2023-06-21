package com.example.demo2.dao.Impl;


import com.example.demo2.dao.daoEquipe;
import com.example.demo2.entities.Equipe;

import java.sql.Connection;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Statement;

public class EquipImpl implements daoEquipe {

    private Connection conn= DB.getConnection();

    @Override
    public void insert(Equipe equipe) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("INSERT INTO equipe (nom, dateCreation, Entreneur) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, equipe.getNom());
            ps.setDate(2, new java.sql.Date(equipe.getDateCreation().getTime()));
            ps.setString(3, equipe.getEntreneur());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet rs = ps.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1);

                    equipe.setId(id);
                }

                DB.closeResultSet(rs);
            } else {
                System.out.println("Aucune ligne renvoyée");
            }
        } catch (SQLException e) {
            System.err.println("problème d'insertion d'un département");;
        } finally {
            DB.closeStatement(ps);
        }
    }

    @Override
    public void update(Equipe equipe) {
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement("UPDATE equipe SET nom = ?, dateCreation = ?, Entreneur = ? WHERE id = ?");
            ps.setString(1, equipe.getNom());
            ps.setDate(2, new java.sql.Date(equipe.getDateCreation().getTime()));
            ps.setString(3, equipe.getEntreneur());
            ps.setInt(4, equipe.getId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Equipe updated successfully.");
            } else {
                System.out.println("No rows were affected by the update operation.");
            }
        } catch (SQLException e) {
            System.err.println("Error occurred while updating the equipe.");
        } finally {
            DB.closeStatement(ps);
        }
    }



    @Override
    public Equipe deleteById(Integer id) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Equipe equipe = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM equipe WHERE id = ?");
            ps.setInt(1, id);
            rs = ps.executeQuery();

            if (rs.next()) {
                equipe = new Equipe();

                equipe.setId(rs.getInt("id"));
                equipe.setNom(rs.getString("Nom"));
                equipe.setDateCreation(new java.util.Date(rs.getTimestamp("dateCreation").getTime()));
                equipe.setEntreneur(rs.getString("Entreneur"));

                // Delete the corresponding record from the database
                ps = conn.prepareStatement("DELETE FROM equipe WHERE id = ?");
                ps.setInt(1, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            System.err.println("Problem retrieving or deleting the equipe with ID: " + id);
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }

        return equipe;
    }



    @Override
    public Equipe findById(Integer id) {
        return null;
    }

    @Override
    public  List<Equipe> findAll() {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement("SELECT * FROM equipe");
            rs = ps.executeQuery();

            List<Equipe> listDepartment = new ArrayList<>();

            while (rs.next()) {
                Equipe equipe = new Equipe();

                equipe.setId(rs.getInt("id"));
                equipe.setNom(rs.getString("Nom"));
                equipe.setDateCreation(new java.util.Date(rs.getTimestamp("dateCreation").getTime()));
                equipe.setEntreneur(rs.getString("Entreneur"));
                listDepartment.add(equipe);
            }
            return listDepartment;
        } catch (SQLException e) {
            System.err.println("problème de requête pour sélectionner un département");;
            return null;
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(ps);
        }
    }
}
