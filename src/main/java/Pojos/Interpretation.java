package Pojos;

import BITalino.BITalino;
import BITalino.BITalinoException;

import javax.bluetooth.RemoteDevice;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import BITalino.Frame;
import BITalino.BITalino;
import BITalino.BITalinoException;

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
    public static final int samplingrate = 100;

    public Interpretation(LocalDate date, int patient, int doctor, String interpretation) {
        this.date = date;
        this.patient_id = patient;
        this.doctor_id = doctor;
        this.interpretation = interpretation;
        this.signalEDA = new Signal(Signal.SignalType.EDA);
        this.signalEMG = new Signal(Signal.SignalType.EMG);
        this.symptoms = new LinkedList<>();

    }
    public Interpretation(int interpretation_id, LocalDate date, int patient, int doctor, String interpretation) {
        this.id = interpretation_id;
        this.date = date;
        this.patient_id = patient;
        this.doctor_id = doctor;
        this.interpretation = interpretation;
        this.signalEDA = new Signal(Signal.SignalType.EDA);
        this.signalEMG = new Signal(Signal.SignalType.EMG);
        this.symptoms = new LinkedList<>();
    }
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

    public void saveValues(Signal signal) {
        if (signal != null) {
            // Llamamos al método getSignalValues con el parámetro de tasa de muestreo.
            LinkedList<Integer> signalValues = signal.getSignalValues(samplingrate);

            // Verificamos el tipo de señal y guardamos los valores en la lista correspondiente.
            if (signal.getSignalType() == Signal.SignalType.EMG) {
                signalEMG.addValues(signalValues);
                System.out.println("Valores guardados en values_EMG");
            } else if (signal.getSignalType() == Signal.SignalType.EDA) {
                signalEDA.addValues(signalValues);
                System.out.println("Valores guardados en values_EDA");
            }
        } else {
            System.out.println("No hay señal asignada a este paciente.");
        }
    }
    /*public File almacenarDatosEnFichero() throws FileNotFoundException {
        Date date = java.sql.Date.valueOf(LocalDate.now());
        File file = new File("MeasurementsBitalino/" + name + "_" + surname + "-" + date + ".txt");

        try (PrintWriter pw = new PrintWriter(file)) {
            pw.println("Patient: " + toString());

            pw.println(" - Bitalino recorded data: ");
            if (signal != null) {
                if (signal.getSignalType() == Signal.SignalType.EMG) {
                    values_EMG.forEach(value -> pw.println("   " + value));
                } else if (signal.getSignalType() == Signal.SignalType.EDA) {
                    values_EDA.forEach(value -> pw.println("   " + value));
                }
            } else {
                pw.println("   No signal data available.");
            }
        }

        return file;
    }*/

    public void recordBitalinoData(int seconds, String macAddress, Signal.SignalType signalType) throws BITalinoException {
        BITalino bitalino = new BITalino();
        int channel = 0;
        try {
            Vector<RemoteDevice> devices = bitalino.findDevices();
            System.out.println(devices);

            bitalino.open(macAddress, samplingrate);
            System.out.println("Connection successful!");

            if (signalType == Signal.SignalType.EMG) {
                channel = 0;
            } else if (signalType == Signal.SignalType.EDA) {
                channel = 2;
            }
            int[] channelsToAcquire = {channel}; // Cambiar según el canal para EMG o EDA
            bitalino.start(channelsToAcquire);

            System.out.println(" - Recording " + signalType + " signal...");
            LinkedList<Integer> recordedValues = new LinkedList<Integer>();
            for (int j = 0; j < seconds /** samplingrate / 10*/; j++) {
                //System.out.println("Starting recording");
                Frame[] frames = bitalino.read(samplingrate);
                //System.out.println("Frames captured: " + frames.length);
                for (Frame frame : frames) {
                    recordedValues.add(frame.analog[channel]);
                }
            }
            System.out.println(recordedValues);
            bitalino.stop();

            // Guardamos los valores según el tipo de señal
            if (signalType == Signal.SignalType.EMG) {
                signalEMG.addValues(recordedValues);
                //this.signalEMG = new Signal(Signal.SignalType.EMG, signalEMG.getValues());

            } else if (signalType == Signal.SignalType.EDA) {
                signalEDA.addValues(recordedValues);
                //this.signalEDA = new Signal(Signal.SignalType.EDA, signalEDA.getValues());
            }

        } catch (BITalinoException ex) {
            ex.printStackTrace();
        } catch (Throwable ex) {
            ex.printStackTrace();
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

    @Override
    public String toString() {
        return "Interpretation{" +
                "date=" + date +
                ", patient_id=" + patient_id +
                ", doctor_id=" + doctor_id +
                ", signalEMG=" + signalEMG +
                ", signalEDA=" + signalEDA +
                ", interpretation='" + interpretation + '\'' +
                ", symptoms=" + symptoms +
                ", observation='" + observation + '\'' +
                '}';
    }
}
