/*
 * Copyright (c) 2012 Lei Hu. All rights reserved.
 * Lei Hu PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.gfg.mail.receiver;

import org.gfg.mail.config.SMTPServerConfig;
import org.gfg.mail.config.ServerConfig;
import org.gfg.mail.config.ServerConfig.ContentType;
import org.gfg.mail.entity.Account;
import org.gfg.mail.sender.Sender;
import org.junit.*;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.*;
import java.util.Map.Entry;

import static org.junit.Assert.fail;

/**
 * @author L <qiyuan4f@gmail.com>
 * @version 1.0
 * @date 2012-10
 */
public class SMTPSenderTest {
	private static Map<Account, String> cases = new HashMap<Account, String>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		Account account = null;
		ServerConfig server = null;

		// 163 SSL
		account = new Account();
		server = new SMTPServerConfig();
		server.setAddress("smtp.ccntgrid.org");
		server.setEncryptionType(ServerConfig.EncryptionType.SSL);
		server.setNeedAuth(true);
		server.setCharset("utf-8");
		server.setContentType(ContentType.HTML);
		
		account.setSenderServer(server);
		account.setMailAddress("L@ccntgrid.org");
		account.setUsername("L");
		account.setPassword("");
		cases.put(account, "不支持SMTP SSL协议");

		// 163
		account = new Account();
		server = new SMTPServerConfig();
		server.setAddress("smtp.ccntgrid.org");
		server.setNeedAuth(true);
		server.setCharset("utf-8");
		server.setContentType(ContentType.HTML);
		
		account.setSenderServer(server);
		account.setMailAddress("L@ccntgrid.org");
		account.setUsername("L");
		account.setPassword("");
		cases.put(account, "不支持SMTP协议");

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
	public void testSend() {
		for (Entry<Account, String> item : cases.entrySet()) {
			Sender sender = null;
			try {
				Account account = item.getKey();
				
				sender = Sender.make(account);
				System.out.println(sender.open());

				Collection<String> attachments = new ArrayList<String>();
				
				// 确保测试文件存在
				attachments.add("D:/ipmsg.exe");

				List<String> recipients = new ArrayList<String>();
				recipients.add("L@ccntgrid.org");

				Message message = getMessage(
						sender.getSession(),
						account.getSenderServer().getContentType(),
						account.getSenderServer().getCharset(),
						"L@ccntgrid.org",
						"sender name",
						recipients,
						"mail subject: how are you?",
						"<html><body><font color='red'>can you see?中国</font></body></html>",
						attachments);

				System.out.println(sender.send(message));
			} catch (Exception e) {
				fail("发送新邮件失败");
			} finally {
				sender.close();
			}
		}
	}

	private Message getMessage(Session session, ContentType contentType,
			String charset, String senderAddress, String senderName,
			List<String> recipients, String sub, String msg, Collection<String> attachments)
			throws SendFailedException {
		MimeMessage message = null;

		try {
			message = new MimeMessage(session);

			if (contentType == ContentType.HTML) {
				message.addHeader("Content-type", "text/html");
			} else {
				message.addHeader("Content-type", "text/plain");
			}

			message.setSubject(sub, charset);
			message.setFrom(new InternetAddress(senderAddress, senderName));

			for (Iterator<String> it = recipients.iterator(); it.hasNext();) {
				String email = (String) it.next();
				message.addRecipients(Message.RecipientType.TO, email);
			}

			Multipart mp = new MimeMultipart();

			// content
			MimeBodyPart contentPart = new MimeBodyPart();

			if (contentType == ContentType.HTML) {
				contentPart.setContent(
						"<meta http-equiv=Content-Type content=text/html; charset="
								+ charset + ">" + msg, "text/html;charset="
								+ charset);
			} else {
				contentPart.setText(msg, charset);
			}

			mp.addBodyPart(contentPart);

			// attachment
			if (attachments != null) {
				MimeBodyPart attachPart;
				for (Iterator<String> it = attachments.iterator(); it.hasNext();) {
					attachPart = new MimeBodyPart();
					FileDataSource fds = new FileDataSource(it.next()
							.toString().trim());
					attachPart.setDataHandler(new DataHandler(fds));
					if (fds.getName().indexOf("$") != -1) {
						attachPart.setFileName(fds.getName().substring(
								fds.getName().indexOf("$") + 1,
								fds.getName().length()));
					} else {
						attachPart.setFileName(fds.getName());
					}
					mp.addBodyPart(attachPart);
				}

			}

			message.setContent(mp);

			message.setSentDate(new Date());
		} catch (Exception e) {
			throw new SendFailedException(e.toString());
		}
		return message;
	}
}
