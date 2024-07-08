package com.example.esifolio2.Projet;

import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import java.io.IOException;
public abstract class Projet {
    protected String nom;
    protected String type;
    protected String descriptif;
    protected File fichierProjet;
    protected File fichierCompétences;
    protected String attestation;


    public Projet(String nom ,String type,String descriptif,File fichierProjet,File fichierCompétences,String attestation) {
        this.nom=nom;
        this.type=type;
        this.descriptif=descriptif;
        this.fichierProjet=fichierProjet;
        this.fichierCompétences=fichierCompétences;
        this.attestation=attestation;
    }

    public Projet() {

    }

    public void SetFichierComp(File FichCmp){
        this.fichierCompétences=FichCmp;
    }
    public void SetFichierPrjt(File FichPrjt){
        this.fichierProjet=FichPrjt;
    }

    public static boolean VerifNomFich(String nomFichier){
        File fichier = new File("src/Projet_Etudiant/"+nomFichier+".txt");
        if (fichier.exists()){return false;}
        else {return true;}
    }

     public File creerProjet(String [] listCmp){
         File Projet=new File("src/");
         try {
             File Dossier2 = new File("src/Projet_Etudiant");
             if (!Dossier2.exists()) {
                 Dossier2.mkdir();
             }
             Projet = new File("src/Projet_Etudiant/" + nom + ".txt");
             if (!Projet.exists()) {

                 Projet.createNewFile();

             }
             FileWriter writer2 = new FileWriter(Projet);
             BufferedWriter bw2 = new BufferedWriter(writer2);
             bw2.write("$T/typeProjet/T$");
             bw2.newLine();
             bw2.write(type);
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
             while(listCmp[i]!=null) {
                 bw2.newLine();
                 bw2.write(listCmp[i]);
                 i++;
             }

             bw2.close();
         } catch (FileNotFoundException ex) {
         } catch (IOException ex) {
             ex.printStackTrace();
         }
         return Projet;

     }

