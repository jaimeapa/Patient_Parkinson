package sendData;

import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import Pojos.Patient;

public class SendDataViaNetwork {

    public static void sendStrings(String message) throws IOException {
        Socket socket = new Socket("10.60.104.200", 8080);
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        System.out.println("Connection established... sending text");
        printWriter.println(message);
        System.out.println("Sending stop command");
        printWriter.println("stop");
        releaseResourcesForString(printWriter, socket);
        System.exit(0);
    }

    public static Patient logIn(String email, String password) throws IOException
    {
        Patient patient = null;
        Socket socket = new Socket("10.60.104.200", 8080);
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println(email);
        printWriter.println(password);
        printWriter.println("stop");
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        //Método no terminado
        return patient;
    }

    public static void sendPatient(Patient patient)
    {
        OutputStream outputStream = null;
        ObjectOutputStream objectOutputStream = null;
        Socket socket = null;

        try {
            socket = new Socket("10.60.104.200", 8080);
            outputStream = socket.getOutputStream();
        } catch (IOException ex) {
            System.out.println("It was not possible to connect to the server.");
            System.exit(-1);
            Logger.getLogger(SendDataViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(patient);
            objectOutputStream.flush();
        } catch (IOException ex) {
            System.out.println("Unable to write the objects on the server.");
            Logger.getLogger(SendDataViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            releaseResourcesForPatient(objectOutputStream, socket);

        }

    }

    private static void releaseResourcesForString(PrintWriter printWriter, Socket socket) {
        printWriter.close();
 
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(SendDataViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private static void releaseResourcesForPatient(ObjectOutputStream objectOutputStream, Socket socket) {
        try {
            objectOutputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(SendDataViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(SendDataViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
