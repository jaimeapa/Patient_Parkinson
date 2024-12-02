package Pojos;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import Pojos.Signal.SignalType;
import Pojos.Doctor;

public class Interpretation {

    private LocalDate date;
    private Patient patient;
    private Doctor doctor;
    private String interpretation;

    public Interpretation(LocalDate date, Patient patient, Doctor doctor, String interpretation) {
        this.date = date;
        this.patient = patient;
        this.doctor = doctor;
        this.interpretation = interpretation;
    }
    public Interpretation(LocalDate date, Patient patient, Doctor doctor){
        this.date = date;
        this.patient = patient;
        this.doctor = doctor;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getInterpretation() {
        return interpretation;
    }

    public void setInterpretation(String interpretation) {
        this.interpretation = interpretation;
    }
}
