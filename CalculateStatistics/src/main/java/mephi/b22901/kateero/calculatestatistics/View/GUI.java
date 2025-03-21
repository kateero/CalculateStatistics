package mephi.b22901.kateero.calculatestatistics.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI {

    private JFrame frame;
    private JButton read;
    private JButton calculate;
    private JButton write;
    private JButton unload;

    public void run() {
        frame = new JFrame("Вычисление параметров");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1));

        read = new JButton("Выбрать файл для чтения");
        calculate = new JButton("Рассчитать показатели");
        write = new JButton("Выбрать файл для записи");
        unload = new JButton("Записать результаты");

        read.setFont(new Font("Verdana", Font.PLAIN, 18));
        calculate.setFont(new Font("Verdana", Font.PLAIN, 18));
        write.setFont(new Font("Verdana", Font.PLAIN, 18));
        unload.setFont(new Font("Verdana", Font.PLAIN, 18));

        read.setBackground(new Color(222, 255, 125));
        calculate.setBackground(new Color(255, 233, 133));
        write.setBackground(new Color(253, 197, 245));
        unload.setBackground(new Color(230, 190, 255));

        panel.add(read);
        panel.add(calculate);
        panel.add(unload);
        panel.add(write);
        panel.add(unload);

        frame.getContentPane().add(panel);
        frame.setVisible(true);

    }

    public JButton getReadButton() {
        return read;
    }

    public JButton getCalculateButton() {
        return calculate;
    }

    public JButton getWriteButton() {
        return write;
    }

    public JButton getUnloadButton() {
        return unload;
    }

    public JFrame getJFrame() {
        return frame;
    }

}
