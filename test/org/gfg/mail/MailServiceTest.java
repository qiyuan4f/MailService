/*
 * Copyright (c) 2012 Lei Hu. All rights reserved.
 * Lei Hu PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.gfg.mail;

import org.gfg.mail.config.IMAPServerConfig;
import org.gfg.mail.config.ServerConfig;
import org.gfg.mail.entity.Account;
import org.gfg.mail.exception.NotSupportedException;
import org.gfg.mail.support.spark.Monitor;
import org.junit.*;

/**
 * @author L <qiyuan4f@gmail.com>
 * @version 1.0 <2012-10-24 00:14>
 */
public class MailServiceTest {
	Account account;
	Monitor monitor;

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * 综合测试
	 */
	@Test
	public void testService() throws InterruptedException, NotSupportedException {

		ServerConfig server;

		account = new Account();
		server = new IMAPServerConfig();
		server.setAddress("imap.163.com");
		account.setReceiverServer(server);
		account.setMailAddress("");
		account.setUsername("");
		account.setPassword("");
		account.setWebMailServerUrl("");
		monitor = new Monitor(account);
		monitor.enable(true);
		monitor.join();
		monitor.dispose();
	}
}
