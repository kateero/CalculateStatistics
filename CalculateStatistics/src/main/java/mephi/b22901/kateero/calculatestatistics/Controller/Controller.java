package mephi.b22901.kateero.calculatestatistics.Controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import mephi.b22901.kateero.calculatestatistics.Model.*;
import mephi.b22901.kateero.calculatestatistics.View.*;

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

        GUI = new GUI();
        this.dl = dl = new DataLoader();
        this.du = new DataUnloader();
    }

    public void process() {

        GUI.run();

        GUI.getReadButton().addActionListener(e -> {
            ChooseFile chooseRead = new ChooseFile(this, GUI, true);
        });

        GUI.getCalculateButton().addActionListener(e -> {
            if (this.getReadPath() != null) {
                try {
                    ds = new DataStorage(dl.load(this.getReadPath()));
                    ca = new CreateArray(ds.ReadData(), this.getNumberSheet());
                    dc = new DataCalculate(ca.getArray(), ca.getNames());
                    JOptionPane.showMessageDialog(null, "Результаты посчитаны", "Успешно", JOptionPane.INFORMATION_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Листа с указанным номером не существует", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(null, "Ошибка чтения файла", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Что-то пошло не так", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Не выбран файл для чтения", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }

        });

        GUI.getWriteButton().addActionListener(e -> {
            ChooseFile chooseWrite = new ChooseFile(this, GUI, false);
        });

        GUI.getUnloadButton().addActionListener(e -> {
            if (this.writePath != null) {
                try {
                    du.unload(dc.calculate(), this.getWritePath());
                    ds.deleteData();
                    JOptionPane.showMessageDialog(null, "Результаты записаны", "Успешно", JOptionPane.INFORMATION_MESSAGE);
                } catch (NullPointerException ex) {
                    JOptionPane.showMessageDialog(null, "Данные не загружены", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Ошибка доступа к файлу", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Не выбран файл для записи", "Ошибка", JOptionPane.ERROR_MESSAGE);
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

    public int getNumberSheet() {
        return numberSheet;
    }

    public void setNumberSheet(int numberSheet) {
        this.numberSheet = numberSheet;
    }
}
