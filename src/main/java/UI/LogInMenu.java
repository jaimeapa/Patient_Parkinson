package UI;

import BITalino.BITalinoException;
import Encryption.EncryptPassword;
import Pojos.*;
import sendData.*;

import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;
import java.net.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LogInMenu {
    private static Socket socket;
    /*private static DataOutputStream dataOutputStream;
    private static DataInputStream dataInputStream;
    private static Role role;*/

    public static void main(String[] args){
        while(true) {
            String ipAdress = Utilities.readString("Write the IP address of the server you want to connect to:\n");
            try {
                socket = new Socket("localhost", 8000);
                SendDataViaNetwork sendDataViaNetwork = new SendDataViaNetwork(socket);
                sendDataViaNetwork.sendInt(1, socket);

                Role role = new Role("patient");
                while (true) {
                    switch (printLogInMenu()) {
                        case 1: {
                            sendDataViaNetwork.sendInt(1, socket);
                            registerPatient();
                            break;
                        }
                        case 2: {
                            sendDataViaNetwork.sendInt(2, socket);
                            logInMenu();
                            break;
                        }
                        case 3: {
                            exitProgram();
                        }
                        default: {
                            System.out.println("That number is not an option, try again");
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Invalid IP Adress");
            }
        }
    }
    private static int printLogInMenu() {
        System.out.println("\n\nPatient Menu:\n"
                + "\n1. Register"
                + "\n2. Log In"
                + "\n3. Exit"
        );
        return Utilities.readInteger("What would you want to do?\n");
    }

    public static void clientPatientMenu(Patient patient_logedIn, Doctor assignedDoctor) throws IOException {
        Patient patient = patient_logedIn;
        LocalDate date = LocalDate.now();
        Interpretation interpretation = new Interpretation(date, patient_logedIn.getPatient_id(), assignedDoctor.getDoctor_id());
        boolean menu = true;
        while(menu){
            switch(printClientMenu()){
                case 1:{
                    readSymptoms(interpretation);
                    break;
                }

                case 2:{
                    readBITalino(interpretation);
                    break;
                }
                case 3:{
                    System.out.println(patient.toString());
                    break;
                }
                case 4:{
                    seeInterpretations();
                    break;
                }
                case 5:{
                    menu = false;
                    sendInterpretationAndLogOut(interpretation);
                    break;
                }
            }
        }
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

    private static void logInMenu() throws IOException{
        String email = Utilities.readString("Email: ");
        String psw = Utilities.readString("Password: ");
        byte[] password = null;
        Role role = new Role("patient");
        try {
            password = EncryptPassword.encryptPassword(psw);
        }catch(NoSuchAlgorithmException e){
            System.out.println("Error when encrypting the password");
            password = null;
        }
        if(password != null) {
            //Patient patient = SendDataViaNetwork.logIn(email, password, socket);
            User u = new User(email, password, role);
            SendDataViaNetwork.sendUser(u, socket);
            try {
                Patient patient = ReceiveDataViaNetwork.recievePatient(socket);
                if (patient != null) {
                    if (patient.getName().equals("name")) {
                        System.out.println("User or password is incorrect");
                    } else {
                        Doctor doctor = ReceiveDataViaNetwork.receiveDoctor(socket);
                        System.out.println("Log in successful");
                        System.out.println(patient.toString());
                        clientPatientMenu(patient, doctor);
                    }
                }
            } catch (IOException e) {
                System.out.println("Log in problem");
            }
        }

    }
    public static void registerPatient() throws IOException
    {
        Patient patient;
        Role role = new Role("patient");
        User u;
        String name;
        String surname;
        LocalDate dob;
        String email;
        byte[] password;
        name = Utilities.readString("Enter your name: ");
        surname = Utilities.readString("Enter your last name: ");
        dob = Utilities.readDate("Enter your date of birth: ");
        do {
            email = Utilities.readString("Enter your email: ");
        }while(!Utilities.checkEmail(email));

        patient = new Patient(name,surname,dob,email);
        String psw = Utilities.readString("Enter your password: ");
        try {
            password = EncryptPassword.encryptPassword(psw);
        }catch(NoSuchAlgorithmException e){
            System.out.println("Error when encrypting the password");
            password = null;
        }
        if(password != null) {
            u = new User(email, password, role);
            SendDataViaNetwork.sendPatient(patient, socket);
            SendDataViaNetwork.sendUser(u, socket);
            String message = ReceiveDataViaNetwork.receiveString(socket);
            patient = ReceiveDataViaNetwork.recievePatient(socket);
            System.out.println(message);
            if (message.equals("ERROR")) {
                System.out.println("\n\nThere are no available doctors, sorry for the inconvinience");
            } else {
                Doctor doctor = ReceiveDataViaNetwork.receiveDoctor(socket);
                System.out.println("Your doctor is: " + doctor.getName());
                clientPatientMenu(patient, doctor);
            }
        }
    }
    private static void readSymptoms(Interpretation interpretation) throws  IOException{
        SendDataViaNetwork.sendInt(1, socket);
        LinkedList<String> symptomsInTable = new LinkedList<>();
        System.out.println("\n\nUsual Symptoms for Parkinson: \n\n");
        String message = "";
        int i = 1;
        while(!message.equals("stop")){
            message = ReceiveDataViaNetwork.receiveString(socket);
            if(!message.equals("stop")){
                System.out.println(i+ ". " + message);
                symptomsInTable.add(message);
                i++;
            }

        }
        System.out.println(i+ ". Other symptoms\n");
        System.out.println("Input the numbers of your symptoms. Write 0 to exit: \n");
        System.out.println(ReceiveDataViaNetwork.receiveString(socket));

        int symptomId;
        boolean mandarDatos = true;
        Symptoms symptomAdded = null;
        LinkedList<Integer> alreadySendId = new LinkedList<>();
        while (mandarDatos) {
            symptomId = Utilities.readInteger("Insert a number: ");
            if (symptomId == 0) {
                mandarDatos = false;
                SendDataViaNetwork.sendInt(symptomId, socket);
            } else if (alreadySendId.contains(symptomId)) {
                System.out.println("You already selected that symptom!");
            }else if (symptomId > 0 && symptomId < i) {
                SendDataViaNetwork.sendInt(symptomId, socket);
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
                interpretation.setObservation(Utilities.readString("Write how you are feeling:\n"));
                break;
            } else if (Utilities.readString("Would you like to add a more detailed description of your symptoms?[yes/no]").equalsIgnoreCase("no")) {
                System.out.println("Noted!");
                break;
            }else{
                System.out.println("Please, write yes or no");
            }
        }
        System.out.println(ReceiveDataViaNetwork.receiveString(socket));
    }

    private static void readBITalino(Interpretation interpretation) throws IOException{
        SendDataViaNetwork.sendInt(2, socket);
        int seconds = Utilities.readInteger("How many seconds will you like to measure your signals?");
        try {
            interpretation.recordBitalinoData(seconds, "20:18:06:13:01:08");
        }catch(BITalinoException e){
            System.out.println("Error al medir ");
        }
    }
    private static void exitProgram() throws IOException{
        System.out.println("Exiting...");
        SendDataViaNetwork.sendInt(3, socket);
        releaseResources(socket);
        System.exit(0);
    }
    private static void sendInterpretationAndLogOut(Interpretation interpretation) throws IOException{
        SendDataViaNetwork.sendInt(5, socket);
        System.out.println("Sending your data to the server...");
        System.out.println(interpretation.toString());
        SendDataViaNetwork.sendInterpretation(interpretation, socket);
        if(ReceiveDataViaNetwork.receiveString(socket).equals("OK")){
            System.out.println("Data recieved by the server!");
        }else{
            System.out.println("Data was not recieved correctly");
        }

    }
    private static void seeInterpretations() throws IOException{
        SendDataViaNetwork.sendInt(4, socket);
        int length = ReceiveDataViaNetwork.receiveInt(socket);
        LinkedList<Interpretation> allInterpretations = new LinkedList<>();
        String symptomName;
        int lengthSymptoms;
        for(int i = 0; i < length; i++){
            Interpretation recievedInterpretation = ReceiveDataViaNetwork.recieveInterpretation(socket);
            allInterpretations.add(recievedInterpretation);
            System.out.println(i + 1 + ". " + recievedInterpretation.getDate().toString());
            lengthSymptoms = ReceiveDataViaNetwork.receiveInt(socket);
            if(lengthSymptoms != 0) {
                for (int j = 0; j < lengthSymptoms; j++) {
                    symptomName = ReceiveDataViaNetwork.receiveString(socket);
                    allInterpretations.get(i).addSymptom(new Symptoms(symptomName));
                }
            }
        }
        SendDataViaNetwork.sendStrings("RECEIVED", socket);
        while(true) {
            int option = Utilities.readInteger("Choose the number of the report you want to see: \n");
            if (option - 1 >= length) {
                System.out.println("That number is not an option, choose one on the list [1 - " + length + "]: \n");
            } else if (allInterpretations.get(option - 1) != null) {
                System.out.println(allInterpretations.get(option - 1));
                break;
            }
        }
    }

    private static void releaseResources(Socket socket){


        try {
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}

