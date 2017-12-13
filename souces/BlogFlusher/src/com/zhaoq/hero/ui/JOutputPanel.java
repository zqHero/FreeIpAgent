package com.zhaoq.hero.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.DefaultCaret;

import com.zhaoq.hero.Constants;
import com.zhaoq.hero.intfs.InitInterface;
import com.zhaoq.hero.intfs.OnBtnClickListener;

/**
 * 处理 输出 事件：
 * author:zhaoq
 * github:https://github.com/zqHero
 * csdn:http://blog.csdn.net/u013233097
 * 2017年12月13日
 */
public class JOutputPanel extends JPanel  implements InitInterface{
	 
	private static final long serialVersionUID = 1L;
	
	JPanel panel1 = null;
	JPanel panel2 = null;
	 
	JButton action = null, clear = null,clearOut = null,exitThread=null;
	 
	JTextArea field = null;
	JTextArea textAout = null;
	 
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		setLayout(new GridLayout(1,2));
		setSize(Constants.PANEL_WIDTH,Constants.OUT_PUT_HEIGHT);
		
		panel1 = new JPanel();
	    panel2 = new JPanel();
	    
	    panel1.setBackground(Color.LIGHT_GRAY);
	    panel2.setBackground(Color.white);
	    
	    add(panel1,BorderLayout.WEST);
	    add(panel2,BorderLayout.EAST);
	    
	    initPanel1();
	    initPanel2();
	    
	    initListener();
	}

	private void initPanel2() {
		// TODO Auto-generated method stub
		JLabel label = new JLabel("system print:");
		label.setPreferredSize(new Dimension(getWidth(), 80));
		
		panel2.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel2.setAlignmentY(CENTER_ALIGNMENT);
		
		textAout = new JTextArea();
		textAout.setLineWrap(true);
		textAout.setOpaque(false);
		textAout.setWrapStyleWord(true);
		textAout.setPreferredSize(new Dimension(400,350));
		textAout.setEditable(false);
		
		DefaultCaret caret = (DefaultCaret)textAout.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		clearOut = new JButton("清空");
		clearOut.setPreferredSize(new Dimension(200,40));
		
		exitThread = new JButton("中止");
		exitThread.setPreferredSize(new Dimension(200,40));
		
		panel2.add(label);		
		panel2.add(new JScrollPane(textAout));	
		panel2.add(clearOut);	
		panel2.add(exitThread);	
		
	}
	
	//
	private void initPanel1() {  
		JLabel label = new JLabel("文章地址列表:");
		label.setPreferredSize(new Dimension(getWidth(), 80));
		
		panel1.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel1.setAlignmentY(CENTER_ALIGNMENT);
		
		field = new JTextArea();
		field.setLineWrap(true);
		field.setOpaque(false);
		field.setWrapStyleWord(true);
		field.setPreferredSize(new Dimension(400,350));
		field.setText(Constants.AUTHOR_INTO);
		field.setEditable(false);
		
		JPanel panel = new JPanel();
		
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		action = new JButton("访问");
		action.setPreferredSize(new Dimension(190,40));
		
		clear = new JButton("清空");
		clear.setPreferredSize(new Dimension(190,40));
		
		panel.setOpaque(false);
		panel.add(action);
		panel.add(clear);
		
		panel1.add(label);		
		panel1.add(field);		
		panel1.add(panel);	
	}
	
	public OnBtnClickListener clickListener;

	private void initListener() {
		// TODO Auto-generated method stub
		 action.addActionListener(new ActionListener(){
	            @Override
	            public void actionPerformed(ActionEvent arg0) {
	               if (clickListener!= null) {
            		   clickListener.onBtnClick(Constants.ACTION_BTN, field.getText());
            	   }
	            }           
	        });
		 
		 clear.addActionListener(new ActionListener(){
	            @Override
	            public void actionPerformed(ActionEvent arg0) {
	            	field.setText(Constants.AUTHOR_INTO);
	            	field.setEnabled(false);
	            }           
	        });
		 
		 clearOut.addActionListener(new ActionListener(){
	            @Override
	            public void actionPerformed(ActionEvent arg0) {
	            	textAout.setText("");
	            	textAout.setEnabled(false);
	            }           
	        });
		 exitThread.addActionListener(new ActionListener(){
	            @Override
	            public void actionPerformed(ActionEvent arg0) {
	            	if (clickListener!= null) {
	            		   clickListener.onBtnClick(Constants.EXIT_THREAD_BTN, field.getText());
	            	   }
	            }           
	        });
	}

	public void appentOutPrint(String appStr){
		textAout.append( "syso:"+ appStr + "\r\n");
	}
	
	public void setOnBtnClickListener(OnBtnClickListener listener) {
		// TODO Auto-generated method stub
		this.clickListener = listener;
	}

	public void appArticleUrlList(String url) {
		// TODO Auto-generated method stub
		if (field.getText().equals(Constants.AUTHOR_INTO)) {
			field.setText("");
		}
		field.append(url + ",\r\n");
	}

	/**
	 * 获取  文章地址列表：
	 */
	public ArrayList<String> getArticleUrlList() {
		// TODO Auto-generated method stub
		String articleStr = field.getText();
		String[] articles = articleStr.split(",\r\n");  //分隔符
		
		ArrayList<String>  strings = new ArrayList<>();
		
		for(int i=0;i<articles.length;i++){
			if (articles[i].equals(Constants.AUTHOR_INTO) || !articles[i].matches("[a-zA-z]+://[^\\s]*")) {
				System.out.println(articles[i]+"------");
				continue;
			}
			strings.add(articles[i]);
		}
		return strings.size() ==0 ? null: strings;
	};
}
