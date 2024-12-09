package Pojos;

import java.time.LocalDate;
import java.util.LinkedList;

/**
 * Represents a patient with personal information and a list of interpretations.
 */
public class Patient {

    private int patient_id;
    private String name;
    private String surname;
    private LocalDate dob;
    private String email;
    private LinkedList<Interpretation> interpretations;

    /**
     * Constructs a new Patient with the specified name, surname, date of birth, and email.
     *
     * @param name The name of the patient.
     * @param surname The surname of the patient.
     * @param dob The date of birth of the patient.
     * @param email The email address of the patient.
     */
    public Patient(String name, String surname, LocalDate dob, String email) {
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.email = email;
        this.interpretations = new LinkedList<>();
    }

    /**
     * Constructs a new Patient with the specified patient ID, name, surname, date of birth, and email.
     *
     * @param patient_id The unique identifier for the patient.
     * @param name The name of the patient.
     * @param surname The surname of the patient.
     * @param dob The date of birth of the patient.
     * @param email The email address of the patient.
     */
    public Patient(int patient_id, String name, String surname, LocalDate dob, String email) {
        this.patient_id = patient_id;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.email = email;
        this.interpretations = new LinkedList<>();
    }

    /**
     * Gets the unique identifier of the patient.
     *
     * @return The patient ID.
     */
    public int getPatient_id() {
        return patient_id;
    }

    /**
     * Sets the unique identifier of the patient.
     *
     * @param patient_id The patient ID to set.
     */
    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    /**
     * Gets the name of the patient.
     *
     * @return The name of the patient.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the patient.
     *
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname of the patient.
     *
     * @return The surname of the patient.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the patient.
     *
     * @param surname The surname to set.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the date of birth of the patient.
     *
     * @return The date of birth of the patient.
     */
    public LocalDate getDob() {
        return dob;
    }

    /**
     * Sets the date of birth of the patient.
     *
     * @param dob The date of birth to set.
     */
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    /**
     * Gets the email address of the patient.
     *
     * @return The email address of the patient.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the patient.
     *
     * @param email The email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Returns a string representation of the patient.
     *
     * @return A string containing the patient's details.
     */
    @Override
    public String toString() {
        return "Patient{" +
                "patient_id=" + patient_id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                '}';
    }
}
