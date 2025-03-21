package mephi.b22901.kateero.calculatestatistics.Controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import mephi.b22901.kateero.calculatestatistics.Model.CreateArray;
import mephi.b22901.kateero.calculatestatistics.Model.DataCalculate;
import mephi.b22901.kateero.calculatestatistics.Model.DataLoader;
import mephi.b22901.kateero.calculatestatistics.Model.DataStorage;
import mephi.b22901.kateero.calculatestatistics.Model.DataUnloader;
import mephi.b22901.kateero.calculatestatistics.View.GUI;
import mephi.b22901.kateero.calculatestatistics.View.ChooseFile;

public class Controller {

    private GUI GUI;
    private DataLoader dl;
    private DataUnloader du;
    private DataStorage ds;
    private CreateArray ca;
    private DataCalculate dc;
    private String readPath;
    private String writePath;
    private int numberSheet;

    public Controller() {

        GUI GUI = new GUI();
        this.dl = dl = new DataLoader();
        this.du = new DataUnloader();

        GUI.run();

        GUI.getReadButton().addActionListener(e -> {
            ChooseFile chooseRead = new ChooseFile(this, GUI, true);
        });

        GUI.getLoadButton().addActionListener(e -> {
            dl = new DataLoader();
            try {
                ds = new DataStorage(dl.load(this.getReadPath()));
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        GUI.getCalculateButton().addActionListener(e -> {
            ca = new CreateArray(ds.ReadData(), this.getNumberSheet());
            dc = new DataCalculate(ca.getArray(), ca.getNames());
        });

        GUI.getWriteButton().addActionListener(e -> {
            ChooseFile chooseWrite = new ChooseFile(this, GUI, false);
        });

        GUI.getUnloadButton().addActionListener(e -> {
            try {
                du.unload(dc.calculate(), this.getWritePath());
                ds.deleteData();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public String getReadPath() {
        return readPath;
    }

    public String getWritePath() {
        return writePath;
    }

    public void setReadPath(String readPath) {
        this.readPath = readPath;
    }

    public void setWritePath(String writePath) {
        this.writePath = writePath;
    }
    
    public int getNumberSheet(){
        return numberSheet;
    }
    
    public void setNumberSheet(int numberSheet){
        this.numberSheet = numberSheet;
    }
}
