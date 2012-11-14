/*
 * Copyright (c) 2012 Lei Hu. All rights reserved.
 * Lei Hu PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.gfg.mail.config;

import java.util.Properties;

/**
 * 实现存储基于IMAP协议的发送服务器的配置信息
 *
 * @author L <qiyuan4f@gmail.com>
 * @version 1.0 <2012-10-24 00:14>
 */
public class IMAPServerConfig extends ServerConfig {
	public IMAPServerConfig() {
		this.encryptionType = EncryptionType.NONE;
		this.protocol = Protocol.IMAP;
	}

	@Override
	public Properties makeProperties() {
		Properties properties = super.makeProperties();

		switch (this.encryptionType) {
			case NONE:
				break;
			case TLS:
				properties.put("mail.imap.starttls.enable", "true");
				break;
			case SSL:
				properties.put("mail.imap.ssl.enable", "true");
				properties.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				properties.put("mail.imap.socketFactory.fallback", "false");
				break;
		}

		if (this.username != null) {
			properties.put("mail.imap.user", this.username);
		}

		if (this.address != null) {
			properties.put("mail.imap.host", this.address);
		}

		if (this.port != null) {
			properties.put("mail.imap.port", this.port);
		}

		if (this.timeout != null) {
			properties.put("mail.imap.timeout", this.timeout);
		}

		if (this.connectionTimeout != null) {
			properties.put("mail.imap.connectiontimeout", this.connectionTimeout);
		}

		return properties;
	}
}
