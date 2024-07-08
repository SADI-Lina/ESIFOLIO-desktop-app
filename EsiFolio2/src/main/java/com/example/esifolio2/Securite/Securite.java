package com.example.esifolio2.Securite;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

public class Securite {
    public static void compressDirWithPwd(String dirPath){
        try {
            /** Création des paramètres zip pour la sécurisation **/
            ZipParameters zipParameters = new ZipParameters();
            /** Fichiers de cryptage **/
            zipParameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            zipParameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            zipParameters.setEncryptFiles(true);
            /** Méthode de cryptage **/
            zipParameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
            /** Force de sécurisation **/
            zipParameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
            String string   = new String(Files.readAllBytes(Paths.get("src/ESIfolioapp/MotDePasse.folio")));
            String password = string.substring(13) ;
            zipParameters.setPassword(password);
            /** Création du fichier zip **/
            File Dossier = new File(dirPath);
            String destinationZipFilePath =  dirPath.replace(Dossier.getName(), Dossier.getName()+".zip");
            ZipFile zipFile = new ZipFile(destinationZipFilePath);
            /** Liste des fichiers à ajouter au fichier zip **/
            ArrayList<File> list = creerlistfile() ;
            zipFile.addFiles(list, zipParameters);
            File file = new File("src/Fonts");
            zipFile.addFolder(file, zipParameters);
            file = new File("src/images");
            zipFile.addFolder(file, zipParameters);
            /** Dossier de création du fichier zip **/
            Securite.deleteDocier(Dossier);
        }
        catch (ZipException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<File> creerlistfile() {
        File repertoirefichier = new File("src/repertoirefichier.txt");
        ArrayList<File> listdesfichier = new ArrayList<>();
        try {
            BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(repertoirefichier), "UTF-8"));
            String line = read.readLine();
            while (line != null) {
                listdesfichier.add(  new File(line));
                line = read.readLine();
            }
            read.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return listdesfichier;
    }

    public static void  unZipPasswordProtectedFiles(String zipFilePath , String Enterpassword){
        /** Suppression de .zip du nom du fichier compressé **/
        String unZipFilePath = zipFilePath.substring(0, zipFilePath.lastIndexOf("."));
        try {
            ZipFile zipFile = new ZipFile(zipFilePath);
            /** Mot de passe en cas de cryptage **/
            if(zipFile.isEncrypted()){
            /** Suppression du mot de passe **/
                zipFile.setPassword(Enterpassword);
                /** Décompression **/
                zipFile.extractAll(unZipFilePath);
            }
            File zipdir = new File(zipFilePath);
            deleteDocier(zipdir);
        } catch (ZipException e) {
            e.printStackTrace();
        }
    }

    public static  void deleteDocier (File Dossier ) {
        if(Dossier.isDirectory()){
            /** Suppression du dossier s'il est vide **/
            if(Dossier.list().length == 0){
                Dossier.delete();
            }else{
                /** Lister le contenu du répertoire **/
                String files[] = Dossier.list();

                for (String tmp : files) {
                    File file = new File(Dossier, tmp);
                    /** Suppression récursive **/
                    deleteDocier(file);
                }
                /** Suppression du dossier s'il est vide **/
                if(Dossier.list().length == 0){
                    Dossier.delete();
                }
            }
        }else{
            /** Suppression du fichier **/
            Dossier.delete();
        }
    }

    public static File  creer_f(String nom_fichier)
    {
        File file = new File(nom_fichier) ;
        if (!file.exists())
        {
            try {
                file.createNewFile();
            }
            catch (IOException e )
            {
                e.printStackTrace();
            }
        }
        return file;
    }

}


