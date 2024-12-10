package sendData;

import java.net.Socket;
import Pojos.Interpretation;
import Pojos.Patient;
import Pojos.User;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 * This class handles sending data over a network connection.
 * It uses a `DataOutputStream` to serialize and send various types of objects and primitives.
 */
public class SendDataViaNetwork {
    /**
     * The DataOutputStream used to write primitive data types to an output stream.
     */
    private DataOutputStream dataOutputStream;

    /**
     * Constructor that initializes the `DataOutputStream` from the provided socket.
     * @param socket the socket used for network communication.
     */
    public SendDataViaNetwork(Socket socket) {
        try {
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
            System.err.println("Error al inicializar el flujo de salida: " + ex.getMessage());
            Logger.getLogger(SendDataViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Sends a UTF-encoded string over the network.
     * @param message the string to send.
     */
    public void sendStrings(String message) {
        try {
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
        } catch (IOException ex) {
            System.err.println("Error al enviar String: " + ex.getMessage());
            Logger.getLogger(SendDataViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Sends an integer value over the network.
     * @param message the integer to send.
     */
    public void sendInt(int message) {
        try {
            dataOutputStream.writeInt(message);
            dataOutputStream.flush();

        } catch (IOException ex) {
            System.err.println("Error al enviar Int: " + ex.getMessage());
            Logger.getLogger(SendDataViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Sends a `User` object over the network.
     * @param user the `User` object to send.
     * @throws IOException if an I/O error occurs.
     */
    public  void sendUser(User user) throws IOException
    {
        dataOutputStream.writeUTF(user.getEmail());
        byte[] password = user.getPassword();

        dataOutputStream.writeUTF(new String(password));
        dataOutputStream.writeUTF(user.getRole().toString());
    }
    /**
     * Sends a `Patient` object over the network.
     * @param patient the `Patient` object to send.
     */
    public void sendPatient(Patient patient) {
        try {
            dataOutputStream.writeInt(patient.getPatient_id());
            dataOutputStream.writeUTF(patient.getName());
            dataOutputStream.writeUTF(patient.getSurname());
            dataOutputStream.writeUTF(patient.getDob().toString());
            dataOutputStream.writeUTF(patient.getEmail());
            dataOutputStream.flush();

        } catch (IOException ex) {
            System.err.println("Error al enviar Patient: " + ex.getMessage());
            Logger.getLogger(SendDataViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Sends an `Interpretation` object over the network.
     * @param interpretation the `Interpretation` object to send.
     * @throws IOException if an I/O error occurs.
     */
    public void sendInterpretation(Interpretation interpretation) throws IOException{
        dataOutputStream.writeUTF(interpretation.getDate().toString());
        dataOutputStream.writeInt(interpretation.getDoctor_id());
        dataOutputStream.writeUTF(interpretation.getSignalEMG().valuesToString());
        dataOutputStream.writeInt(interpretation.getPatient_id());
        dataOutputStream.writeUTF(interpretation.getSignalEDA().valuesToString());
        dataOutputStream.writeUTF(interpretation.getObservation());
        dataOutputStream.writeUTF(interpretation.getInterpretation());
    }
    /**
     * Releases the resources used by the `DataOutputStream`.
     */
    public void releaseResources() {
        try {
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
        } catch (IOException ex) {
            System.err.println("Error al liberar recursos: " + ex.getMessage());
            Logger.getLogger(SendDataViaNetwork.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
