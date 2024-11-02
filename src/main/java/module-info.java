module com.example.patient_parkinson {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.patient_parkinson to javafx.fxml;
    exports com.example.patient_parkinson;
}