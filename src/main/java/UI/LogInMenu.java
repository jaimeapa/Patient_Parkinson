package UI;

public class LogInMenu {
    public static void main(String[] args) {
        switch(printMenu()){
            case 1:{
                Utilities.registerPatient();
                break;
            }
            case 2:{
                break;
            }
            case 3:{
                break;
            }

        }
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
