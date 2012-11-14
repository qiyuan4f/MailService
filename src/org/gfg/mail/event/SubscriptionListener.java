/*
 * Copyright (c) 2012 Lei Hu. All rights reserved.
 * Lei Hu PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.gfg.mail.event;

import org.gfg.mail.entity.Mail;

import java.util.List;

/**
 * 可订阅类接口，实现订阅接口的规范
 *
 * @author L <qiyuan4f@gmail.com>
 * @version 1.0 <2012-10-24 00:14>
 */
@Deprecated
public interface SubscriptionListener {
	/**
	 * 推送电子邮件给订阅者
	 * 
	 * @param mailList 被推送给订阅者的电子邮件列表
	 */
	public void onDeliver(List<Mail> mailList);
}
