/*
 * Copyright (c) 2012 Lei Hu. All rights reserved.
 * Lei Hu PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.gfg.mail.receiver;

import org.gfg.mail.config.POP3ServerConfig;
import org.gfg.mail.config.ServerConfig;
import org.gfg.mail.entity.Account;
import org.gfg.mail.receiver.Receiver.SearchType;
import org.junit.*;

import javax.mail.Message;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import static org.junit.Assert.fail;

public class POP3ReceiverTest {
	private static Map<Account, String> cases = new HashMap<Account, String>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Account account = null;
		ServerConfig server;
		
		//163 SSL
//		account = new Account();
//		server = new POP3ServerConfig();
//		server.setAddress("pop.ccntgrid.org");
//		server.setEncryptionType(ServerConfig.EncryptionType.SSL);
//		account.setReceiverServer(server);
//		account.setMailAddress("L@ccntgrid.org");
//		account.setUsername("L");
//		account.setPassword("");
//		cases.put(account, "不支持POP3 SSL协议");
		
		// 163
		account = new Account();
		server = new POP3ServerConfig();
		server.setAddress("pop.ccntgrid.org");
		account.setReceiverServer(server);
		account.setMailAddress("L@ccntgrid.org");
		account.setUsername("L");
		account.setPassword("");
		cases.put(account, "不支持POP3协议");
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testFetch() {
		for (Entry<Account, String> item : cases.entrySet()) {
			Receiver receiver = null;
			try {
				receiver = Receiver.make(item.getKey());
				receiver.open();
				Message[] list = receiver.search(SearchType.UNREAD);
				System.out.println(list[0].getSubject());
			} catch (Exception e) {
				fail(item.getValue());
			} finally {
				receiver.close();
			}
		}
	}
}
