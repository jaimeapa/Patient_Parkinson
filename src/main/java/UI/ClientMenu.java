package UI;

import Pojos.Patient;


public class ClientMenu {
    private static Patient patient;

    public static void clientMenu(Patient patient_logedIn){
        patient = patient_logedIn;
        while(true){
            switch(printMenu()){
                case 1:{

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
private static int printMenu(){
    System.out.println("Diagnosis Menu:\n"
            + "\n1. Register personal data"
            + "\n2. Input your symptoms"
            + "\n3. Record Signal with BITalino"
            + "\n4. See your data"
            + "\n5. Log out"
    );
    return Utilities.readInteger("What would you want to do?");
}
}
