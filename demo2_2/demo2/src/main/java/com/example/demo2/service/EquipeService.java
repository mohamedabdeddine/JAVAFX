package com.example.demo2.service;

import com.example.demo2.dao.Impl.EquipImpl;
import com.example.demo2.dao.daoEquipe;
import com.example.demo2.entities.Equipe;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class EquipeService {
    private daoEquipe daoequipe = new EquipImpl();

    public List<Equipe> findAll() {
        return daoequipe.findAll();
    }

    public void save(Equipe equipe) {
        daoequipe.insert(equipe);
    }


    public void update(Equipe equipe) {
        daoequipe.update(equipe);
    }

    public void remove(Equipe equipe) {
        daoequipe.deleteById(equipe.getId());
    }


    public void exporterVersExcel(String path) {
        List<Equipe> equipes = findAll();
        // Create a date format for formatting the date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        // Create a new workbook
        try (Workbook workbook = new XSSFWorkbook()) {
            // Create a new sheet
            Sheet sheet = workbook.createSheet("Equipes");
            // Create header row
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("ID");
            headerRow.createCell(1).setCellValue("Nom");
            headerRow.createCell(2).setCellValue("Date de création");
            headerRow.createCell(3).setCellValue("Entraîneur");
            // Create data rows
            int rowNumber = 1;
            for (Equipe equipe : equipes) {
                Row row = sheet.createRow(rowNumber++);
                row.createCell(0).setCellValue(equipe.getId());
                row.createCell(1).setCellValue(equipe.getNom());
                row.createCell(2).setCellValue(dateFormat.format(equipe.getDateCreation()));
                row.createCell(3).setCellValue(equipe.getEntreneur());
            }
            // Auto-size columns
            for (int i = 0; i < 4; i++) {
                sheet.autoSizeColumn(i);
            }
            // Write the workbook to a file
            try (FileOutputStream fos = new FileOutputStream(path)) {
                workbook.write(fos);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void importerDepuisExcel(String path) throws ParseException {
        try (Workbook workbook = WorkbookFactory.create(new FileInputStream(path))) {
            Sheet sheet = workbook.getSheetAt(0);

            // Start from row 1 as row 0 contains the header
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);

                int id = (int) row.getCell(0).getNumericCellValue();
                String nom = row.getCell(1).getStringCellValue();
                double dateValue = row.getCell(2).getNumericCellValue();
                String entraineur = row.getCell(3).getStringCellValue();

                // Parse the date using SimpleDateFormat
                //SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                //Date dateCreation = dateFormat.parse(dateStr);
                Date dateCreation = DateUtil.getJavaDate(dateValue);

                // Create the Equipe object
                Equipe equipe = new Equipe(id, nom, dateCreation, entraineur);

                // Save or process the equipe object as needed
                save(equipe);
            }
        } catch (IOException   e) {
            e.printStackTrace();
        }
    }
    public void exporterVersTxt(String path) {
        List<Equipe> equipes = findAll();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

            for (Equipe equipe : equipes) {
                writer.write(equipe.getId() + "," +
                        equipe.getNom() + "," +
                        dateFormat.format(equipe.getDateCreation()) + "," +
                        equipe.getEntreneur());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void importerDepuisTxt(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 4) {
                    int id = Integer.parseInt(data[0].trim());
                    String nom = data[1].trim();
                    Date dateCreation = new SimpleDateFormat("dd/MM/yyyy").parse(data[2].trim());
                    String entraineur = data[3].trim();

                    Equipe equipe = new Equipe(id, nom, dateCreation, entraineur);
                    save(equipe);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
    public void exporterVersJson(String path) {
        List<Equipe> equipes = findAll();

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File(path), equipes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void importerDepuisJson(String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Equipe> equipes = objectMapper.readValue(new File(path), new TypeReference<List<Equipe>>() {});
            for (Equipe equipe : equipes) {
                save(equipe);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}