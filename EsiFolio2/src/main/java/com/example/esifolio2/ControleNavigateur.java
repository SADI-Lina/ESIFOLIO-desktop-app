package com.example.esifolio2;

import java.io.IOException;

public class ControleNavigateur {
    public static void afficherPage(String url){
        String commande = null;
        try{
            if(plateformeWindows()){
                commande = "rundll32 url.dll,FileProtocolHandler " + url;
                Process pc = Runtime.getRuntime().exec(commande);
            }
            else{
                commande = "netscape -remote openURL("+url+")";
                Process pc = Runtime.getRuntime().exec(commande);
                try{
                    int codeExit = pc.waitFor();
                    if(codeExit!=0){
                        commande = "netscape " + url;
                        pc = Runtime.getRuntime().exec(commande);
                    }
                }catch(InterruptedException e){

                }
            }
        }catch(IOException e){

        }
    }

    private static boolean plateformeWindows() {
        String systeme = System.getProperty("os.name");
        if(systeme != null && systeme.startsWith("Windows")){
            return true;
        }
        else return false;
    }
}
