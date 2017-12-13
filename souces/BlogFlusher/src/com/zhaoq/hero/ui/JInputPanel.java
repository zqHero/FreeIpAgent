package com.zhaoq.hero.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.zhaoq.hero.Constants;
import com.zhaoq.hero.intfs.InitInterface;
import com.zhaoq.hero.intfs.OnBtnClickListener;

public class JInputPanel extends JPanel implements InitInterface{

	private static final long serialVersionUID = 10L;
	
	JButton btn = null;
	JPanel jPanel1 = null;
	JPanel jPanel2 = null;
	private OnBtnClickListener listener;
	
	JTextField inputText = null;
	
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		setLayout(new FlowLayout(FlowLayout.LEFT));  
		setSize(Constants.PANEL_WIDTH-6,Constants.INPUT_PANEL_HEIGHT);
		setAlignmentY(CENTER_ALIGNMENT);
		
		
		JLabel jaLabel = new JLabel("输入文章地址:");

		inputText = new JTextField();
		inputText.setPreferredSize(
				new Dimension(650, Constants.INPUT_PANEL_HEIGHT));
		
		btn = new JButton("添加");
		
		add(jaLabel);
		add(inputText);
		add(btn);
		
		initListener();
	}

	private void initListener() {
		// TODO Auto-generated method stub
		btn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (listener!=null) {
					listener.onBtnClick(Constants.ADD_BTN,inputText.getText());
				}
			}
		});
	}

	public void setOnBtnClickListener(OnBtnClickListener listener) {
		// TODO Auto-generated method stub
		this.listener = listener;
	}

}
