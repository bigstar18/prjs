<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>

<html>
<head>
<link rel="stylesheet" href="${mgrPath}/skinstyle/default/css/app/common.css" type="text/css"/>
<script src="${publicPath }/js/jquery-1.6.min.js" type="text/javascript"></script>
<title></title>	
<script type="text/javascript">
 

var status;
window_onload = function() {
 	status=$("#status").attr("value");
}
function balanceChk_onclick(id)
{
  var name;
 
  if(id=='04')
  {
  name="���߸��½�������";
  }
  if (id == '13') {
  	name="���㱣֤��";
  }
  if (id == 'online') {
  	name = "���߸��½��׽�����";
  }
  if (id == 'recoverDelayTrade') {
  	name = "���׽���ת�ָ����ڽ���";
  }
  if (id == 'onlineDelay') {
  	name = "���߸������ڽ��׽�����";
  }
  if (confirm("��ȷ��Ҫ" + name + "��"))
  {
	  if(id=='04'){
	  	//���߸��½�������
		var updateDataUrl = $("#ok4").attr("action");
		$("#frm").attr("action",updateDataUrl);
		frm.submit();
	  }
	 if (id == '13') {
	 	//���㱣֤��
	  	var updateDataUrl = $("#ok13").attr("action");
		$("#frm").attr("action",updateDataUrl);
		frm.submit();
	 }
	 if (id == 'online') {
	  	//���߸��½��׽�����
	  	var updateDataUrl = $("#ok16").attr("action");
		$("#frm").attr("action",updateDataUrl);
		frm.submit();
	 }
	 if (id == 'recoverDelayTrade') {
	  	//���׽���ת�ָ����ڽ���
	  	var updateDataUrl = $("#ok17").attr("action");
		$("#frm").attr("action",updateDataUrl);
		frm.submit();
	 }
	 if (id == 'onlineDelay') {
	  	//���߸������ڽ��׽�����
	  	var updateDataUrl = $("#ok18").attr("action");
		$("#frm").attr("action",updateDataUrl);
		frm.submit();
	 }
  }
}

function balance(){
	if (confirm("��ȷ��Ҫ����������")) {
		if (('3' == status) || '10' == status) {
			var updateDataUrl = $("#ok14").attr("action");
			$("#frm").attr("action",updateDataUrl);
			frm.submit();
		}else {
			alert("ϵͳ���ǽ������״̬������������");
			return false;
		}
		
	}
	
}
//�������ñ�֤��
function operateMargin(){
	var url = $("#ok15").attr("action");
	if(showDialog(url, "", 600, 600)){
	
	}
}
//�����������Ᵽ֤��
function operateSpacMargin(){
	var url = $("#ok11").attr("action");
	showDialog(url, "", 600, 600);
}

function openingPrice(){//���̼�
	var url = $("#ok20").attr("action");
	showDialog(url, "", 600, 600);
}

</script>
</head>
<body  leftmargin="0" topmargin="0" onLoad="window_onload()">

<table border="0" height="300" align="center" >
<tr><td>
<form id="frm" method="post" action="">
	<fieldset  class="pickList">
	<legend class="common">��ͨ����</legend>
      <table border="1" align="center" cellpadding="5" cellspacing="5" class="commonTest" height="220" width="500">
      
    	<tr>
    		<td>
    			����
    			<input type="hidden" id="status" value="${sessionScope.status }">
    		</td>
    		<td>
    			˵��
    		</td>
    	</tr>
        <tr height="20">
        <td>
        <rightButton:rightButton name="���߸��½�������"  id="ok4" onclick="balanceChk_onclick('04');" className="button1" action="${basePath}/timebargain/xtwh/yccl4.action" ></rightButton:rightButton>
