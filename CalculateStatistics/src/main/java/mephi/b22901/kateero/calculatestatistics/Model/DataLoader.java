package mephi.b22901.kateero.calculatestatistics.Model;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataLoader {

    public XSSFWorkbook load(String path) throws IOException {
        XSSFWorkbook data = new XSSFWorkbook(new FileInputStream(path));
        return data;
    }
}
