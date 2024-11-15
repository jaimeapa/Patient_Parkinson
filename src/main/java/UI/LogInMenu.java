package UI;

import Pojos.Patient;
import sendData.*;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LogInMenu {
    public static void main(String[] args) throws IOException {
        //Patient patient = null;
        Socket socket = new Socket("localhost", 8000);
        SendDataViaNetwork.sendInt(1,socket);
        while(true){
            switch (printMenu()) {
                case 1 : {
                    registerPatient(socket);
                    break;
                }
                case 2 :{
                    logInMenu(socket);
                    break;
                }
                case 3 :{
                    System.out.println("Exiting...");
                    releaseSocket(socket);
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

    private static int printMenu() {
        System.out.println("Patient Menu:\n"
                + "\n1. Register"
                + "\n2. Log In"
                + "\n3. Exit"
        );
        return Utilities.readInteger("What would you want to do?");
    }

    public static void registerPatient(Socket socket)
    {
        Patient patient = null;
        String name = Utilities.readString("Enter your name: ");
        String surname = Utilities.readString("Enter your last name: ");
        LocalDate dob = Utilities.readDate("Enter your date of birth: ");
        String email = Utilities.readString("Enter your email: ");
        patient = new Patient(name,surname,dob,email);
        SendDataViaNetwork.sendPatient(patient, socket);
    }
    private static void releaseSocket(Socket socket){
        try {
            socket.close();
        } catch (IOException ex) {
            //Logger.getLogger(SendBinaryDataViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }
}

