/*
 * Copyright (c) 2012 Lei Hu. All rights reserved.
 * Lei Hu PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.gfg.mail.auth;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 简单的用户名/密码认证方式
 *
 * @author L <qiyuan4f@gmail.com>
 * @version 1.0 <2012-10-24 00:14>
 */
public class SimpleAuthenticator extends Authenticator {
	private String user;
	private String password;

	public SimpleAuthenticator(String user, String password) {
		this.user = user;
		this.password = password;
	}

	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(this.user, this.password);
	}
}
