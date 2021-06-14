package com.example.socksdemo.ExcelUtil;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ExcelOperationHelper {
	
	private static Logger logger = LoggerFactory.getLogger(ExcelOperationHelper.class);
	
	public static Workbook getWorkBook(MultipartFile file) {
		// 获得文件名
		String fileName = file.getOriginalFilename();
		// 创建Workbook工作薄对象，表示整个excel
		Workbook workbook = null;
		try {
			// 获取excel文件的io流
			
			InputStream is = file.getInputStream();
			// 根据文件后缀名不同(xls和xlsx)获得不同的Workbook实现类对象
			if (fileName.endsWith("xls")) {
				// 2003
				workbook = new HSSFWorkbook(is);
			} else if (fileName.endsWith("xlsx")) {
				// 2007
				workbook = new XSSFWorkbook(is);
			}
		} catch (IOException e) {
			logger.info(e.getMessage());
		}
		return workbook;
	}

	public static String getCellValue(Cell cell) {
		String cellValue = "0";
		if (cell == null) {
			return cellValue;
		}
		// 判断数据的类型
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC: // 数字
			if (HSSFDateUtil.isCellDateFormatted(cell)) {  
                //如果是date类型则 ，获取该cell的date值  
                Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());  
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");  
                cellValue = format.format(date);;  
            }else {// 纯数字  
                BigDecimal big=new BigDecimal(cell.getNumericCellValue());  
                cellValue = big.toString();  
                //解决1234.0  去掉后面的.0  
                if(null!=cellValue&&!"".equals(cellValue.trim())){  
                     String[] item = cellValue.split("[.]");  
                     if(1<item.length&&"0".equals(item[1])){  
                    	 cellValue=item[0];  
                     }  
                }  
            }  
            break;  
		case Cell.CELL_TYPE_STRING: // 字符串
			cellValue = String.valueOf(cell.getStringCellValue());
			break;
		case Cell.CELL_TYPE_BOOLEAN: // Boolean
			cellValue = " "+ cell.getBooleanCellValue();  
            break; 
		case Cell.CELL_TYPE_FORMULA: // 公式
			//读公式计算值  
			cellValue = String.valueOf(cell.getNumericCellValue());  
            if (cellValue.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串  
            	cellValue = cell.getStringCellValue().toString();  
            }  
            break;  
		case Cell.CELL_TYPE_BLANK: // 空值
			cellValue = "0";
			break;
		case Cell.CELL_TYPE_ERROR: // 故障
			cellValue = "";  
	        logger.error("excel出现故障");  
			break;
		default:
			cellValue = cell.getStringCellValue().toString();  
		}
		return cellValue;
	}
	
	public static void createRowCell(XSSFWorkbook workBook,XSSFRow row , int columnCnt) {
		for (int i = 0; i < columnCnt; i++) {
			Cell cell =row.createCell(i);
			XSSFCellStyle cellStyle = workBook.createCellStyle(); 
			cellStyle.setBorderBottom(cellStyle.BORDER_THIN); // 下边框
			cellStyle.setBorderLeft(cellStyle.BORDER_THIN);// 左边框  
			cellStyle.setBorderTop(cellStyle.BORDER_THIN);// 上边框  
			cellStyle.setBorderRight(cellStyle.BORDER_THIN);// 右边框  
			cell.setCellStyle(cellStyle); 
		}
	}
	

	
}
