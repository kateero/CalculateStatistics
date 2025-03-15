package mephi.b22901.kateero.calculatestatistics;

import java.io.IOException;
import mephi.b22901.kateero.calculatestatistics.Model.*;

public class CalculateStatistics {

    public static void main(String[] args) {
        try {
            DataLoader dl = new DataLoader();
            DataStorage ds = new DataStorage(dl.load("C:\\Users\\Катя\\Downloads\\Лаба_1 образцы данных.xlsx"));
            CreateArray ca = new CreateArray(ds.ReadData(), 1);
            DataCalculate dc = new DataCalculate(ca.getArray(), ca.getNames());
            dc.calculate();
            dc.print();
            ds.deleteData();
        
        } catch (IOException ex) {
            System.err.println("Ошибка чтения файла");
        }
    }
}
