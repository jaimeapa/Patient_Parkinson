package UI;

import Pojos.Patient;
import sendData.*;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LogInMenu {
    private static ObjectOutputStream objectOutputStream;
    private static Socket socket;
    private static OutputStream outputStream;
    private static DataOutputStream dataOutputStream;
    private static ClientMenu clientMenu;
    private static PrintWriter printWriter;
    public static void main(String[] args) throws IOException {
        //Patient patient = null;
        socket = new Socket("localhost", 8000);

        dataOutputStream = new DataOutputStream(socket.getOutputStream());
        objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        outputStream = socket.getOutputStream();
        printWriter = new PrintWriter(socket.getOutputStream(), true);
        SendDataViaNetwork.sendInt(1, dataOutputStream);

        //SendDataViaNetwork.sendInt(1,socket, dataOutputStream);
        while(true){
            switch (printLogInMenu()) {
                case 1 : {
                    SendDataViaNetwork.sendInt(1, dataOutputStream);
                    registerPatient(socket);
                    break;
                }
                case 2 :{
                    logInMenu(socket);
                    break;
                }
                case 3 :{
                    System.out.println("Exiting...");
                    releaseResources(socket, dataOutputStream, outputStream, objectOutputStream);
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
        Patient patient = SendDataViaNetwork.logIn(email, password, socket);

        if (patient != null) {
            System.out.println("Login successful!");
            ClientMenu.clientMenu(patient);
            // Redirigir a la siguiente parte de la aplicación
        } else {
            System.out.println("Invalid email or password.");
        }
    }

    private static int printLogInMenu() {
        System.out.println("Patient Menu:\n"
                + "\n1. Register"
                + "\n2. Log In"
                + "\n3. Exit"
        );
        return Utilities.readInteger("What would you want to do?");
    }

    public static void registerPatient(Socket socket) throws IOException
    {
        Patient patient = null;
        String name = Utilities.readString("Enter your name: ");
        String surname = Utilities.readString("Enter your last name: ");
        LocalDate dob = Utilities.readDate("Enter your date of birth: ");
        String email = Utilities.readString("Enter your email: ");
        patient = new Patient(name,surname,dob,email);
        //System.out.println(patient.toString());
        SendDataViaNetwork.sendPatient(patient, objectOutputStream);
        clientMenu(patient);

    }

    public static void clientMenu(Patient patient_logedIn) throws IOException {
        Patient patient = patient_logedIn;
        boolean menu = true;
        while(menu){
            switch(printClientMenu()){
                case 1:{
                    SendDataViaNetwork.sendInt(1, dataOutputStream);
                    break;
                }
                case 2:{
                    break;
                }
                case 3:{
                    switch(bitalinoMenu())
                    {
                        case 1:
                        {
                            break;
                        }
                        case 2:
                        {
                            break;
                        }
                    }
                    break;
                }
                case 4:{

                    break;
                }
                case 5:{
                    menu = false;
                    break;
                }

            }
        }
    }

    private static int bitalinoMenu(){
        System.out.println("Possible measurements\n"
                + "\n1. EMG"
                + "\n2. EDA"
        );
        return Utilities.readInteger("What do you want to measure?");
    }
    private static int printClientMenu(){
        System.out.println("Diagnosis Menu:\n"
                + "\n1. Register personal data"
                + "\n2. Input your symptoms"
                + "\n3. Record Signal with BITalino"
                + "\n4. See your data"
                + "\n5. Log out"
        );
        return Utilities.readInteger("What would you want to do?");
    }


    private static void releaseResources(Socket socket, DataOutputStream dataOutputStream, OutputStream outputStream, ObjectOutputStream objectOutputStream){
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
            socket.close();
        } catch (IOException ex) {
            //Logger.getLogger(SendBinaryDataViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
}

