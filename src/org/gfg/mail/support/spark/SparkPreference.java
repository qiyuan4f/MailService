/*
 * Copyright (c) 2012 Lei Hu. All rights reserved.
 * Lei Hu PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.gfg.mail.support.spark;

import org.jivesoftware.spark.preference.Preference;

import javax.swing.*;

/**
 * 实现基于Spark插件的配置面板
 *
 * @author L <qiyuan4f@gmail.com>
 * @version 1.0 <2012-10-24 00:14>
 */
public class SparkPreference implements Preference {
	public static final String TITLE = "New Mail Listener";

	@Override
	public void commit() {
		// TODO Auto-generated method stub

	}

	@Override
	public Object getData() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getErrorMessage() {
		return "Error: " + TITLE;
	}

	@Override
	public JComponent getGUI() {
		JPanel panel = new JPanel();
		panel.add(new JButton("Welcome to my Preferences"));
		return panel;
	}

	@Override
	public Icon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getListName() {
		return "ListName: " + TITLE;
	}

	@Override
	public String getNamespace() {
		return "Namespace: " + TITLE;
	}

	@Override
	public String getTitle() {
		return TITLE;
	}

	@Override
	public String getTooltip() {
		return "Tooltip: " + TITLE;
	}

	@Override
	public boolean isDataValid() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void load() {
		// TODO Auto-generated method stub

	}

	@Override
	public void shutdown() {
		// TODO Auto-generated method stub

	}

}
