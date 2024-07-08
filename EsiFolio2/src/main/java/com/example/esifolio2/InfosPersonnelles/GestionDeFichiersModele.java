package com.example.esifolio2.InfosPersonnelles;

import java.io.*;

public interface GestionDeFichiersModele {

    default   void  creer_D() { //Création répertoire
        File folder = new File("src/ESIfolioapp");
        if (!folder.exists()) {
            folder.mkdir();

        }

    }

    default   File creer_f(String nom_fichier) { //Création fichier
        File file = new File("src/ESIfolioapp/" + nom_fichier);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    default String filtrer(File fichier , String mot) {
        String line="";
        try {
            BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(fichier), "UTF-8"));
            line = read.readLine();
            String mot1 = mot.toUpperCase();
            String mot2 = mot.toLowerCase();
            while (line != null) {
                if (line.contains(mot)){
                    return line;
                }
                line = read.readLine();
            }
            read.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            return line;
        }
    }

    default  boolean recherche(File file, String mot) {
        boolean trouv = false;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            String line = br.readLine();
            while (line != null) {
                String mot1 = mot.toUpperCase();
                String mot2 = mot.toLowerCase();
                if (line.contains(mot1) || line.contains(mot2)) {
                    trouv = true;
                }
                line = br.readLine();
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return trouv;
    }
}
