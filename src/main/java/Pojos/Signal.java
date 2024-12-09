package Pojos;

import java.util.LinkedList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents a signal associated with a patient.
 * It supports both EMG and EDA signal types, and provides functionalities for
 * storing, processing, and managing signal data.
 */
public class Signal {
    private List<Integer> values;
    private String signalFilename;
    private SignalType signalType;
    public static final int samplingrate = 100;
    public enum SignalType{
        EMG,
        EDA
    }

    /**
     * Constructor to initialize a signal with a specific type.
     * @param signaltype the type of the signal (EMG or EDA).
     */
    public Signal(SignalType signaltype){
        this.values = new LinkedList<>();
        this.signalType = signaltype;
    }

    // Método para crear el nombre del archivo basado en el tipo de señal
    /*private String createFilename() {
        Calendar c = Calendar.getInstance();
        String day = Integer.toString(c.get(Calendar.DATE));
        String month = Integer.toString(c.get(Calendar.MONTH));
        String year = Integer.toString(c.get(Calendar.YEAR));
        String hour = Integer.toString(c.get(Calendar.HOUR));
        String minute = Integer.toString(c.get(Calendar.MINUTE));
        String second = Integer.toString(c.get(Calendar.SECOND));
        String millisecond = Integer.toString(c.get(Calendar.MILLISECOND));

        String signalPrefix = signalType == SignalType.EMG ? "EMG" : "EDA";
        return patientName + signalPrefix + day + month + year + "_" + hour + minute + second + millisecond + ".txt";
    }*/

    // Métodos getters y setters
    public List<Integer> getValues() {
        return values;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }

    public void setValuesEMG(String stringEMG) {
        this.values = stringToValues(stringEMG);
    }
    public void setValuesEDA(String stringEDA) {
        this.values = stringToValues(stringEDA);
    }

    public String getSignalFilename() {
        return signalFilename;
    }

    public void setSignalFilename(String signalFilename) {
        this.signalFilename = signalFilename;
    }

    public SignalType getSignalType() {
        return signalType;
    }

    public void setSignalType(SignalType signalType) {
        this.signalType = signalType;
    }

    /**
     * Stores the signal data in a file based on its type.
     */
    public void storeSignalInFile() {
        FileWriter fw = null;
        BufferedWriter bw = null;
        String ruta=null;
        try {
            if(this.signalType==SignalType.EDA) {
                ruta = "MeasurementsEDA\\" + signalFilename;

            }else{
                if(this.signalType==SignalType.EMG) {
                    ruta = "MeasurementsEMG\\" + signalFilename;
                }
            }
            String contenido = getSignalValues(samplingrate).toString();
            File file = new File(ruta);
            if (!file.exists()) {
                file.createNewFile();
            }
            fw = new FileWriter(file);
            bw = new BufferedWriter(fw);
            bw.write(contenido);

        } catch (IOException ex) {
            Logger.getLogger(Signal.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bw.close();
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Signal.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Retrieves the signal values at the specified sampling rate.
     * @param samplingRate the sampling rate for the signal.
     * @return a list of signal values at the specified rate.
     */
    public LinkedList<Integer> getSignalValues(int samplingRate) {
        LinkedList<Integer> result = new LinkedList<>();
        for (int j = 0; j < samplingRate; j++) {
            int blockSize = samplingRate;
            // Si necesitas esta información visual, puedes guardarla en otro lugar.
            for (int i = 0; i < blockSize; i++) {
                int value = j * blockSize + i;
                result.add(values.get(value));  // Agregar los valores a la lista.
            }
        }
        return result;
    }

    /**
     * Converts the list of signal values into a space-separated string.
     * @return a string representation of the signal values.
    */
    public String valuesToString() {
        StringBuilder message = new StringBuilder();
        String separator = " ";

        for (int i = 0; i < values.size(); i++) {
            message.append(values.get(i));
            if (i < values.size() - 1) {
                message.append(separator);
            }
        }

        return message.toString();
    }
    /**
     * Parses a space-separated string into a list of signal values.
     * @param str the input string representing signal values.
     * @return a list of integers parsed from the input string.
     */
    public List<Integer> stringToValues(String str) {
        values.clear(); // Limpiamos la lista antes de agregar nuevos valores.
        String[] tokens = str.split(" "); // Dividimos el String por el espacio.
        int size = tokens.length;
        if(size>2) {
            for (int i = 0; i < size; i++) {
                try {
                    values.add(Integer.parseInt(tokens[i])); // Convertimos cada fragmento a Integer y lo agregamos a la LinkedList.
                } catch (NumberFormatException e) {
                    // Manejo de error si algún valor no es un Integer válido.
                    System.out.println("Error al convertir el valor: " + tokens[i]);
                }
            }
        }


        return values;
    }
    /**
     * Adds a list of new signal values to the existing signal.
     * @param values a list of new signal values to add.
     */
    public void addValues(LinkedList<Integer> values){
        this.values.addAll(values);
    }

    @Override
    public String toString() {
        return "Signal{" +
                "values=" + values +
                ", signalFilename='" + signalFilename + '\'' +
                ", signalType=" + signalType +
                '}';
    }
}
