package mephi.b22901.kateero.calculatestatistics.Model;

import java.util.ArrayList;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class CreateArray {

    public CreateArray(XSSFWorkbook data, int numberSheet) {
        FormulaEvaluator evaluator = data.getCreationHelper().createFormulaEvaluator();
        XSSFSheet sheet = data.getSheetAt(numberSheet);
        XSSFRow row = sheet.getRow(0);

        int numberColumn = row.getLastCellNum();
        int numberRow = sheet.getLastRowNum();

        ArrayList<Double>[] arrayData = new ArrayList[numberColumn];

        for (int i = 0; i < numberColumn; i++) {
            arrayData[i] = new ArrayList<Double>();
            XSSFRow rowRead;
            for (int j = 1; j < numberRow; j++) {
                rowRead = sheet.getRow(j);
                XSSFCell cell = rowRead.getCell(i);
                if (cell == null) {
                    continue;
                }

                switch (cell.getCellType()) {
                    case NUMERIC:
                        arrayData[i].add(cell.getNumericCellValue());
                        break;

                    case STRING:
                        try {
                            arrayData[i].add(Double.valueOf(cell.getStringCellValue()));
                        } catch (NumberFormatException ex) {
                            System.err.println("Not number! Row: " + j + " column: " + i);
                        }
                        break;

                    case FORMULA:
                        arrayData[i].add(evaluator.evaluate(cell).getNumberValue());
                        break;

                    case BLANK:
                        break;

                    default:
                        break;
                }

            }
            System.out.println("Processed " + i + " column");
        }

    }
}
