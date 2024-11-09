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
    private int hospital_id;
    private LinkedList<String> symptoms;
    private LinkedList<Integer> values;

    public Patient(int patient_id, String name, String surname, LocalDate dob, String email, int hospital_id, LinkedList<String> symptoms) {
        this.patient_id = patient_id;
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.email = email;
        this.hospital_id = hospital_id;
        this.symptoms = symptoms;
        this.values = new LinkedList<Integer>();
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

    public int getHospital_id() {
        return hospital_id;
    }

    public void setHospital_id(int hospital_id) {
        this.hospital_id = hospital_id;
    }

    public LinkedList<String> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(LinkedList<String> symptoms) {
        this.symptoms = symptoms;
    }

    public LinkedList<Integer> getValues() {
        return values;
    }

    public void setValues(LinkedList<Integer> values) {
        this.values = values;
    }
    public File almacenarDatosEnFichero() throws FileNotFoundException, FileNotFoundException {

        Date date = (Date) java.sql.Date.valueOf(LocalDate.now());
        File file = new File("MeasurementsBitalino\\" + name + "_" + surname + "-" + date + ".txt");

        PrintWriter pw = new PrintWriter(file);
        pw.println("Patient: " + toString());

        pw.println(" -Bitalino recorded data: " );
        for(Integer v: values) {
            pw.println("   " + v);
        }
        pw.close();

        return file;
    }

    public void recordBitalinoData(int seconds, String macAddress) {

        //String macAddress = "20:17:11:20:51:27";
        BITalino bitalino = new BITalino();
        try {
            //Una vez encontrado por bluettoth el dispositivo, se imprimime
            Vector<RemoteDevice> devices = bitalino.findDevices();
            System.out.println(devices);

            //Establecemos el numero de muestras que queremos por segundo
            int samplingRate = 10;
            bitalino.open(macAddress, samplingRate);

            //Establecemos el canal por el que se obtiene la señal EMG (A1)
            int[] channelsToAcquire = {0};
            bitalino.start(channelsToAcquire);

            System.out.println(" -Recording signal...");
            //Imprimimos datos por pantalla y los almacenamos en una variable local (lista de valores)
            for (int j=0; j<10; j++) {
                //Each time reads a block of 10 samples
                int blockSize = samplingRate;
                Frame[] frames = bitalino.read(blockSize);
                System.out.println("   Size blocks: " + (j+1) + "/" + frames.length);
                for (int i=0; i<frames.length; i++) {
                    System.out.println("    "+ (j * blockSize + i) + " seq: " + frames[i].seq + " "
                            + frames[i].analog[0] + " ");
                    values.add(frames[i].analog[0]);
                }
            }
            bitalino.stop();
            //Creamos una nueva señal con los valores y un nombre para el fichero con la señal EMG
            this.signal = new Signal(values, this.name + "_" + this.surname);

        } catch (BITalinoException ex) {
            ex.printStackTrace();
        } catch (Throwable ex) {
            ex.printStackTrace();
        } finally {
            try {
                //Connection stops when we disconnect the bluetooth
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
                ", values=" + values +
                '}';
    }
}
