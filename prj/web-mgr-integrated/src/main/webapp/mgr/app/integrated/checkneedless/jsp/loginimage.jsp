<%@ page contentType="image/jpeg;charset=GBK" import="java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*"%>
<%!
/**
 * 产生随机颜色
 * @param fc 前台颜色值
 * @param bc 后台颜色值
 * @return Color 随机颜色
 */
Color getRandColor(int fc,int bc){
	Random random=new Random();//随机数产生器
	if(fc>255) fc=255;
	if(bc>255) bc=255;
	int r=fc+random.nextInt(bc-fc);//红
	int g=fc+random.nextInt(bc-fc);//绿
	int b=fc+random.nextInt(bc-fc);//蓝
	return new Color(r,g,b);
}
%>
<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires",0);

int width=75,height=20;
//生成图片
BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
Graphics g=image.getGraphics();
g.setColor(getRandColor(200,250));
g.fillRect(0,0,width,height);
g.setFont(new Font("Times New Roman",Font.PLAIN,18));
g.setColor(getRandColor(160,200));
Random random=new Random();
for(int i=0;i<155;i++){//随机在图片上画155条随机线条
	int x=random.nextInt(width);
	int y=random.nextInt(height);
	int x1=random.nextInt(10);
	int y1=random.nextInt(10);
	g.drawLine(x,y,x+x1,y+y1);
}
char c[]=new char[62];//随机数数组
int n=0;
for(int i=97;i<123;i++){//a-z(i,l,o 分别转换成了j,m,p)
	if(i==105||i==108||i==111){
		c[n++]=(char)(i+1);
	}else{
		c[n++]=(char)i;
	}
}
for(int i=65;i<91;i++){//A-Z(I,L,O 分别替换成了 J,M,P)
	if(i==73||i==79||i==76){
		c[n++]=(char)(i+1);
	}else{
		c[n++]=(char)i;
	}
}
for(int i=48;i<58;i++){//0-9(0 替换成了 1)
	if(i==48){
		c[n++]=(char)(i+1);
	}else{
		c[n++]=(char)i;
	}
}
String sRand="";
for(int i=0;i<5;i++){//生成五个字符写入图片
	int x=random.nextInt(62);//生成62以内的整数
	String rand = String.valueOf(c[x]);//取得c数组相应位置的字符
	sRand+=rand;//增加到字符串中
	g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));//设置本次书写颜色
	g.drawString(rand,13*i+6,16);//写入字符
}
session.setAttribute("RANDOMICITYNUM",sRand);
g.dispose();
try{
	ImageIO.write(image,"JPEG",response.getOutputStream());
}catch(Exception e){}
out.clear();
out=pageContext.pushBody();
%>