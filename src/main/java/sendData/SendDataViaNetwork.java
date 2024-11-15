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
    public static void sendInt(Integer message, Socket socket) throws IOException{
        OutputStream outputStream = socket.getOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        dataOutputStream.writeInt(message);
        releaseResourcesInt(dataOutputStream,outputStream);
    }

    public static Patient logIn(String email, String password, Socket socket) throws IOException
    {
        Patient patient = null;
        //Socket socket = new Socket("10.60.104.200", 8080);
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        InputStream inputStream = socket.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        printWriter.println(email);
        printWriter.println(password);
        printWriter.println("stop");

        if(!dataInputStream.readBoolean()){
            return null;
        }
        else{

            return patient;
        }
        //Método no terminado
    }

    public static void sendPatient(Patient patient, Socket socket)
    {
        OutputStream outputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try {
            //socket = new Socket("localhost", 8080);
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
            releaseResourcesForPatient(objectOutputStream);

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
    private static void releaseResourcesInt(DataOutputStream dataOutputStream, OutputStream outputStream){
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
    }
    private static void releaseResourcesForPatient(ObjectOutputStream objectOutputStream) {
        try {
            objectOutputStream.close();
        } catch (IOException ex) {
            Logger.getLogger(SendDataViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
