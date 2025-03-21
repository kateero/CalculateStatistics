package mephi.b22901.kateero.calculatestatistics.Model;

import static org.apache.poi.ss.usermodel.CellType.FORMULA;
import static org.apache.poi.ss.usermodel.CellType.NUMERIC;
import static org.apache.poi.ss.usermodel.CellType.STRING;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CreateArray {

    private double[][] arrayData;
    private String[] names;

    public CreateArray(XSSFWorkbook data, int numberSheet) {
        FormulaEvaluator evaluator = data.getCreationHelper().createFormulaEvaluator();
        XSSFSheet sheet = data.getSheetAt(numberSheet);
        XSSFRow row = sheet.getRow(0);

        int numberColumn = row.getLastCellNum();
        int numberRow = sheet.getLastRowNum();

        this.arrayData = new double[numberColumn][];
        this.names = new String[numberColumn];

        for (int i = 0; i < numberColumn; i++) {
            arrayData[i] = new double[numberRow];
            names[i] = sheet.getRow(0).getCell(i).getStringCellValue();

            XSSFRow rowRead;
            for (int j = 0; j < numberRow; j++) {
                rowRead = sheet.getRow(j + 1);
                XSSFCell cell = rowRead.getCell(i);
                if (cell == null) {
                    continue;
                }

                switch (cell.getCellType()) {
                    case NUMERIC:
                        arrayData[i][j] = cell.getNumericCellValue();
                        break;

                    case STRING:
                        try {
                            arrayData[i][j] = Double.parseDouble(cell.getStringCellValue().replace(',', '.'));
                        } catch (NumberFormatException ex) {
                            System.err.println("Not number! Row: " + j + " column: " + i);
                        }
                        break;

                    case FORMULA:
                        arrayData[i][j] = evaluator.evaluate(cell).getNumberValue();
                        break;

                    default:
                        break;
                }
            }
        }
    }

    public double[][] getArray() {
        return arrayData;
    }

    public String[] getNames() {
        return names;
    }
}
