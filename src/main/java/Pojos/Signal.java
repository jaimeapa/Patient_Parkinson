package Pojos;

import java.time.LocalDate;
import java.util.List;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Signal {
    private List<Integer> valuesEMG;
    private List<Integer> valuesEDA;
    private String patientName;
    private LocalDate beginDate;
    private String EMGFilename;
    private String EDAFilename;

    //HACER ENUMERADO CON VALUES_EMG Y VALUES_EDA

    public enum SignalType{
        SignalEMG,
        SignalEDA
    }





    public Signal(List<Integer> valuesEMG, String patientName, LocalDate beginDate, String EMGFilename) {
        this.valuesEMG = valuesEMG;
        this.patientName = patientName;
        this.beginDate = beginDate;
        this.EMGFilename = EMGFilename;
    }
    public Signal(List<Integer> valuesEDA, String patientName, LocalDate beginDate, String EDAFilename) {
        this.valuesEDA = valuesEDA;
        this.patientName = patientName;
        this.beginDate = beginDate;
        this.EDAFilename = EDAFilename;
    }
    public Signal(List<Integer> valuesEMG, String patientName) {
        this.valuesEMG = valuesEMG;
        this.patientName = patientName;
    }

    public List<Integer> getValuesEMG() {
        return valuesEMG;
    }

    public void setValuesEMG(List<Integer> valuesEMG) {
        this.valuesEMG = valuesEMG;
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

    public String getEMGFilename() {
        return EMGFilename;
    }

    public void setEMGFilename(String EMGFilename) {
        this.EMGFilename = EMGFilename;
    }
    public List<Integer> getValuesEDA() {
        return valuesEDA;
    }

    public void setValuesEDA(List<Integer> valuesEDA) {
        this.valuesEDA = valuesEDA;
    }

    public String getEDAFilename() {
        return EDAFilename;
    }

    public void setEDAFilename(String EDAFilename) {
        this.EDAFilename = EDAFilename;
    }

    public void StoreEMGinFile(){

        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            CreateEMGFilename();
            String ruta = "MeasurementsBitalino\\" + EMGFilename +".txt";
            String contenido = getSignalValuesEMG(10);//this.EMG_values.toString();

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

    public void CreateEMGFilename (){

        Calendar c = Calendar.getInstance();
        String day = Integer.toString(c.get(Calendar.DATE));
        String month = Integer.toString(c.get(Calendar.MONTH));
        String year = Integer.toString(c.get(Calendar.YEAR));
        String hour = Integer.toString(c.get(Calendar.HOUR));
        String minute = Integer.toString(c.get(Calendar.MINUTE));
        String second = Integer.toString(c.get(Calendar.SECOND));
        String millisecond = Integer.toString(c.get(Calendar.MILLISECOND));
        this.EMGFilename = patientName+"EMG"+day+month+year+"_"+hour+minute+second+millisecond+".txt";
    }

    public String getSignalValuesEMG(int samplingRate) {

        String result = "";
        for (int j=0; j<samplingRate; j++) {
            // each time reads a block of 10 samples
            int blockSize = samplingRate;
            //Frame[] frames = bitalino.read(blockSize);
            result = result + "\nSize blocks: " + (j+1) + "/" + blockSize;
            for (int i=0; i<blockSize; i++) {
                int value = j * blockSize + i;
                result = result + ("\n Value " + (value+1) + ": " + valuesEMG.get(value));
            }
        }
        return result;
    }
    public void StoreEDAinFile(){

        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            CreateEMGFilename();
            String ruta = "MeasurementsBitalino\\" + EDAFilename +".txt";
            String contenido = getSignalValuesEDA(10);

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

    public void CreateEDAFilename (){

        Calendar c = Calendar.getInstance();
        String day = Integer.toString(c.get(Calendar.DATE));
        String month = Integer.toString(c.get(Calendar.MONTH));
        String year = Integer.toString(c.get(Calendar.YEAR));
        String hour = Integer.toString(c.get(Calendar.HOUR));
        String minute = Integer.toString(c.get(Calendar.MINUTE));
        String second = Integer.toString(c.get(Calendar.SECOND));
        String millisecond = Integer.toString(c.get(Calendar.MILLISECOND));
        this.EDAFilename = patientName+"EDA"+day+month+year+"_"+hour+minute+second+millisecond+".txt";
    }

    public String getSignalValuesEDA(int samplingRate) {

        String result = "";
        for (int j=0; j<samplingRate; j++) {
            // each time reads a block of 10 samples
            int blockSize = samplingRate;
            //Frame[] frames = bitalino.read(blockSize);
            result = result + "\nSize blocks: " + (j+1) + "/" + blockSize;
            for (int i=0; i<blockSize; i++) {
                int value = j * blockSize + i;
                result = result + ("\n Value " + (value+1) + ": " + valuesEDA.get(value));
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "Signal{" +
                "valuesEMG=" + valuesEMG +
                ", patientName='" + patientName + '\'' +
                ", beginDate=" + beginDate +
                ", EMGFilename='" + EMGFilename + '\'' +
                '}';
    }
}


