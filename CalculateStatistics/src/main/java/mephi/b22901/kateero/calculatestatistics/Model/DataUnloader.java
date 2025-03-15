package mephi.b22901.kateero.calculatestatistics.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataUnloader {

    public void unload(XSSFWorkbook calculated, String path) throws FileNotFoundException, IOException {
        File file = new File(path);
        FileOutputStream fileOut = new FileOutputStream(file);
        calculated.write(fileOut);
        calculated.close();
        fileOut.close();
    }
}
