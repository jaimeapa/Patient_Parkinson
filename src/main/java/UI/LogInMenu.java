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
    //private static Socket socket;
    private static SendDataViaNetwork sendDataViaNetwork;
    private static ReceiveDataViaNetwork receiveDataViaNetwork;

    public static void main(String[] args){
        while(true) {
            String ipAdress = Utilities.readString("Write the IP address of the server you want to connect to:\n");
            try {
                Socket socket = new Socket(ipAdress, 8000);
                sendDataViaNetwork = new SendDataViaNetwork(socket);
                receiveDataViaNetwork = new ReceiveDataViaNetwork(socket);
                sendDataViaNetwork.sendInt(1);

                Role role = new Role("patient");
                while (true) {
                    switch (printLogInMenu()) {
                        case 1: {
                            sendDataViaNetwork.sendInt(1);
                            registerPatient();
                            break;
                        }
                        case 2: {
                            sendDataViaNetwork.sendInt(2);
                            logInMenu();
                            break;
                        }
                        case 3: {
                            exitProgram(socket);
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
            sendDataViaNetwork.sendUser(u);
            try {
                Patient patient = receiveDataViaNetwork.recievePatient();
                if (patient != null) {
                    if (patient.getName().equals("name")) {
                        System.out.println("User or password is incorrect");
                    } else {
                        Doctor doctor = receiveDataViaNetwork.receiveDoctor();
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
            sendDataViaNetwork.sendPatient(patient);
            sendDataViaNetwork.sendUser(u);
            String message = receiveDataViaNetwork.receiveString();
            patient = receiveDataViaNetwork.recievePatient();
            System.out.println(message);
            if (message.equals("ERROR")) {
                System.out.println("\n\nThere are no available doctors, sorry for the inconvinience");
            } else {
                Doctor doctor = receiveDataViaNetwork.receiveDoctor();
                System.out.println("Your doctor is: " + doctor.getName());
                clientPatientMenu(patient, doctor);
            }
        }
    }
    private static void readSymptoms(Interpretation interpretation) throws  IOException{
        sendDataViaNetwork.sendInt(1);
        LinkedList<String> symptomsInTable = new LinkedList<>();
        System.out.println("\n\nUsual Symptoms for Parkinson: \n\n");
        String message = "";
        int i = 1;
        while(!message.equals("stop")){
            message = receiveDataViaNetwork.receiveString();
            if(!message.equals("stop")){
                System.out.println(i+ ". " + message);
                symptomsInTable.add(message);
                i++;
            }

        }
        System.out.println(i+ ". Other symptoms\n");
        System.out.println("Input the numbers of your symptoms. Write 0 to exit: \n");
        System.out.println(receiveDataViaNetwork.receiveString());

        int symptomId;
        boolean mandarDatos = true;
        Symptoms symptomAdded = null;
        LinkedList<Integer> alreadySendId = new LinkedList<>();
        while (mandarDatos) {
            symptomId = Utilities.readInteger("Insert a number: ");
            if (symptomId == 0) {
                mandarDatos = false;
                sendDataViaNetwork.sendInt(symptomId);
            } else if (alreadySendId.contains(symptomId)) {
                System.out.println("You already selected that symptom!");
            }else if (symptomId > 0 && symptomId < i) {
                sendDataViaNetwork.sendInt(symptomId);
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
        System.out.println(receiveDataViaNetwork.receiveString());
    }

    private static void readBITalino(Interpretation interpretation) throws IOException{
        sendDataViaNetwork.sendInt(2);
        int seconds = Utilities.readInteger("How many seconds will you like to measure your signals?");
        try {
            interpretation.recordBitalinoData(seconds, "20:18:06:13:01:08");
        }catch(BITalinoException e){
            System.out.println("Error al medir ");
        }
    }
    private static void exitProgram(Socket socket) throws IOException{
        System.out.println("Exiting...");
        sendDataViaNetwork.sendInt(3);
        releaseResources(socket);
        System.exit(0);
    }
    private static void sendInterpretationAndLogOut(Interpretation interpretation) throws IOException{
        sendDataViaNetwork.sendInt(5);
        System.out.println("Sending your data to the server...");
        System.out.println(interpretation.toString());
        sendDataViaNetwork.sendInterpretation(interpretation);
        if(receiveDataViaNetwork.receiveString().equals("OK")){
            System.out.println("Data recieved by the server!");
        }else{
            System.out.println("Data was not recieved correctly");
        }

    }
    private static void seeInterpretations() throws IOException{
        sendDataViaNetwork.sendInt(4);
        int length = receiveDataViaNetwork.receiveInt();
        LinkedList<Interpretation> allInterpretations = new LinkedList<>();
        String symptomName;
        int lengthSymptoms;
        for(int i = 0; i < length; i++){
            Interpretation recievedInterpretation = receiveDataViaNetwork.recieveInterpretation();
            allInterpretations.add(recievedInterpretation);
            System.out.println(i + 1 + ". " + recievedInterpretation.getDate().toString());
            lengthSymptoms = receiveDataViaNetwork.receiveInt();
            if(lengthSymptoms != 0) {
                for (int j = 0; j < lengthSymptoms; j++) {
                    symptomName = receiveDataViaNetwork.receiveString();
                    allInterpretations.get(i).addSymptom(new Symptoms(symptomName));
                }
            }
        }
        sendDataViaNetwork.sendStrings("RECEIVED");
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
        sendDataViaNetwork.releaseResources();
        receiveDataViaNetwork.releaseResources();

        try {
            socket.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}

