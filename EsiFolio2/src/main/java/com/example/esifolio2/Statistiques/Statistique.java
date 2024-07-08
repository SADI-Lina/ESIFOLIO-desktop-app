package com.example.esifolio2.Statistiques;

import java.io.*;
import java.io.IOException;

public class Statistique {
    private int nbrProjets;
    private int nbrCompétences;

    public void setNbrCompétences(int nbrCompétences) {
        this.nbrCompétences = nbrCompétences;
    }

    public void setNbrProjet(int nbrProjet) {
        this.nbrProjets = nbrProjets;
    }

    public int getNbrCompétences() {
        return nbrCompétences;
    }

    public int nombreCompetences(File fich) { //Retourne le nombre total de compétences
        int nbrComp=0;
        try {
            FileReader fr = new FileReader(fich);
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while(line!=null){
              nbrComp++;
              line = br.readLine();
            }
        } catch (FileNotFoundException ex) {
        }catch (IOException ex) {
            ex.printStackTrace();
        }
        return nbrComp;
    }

    public int NombrProjets (File DossierFich){ //Retourne le nombre total de projets
        int nbrFich =0;
        File [] tabFich = DossierFich.listFiles();
          for (File fich : tabFich) {
                nbrFich++;
            }
          if(nbrFich-2<0){
              nbrFich = 2;
          }
          return nbrFich-2;
    }

    public int[] stat_selon_type(File DossierFich){ //Répartit les projets selon leur type
        int[] tableauRep = new int[3];
        tableauRep[0]=0;
        tableauRep[1]=0;
        tableauRep[2]=0;
        File [] tabFich = DossierFich.listFiles();
        for (File fich : tabFich) {
            try{
                FileReader fr = new FileReader(fich);
                BufferedReader br = new BufferedReader(fr);
                String ligne= br.readLine();
                while((ligne!=null)&&(!ligne.contains("type"))){
                    ligne=br.readLine();
                }
                if (ligne!=null){if (ligne.contains("type")){
                    ligne= br.readLine();
                    if (ligne.contains("Ecole")){
                        tableauRep[0]++;
                    }
                    if (ligne.contains("Club")){
                        tableauRep[1]++;
                    }
                    if (ligne.contains("Personnel")){
                        tableauRep[2]++;
                    }
                }
                }
            }catch (FileNotFoundException ex) {
            }catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return tableauRep;
    }


    public int [] stat_type_Compétences(File FichCmp){ //Répartit les compétences selon leur type
        int[] tableau = new int[2];
        tableau[0]=0;
        tableau[1]=0;
        try {
            FileReader fr = new FileReader(FichCmp);
            BufferedReader br= new BufferedReader(fr);
            String ligne = br.readLine();
            while(ligne!=null){
                if(ligne.contains("$E$")){tableau[0]++;}
                else {tableau[1]++;}
                ligne= br.readLine();
            }

        }catch (FileNotFoundException ex) {
        }catch (IOException ex) {
            ex.printStackTrace();
        }
        return tableau;
    }

}
