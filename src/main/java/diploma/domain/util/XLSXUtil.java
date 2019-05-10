package diploma.domain.util;

import diploma.domain.entities.FormattedDate;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public final class XLSXUtil {
    private XLSXUtil() {
    }

    static void readXLSXTo(Map<FormattedDate, Integer> data, String pathToXLSX) throws IOException, InvalidFormatException {
        DataFormatter dataFormatter = new DataFormatter();
        try (Workbook workbook = WorkbookFactory.create(new File(pathToXLSX))) {
            workbook.forEach(sheet -> sheet.forEach(row -> {
                String day = dataFormatter.formatCellValue(row.getCell(0));
                String time = dataFormatter.formatCellValue(row.getCell(1));
                int temperature = Integer.parseInt(dataFormatter.formatCellValue(row.getCell(2)));
                if (day.length() < 2) {
                    day = "0" + day;
                }
                if (time.length() < 5) {
                    time = "0" + time;
                }
                String key = sheet.getSheetName() + "-" + day + "T" + time;
                data.put(new FormattedDate(key), temperature);
            }));
        }
    }
}
