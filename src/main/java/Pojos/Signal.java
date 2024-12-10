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
    /**
     * A list of integer values representing the signal data.
     */
    private List<Integer> values;

    /**
     * The filename where the signal data is stored.
     */
    private String signalFilename;

    /**
     * The type of the signal (either EMG or EDA).
     */
    private SignalType signalType;

    /**
     * The sampling rate used for the signal data (in Hz).
     * Default value is 100 Hz.
     */
    public static final int samplingrate = 100;

    /**
     * Enum representing the possible types of signals.
     */
    public enum SignalType {
        /**
         * Represents an EMG (electromyography) signal.
         */
        EMG,

        /**
         * Represents an EDA (electrodermal activity) signal.
         */
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

    /**
     * Returns the list of values representing the signal data.
     *
     * @return A list of integers representing the signal data.
     */
    public List<Integer> getValues() {
        return values;
    }

    /**
     * Sets the list of values for the signal data.
     *
     * @param values A list of integers representing the signal data to set.
     */
    public void setValues(List<Integer> values) {
        this.values = values;
    }

    /**
     * Sets the EMG (electromyography) signal data by converting a string representation to values.
     *
     * @param stringEMG A string containing the EMG signal data.
     */
    public void setValuesEMG(String stringEMG) {
        this.values = stringToValues(stringEMG);
    }

    /**
     * Sets the EDA (electrodermal activity) signal data by converting a string representation to values.
     *
     * @param stringEDA A string containing the EDA signal data.
     */
    public void setValuesEDA(String stringEDA) {
        this.values = stringToValues(stringEDA);
    }

    /**
     * Returns the filename where the signal data is stored.
     *
     * @return The filename of the signal data.
     */
    public String getSignalFilename() {
        return signalFilename;
    }

    /**
     * Sets the filename where the signal data is stored.
     *
     * @param signalFilename The filename to set for the signal data.
     */
    public void setSignalFilename(String signalFilename) {
        this.signalFilename = signalFilename;
    }

    /**
     * Returns the type of the signal (EMG or EDA).
     *
     * @return The type of the signal.
     */
    public SignalType getSignalType() {
        return signalType;
    }

    /**
     * Sets the type of the signal (EMG or EDA).
     *
     * @param signalType The signal type to set.
     */
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
