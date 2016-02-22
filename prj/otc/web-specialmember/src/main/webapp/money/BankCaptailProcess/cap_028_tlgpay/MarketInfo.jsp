<%@ page contentType="text/html;charset=GBK" %>
<%
String marketCode="00000000";//市场编号
String receiveUrl="http://IP:port/TLGPay/gnnt.do";//通联网关支付后台通知URL（适配器URL只需更新IP和端口）
String pickupUrl="http://IP:port/specialmember/money/BankCaptailProcess/cap_028_tlapay/back.jsp";//前台页面会跳URL 只需更新IP和端口
String md5Key="1234567890";//Md5秘钥
String certPath="key/TLCert.cer";//公钥证书地址  TLCert-test.cer

%>