package com.example.esifolio2;

import com.example.esifolio2.InfosPersonnelles.GestionDeFichiersModele;

import java.io.*;

public class EtatAppli implements GestionDeFichiersModele {

    private static String motDePasse;
    private static boolean premiereConnexion;
    private static String lienRepertoire="";

    public static void setLienRepertoire(String lienRepertoire) {
        EtatAppli.lienRepertoire = lienRepertoire;
    }

    public static String getLienRepertoire() {
        return lienRepertoire;
    }

    public static void setMotDePasse(String motDePasse) {
        EtatAppli.motDePasse = motDePasse;
    }

    public static void setPremiereConnexion(boolean premiereConnexion) {
        EtatAppli.premiereConnexion = premiereConnexion;
    }

    public File insertMdp (){
        this.creer_D();
        File file = this.creer_f("MotDePasse.folio");
        try {
            FileWriter writer = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write("MOT DE PASSE " + motDePasse);
            bw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public File insertPremiereCo (){
        this.creer_D();
        File file = this.creer_f("PremiereConnexion.folio");
        try {
            FileWriter writer = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write("PREMIERE CONNEXION " + String.valueOf(premiereConnexion));
            bw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public File insertLienRepertoire (){
        this.creer_D();
        File file = this.creer_f("LienRepertoire.folio");
        try {
            FileWriter writer = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write("LIEN REPERTOIRE " + String.valueOf(lienRepertoire));
            bw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

}
