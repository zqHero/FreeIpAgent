# FreeIpAgent
免费ip代理

<font color="#f00">
  本库保存一些ip代理服务器。
    <br/>
    用于csdn刷量，如果代理ip服务器地址过期请自行修改  ips.txt 文件：
      <br/>
      1，首先fork该库到你的github。
      <br/>
      2，按照格式，添加代理服务器到文件ips.txt中。
      <br/>
      3，请求合并，博主会尽快处理。
</font>





####<font color="#f00"> 前言:</font>

相信很多写博客的朋友，会苦恼于博客访问量上不去的问题。博主最近工作比较新手动谢了个小程序，一个可以刷访问量的程序。当然主要是针对csdn博客。有了他 就再也不必担心博客访问量上不去的问题了。

#### <font color="#0e9c94">注：博客最主要还是要自己做好seo优化，以及提高博客内容质量。本篇文章，仅供学习交流。大家如果有问题，可以留言。</font>

<br/>
<br/>
#### <font color="#f00">一，废话少说，看图：</font>

软件 点击jar 包直接运行：如下：

![这里写图片描述](http://img.blog.csdn.net/20171213181321393?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMzIzMzA5Nw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


稍后查看博客访问量会有变化哟，博主只是进行可简单测试，如果你一直刷，那么你的博客访问量就会蹭蹭的上涨哟：

![这里写图片描述](http://img.blog.csdn.net/20171213181445868?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMzIzMzA5Nw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


<br/>
<br/>
#### <font color="#f00">二，代码介绍：</font>

&nbsp;&nbsp;&nbsp;&nbsp;代码其实没什么东西，很好理解。csdn默认使用一台电脑刷新文章，访问量默认是不会发生变化的。于是我们只能用代理ip 来访问博客了。博主在网上找了一些代理ip。当然如果失效。你可以更新ip地址。

献上地址：GitHub：https://github.com/zqHero/FreeIpAgent/blob/master/Ips.txt

下面主要是博主在网上收集到的ip代理：

![这里写图片描述](http://img.blog.csdn.net/20171213182452545?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvdTAxMzIzMzA5Nw==/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)



#### <font color="#0e9c94">主要实现步骤：</font>
 <font color="#9e6c64">1，首先我们应该获取到我们的IP代理地址：</font>
```
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
```

<font color="#9e6c64">2，其次使用我们的ip设置 我们请求属性，访问博客：</font>

```
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
```


这样我们就完成了CSDN博客的流量的刷新工作。简书的博主试过，貌似没用，不过简书的默认刷新浏览器就可以增加访问量了。

献上源码地址：

https://github.com/zqHero/FreeIpAgent



#### <font color="#0e9c94">注：博客最主要还是要自己做好seo优化，以及提高博客内容质量。本篇文章，仅供学习交流。大家如果有问题，可以留言。如果对你有用欢迎fork 和star。 请尊重原创。</font>


