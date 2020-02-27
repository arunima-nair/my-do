package com.dt.deliveryorder.util;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;
import java.util.List;

public class ExcelFileWrapper {

   public interface  ExcelTypeConstants {
        static Integer STRING = 0;
         static Integer DATE = 1;
        static Integer INTEGER = 2;
    }

    private File file;
    private InpFileType inpFileType;
    private Row rowIterator;
    private HSSFWorkbook hssfWorkbook;
    private XSSFWorkbook xssfWorkbook;
    private List<String> csvData;
    private Integer totalRows;
    private boolean fileOpened = false;

  public ExcelFileWrapper(File file, InpFileType inpFileType) {
            this.file = file;
            this.inpFileType  = inpFileType;
            openExcelFile();
    }




    public  String getValueFromCell(Integer rowIndex, Integer colIndex, Integer type) {
        if (colIndex == -1 )
            return StringUtils.EMPTY;

        switch (this.inpFileType){
            case XLSX:
                XSSFCell xssfCell = xssfWorkbook.getSheetAt(0).getRow(rowIndex).getCell(colIndex);
                if (type == ExcelTypeConstants.STRING)
                    xssfCell.setCellType(CellType.STRING);
                return xssfCell.toString();
            case XLS:
                return hssfWorkbook.getSheetAt(0).getRow(rowIndex).getCell(colIndex).getStringCellValue();
            case CSV:
                if (colIndex < StringUtils.splitPreserveAllTokens(csvData.get(rowIndex),",").length){
                    return StringUtils.splitPreserveAllTokens(csvData.get(rowIndex),",")[colIndex];
                }
        }


        return StringUtils.EMPTY;
      }

    private boolean isNumericCell(String name) {
        return  StringUtils.equalsIgnoreCase(name,"NUMERIC");

    }

    private boolean isStringCell(String name) {
        return  StringUtils.equalsIgnoreCase(name,"STRING");

    }

    public Integer getTotalRows(){
         if (!this.fileOpened)
              openExcelFile();
         return this.totalRows;
      }

    public  void openExcelFile() {

          try {
              switch (this.inpFileType){
                  case XLSX:
                      OPCPackage pkg = OPCPackage.open(this.file);
                      xssfWorkbook =  new XSSFWorkbook(pkg);
                      this.totalRows =  xssfWorkbook.getSheetAt(0).getPhysicalNumberOfRows();
                      this.fileOpened = true;
                      break;
                  case XLS:
                      hssfWorkbook = new HSSFWorkbook(new FileInputStream(this.file));
                      this.totalRows = hssfWorkbook.getSheetAt(0).getPhysicalNumberOfRows();
                      this.fileOpened = true;
                      break;
                  case CSV:
                      csvData = FileUtils.readLines(this.file, Charset.defaultCharset());
                      this.totalRows = csvData.size();
                      this.fileOpened = true;
                      break;
              }
          } catch (Exception ex){
              throw new RuntimeException(ex);

          }
      }


}
