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

    public static void sendStrings(String message, DataOutputStream dataOutputStream) throws IOException {

        //System.out.println("Connection established... sending text");
        dataOutputStream.writeUTF(message);
        //releaseResourcesForString(printWriter,socket);

    }
    public static void sendInt(Integer message,  DataOutputStream dataOutputStream) throws IOException{
        //OutputStream outputStream = socket.getOutputStream();
        //DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
        try{
            dataOutputStream.writeInt(message);
        }catch (IOException ex){
            ex.printStackTrace();
        }

        //releaseResourcesInt(dataOutputStream,outputStream);
    }

    public static Patient logIn(String email, String password, DataOutputStream dataOutputStream) throws IOException
    {
        Patient patient = null;
        //Socket socket = new Socket("10.60.104.200", 8080);
        /*PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        InputStream inputStream = socket.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));*/
        /*printWriter.println(email);
        printWriter.println(password);
        printWriter.println("stop");*/
        dataOutputStream.writeUTF(email);
        dataOutputStream.writeUTF(password);
        return patient;

        //Método no terminado
    }

    public static void sendPatient(Patient patient, DataOutputStream dataOutputStream)
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
            //objectOutputStream = new ObjectOutputStream(outputStream);
            /*objectOutputStream.writeObject(patient);
            objectOutputStream.flush();
            objectOutputStream.reset();*/
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

    public static void sendInterpretation(Interpretation interpretation, DataOutputStream dataOutputStream) throws IOException{
        dataOutputStream.writeUTF(interpretation.getDate().toString());
        dataOutputStream.writeInt(interpretation.getDoctor_id());
        dataOutputStream.writeInt(interpretation.getPatient_id());
        dataOutputStream.writeUTF(interpretation.getSignalEMG().valuesToString());
        dataOutputStream.writeUTF(interpretation.getSignalEDA().valuesToString());
        dataOutputStream.writeUTF(interpretation.getObservation());
    }
    public static void sendUser(User u, DataOutputStream dataOutputStream) throws IOException
    {
        dataOutputStream.writeUTF(u.getEmail());
        dataOutputStream.writeUTF(new String(u.getPassword()));
        dataOutputStream.writeUTF(u.getRole().toString());
    }

    /*public static void sendData(Patient patient, Signal.SignalType signalType, DataOutputStream dataOutputStream) throws IOException{
        dataOutputStream.writeUTF(signalType.name());
        if (signalType == Signal.SignalType.EMG) {
            LinkedList<Integer> values_EMG = patient.getValues_EMG();
            for(int i=0; i < values_EMG.size(); i++){
                dataOutputStream.writeInt(values_EMG.get(i));
            }
        } else if (signalType == Signal.SignalType.EDA) {
            LinkedList<Integer> values_EDA = patient.getValues_EDA();
            for(int i=0; i < values_EDA.size(); i++){
                dataOutputStream.writeInt(values_EDA.get(i));
            }
        }
        dataOutputStream.writeInt(-1);
    }*/

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
