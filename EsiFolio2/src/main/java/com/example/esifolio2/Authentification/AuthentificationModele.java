package com.example.esifolio2.Authentification;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AuthentificationModele {

    private String motDePasse;

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public static boolean VerifierMail(String mail)
    {
        if (!mail.contains("@esi.dz")){
            return false;
        }
        else {
            if (mail.indexOf("_") == 2) {
                return true;
            } else {
                return false;
            }
        }
    }

    public static int validerMotDePasse(String MotDePasse) { //Minimum : 6 caractères alphanumériques
        int inputlength = MotDePasse.length();
        int ascii = 0;
        int i = 0;
        int output =0;
        int sortir = 0;  //0 : reboucler ; 1 : sortir
        while ((i < inputlength) && (sortir == 0)) {
            char ch = MotDePasse.charAt(i);
            ascii = ch;
            if ((48 <= ascii && ascii <= 57) || (65 <= ascii && ascii <= 90) || (97 <= ascii && ascii <= 122)) {
            } else {
                sortir = 1;
                output = 0;
            }
            i++;
        }
        if (sortir == 0) {
            /** Vérification de la taille (taille min = 6 ) **/
            if (inputlength >= 6 ) {
                output = 1;
            } else {
                output = 2;
            }
        }
        return  output;
    }

    public static void sendMail(String recepient) throws Exception {
        String code_alea;
        int code;
        /** Génération du code de confirmation **/
        Random random = new Random();
        code = 11111 +random.nextInt(99999-11111);
        code_alea = Integer.toString(code);
        /**Envoi du mail **/
        Properties properties = new Properties();

        properties.put("mail.smtp.auth","true" );
        properties.put("mail.smtp.starttls.enable","true" );
        properties.put("mail.smtp.host","smtp.mail.yahoo.com" );
        properties.put("mail.smtp.port","587" );

        String myAccountEmail = "contactesifolio@yahoo.com";
        String password = "zssu\n" +
                "parj\n" +
                "qfhg\n" +
                "yryv";

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail,password);
            }
        });

        Message message = prepareMessage(session,myAccountEmail,recepient,code_alea);

        Transport.send(message);
        /** Création répertoire **/
        File folder = new File("./src/ESIfolioapp");
        if (!folder.exists()) {
            folder.mkdir();

        }
        /** Création fichier contenant le code **/
        File file = new File("./src/ESIfolioapp/" + "CodeConfirmation.folio");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        /** Remplir le fichier **/
        try {
            FileWriter writer = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write("CODE " + code_alea);
            bw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static Message prepareMessage(Session session,String myAccountEmail, String recepient ,String code_alea) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("[EsiFolio] Code De Confirmation");
            message.setText("Votre code de confirmation est : " + code_alea);
            return message;
        } catch (Exception ex) {
            Logger.getLogger(AuthentificationModele.class.getName()).log(Level.SEVERE , null ,ex);
        }
        return null;
    }



    public static void sendMailContact(String messageUtilisateur , String adresseUtilisateur, String nomPrenoms) throws Exception {
        /**Envoi du mail **/
        Properties properties = new Properties();

        properties.put("mail.smtp.auth","true" );
        properties.put("mail.smtp.starttls.enable","true" );
        properties.put("mail.smtp.host","smtp.mail.yahoo.com" );
        properties.put("mail.smtp.port","587" );

        String myAccountEmail = "contactesifolio@yahoo.com";
        String password = "zssu\n" +
                "parj\n" +
                "qfhg\n" +
                "yryv";

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail,password);
            }
        });

        Message message = prepareMessageContact(session,myAccountEmail,myAccountEmail,messageUtilisateur,adresseUtilisateur,nomPrenoms);

        Transport.send(message);
    }
    private static Message prepareMessageContact(Session session,String myAccountEmail, String recepient,String messageUtilisateur, String adresseUtlisateur,String nomPrenoms) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("[EsiFolio] Contact ");
            message.setText("Adresse : "+adresseUtlisateur+"\n"+"Nom et prénoms : "+nomPrenoms+"\n"+"Message :\n"+messageUtilisateur);
            return message;
        } catch (Exception ex) {
            Logger.getLogger(AuthentificationModele.class.getName()).log(Level.SEVERE , null ,ex);
        }
        return null;
    }
}
