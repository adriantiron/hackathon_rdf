package hackathon1;

import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVFormat;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.*;
import java.util.Iterator;

public class xlsx2csv {

    public static void main(String[] args) {
        InputStream inp = null;

        try {
            inp = new FileInputStream("resources/invatamant-superior-2020.xlsx");
            Workbook wb = WorkbookFactory.create(inp);
            DataFormatter formatter = new DataFormatter();

            for(Sheet sheet: wb) {
                Writer writer = new BufferedWriter(new FileWriter("resources/csv/" + sheet.getSheetName() + ".csv"));
                CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);

                Iterator<Row> rowIterator = sheet.rowIterator();
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {
                        Cell cell = cellIterator.next();
                        csvPrinter.print(formatter.formatCellValue(cell)); // Call Commons CSV here to print
                    }
                    // Newline after each row
                    csvPrinter.println();

                }

                csvPrinter.flush();
                csvPrinter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inp.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }
}
