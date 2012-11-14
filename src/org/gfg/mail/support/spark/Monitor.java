/*
 * Copyright (c) 2012 Lei Hu. All rights reserved.
 * Lei Hu PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.gfg.mail.support.spark;

import org.gfg.mail.entity.Account;
import org.gfg.mail.exception.NotSupportedException;
import org.gfg.mail.receiver.Receiver;
import org.gfg.mail.receiver.Receiver.SearchType;
import org.gfg.mail.ui.TipWindow;
import org.gfg.mail.util.MessageUtil;

import javax.mail.Message;
import javax.mail.event.MessageCountEvent;
import javax.mail.event.MessageCountListener;

/**
 * 实现新邮件提醒的核心类
 * 
 * @author L <qiyuan4f@gmail.com>
 * @version 1.0 <2012-10-24 00:14>
 */
public class Monitor implements Runnable, MessageCountListener {
	public static final int DEFAUL_INTERVAL = 3000;
	private TipWindow tipWindow = new TipWindow();

	private boolean enabled;

	private Receiver receiver;
	private Account account;

	private Thread thread;

	public Monitor(Account account) throws NotSupportedException {
		this.account = account;
	}

	private void init() throws NotSupportedException {
		this.receiver = Receiver.make(this.account);
		this.receiver.open();
		this.receiver.getFolder().addMessageCountListener(this);
		thread = new Thread(this);
	}

	/**
	 * 重新从服务器上获取所有未读的邮件，并使用提示窗口提示用户
	 */
	private void reNewMessageTip() {
		Message[] messages = receiver.search(SearchType.UNREAD);

		if (messages != null && messages.length > 0) {
			for (Message item : messages) {
				this.tipWindow.addMessage(MessageUtil.makeTipMessage(
						this.account, item));
			}
			this.tipWindow.turn(0);
			this.tipWindow.startPop();
		}
	}

	/**
	 * 将给定的消息以弹出窗口方式提示用户
	 * 
	 * @param messages
	 *            提示消息
	 */
	private void newMessageTip(Message[] messages) {
		if (tipWindow == null) {
			tipWindow = new TipWindow();
		}

		int index = tipWindow.getMessageCount();

		for (Message item : messages) {
			tipWindow.addMessage(MessageUtil.makeTipMessage(account, item));
		}
		tipWindow.turn(index);
		tipWindow.startPop();
	}

	/**
	 * 当前监视器是否启用
	 * 
	 * @return <tt>true</tt>启用状态；<tt>false</tt>禁用状态
	 */
	public boolean isEnabled() {
		return this.enabled;
	}

	/**
	 * 开启或关闭监视器
	 * 
	 * @param enabled
	 *            <tt>true</tt>表示开启；<tt>false</tt>表示关闭
	 * 
	 * @throws NotSupportedException
	 *             启动监视器失败
	 * @throws InterruptedException
	 *             关闭监视器失败
	 */
	public void enable(boolean enabled) throws NotSupportedException,
			InterruptedException {
		if (this.enabled != enabled) {
			this.enabled = enabled;

			if (enabled) {
				// 第一次开启服务时，初始化所有服务
				if (thread == null) {
					init();
				}
				reNewMessageTip();
				thread.start();
			} else {
				if (thread != null && thread.isAlive()) { // 等待监视进程自然结束
					thread.join();
				}
			}
		}
	}

	/**
	 * 销毁该监视器对象，调用后该监视器将不可用
	 */
	public void dispose() {
		// 关闭监视器线程
		if (this.isEnabled()) {
			try {
				this.enable(false);
			} catch (NotSupportedException ignored) {

			} catch (InterruptedException ignored) {

			}
		}

		// 关闭接受服务器对象
		if (this.receiver != null) {
			this.receiver.close();
		}
	}
	
	public void join() throws InterruptedException {
		this.thread.join();
	}

	@Override
	public void run() {
		while (this.enabled) {
			try {
				Thread.sleep(Monitor.DEFAUL_INTERVAL);
				this.receiver.getFolder().getMessageCount();
			} catch (Exception e) {

			}
		}
	}

	@Override
	public void messagesAdded(MessageCountEvent event) {
		Message[] messages = event.getMessages();
		this.newMessageTip(messages);
	}

	@Override
	public void messagesRemoved(MessageCountEvent event) {

	}
}
