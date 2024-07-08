package com.example.esifolio2.InfosPersonnelles;

import java.io.*;

public class FichiersModele {
    public static String filtrer(File fichier , String mot) { //Obtenir le contenu d'un fichier sans la partie mot
        String line="";
        try {
            BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(fichier), "UTF-8"));
            line = read.readLine();
            while (line != null) {
                if (line.contains(mot)){
                    return line.replace(mot,"");
                }
                line = read.readLine();
            }
            read.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            return line.replace(mot,"");
        }
    }
}
