package UI;

import sendData.*;


public class LogInMenu {
    public static void main(String[] args) {
        switch(printMenu()){
            case 1:{
                Utilities.registerPatient();

                break;
            }
            case 2:{
                logInMenu();
                break;
            }
            case 3:{
                break;
            }

        }
    }

    private static void logInMenu()
    {
        String email = Utilities.readString("Email: ");
        String password = Utilities.readString("Password: ");

    }
    private static int printMenu(){
        System.out.println("Patient Menu:\n"
                + "\n1. Register"
                + "\n2. Log In"
                + "\n3. Exit"
        );
        return Utilities.readInteger("What would you want to do?");
    }
}
