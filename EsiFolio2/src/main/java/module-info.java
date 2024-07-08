module EsiFolio2 {
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.controls;
    requires java.mail;
    requires java.logging;
    requires kernel;
    requires html2pdf;
    requires zip4j;
    opens com.example.esifolio2 to javafx.fxml;
    exports com.example.esifolio2;
    exports com.example.esifolio2.Statistiques;
    opens com.example.esifolio2.Statistiques to javafx.fxml;
    exports com.example.esifolio2.InfosPersonnelles;
    opens com.example.esifolio2.InfosPersonnelles to javafx.fxml;
    exports com.example.esifolio2.Authentification;
    opens com.example.esifolio2.Authentification to javafx.fxml;

}