package UI;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.time.DateTimeException;
import java.time.LocalDate;

import Pojos.Patient;

public class Utilities {
    private static InputStreamReader input = new InputStreamReader(System.in);
    private static BufferedReader buffer = new BufferedReader(input);

    public static int readInteger(String question) {

        int num;
        String line;
        while (true) {
            try {
                System.out.print(question);
                line = buffer.readLine();
                num = Integer.parseInt(line);
                return num;

            } catch (IOException ioe) {
                System.out.println(" ERROR: Unable to read.");

            } catch (NumberFormatException nfe) {
                System.out.println(" ERROR: Must be a whole number.");
            }
        }
    }
    public static LocalDate readDate(String question)  {

        while (true) {
            try {
                System.out.println(question);
                int day = readInteger("   Day: ");
                int month = readInteger("   Month: ");
                int year = readInteger("   Year: ");
                return LocalDate.of(year, month, day);

            } catch (DateTimeException e) {
                System.out.println(" ERROR: Introduce a valid date.");
            }

        }
    }
    public static String readString(String question) {

        String line;
        while (true) {
            try {
                System.out.print(question);
                line = buffer.readLine();
                return line;

            } catch (IOException ioe) {
                System.out.println(" ERROR: Unable to read.");
            }
        }
    }
    public static Patient registerPatientData()
    {
        Patient patient = null;


        return patient;
    }



}
