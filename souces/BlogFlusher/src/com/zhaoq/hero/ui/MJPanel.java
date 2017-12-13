package com.zhaoq.hero.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;

import com.zhaoq.hero.Constants;
import com.zhaoq.hero.utils.CommUtils;

/**
 * author:zhaoq
 * github:https://github.com/zqHero
 * csdn:http://blog.csdn.net/u013233097
 * 2017年12月4日
 */
public class MJPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;

	public MJPanel(){
		//init  view
		setLayout(new BorderLayout());
		initContent();
	}
	
	JOutputPanel outputPanel = null;
	JInputPanel inputPanel = null;

	private void initContent() {
		// TODO Auto-generated method stub
		inputPanel = new JInputPanel();
	    // 设置面板的边框
		inputPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		
		outputPanel = new JOutputPanel();
		outputPanel.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
		
		outputPanel.setPreferredSize(new Dimension(getWidth(), Constants.OUT_PUT_HEIGHT));
		
		add(inputPanel,BorderLayout.NORTH);
		add(outputPanel,BorderLayout.SOUTH);
		
		inputPanel.initView();
		outputPanel.initView();
	}
	
	public void appentOutPrint(String string){
		outputPanel.appentOutPrint(string);
	}

	public void addListener() {
		// TODO Auto-generated method stub
		CommUtils.addListener(inputPanel,outputPanel);
	}
}
