package UI;

import BITalino.BITalinoException;
import Pojos.*;
import sendData.*;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.net.*;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LogInMenu {
    private static ObjectOutputStream objectOutputStream;
    private static Socket socket;
    private static OutputStream outputStream;
    private static DataOutputStream dataOutputStream;
    private static PrintWriter printWriter;
    private static BufferedReader bufferedReader;
    private static DataInputStream dataInputStream;
    private static ObjectInputStream objectInputStream;
    private static Role role;

    public static void main(String[] args) throws IOException {
        //Patient patient = null;
        socket = new Socket("localhost", 8000);

        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream = socket.getOutputStream();
        bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        //InputStream inputStream = socket.getInputStream();
        dataInputStream = new DataInputStream(socket.getInputStream());
        objectInputStream = new ObjectInputStream(socket.getInputStream());

        SendDataViaNetwork.sendInt(1, dataOutputStream);
        role = new Role("patient");
        //SendDataViaNetwork.sendInt(1,socket, dataOutputStream);
        while(true){
            switch (printLogInMenu()) {
                case 1 : {
                    SendDataViaNetwork.sendInt(1, dataOutputStream);
                    registerPatient(socket);
                    break;
                }
                case 2 :{
                    SendDataViaNetwork.sendInt(2, dataOutputStream);
                    logInMenu(socket);
                    break;
                }
                case 3 :{
                    System.out.println("Exiting...");
                    SendDataViaNetwork.sendInt(3, dataOutputStream);
                    releaseResources(socket, dataOutputStream, outputStream, objectOutputStream, dataInputStream, objectInputStream, bufferedReader, printWriter);
                    System.exit(0);
                }
                default:{
                    System.out.println("That number is not an option, try again");
                    break;
                }
            }
        }


    }

    private static void logInMenu(Socket socket) throws IOException{
        String email = Utilities.readString("Email: ");
        String password = Utilities.readString("Password: ");
        //Patient patient = SendDataViaNetwork.logIn(email, password, socket);
        User u = new User (email,password.getBytes(), role);
        SendDataViaNetwork.sendUser(u, dataOutputStream);
        try {
            Patient patient = ReceiveDataViaNetwork.recievePatient(dataInputStream);
            if (patient != null) {
                if(patient.getName().equals("name")){
                    System.out.println("User or password is incorrect");
                }else {
                    Doctor doctor = ReceiveDataViaNetwork.receiveDoctor(dataInputStream);
                    System.out.println("Log in successful");
                    System.out.println(patient.toString());
                    clientPatientMenu(patient, doctor);
                }
            }
        }catch(IOException e){
            System.out.println("Log in problem");
        }
        /*if (patient != null) {
            System.out.println("Login successful!");
            ClientMenu.clientMenu(patient);
            // Redirigir a la siguiente parte de la aplicación
        } else {
            System.out.println("Invalid email or password.");
        }*/
    }

    private static int printLogInMenu() {
        System.out.println("\n\nPatient Menu:\n"
                + "\n1. Register"
                + "\n2. Log In"
                + "\n3. Exit"
        );
        return Utilities.readInteger("What would you want to do?\n");
    }

    public static void registerPatient(Socket socket) throws IOException
    {
        Patient patient = null;
        User u = null;
        String name;
        String surname;
        LocalDate dob;
        String email;
        String password;
        name = Utilities.readString("Enter your name: ");
        surname = Utilities.readString("Enter your last name: ");
        dob = Utilities.readDate("Enter your date of birth: ");
        System.out.println(dob.toString());
        do {
            email = Utilities.readString("Enter your email: ");
        }while(!Utilities.checkEmail(email));

        patient = new Patient(name,surname,dob,email);
        password = Utilities.readString("Enter your password: ");
        u = new User(email, password.getBytes(), role);
        //System.out.println(patient.toString());
        SendDataViaNetwork.sendPatient(patient, dataOutputStream);
        SendDataViaNetwork.sendUser(u, dataOutputStream);
        String message = ReceiveDataViaNetwork.receiveString(bufferedReader);
        patient = ReceiveDataViaNetwork.recievePatient(dataInputStream);
        if(message.equals("ERROR")){
            System.out.println("\n\nThere are no available doctors, sorry for the inconvinience");
        }else {
            Doctor doctor = ReceiveDataViaNetwork.receiveDoctor(dataInputStream);
            clientPatientMenu(patient, doctor);
        }
    }

    public static void clientPatientMenu(Patient patient_logedIn, Doctor assignedDoctor) throws IOException {
        Patient patient = patient_logedIn;
        LocalDate date = LocalDate.now();
        Interpretation interpretation = new Interpretation(date, patient_logedIn.getPatient_id(), assignedDoctor.getDoctor_id());
        boolean menu = true;
        while(menu){
            switch(printClientMenu()){
                case 1:{
                    readSymptoms(patient_logedIn, interpretation);
                    break;
                }

                case 2:{
                    readBITalino(patient_logedIn, interpretation);
                    break;
                }
                case 3:{
                    System.out.println(patient.toString());
                    break;
                }
                case 4:{
                    SendDataViaNetwork.sendInt(4, dataOutputStream);
                    break;
                }
                case 5:{
                    menu = false;
                    SendDataViaNetwork.sendInt(4, dataOutputStream);
                    System.out.println("Sending your data to the server...");
                    SendDataViaNetwork.sendInterpretation(interpretation,dataOutputStream);
                    if(ReceiveDataViaNetwork.receiveString(bufferedReader).equals("OK")){
                        System.out.println("Data recieved by the server!");
                    }else{
                        System.out.println("Data was not recieved correctly");
                    }

                    System.out.println("Closing the server...");
                    break;
                }
            }
        }
    }

    private static int bitalinoMenu(){
        System.out.println("\n\nPossible measurements\n"
                + "\n1. EMG"
                + "\n2. EDA"
        );
        return Utilities.readInteger("What do you want to measure?\n");
    }
    private static int printClientMenu(){
        System.out.println("\n\nDiagnosis Menu:\n"
                + "\n1. Input your symptoms"
                + "\n2. Record Signal with BITalino"
                + "\n3. See your data"
                + "\n4. See your reports"
                + "\n5. Log out"
        );
        return Utilities.readInteger("What would you want to do?\n");
    }


    private static void releaseResources(Socket socket, DataOutputStream dataOutputStream, OutputStream outputStream, ObjectOutputStream objectOutputStream, DataInputStream dataInputStream, ObjectInputStream objectInputStream, BufferedReader bufferedReader, PrintWriter printWriter){
        try {
            objectOutputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(SendDataViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            dataOutputStream.close();
        } catch (IOException ex) {
            //Logger.getLogger(SendBinaryDataViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        try {
            outputStream.close();
        } catch (IOException ex) {
            // Logger.getLogger(SendBinaryDataViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        try {
            objectInputStream.close();
        } catch (IOException ex) {
            //Logger.getLogger(ReceiveClientViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        try {
            dataInputStream.close();
        } catch (IOException ex) {
            //Logger.getLogger(ReceiveBinaryDataViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        try {
            bufferedReader.close();
        } catch (IOException ex) {
            //Logger.getLogger(ReceiveStringsViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
        printWriter.close();
        try {
            socket.close();
        } catch (IOException ex) {
            //Logger.getLogger(SendBinaryDataViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

    private static void readSymptoms(Patient patient_logedIn, Interpretation interpretation) throws  IOException{
        SendDataViaNetwork.sendInt(1, dataOutputStream);
        LinkedList<String> symptomsInTable = new LinkedList<>();
        System.out.println("\n\nUsual Symptoms for Parkinson: \n\n");
        String message = "";
        int i = 1;
        while(!message.equals("stop")){
            message = ReceiveDataViaNetwork.receiveString(bufferedReader);
            if(!message.equals("stop")){
                System.out.println(i+ ". " + message);
                symptomsInTable.add(message);
                i++;
            }

        }
        System.out.println(i+ ". Other symptoms\n");
        System.out.println("Input the numbers of your symptoms. Write 0 to exit: \n");
        System.out.println(ReceiveDataViaNetwork.receiveString(bufferedReader));

        int symptomId;
        boolean mandarDatos = true;
        Symptoms symptomAdded = null;
        LinkedList<Integer> alreadySendId = new LinkedList<>();
        while (mandarDatos) {
            symptomId = Utilities.readInteger("Insert a number: ");
            if (symptomId == 0) {
                mandarDatos = false;
                SendDataViaNetwork.sendInt(symptomId, dataOutputStream);
            } else if (alreadySendId.contains(symptomId)) {
                System.out.println("You already selected that symptom!");
            }else if (symptomId > 0 && symptomId < i) {
                SendDataViaNetwork.sendInt(symptomId, dataOutputStream);
                alreadySendId.add(symptomId);
                symptomAdded = new Symptoms(symptomsInTable.get(symptomId - 1));
                interpretation.addSymptom(symptomAdded);
            }else{
                System.out.println("There are no symptoms with that number!");
                System.out.println("Type the numbers corresponding to the symptoms you have (To stop adding symptoms type '0'): ");
            }
        }
        while(true) {
            if (Utilities.readString("Would you like to add a more detailed description of your symptoms?[yes/no]").equalsIgnoreCase("yes")) {
                //SendDataViaNetwork.sendStrings("ADDSYMPTOM", printWriter);
                interpretation.setObservation(Utilities.readString("Write how you are feeling:\n"));
                break;
            } else if (Utilities.readString("Would you like to add a more detailed description of your symptoms?[yes/no]").equalsIgnoreCase("no")) {
                System.out.println("Noted!");
                break;
            }else{
                System.out.println("Please, write yes or no");
            }
        }
        System.out.println(ReceiveDataViaNetwork.receiveString(bufferedReader));
    }

    private static void readBITalino(Patient patient_logedIn, Interpretation interpretation) throws IOException{
        SendDataViaNetwork.sendInt(2, dataOutputStream);
        switch(bitalinoMenu())
        {
            case 1:
            {
                try {
                    interpretation.recordBitalinoData(5, "20:18:06:13:01:08", Signal.SignalType.EMG);
                    //SendDataViaNetwork.sendData(patient_logedIn, Signal.SignalType.EMG,dataOutputStream);
                }catch(BITalinoException e){
                    System.out.println("Error al medir ");
                }
                break;
            }
            case 2:
            {
                try {
                    interpretation.recordBitalinoData(5, "20:18:06:13:01:08", Signal.SignalType.EDA);
                    //SendDataViaNetwork.sendData(patient_logedIn, Signal.SignalType.EDA,dataOutputStream);
                }catch(BITalinoException e){
                    System.out.println("Error al medir ");
                }
                break;
            }
        }
    }
}

