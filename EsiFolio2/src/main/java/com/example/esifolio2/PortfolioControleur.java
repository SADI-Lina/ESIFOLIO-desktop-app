package com.example.esifolio2;

import com.example.esifolio2.InfosPersonnelles.GestionDeFichiersModele;
import com.example.esifolio2.Portfolio.Portfolio;
import com.example.esifolio2.Securite.Securite;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PortfolioControleur extends InteractionsControleur implements GestionDeFichiersModele {
    @FXML private Button boutonPDF1;
    @FXML private Button boutonHTML1;
    @FXML private Button boutonPDF2;
    @FXML private Button boutonHTML2;
    @FXML public void PDF1Clicked(ActionEvent actionEvent) {
        /** Télécharger modèle 1 sous format PDF **/
        Genererportfolio cl=new Genererportfolio();
        File fichNoms = new File("src/Projet_Etudiant/Fichier_nomsProjets.txt");
        String [] tab= new String [200];
        try {
            //recuperer le nom des projet à partir de Fichier_nomsProjets et les mettres dans un tableau
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fichNoms), "Cp1252"));
            String ligne=br.readLine();
            int j=0;
            while(ligne!=null){
                tab[j]=ligne.substring(3,ligne.length());
                ligne= br.readLine();
                j++;
            }
            //crée l'html dans le repertoire dossieretudiant qui se trouve dans l'app
            String nomP = cl.genererPortfolio(tab);
            //crée le pdf dans le repertoire dossieretudiant qui se trouve dans l'app
            cl.convert("src/dossieretudiant/HTML/"+nomP+".html" ,nomP);

            String choix = "PDF";
            // recuperer le repertoire saisie par l'etudiant
            String chaine  = new String(Files.readAllBytes(Paths.get("src/ESIfolioapp/LienRepertoire.folio")));
            String dirPath = chaine.substring(16);
            String chaine1  = new String(Files.readAllBytes(Paths.get("src/ESIfolioapp/MotDePasse.folio")));
            String password = chaine1.substring(13);
            File src = new File("src/dossieretudiant/"+choix+"/");
            File dest = new File(dirPath+"/ESIFOLIO-Portfolio");
            File Zip = new File(dirPath+"/ESIFOLIO-Portfolio.zip");
            ;
            dirPath = dirPath+"/ESIFOLIO-Portfolio";
            if(!dest.exists()){
                if(!Zip.exists()){//si le répertoire et le zip n'existent pas (la premiere génération), créez-le
                    dest.mkdir();
                    File src2 = new File("src/dossieretudiant/HTML/Fonts");
                    File src3 = new File("src/Fonts");
                    if (!src2.exists()){
                        cl.CopyResssources(src3,src2);
                    }
                    File dest2 = new File(dirPath+"/Fonts");
                    if (dest2.exists()) {Securite.deleteDocier(dest2);}
                    cl.CopyResssources(src2,dest2);
                    if (src2.exists()){
                        Securite.deleteDocier(src2);}
                    cl.CopyResssources(src,dest);
                    BufferedWriter rep = new BufferedWriter(new FileWriter(new File("src/repertoirefichier.txt")));
                    rep.write(dest.getPath()+"\\"+nomP+"."+choix.toLowerCase());
                    rep.newLine();
                    rep.close();
                }else{
                    BufferedWriter rep = new BufferedWriter(new FileWriter(new File("src/repertoirefichier.txt"),true));
                    //on dezip le dossier pour que peut rajouter les autres portfolio (plusieurs generation)
                    Securite.unZipPasswordProtectedFiles(Zip.getPath(),password);
                    cl.CopyResssources(src,dest);
                    rep.write(dest.getPath()+"\\"+nomP+"."+choix.toLowerCase());
                    rep.newLine();
                    rep.close();
                }
            }else{
                BufferedWriter rep = new BufferedWriter(new FileWriter(new File("src/repertoirefichier.txt"),true));
                if(Zip.exists()){
                    Securite.deleteDocier(Zip);
                    //lister le contenu du répertoire
                    cl.CopyResssources(src,dest);
                    rep.write(dest.getPath()+"\\"+nomP+"."+choix.toLowerCase());
                    rep.newLine();
                }
                else{
                    cl.CopyResssources(src,dest);
                    rep.write(dest.getPath()+"\\"+nomP+"."+choix.toLowerCase());
                    rep.newLine();
                }
                rep.close();
            }


            File fichAsupp = new File ("src/dossieretudiant/HTML/"+nomP+".html");
            if(fichAsupp.delete()){
            };
            File fichAsp2 = new File("src/dossieretudiant/PDF/"+nomP+".pdf");
            if(fichAsp2.delete()){
            }
            Securite.compressDirWithPwd(dirPath);
            File dos = new File(dirPath);
            Securite.deleteDocier(dos);
            /** Passer vers la fenêtre succès **/
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("PortfolioCreeVue.fxml"));
            Parent root = fxmlLoader.load();
            PortfolioCreeControleur controleur = fxmlLoader.getController();
            controleur.setNomPrenoms(info);
            controleur.setChemin(1,"PDF");
            Scene scene = new Scene(root,900.0,600.0);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            window.centerOnScreen();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML public void PDF1MouseExited() {
        boutonPDF1.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 8");
    }

    @FXML public void PDF1Pressed() {
        boutonPDF1.setStyle("-fx-background-color: #582B94; -fx-background-radius: 8");
    }

    @FXML public void PDF1Survol() {
        boutonPDF1.setStyle("-fx-background-color: #804BC6; -fx-background-radius: 8");
    }

    @FXML public void HTML1Clicked(ActionEvent actionEvent) {
        /** Télécharger modèle 1 sous format HTML **/
        Genererportfolio cl=new Genererportfolio();
        File fichNoms = new File("src/Projet_Etudiant/Fichier_nomsProjets.txt");
        String [] tab= new String [200];
        try {
            //recuperer le nom des projet à partir de Fichier_nomsProjets et les mettres dans un tableau
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fichNoms), "Cp1252"));
            String ligne=br.readLine();
            int j=0;
            while(ligne!=null){
                tab[j]=ligne.substring(3,ligne.length());
                ligne= br.readLine();
                j++;
            }
            //crée l'html dans le repertoire dossieretudiant qui se trouve dans l'app
            String nomP = cl.genererPortfolio(tab);
            //crée le pdf dans le repertoire dossieretudiant qui se trouve dans l'app
            cl.convert("src/dossieretudiant/HTML/"+nomP+".html" ,nomP);

            String choix = "HTML";
            // recuperer le repertoire saisie par l'etudiant
            String chaine  = new String(Files.readAllBytes(Paths.get("src/ESIfolioapp/LienRepertoire.folio")));
            String dirPath = chaine.substring(16);
            String chaine1  = new String(Files.readAllBytes(Paths.get("src/ESIfolioapp/MotDePasse.folio")));
            String password = chaine1.substring(13);
            File src = new File("src/dossieretudiant/"+choix+"/");
            File dest = new File(dirPath+"/ESIFOLIO-Portfolio");
            File Zip = new File(dirPath+"/ESIFOLIO-Portfolio.zip");
            ;
            dirPath = dirPath+"/ESIFOLIO-Portfolio";
            if(!dest.exists()){
                if(!Zip.exists()){//si le répertoire et le zip n'existent pas (la premiere génération), créez-le
                    dest.mkdir();
                    File src2 = new File("src/dossieretudiant/HTML/Fonts");
                    File src3 = new File("src/Fonts");
                    if (!src2.exists()){
                        cl.CopyResssources(src3,src2);
                    }
                    File dest2 = new File(dirPath+"/Fonts");
                    if (dest2.exists()) {Securite.deleteDocier(dest2);}
                    cl.CopyResssources(src2,dest2);
                    if (src2.exists()){
                        Securite.deleteDocier(src2);}
                    cl.CopyResssources(src,dest);
                    BufferedWriter rep = new BufferedWriter(new FileWriter(new File("src/repertoirefichier.txt")));
                    rep.write(dest.getPath()+"\\"+nomP+"."+choix.toLowerCase());
                    rep.newLine();
                    rep.close();
                }else{
                    BufferedWriter rep = new BufferedWriter(new FileWriter(new File("src/repertoirefichier.txt"),true));
                    //on dezip le dossier pour que peut rajouter les autres portfolio (plusieurs generation)
                    Securite.unZipPasswordProtectedFiles(Zip.getPath(),password);
                    cl.CopyResssources(src,dest);
                    rep.write(dest.getPath()+"\\"+nomP+"."+choix.toLowerCase());
                    rep.newLine();
                    rep.close();
                }
            }else{
                BufferedWriter rep = new BufferedWriter(new FileWriter(new File("src/repertoirefichier.txt"),true));
                if(Zip.exists()){
                    Securite.deleteDocier(Zip);
                    //lister le contenu du répertoire
                    cl.CopyResssources(src,dest);
                    rep.write(dest.getPath()+"\\"+nomP+"."+choix.toLowerCase());
                    rep.newLine();
                }
                else{
                    cl.CopyResssources(src,dest);
                    rep.write(dest.getPath()+"\\"+nomP+"."+choix.toLowerCase());
                    rep.newLine();
                }
                rep.close();
            }


            File fichAsupp = new File ("src/dossieretudiant/HTML/"+nomP+".html");
            if(fichAsupp.delete()){
            };
            File fichAsp2 = new File("src/dossieretudiant/PDF/"+nomP+".pdf");
            if(fichAsp2.delete()){
            }
            Securite.compressDirWithPwd(dirPath);
            File dos = new File(dirPath);
            Securite.deleteDocier(dos);
            /** Passer vers la fenêtre succès **/
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("PortfolioCreeVue.fxml"));
            Parent root = fxmlLoader.load();
            PortfolioCreeControleur controleur = fxmlLoader.getController();
            controleur.setNomPrenoms(info);
            controleur.setChemin(1,"HTML");
            Scene scene = new Scene(root,900.0,600.0);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            window.centerOnScreen();
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML public void HTML1Survol() {
        boutonHTML1.setStyle("-fx-background-color: #804BC6; -fx-background-radius: 8");
    }

    @FXML public void HTML1MouseExited() {
        boutonHTML1.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 8");
    }

    @FXML public void HTML1Pressed() {
        boutonHTML1.setStyle("-fx-background-color: #582B94; -fx-background-radius: 8");
    }

    @FXML public void PDF2Pressed() {
        boutonPDF2.setStyle("-fx-background-color: #582B94; -fx-background-radius: 8");
    }

    @FXML public void PDF2Clicked(ActionEvent actionEvent) throws IOException {
        /** Télécharger modèle 2 sous format PDF **/
        Portfolio cl=new Portfolio();
        try {
            //crée l'html dans le repertoire dossieretudiant qui se trouve dans l'app
            String nomP = cl.GenererPortfolio();
            //crée le pdf dans le repertoire dossieretudiant qui se trouve dans l'app
            cl.convert("src/dossieretudiant/HTML/"+nomP+".html" ,nomP);

            String choix = "PDF";
            // recuperer le repertoire saisie par l'etudiant
            String chaine  = new String(Files.readAllBytes(Paths.get("src/ESIfolioapp/LienRepertoire.folio")));
            String dirPath = chaine.substring(16);
            String chaine1  = new String(Files.readAllBytes(Paths.get("src/ESIfolioapp/MotDePasse.folio")));
            String password = chaine1.substring(13);
            File src = new File("src/dossieretudiant/"+choix+"/");
            File dest = new File(dirPath+"\\ESIFOLIO-Portfolio");
            File Zip = new File(dirPath+"/ESIFOLIO-Portfolio.zip");

            dirPath = dirPath+"\\ESIFOLIO-Portfolio";
            if(!dest.exists()){
                if(!Zip.exists()){//si le répertoire et le zip n'existent pas (la premiere génération), créez-le
                    dest.mkdir();
                    File src2 = new File("src/dossieretudiant/HTML/images");
                    File src3 = new File("src/images");
                    if (!src2.exists()){
                        cl.CopyResssources(src3,src2);
                    }
                    File dest2 = new File(dirPath+"/images");
                    if (dest2.exists()) {Securite.deleteDocier(dest2);}
                    cl.CopyResssources(src2,dest2);
                    if (src2.exists()){
                        Securite.deleteDocier(src2);}
                    cl.CopyResssources(src,dest);
                    BufferedWriter rep = new BufferedWriter(new FileWriter(new File("src/repertoirefichier.txt")));
                    rep.write(dest.getPath()+"\\"+nomP+"."+choix.toLowerCase());
                    rep.newLine();
                    rep.close();
                }else{
                    BufferedWriter rep = new BufferedWriter(new FileWriter(new File("src/repertoirefichier.txt"),true));
                    //on dezip le dossier pour que peut rajouter les autres portfolio (plusieurs generation)
                    Securite.unZipPasswordProtectedFiles(Zip.getPath(),password);
                    cl.CopyResssources(src,dest);
                    rep.write(dest.getPath()+"\\"+nomP+"."+choix.toLowerCase());
                    rep.newLine();
                    rep.close();
                }
            }else{
                BufferedWriter rep = new BufferedWriter(new FileWriter(new File("src/repertoirefichier.txt"),true));
                if(Zip.exists()){
                    Securite.deleteDocier(Zip);
                    //lister le contenu du répertoire
                    cl.CopyResssources(src,dest);
                    rep.write(dest.getPath()+"\\"+nomP+"."+choix.toLowerCase());
                    rep.newLine();
                }
                else{
                    cl.CopyResssources(src,dest);
                    rep.write(dest.getPath()+"\\"+nomP+"."+choix.toLowerCase());
                    rep.newLine();
                }
                rep.close();
            }


            File fichAsupp = new File ("src/dossieretudiant/HTML/"+nomP+".html");
            if(fichAsupp.delete()){
            }
            File fichAsp2 = new File("src/dossieretudiant/PDF/"+nomP+".pdf");
            if(fichAsp2.delete()){
            }
            Securite.compressDirWithPwd(dirPath);
            File dos = new File(dirPath);
            Securite.deleteDocier(dos);
            /** Passer vers la fenêtre succès **/
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("PortfolioCreeVue.fxml"));
            Parent root = fxmlLoader.load();
            PortfolioCreeControleur controleur = fxmlLoader.getController();
            controleur.setNomPrenoms(info);
            controleur.setChemin(2,"PDF");
            Scene scene = new Scene(root,900.0,600.0);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            window.centerOnScreen();
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML public void PDF2MouseExited() {
        boutonPDF2.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 8");
    }

    @FXML public void PDF2Survol() {
        boutonPDF2.setStyle("-fx-background-color: #804BC6; -fx-background-radius: 8");
    }

    @FXML public void HTML2Clicked(ActionEvent actionEvent) throws IOException {
        /** Télécharger modèle 2 sous format HTML **/
        Portfolio cl=new Portfolio();
        try {
            //crée l'html dans le repertoire dossieretudiant qui se trouve dans l'app
            String nomP = cl.GenererPortfolio();
            //crée le pdf dans le repertoire dossieretudiant qui se trouve dans l'app
            cl.convert("src/dossieretudiant/HTML/"+nomP+".html" ,nomP);

            String choix = "HTML";
            // recuperer le repertoire saisie par l'etudiant
            String chaine  = new String(Files.readAllBytes(Paths.get("src/ESIfolioapp/LienRepertoire.folio")));
            String dirPath = chaine.substring(16);
            String chaine1  = new String(Files.readAllBytes(Paths.get("src/ESIfolioapp/MotDePasse.folio")));
            String password = chaine1.substring(13);
            File src = new File("src/dossieretudiant/"+choix+"/");
            File dest = new File(dirPath+"\\ESIFOLIO-Portfolio");
            File Zip = new File(dirPath+"/ESIFOLIO-Portfolio.zip");

            dirPath = dirPath+"\\ESIFOLIO-Portfolio";
            if(!dest.exists()){
                if(!Zip.exists()){//si le répertoire et le zip n'existent pas (la premiere génération), créez-le
                    dest.mkdir();
                    File src2 = new File("src/dossieretudiant/HTML/images");
                    File src3 = new File("src/images");
                    if (!src2.exists()){
                        cl.CopyResssources(src3,src2);
                    }
                    File dest2 = new File(dirPath+"/images");
                    if (dest2.exists()) {Securite.deleteDocier(dest2);}
                    cl.CopyResssources(src2,dest2);
                    if (src2.exists()){
                        Securite.deleteDocier(src2);}
                    cl.CopyResssources(src,dest);
                    BufferedWriter rep = new BufferedWriter(new FileWriter(new File("src/repertoirefichier.txt")));
                    rep.write(dest.getPath()+"\\"+nomP+"."+choix.toLowerCase());
                    rep.newLine();
                    rep.close();
                }else{
                    BufferedWriter rep = new BufferedWriter(new FileWriter(new File("src/repertoirefichier.txt"),true));
                    //on dezip le dossier pour que peut rajouter les autres portfolio (plusieurs generation)
                    Securite.unZipPasswordProtectedFiles(Zip.getPath(),password);
                    cl.CopyResssources(src,dest);
                    rep.write(dest.getPath()+"\\"+nomP+"."+choix.toLowerCase());
                    rep.newLine();
                    rep.close();
                }
            }else{
                BufferedWriter rep = new BufferedWriter(new FileWriter(new File("src/repertoirefichier.txt"),true));
                if(Zip.exists()){
                    Securite.deleteDocier(Zip);
                    //lister le contenu du répertoire
                    cl.CopyResssources(src,dest);
                    rep.write(dest.getPath()+"\\"+nomP+"."+choix.toLowerCase());
                    rep.newLine();
                }
                else{
                    cl.CopyResssources(src,dest);
                    rep.write(dest.getPath()+"\\"+nomP+"."+choix.toLowerCase());
                    rep.newLine();
                }
                rep.close();
            }
            File fichAsupp = new File ("src/dossieretudiant/HTML/"+nomP+".html");
            if(fichAsupp.delete()){
            }
            File fichAsp2 = new File("src/dossieretudiant/PDF/"+nomP+".pdf");
            if(fichAsp2.delete()){
            }
            Securite.compressDirWithPwd(dirPath);
            File dos = new File(dirPath);
            Securite.deleteDocier(dos);
            /** Passer vers la fenêtre succès **/
            FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("PortfolioCreeVue.fxml"));
            Parent root = fxmlLoader.load();
            PortfolioCreeControleur controleur = fxmlLoader.getController();
            controleur.setNomPrenoms(info);
            controleur.setChemin(2,"HTML");
            Scene scene = new Scene(root,900.0,600.0);
            Stage window = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
            window.centerOnScreen();
        }catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML public void HTML2Survol() {
        boutonHTML2.setStyle("-fx-background-color: #804BC6; -fx-background-radius: 8");
    }

    @FXML public void HTML2MouseExited() {
        boutonHTML2.setStyle("-fx-background-color: #6F39B6; -fx-background-radius: 8");
    }

    @FXML public void HTML2Pressed() {
        boutonHTML2.setStyle("-fx-background-color: #582B94; -fx-background-radius: 8");
    }
}
