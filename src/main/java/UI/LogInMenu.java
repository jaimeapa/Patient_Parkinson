package UI;

import sendData.*;
import java.sql.*;

public class LogInMenu {
    public static void main(String[] args) {
        switch (printMenu()) {
            case 1 -> Utilities.registerPatient();
            case 2 -> logInMenu();
            case 3 -> System.out.println("Exiting...");
        }
    }

    private static void logInMenu() {
        String email = Utilities.readString("Email: ");
        String password = Utilities.readString("Password: ");

        if (Utilities.verifyCredentials(email, password)) {
            System.out.println("Login successful!");
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
}

