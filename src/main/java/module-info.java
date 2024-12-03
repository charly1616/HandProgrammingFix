module charly.projects.handprogrammingf {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires java.sql;

    opens charly.projects.handprogrammingf to javafx.fxml;
    exports charly.projects.handprogrammingf;
    exports charly.projects.handprogrammingf.Model;
    exports charly.projects.handprogrammingf.Bloques;
    exports charly.projects.handprogrammingf.Controller;
}