package mephi.b22901.kateero.calculatestatistics.Model;

import java.io.IOException;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DataStorage {

    private XSSFWorkbook data;

    public DataStorage(XSSFWorkbook data) {
        this.data = data;
    }
    
    public XSSFWorkbook ReadData(){
        return data;
    }

    public void deleteData() throws IOException {
        data.close();
    }
}
