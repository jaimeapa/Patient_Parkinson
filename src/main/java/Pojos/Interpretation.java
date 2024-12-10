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
 * This class represents an Interpretation for a patient's medical data.
 * It stores information such as the interpretation date, associated signals (EMG and EDA),
 * patient and doctor IDs, and observations related to the interpretation.
 */
public class Interpretation {

    /**
     * The ID of the medical record.
     */
    private int id;

    /**
     * The date when the medical record was created.
     */
    private LocalDate date;

    /**
     * The ID of the patient associated with this medical record.
     */
    private int patient_id;

    /**
     * The ID of the doctor who created this medical record.
     */
    private int doctor_id;

    /**
     * The EMG (electromyography) signal data associated with this record.
     */
    private Signal signalEMG;

    /**
     * The EDA (electrodermal activity) signal data associated with this record.
     */
    private Signal signalEDA;

    /**
     * The interpretation or diagnosis based on the data in the medical record.
     */
    private String interpretation;

    /**
     * A list of symptoms related to the patient in this medical record.
     */
    private List<Symptoms> symptoms;

    /**
     * Additional observations or notes about the patient's condition.
     */
    private String observation;

    /**
     * The sampling rate used for the signal data (in Hz).
     * Default value is 100 Hz.
     */
    public static final int samplingrate = 100;
    /**
     * Constructor to create an interpretation with the specified date, patient, and doctor IDs.
     * @param date the date of the interpretation.
     * @param patient the ID of the patient.
     * @param doctor the ID of the doctor.
     */
    public Interpretation(LocalDate date, int patient, int doctor){
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
     * Constructor to create an interpretation with all required details.
     * @param date the date of the interpretation.
     * @param interpretation textual interpretation provided by the doctor.
     * @param signalEMG EMG signal data.
     * @param signalEDA EDA signal data.
     * @param patient_id the ID of the patient.
     * @param doctor_id the ID of the doctor.
     * @param observation additional observations.
     */
    public Interpretation(LocalDate date, String interpretation, Signal signalEMG, Signal signalEDA, int patient_id, int doctor_id, String observation) {
        this.date = date;
        this.interpretation = interpretation;
        this.symptoms = new LinkedList<>();
        this.signalEMG = signalEMG;
        this.signalEDA = signalEDA;
        this.patient_id = patient_id;
        this.doctor_id = doctor_id;
        this.observation = observation;
    }
    // Getters and setters for class properties.

    /**
     * Adds a symptom to the interpretation's symptoms list.
     */
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

    /**
     * Records data from a BITalino device for a given duration and stores the EMG and EDA signals.
     * @param seconds duration of data recording in seconds.
     * @param macAddress MAC address of the BITalino device.
     * @param bitalino BITalino device object.
     * @throws BITalinoException if there is an issue with the BITalino device.
     */
    public void recordBitalinoData(int seconds, String macAddress, BITalino bitalino) throws BITalinoException {
        try {
            bitalino.open(macAddress, samplingrate);
            System.out.println("Connection successful!");


            int[] channelsToAcquire = {0,2}; // Cambiar según el canal para EMG o EDA
            bitalino.start(channelsToAcquire);

            System.out.println(" - Recording the EMG and EDA signals...");
            LinkedList<Integer> recordedValuesEDA = new LinkedList  <>();
            LinkedList<Integer> recordedValuesEMG = new LinkedList<>();
            for (int j = 0; j < seconds /** samplingrate / 10*/; j++) {
                //System.out.println("Starting recording");
                Frame[] frames = bitalino.read(samplingrate);
                //System.out.println("Frames captured: " + frames.length);
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
     * @return a string containing details of the interpretation.
     */
    @Override
    public String toString() {
        return "Report " + date + ":"+
                "\n signalEMG=" + signalEMG +
                "\n signalEDA=" + signalEDA +
                "\n Your Symptoms: " + symptoms +
                "\n Your observations: '" + observation + '\'' +
                "\n Doctor´s notes: '" + interpretation + '\'';
    }
}
