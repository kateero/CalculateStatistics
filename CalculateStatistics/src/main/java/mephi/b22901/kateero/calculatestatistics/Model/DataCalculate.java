package mephi.b22901.kateero.calculatestatistics.Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import static java.lang.Double.NaN;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.math3.analysis.function.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.commons.math3.stat.correlation.*;
import org.apache.commons.math3.stat.descriptive.moment.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;

public class DataCalculate {

    private XSSFWorkbook calculated;
    private double[][] data;
    private String[] names;
    private String[] calculations = {"Среднее геометрическое", "Среднее арифметическое", "Оценка стандартного отклонения",
        "Размах", "Количество элементов", "Коэффициент вариации", "Доверительный интервал",
        "Оценка дисперсии", "Максимум", "Минимум"};

    public DataCalculate(double[][] data, String[] names) {
        this.data = data;
        this.names = names;

        calculated = new XSSFWorkbook();
        XSSFSheet sheet = calculated.createSheet("Results");
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

    }

    public void calculate() {

        GeometricMean geomMean = new GeometricMean();
        Mean mean = new Mean();
        StandardDeviation sd = new StandardDeviation();
        NumberUtils numberUtils = new NumberUtils();
        Covariance cov = new Covariance();
        Variance var = new Variance();

        double[] results = new double[calculations.length];

        for (int i = 0; i < data.length; i++) {
            results[0] = geomMean.evaluate(data[i]);
            results[1] = mean.evaluate(data[i]);
            results[2] = sd.evaluate(data[i]);
            results[3] = numberUtils.max(data[i]) - numberUtils.min(data[i]);
            results[4] = data[i].length;
            results[5] = sd.evaluate(data[i]) / mean.evaluate(data[i]);
            results[6] = 4;
            results[7] = var.evaluate(data[i]);
            results[8] = numberUtils.max(data[i]);
            results[9] = numberUtils.min(data[i]);

            for (int j = 0; j < results.length; j++) {
                XSSFRow row = calculated.getSheet("Results").getRow(j + 1);
                XSSFCell cell = row.createCell(i + 1);
                cell.setCellValue(results[j]);
            }
        }

    }

    public void print() {
        try {
            String filePath = "C:\\Users\\Катя\\OneDrive\\Рабочий стол\\Лист Microsoft Excel.xlsx";
            File file = new File(filePath);
            FileOutputStream fileOut = new FileOutputStream(file);

            calculated.write(fileOut);
            calculated.close();
            fileOut.close();
        } catch (FileNotFoundException ex) {
            System.err.println("File not found");
        } catch (IOException ex) {
            System.err.println("ERROR");
        }
    }
}
