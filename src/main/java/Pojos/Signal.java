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
    private String patientName;
    private LocalDate beginDate;
    private String EMGFilename;

    public Signal(List<Integer> valuesEMG, String patientName, LocalDate beginDate, String EMGFilename) {
        this.valuesEMG = valuesEMG;
        this.patientName = patientName;
        this.beginDate = beginDate;
        this.EMGFilename = EMGFilename;
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

    public void StoreEMGinFile(){

        FileWriter fw = null;
        BufferedWriter bw = null;
        try {
            CreateEMGFilename();
            String ruta = "MeasurementsBitalino\\" + EMGFilename +".txt";
            String contenido = getSignalValues(10);//this.EMG_values.toString();

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

    public String getSignalValues(int samplingRate) {

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


