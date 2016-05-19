package com.demo.common.util.excel;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Name;

import com.demo.common.bean.ComboBean;
import com.demo.common.constant.BeanName;
import com.demo.common.init.Context;
import com.demo.common.model.BasicInfo;
import com.demo.common.service.CommonService;
import com.demo.common.util.DateUtils;
import com.demo.common.util.StringUtils;

/**
 * excel文件导出工具
 * @author heshaowei
 *
 */
public class ExportExcelUtil {
	private static final String COLUMN_FIELDS = "columnFields";
	
	private static final String date_format = "yyyy-MM-dd HH:mm:ss";
	
	
	private static CommonService commonService;
	
	private static final Logger LOGGER = Logger.getLogger(ExportExcelUtil.class);
	
	/**
	 * 导出只有唯一标签页sheet的excel
	 * 
	 * @param listData 数据集合
	 * @param clz 数据类型
	 * @param response 
	 */
	public static <T> void exportExcel(List<T> listData,HttpServletRequest request,HttpServletResponse response){
		LOGGER.info("-----开始导出EXCEL----");
		
		ExcelDataInfo excelDataInfo = new ExcelDataInfo("sheet1", listData);
		List<ExcelDataInfo> excelDataInfos = new ArrayList<>();
		excelDataInfos.add(excelDataInfo);
		exportSheetsExcel(excelDataInfos,request, response);
		
		LOGGER.info("-----导出EXCEL结束----");
	}
	
	
	/**
	 * 
	 * @param listData
	 * @param response
	 * @param headNames 标题名称
	 * @param columnFields 列属性名称
	 */
	public static <T> void exportExcel(Map<String, List<?>> map,HttpServletRequest request,HttpServletResponse response){
		
		Iterator<String> it = map.keySet().iterator();
		List<ExcelDataInfo> excelDataInfos = new ArrayList<>();
		while(it.hasNext()){
			String key = it.next();
			List<?> listData = map.get(key);
			ExcelDataInfo excelDataInfo = new ExcelDataInfo(key, listData);
			excelDataInfos.add(excelDataInfo);
			
		}
		
		exportSheetsExcel(excelDataInfos, request, response);
	}
	

	/**
	 * 导出指定页签的excel
	 * @param excelDataInfos
	 * @param request
	 * @param response
	 */
	public static <T> void exportSheetsExcel(List<ExcelDataInfo> excelDataInfos,HttpServletRequest request,HttpServletResponse response){
		HSSFWorkbook wb = new HSSFWorkbook();
		
		for (ExcelDataInfo excelDataInfo : excelDataInfos) {
			String key = excelDataInfo.getKey();
			List<?> listData = excelDataInfo.getListData();
			if(null==listData||listData.size()==0){
				continue;
			}
			
			Class clz = listData.get(0).getClass();
			//获取列名称及字段名称
			Field[] fields = clz.getDeclaredFields();
			Map<String, List<String>> columnMap = getColumnMap(fields,null);
			List<String> columnField = columnMap.get(COLUMN_FIELDS);
			createOneSheetExcel(wb, listData,key, request, response, columnField);
		}

		//输出文件
		String fileName = request.getParameter("fileName");
		if(null==fileName){
			String url = request.getRequestURL().toString();
			fileName = url.substring(url.lastIndexOf("/")+1, url.lastIndexOf(".")>-1?url.lastIndexOf("."):url.length());
			fileName += UUID.randomUUID();
		}
		exportFile(request,response,wb,fileName);
	}
	
