/*
 * Copyright (c) 2012 Lei Hu. All rights reserved.
 * Lei Hu PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.gfg.mail.config;

import java.util.Properties;

/**
 * 存储用户配置对于系统配置信息
 *
 * @author L <qiyuan4f@gmail.com>
 * @version 1.0 <2012-10-24 00:14>
 */
public interface Configuration {
	/**
	 * 工厂方法，创建属性集实体
	 * @return 属性集实体类，存储属性键值对
	 */
	public Properties makeProperties();
}
