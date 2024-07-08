package com.example.esifolio2;

import com.example.esifolio2.InfosPersonnelles.GestionDeFichiersModele;

import java.io.*;

public class Etudiant implements GestionDeFichiersModele {

    private String nom;
    private String prenoms;
    private String dateNaissance;
    private String matricule;
    private String adresseMail;
    private String adresse;
    private String numTel;

    public Etudiant(){

    }

    public Etudiant(String nom, String prenoms, String dateNaissance, String matricule, String adresseMail, String adresse, String numTel) {
        this.nom = nom;
        this.prenoms = prenoms;
        this.dateNaissance = dateNaissance;
        this.matricule = matricule;
        this.adresseMail = adresseMail;
        this.adresse = adresse;
        this.numTel = numTel;
    }

    public Etudiant(String nom, String prenoms, String dateNaissance, String matricule, String adresseMail) {
        this.nom = nom;
        this.prenoms = prenoms;
        this.dateNaissance = dateNaissance;
        this.matricule = matricule;
        this.adresseMail = adresseMail;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenoms(String prenoms) {
        this.prenoms = prenoms;
    }

    public void setDateNaissance(String dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public void setAdresseMail(String adresseMail) {
        this.adresseMail = adresseMail;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setNumTel(String numTel) {
        this.numTel = numTel;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenoms() {
        return prenoms;
    }

    public String getDateNaissance() {
        return dateNaissance;
    }

    public String getMatricule() {
        return matricule;
    }

    public String getAdresseMail() {
        return adresseMail;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getNumTel() {
        return numTel;
    }

    public File insert (){
        //Etudiant infoPerso = new Etudiant();
        this.creer_D();
        File file = this.creer_f("InformationsPersonnelles.folio");
        //Scanner scanner = new Scanner(System.in);
        try {
            FileWriter writer = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(writer);
            //String nom = scanner.next();
            bw.write("NOM " + nom );
            bw.newLine();
            //String prenom = scanner.next();
            bw.write("PRENOMS " + prenoms);
            bw.newLine();
            //String dateDeNaissance = scanner.next();
            bw.write("DATE DE NAISSANCE " + dateNaissance);
            bw.newLine();
            //String matricule = scanner.next();
            bw.write("MATRICULE " + matricule);
            bw.newLine();
            //String mail = scanner.next();
            bw.write("MAIL " + adresseMail);
            bw.newLine();
            //String adress = scanner.next();
            bw.write("ADRESSE " + adresse);
            bw.newLine();
            bw.write("NUMERO DE TELEPHONE " + numTel);
            bw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public void modifier (File file , String toUpdate, String updated) {
        try {

            BufferedReader br = new BufferedReader(new FileReader(file));
            PrintWriter writer = new PrintWriter(new File(file + ".out"), "UTF-8");
            String line;

            while ((line = br.readLine()) != null) {
                line = line.replace(toUpdate, updated);
                writer.println(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
