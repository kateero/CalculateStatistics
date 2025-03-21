package mephi.b22901.kateero.calculatestatistics.Model;

import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.linear.RealMatrix;
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
        "Размах", "Количество элементов", "Коэффициент вариации", "Доверительный интервал 95%",
        "Оценка дисперсии", "Максимум", "Минимум", "", "Коэффициенты ковариации"};

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

        for (int i = 0; i < calculations.length + names.length; i++) {
            XSSFRow row = sheet.createRow(i + 1);
            XSSFCell cell = row.createCell(0);

            if (i > calculations.length - 1) {
                cell.setCellValue(names[i - calculations.length]);
            } else if (i == calculations.length - 1) {
                cell.setCellValue(calculations[i]);
                for (int j = 1; j < data.length + 1; j++) {
                    cell = row.createCell(j);
                    cell.setCellValue(names[j - 1]);
                }
            } else {
                cell.setCellValue(calculations[i]);
            }
        }
    }

    public XSSFWorkbook calculate() {
        GeometricMean geomMean = new GeometricMean();
        Mean mean = new Mean();
        StandardDeviation sd = new StandardDeviation();
        NumberUtils numberUtils = new NumberUtils();
        double[][] transposedData = transpose(data);
        double[][] cov = new Covariance(transposedData).getCovarianceMatrix().getData();
        Variance var = new Variance();

        double[] results = new double[calculations.length - 2];

        for (int i = 0; i < data.length; i++) {
            TDistribution tDist = new TDistribution(data[i].length - 1);
            double tCritical = tDist.inverseCumulativeProbability(1 - 0.05/2);
            
            results[0] = geomMean.evaluate(data[i]);
            results[1] = mean.evaluate(data[i]);
            results[2] = sd.evaluate(data[i]);
            results[3] = numberUtils.max(data[i]) - numberUtils.min(data[i]);
            results[4] = data[i].length;
            results[5] = sd.evaluate(data[i]) / mean.evaluate(data[i]);
            results[6] = tCritical * results[5] / Math.sqrt(results[4]);
            results[7] = var.evaluate(data[i]);
            results[8] = numberUtils.max(data[i]);
            results[9] = numberUtils.min(data[i]);

            calculated.getSheet("Results").setColumnWidth(i + 1, 3840);

            for (int j = 0; j < results.length; j++) {
                XSSFRow row = calculated.getSheet("Results").getRow(j + 1);
                XSSFCell cell = row.createCell(i + 1);
                if (Double.isNaN(results[j])) {
                    cell.setCellValue("-");
                } else {
                    cell.setCellValue(results[j]);
                }
            }

            for (int j = 0; j < data.length; j++) {
                XSSFRow row = calculated.getSheet("Results").getRow(results.length + j + 3);
                XSSFCell cell = row.createCell(i + 1);
                cell.setCellValue(cov[j][i]);
            }
        }
        return calculated;
    }

    public static double[][] transpose(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        double[][] transposed = new double[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                transposed[j][i] = matrix[i][j];
            }
        }
        return transposed;
    }
}
