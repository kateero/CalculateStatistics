package mephi.b22901.kateero.calculatestatistics.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.math3.stat.*;
import org.apache.commons.math3.stat.descriptive.moment.GeometricMean;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class DataCalculate {

    private XSSFWorkbook calculated;
    private double[][] data;
    private String[] names;
    private String[] calculations = {"Среднее геометрическое", "Среднее арифметическое", "Оценка стандартного отклонения",
        "Размах", "Коэффициенты ковариации", "Количество элементов", "Коэффициент вариации", "Доверительный интервал",
        "Оценка дисперсии", "Максимум", "Минимум"};

    public DataCalculate(double[][] data, String[] names) {
        this.data = data;
        this.names = names;

        calculated = new XSSFWorkbook();
        XSSFSheet sheet = calculated.createSheet("Результаты");
        XSSFRow rowNames = sheet.createRow(0);

        for (int i = 1; i < names.length + 1; i++) {
            XSSFCell cell = rowNames.createCell(i);
            cell.setCellValue(names[i - 1]);
        }

        sheet.setColumnWidth(0, 7680);

        for (int i = 0; i < calculations.length; i++) {
            XSSFRow row = sheet.createRow(i + 1);
            XSSFCell cell = row.createCell(0);
            cell.setCellValue(calculations[i]);
        }

        try {
            String filePath = "C:\\Users\\Катя\\OneDrive\\Рабочий стол\\Лист Microsoft Excel.xlsx";
            File file = new File(filePath);
            FileOutputStream fileOut = new FileOutputStream(file);

            calculated.write(fileOut);
            calculated.close();
            fileOut.close();
        } catch (FileNotFoundException ex) {
            System.err.println("File not found");
        } catch(IOException ex){
            System.err.println("ERROR");
        }
    }
    
    public void calculate(){
        for(int i = 0; i < data.length; i++){
            double meanGeom = new GeometricMean().evaluate(data[i]); 
            System.out.println(meanGeom);
        }
        
    }
    
    
}
