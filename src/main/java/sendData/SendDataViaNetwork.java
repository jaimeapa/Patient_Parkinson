package sendData;

import java.io.*;
import java.net.Socket;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

import Pojos.Interpretation;
import Pojos.Patient;
import Pojos.Signal;
import Pojos.User;

public class SendDataViaNetwork {

    public static void sendStrings(String message, Socket socket) throws IOException {

        //System.out.println("Connection established... sending text");
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(message);
        //releaseResourcesForString(printWriter,socket);

    }
    public static void sendInt(Integer message,  Socket socket) throws IOException{
        //OutputStream outputStream = socket.getOutputStream();
        //DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        try{
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeInt(message);
        }catch (IOException ex){
            ex.printStackTrace();
        }

        //releaseResourcesInt(dataOutputStream,outputStream);
    }

    public static Patient logIn(String email, String password, Socket socket) throws IOException
    {
        Patient patient = null;
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(email);
        dataOutputStream.writeUTF(password);
        return patient;

        //Método no terminado
    }

    public static void sendPatient(Patient patient, Socket socket)
    {
        //OutputStream outputStream = null;
        //ObjectOutputStream objectOutputStream = null;

        /*try {
            //socket = new Socket("localhost", 8080);
            outputStream = socket.getOutputStream();
        } catch (IOException ex) {
            System.out.println("It was not possible to connect to the server.");
            System.exit(-1);
            Logger.getLogger(SendDataViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        try {
            DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
            dataOutputStream.writeInt(patient.getPatient_id());
            dataOutputStream.writeUTF(patient.getName());
            dataOutputStream.writeUTF(patient.getSurname());
            dataOutputStream.writeUTF(patient.getDob().toString());
            dataOutputStream.writeUTF(patient.getEmail());

        } catch (IOException ex) {
            System.out.println("Unable to write the objects on the server.");
            Logger.getLogger(SendDataViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        } /*finally {
            releaseResourcesForPatient(objectOutputStream);

        }*/

    }

    public static void sendInterpretation(Interpretation interpretation, Socket socket) throws IOException{
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(interpretation.getDate().toString());
        dataOutputStream.writeInt(interpretation.getDoctor_id());
        dataOutputStream.writeUTF(interpretation.getSignalEMG().valuesToString());
        dataOutputStream.writeInt(interpretation.getPatient_id());
        dataOutputStream.writeUTF(interpretation.getSignalEDA().valuesToString());
        dataOutputStream.writeUTF(interpretation.getObservation());
        dataOutputStream.writeUTF(interpretation.getInterpretation());
    }
    public static void sendUser(User u, Socket socket) throws IOException
    {
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeUTF(u.getEmail());
        byte[] password = u.getPassword();

        dataOutputStream.writeUTF(new String(password));
        dataOutputStream.writeUTF(u.getRole().toString());
    }




    private static void releaseResources(DataOutputStream dataOutputStream){
        try {
            dataOutputStream.close();
        } catch (IOException ex) {
            //Logger.getLogger(SendBinaryDataViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
    }

}
