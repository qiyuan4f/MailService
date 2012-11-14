/*
 * Copyright (c) 2012 Lei Hu. All rights reserved.
 * Lei Hu PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.gfg.mail.config;

import java.util.Properties;

/**
 * 实现存储基于POP3协议的发送服务器的配置信息
 *
 * @author L <qiyuan4f@gmail.com>
 * @version 1.0 <2012-10-24 00:14>
 */
public class POP3ServerConfig extends ServerConfig {
	public POP3ServerConfig() {
		this.encryptionType = EncryptionType.NONE;
		this.protocol = Protocol.POP3;
	}

	@Override
	public Properties makeProperties() {
		Properties properties = super.makeProperties();

		switch (this.encryptionType) {
			case NONE:
				break;
			case TLS:
				properties.put("mail.pop3.starttls.enable", "true");
				break;
			case SSL:
				properties.put("mail.pop3.ssl.enable", "true");
				properties.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				properties.put("mail.pop3.socketFactory.fallback", "false");
				break;
		}


		if (this.username != null) {
			properties.put("mail.pop3.user", this.username);
		}

		if (this.address != null) {
			properties.put("mail.pop3.host", this.address);
		}

		if (this.port != null) {
			properties.put("mail.pop3.port", this.port);
		}

		if (this.timeout != null) {
			properties.put("mail.pop3.timeout", this.timeout);
		}

		if (this.connectionTimeout != null) {
			properties.put("mail.pop3.connectiontimeout", this.connectionTimeout);
		}

		return properties;
	}
}
