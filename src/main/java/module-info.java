module com.example.siteecommerce {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.siteecommerce to javafx.fxml;
    exports com.example.siteecommerce;
}