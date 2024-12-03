module charly.projects.handprogrammingf {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens charly.projects.handprogrammingf to javafx.fxml;
    exports charly.projects.handprogrammingf;
}