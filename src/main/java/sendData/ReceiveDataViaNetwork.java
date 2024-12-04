package sendData;

import Pojos.*;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReceiveDataViaNetwork {

    public static String receiveString(DataInputStream dataInputStream) throws IOException {

        //String line = "";
        String information = "";
        information = dataInputStream.readUTF();
        //System.out.println(information);
        return information;
    }

    public static Doctor receiveDoctor(DataInputStream dataInputStream){
        //InputStream inputStream = null;
        //ObjectInputStream objectInputStream = null;
        Doctor doctor = null;

        try {
            //Object tmp;
            int id = dataInputStream.readInt();
            String name = dataInputStream.readUTF();
            String surname = dataInputStream.readUTF();
            String date = dataInputStream.readUTF();
            String email = dataInputStream.readUTF();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dob = LocalDate.parse(date, formatter);
            doctor = new Doctor(id,name,surname,dob,email);

            //patient = (Patient) objectInputStream.readObject();
        } catch (EOFException ex) {
            System.out.println("All data have been correctly read.");
        } catch (IOException  ex) {
            System.out.println("Unable to read from the client.");
            ex.printStackTrace();
            //Logger.getLogger(ReceiveClientViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(doctor != null){
            System.out.println(doctor.toString()   );
        }
        return doctor;
    }

    public static Patient recievePatient(DataInputStream dataInputStream){
        //InputStream inputStream = null;
        //ObjectInputStream objectInputStream = null;
        Patient patient = null;

        try {
            //Object tmp;
            int id = dataInputStream.readInt();
            String name = dataInputStream.readUTF();
            String surname = dataInputStream.readUTF();
            String date = dataInputStream.readUTF();
            String email = dataInputStream.readUTF();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate dob = LocalDate.parse(date, formatter);
            patient = new Patient(id,name,surname,dob,email);

            //patient = (Patient) objectInputStream.readObject();
        } catch (EOFException ex) {
            System.out.println("All data have been correctly read.");
        } catch (IOException  ex) {
            System.out.println("Unable to read from the client.");
            ex.printStackTrace();
            //Logger.getLogger(ReceiveClientViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(patient != null){
            System.out.println(patient.toString()   );
        }
        return patient;
    }
    public static Interpretation recieveInterpretation(DataInputStream dataInputStream){
        //InputStream inputStream = null;
        //ObjectInputStream objectInputStream = null;
        Interpretation interpretation = null;

        try {
            //Object tmp;
            String stringDate = dataInputStream.readUTF();
            int doctor_id = dataInputStream.readInt();
            String stringEMG = dataInputStream.readUTF();
            int patient_id = dataInputStream.readInt();
            String stringEDA = dataInputStream.readUTF();
            String observation = dataInputStream.readUTF();
            String interpretation1 = dataInputStream.readUTF();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(stringDate, formatter);
            Signal signalEMG = new Signal(Signal.SignalType.EMG);
            signalEMG.setValuesEMG(stringEMG);
            Signal signalEDA = new Signal(Signal.SignalType.EDA);
            signalEDA.setValuesEDA(stringEDA);
            interpretation = new Interpretation(date, interpretation1, signalEMG, signalEDA, patient_id, doctor_id, observation);

            //patient = (Patient) objectInputStream.readObject();
        } catch (EOFException ex) {
            System.out.println("All data have been correctly read.");
        } catch (IOException  ex) {
            System.out.println("Unable to read from the client.");
            ex.printStackTrace();
            //Logger.getLogger(ReceiveClientViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }

        return interpretation;
    }

    public static int receiveInt(DataInputStream dataInputStream) throws IOException{
        //InputStream inputStream = socket.getInputStream();
        //DataInputStream dataInputStream = new DataInputStream(inputStream);
        int message = 10;
        try{
            message = dataInputStream.readInt();
        }catch(IOException ex){
            ex.printStackTrace();
        }

        return message;
    }

    public static User recieveUser(DataInputStream dataInputStream)
    {
        User u = null;
        try{
            String email = dataInputStream.readUTF();
            byte[] psw = dataInputStream.readUTF().getBytes();
            String role = dataInputStream.readUTF();
            Role r = new Role(role);
            u = new User(email,psw,r);
        }catch (IOException e){
            e.printStackTrace();
        }
        return u;
    }

    private static void releaseResources2(DataInputStream dataInputStream){
        try {
            dataInputStream.close();
        } catch (IOException ex) {
            //Logger.getLogger(ReceiveBinaryDataViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

    }
    private static void releaseResources(BufferedReader bufferedReader) {
        try {
            bufferedReader.close();
        } catch (IOException ex) {
            Logger.getLogger(ReceiveDataViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static void releasePatientResources(ObjectInputStream objectInputStream){
        try {
            objectInputStream.close();
        } catch (IOException ex) {
            //Logger.getLogger(ReceiveClientViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
}