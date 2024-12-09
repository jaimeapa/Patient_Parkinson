package Pojos;

import BITalino.BITalino;
import BITalino.BITalinoException;

import javax.bluetooth.RemoteDevice;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import BITalino.Frame;


/**
 * Represents a medical interpretation report for a patient, including the EMG and EDA signals,
 * the symptoms, and the doctor's observations and interpretation.
 */
public class Interpretation  {

    /** Unique identifier for the interpretation. */
    private int id;

    /** Date of the interpretation. */
    private LocalDate date;

    /** Patient ID associated with the interpretation. */
    private int patient_id;

    /** Doctor ID who provided the interpretation. */
    private int doctor_id;

    /** EMG signal associated with the interpretation. */
    private Signal signalEMG;

    /** EDA signal associated with the interpretation. */
    private Signal signalEDA;

    /** The doctor's interpretation of the signals. */
    private String interpretation;

    /** A list of symptoms associated with the patient. */
    private List<Symptoms> symptoms;

    /** Additional observations made by the doctor. */
    private String observation;

    /** The sampling rate used for signal recording (100 Hz). */
    public static final int samplingrate = 100;

    /**
     * Constructs a new Interpretation with the specified date, patient, and doctor.
     *
     * @param date The date of the interpretation.
     * @param patient The ID of the patient.
     * @param doctor The ID of the doctor.
     */
    public Interpretation(LocalDate date, int patient, int doctor) {
        this.date = date;
        this.patient_id = patient;
        this.doctor_id = doctor;
        this.signalEDA = new Signal(Signal.SignalType.EDA);
        this.signalEMG = new Signal(Signal.SignalType.EMG);
        this.interpretation = "";
        this.observation = "";
        this.symptoms = new LinkedList<>();
    }

    /**
     * Constructs a new Interpretation with the specified details.
     *
     * @param date The date of the interpretation.
     * @param interpretation The doctor's interpretation.
     * @param signalEMG The EMG signal for the interpretation.
     * @param signalEDA The EDA signal for the interpretation.
     * @param patient_id The ID of the patient.
     * @param doctor_id The ID of the doctor.
     * @param observation Additional observations made by the doctor.
     */
    public Interpretation(LocalDate date, String interpretation, Signal signalEMG, Signal signalEDA,
                          int patient_id, int doctor_id, String observation) {
        this.date = date;
        this.interpretation = interpretation;
        this.symptoms = new LinkedList<>();
        this.signalEMG = signalEMG;
        this.signalEDA = signalEDA;
        this.patient_id = patient_id;
        this.doctor_id = doctor_id;
        this.observation = observation;
    }

    /**
     * Gets the date of the interpretation.
     *
     * @return The date of the interpretation.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Gets the unique identifier of the interpretation.
     *
     * @return The interpretation ID.
     */
    public int getInterpretation_id() {
        return id;
    }

    /**
     * Sets the date of the interpretation.
     *
     * @param date The date to set.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Gets the ID of the patient associated with the interpretation.
     *
     * @return The patient ID.
     */
    public int getPatient_id() {
        return patient_id;
    }

    /**
     * Sets the ID of the patient associated with the interpretation.
     *
     * @param patient The patient ID to set.
     */
    public void setPatient(int patient) {
        this.patient_id = patient;
    }

    /**
     * Gets the ID of the doctor who provided the interpretation.
     *
     * @return The doctor ID.
     */
    public int getDoctor_id() {
        return doctor_id;
    }

    /**
     * Gets the EMG signal associated with the interpretation.
     *
     * @return The EMG signal.
     */
    public Signal getSignalEMG() {
        return signalEMG;
    }

    /**
     * Gets the EDA signal associated with the interpretation.
     *
     * @return The EDA signal.
     */
    public Signal getSignalEDA() {
        return signalEDA;
    }

    /**
     * Gets the observation made by the doctor.
     *
     * @return The doctor's observation.
     */
    public String getObservation() {
        return observation;
    }

