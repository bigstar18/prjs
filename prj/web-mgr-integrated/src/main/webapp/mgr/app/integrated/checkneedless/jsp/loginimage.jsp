<%@ page contentType="image/jpeg;charset=GBK" import="java.awt.*,java.awt.image.*,java.util.*,javax.imageio.*"%>
<%!
/**
 * ���������ɫ
 * @param fc ǰ̨��ɫֵ
 * @param bc ��̨��ɫֵ
 * @return Color �����ɫ
 */
Color getRandColor(int fc,int bc){
	Random random=new Random();//�����������
	if(fc>255) fc=255;
	if(bc>255) bc=255;
	int r=fc+random.nextInt(bc-fc);//��
	int g=fc+random.nextInt(bc-fc);//��
	int b=fc+random.nextInt(bc-fc);//��
	return new Color(r,g,b);
}
%>
<%
response.setHeader("Pragma","No-cache");
response.setHeader("Cache-Control","no-cache");
response.setDateHeader("Expires",0);

int width=75,height=20;
//����ͼƬ
BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
Graphics g=image.getGraphics();
g.setColor(getRandColor(200,250));
g.fillRect(0,0,width,height);
g.setFont(new Font("Times New Roman",Font.PLAIN,18));
g.setColor(getRandColor(160,200));
Random random=new Random();
for(int i=0;i<155;i++){//�����ͼƬ�ϻ�155���������
	int x=random.nextInt(width);
	int y=random.nextInt(height);
	int x1=random.nextInt(10);
	int y1=random.nextInt(10);
	g.drawLine(x,y,x+x1,y+y1);
}
char c[]=new char[62];//���������
int n=0;
for(int i=97;i<123;i++){//a-z(i,l,o �ֱ�ת������j,m,p)
	if(i==105||i==108||i==111){
		c[n++]=(char)(i+1);
	}else{
		c[n++]=(char)i;
	}
}
for(int i=65;i<91;i++){//A-Z(I,L,O �ֱ��滻���� J,M,P)
	if(i==73||i==79||i==76){
		c[n++]=(char)(i+1);
	}else{
		c[n++]=(char)i;
	}
}
for(int i=48;i<58;i++){//0-9(0 �滻���� 1)
	if(i==48){
		c[n++]=(char)(i+1);
	}else{
		c[n++]=(char)i;
	}
}
String sRand="";
for(int i=0;i<5;i++){//��������ַ�д��ͼƬ
	int x=random.nextInt(62);//����62���ڵ�����
	String rand = String.valueOf(c[x]);//ȡ��c������Ӧλ�õ��ַ�
	sRand+=rand;//���ӵ��ַ�����
	g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));//���ñ�����д��ɫ
	g.drawString(rand,13*i+6,16);//д���ַ�
}
session.setAttribute("RANDOMICITYNUM",sRand);
g.dispose();
try{
	ImageIO.write(image,"JPEG",response.getOutputStream());
}catch(Exception e){}
out.clear();
out=pageContext.pushBody();
%>