<%@ page import="java.awt.*,java.awt.geom.*,java.awt.image.BufferedImage,java.io.IOException,java.util.Random,javax.imageio.ImageIO,javax.servlet.ServletException,javax.servlet.http.HttpServlet,javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse,javax.servlet.http.HttpSession" %>


<%!
	// 获取随机颜色
	public Color getRandColor(int s,int e){
		Random random = new Random();
		if (s > 255) s = 255;
		if (e > 255) e = 255;
		int r = s + random.nextInt(e - s); // 随机生成RGB颜色中的r值
		int g = s + random.nextInt(e - s); // 随机生成RGB颜色中的g值
		int b = s + random.nextInt(e - s); // 随机生成RGB颜色中的b值
		return new Color(r, g, b);
	}

	// 添加噪点的方法
	private int getRandomIntColor() {
		Random random = new Random();
		int[] rgb = new int[3];
		for (int i = 0; i < 3; i++)
			rgb[i] = random.nextInt(255);

		int color = 0;
		for (int c : rgb) {
			color = color << 8;
			color = color | c;
		}
		return color;
	}
%>

<%
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "No-cache");
	response.setDateHeader("Expires", 0);
	response.setContentType("image/jpeg"); // 指定生成的响应是图片

	/****** 设置参数 ******/
	// 验证码类型：ch-字母 zh-汉字 nu-数字 (不设置默认为数字加字母)
	String type = ""; // 不从request中取type防止识别出简易类型验证码（如登录页面设定使用汉字验证码，但数字验证码被OCR后客户手工指定type使用数字验证码）
	int num = 4; //验证码个数（汉字个数减半）
	int width = 60;
	int height = 20;
	int bline = 0; //背景干扰线条数
	int fline = 5; //前景干扰线条数
	boolean bgflag = true; //背景是否为白色，否的话背景取随机色
	/****** 设置参数 ******/

	// 0，1，2易和字母的o，l，z混淆，不生成
	char[] captchars = new char[] { '2', '3', '4', '5', '6', '7', '8', '2', '3', '4', '5', '6', '7', '8', //重复数字来加大数字出现概率
		//'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n', 'p', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 
		'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'K', 'L', 'M', 'N', 'P', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' }; //去掉J Q 显示不全或有前景干扰线时看不清
	
	// 设置备选汉字，剔除一些不雅的汉字
	String base = "\u7684\u4e00\u4e86\u662f\u6211\u4e0d\u5728\u4eba\u4eec\u6709\u6765\u4ed6\u8fd9\u4e0a\u7740\u4e2a\u5730\u5230\u5927\u91cc\u8bf4\u5c31\u53bb\u5b50\u5f97\u4e5f\u548c\u90a3\u8981\u4e0b\u770b\u5929\u65f6\u8fc7\u51fa\u5c0f\u4e48\u8d77\u4f60\u90fd\u628a\u597d\u8fd8\u591a\u6ca1\u4e3a\u53c8\u53ef\u5bb6\u5b66\u53ea\u4ee5\u4e3b\u4f1a\u6837\u5e74\u60f3\u751f\u540c\u8001\u4e2d\u5341\u4ece\u81ea\u9762\u524d\u5934\u9053\u5b83\u540e\u7136\u8d70\u5f88\u50cf\u89c1\u4e24\u7528\u5979\u56fd\u52a8\u8fdb\u6210\u56de\u4ec0\u8fb9\u4f5c\u5bf9\u5f00\u800c\u5df1\u4e9b\u73b0\u5c71\u6c11\u5019\u7ecf\u53d1\u5de5\u5411\u4e8b\u547d\u7ed9\u957f\u6c34\u51e0\u4e49\u4e09\u58f0\u4e8e\u9ad8\u624b\u77e5\u7406\u773c\u5fd7\u70b9\u5fc3\u6218\u4e8c\u95ee\u4f46\u8eab\u65b9\u5b9e\u5403\u505a\u53eb\u5f53\u4f4f\u542c\u9769\u6253\u5462\u771f\u5168\u624d\u56db\u5df2\u6240\u654c\u4e4b\u6700\u5149\u4ea7\u60c5\u8def\u5206\u603b\u6761\u767d\u8bdd\u4e1c\u5e2d\u6b21\u4eb2\u5982\u88ab\u82b1\u53e3\u653e\u513f\u5e38\u6c14\u4e94\u7b2c\u4f7f\u5199\u519b\u5427\u6587\u8fd0\u518d\u679c\u600e\u5b9a\u8bb8\u5feb\u660e\u884c\u56e0\u522b\u98de\u5916\u6811\u7269\u6d3b\u90e8\u95e8\u65e0\u5f80\u8239\u671b\u65b0\u5e26\u961f\u5148\u529b\u5b8c\u5374\u7ad9\u4ee3\u5458\u673a\u66f4\u4e5d\u60a8\u6bcf\u98ce\u7ea7\u8ddf\u7b11\u554a\u5b69\u4e07\u5c11\u76f4\u610f\u591c\u6bd4\u9636\u8fde\u8f66\u91cd\u4fbf\u6597\u9a6c\u54ea\u5316\u592a\u6307\u53d8\u793e\u4f3c\u58eb\u8005\u5e72\u77f3\u6ee1\u65e5\u51b3\u767e\u539f\u62ff\u7fa4\u7a76\u5404\u516d\u672c\u601d\u89e3\u7acb\u6cb3\u6751\u516b\u96be\u65e9\u8bba\u5417\u6839\u5171\u8ba9\u76f8\u7814\u4eca\u5176\u4e66\u5750\u63a5\u5e94\u5173\u4fe1\u89c9\u6b65\u53cd\u5904\u8bb0\u5c06\u5343\u627e\u4e89\u9886\u6216\u5e08\u7ed3\u5757\u8dd1\u8c01\u8349\u8d8a\u5b57\u52a0\u811a\u7d27\u7231\u7b49\u4e60\u9635\u6015\u6708\u9752\u534a\u706b\u6cd5\u9898\u5efa\u8d76\u4f4d\u5531\u6d77\u4e03\u5973\u4efb\u4ef6\u611f\u51c6\u5f20\u56e2\u5c4b\u79bb\u8272\u8138\u7247\u79d1\u5012\u775b\u5229\u4e16\u521a\u4e14\u7531\u9001\u5207\u661f\u5bfc\u665a\u8868\u591f\u6574\u8ba4\u54cd\u96ea\u6d41\u672a\u573a\u8be5\u5e76\u5e95\u6df1\u523b\u5e73\u4f1f\u5fd9\u63d0\u786e\u8fd1\u4eae\u8f7b\u8bb2\u519c\u53e4\u9ed1\u544a\u754c\u62c9\u540d\u5440\u571f\u6e05\u9633\u7167\u529e\u53f2\u6539\u5386\u8f6c\u753b\u9020\u5634\u6b64\u6cbb\u5317\u5fc5\u670d\u96e8\u7a7f\u5185\u8bc6\u9a8c\u4f20\u4e1a\u83dc\u722c\u7761\u5174\u5f62\u91cf\u54b1\u89c2\u82e6\u4f53\u4f17\u901a\u51b2\u5408\u7834\u53cb\u5ea6\u672f\u996d\u516c\u65c1\u623f\u6781\u5357\u67aa\u8bfb\u6c99\u5c81\u7ebf\u91ce\u575a\u7a7a\u6536\u7b97\u81f3\u653f\u57ce\u52b3\u843d\u94b1\u7279\u56f4\u5f1f\u80dc\u6559\u70ed\u5c55\u5305\u6b4c\u7c7b\u6e10\u5f3a\u6570\u4e61\u547c\u6027\u97f3\u7b54\u54e5\u9645\u65e7\u795e\u5ea7\u7ae0\u5e2e\u5566\u53d7\u7cfb\u4ee4\u8df3\u975e\u4f55\u725b\u53d6\u5165\u5cb8\u6562\u6389\u5ffd\u79cd\u88c5\u9876\u6025\u6797\u505c\u606f\u53e5\u533a\u8863\u822c\u62a5\u53f6\u538b\u6162\u53d4\u80cc\u7ec6";

	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	Graphics2D g = (Graphics2D) image.getGraphics();
	Random random = new Random();
	// 设置背景色并填充
	if(bgflag)
		g.setColor(new Color(255,255,255)); //白色
	else
		g.setColor(getRandColor(200, 250)); //随机色
	g.fillRect(0, 0, width, height);
	g.setFont(new Font("黑体", Font.BOLD, 17));
	// 随机产生bline条背景干扰线
	g.setColor(getRandColor(180, 200));
	for (int i=0;i<bline;i++)
	{
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int xl = random.nextInt(12);
		int yl = random.nextInt(12);
		g.drawLine(x,y,x+xl,y+yl);
	}
	String sRand = "";
	// 输出随机的验证文字
	String ctmp = "";
	int itmp = 0;
	for (int i = 0; i < num; i++) {
		if("ch".equals(type)) {
			itmp = random.nextInt(26) + 65; // 生成A~Z的字母
			ctmp = String.valueOf((char) itmp);
		}
		else if("zh".equals(type)) { // 减半生成汉字
			if(i%2==0) {
				int start = random.nextInt(base.length()-1);
				ctmp = base.substring(start, start + 1);
			}
			else
				ctmp = "";
		}
		else if("nu".equals(type)) {
			itmp = random.nextInt(10) + 48; // 生成0~9的数字
			ctmp = String.valueOf((char) itmp);
		}
		else { // 默认生成数字、字母混合
			ctmp = String.valueOf(captchars[random.nextInt(captchars.length)]);
		}

		sRand += ctmp;
		g.setColor(getRandColor(20, 130)); // 设置深色前景色
		// 将文字旋转指定角度
		AffineTransform trans = new AffineTransform();
		//trans.rotate(random.nextInt(15) * 3.14 / 180, 12*i+5, 7);
		// 缩放文字1~1.1倍
		float scaleSize = random.nextInt(2)*0.1f + 1.0f;
		trans.scale(scaleSize, scaleSize);
		g.setTransform(trans);
		// 将字画到图片上
		g.drawString(ctmp, 12*i+5, 13+random.nextInt(5));
	}

	// 随机产生fline条前景干扰线
	g.setColor(getRandColor(20, 130));
	for (int i=0; i<fline; i++)
	{
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int xl = random.nextInt(12);
		int yl = random.nextInt(12);
		g.drawLine(x,y,x+xl,y+yl);
	}

	// 添加噪点
	float yawpRate = 0.012f; // 噪声率
	int area = (int) (yawpRate * width * height);
	for (int i = 0; i < area; i++) {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int rgb = getRandomIntColor();
		image.setRGB(x, y, rgb);
	}

	// 将生成的验证码保存到Session中
	//System.out.println(sRand);
	session.setAttribute("RANDOMICITYNUM", sRand);
	// 图象生效
	g.dispose();
	// 输出图象到页面
	ImageIO.write(image, "JPEG", response.getOutputStream());

%>