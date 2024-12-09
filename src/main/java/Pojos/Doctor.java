package Pojos;

import java.io.Serializable;
import java.rmi.NotBoundException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.LinkedList;

/**
 * Represents a doctor with personal information and a list of assigned patients.
 * Implements {@link Serializable} for object serialization.
 */
public class Doctor implements Serializable {

    /** Unique identifier for the doctor. */
    private int doctor_id;

    /** First name of the doctor. */
    private String name;

    /** Surname of the doctor. */
    private String surname;

    /** Date of birth of the doctor. */
    private LocalDate dob;

    /** Email address of the doctor. */
    private String email;

    /** List of patients assigned to the doctor. */
    private LinkedList<Patient> patients;

    /**
     * Constructs a new Doctor with the specified details.
     *
     * @param doctor_id Unique identifier for the doctor.
     * @param name First name of the doctor.
     * @param surname Surname of the doctor.
     * @param dob Date of birth of the doctor.
     * @param email Email address of the doctor.
     */
    public Doctor(int doctor_id, String name, String surname, LocalDate dob, String email) {
        this.doctor_id = doctor_id;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.email = email;
        this.patients = new LinkedList<>();
    }

    /**
     * Gets the unique identifier of the doctor.
     *
     * @return The doctor ID.
     */
    public int getDoctor_id() {
        return doctor_id;
    }

    /**
     * Sets the unique identifier of the doctor.
     *
     * @param doctor_id The doctor ID to set.
     */
    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    /**
     * Gets the first name of the doctor.
     *
     * @return The doctor's first name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the first name of the doctor.
     *
     * @param name The first name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the surname of the doctor.
     *
     * @return The doctor's surname.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets the surname of the doctor.
     *
     * @param surname The surname to set.
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the date of birth of the doctor.
     *
     * @return The doctor's date of birth.
     */
    public LocalDate getDob() {
        return dob;
    }

    /**
     * Sets the date of birth of the doctor.
     *
     * @param dob The date of birth to set.
     */
    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    /**
     * Gets the email address of the doctor.
     *
     * @return The doctor's email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the doctor.
     *
     * @param email The email address to set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the list of patients assigned to the doctor.
     *
     * @return A list of the doctor's patients.
     */
    public LinkedList<Patient> getPatients() {
        return patients;
    }

    /**
     * Sets the list of patients assigned to the doctor.
     *
     * @param patients The list of patients to set.
     */
    public void setPatients(LinkedList<Patient> patients) {
        this.patients = patients;
    }

    /**
     * Returns a string representation of the doctor object.
     *
     * @return A string containing the doctor's details.
     */
    @Override
    public String toString() {
        return "Doctor{" +
                "doctor_id=" + doctor_id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                '}';
    }
}

