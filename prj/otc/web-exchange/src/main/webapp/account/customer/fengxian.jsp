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
<div align="center"><font size="4" color="#e73a49">���ո�֪��</font></div>
<br/><strong style="color:#e73a49">�𾴵Ľ����̣�</strong>
<br/>&nbsp;&nbsp;&nbsp;&nbsp;���������ϸ�Ķ��������ո�֪�顷�����ݣ�����Ҫ��Ⲣǩ�𱾡����ո�֪�顷�󷽿��ڳ�������Ʒ�����������¼�ơ��������� �������Ͳ��뽻�ס�
<br/>&nbsp;&nbsp;&nbsp;&nbsp;��Ʒ�������з��յģ�����Ͷ�ʿ��ܻ�����ʧ���������ܳ�����ʼͶ�ʶ��ˣ�������������������״���ͷ��ճ����������ڽ��н���֮ǰ��������ȷ�������ݣ�
<br/>&nbsp;&nbsp;&nbsp;&nbsp;һ�������������Ķ������ؽ���������ع���������޷����㽻������ع����Ҫ���������еĹ�����Ʒ�����ܸ����йع��򱻴�Ϊת�ã������е��ɴ˲����ĺ����
<br/>&nbsp;&nbsp;&nbsp;&nbsp;�������ڹ��ҷ��ɷ��漰���ߵı仯����������ع�����޸ġ�������ʩ�ĳ�̨��ԭ�������еĹ�����Ʒ�����޷��������У������е��ɴ˵��µ���ʧ��
<br/>&nbsp;&nbsp;&nbsp;&nbsp;�����ڲ����Ͻ����������׹���涨�����������£��������޷��������������Ʒ������������������Ľ����ʽ��п����޷��ֲ�ȫ����ʧ�������е��ɴ˵��µ�ȫ����ʧ��
<br/>&nbsp;&nbsp;&nbsp;&nbsp;�ġ����ڵ���ˮ�֡����ֵȲ��ɿ������ػ��߼����ϵͳ��ͨѶϵͳ���ϵȽ���������Ԥ�������ܱ��Ⲣ���ܿ˷���ԭ�򣬿����������ָ���޷��ɽ������޷�ȫ���ɽ��������е��ɴ˵��µ���ʧ��
<br/>&nbsp;&nbsp;&nbsp;&nbsp;�塢�������������Ʒ����ʱ�����ڣ������������ڣ����·��գ������е��ɴ˵��µ���ʧ��
<br/>&nbsp;&nbsp;&nbsp;&nbsp;1.�����ȱ�����Ͻ��׾��飬���������������ɽ���ʧ�ܻ���ʧ��
<br/>&nbsp;&nbsp;&nbsp;&nbsp;2.���Ľ������붪ʧ�����˵��ã�
<br/>&nbsp;&nbsp;&nbsp;&nbsp;3.�����޷����ƺͲ���Ԥ���ϵͳ���ϡ��豸���ϡ�ͨѶ���ϡ��������ϡ�������ϵ����أ����ܵ���ϵͳ��������������̱����ʹ���Ľ���ָ������ӳ١��жϡ����ݴ���������
<br/>&nbsp;&nbsp;&nbsp;&nbsp;4.�������Ͻ���ϵͳ���ڱ�����ڿͺͼ�������������Ŀ����ԣ��ɴ˿��ܵ���ϵͳ���ϣ��ǽ����޷����м�������Ϣ���ִ�����ӳ٣�
<br/>&nbsp;&nbsp;&nbsp;&nbsp;5.�������ϵ����ݴ��������ͨ�ŷ�æ��ԭ������ӳ١��жϡ����ݴ������ȫ���Ӷ�ʹ���Ͻ��׳����ӳ١��жϵ��쳣���Ρ�
<br/>&nbsp;&nbsp;&nbsp;&nbsp;�������ո�֪�顷�޷���ʾ������Ʒ���׵����з��պͽ�����ȫ���޷����ƺͲ���Ԥ������Ρ����������н���֮ǰ��ȫ���˽⽻��������ع��򼰹涨��������ľ��ó������������տ������������弰����������������͹�������
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