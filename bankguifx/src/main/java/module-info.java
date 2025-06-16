module br.com.descomplica {
    requires javafx.controls;
    requires javafx.fxml;

    opens br.com.descomplica to javafx.fxml;
    exports br.com.descomplica;
}
