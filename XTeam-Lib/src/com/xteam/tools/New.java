/*
 * 创建时间：2010-10-26 上午10:20:19
 * 工程名称：CommonTools
 * 文   件  名：com.xuming.tools.New.java
 * Author:Leo
 * 
 */
package com.xteam.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Leo
 * 
 */
public class New {
	/**
	 * new 建立一个带泛型的ArrayList
	 * 
	 * @param <T>
	 * @return List<T>
	 * @author:Leo
	 */
	public static <T> List<T> ArrayList() {
		return new ArrayList<T>();
	}
	
	/**
	 * new 一个泛型的LinkedList
	 * @param <T>
	 * @return List<T>
	 * @author:Leo
	 */
	public static <T> List<T> LinkedList(){
		return new LinkedList<T>();
	}
	
	/**
	 * new 一个hashset
	 * @param <T>
	 * @return Set<T>
	 * @author:Leo
	 */
	public static <T> Set<T> HashSet(){
		return new HashSet<T>();
	}
	
	/**
	 * new 一个LinkedHashSet
	 * @param <T>
	 * @return Set<T>
	 * @author:Leo
	 */
	public static <T> Set<T> LinkedHashSet(){
		return new LinkedHashSet<T>();
	}
	
	/**
	 * new 一个TreeSet
	 * @param <T>
	 * @return Set<T>
	 * @author:Leo
	 */
	public static <T> Set<T> TreeSet(){
		return new TreeSet<T>();
	}
	
	/**
	 * new 一个hashmap
	 * @param <K>
	 * @param <V>
	 * @return Map<K,V>
	 * @author:Leo
	 */
	public static <K,V> Map<K,V> HashMap(){
		return new HashMap<K, V>();
	}
	
	/**
	 * new 一个LinkedHashMap
	 * @param <K>
	 * @param <V>
	 * @return Map<K,V>
	 * @author:Leo
	 */
	public static <K,V> Map<K,V> LinkedHashMap(){
		return new LinkedHashMap<K, V>();
	}
	
	
}
