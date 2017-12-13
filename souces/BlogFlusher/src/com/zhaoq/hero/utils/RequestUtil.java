package com.zhaoq.hero.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.zhaoq.hero.Constants;
import com.zhaoq.hero.domain.IPAgentEntity;
import com.zhaoq.hero.intfs.RequestCallBack;

/**
 * 请求  数据
 * author:zhaoq
 * github:https://github.com/zqHero
 * csdn:http://blog.csdn.net/u013233097
 * 2017年12月13日
 */
public class RequestUtil {
	
	private static List<String> murls;
	private static RequestCallBack mcallback;

	static Thread rThread ;
	/**
	 * to  Request Data:
	 * @param urls
	 * @param requestCallBack
	 */
	public static void requestData(List<String> urls, RequestCallBack requestCallBack) {
		// TODO Auto-generated method stub
		murls = urls;
		mcallback = requestCallBack;
		
		rThread = RequesThread.getInstance(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				reques();
			}
		});
		rThread.start();
	}
	//
	//1.想http代理地址api发起请求，获得想要的代理ip地址
	static List<IPAgentEntity> ipList = getIp(Constants.IPAgentUrl);
	
	private static void reques() {
		// TODO Auto-generated method stub
		if (murls == null || murls.size()==0) {
			return;
		}
		for(String url :murls){
			if (url == null || url.equals("")) {
				continue;
			}
			int count = 0;
			//默认 每条  文章地址请求     10000 次：
			for(int i=0; i< 10000; i++){
				IPAgentEntity myIpAgentEntity = ipList.get((int) (Math.random() * ipList.size()));
				
				System.setProperty("http.maxRedirects", "50");
		        System.getProperties().setProperty("proxySet", "true");
		        System.getProperties().setProperty("http.proxyHost", myIpAgentEntity.getAddress());
		        System.getProperties().setProperty("http.proxyPort", myIpAgentEntity.getPort());
	
		        try {
					Document doc = Jsoup.connect(url)
					  				.userAgent("Mozilla")
					  				.cookie("auth", "token")
					  				.timeout(3000)
					  				.get();
					if(doc != null) {
						count++;
						if(mcallback != null)mcallback.requesCallBack(
								url + "--成功刷新次数: " + count);
					}
				} catch (IOException e) {
					if(mcallback != null)mcallback.requesCallBack(
							myIpAgentEntity.getAddress() + ":" + myIpAgentEntity.getPort() + "报错");
				}		
			}
		}
	}


	/**
	 * 获取  ip  代理地址：
	 * @param url
	 * @return
	 */
	public static List<IPAgentEntity> getIp(String url) {
        List<IPAgentEntity> ipList = new ArrayList<IPAgentEntity>();
        try {
            //1.向ip代理地址发起get请求，拿到代理的ip
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(3000)
                    .get();

            //匹配正则表达式：
            Pattern pattern = Pattern.compile("\\d+\\.\\d+\\.\\d+\\.\\d+:(\\d)*");
            Matcher matcher = pattern.matcher(doc.toString());

            ArrayList<String> ips = new ArrayList<String>();
            while (matcher.find()) {
                ips.add(matcher.group());
            }
			for( String ip : ips) {
				IPAgentEntity myIp = new IPAgentEntity();
				String[] temp = ip.split(":");
				myIp.setAddress(temp[0].trim());
				myIp.setPort(temp[1].trim());
				ipList.add(myIp);
			}
        } catch (IOException e) {
        	if(mcallback != null)mcallback.requesCallBack("加载错误:" + e.toString() + "\r\n加载  代理ip地址出错:\r\n"
        			+ "请移步：https://github.com/zqHero/FreeIpAgent/blob/master/Ips.txt  检查是否更改");
        }
        return ipList;
    }


	@SuppressWarnings("deprecation")
	public static void exitCurrentThread() {
		// TODO Auto-generated method stub
		if (rThread!=null) {
			rThread.stop();
		}
	}
	
	//保证   线程   安全：
	static class RequesThread extends Thread{
		
		private RequesThread(Runnable runnable){
			super(runnable);
		}
		
		static RequesThread thread;
		
		@SuppressWarnings("deprecation")
		public synchronized static RequesThread getInstance(Runnable runnable){
			if (thread!=null) {
				thread.stop();
			}
			thread = new RequesThread(runnable);
			return thread;
		}
		
	}

}
