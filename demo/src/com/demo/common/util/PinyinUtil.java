package com.demo.common.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sourceforge.pinyin4j.PinyinHelper;

public class PinyinUtil {

	/**
	 * 汉字转拼音
	 * 
	 * @param name汉字字符串
	 * @return
	 */
	public static List<String> nameToPinyin(String name) {
		List<String> list = new ArrayList<String>();
		Map<Integer, List<String>> pinMap = new LinkedHashMap<Integer, List<String>>();
		if (name.matches("^[\u4E00-\u9FA5]+$")) {
			char[] nameChars = name.toCharArray();
			for (int i = 0; i < nameChars.length; i++) {
				pinMap.put(i, Arrays.asList(getPinyin(nameChars[i])));
			}
			list = toCombList(pinMap);
		}
		return list;
	}

	/**
	 * 汉字转拼音
	 * @param name 汉字字符串
	 * @return
	 */
	public static String[] nameToPinyinArray(String name) {
		if (name.matches("^[\u4E00-\u9FA5]+$")) {
			char[] nameChars = name.toCharArray();
			String[][] pinMap = new String[nameChars.length][];
			for (int i = 0; i < nameChars.length; i++) {
				pinMap[i] = getPinyin(nameChars[i]);
			}
			String[] arrs = toCombList(pinMap);
			return arrs;
		}

		return null;
	}

	/**
	 * 顺序组合 搭配可能的 拼音组合
	 * 
	 * @param arrs
	 * @return 返回所有可能的搭配
	 */
	public static String[] toCombList(String[]... arrs) {
		String[] pinys = {};
		for (int i = 0; i < arrs.length; i++) {
			if (pinys.length == 0) {
				pinys = new String[arrs[i].length];
				System.arraycopy(arrs[i], 0, pinys, 0, arrs[i].length);
			} else {
				String[] pinyTmp = pinys.clone();
				for (int j = 0; j < arrs[i].length; j++) {
					for (int k = 0; k < pinyTmp.length; k++) {
						if (pinys[k].equals(pinys[k] + arrs[i][j])) {
							continue;
						} else {
							pinys = addToArr(pinys, pinys[k] + arrs[i][j]);
						}
					}
				}
				for (int j = 0; j < pinyTmp.length; j++) {
					pinys = removeFromArr(pinys, pinyTmp[j]);
				}
			}
		}
		return pinys;
	}

	/**
	 * 移除数组中元素
	 * 
	 * @param arr
	 *            操作的数组
	 * @param s
	 *            移除的元素值
	 * @return
	 */
	public static String[] removeFromArr(String[] arr, String s) {
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals(s)) {
				for (int j = i + 1; j < arr.length; j++) {
					arr[j - 1] = arr[j];
				}
				break;
			}
		}
		String[] arrs = new String[arr.length - 1];
		System.arraycopy(arr, 0, arrs, 0, arrs.length);
		return arrs;
	}

	/**
	 * 向数组末尾增加一位
	 * 
	 * @param arr
	 * @param s
	 * @return
	 */
	public static String[] addToArr(String[] arr, String s) {
		String[] arrs = new String[arr.length + 1];
		System.arraycopy(arr, 0, arrs, 0, arr.length);
		arrs[arrs.length - 1] = s;
		return arrs;
	}

	/**
	 * Map 顺序组合 搭配可能的 拼音组合
	 * 
	 * @param map
	 * @return
	 */
	public static List<String> toCombList(Map<Integer, List<String>> map) {
		List<String> listStr = new ArrayList<String>();
		Set<Entry<Integer, List<String>>> set = map.entrySet();
		Iterator<Entry<Integer, List<String>>> iterator = set.iterator();
		while (iterator.hasNext()) {
			Entry<Integer, List<String>> entry = iterator.next();
			List<String> ls = entry.getValue();
			if (listStr.size() == 0) {
				listStr.addAll(ls);
			} else {
				List<String> tmpList = new ArrayList<String>();
				tmpList.addAll(listStr);
				for (String s : ls) {
					for (int i = 0; i < tmpList.size(); i++) {
						String tmp = listStr.get(i);
						if (listStr.contains(tmp + s)) {
							continue;
						} else {
							listStr.add(tmp + s);
						}
					}
				}
				listStr.removeAll(tmpList);
			}
		}
		return listStr;
	}

	/**
	 * 将字符转换成 可能的拼音
	 * 
	 * @param ch
	 * @return
	 */
	public static String[] getPinyin(char ch) {
		String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(ch);
		String[] listArr = {};
		for (int i = 0; i < pinyins.length; i++) {
			String py = pinyins[i].substring(0, pinyins[i].length() - 1);
			boolean has = false;
			for (int j = 0; j < listArr.length; j++) {
				if (listArr[j].equals(py)) {
					has = true;
					break;
				}
			}
			if (!has) {
				String[] tmp = listArr.clone();
				listArr = new String[listArr.length + 1];
				System.arraycopy(tmp, 0, listArr, 0, tmp.length);
				listArr[listArr.length - 1] = py;
			}
		}
		return listArr;
	}

	public static void main(String[] args) {
		long begin = System.currentTimeMillis();
		List<Map<String, String>> list = new ArrayList<>();
		System.out.println(begin);
		for (int m = 0; m < 30000; m++) {
			
			
			String name = "陈玉红";
			if(m==1999){
				name = "何少伟";
			}
			if(m==2999){
				name = "和稍微";
			}
			String[] arrs = nameToPinyinArray(name);
			for (int i = 0; i < arrs.length; i++) {
				Map<String, String> map = new HashMap<>();
				map.put("name", name);
				map.put("value", arrs[i]);
//				System.out.println(arrs[i]);
				list.add(map);
			}
		}
		long end = System.currentTimeMillis();
		System.out.println(end-begin);
		
		List<String> matched = new ArrayList<>();
		for (Map<String, String> map : list) {
			if(map.get("value").startsWith("chenyu")){
				String name = map.get("name");
				System.out.println(name);
				matched.add(name);
				
//				break;
			}
		}
		long end1 = System.currentTimeMillis();
		System.out.println(end1-end);
		System.out.println(matched);
	}
}
