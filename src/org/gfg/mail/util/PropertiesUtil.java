/*
 * Copyright (c) 2012 Lei Hu. All rights reserved.
 * Lei Hu PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.gfg.mail.util;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 汇总一些对于Properties的快捷操作
 *
 * @author L <qiyuan4f@gmail.com>
 * @version 1.0
 * @date 2012-10-11 01:57
 */
public class PropertiesUtil {
	/**
	 * 合并两个属性集合，其中副属性会作为新增数据附加到主属性，作为结果返回
	 * 
	 * @param a 主属性
	 * @param b 副属性
	 * @return 合并后的集合
	 */
	public static Properties merge(Properties a, Properties b) {
		Set<Map.Entry<Object, Object>> entrySet = b.entrySet();

		for (Map.Entry<Object, Object> item : entrySet) {
			a.put(item.getKey(), item.getValue());
		}

		return a;
	}
}
