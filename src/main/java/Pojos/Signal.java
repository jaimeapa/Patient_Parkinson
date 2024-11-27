package Pojos;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Signal {
    private List<Integer> values;
    private String patientName;
    private LocalDate beginDate;
    private String signalFilename;
    private SignalType signalType;
    public static final int samplingrate = 100;

    public enum SignalType{
        EMG,
        EDA
    }

    // Constructor único
    public Signal(List<Integer> values, String patientName, LocalDate beginDate, SignalType signalType) {
        this.values = values;
        this.patientName = patientName;
        this.beginDate = beginDate;
        this.signalType = signalType;
        this.signalFilename = createFilename();
    }

    public Signal(SignalType signaltype, List<Integer> values){
        this.values = values;
        this.signalType = signaltype;
    }

    public Signal(SignalType signaltype, List<Integer> values, LocalDate beginDate){
        this.values = values;
        this.signalType = signaltype;
        this.beginDate = beginDate;
    }

    // Método para crear el nombre del archivo basado en el tipo de señal
    private String createFilename() {
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
    }

    // Métodos getters y setters
    public List<Integer> getValues() {
        return values;
    }

    public void setValues(List<Integer> values) {
        this.values = values;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
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

    // Métodos para almacenar los datos en archivos
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

    // Método común para obtener los valores de la señal
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

    // Método para obtener los valores de la señal como un String
    /*   public String getSignalValuesString (int samplingRate) {
        StringBuilder result = new StringBuilder();

        for (int j = 0; j < samplingRate; j++) {
            int blockSize = samplingRate;
            for (int i = 0; i < blockSize; i++) {
                int value = j * blockSize + i;
                result.append(values.get(value)).append(", ");
            }
        }
        if (result.length() > 0) {
            result.setLength(result.length() - 2);
        }

        return result.toString();
    }*/



    @Override
    public String toString() {
        return "Signal{" +
                "values=" + values +
                ", patientName='" + patientName + '\'' +
                ", beginDate=" + beginDate +
                ", signalFilename='" + signalFilename + '\'' +
                ", signalType=" + signalType +
                '}';
    }
}
