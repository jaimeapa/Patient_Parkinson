package Pojos;

import java.time.LocalDate;
import java.util.List;

public class Interpretation {

    private int id;
    private LocalDate date;
    private int patient_id;
    private int doctor_id;
    private Signal signalEMG;
    private Signal signalEDA;
    private String interpretation;
    private List<Symptoms> symptoms;
    private String observation;

    public Interpretation(LocalDate date, int patient, int doctor, String interpretation) {
        this.date = date;
        this.patient_id = patient;
        this.doctor_id = doctor;
        this.interpretation = interpretation;
    }
    public Interpretation(int interpretation_id, LocalDate date, int patient, int doctor, String interpretation) {
        this.id = interpretation_id;
        this.date = date;
        this.patient_id = patient;
        this.doctor_id = doctor;
        this.interpretation = interpretation;
    }
    public Interpretation(LocalDate date, int patient, int doctor){
        this.date = date;
        this.patient_id = patient;
        this.doctor_id = doctor;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getInterpretation_id() {
        return id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient(int patient) {
        this.patient_id = patient;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public Signal getSignalEMG() {
        return signalEMG;
    }

    public Signal getSignalEDA() {
        return signalEDA;
    }

    public String getObservation() {
        return observation;
    }

    public void setDoctor(int doctor) {
        this.doctor_id = doctor;
    }

    public String getInterpretation() {
        return interpretation;
    }

    public void setInterpretation(String interpretation) {
        this.interpretation = interpretation;
    }

    public void setInterpretation_id(int interpretation_id) {
        this.id = interpretation_id;
    }

    public void setSignalEMG(Signal signalEMG) {
        this.signalEMG = signalEMG;
    }

    public void setSignalEDA(Signal signalEDA) {
        this.signalEDA = signalEDA;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public void addSymptom(Symptoms symptom){
        this.symptoms.add(symptom);
    }
}