<!--        <button id="ok1" value="���߸��½�������" ></button>-->
        </td>
        
        <td>
        	"���ײ�������"�˵��µ�"�����г�����"��"��Ʒ����"ģ��Ķ�;"����������"�˵��µ�"��������"�Ķ�,��ʱ��Ч.
    	</td>
        </tr>
        <tr height="20">
        <td>
        <rightButton:rightButton name="�������ñ�֤��"  id="ok15" onclick="operateMargin();" className="button1" action="${basePath}/timebargain/xtwh/ycclEditMargin.action" ></rightButton:rightButton>
        </td>
        
        <td>
        	���ս��׽�ʹ���µı�֤���׼.����˳��"��ͣ����"-->"�������ñ�֤��"-->"���߸��½�������"-->"�ָ�����"
    	</td>
        </tr>
        
        <tr height="20">
        <td>
        <rightButton:rightButton name="�����������Ᵽ֤��"   id="ok11" onclick="operateSpacMargin();" className="button1" action="${basePath}/timebargain/xtwh/ycclSpacMargin.action" ></rightButton:rightButton>
        </td>
        
        <td>
        	���ս��׽�ʹ���µ����Ᵽ֤���׼.����˳��"��ͣ����"-->"�����������Ᵽ֤��"-->"���߸��½�������"-->"�ָ�����"
    	</td>
        </tr>
        
        <tr height="20">
        <td>
        <rightButton:rightButton name="���㱣֤��"   id="ok13" onclick="balanceChk_onclick('13');" className="button1" action="${basePath}/timebargain/xtwh/yccl13.action" ></rightButton:rightButton>
        </td>
        
        <td>
        	����ǰ��֤���׼,���¼����ѳɽ���ͬ��֤��.
    	</td>
        </tr>
        
        <tr height="20">
        <td>
        <rightButton:rightButton name="��������ϵͳ����"   id="ok14" onclick="balance();" className="button1" action="${basePath}/timebargain/xtwh/ycclbalanceChkFroenFundEXC.action" ></rightButton:rightButton>
        </td>
        
        <td>
        	�ٴζԽ���ϵͳ���н��㴦��.
    	</td>
        </tr>
        
        <tr>
		<td >
			<rightButton:rightButton name="���߸��½��׽�����"   id="ok16" onclick="balanceChk_onclick('online');" className="button1" action="${basePath}/timebargain/xtwh/ycclOnline.action" ></rightButton:rightButton>
		</td>
		
		<td>
			"���׽ڹ���"�еĽ��׽�����,�ڴ˴�����ʱ��Ч.
    	</td>
		</tr>

           <tr>
		<td >
			<rightButton:rightButton name="�޸Ŀ���ָ����"   id="ok20" onclick="openingPrice();" className="button1" action="${basePath}/timebargain/xtwh/yccl20.action" ></rightButton:rightButton>
		</td>
		
		<td>
			������ڳ�ʼ�����״̬���޸ĵĻ����޸ĳɹ���Ҫ�������߸��½������ݰ�ťˢ���ڴ�
    	</td>
		</tr>
      </table>
  	</fieldset>

<c:choose>
	<c:when test="${sessionScope.useDelay=='Y'}">
  	<fieldset  class="pickList">
	<legend class="common">���ڲ���</legend>
      <table border="1" align="center" cellpadding="5" cellspacing="5" class="commonTest"  width="500">
      
        <tr height="20">
        <td>
        <rightButton:rightButton name="���׽���ת�ָ����ڽ���"   id="ok17" onclick="balanceChk_onclick('recoverDelayTrade');" className="button1" action="${basePath}/timebargain/xtwh/ycclRecoverDelayTrade.action" ></rightButton:rightButton>
        </td>
        
        <td>
        	���ڽ��׵�ʱ����,�ָ��ѽ��������ڽ���.
    	</td>
        </tr>
        
        <tr>
		<td >
			<rightButton:rightButton name="���߸������ڽ��׽�����"   id="ok18" onclick="balanceChk_onclick('onlineDelay');" className="button1" action="${basePath}/timebargain/xtwh/ycclOnlineDelay.action" ></rightButton:rightButton>
		</td>
		
		<td>
			"���ڽ��׽ڹ���"�е����ڽ��׽�����,�ڴ˴�����ʱ��Ч.
    	</td>
		</tr>

      </table>
  	</fieldset>
  	</c:when>
</c:choose>

<form>
</td></tr>
</table>


</body>
</html>