	/**
	 * 创建一个页签
	 * @param wb
	 * @param listData
	 * @param request
	 * @param response
	 * @param columnFields
	 */
	private static <T> void createOneSheetExcel(HSSFWorkbook wb,List<T> listData,String sheetName,HttpServletRequest request,HttpServletResponse response,
			List<String> columnFields){
		HSSFSheet sheet = wb.createSheet(sheetName);
	
		
		//设置表头的基本样式
		HSSFCellStyle headStyle = setHeadStyle(wb);

		//创建标题
		createHeadRow(sheet,headStyle,columnFields,listData.get(0).getClass());
		
		//字体
		HSSFFont font = wb.createFont();
		font.setFontName("楷体");
		//偶数行样式
		HSSFCellStyle cellStyle1 = wb.createCellStyle();
		cellStyle1.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle1.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle1.setFillPattern(HSSFCellStyle.BORDER_THIN);
		cellStyle1.setFillForegroundColor(HSSFColor.WHITE.index);
		cellStyle1.setFont(font);
		//奇数行样式
		HSSFCellStyle cellStyle2 = wb.createCellStyle();
		cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle2.setFillPattern(HSSFCellStyle.BORDER_THIN);
		cellStyle2.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		cellStyle2.setFont(font);
		
		Pattern pa1 = Pattern.compile("^((?:19|20)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$");//yyyy-mm-dd日期
		
		LOGGER.info("开始创建单元格:"+DateUtils.getCurTime());
		
		SimpleDateFormat format = new SimpleDateFormat(date_format);
		T t = null;
		for (int i = 0; i < listData.size(); i++) {
			HSSFRow row = sheet.createRow(i + 1);
			t = listData.get(i);
			for (int j = 0; j < columnFields.size(); j++) {
				int col = (short) j;
				
				//设置单元格的样式
				HSSFCell cell = row.createCell(col);
				
				//设置单元格的字符编码格式
				cell.setCellType(HSSFCell.ENCODING_UTF_16);
				//设置单元格值
				Object obj = null;
				try {
					Field field = t.getClass().getDeclaredField(columnFields.get(j));
					field.setAccessible(true);
					obj = field.get(t);
					//得到列值
					String strValue = "";
//					String type = field.getType().getSimpleName();
					if(obj instanceof Date){
						Date dateValue = (Date)obj;
						String val = format.format(dateValue);
						//日期格式转换成特定的文本格式
						cell.setCellValue(new HSSFRichTextString(val));
						strValue = val;
					}else if(obj instanceof Integer||obj instanceof Long||obj instanceof Float||obj instanceof Double){
						Double val = Double.valueOf(StringUtils.toStr(obj));
						cell.setCellValue(val);
						strValue = val.toString();
						cellStyle1.setDataFormat(wb.createDataFormat().getFormat("0.000"));
						cellStyle2.setDataFormat(wb.createDataFormat().getFormat("0.000"));
					}else{
						String val = StringUtils.toStr(obj);
						//设置单元格的值
						cell.setCellValue(new HSSFRichTextString(val));
						strValue = val;
					}
					
					//设置不同行的样式区分
					if(i%2!=0){
						cell.setCellStyle(cellStyle2);
					}else{
						cell.setCellStyle(cellStyle1);
					}
					
					//判断是否为锁定值，则限制用户不能更改
					ExcelHeadName excelHeadName = field.getAnnotation(ExcelHeadName.class);
					if(null!=excelHeadName&&excelHeadName.isLocked()){
						String[] validDatas = {strValue};
						// 只对(0，0)单元格有效  
				        CellRangeAddressList regions = new CellRangeAddressList(i+1, i+1, j, j);  
				        // 生成下拉框内容  
				        DVConstraint constraint = DVConstraint.createExplicitListConstraint(validDatas);  
				        // 绑定下拉框和作用区域  
				        HSSFDataValidation data_validation = new HSSFDataValidation(regions,constraint);
				        // 对sheet页生效  
				        sheet.addValidationData(data_validation);  
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//设置下拉框验证
		for(int i=0;i<columnFields.size();i++){
			try {
				Field field = t.getClass().getDeclaredField(columnFields.get(i));
				ExcelHeadName excelHeadName = field.getAnnotation(ExcelHeadName.class);
				String[] validDatas = {};
				if(null!=excelHeadName&&excelHeadName.validDatas().length>0){
					validDatas = excelHeadName.validDatas();
				}
				if(null!=excelHeadName&&excelHeadName.basicValidDatas().length()>0){
					commonService = (CommonService) Context.getInstance().getBean(BeanName.COMMON_SERVICE);
					List<ComboBean<BasicInfo>> datas = commonService.quertBasicInfo(excelHeadName.basicValidDatas());
					String[] validDatas1 = new String[datas.size()];
					for (int n=0;n<datas.size();n++) {
						ComboBean<BasicInfo> comboBean = datas.get(n);
						validDatas1[n] = comboBean.getName();
					}
					validDatas = (String[]) ArrayUtils.addAll(validDatas, validDatas1);
				}
				if(null!=excelHeadName&&excelHeadName.validQueryMethod().length()>0){
					String methodName = excelHeadName.validQueryMethod();
					
					commonService = (CommonService) Context.getInstance().getBean(BeanName.COMMON_SERVICE);
					try {
						Method method = commonService.getClass().getDeclaredMethod(methodName);
						method.setAccessible(true);
						Object listValues = method.invoke(commonService);
						if(listValues instanceof String[]){
							validDatas = (String[]) listValues;
						}else{
							LOGGER.error("方法返回类型应该为字符串数组!");
						}
					} catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						LOGGER.error(e.getMessage(),e);
					}
					
				}
				
				if(validDatas.length>0){
					HSSFSheet hidden = wb.createSheet("hidden"+i);
					//数据源sheet页不显示
					
					wb.setSheetHidden(wb.getNumberOfSheets()-1, true);
					CellStyle style = wb.createCellStyle();
					style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
					style.setAlignment(CellStyle.ALIGN_CENTER);
					style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
					HSSFRow row = null;
					HSSFCell cell = null;
					for (int l = 0, length = validDatas.length; l < length; l++) {
						row = hidden.createRow(l);
						cell = row.createCell(0);
						cell.setCellValue(validDatas[l]);
					}
					Name namedCell = wb.createName();
					namedCell.setNameName("hidden"+i);
					namedCell.setRefersToFormula("hidden"+i+"!$A$1:$A$" + validDatas.length);
					DVConstraint constraint = DVConstraint
							.createFormulaListConstraint("hidden"+i+"!$A$1:$A$" + validDatas.length);
					// 只对(0，0)单元格有效  
			        CellRangeAddressList regions = new CellRangeAddressList(1, listData.size()==1?60000:listData.size(), i, i);  
			        // 生成下拉框内容  
//			        DVConstraint constraint = DVConstraint.createExplicitListConstraint(validDatas);  
			        // 绑定下拉框和作用区域  
			        HSSFDataValidation data_validation = new HSSFDataValidation(regions,constraint);
			        // 对sheet页生效  
			        sheet.addValidationData(data_validation);  
				}
			} catch (SecurityException | NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
		
		
		LOGGER.info("完成创建单元格:"+DateUtils.getCurTime());
		
	}
	
	private static void exportFile(HttpServletRequest request,HttpServletResponse response,HSSFWorkbook wb,String fileName){
		try {
			String tempFileName = fileName+"_"+UUID.randomUUID()+".xls";
			//将文件生成在临时文件夹
			String sysPath = request.getSession().getServletContext().getRealPath("");
			String tempFilePath = sysPath+"/file/temp/"+tempFileName;
			File tempFile = new File(tempFilePath);
			FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
			wb.write(fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
			
			//重定向文件路径
			response.sendRedirect(request.getContextPath()+"/file/temp/"+URLEncoder.encode(tempFileName, "UTF-8"));
			
			//删除临时文件
			/*if(tempFile.isFile()&&tempFile.exists()){
				boolean f = tempFile.delete();
				if(f){
					LOGGER.info("删除临时文件："+tempFilePath+"成功！");
				}else{
					LOGGER.info("删除临时文件："+tempFilePath+"失败！");
				}
			}*/
			
			/*response.reset();
			response.setContentType("application/vnd.ms-excel");
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Type","application/force-download");
			response.setHeader("Content-Type","application/vnd.ms-excel");
			setFileDownloadHeader(request, response, fileName+".xls");
			
			//下载临时文件
			FileInputStream fileInputStream = new FileInputStream(tempFile);
			OutputStream os = response.getOutputStream();
			byte[] bus = new byte[1024];
			int len;
			while((len = fileInputStream.read(bus,0,bus.length))!=-1){
				os.write(bus,0,len);
			}
			
			os.flush();
			os.close();*/
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
        final String userAgent = request.getHeader("USER-AGENT");
        try {
            String finalFileName = null;
            if(StringUtils.contains(userAgent, "MSIE")){//IE浏览器
                finalFileName = URLEncoder.encode(fileName,"UTF8");
            }else if(StringUtils.contains(userAgent, "Mozilla")){//google,火狐浏览器
                finalFileName = new String(fileName.getBytes(), "ISO8859-1");
            }else{
                finalFileName = URLEncoder.encode(fileName,"UTF8");//其他浏览器
            }
            response.setHeader("Content-Disposition", "attachment; filename=\"" + finalFileName + "\"");//这里设置一下让浏览器弹出下载提示框，而不是直接在浏览器中打开
        } catch (UnsupportedEncodingException e) {
        
        }
    }
	
	private static HSSFCellStyle setHeadStyle(HSSFWorkbook wb) {
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		cellStyle.setFillForegroundColor(HSSFColor.GREEN.index);
		cellStyle.setWrapText(true);
		HSSFFont font2 = wb.createFont();
		font2.setFontName("华文新魏");
		font2.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);//粗体显示
		font2.setFontHeightInPoints((short) 12);
		font2.setColor(HSSFColor.WHITE.index);
		cellStyle.setFont(font2);
		cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		return cellStyle;
	}

	private static void createHeadRow(HSSFSheet sheet,HSSFCellStyle headStyle, List<String> fieldNames,Class clz) {
		HSSFRow row2 = sheet.createRow(0);
		row2.setHeight((short) 800);
		for (int i = 0; i < fieldNames.size(); i++) {
			
			try {
				Field field = clz.getDeclaredField(fieldNames.get(i));
				ExcelHeadName excelHeadName = field.getAnnotation(ExcelHeadName.class);
				String headName = excelHeadName.name();
				int columnWidth = excelHeadName.columnWidth();
				sheet.setColumnWidth(i, columnWidth);
				HSSFCell cell = row2.createCell(i);
				cell.setCellStyle(headStyle);
				cell.setCellType(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue(new HSSFRichTextString(headName));
			} catch (SecurityException | NoSuchFieldException e) {
				LOGGER.error(e.getMessage(), e);
			}
			
		}
	}

	public static Map<String, List<String>> getColumnMap(Field[] fields, List<String> hideColumns){
		List<String> columnFields = new ArrayList<>();
		for (Field field : fields) {
			ExcelHeadName headname = field.getAnnotation(ExcelHeadName.class);
			if(null!=headname){
				
				String name = headname.name();
				String fieldName = field.getName();
				if(null!=hideColumns&&hideColumns.indexOf(fieldName)>-1){
					continue;
				}
				columnFields.add(fieldName);
			}
		}
		Map<String, List<String>> map = new HashMap<>();
		map.put(COLUMN_FIELDS, columnFields);
		return map;
	}
	
	public static void main(String[] args) {
		Pattern pa1 = Pattern.compile("^((?:19|20)\\d\\d)-(0[1-9]|1[012])-(0[1-9]|[12][0-9]|3[01])$");//yyyy-mm-dd日期

		System.out.println(pa1.matcher(null).find());
	}
}
