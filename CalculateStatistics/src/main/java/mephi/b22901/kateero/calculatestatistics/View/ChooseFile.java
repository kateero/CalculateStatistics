package mephi.b22901.kateero.calculatestatistics.View;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import javax.swing.filechooser.FileNameExtensionFilter;
import mephi.b22901.kateero.calculatestatistics.Controller.Controller;

public class ChooseFile {

    private JButton chooseFile;
    private JButton end;

    public ChooseFile(Controller controller, GUI GUI, boolean readFile) {

        GUI.getJFrame().setVisible(false);
        JFrame readFrame = new JFrame("Выбор файла");
        readFrame.setSize(500, 300);
        readFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        readFrame.setLocationRelativeTo(null);
        readFrame.getContentPane().setLayout(new FlowLayout());

        JTextField filePathField = new JTextField(30);
        filePathField.setBackground(Color.WHITE);
        filePathField.setEditable(false);

        JTextArea areaNumberSheet = new JTextArea("Введите номер листа");
        areaNumberSheet.setFont(new Font("Verdana", Font.PLAIN, 14));
        areaNumberSheet.setBackground(new Color(245, 235, 224));
        areaNumberSheet.setEditable(false);
        areaNumberSheet.setFont(new Font("Verdana", Font.PLAIN, 14));
        JTextField sheetNumber = new JTextField(5);

        chooseFile = new JButton("Выбрать файл");
        end = new JButton("Завершить");
        
        chooseFile.setBackground(new Color(255, 200, 221));
        chooseFile.setFont(new Font("Verdana", Font.PLAIN, 12));
        end.setBackground(new Color(255, 200, 221));
        end.setFont(new Font("Verdana", Font.PLAIN, 12));

        readFrame.getContentPane().add(filePathField);
        readFrame.getContentPane().add(chooseFile);
        if (readFile == true) {
            readFrame.getContentPane().add(areaNumberSheet);
            readFrame.getContentPane().add(sheetNumber);
        }
        readFrame.getContentPane().add(end);
        readFrame.getContentPane().setBackground(new Color(245, 235, 224));

        readFrame.setVisible(true);

        chooseFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel файлы (*.xls, *.xlsx)", "xls", "xlsx");
                fileChooser.setFileFilter(filter);
                int returnValue = fileChooser.showOpenDialog(null);

                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    filePathField.setText(selectedFile.getAbsolutePath());
                }
            }
        }
        );

        end.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (filePathField.getText().isEmpty() == false) {
                    if (filePathField.getText().endsWith(".xls") || filePathField.getText().endsWith(".xlsx")) {
                        if (readFile == true) {
                            if (sheetNumber.getText().trim().isEmpty() == false && sheetNumber.getText().matches("\\d+")) {
                                controller.setReadPath(filePathField.getText());
                                controller.setNumberSheet(Integer.parseInt(sheetNumber.getText()) - 1);
                                readFrame.setVisible(false);
                                GUI.getJFrame().setVisible(true);
                            } else {
                                JOptionPane.showMessageDialog(null, "Введите номер листа!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            controller.setWritePath(filePathField.getText());
                            readFrame.setVisible(false);
                            GUI.getJFrame().setVisible(true);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Выберите excel файл!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Выберите файл!", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        );
    }

    public JButton getChooseFile() {
        return chooseFile;
    }

    public JButton getEnd() {
        return end;
    }
}
