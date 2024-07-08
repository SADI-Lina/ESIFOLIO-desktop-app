package com.example.esifolio2.Projet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;

public class ProjetClub extends Projet {
    private String club;

    public ProjetClub(){
        super();
    }
    public ProjetClub(String nom, String type, String descriptif, File fichierProjet, File fichierCompétences, String attestation,String club) {
        super(nom, type, descriptif, fichierProjet, fichierCompétences,attestation);
        this.club= club;
    }
    public void SetClub(String clb){this.club=clb.toUpperCase();}

    public static List<String> AffichageClub() {
        List<String> liste = new ArrayList<>();
        try {
            File Club = new File("src/Club.txt");
            FileReader cont = new FileReader(Club);
            BufferedReader bw = new BufferedReader(cont);
            String lgn = bw.readLine();
            while (lgn != null) {
                liste.add(lgn);
                lgn = bw.readLine();
            }
        }catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return liste;
    }

    public static List<String> AffichageCompetences(){
        List<String> liste =new ArrayList<>();
        try{
            File CompetenceCA=new File("src/AutreCompetence.txt");
            File fich2=CompetenceCA;
            if (fich2.exists()) {
                FileReader cont3 = new FileReader(fich2);
                BufferedReader lect3 = new BufferedReader(cont3);
                String line1;
                boolean trouve = false;
                while (((line1 = lect3.readLine()) != null) && (trouve == false)) {
                    liste.add(line1);
                }
                lect3.close();
            }
        }catch (FileNotFoundException e) {

        } catch (IOException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public List<String> AfficherCompetences(){
        List<String> liste =new ArrayList<>();
        try{
        File CompetenceCA=new File("src/AutreCompetence.txt");
        File fich2=CompetenceCA;
        if (fich2.exists()) {
            FileReader cont3 = new FileReader(fich2);
            BufferedReader lect3 = new BufferedReader(cont3);
            String line1;
            boolean trouve = false;
            while (((line1 = lect3.readLine()) != null) && (trouve == false)) {
                liste.add(line1);
            }
            lect3.close();
        }
        }catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        return liste;
    }

    public File creerProjet(String [] Competences){
        File ProjetClb=new File("src/");
        try {
            File Dossier2 = new File("src/Projet_Etudiant");
            if (!Dossier2.exists()) {
                Dossier2.mkdir();
            }
            ProjetClb= new File("src/Projet_Etudiant/" + nom + ".txt");
            if (!ProjetClb.exists()) {

                ProjetClb.createNewFile();

            }
            FileWriter writer2 = new FileWriter(ProjetClb);
            BufferedWriter bw2 = new BufferedWriter(writer2);
            bw2.write("$T/typeProjet/T$");
            bw2.newLine();
            bw2.write("Club");
            bw2.newLine();
            bw2.write("$N/nomProjet/N$");
            bw2.newLine();
            bw2.write(nom);
            bw2.newLine();
            bw2.write("$C/clubProjet/C$");
            bw2.newLine();
            bw2.write(club);
            bw2.newLine();
            bw2.write("$D/descriptifProjet/D$");
            bw2.newLine();
            bw2.write(descriptif);
            bw2.newLine();
            bw2.write("$CA/compétencesAcquisesProjet/CA$");
            int i=0;
            while(Competences[i]!=null) {
                bw2.newLine();
                bw2.write(Competences[i]);
                i++;
            }
            bw2.newLine();
            bw2.write("$A/attestationProjet/A$");
            bw2.newLine();
            if (attestation!=null){bw2.write(attestation);}
            else{bw2.write("$$");}


            bw2.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ProjetClb;

    }

    public File creerProjet(List<String> Competences){
        File ProjetClb=new File("src/");
        try {
            File Dossier2 = new File("src/Projet_Etudiant");
            if (!Dossier2.exists()) {
                Dossier2.mkdir();
            }
            ProjetClb= new File("src/Projet_Etudiant/" + nom + ".txt");
            if (!ProjetClb.exists()) {

                ProjetClb.createNewFile();

            }
            FileWriter writer2 = new FileWriter(ProjetClb);
            BufferedWriter bw2 = new BufferedWriter(writer2);
            bw2.write("$T/typeProjet/T$");
            bw2.newLine();
            bw2.write("Club");
            bw2.newLine();
            bw2.write("$N/nomProjet/N$");
            bw2.newLine();
            bw2.write(nom);
            bw2.newLine();
            bw2.write("$C/clubProjet/C$");
            bw2.newLine();
            bw2.write(club);
            bw2.newLine();
            bw2.write("$D/descriptifProjet/D$");
            bw2.newLine();
            bw2.write(descriptif);
            bw2.newLine();
            bw2.write("$CA/compétencesAcquisesProjet/CA$");
            for(String element : Competences){
                bw2.newLine();
                bw2.write(element);
            }
            bw2.newLine();
            bw2.write("$A/attestationProjet/A$");
            bw2.newLine();
            if (attestation!=null){bw2.write(attestation);}
            else{bw2.write("$$");}


            bw2.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return ProjetClb;

    }

    public static File RemFichComp(List<String> comp){

        File Dossier = new File("src/Projet_Etudiant");
        if (!Dossier.exists()) {
            Dossier.mkdir();
        }
        File competences_ac = new File("src/Projet_Etudiant/Compétences_aquise.txt");
        try{
            if (!competences_ac.exists()) {

                competences_ac.createNewFile();
            }

            if (competences_ac.exists()) {
                boolean existe =false;
                FileWriter writer1 = new FileWriter(competences_ac, true);
                BufferedWriter bw1 = new BufferedWriter(writer1);
                for(String element : comp){
                    FileReader cont = new FileReader(competences_ac);
                    BufferedReader br1 = new BufferedReader(cont);
                    String LectLigne = br1.readLine();
                    while ((LectLigne != null)&&(existe==false)&&(element!=null)) {
                        if (LectLigne.contains(element)) {
                            existe=true;
                        }
                        LectLigne= br1.readLine();
                    }
                    if ((existe == false)&&(element!=null)) {
                        bw1.write(element);
                        bw1.newLine();
                    }
                    existe=false;
                    br1.close();
                }
                bw1.close();
            }
        }catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return competences_ac;

    }
    }



