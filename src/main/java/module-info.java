module com.example.tetrisjavafx {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.example.tetrisjavafx to javafx.fxml;
    exports com.example.tetrisjavafx;
}