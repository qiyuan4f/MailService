/*
 * Copyright (c) 2012 Lei Hu. All rights reserved.
 * Lei Hu PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.gfg.mail.ui;

import javax.swing.*;
import java.awt.*;

/**
 * 实现可定制内边距的JPanel
 *
 * @author L <qiyuan4f@gmail.com>
 * @version 1.0 <2012-10-24 00:14>
 */
public class AdvancePanel extends JPanel {
	private static final long serialVersionUID = 4598305088199410239L;
	
	private Insets insets;
	
	public AdvancePanel() {
		
	}

	@Override
	public Insets getInsets() {
		return this.insets;
	}
	
	public void setInsets(Insets insets) {
		if (insets != null && insets != this.insets) {
			this.insets = insets;
			this.repaint();
		}
	}
}
