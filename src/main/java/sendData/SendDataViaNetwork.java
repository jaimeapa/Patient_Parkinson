package sendData;

import java.net.Socket;
import Pojos.Interpretation;
import Pojos.Patient;
import Pojos.User;
import java.io.DataOutputStream;
import java.io.IOException;

public class SendDataViaNetwork {
    private DataOutputStream dataOutputStream;

    public SendDataViaNetwork(Socket socket) {
        try {
            this.dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Error al inicializar el flujo de salida: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean sendStrings(String message) {
        try {
            dataOutputStream.writeUTF(message);
            dataOutputStream.flush();
            return true;
        } catch (IOException e) {
            System.err.println("Error al enviar String: " + e.getMessage());
            return false;
        }
    }

    public boolean sendInt(int message) {
        try {
            dataOutputStream.writeInt(message);
            dataOutputStream.flush();
            return true;
        } catch (IOException e) {
            System.err.println("Error al enviar Int: " + e.getMessage());
            return false;
        }
    }

    public  void sendUser(User u) throws IOException
    {
        dataOutputStream.writeUTF(u.getEmail());
        byte[] password = u.getPassword();

        dataOutputStream.writeUTF(new String(password));
        dataOutputStream.writeUTF(u.getRole().toString());
    }

    public boolean sendPatient(Patient patient) {
        try {
            dataOutputStream.writeInt(patient.getPatient_id());
            dataOutputStream.writeUTF(patient.getName());
            dataOutputStream.writeUTF(patient.getSurname());
            dataOutputStream.writeUTF(patient.getDob().toString());
            dataOutputStream.writeUTF(patient.getEmail());
            dataOutputStream.flush();
            return true;
        } catch (IOException e) {
            System.err.println("Error al enviar Patient: " + e.getMessage());
            return false;
        }
    }
    public void sendInterpretation(Interpretation interpretation) throws IOException{
        dataOutputStream.writeUTF(interpretation.getDate().toString());
        dataOutputStream.writeInt(interpretation.getDoctor_id());
        dataOutputStream.writeUTF(interpretation.getSignalEMG().valuesToString());
        dataOutputStream.writeInt(interpretation.getPatient_id());
        dataOutputStream.writeUTF(interpretation.getSignalEDA().valuesToString());
        dataOutputStream.writeUTF(interpretation.getObservation());
        dataOutputStream.writeUTF(interpretation.getInterpretation());
    }
    public void releaseResources() {
        try {
            if (dataOutputStream != null) {
                dataOutputStream.close();
            }
        } catch (IOException e) {
            System.err.println("Error al liberar recursos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
