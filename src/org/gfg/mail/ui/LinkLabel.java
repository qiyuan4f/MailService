/*
 * Copyright (c) 2012 Lei Hu. All rights reserved.
 * Lei Hu PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.gfg.mail.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.Map;

/**
 * 实现基于JLabel的超链接控件
 * 
 * @author L <qiyuan4f@gmail.com>
 * @version 1.0 <2012-10-24 00:14>
 */
public class LinkLabel extends JLabel implements MouseListener {
	private static final long serialVersionUID = 5604032348347771542L;

	private String url;
	private boolean lineWrap;

	public LinkLabel() {
		super();
		init(null);
	}

	public LinkLabel(String text) {
		super(text);
		init(null);
	}

	public LinkLabel(String text, String url) {
		super(text);
		init(url);
	}

	public LinkLabel(Icon icon) {
		super(icon);
		init(null);
	}

	public LinkLabel(Icon icon, int horizontalAlignment) {
		super(icon, horizontalAlignment);
		init(null);
	}

	private void init(String url) {
		this.url = url;
		this.addMouseListener(this);
		this.setForeground(Color.BLUE);
	}

	/**
	 * 打开浏览器，访问地址为<tt>url</tt>
	 */
	private void browse() {
		if (Desktop.isDesktopSupported()) {
			Desktop desktop = Desktop.getDesktop();
			if (desktop.isSupported(Desktop.Action.BROWSE)) {
				try {
					desktop.browse(new URI(this.url));
					return;
				} catch (Exception e) {

				}
			}
		} else {
			String osName = System.getenv("os.name");
			try {
				if (osName.startsWith("Windows")) {
					Runtime.getRuntime().exec(
							"rundll32 url.dll,FileProtocolHandler " + this.url);
				} else if (osName.startsWith("Mac OS")) {
					Class<?> manager = Class
							.forName("com.apple.eio.FileManager");
					Method openURL = manager.getDeclaredMethod("openURL",
							new Class[] { String.class });
					openURL.invoke(null, new Object[] { url });
				} else {
					Map<String, String> env = System.getenv();

					if (env.get("BROWSER") != null) {
						Runtime.getRuntime().exec(
								env.get("BROWSER") + " " + this.url);
						return;
					} else {
						String[] browsers = { "firefox", "chrome", "iceweasel",
								"opera", "konqueror", "epiphany", "mozilla",
								"netscape" };
						String browser = null;
						for (int i = 0; i < browsers.length; ++i) {
							if (Runtime
									.getRuntime()
									.exec(new String[] { "which", browsers[i] })
									.waitFor() == 0) {
								browser = browsers[i];
								break;
							}
						}

						if (browser == null) {
							throw new RuntimeException(
									"no browser to open uri.");
						} else {
							Runtime.getRuntime().exec(
									new String[] { browser, this.url });
						}
					}
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "no browser to open uri.");
			}
		}
	}

	/**
	 * 是否支持自动换行
	 * 
	 * @return <tt>true</tt>，表示支持；<tt>false</tt>，表示不支持
	 */
	public boolean isLineWrap() {
		return lineWrap;
	}

	/**
	 * 设置是否支持自动换行
	 * 
	 * @param lineWrap
	 *            <tt>true</tt>，表示支持；<tt>false</tt>，表示不支持
	 */
	public void setLineWrap(boolean lineWrap) {
		this.lineWrap = lineWrap;
	}

	@Override
	public String getText() {
		return this.lineWrap ? "<html><p>" + super.getText() + "</p></html?"
				: super.getText();
	}

	/**
	 * 获取控件被点击时跳转的链接地址
	 * 
	 * @return 链接地址
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * 设置控件被点击时跳转的链接地址
	 * 
	 * @param url
	 *            链接地址
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (this.url != null && !this.url.isEmpty()) {
			this.browse();
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (this.url != null && !this.url.isEmpty()) {
			this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
	}
}
