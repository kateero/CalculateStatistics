package mephi.b22901.kateero.calculatestatistics.Controller;

import java.io.IOException;
import mephi.b22901.kateero.calculatestatistics.Model.CreateArray;
import mephi.b22901.kateero.calculatestatistics.Model.DataCalculate;
import mephi.b22901.kateero.calculatestatistics.Model.DataLoader;
import mephi.b22901.kateero.calculatestatistics.Model.DataStorage;
import mephi.b22901.kateero.calculatestatistics.Model.DataUnloader;

public class Controller {
    public Controller(){
         try {
            DataLoader dl = new DataLoader();
            DataStorage ds = new DataStorage(dl.load("C:\\Users\\����\\Downloads\\����_1 ������� ������.xlsx"));
            CreateArray ca = new CreateArray(ds.ReadData(), 2);
            DataCalculate dc = new DataCalculate(ca.getArray(), ca.getNames());
            DataUnloader du = new DataUnloader();
            du.unload(dc.calculate(), "C:\\Users\\����\\OneDrive\\������� ����\\���� Microsoft Excel.xlsx");
            ds.deleteData();
        
        } catch (IOException ex) {
            System.err.println("������ ������ �����");
        }
    }
}
