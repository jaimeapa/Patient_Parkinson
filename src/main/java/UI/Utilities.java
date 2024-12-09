package UI;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.rmi.NotBoundException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Pojos.Patient;

public class Utilities {

    public static int readInteger(String question) {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(input);
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
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader buffer = new BufferedReader(input);
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
    public static boolean checkEmail(String email){
        Pattern pattern = Pattern.compile("([a-z0-9]+(\\.?[a-z0-9])*)+@(([a-z]+)\\.([a-z]+))+");
        Matcher mather = pattern.matcher(email);
        if (mather.find() == true) {
            return true;
        } else {
            System.out.println("Please follow the email format: example@example.com");
            return false;
        }
    }

    public static String formatMacAdress(String input) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < input.length(); i++) {
            result.append(input.charAt(i));
            // Agregar ':' después de cada dos caracteres, excepto al final
            if ((i + 1) % 2 == 0 && i < input.length() - 1) {
                result.append(":");
            }
        }
        return result.toString();
    }
    public static Patient registerPatientData()
    {
        Patient patient = null;


        return patient;
    }



}
