/*
 * Copyright (c) 2012 Lei Hu. All rights reserved.
 * Lei Hu PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.gfg.mail.sender;

import com.sun.mail.smtp.SMTPTransport;

import javax.mail.Message;
import javax.mail.SendFailedException;

/**
 * 实现使用SMTP协议发送邮件
 *
 * @author L <qiyuan4f@gmail.com>
 * @version 1.0 <2012-10-24 00:14>
 */
public class SMTPSender extends Sender {
	private static final String SMTP = "smtp";
	private SMTPTransport transport = null;

	public SMTPSender() {

	}

	@Override
	public boolean open() {
		boolean flag = true;

		try {
			// 创建transport实例
			this.transport = (SMTPTransport) this.session.getTransport(SMTP);
			this.transport.connect();
		} catch (Exception e) {
			flag = false;
			this.close();
		}

		return flag;
	}

	@Override
	public void close() {
		if (this.transport != null) {
			try {
				this.transport.close();
			} catch (Exception e) {
				// 忽略异常
			} finally {
				transport = null;
			}
		}
	}


	@Override
	public boolean send(Message message) throws SendFailedException {
		boolean flag = true;

		try {
			this.transport.sendMessage(message, message.getAllRecipients());
		} catch (Exception e) {
			flag = false;
		}

		return flag;
	}
}
