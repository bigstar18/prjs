<%@ page contentType="text/html;charset=GBK"%>
<html><head>
<link href="${skinPath}/css/mgr/memberadmin/module.css" rel="stylesheet" type="text/css" />
<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
<script src="${publicPath }/js/jquery.validationEngine-zh_CN.js" type="text/javascript" charset="GBK"></script>
<script src="${publicPath }/js/validationengine/jquery.validationEngine.js" type="text/javascript" charset="GBK"></script>
<script type="text/javascript" src="${publicPath}/js/autocomplete/jquery.autocomplete.js"></script>
<script type="text/javascript" src="${publicPath}/js/PasswordMode.js"></script>
</head>
<body>
<a name="firstAnchor"></a> 
<div id="print" style="margin-left:15px;margin-right:15px;margin-top:30px;margin-bottom:20px;border:2px dashed #F4A460;">
<div align="center"><font size="4" color="#e73a49">��������֪</font></div>
<br/>&nbsp;&nbsp;&nbsp;&nbsp;<strong style="color:#e73a49">һ�� �����̵Ŀ�������</strong>
<br/>&nbsp;&nbsp;&nbsp;&nbsp;�ϸ�ķ��˽�����Ӧ�Ǿ��з����ʸ����Ӧ������֯�ʸ��ܶ������´�����Ʒ���׻�������е��������εľ�����֯���ϸ����Ȼ�˽�������������ʮ�����꣬������ȫ������Ϊ�����Ҿ���һ���Ļ����������������տ��������ͽ�����Ʒ���֪ʶ�Ĺ���
<br/>&nbsp;&nbsp;&nbsp;&nbsp;������������ʵ����Ч����ݿ������������뱣֤�ʽ���Դ�ĺϷ��Լ����ṩ���ϵ���ʵ�ԡ��Ϸ��ԡ���Ч�ԡ�
<br/>&nbsp;&nbsp;&nbsp;&nbsp;<strong style="color:#e73a49">���������ļ���ǩ��</strong>
<br/>&nbsp;&nbsp;&nbsp;&nbsp;�ϸ����Ȼ�˽����̿����������ɽ����̱�������ǩ�𿪻��ļ���
<br/>&nbsp;&nbsp;&nbsp;&nbsp;���˽����̿����������䷨�������ˣ������ˣ���ί�д�����ǩ�𿪻��ļ����������ڿ����ļ��ϼӸǷ��˽����̹��»��ͬר���¡�ί�д����˰����������ģ����˽�����Ӧ�ṩ�Ӹǻ������²����������ˣ������ˣ�ǩ������ʵ���Ϸ�����Ч�ġ���Ȩί���顷ԭ����
<br/>&nbsp;&nbsp;&nbsp;&nbsp;<strong style="color:#e73a49">���� ������ ����</strong>
<br/>&nbsp;&nbsp;&nbsp;&nbsp;��һ��֪����Ʒ���׷���
<br/>&nbsp;&nbsp;&nbsp;&nbsp;������Ӧ֪��������Ʒ���׾��з��գ�Ӧ��ϸ�Ķ������ո�֪�顷��ǩ��ȷ�ϡ�
<br/>&nbsp;&nbsp;&nbsp;&nbsp;����������������Ȩ�������(��Ա)���ý��ܽ����̵�ȫȨί�С�
<br/>&nbsp;&nbsp;&nbsp;&nbsp;������Ӧ֪����Ȩ�������(��Ա)���乤����Ա���ý��ܽ����̵�ȫȨί�У�������Ҳ����Ҫ����Ȩ�������(��Ա)���乤����Ա��ȫȨί�еķ�ʽ���д�����Ʒ���ס�
<br/>&nbsp;&nbsp;&nbsp;&nbsp;��������Ȩ�������(��Ա)��ʾ��ַ
<br/>&nbsp;&nbsp;&nbsp;&nbsp;�йس�������Ʒ��������Ȩ�������(��Ա)����Ϣ����ͨ���ٷ�����www.yrdce.cn���Ĺ�ʾ��Ϣ���в�ѯ�ͺ�ʵ��
<br/>&nbsp;&nbsp;&nbsp;&nbsp;���ģ�֪����Ȩ�������(��Ա)���йع涨
<br/>&nbsp;&nbsp;&nbsp;&nbsp;������Ӧ֪��ֻ�еõ���������Ȩ��ȡ����Ӧ�ʸ����Ȩ�������(��Ա)�����֧�������ܸ��ݡ���������Ʒ�������ֻ�������Ȩ�������(��Ա)����취(����)�������Э���������������ҵ��
</div>
<div id="noprint" align="right" style="margin-right:10px;margin-bottom:5px;"><input type="button" value="��ӡ" onclick="myPrint()"/></div>
<br/>
</body>
<SCRIPT type="text/javascript">
	window.onload=function(){
		window.location.hash = firstAnchor;
	}
	function myPrint(){
		document.getElementById("noprint").style.display="none";
		window.print();
		document.getElementById("noprint").style.display="";
	}
</SCRIPT>
</html>