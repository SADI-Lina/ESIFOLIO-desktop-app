package com.example.esifolio2.Portfolio;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfWriter;
import java.io.*;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Portfolio {
    public void CopyResssources(File source, File destination) {
        try {
            if (source.isDirectory()) {
                if (!destination.exists()) {
                    destination.mkdir();
                }
                //lister le contenu du répertoire

                String files[] = source.list();

                for (String f : files) {
                    //construire la structure des fichiers src et dest
                    File srcF = new File(source, f);
                    File destF = new File(destination, f);
                    //copie récursive
                    Files.copy(srcF.toPath(), destF.toPath());
                }
            } else {
                //si src est un fichier, copiez-le.
                InputStream in = new FileInputStream(source);
                OutputStream out = new FileOutputStream(destination);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }

                in.close();
                out.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String GenererPortfolio() throws IOException {
        // recuperer le repertoire des fichiers
        File projet_Etud = new File("src/Projet_Etudiant");
        File [] tabNomFich1 = projet_Etud.listFiles();
        File fichInfoPerso = new File("src/ESIfolioapp/InformationsPersonnelles.folio");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("ddMMyyyyHHmm");
        String heureDate = dtf.format(LocalDateTime.now());
        File Portfolio = new File("src/dossieretudiant/HTML/Portfolio2-"+heureDate+".html");
        String nom;
        String prenom ;
        String matricule;
        String numero_tel;
        String  adr_mail;
        String adresse;
        String date_naissance;
        File tabNomFich =new File("src/page1+info.txt");
        try {
            FileReader fileR = new FileReader(fichInfoPerso);
            BufferedReader bufRead = new BufferedReader(fileR);
            String line1 = bufRead.readLine();
            nom = line1.substring(4);
            line1 = bufRead.readLine();
            prenom = line1.substring(7);
            line1 = bufRead.readLine();
            date_naissance = line1.substring(18);
            line1 = bufRead.readLine();
            matricule= line1.substring(10);
            line1 = bufRead.readLine();
            adr_mail = line1.substring(5);
            line1 = bufRead.readLine();
            adresse = line1.substring(8);
            line1 = bufRead.readLine();
            numero_tel = line1.substring(20);
            bufRead.close();
            fileR.close();
            FileReader fr = new FileReader(tabNomFich);
            BufferedReader br = new BufferedReader(fr);
            FileWriter fwrite = new FileWriter(Portfolio);
            BufferedWriter bwrite = new BufferedWriter(fwrite);
            List<String> tabInfo = new ArrayList<>();
            assert tabNomFich1 != null;
            /*Extraire Projet*/
            for (int i = 0; i < tabNomFich1.length; i++) {
                while ((tabNomFich1[i].getName().contains("Compétences_aquise.txt")) || (tabNomFich1[i].getName().contains("Fichier_nomsProjets.txt"))) {
                    i++;
                }
                File fichPrjt = tabNomFich1[i];
                FileReader fr1 = new FileReader(fichPrjt);
                BufferedReader br1 = new BufferedReader(fr1);
                String line = br1.readLine();
                tabInfo.add("$Prjt$Etud$2021");
                String descEntier = "";
                while ((line != null)) {
                    if (!line.contains("$")) {
                        tabInfo.add(line);
                    }
                    if (line.contains("$D/descriptifProjet/D$")) {
                        line = br1.readLine();
                        while (!line.contains("$")) {
                            descEntier += " " + line;
                            line = br1.readLine();
                        }
                        tabInfo.add(descEntier);
                    }
                    line = br1.readLine();
                    if ((line != null) && (line.contains("$A/attestationProjet/A$"))) {
                        line = null;
                    }
                }
                br1.close();
                fr1.close();

            }


            String lignePrjt = br.readLine();
            while (lignePrjt != null) {
                bwrite.write(lignePrjt);
                lignePrjt= br.readLine();
            }
            bwrite.write("<li class=\"nom-etudiant\"> <span class=\"mot\">Nom : </span>"+ "<span class=\"mot-descriptif\">"+nom+"</span>"+"</li>\n" +
                    "<li class=\"prenom-etudiant\"><span class=\"mot\">Prénom : </span> "+"<span class=\"mot-descriptif\">"+prenom +"</span>"+"</li>\n" +
                    "<li class=\"date-naissance\"><span class=\"mot\">Date de naissance : </span>"+"<span class=\"mot-descriptif\">"+date_naissance+"</span>"+"</li>\n" +
                    "<li class=\"matricule\"><span class=\"mot\">Matricule : </span>"+"<span class=\"mot-descriptif\">"+matricule+"</span>"+"</li>\n" +
                    "<li class=\"adresse-mail\"><span class=\"mot\">Adresse mail : </span>"+"<span class=\"mot-descriptif\">"+adr_mail+"</span>"+"</li>\n" );
            if(!numero_tel.contains("null")) {
                bwrite.write("<li class=\"adresse\"><span class=\"mot\">Numéro de téléphone : </span>" +"<span class=\"mot-descriptif\">"+ numero_tel +"</span>"+ "</li>\n");
            }
            if(!adresse.contains("null")) {
                bwrite.write("<li class=\"adresse\"><span class=\"mot\">Adresse : </span>" +"<span class=\"mot-descriptif\">"+adresse +"</span>"+ "</li>\n");
            }
            bwrite.write(  "            </ul>\n" +
                    "    </section>");
            int k=1;
            int j=0;
            while(j!=tabInfo.size()){

                j++;
                String type= tabInfo.get(j);
                j++;
                String nomP= tabInfo.get(j);
                bwrite.write( "<section class=\"projet\">"+
                        "<h1 class=\"titre-projet\">Projet "+" "+k+"</h1>"+
                        "<ul class=\"infos-projet\">"+
                        "</div>"+
                        "<li class=\"nom-projet\"> <span class=\"mot\">Nom : </span>"+"<span class=\"mot-descriptif\">"+nomP+"</span>"+"</li>"+
                        "<li class=\"type-projet\"> <span class=\"mot\">Type : </span> "+"<span class=\"mot-descriptif\">"+type+"</span>"+"</li>");
                j++;
                if (type.contains("Ecole") ){
                    bwrite.write("<li class=\"club-projet\"><span class=\"mot\">Module : </span>"+ "<span class=\"mot-descriptif\">"+tabInfo.get(j)+"</span>"+" </li>");
                    j++;
                }
                if (type.contains("Club")){
                    bwrite.write("<li class=\"club-projet\"><span class=\"mot\">Club : </span>   "+ "<span class=\"mot-descriptif\">"+tabInfo.get(j)+"</span>"+"</li>");
                    j++;
                }

                bwrite.write("<li class=\"descriptif\"><span class=\"mot\">Descriptif : </span>"+ "<span class=\"mot-descriptif\">"+tabInfo.get(j)+"</span>"+"</li>");
                j++;
                bwrite.write("<li class=\"compétences-acquises\"> <span class=\"mot\">Compétences acquises :</li>"+
                        " <div class=\"competence first\">"+
                        "</span><img src=\"./images/check-solid.svg\" alt=\"check\"><span>"+tabInfo.get(j)+"</span></div>");
                j++;
                while((j!=tabInfo.size())&&(!tabInfo.get(j).contains("$Prjt$Etud$2021"))){
                    bwrite.write("<div class=\"competence\">"+
                            " </span><img src=\"./images/check-solid.svg\" alt=\"check\"><span>"+tabInfo.get(j)+"</span> </div>");
                    j++;
                }
                k++;


                bwrite.write( " </ul>"+
                        "</section>");
            }
            bwrite.write("</body>"+
                    "</html>");

            bwrite.close();
            br.close();
            fr.close();
            fwrite.close();

        } catch (FileNotFoundException ex) {
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return "Portfolio2-"+heureDate;

    }

    public void convert(String inputHtml,String nomPortfolio) throws IOException {
        // recuperer le repertoire des fichiers
        FileOutputStream pdf =new FileOutputStream("src/dossieretudiant/PDF/"+nomPortfolio+".pdf");
        PdfWriter pdfWriter = new PdfWriter(pdf);
        ConverterProperties converterProperties = new ConverterProperties();
        HtmlConverter.convertToPdf(new FileInputStream(inputHtml), pdfWriter,converterProperties);
    }
}


