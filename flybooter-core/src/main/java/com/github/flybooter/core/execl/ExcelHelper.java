package com.github.flybooter.core.execl;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author feixm
 */
@Slf4j
public class ExcelHelper {
    private final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static XSSFWorkbook generateExcel(Class<?> c, List<?> dataList){
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet();
        Row row = sheet.createRow(0);
        generateTitles(c, row);
        generateContent(sheet, c, dataList);
        return wb;
    }

    private static void generateTitles(Class<?> c, Row row) {
        List<String> titles = Stream.of(c.getDeclaredFields())
                .map(field -> field.getAnnotation(ExcelResources.class))
                .filter(t -> t != null)
                .sorted(Comparator.comparing(ExcelResources::order))
                .map(ExcelResources::title)
                .collect(Collectors.toList());
        for (int col = 0; col < titles.size(); col++) {
            row.createCell(col);
            row.getCell(col).setCellValue(titles.get(col));
        }
    }

    private static void generateContent(XSSFSheet sheet, Class<?> c, List<?> dataList) {
        List<String> fieldNames = getFiledNames(c);
        for (int i = 0; i < dataList.size(); i++) {
            Row newRow = sheet.createRow(i + 1);
            for (int j = 0; j < fieldNames.size(); j++){
                newRow.createCell(j);
                String value = getFieldValue(c, dataList.get(i), fieldNames.get(j));
                newRow.getCell(j).setCellValue(value);
            }
        }
    }

    private static List<String> getFiledNames(Class<?> c) {
        return Stream.of(c.getDeclaredFields())
                .map(field -> field.getAnnotation(ExcelResources.class))
                .filter(t -> t != null)
                .sorted(Comparator.comparing(ExcelResources::order))
                .map(ExcelResources::fieldName)
                .collect(Collectors.toList());
    }

    private static String getFieldValue(Class<?> c, Object o, String filedName) {
        try {
            Field nameField = c.getDeclaredField(filedName);
            nameField.setAccessible(true);
            return Optional.ofNullable(nameField.get(o))
                    .map(obj -> {
                        if (obj instanceof LocalDateTime) {
                            return ((LocalDateTime) obj).format(DateTimeFormatter.ofPattern(DATE_FORMAT));
                        }else if (obj instanceof Date) {
                            return dateFormatYYYYMMDDHHMMSS((Date) obj);
                        } else {
                            return String.valueOf(obj);
                        }
                    }).orElse(StringUtils.EMPTY);
        } catch (Exception e) {
            log.error("获取字段值出错");
        }
        return StringUtils.EMPTY;
    }

    private static String dateFormatYYYYMMDDHHMMSS(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        return simpleDateFormat.format(date);
    }
}
