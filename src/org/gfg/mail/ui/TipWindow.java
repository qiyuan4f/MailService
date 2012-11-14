/*
 * Copyright (c) 2012 Lei Hu. All rights reserved.
 * Lei Hu PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.gfg.mail.ui;

import org.gfg.mail.entity.TipMessage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

/**
 * 实现屏幕右下角弹出消息提醒窗口，类似QQ的消息提醒
 * 
 * @author L <qiyuan4f@gmail.com>
 * @version 1.0 <2012-10-24 00:14>
 */
public class TipWindow extends JDialog implements WindowListener, Runnable, ComponentListener {
	private static final long serialVersionUID = 5453483098394243568L;
	public static final int DEFAULT_WIDTH = 350;
	public static final int DEFAULT_HEIGHT = 200;

	private LinkLabel title;
	private LinkLabel smallTitle;
	private LinkLabel content;
	private JLabel labelCount;

	private Dimension dimension;
	private int x, y;
	private int width, height;
	private boolean isActive = false;

	private java.util.List<TipMessage> data;
	private int index;

	public TipWindow() {
		this.index = -1;
		this.data = new ArrayList<TipMessage>();

		this.initComponents();
	}

	/**
	 * 以动画的方式弹出消息对话框
	 */
	public void startPop() {
		if (!this.isActive) {
			this.setVisible(true);
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i <= height; i += 10) {
						try {
							setLocation(x, y - i);
							Thread.sleep(20);
						} catch (InterruptedException ignored) {

						}
					}
				}
			});
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {

			}

			this.isActive = true;
		}
	}

	/**
	 * 以动画的方式关闭对话框
	 */
	public void stopPop() {
		if (this.isActive) {
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					for (int i = height; i >= 0; i -= 10) {
						try {
							setLocation(x, y - i);
							Thread.sleep(20);
						} catch (InterruptedException ignored) {

						}
					}
				}
			});
			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {

			}
			this.setVisible(false);
			this.isActive = false;
		}
	}

	@Override
	public void run() {
		for (int i = 0; i <= height; i += 10) {
			try {
				this.setLocation(x, y - i);
				Thread.sleep(20);
			} catch (InterruptedException ignored) {

			}
		}
	}

	private void initComponents() {
		// 初始化基本数据
		this.dimension = Toolkit.getDefaultToolkit().getScreenSize();
		this.width = TipWindow.DEFAULT_WIDTH;
		this.height = TipWindow.DEFAULT_HEIGHT;

		this.x = (int) (this.dimension.getWidth() - this.width - 10);
		this.y = (int) (this.dimension.getHeight());
		
		// 添加标题部分
		JPanel titlePanel = new JPanel();
		FlowLayout titleLayout = new FlowLayout(FlowLayout.LEFT);
		titlePanel.setLayout(titleLayout);

		title = new LinkLabel();
		title.setFont(new Font(null, Font.BOLD, 16));
		title.setForeground(Color.BLACK);
		title.setPreferredSize(new Dimension(this.width - 50, 18));
		title.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				String url = title.getUrl();
				if (url != null && !url.isEmpty()) {
					removeMessage(index);
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
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});

		titlePanel.add(title, FlowLayout.LEFT);

		// 添加内容摘要
		JPanel contentPanel = new JPanel();
		FlowLayout contentLayout = new FlowLayout(FlowLayout.LEFT);
		contentPanel.setLayout(contentLayout);
		content = new LinkLabel();
		content.setLineWrap(true);
		content.setFont(new Font(null, Font.PLAIN, 12));
		content.setVerticalAlignment(SwingConstants.TOP);
		content.setPreferredSize(new Dimension(this.width, 85));
		contentPanel.add(content, FlowLayout.LEFT);

		// 添加操作区
		JPanel operationPanel = new JPanel();
		FlowLayout operationLayout = new FlowLayout(FlowLayout.RIGHT);
		operationPanel.setLayout(operationLayout);

		smallTitle = new LinkLabel();
		smallTitle.setFont(new Font(null, Font.PLAIN, 12));
		smallTitle.setForeground(Color.GRAY);
		labelCount = new JLabel("0/0");

		operationPanel.add(smallTitle);
		operationPanel.add(labelCount);

		// 上一条记录
		JButton btnPrevious = new JButton("<");
		btnPrevious.setPreferredSize(new Dimension(18, 18));
		btnPrevious.setMargin(new Insets(0, 0, 0, 0));
		btnPrevious.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (index > 0) {
					turn(index - 1);
				}
			}
		});

		JButton btnNext = new JButton(">");
		btnNext.setPreferredSize(new Dimension(18, 18));
		btnNext.setMargin(new Insets(0, 0, 0, 0));
		btnNext.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (index < data.size() - 1) {
					turn(index + 1);
				}
			}
		});

		operationPanel.add(btnPrevious);
		operationPanel.add(btnNext);

		BorderLayout layout = new BorderLayout();
		layout.setHgap(5);
		layout.setVgap(5);

		AdvancePanel container = new AdvancePanel();
		container.setInsets(new Insets(0, 5, 0, 5));
		container.setLayout(layout);

		container.add(titlePanel, BorderLayout.NORTH);
		container.add(contentPanel, BorderLayout.CENTER);
		container.add(operationPanel, BorderLayout.SOUTH);

		// 初始化主体部分
		this.add(container);
		this.setPreferredSize(new Dimension(this.width, this.height));
		this.setLocation(this.x, this.y);
		this.setResizable(false);
		this.setUndecorated(true);
		this.getRootPane().setWindowDecorationStyle(
				JRootPane.INFORMATION_DIALOG);
		this.setAlwaysOnTop(true);
		JFrame.setDefaultLookAndFeelDecorated(true);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.addWindowListener(this);
		this.addComponentListener(this);
		this.pack();
	}

	/**
	 * 切换消息
	 * 
	 * @param index
	 *            消息序号，0为第一条消息
	 */
	public void turn(int index) {
		if (index < data.size() && index >=0 ) {
			this.index = index;
			this.showMessage();
			this.showCount();
		}
	}

	/**
	 * 获取当前消息序号
	 * 
	 * @return 消息序号，0表示第一条记录，-1表示当前没有选中任何记录
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * 将指定消息显示在当前面板中
	 */
	private void showMessage() {
		TipMessage message = data.get(this.index);
		
		if (message == null) {
			showClean();
		} else {
			this.setTitle(message.getTitle());
			this.title.setText(message.getTitle());
			this.title.setUrl(message.getTitleURL());
			this.smallTitle.setText(message.getSmallTitle());
			this.smallTitle.setUrl(message.getSmallTitleURL());
			this.content.setText(message.getContent());
			this.content.setUrl(message.getContentURL());
		}
	}

	/**
	 * 清除显示面板
	 */
	private void showClean() {
		this.setTitle("");
		this.title.setText("");
		this.title.setUrl("");
		this.content.setText("");
		this.content.setUrl("");
		this.smallTitle.setText("");
		this.smallTitle.setUrl("");
		this.labelCount.setText("0/0");
		this.setVisible(false);
	}

	/**
	 * 刷新计数Label的界面
	 * 
	 */
	private void showCount() {
		int count = this.data.size();
		int index = (count == 0) ? 0 : (this.index + 1);
		this.labelCount.setText(index + "/" + count);
	}

	/**
	 * 获取消息数量
	 * 
	 * @return 消息数
	 */
	public int getMessageCount() {
		return this.data.size();
	}

	/**
	 * 添加指定消息
	 * 
	 * @param message 待添加的消息 
	 */
	public void addMessage(TipMessage message) {
		if (message != null) {
			this.data.add(message);
			
		}
	}

	/**
	 * 移除指定序号的消息，下标以0起始，即0表示第一条消息
	 * 
	 * @param index 指定消息序号，必须大于等于0
	 */
	public void removeMessage(int index) {
		if (index >= 0 && index < this.data.size()) {
			this.data.remove(index);
			
			if (this.data.size() == 0) {
				this.showClean();
			} else {
				if (index == this.index) {
					this.index %= this.data.size();
					this.turn(this.index % this.data.size());
				} else {
					this.showCount();
				}
			}
		}
	}

	/**
	 * 移除所有消息
	 */
	public void removeAllMessage() {
		this.data.clear();
		this.showClean();
	}

	@Override
	public void windowOpened(WindowEvent e) {
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		this.stopPop();
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void componentMoved(ComponentEvent e) {
		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		
	}

	@Override
	public void componentHidden(ComponentEvent e) {
		
	}
}
