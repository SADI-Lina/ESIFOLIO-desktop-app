package com.example.esifolio2.Projet;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.util.Stack;

public class ProjetEcole extends Projet {
    private String module;
    private String anneeEtude;

    public ProjetEcole(){
        super();
    }

    public ProjetEcole(String nom, String type, String descriptif, File fichierProjet, File fichierCompétences, String attestation,String module, String anneeEtude) {
        super(nom, type, descriptif, fichierProjet, fichierCompétences,attestation);
        this.module = module;
        this.anneeEtude = anneeEtude;
    }
    public void setModule(String mdl){this.module=mdl.toUpperCase();}

    public void setAnnee(String annee){
        this.anneeEtude=annee;
    }

    public List<String> AfficherModules() {
        File Mdl = new File("src/CompetenceEcole/Modules.txt");
        List<String> liste = new ArrayList<>();
        try {
            File Fichier_Module = Mdl;
            FileReader cont1 = new FileReader(Fichier_Module);
            BufferedReader lect = new BufferedReader(cont1);
            String ligne;
            boolean trouv = false;
            while (((ligne = lect.readLine()) != null) && (trouv == false)) {
                if (ligne.contains("$" + anneeEtude + "$")) {
                    ligne = lect.readLine();
                    while(!ligne.contains("$")){
                        liste.add(ligne);
                        trouv = true;
                        ligne = lect.readLine();
                    }

                }
            }
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    return liste;
    }


    public List <String> AfficherCompetences() {
        List <String> liste = new ArrayList<>();
        File Annee1 = new File("src/CompetenceEcole/1CP.txt");
        File Annee2 = new File("src/CompetenceEcole/2CP.txt");
        File Annee3 = new File("src/CompetenceEcole/1CS.txt");
        File Annee41 = new File("src/CompetenceEcole/2CSSD.txt");
        File Annee42 = new File("src/CompetenceEcole/2CSSL.txt");
        File Annee43 = new File("src/CompetenceEcole/2CSST.txt");
        File Annee44 = new File("src/CompetenceEcole/2CSSQ.txt");
        File Annee5 = new File("src/CompetenceEcole/3CS.txt");
        File Annee6 = new File("src/CompetenceEcole/Master.txt");
        File fich = Annee1;
        boolean trouve = false;
        try {
            if (anneeEtude.contains("1CP")) {
                fich = Annee1;
            }
            else if (anneeEtude.contains("2CP")) {
                fich = Annee2;
            }
            else if (anneeEtude.contains("1CS")) {
                fich = Annee3;
            }
            else if (anneeEtude.contains("2CSSD")) {
                fich = Annee41;
            }
            else if (anneeEtude.contains("2CSSL")) {
                fich = Annee42;
            }
            else if (anneeEtude.contains("2CSST")) {
                fich = Annee43;
            }
            else if (anneeEtude.contains("2CSSQ")) {
                fich = Annee44;
            }
            else if (anneeEtude.contains("3CS")) {
                fich = Annee5;
            }
            else if (anneeEtude.contains("Master")) {
                fich = Annee6;
            }
            else{
                return new ArrayList<>();
            }
            FileReader cont2 = new FileReader(fich);
            BufferedReader lect2 = new BufferedReader(cont2);
            String line;
            while (((line = lect2.readLine()) != null) && (trouve == false)) {
                if (line.contains("$" + module + "$")) {
                    trouve = true;
                    line = lect2.readLine();
                    if (line.contains("$")) {
                    }
                    else {
                        while ((line != null) && (!line.contains("$"))) {
                            liste.add(line);
                            line = lect2.readLine();
                        }
                    }
                }
            }
            lect2.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return liste;
    }

    public File creerProjet(String [] Competence) {

        File ProjetEcl=new File("src/");
        try {
            File Dossier2 = new File("src/Projet_Etudiant");
            if (!Dossier2.exists()) {
                Dossier2.mkdir();
            }
            ProjetEcl = new File("src/Projet_Etudiant/" + nom + ".txt");
            if (!ProjetEcl.exists()) {

                ProjetEcl.createNewFile();

            }
            FileWriter writer2 = new FileWriter(ProjetEcl);
            BufferedWriter bw2 = new BufferedWriter(writer2);
            bw2.write("$T/typeProjet/T$");
            bw2.newLine();
            bw2.write("Ecole");
            bw2.newLine();
            bw2.write("$N/nomProjet/N$");
            bw2.newLine();
            bw2.write(nom);
            bw2.newLine();
            bw2.write("$M/moduleProjet/M$");
            bw2.newLine();
            bw2.write(module);
            bw2.newLine();
            bw2.write("$D/descriptifProjet/D$");
            bw2.newLine();
            bw2.write(descriptif);
            bw2.newLine();
            bw2.write("$CA/compétencesAcquisesProjet/CA$");
            int i=0;
            while(Competence[i]!=null) {
                bw2.newLine();
                bw2.write(Competence[i]);
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
        return ProjetEcl;

    }


    public static String getAnnee(String module) throws IOException {
        File file = new File("src/CompetenceEcole/Modules.txt");
        FileReader cont = new FileReader(file);
        BufferedReader lect = new BufferedReader(cont);
        boolean trouv = false;
        String line=lect.readLine();
        Stack <String> pile = new Stack<>();
        while(line!=null && !trouv){
            if(line.equals(module)){
                trouv=true;
            }
            pile.push(line);
            line = lect.readLine();
        }
        lect.close();
        trouv = false;
        String annee = null;
        while (!pile.empty() && !trouv ){
            annee=pile.peek();
            if(annee.contains("$")){
                trouv=true;
            }
            pile.pop();
        }
        return annee;
    }

    public File creerProjet(ArrayList<String> listCmp) {

        File ProjetEcl=new File("src/");
        try {
            File Dossier2 = new File("src/Projet_Etudiant");
            if (!Dossier2.exists()) {
                Dossier2.mkdir();
            }
            ProjetEcl = new File("src/Projet_Etudiant/" + nom + ".txt");
            if (!ProjetEcl.exists()) {

                ProjetEcl.createNewFile();

            }
            FileWriter writer2 = new FileWriter(ProjetEcl);
            BufferedWriter bw2 = new BufferedWriter(writer2);
            bw2.write("$T/typeProjet/T$");
            bw2.newLine();
            bw2.write("Ecole");
            bw2.newLine();
            bw2.write("$N/nomProjet/N$");
            bw2.newLine();
            bw2.write(nom);
            bw2.newLine();
            bw2.write("$M/moduleProjet/M$");
            bw2.newLine();
            bw2.write(module);
            bw2.newLine();
            bw2.write("$D/descriptifProjet/D$");
            bw2.newLine();
            bw2.write(descriptif);
            bw2.newLine();
            bw2.write("$CA/compétencesAcquisesProjet/CA$");
            for(String element : listCmp){
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
        return ProjetEcl;

    }

    public static File RemFichComp(ArrayList<String> comp){

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

