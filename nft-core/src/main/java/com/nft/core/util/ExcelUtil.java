package com.nft.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.IOException;

/**
 * @author caoc
 * @createDate 2021/11/12
 */
@Slf4j
public class ExcelUtil {

    public static void main(String[] args) {

        File file = new File("/Users/fangzhongming/Documents/工作/数智人/行业领域岗位.xlsx");
        try {
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheetAt(0);
            int rows = sheet.getPhysicalNumberOfRows();
            log.info("当前行数：{}", rows);
            log.info("合并个数：{}", sheet.getNumMergedRegions());

            for (int i = 1; i < rows; i++) {
                Row row = sheet.getRow(i);
                Cell industCell = row.getCell(0);
                String industName = getMergedRegionValue(sheet, i, industCell.getColumnIndex());
                Cell jobCell = row.getCell(1);
                String jobName = "";
                if (isMergedRegion(sheet, i, jobCell.getColumnIndex())){
                    jobName = getMergedRegionValue(sheet, i, jobCell.getColumnIndex());
                }else{
                    jobName = jobCell.getStringCellValue();
                }
                Cell positionCell = row.getCell(2);
                String position = positionCell.getStringCellValue();

                log.info("{}, {}, {}", industName, jobName, position);


            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static boolean isMergedRegion(Sheet sheet,int row ,int column) {
        int sheetMergeCount = sheet.getNumMergedRegions();
        for (int i = 0; i < sheetMergeCount; i++) {
            CellRangeAddress range = sheet.getMergedRegion(i);
            int firstColumn = range.getFirstColumn();
            int lastColumn = range.getLastColumn();
            int firstRow = range.getFirstRow();
            int lastRow = range.getLastRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    return true;
                }
            }
        }
        return false;
    }

    public static String getMergedRegionValue(Sheet sheet ,int row , int column){
        int sheetMergeCount = sheet.getNumMergedRegions();
        for(int i = 0 ; i < sheetMergeCount ; i++){
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();
            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell) ;
                }
            }
        }
        return null ;
    }

    public static String getCellValue(Cell cell){

        if(cell == null) {
            return "";
        }

        if(cell.getCellType() == CellType.STRING){

            return cell.getStringCellValue();

        }else if(cell.getCellType() == CellType.BOOLEAN){

            return String.valueOf(cell.getBooleanCellValue());

        }else if(cell.getCellType() == CellType.FORMULA){

            return cell.getCellFormula() ;

        }else if(cell.getCellType() == CellType.NUMERIC){

            return String.valueOf(cell.getNumericCellValue());

        }
        return "";
    }
}