    /**
     * Sets the ID of the doctor who provided the interpretation.
     *
     * @param doctor The doctor ID to set.
     */
    public void setDoctor(int doctor) {
        this.doctor_id = doctor;
    }

    /**
     * Gets the interpretation provided by the doctor.
     *
     * @return The doctor's interpretation.
     */
    public String getInterpretation() {
        return interpretation;
    }

    /**
     * Sets the interpretation provided by the doctor.
     *
     * @param interpretation The interpretation to set.
     */
    public void setInterpretation(String interpretation) {
        this.interpretation = interpretation;
    }

    /**
     * Sets the unique identifier of the interpretation.
     *
     * @param interpretation_id The interpretation ID to set.
     */
    public void setInterpretation_id(int interpretation_id) {
        this.id = interpretation_id;
    }

    /**
     * Sets the EMG signal for the interpretation.
     *
     * @param signalEMG The EMG signal to set.
     */
    public void setSignalEMG(Signal signalEMG) {
        this.signalEMG = signalEMG;
    }

    /**
     * Sets the EDA signal for the interpretation.
     *
     * @param signalEDA The EDA signal to set.
     */
    public void setSignalEDA(Signal signalEDA) {
        this.signalEDA = signalEDA;
    }

    /**
     * Sets the observation made by the doctor.
     *
     * @param observation The observation to set.
     */
    public void setObservation(String observation) {
        this.observation = observation;
    }

    /**
     * Adds a symptom to the list of symptoms.
     *
     * @param symptom The symptom to add.
     */
    public void addSymptom(Symptoms symptom) {
        this.symptoms.add(symptom);
    }

    /**
     * Records data from the BITalino device for a specified duration and MAC address.
     *
     * @param seconds The duration of the recording in seconds.
     * @param macAddress The MAC address of the BITalino device.
     * @throws BITalinoException If there is an issue with the BITalino device.
     */
    public void recordBitalinoData(int seconds, String macAddress) throws BITalinoException {
        BITalino bitalino = new BITalino();
        try {
            Vector<RemoteDevice> devices = bitalino.findDevices();
            System.out.println(devices);

            bitalino.open(macAddress, samplingrate);
            System.out.println("Connection successful!");

            int[] channelsToAcquire = {0, 2}; // Change the channel according to the EMG or EDA signal
            bitalino.start(channelsToAcquire);

            System.out.println(" - Recording the EMG and EDA signals...");
            LinkedList<Integer> recordedValuesEDA = new LinkedList<>();
            LinkedList<Integer> recordedValuesEMG = new LinkedList<>();
            for (int j = 0; j < seconds; j++) {
                Frame[] frames = bitalino.read(samplingrate);
                for (Frame frame : frames) {
                    recordedValuesEMG.add(frame.analog[0]);
                    recordedValuesEDA.add(frame.analog[1]);
                }
            }

            bitalino.stop();

            if (recordedValuesEDA.stream().allMatch(num -> num == 0)) {
                recordedValuesEDA = new LinkedList<>();
            }
            if (recordedValuesEMG.stream().allMatch(num -> num == 0)) {
                recordedValuesEMG = new LinkedList<>();
            }
            System.out.println(recordedValuesEMG);
            System.out.println(recordedValuesEDA);

            signalEMG.addValues(recordedValuesEMG);
            signalEDA.addValues(recordedValuesEDA);

        } catch (Throwable ex) {
            System.out.println("Problem when connecting to the BITalino");
        } finally {
            try {
                if (bitalino != null) {
                    bitalino.close();
                }
            } catch (BITalinoException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * Returns a string representation of the interpretation.
     *
     * @return A string containing the interpretation details.
     */
    @Override
    public String toString() {
        return "Report " + date + ":" +
                "\n signalEMG=" + signalEMG +
                "\n signalEDA=" + signalEDA +
                "\n Your Symptoms: " + symptoms +
                "\n Your observations: '" + observation + '\'' +
                "\n Doctor´s notes: '" + interpretation + '\'';
    }
}





