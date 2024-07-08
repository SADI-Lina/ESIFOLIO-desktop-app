package com.example.esifolio2.Projet;

import java.io.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProjetPersonnel extends Projet {
    public ProjetPersonnel(String nom, String type, String descriptif, File fichierProjet, File fichierCompétences,String attestation) {
        super(nom, type, descriptif, fichierProjet, fichierCompétences,attestation);
    }
    public ProjetPersonnel(){
        super();
    }
    public List<String> AfficherCompetences(){
        List<String> liste = new ArrayList<>();
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
        File ProjetPerso=new File("src/");
        try {
            File Dossier2 = new File("src/Projet_Etudiant");
            if (!Dossier2.exists()) {
                Dossier2.mkdir();
            }
            ProjetPerso= new File("src/Projet_Etudiant/" + nom + ".txt");
            if (!ProjetPerso.exists()) {

                ProjetPerso.createNewFile();

            }
            FileWriter writer2 = new FileWriter(ProjetPerso);
            BufferedWriter bw2 = new BufferedWriter(writer2);
            bw2.write("$T/typeProjet/T$");
            bw2.newLine();
            bw2.write("Personnel");
            bw2.newLine();
            bw2.write("$N/nomProjet/N$");
            bw2.newLine();
            bw2.write(nom);
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
        return ProjetPerso;

    }

    public File creerProjet(List<String> Competences){
        File ProjetPerso=new File("src/");
        try {
            File Dossier2 = new File("src/Projet_Etudiant");
            if (!Dossier2.exists()) {
                Dossier2.mkdir();
            }
            ProjetPerso= new File("src/Projet_Etudiant/" + nom + ".txt");
            if (!ProjetPerso.exists()) {

                ProjetPerso.createNewFile();

            }
            FileWriter writer2 = new FileWriter(ProjetPerso);
            BufferedWriter bw2 = new BufferedWriter(writer2);
            bw2.write("$T/typeProjet/T$");
            bw2.newLine();
            bw2.write("Personnel");
            bw2.newLine();
            bw2.write("$N/nomProjet/N$");
            bw2.newLine();
            bw2.write(nom);
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
        return ProjetPerso;

    }

    public File RemFichComp(List<String> comp){

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
