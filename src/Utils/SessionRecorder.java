package Utils;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class SessionRecorder {
    private FileWriter csvWriter;

    public SessionRecorder(String filename) {
        try {
            csvWriter = new FileWriter(filename);
            csvWriter.append("DeltaT");
            csvWriter.append(",");
            csvWriter.append("Heading");
            csvWriter.append(",");
            csvWriter.append("XPos");
            csvWriter.append(",");
            csvWriter.append("YPos");
            csvWriter.append(",");
            csvWriter.append("VelTheta");
            csvWriter.append(",");
            csvWriter.append("VelX");
            csvWriter.append(",");
            csvWriter.append("VelY");
            csvWriter.append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void append(List<? extends Number> data) {
        try {
            csvWriter.append(String.join(",", data.toString()));
            csvWriter.append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void complete() {
        try {
            csvWriter.flush();
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
