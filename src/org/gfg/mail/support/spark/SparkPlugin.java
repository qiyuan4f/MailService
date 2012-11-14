/*
 * Copyright (c) 2012 Lei Hu. All rights reserved.
 * Lei Hu PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.gfg.mail.support.spark;

import org.gfg.mail.config.IMAPServerConfig;
import org.gfg.mail.config.ServerConfig;
import org.gfg.mail.entity.Account;
import org.jivesoftware.spark.SparkManager;
import org.jivesoftware.spark.plugin.Plugin;

import javax.swing.*;

/**
 * 实现基于Spark的新邮件提醒插件
 *
 * @author L <qiyuan4f@gmail.com>
 * @version 1.0 <2012-10-24 00:14>
 */
public class SparkPlugin implements Plugin {
	private Account account;
	private Monitor monitor;

	@Override
	public boolean canShutDown() {
		return true;
	}

	@Override
	public void initialize() {
		SparkManager.getPreferenceManager().addPreference(
				new SparkPreference());
		// 初始化账户信息
		String username = SparkManager.getSessionManager().getUsername();
		String password = SparkManager.getSessionManager().getPassword();
		String serverAddress = SparkManager.getSessionManager()
				.getServerAddress();

		// 初始化服务器配置
		ServerConfig server = new IMAPServerConfig();
		server.setAddress("imap." + serverAddress);

		this.account = new Account();
		this.account.setReceiverServer(server);
		this.account.setMailAddress(username + "@" + serverAddress);
		this.account.setUsername(username);
		this.account.setPassword(password);
		this.account.setWebMailServerUrl("http://mail." + serverAddress);

		try {
			this.monitor = new Monitor(account);
			monitor.enable(true);
		} catch (Exception e) {
			System.out.println("ERROR: New Mail Listener Plugin start failed.");
		}
	}

	@Override
	public void shutdown() {
		if (monitor != null) {
			monitor.dispose();
		}
	}

	@Override
	public void uninstall() {
		JOptionPane.showMessageDialog(null, "Plugin has been uninstalled");
	}
}