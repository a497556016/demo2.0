package com.demo.common.util;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class ArraysUtil<T> {

	public static <T> List<T> removeDuplicate(List<T> list) {
		HashSet<T> hashSet = new HashSet<>(list);
		list.clear();
		list.addAll(hashSet);
		return list;
	}

	public static <T> List<T> asList(T[] arrays) {
		List<T> list = new ArrayList<>();
		for(T t : arrays){
			list.add(t);
		}
		return list;
	}

	public static <T> boolean isEmpty(List<T> list){
		if(null==list||list.size()==0){
			return true;
		}else{
			return false;
		}
	}
	
	public static <T> String asStrBySeparator(List<T> list,String separator){
		StringBuffer sb = new StringBuffer();
		for (T t : list) {
			sb.append(StringUtils.toStr(t)+separator);
		}
		if(sb.length()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
}