    public  static void  supprimerProjet(String nomfich) {
        File fich=new File("src/Projet_Etudiant/"+nomfich+".txt");
        if(fich.delete())
        {
            File fichierNom=new File("src/Projet_Etudiant/Fichier_nomsProjets.txt");
            SuppressionInfo(fichierNom,nomfich);
        }
    }
    public static void SuppressionInfo(File FichModif,String InfoChange){
        try {
            FileReader contenuFich = new FileReader(FichModif);
            BufferedReader buffread = new BufferedReader(contenuFich);
            String[] Tableau1= new String[100];
            int i=0;
            int j=0;
            int k=0;
            String Ligne1=buffread.readLine();
            while(Ligne1!=null){
                Tableau1[i]=Ligne1;
                i++;
                if (Ligne1.contains(InfoChange)){
                    k=i;
                    Ligne1=buffread.readLine();
                    while((Ligne1!=null)){
                        Tableau1[i]=Ligne1;
                        i++;
                        Ligne1=buffread.readLine();
                    }
                }
                else{Ligne1=buffread.readLine();}
            }
            buffread.close();
            FileWriter cont = new FileWriter(FichModif);
            BufferedWriter wr =new BufferedWriter(cont);
            for (j=0;j<k-1;j++){
                wr.write(Tableau1[j]);
                wr.newLine();
            }
            j++;
            while (Tableau1[j]!=null){
                wr.write(Tableau1[j]);
                wr.newLine();
                j++;
            }
            wr.close();
        }catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void ModifInfo(String nomFich,String InfoChange,String NewInfo){
        try {
            File FichModif = new File("src/Projet_Etudiant/"+nomFich+".txt");
            FileReader contenuFich = new FileReader(FichModif);
            BufferedReader buffread = new BufferedReader(contenuFich);
            String[] Tableau1 = new String[20];
            int i = 0;
            int j = 0;
            int k = 0;
            String Ligne1 = buffread.readLine();
            if ((InfoChange.contains("nom"))||(InfoChange.contains("descriptif"))||(InfoChange.contains("attestation"))) {
                while (Ligne1 != null) {
                    Tableau1[i] = Ligne1;
                    if (Ligne1.contains(InfoChange+"Projet")) {
                        k = i + 1;
                        i++;
                        Ligne1 = buffread.readLine();
                        while ((Ligne1 != null)) {
                            Tableau1[i] = Ligne1;
                            i++;
                            Ligne1 = buffread.readLine();
                        }
                    } else {
                        Ligne1 = buffread.readLine();
                    }
                    i++;
                }
                buffread.close();
                FileWriter cont = new FileWriter(FichModif);
                BufferedWriter wr = new BufferedWriter(cont);
                while(Tableau1[j]!=null) {
                    if (j==k){j++;
                        wr.write(NewInfo);
                        wr.newLine();
                    }
                    if(Tableau1[j]!=null) {
                        wr.write(Tableau1[j]);
                        wr.newLine();
                        j++;
                    }
                }
            wr.close();
            cont.close();
                if (InfoChange.contains("nom")){
                    FichModif.renameTo(new File("src/Projet_Etudiant/"+NewInfo+".txt"));
                    FichNomModif(nomFich,NewInfo);
                }
        }
        }catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void ajoutInfo(File FichModif,String NewInfo){
        try {
            FileReader contenuFich = new FileReader(FichModif);
            BufferedReader buffread = new BufferedReader(contenuFich);
            String[] Tableau1= new String[200];
            int i=0;
            int j=0;
            int k=0;
            String Ligne1=buffread.readLine();
            while(Ligne1!=null){
                Tableau1[i]=Ligne1;
                i++;
                if (Ligne1.contains("$CA/compétencesAcquisesProjet/CA$")){
                    k=i;
                    Ligne1=buffread.readLine();
                    while((Ligne1!=null)){
                        Tableau1[i]=Ligne1;
                        i++;
                        Ligne1=buffread.readLine();
                    }
                }
                else{Ligne1=buffread.readLine();}
            }
            buffread.close();
            boolean trouv=false;
            int a=0;
            while((Tableau1[a]!=null)&&(trouv==false)){
                if (Tableau1[a].contains(NewInfo)){
                    trouv=true;
                }
                a++;
            }
            FileWriter cont = new FileWriter(FichModif);
            BufferedWriter wr =new BufferedWriter(cont);
            for (j=0;j<k-1;j++){
                if(trouv==true){if(j==a-1){j++;}}
                if (Tableau1[j]!=null){wr.write(Tableau1[j]);
                wr.newLine();}
            }
            wr.write(Tableau1[j]);
            wr.newLine();
            j++;
            wr.write(NewInfo);
            wr.newLine();
            while (Tableau1[j]!=null){
                if(trouv==true){if(j==a-1){j++;}}
                if (Tableau1[j]!=null){wr.write(Tableau1[j]);
                wr.newLine();
                j++;}
            }
            wr.close();
        }catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public File listFichiers(String nomFich) {
        File fichierProjets = new File("src/Projet_Etudiant/Fichier_nomsProjets.txt");
        try {
            if (!fichierProjets.exists()) {
                fichierProjets.createNewFile();
            }
            FileWriter fwrite = new FileWriter(fichierProjets, true);
            BufferedWriter bwrite = new BufferedWriter(fwrite);
            if (type.contains("Ecole")){bwrite.write("$E$"+nomFich);
            bwrite.newLine();}
            if (type.contains("Club")){bwrite.write("$C$"+nomFich);
            bwrite.newLine();}
            if (type.contains("Personnel")){bwrite.write("$P$"+nomFich);
                bwrite.newLine();}
            bwrite.close();
        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return fichierProjets;
    }

    public static void FichNomModif(String ancienNom,String nvNom){
        try{
            File fichier=new File("src/Projet_Etudiant/Fichier_nomsProjets.txt");
        FileReader fread=new FileReader(fichier);
        BufferedReader bread=new BufferedReader(fread);
        String [] tabNom = new String [60];
        int i=0;
        String type="";
        int nbrLigne=0;
        String ligne = bread.readLine();
        while(ligne!=null){
            tabNom[i]=ligne;
            if (ligne.contains("$E$"+ancienNom)){
                type="$E$";
                nbrLigne=i;

            }
            if (ligne.contains("$C$"+ancienNom)){
                type="$C$";
                nbrLigne=i;
            }
            if (ligne.contains("$P$"+ancienNom)){
                type="$P$";
                nbrLigne=i;
            }
            i++;
            ligne=bread.readLine();
        }
        bread.close();
        FileWriter fwrite=new FileWriter(fichier);
        BufferedWriter bwrite=new BufferedWriter(fwrite);
        int j=0;
        while(tabNom[j]!=null){
            if (j == nbrLigne) {
                j++;
            }
            if (tabNom[j]!=null) {
                bwrite.write(tabNom[j]);
                bwrite.newLine();
                j++;
            }

        }
        bwrite.write(type+nvNom);
        bwrite.newLine();
        bwrite.close();
        }catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getDescriptif(String nomProjet) throws IOException {
        File fichier = Paths.get("src/Projet_Etudiant/" + nomProjet + ".txt").toFile();
        FileReader cont = new FileReader(fichier);
        BufferedReader bw = new BufferedReader(cont);
        String descriptif = bw.readLine();
        while(!descriptif.equals("$D/descriptifProjet/D$")){
            descriptif= bw.readLine();
        }
        descriptif= bw.readLine();
        String newline = bw.readLine();
        while(!newline.contains("$")){
            descriptif = descriptif + "\n" + newline;
            newline= bw.readLine();
        }

        bw.close();
        return descriptif;
    }

    public static String getAttestation(String nomProjet) throws IOException {
        File fichier = Paths.get("src/Projet_Etudiant/" + nomProjet + ".txt").toFile();
        FileReader cont = new FileReader(fichier);
        BufferedReader bw = new BufferedReader(cont);
        String attestation = bw.readLine();
        while(!attestation.equals("$A/attestationProjet/A$")){
            attestation= bw.readLine();
        }
        attestation= bw.readLine();
        bw.close();
        if(attestation.equals("$$")){
            attestation=null;
        }
        return attestation;
    }

    public static List<String> getCompetence(String nomProjet) throws IOException {
        File fichier = Paths.get("src/Projet_Etudiant/" + nomProjet + ".txt").toFile();
        FileReader cont = new FileReader(fichier);
        BufferedReader bw = new BufferedReader(cont);
        String competence1 = bw.readLine();
        while(!competence1.equals("$CA/compétencesAcquisesProjet/CA$")){
            competence1= bw.readLine();
        }
        competence1= bw.readLine();
        List<String> list = new ArrayList<>();
        if(!competence1.equals("$A/attestationProjet/A$")) {
            list.add(competence1);
            competence1 = bw.readLine();
            while (!competence1.equals("$A/attestationProjet/A$")) {
                list.add(competence1);
                competence1 = bw.readLine();
            }
        }
        bw.close();
        return list;
    }

    public static String getClub(String nomProjet) throws IOException {
        File fichier = Paths.get("src/Projet_Etudiant/" + nomProjet + ".txt").toFile();
        FileReader cont = new FileReader(fichier);
        BufferedReader bw = new BufferedReader(cont);
        String club = bw.readLine();
        while(!club.equals("$C/clubProjet/C$")){
            club= bw.readLine();
        }
        club= bw.readLine();
        bw.close();
        return club;
    }

    public static String getModule(String nomProjet) throws IOException {
        File fichier = Paths.get("src/Projet_Etudiant/" + nomProjet + ".txt").toFile();
        FileReader cont = new FileReader(fichier);
        BufferedReader bw = new BufferedReader(cont);
        String module = bw.readLine();
        while(!module.equals("$M/moduleProjet/M$")){
            module= bw.readLine();
        }
        module= bw.readLine();
        bw.close();
        return module;
    }

    public static File RemFichComp1(List<String> comp){

        File Dossier = new File("src/Statistiques");
        if (!Dossier.exists()) {
            Dossier.mkdir();
        }
        File competences_ac = new File("src/Statistiques/Compétences_aquise-types.txt");
        try{
            if (!competences_ac.exists()) {

                competences_ac.createNewFile();
            }

            if (competences_ac.exists()) {
                boolean existe =false;
                FileWriter writer1 = new FileWriter(competences_ac, true);
                BufferedWriter bw1 = new BufferedWriter(writer1);
                for (String element : comp) {
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
    public List<String> TabComp1(List <String> tableau) {
        int i;
        for(i=0;i<tableau.size();i++){
            tableau.set(i,"$"+this.type.substring(0,1)+"$"+tableau.get(i));
        }
        return tableau;
    }

    public static boolean verifierChemin(String chemin){
        return Paths.get(chemin).isAbsolute();
    }

    abstract public List<String> AfficherCompetences();

}
