package com.zhaoq.hero.ui;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

import com.zhaoq.hero.Constants;

/**
 * author:zhaoq
 * github:https://github.com/zqHero
 * csdn:http://blog.csdn.net/u013233097
 * 2017年12月2日
 */
public class MainUi {
	
	JFrame mainFrame = null;
	
	MJPanel panel = null;
	
	//constructor
	public MainUi() {
		// you can du somthing     init:
		initFrame();
	}

	//init main frame:
	private void initFrame() {
		// Auto-generated method stub
		mainFrame = new JFrame("刷量小程序");
		
		//creat menubar
		JMenuBar menuBar = new JMenuBar();
		
		//add menu
		menuBar.add(addExitMenu());
		
		//add menuBar
		mainFrame.setJMenuBar(menuBar);
		 
		panel = new MJPanel(); 
		mainFrame.add(panel);
	}
	
	private JMenu addExitMenu() {
		final JMenu exitMenu = new JMenu("退出(E)");
		exitMenu.setMnemonic('E');
		
		exitMenu.addMenuListener(new MenuListener() {
			
			@Override
			public void menuSelected(MenuEvent e) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
			@Override
			public void menuDeselected(MenuEvent e) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void menuCanceled(MenuEvent e) {
				// TODO Auto-generated method stub
			}
		});
		return exitMenu;
	}
	
	public void appentOutPrint(String string) {
		// TODO Auto-generated method stub
		panel.appentOutPrint(string);
	}

	public void show(){
		if(mainFrame!=null){
			mainFrame.setSize(Constants.PANEL_WIDTH, Constants.PANEL_HEIGHT);
			mainFrame.setResizable(false);
			// 设置窗体位置在屏幕中央
			mainFrame.setLocationRelativeTo(null);
			 // 关闭窗体后退出程序
			mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

			ImageIcon icon= new ImageIcon("./imgs/1.jpg");
			
	        mainFrame.setIconImage(icon.getImage());
	        // 显示窗体
	        mainFrame.setVisible(true);
		}
		initListener();
	}

	/**
	 * 設置 事件  監聽：
	 */
	private void initListener() {
		// TODO Auto-generated method stub
		panel.addListener();
	}
}
