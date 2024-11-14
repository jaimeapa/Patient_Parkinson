package Pojos;

import javax.bluetooth.RemoteDevice;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.Vector;

import BITalino.Frame;
import BITalino.BITalino;
import BITalino.BITalinoException;

public class Patient {
    private int patient_id;
    private String name;
    private String surname;
    private LocalDate dob;
    private String email;
    private Signal signal;
    private Symptoms symptoms;
    private LinkedList<Integer> values_EDA;
    private LinkedList<Integer> values_EMG;


    public Patient(int patient_id, String name, String surname, LocalDate dob, String email, Symptons symptoms) {
        this.patient_id = patient_id;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.email = email;
        this.symptoms = symptoms;
        this.values_EDA = new LinkedList<String>;
        this.values_EMG = new LinkedList<String>;
    }

    public Patient(String name, String surname, LocalDate dob, String email) {
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.email = email;
    }

    public void guardarValoresSegunTipoDeSenal(Signal signal) {
        if (signal != null) {
            // Llamamos al método getSignalValues con el parámetro de tasa de muestreo.
            LinkedList<Integer> signalValues = signal.getSignalValues(samplingRate);

            // Verificamos el tipo de señal y guardamos los valores en la lista correspondiente.
            if (signal.getSignalType() == Signal.SignalType.EMG) {
                values_EMG.addAll(signalValues);
                System.out.println("Valores guardados en values_EMG");
            } else if (signal.getSignalType() == Signal.SignalType.EDA) {
                values_EDA.addAll(signalValues);
                System.out.println("Valores guardados en values_EDA");
            }
        } else {
            System.out.println("No hay señal asignada a este paciente.");
        }
    }


    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Pojos.Signal getSignal() {
        return signal;
    }
    public void setSignal(Pojos.Signal signal) {
        this.signal = signal;
    }

    public LinkedList<Symptoms> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(LinkedList<Symptoms> symptoms) {
        this.symptoms = symptoms;
    }

    public LinkedList<Integer> getValues_EMG() {
        return values_EMG;
    }

    public void setValues_EMG(LinkedList<Integer> values_EMG) {
        this.values_EMG = values_EMG;
    }
    public LinkedList<Integer> getValues_EDA() {
        return values_EDA;
    }

    public void setValues_EDA(LinkedList<Integer> values_EDA) {
        this.values_EDA = values_EDA;
    }
    public File almacenarDatosEnFichero() throws FileNotFoundException, FileNotFoundException {

        Date date = (Date) java.sql.Date.valueOf(LocalDate.now());
        File file = new File("MeasurementsBitalino\\" + name + "_" + surname + "-" + date + ".txt");

        PrintWriter pw = new PrintWriter(file);
        pw.println("Patient: " + toString());

        pw.println(" -Bitalino recorded data: " );
        for(Integer v: values) { //como hacemos para que detecte si EDA o EMG???
            pw.println("   " + v);
        }
        pw.close();

        return file;
    }

    public void recordBitalinoData(int seconds, String macAddress, Signal.SignalType signalType) {
        BITalino bitalino = new BITalino();
        try {
            Vector<RemoteDevice> devices = bitalino.findDevices();d
            System.out.println(devices);

            int samplingRate = 10;
            bitalino.open(macAddress, samplingRate);

            int[] channelsToAcquire = {0}; // Asegúrate de usar el canal adecuado para EMG o EDA
            bitalino.start(channelsToAcquire);

            System.out.println(" -Recording " + signalType + " signal...");

            List<Integer> recordedValues = new LinkedList<>();
            for (int j = 0; j < seconds * samplingRate / 10; j++) {
                Frame[] frames = bitalino.read(samplingRate);
                for (Frame frame : frames) {
                    recordedValues.add(frame.analog[0]);
                }
            }

            bitalino.stop();

            // Asigna los valores según el tipo de señal
            if (signalType == Signal.SignalType.EMG) {
                values_EMG.addAll(recordedValues);
                this.signal = new Signal(Signal.SignalType.EMG, values_EMG);
            } else if (signalType == Signal.SignalType.EDA) {
                values_EDA.addAll(recordedValues);
                this.signal = new Signal(Signal.SignalType.EDA, values_EDA);
            }

        } catch (BITalinoException | Throwable ex) {
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
        return "Patient{" +
                "patient_id=" + patient_id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dob=" + dob +
                ", email='" + email + '\'' +
                ", Signal=" + signal +
                ", hospital_id=" + hospital_id +
                ", symptoms=" + symptoms +
                ", values EMG=" + values_EMG +
                ", values EDA=" + values_EDA +
                '}';
    }

    }
