package com.example.esifolio2;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfWriter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Genererportfolio {


    public File extraireInfoP (String nomFile) {
        File file1 = new File ("./"+nomFile);
        File file2 = new File(nomFile+"Extr.txt");
        try {
            file2.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedWriter write = new BufferedWriter(new FileWriter(file2));
            BufferedReader read1 = new BufferedReader(new InputStreamReader(new FileInputStream(file1), "UTF-8"));
            String nom = read1.readLine();
            write.write("<h4 class=\"byline\">Nom</h4>");
            write.newLine();
            write.write("<h4 class=\"donnée\">"+nom.substring(4)+"</h4>");
            write.newLine();
            String prenom = read1.readLine();
            write.write("<h4 class=\"byline\">Prenom(s)</h4>");
            write.newLine();
            write.write("<h4 class=\"donnée\">"+prenom.substring(7)+"</h4>");
            write.newLine();
            String dateDeNaissance = read1.readLine();
            write.write("<h4 class=\"byline\">Date De Naissance</h4>");
            write.newLine();
            write.write("<h4 class=\"donnée\">"+dateDeNaissance.substring(18)+"</h4>");
            write.newLine();
            String matricule = read1.readLine();
            write.write("<h4 class=\"byline\">Matricule</h4>");
            write.newLine();
            write.write("<h4 class=\"donnée\">"+matricule.substring(10)+"</h4>");
            write.newLine();
            write.write("<h2>Coordonnées</h2> ");
            write.newLine();
            String mail = read1.readLine();
            write.write("<h4 class=\"byline\">Email</h4>");
            write.newLine();
            write.write("<h4 class=\"donnée\">"+mail.substring(5)+"</h4>");
            write.newLine();
            String adress = read1.readLine();
            if (!adress.contains("null")){
                write.write("<h4 class=\"byline\">Adresse</h4>");
                write.newLine();
                write.write("<h4 class=\"donnée\">"+adress.substring(8)+"</h4>");
                write.newLine();}
            String tel = read1.readLine();
            if (!tel.contains("null")){
                write.write("<h4 class=\"byline\">N°Télephone</h4>");
                write.newLine();
                write.write("<h4 class=\"donnée\">"+tel.substring(20)+"</h4>");
                write.newLine();}
            write.close();
            read1.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file2;
    }


    public File extraireProjet(String nomFile){
        File file1 = new File ("src/Projet_Etudiant/"+nomFile+".txt");
        File file2 = new File(nomFile+"Extr.txt");
        try {
            file2.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedWriter write = new BufferedWriter(new FileWriter(file2));
            BufferedReader read = new BufferedReader(new InputStreamReader(new FileInputStream(file1), "UTF-8"));
            String line = read.readLine();
            String type = read.readLine();
            line = read.readLine();
            String nom = read.readLine();
            write.write("<h4 class=\"byline\">NOM</h4>" );
            write.newLine();
            write.write("<h4 class=\"donnée\">"+nom+"</h4>" );
            write.newLine();
            write.write("<h4 class=\"byline\">TYPE</h4>" );
            write.newLine();
            write.write("<h4 class=\"donnée\">"+type+"</h4>" );
            write.newLine();
            line = read.readLine();
            if(type.equalsIgnoreCase("Ecole")){
                String module = read.readLine();
                write.write("<h4 class=\"byline\">MODULE</h4>" );
                write.newLine();
                write.write("<h4 class=\"donnée\">"+module+"</h4>" );
                write.newLine();
                line= read.readLine();
            }
            if (type.equalsIgnoreCase("Club")){
                String club = read.readLine();
                write.write("<h4 class=\"byline\">CLUB</h4>" );
                write.newLine();
                write.write("<h4 class=\"donnée\">"+club+"</h4>" );
                write.newLine();
                line= read.readLine();
            }
            String descriptif = read.readLine();
            write.write("<h4 class=\"byline\">DESCRIPTIF</h4>" );
            write.write("<p>"+descriptif+"</p>");
            write.newLine();
            line = read.readLine();
            String competence = read.readLine();
            write.write("<h4 class=\"byline\">COMPETENCES</h4>");
            write.newLine();
            while (!(competence.equalsIgnoreCase("$A/attestationProjet/A$") )) {
                write.write("<li>"+competence+"</li>");
                write.newLine();
                competence = read.readLine();
            }
            write.write("<li> </li>");
            String lien = read.readLine();
            if (lien.contains("https://") || lien.contains("http://")){
                write.write("<h4 class=\"byline\">LIEN</h4>" );
                write.newLine();
                write.write("<a href=\""+lien+"\" target=\"_blank\"><h4 class=\"donnée\">"+lien+"</h4></a>" );
                write.newLine();
            }
            write.close();
            read.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file2;
    }
    public void CopyResssources(File src , File dest) throws IOException {
        if(src.isDirectory()){
            //si le répertoire n'existe pas, créez-le
            if(!dest.exists()){
                dest.mkdir();
            }
            //lister le contenu du répertoire
            String files[] = src.list();

            for (String f : files) {
                //construire la structure des fichiers src et dest
                File srcF = new File(src, f);
                File destF = new File(dest, f);
                //copie récursive
                Files.copy(srcF.toPath(),destF.toPath());
            }
        }else{
            //si src est un fichier, copiez-le.
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);

            byte[] buffer = new byte[1024];
            int length;
            //copier le contenu du fichier
            while ((length = in.read(buffer)) > 0){
                out.write(buffer, 0, length);
            }

            in.close();
            out.close();
        }}

    public String genererPortfolio (String[] nomTab ) throws IOException {
        String info;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyyHHmm");
        String heureDate = dtf.format(LocalDateTime.now());
        File modelInfo = new File("src/HTML/info.html");
        File fileInfo = extraireInfoP("src/ESIfolioapp/InformationsPersonnelles.folio");
        File modelPage = new File("src/HTML/page.html");
        File modelCoord = new File("src/HTML/coord.html");
        String chaine  = new String(Files.readAllBytes(Paths.get("src/ESIfolioapp/LienRepertoire.folio")));
        // recuperer le repertoire des fichiers
        String dirPath = chaine.substring(16);
        File Portfolio = new File("src/dossieretudiant/HTML/Portfolio1-"+heureDate+".html");
        try {
            Portfolio.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            BufferedWriter write1 = new BufferedWriter(new FileWriter(Portfolio));
            BufferedReader read2 = new BufferedReader(new InputStreamReader(new FileInputStream(fileInfo), "UTF-8"));
            BufferedReader read1 = new BufferedReader(new InputStreamReader(new FileInputStream(modelInfo), "UTF-8"));
            String line = read1.readLine();
            boolean trouv=false;
            while ((line != null)&&(trouv==false)) {

                if (line.contains("Info_P")){
                    info=read2.readLine();
                    while (!info.contains("Coordonnées")){
                        write1.write( info);
                        write1.newLine();
                        info= read2.readLine();
                    }
                }

                else {
                    write1.write(line);
                    write1.newLine();
                }
                if(line.contains("<!--Arret-->")){
                    trouv=true;
                }

                line = read1.readLine();
            }
            int j=0;
            int randomNum = 1;
            while(nomTab[j]!=null) {
                File fileProjet =extraireProjet(nomTab[j]);
                BufferedReader read4 = new BufferedReader(new InputStreamReader(new FileInputStream(modelPage), "UTF-8"));
                String prj = read4.readLine();
                while (prj != null) {

                    if (prj.contains("P_R_J")) {
                        prj=read4.readLine();
                        BufferedReader read5 = new BufferedReader(new InputStreamReader(new FileInputStream(fileProjet), "UTF-8"));
                        String prj1 = read5.readLine();
                        while (prj1 != null) {
                            write1.write(prj1);
                            write1.newLine();
                            prj1 = read5.readLine();
                        }
                        read5.close();
                    }
                    if(prj.contains("<!--ici-->")) {
                        write1.write(prj+" "+(j+1));
                        write1.newLine();
                        prj = read4.readLine();
                    }
                    if(prj.contains("<!--class-->")) {
                        write1.write(prj+""+randomNum);
                        int i = randomNum;
                        randomNum = 1 + (int)(Math.random() * 3);
                        while (randomNum == i){randomNum = 1 + (int)(Math.random() * 3);}
                        write1.newLine();
                        prj = read4.readLine();
                    }
                    write1.write(prj);
                    write1.newLine();
                    prj = read4.readLine();
                }
                read4.close();
                j++;
                fileProjet.delete();
            }
            BufferedReader read4 = new BufferedReader(new InputStreamReader(new FileInputStream(modelCoord), "UTF-8"));
            String coord = read4.readLine();

            while (coord!=null){
                if (coord.contains("COORD")){
                    coord = read4.readLine();
                    info=read2.readLine();
                    while (info!=null){
                        write1.write( info);
                        write1.newLine();
                        info= read2.readLine();
                    }}
                write1.write(coord);
                write1.newLine();
                coord = read4.readLine();
            }
            while(line!=null){
                write1.write(line);
                write1.newLine();
                line=read1.readLine();
            }
            write1.close();
            read1.close();
            read2.close();
            read4.close();
            fileInfo.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Portfolio1-"+heureDate;
    }

    public void convert(String inputHtml , String nomFich) throws IOException {

        FileOutputStream pdf =new FileOutputStream("src/dossieretudiant/PDF/"+nomFich+".pdf");
        PdfWriter pdfWriter = new PdfWriter(pdf);
        ConverterProperties converterProperties = new ConverterProperties();
        HtmlConverter.convertToPdf(new FileInputStream(inputHtml), pdfWriter,converterProperties);

    }





}

