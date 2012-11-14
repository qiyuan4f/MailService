/*
 * Copyright (c) 2012 Lei Hu. All rights reserved.
 * Lei Hu PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.gfg.mail.entity;

import javax.mail.Message;

/**
 * 邮件实体类，存储一封邮件的完整信息，包括主题、内容、附件、收件人、发件人
 *
 * @author L <qiyuan4f@gmail.com>
 * @version 1.0 <2012-10-24 00:14>
 */
@Deprecated
public class Mail {
	/**
	 * 邮件消息内容实体
	 */
	private Message message;

	/**
	 * 邮件的唯一标识
	 */
	private String uid;
	
	public Mail() {
		
	}
	
	public Mail(String uid, Message message) {
		this.uid = uid;
		this.message = message;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}
}
