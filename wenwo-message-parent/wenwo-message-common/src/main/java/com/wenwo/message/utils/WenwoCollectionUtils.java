/**
 * 
 */
package com.wenwo.message.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.util.CollectionUtils;

/**
 * 
 * @author "StanleyDing"
 * @date 2013-9-27
 * @since 2.0
 * 
 * History：
 * Date,	By,		What
 * 2013-9-27,	"StanleyDing", 	Create
 */
public class WenwoCollectionUtils {
	public static <K, V> void addToMappedList(Map<K, List<V>> mappedList, K key, V value) {
		List<V> list = mappedList.get(key);
		if (list == null) {
			list = new ArrayList<V>();
			mappedList.put(key, list);
		}
		list.add(value);
	}
	
	public static <K, V> void addToMappedSet(Map<K, Set<V>> mappedSet, K key, V value) {
		Set<V> list = mappedSet.get(key);
		if (list == null) {
			list = new HashSet<V>();
			mappedSet.put(key, list);
		}
		list.add(value);
	}
	
	public static <K, V> Map<K, V> asMap(final java.util.Collection<V> coll,
	        final Class<K> keyType,
	        final Class<V> valueType,
	        final String keyMethodName) {

	    final HashMap<K, V> map = new HashMap<K, V>();
	    Method method = null;

	    if (CollectionUtils.isEmpty(coll)) return map;
	    
	    try {
	        // return the Method to invoke to get the key for the map
	        method = valueType.getMethod(keyMethodName);
	    }
	    catch (final NoSuchMethodException e) {
			throw new IllegalArgumentException("no method named "+keyMethodName, e);
	    }
	    try {
	        for (final V value : coll) {

	            Object object;
	            object = method.invoke(value);
	            @SuppressWarnings("unchecked")
	            final K key = (K) object;
	            map.put(key, value);
	        }
	    }
	    catch (final Exception e) {
	       throw new IllegalArgumentException("get key failed.", e);
	    }
	    return map;
	}
	
	/**
	 * 合并两个列表，并根据 distinctFieldName 字段去重。
	 * 如果list1和list2包含distinctFieldName相等的元素，保留list1中的元素，丢弃list2中的元素
	 * @param list1
	 * @param list2
	 * @param distinctFieldName
	 * @return
	 */
	public static <T> List<T> mergeList(List<T> list1, List<T> list2, String distinctFieldName) {
		if(list1==null) {
			return list2;
		} else if(list2==null) {
			return list1;
		}
		HashMap<Object, T> map = new HashMap<Object, T>();
		
		try {
	        for (final T value : list1) {
	            Object key = PropertyUtils.getProperty(value, distinctFieldName);
	            map.put(key, value);
	        }
	        for (final T value : list2) {
	            Object key = PropertyUtils.getProperty(value, distinctFieldName);
	            if(!map.containsKey(key)) {
	            	map.put(key, value);
	            }
	        }
	    }
	    catch (final Exception e) {
	       throw new IllegalArgumentException("get key failed:"+distinctFieldName, e);
	    }
		return new ArrayList<T>(map.values());
	}
	
	private WenwoCollectionUtils(){}
}
