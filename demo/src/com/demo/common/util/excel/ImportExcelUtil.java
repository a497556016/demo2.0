package com.demo.common.util.excel;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.demo.common.exception.CommonException;
import com.demo.common.util.ArraysUtil;
import com.demo.common.util.StringUtils;

/**
 * excel文件导入解析工具
 * @author heshaowei
 *
 * @param <T>
 */
public class ImportExcelUtil<T> {
	private static final Logger LOGGER = Logger.getLogger(ImportExcelUtil.class);
	public static <T> List<T> parseExcel(InputStream in,Class<T> clz) throws CommonException{
		List<T> list = new ArrayList<>();
		Map<Integer, String> headerMap = new HashMap<Integer, String>();
		Row curRow = null;
		Cell curCell = null;
		try{
			Workbook workbook = WorkbookFactory.create(in);
			
			Sheet sheel1 = workbook.getSheetAt(0);
			Iterator rowIter = sheel1.iterator();
			//获取表头信息
			while(rowIter.hasNext()){
				curRow = (Row) rowIter.next();
				Iterator cellIter = curRow.cellIterator();
				// 解析表头
				if (curRow.getRowNum() == 0) {
					while(cellIter.hasNext()){
						curCell = (Cell) cellIter.next();
						String colName = curCell.getRichStringCellValue()
								.getString().trim();
						Integer colIndex = curCell.getColumnIndex();
						headerMap.put(colIndex,colName);
					}
				}else{
					T t = clz.newInstance();
					while(cellIter.hasNext()){
						curCell = (Cell) cellIter.next();
						Map<String, String> fieldMap = getFieldMap(t);
						setRowContent(fieldMap,curCell,headerMap,t);
					}
					//验证
					if(validateField(t)){
						list.add(t);
					}
				}
			}
		}catch(CommonException e1){
			throw e1;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
		return list;
	}
	
	private static <T> boolean validateField(T t) throws CommonException{
		Class clz = t.getClass();
		Field[] fields = clz.getDeclaredFields();
		for (Field field : fields) {
			ExcelHeadName headname = field.getAnnotation(ExcelHeadName.class);
			if(null!=headname){
				Object value = null;
				try {
					field.setAccessible(true);
					value = field.get(t);
				} catch (Exception e) {
					LOGGER.error(e.getMessage(), e);
				}
				boolean allowNull = headname.allowNull();
				boolean allowEmpty = headname.allowEmpty();
				String regex = headname.regex();
				
				if(null==value&&!allowNull){
					LOGGER.debug(field.getName()+"不能为null");
					throw new CommonException(headname.name()+"不能为null");
				}
				if((null==value||value.toString().trim().length()==0)&&!allowEmpty){
					LOGGER.debug(field.getName()+"不能为空");
					throw new CommonException(headname.name()+"不能为空");
				}
				if(regex.trim().length()>0&&(null==value||!value.toString().matches(regex))){
					LOGGER.debug(field.getName()+"的值"+value+"不符合表达式:"+regex);
					throw new CommonException(headname.name()+"的值"+value+"不符合表达式:"+regex);
					//throw new CommonException(field.getName()+"的值"+value+"不符合表达式:"+regex);
				}
			}
		}
		return true;
	}
	
	private static <T> Map<String, String> getFieldMap(T t){
		Map<String, String> fieldMap = new HashMap<>();
		Field[] fields = t.getClass().getDeclaredFields();
		for (Field field : fields) {
			ExcelHeadName headname = field.getAnnotation(ExcelHeadName.class);
			if(null!=headname){
				String name = headname.name();
				fieldMap.put(name, field.getName());
			}
		}
		return fieldMap;
	}
	
	private static <T> List<String> getNotLockedFieldList(T t){
		List<String> fieldList = new ArrayList<>();
		Field[] fields = t.getClass().getDeclaredFields();
		for (Field field : fields) {
			ExcelHeadName headname = field.getAnnotation(ExcelHeadName.class);
			if(null!=headname&&!headname.isLocked()){
				
				fieldList.add(field.getName());
			}
		}
		return fieldList;
	}
	
	private static <T> List<String> getLockedFieldNames(T t){
		List<String> fieldList = new ArrayList<>();
		Field[] fields = t.getClass().getDeclaredFields();
		for (Field field : fields) {
			ExcelHeadName headname = field.getAnnotation(ExcelHeadName.class);
			if(null!=headname&&headname.isLocked()){
				fieldList.add(field.getName());
			}
		}
		return fieldList;
	}
	
	private static <T> void setRowContent(Map<String, String> fieldMap,Cell curCell,
			Map<Integer, String> headerMap,T t) {
		try {
			String headName = headerMap.get(curCell.getColumnIndex());
			String fieldName = fieldMap.get(headName);
			if(null==headName||null==fieldName){
				LOGGER.info("导入excel没有读取到该列，该列为空。");
				return;
			}
			Field field = t.getClass().getDeclaredField(fieldName);
			
			ExcelHeadName headname = field.getAnnotation(ExcelHeadName.class);
			
			String type = headname.type();
			String fmt = headname.fmt();
			
			field.setAccessible(true);
			int type1 = curCell.getCellType();
			Object value = null;
			
			
			switch(type1){
				case Cell.CELL_TYPE_STRING : {
					value = curCell.getRichStringCellValue();
					break;
				}
				case Cell.CELL_TYPE_NUMERIC : {
					if(type.equals(ExcelHeadName.DATE)){
						value = curCell.getDateCellValue();
					}else{
						value = curCell.getNumericCellValue();
						value = StringUtils.doubleToString((Double)value);
					}
					//当小数位后面为0时不保留小数位
					String vStr = StringUtils.toStr(value);
					if(vStr.matches("[0-9]+\\.[0]+")){
						Double v = (Double)value;
						value = v.intValue();
					}
					/*if(type.equals(ExcelHeadName.INT_STRING)){
						Double v = (Double)value;
						value = v.intValue();
					}*/
					break;
				}
				case Cell.CELL_TYPE_FORMULA : {
					value = curCell.getCellFormula();
					break;
				}
				case Cell.CELL_TYPE_ERROR : {
					value = curCell.getErrorCellValue();
					break;
				}
				case Cell.CELL_TYPE_BOOLEAN : {
					value = curCell.getBooleanCellValue();
					break;
				}
				case Cell.CELL_TYPE_BLANK: {
					value = "";
					break;
				}
				default : {
					value = curCell.getRichStringCellValue();
				}
			}
			
			try {
				if(ExcelHeadName.STRING.equals(type)||ExcelHeadName.INT_STRING.equals(type)){
					field.set(t, value.toString());
				}else if(ExcelHeadName.INTEGER.equals(type)){
					field.setInt(t, Integer.parseInt(value.toString()));
				}else if(ExcelHeadName.DOUBLE.equals(type)){
					field.setDouble(t, Double.parseDouble(value.toString()));
				}else if(ExcelHeadName.FLOAT.equals(type)){
					if(null==value||value.toString().trim().equals("")){
						value = 0;
					}
					Float f = Float.parseFloat(value.toString());
					field.set(t, f);
				}else if(ExcelHeadName.DATE.equals(type)){
					Date date = (Date)value;
					SimpleDateFormat sdf = new SimpleDateFormat(fmt);
					field.set(t, sdf.format(date));
				}else{
					field.set(t, value);
				}
				
			} catch (IllegalArgumentException | IllegalAccessException e) {
				LOGGER.error(e.getMessage(), e);
			}
			
		} catch (NoSuchFieldException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (SecurityException e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 * @param listIm
	 * @param list
	 * @return 返回错误码：</br>
	 * 000000：成功</br>
	 * 000001：输入参数为空！</br>
	 * 000002：导入的列表长度与实际不符</br>
	 * 000003：导入的列表与实际匹配的数据没有完全一一对应</br>
	 * 000004：导入数据有重复数据
	 */
	public static <T> String copyDataInto(
			List<T> listIm,
			List<T> list) {
		if(ArraysUtil.isEmpty(listIm)||ArraysUtil.isEmpty(list)){
			LOGGER.error("输入参数为空！");
			return "000001";
		}
		if(listIm.size()!=list.size()){
			LOGGER.error("输入参数为空！");
			return "000002";
		}
		
		List<String> fieldNames = getNotLockedFieldList(listIm.get(0));
		List<String> lockedFieldNames = getLockedFieldNames(listIm.get(0));
		
		//判断导入数据中根据主键判断是否有重复数据
		boolean f = isHaveDuplicateData(listIm,lockedFieldNames);
		if(f){
			return "000004";
		}
				
		int index = 0;
		for (T t : list) {
			for(T tIm : listIm){
				boolean isEqualLockedField = isEqualLockedField(tIm,t,lockedFieldNames);
				if(isEqualLockedField){
					putInFields(tIm,t,fieldNames);
					index++;
				}
			}
		}
		if(index==listIm.size()){
			return "000000";
		}else{
			return "000003";
		}
	}
	
	public static <T> void putInFields(T t1,T t2,List<String> fieldNames){
		for (String name : fieldNames) {
			try {
				Field field1 = t1.getClass().getDeclaredField(name);
				Field field2 = t2.getClass().getDeclaredField(name);
				field1.setAccessible(true);
				field2.setAccessible(true);
				Object obj1 = field1.get(t1);
				field2.set(t2, obj1);
			} catch (IllegalArgumentException | SecurityException
					| IllegalAccessException | NoSuchFieldException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
	}
	
	public static <T> boolean isEqualLockedField(T t1,T t2,List<String> lockedFieldNames){
		int index = 0;
		for (String name : lockedFieldNames) {
			try {
				Field field1 = t1.getClass().getDeclaredField(name);
				Field field2 = t2.getClass().getDeclaredField(name);
				field1.setAccessible(true);
				field2.setAccessible(true);
				Object obj1 = field1.get(t1);
				Object obj2 = field2.get(t2);
				if(null!=obj1&&obj1.equals(obj2)){
					index++;
				}
			} catch (IllegalArgumentException | SecurityException
					| IllegalAccessException | NoSuchFieldException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return index==lockedFieldNames.size();
		
	}
	
	public static <T> boolean isHaveDuplicateData(List<T> list,List<String> lockedFieldNames){
		boolean f = false;
		
		try {
			Set<String> set = new HashSet<>();
			for (T t : list) {
				StringBuffer buffer = new StringBuffer();
				for (String name : lockedFieldNames) {
					Field field = t.getClass().getDeclaredField(name);
					field.setAccessible(true);
					Object obj = field.get(t);
					String str = null==obj?"null":obj.toString().trim();
					buffer.append(str);
				}
				set.add(buffer.toString());
			}
			if(set.size()<list.size()){
				f = true;
			}
		} catch (SecurityException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			LOGGER.error(e.getMessage(), e);
		}
		
		return f;
	}
	
	public static void main(String[] args) {
		System.out.println("sdasda!!".matches("[\\w]{6,30}"));
	}
}
