package com.zhaoq.hero.utils;

import java.util.List;

import com.zhaoq.hero.Constants;
import com.zhaoq.hero.intfs.OnBtnClickListener;
import com.zhaoq.hero.intfs.RequestCallBack;
import com.zhaoq.hero.ui.JInputPanel;
import com.zhaoq.hero.ui.JOutputPanel;

/**
 * author:zhaoq
 * github:https://github.com/zqHero
 * csdn:http://blog.csdn.net/u013233097
 * 2017年12月13日
 */
public class CommUtils {
	
	private static JOutputPanel moutputPanel;

	/**
	 * 添加 点击事件
	 * @param inputPanel
	 * @param outputPanel
	 */
	public static void addListener(JInputPanel inputPanel, JOutputPanel outputPanel) {
		moutputPanel = outputPanel;
		// TODO Auto-generated method stub
		outputPanel.setOnBtnClickListener(listener);
		inputPanel.setOnBtnClickListener(listener);
	}
	
	
	public static OnBtnClickListener listener = new OnBtnClickListener() {
		
		@Override
		public void onBtnClick(String btnName, String content) {
			// TODO Auto-generated method stub  handle  the  btn click:
			switch(btnName){
				case Constants.ACTION_BTN:
					requestArticleUrl();
					break;
				case Constants.ADD_BTN:
					addUrl(content);
					break;
					
				case Constants.EXIT_THREAD_BTN:
					//中止   当前请求   线程：
					RequestUtil.exitCurrentThread();
					moutputPanel.appentOutPrint("中止请求 exit。");
					break;
			}
		}

	};
	
	
	/**
	 * 添加 地址：
	 */
	private static void addUrl(String content) {
		// TODO Auto-generated method stub    check the article url
		if (content.matches("[a-zA-z]+://[^\\s]*")) {
			moutputPanel.appArticleUrlList(content);
		}else{
			//請輸入網址
			moutputPanel.appentOutPrint("請輸入一个文章地址");
		}
	}
	
	/**
	 * 请求   文章地址：
	 */
	private static void requestArticleUrl() {
		// TODO Auto-generated method stub
		List<String> urls = moutputPanel.getArticleUrlList();
		RequestUtil.requestData(urls,new RequestCallBack(){
			@Override
			public void requesCallBack(String info) {
				// TODO Auto-generated method stub
				moutputPanel.appentOutPrint(info);
			}
		});
	}

}
