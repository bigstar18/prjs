<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>

<html>
<head>
<%
  //String market=(String)request.getAttribute("market");
  String status = (String)request.getAttribute("status");
 %>
<title></title>	
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<script type="text/javascript">
function balanceChk_onclick(id)
{
  var name;
 
  if(id=='04')
  {
  name="���߸��½�������";
  }
 
  if (id == '12') {
  	name="�ָ�����";
  }
  if (id == '13') {
  	name="���㱣֤��";
  }
  if (id == '14') {
  	name="��������ϵͳ����";
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
  agencyForm.type.value=id;
  agencyForm.submit();

  document.getElementsByName('ok3').disabled = true;
  document.getElementsByName('ok12').disabled = true;
  document.getElementsByName('ok13').disabled = true;
  document.getElementsByName('ok14').disabled = true;
  document.getElementsByName('ok15').disabled = true;
  document.getElementsByName('ok16').disabled = true;
  document.getElementsByName('ok17').disabled = true;
  document.getElementsByName('ok18').disabled = true;
  }
}

function balance(){
	if (confirm("��ȷ��Ҫ����������")) {
		if (('3' == '<%=status%>') || '10' == '<%=status%>') {
			parent.HiddFrame.location.href = "<c:url value="/timebargain/balance/balance.do?funcflg=balanceChkFroenFundEXC"/>";
		}else {
			alert("ϵͳ���ǽ������״̬������������");
			return false;
		}
		
	}
	
}

function operateMargin(){
	
	pTop("<c:url value="/timebargain/xtgl/agency_excption_margin.jsp"/>",600,600);
}

function operateSpacMargin(){
	pTop("<c:url value="/timebargain/xtgl/agency_excption_spacMargin.jsp"/>",600,600);
}

function online(){
	document.location.href = "<c:url value="/timebargain/baseinfo/tradeTime.do?funcflg=onLine"/>";
}

function openingPrice(){//���̼�
	pTop("<c:url value="/timebargain/xtgl/agency_openingPrice.jsp"/>",600,600);
}

</script>
</head>
<body  leftmargin="0" topmargin="0" onLoad="" onkeypress="keyEnter(event.keyCode);">

<table border="0" height="300" align="center" >
<tr><td>
<html:form method="post" action="/timebargain/xtgl/agency.do?funcflg=operate" target="HiddFrame">
	<fieldset  class="pickList">
	<legend class="common">��ͨ����</legend>
      <table border="1" align="center" cellpadding="5" cellspacing="5" class="commonTest" height="220" width="500">
      
    	<tr>
    		<td>
    			����
    		</td>
    		<td>
    			˵��
    		</td>
    	</tr>
        <tr height="20">
        <td>
        <html:button style="width:140px" property="ok3" styleClass="button" onclick="javascript:balanceChk_onclick('04');">&nbsp;���߸��½�������&nbsp;</html:button>
        </td>
        
        <td>
        	"���ײ�������"�˵��µ�"�����г�����"��"��Ʒ����"ģ��Ķ�;"����������"�˵��µ�"��������"�Ķ�,��ʱ��Ч.
    	</td>
        </tr> 
        
        <!--  
        ��Ϊ��ȥ������ɻָ�����ʱ�ڽ��׽���ʱ�Զ�������ί�У���϶����л����ڣ�
�����Ҫ�˹��ܿ����˹���db�е�״̬��������timebargain�����ܱ�֤�ڴ��dbһ�¡�
        <tr height="20">
        <td>
        <html:button style="width:140px" property="ok12" styleClass="button" onclick="javascript:balanceChk_onclick('12');">���׽���ת�ָ�����</html:button>
        </td>
        
        <td>
        	����ʱ����,�ָ��ѽ����Ľ���.
    	</td>
        </tr>
        -->
        <tr height="20">
        <td>
        <html:button style="width:140px" property="ok15" styleClass="button" onclick="javascript:operateMargin();">&nbsp;&nbsp;�������ñ�֤��&nbsp;&nbsp;</html:button>
        </td>
        
        <td>
        	���ս��׽�ʹ���µı�֤���׼.����˳��"��ͣ����"-->"�������ñ�֤��"-->"���߸��½�������"-->"�ָ�����"
    	</td>
        </tr>
        
        <tr height="20">
        <td>
        <html:button style="width:140px" property="ok15" styleClass="button" onclick="javascript:operateSpacMargin();">&nbsp;&nbsp;�����������Ᵽ֤��&nbsp;&nbsp;</html:button>
        </td>
        
        <td>
        	���ս��׽�ʹ���µ����Ᵽ֤���׼.����˳��"��ͣ����"-->"�����������Ᵽ֤��"-->"���߸��½�������"-->"�ָ�����"
    	</td>
        </tr>
        
        <tr height="20">
        <td>
        <html:button style="width:140px" property="ok13" styleClass="button" onclick="javascript:balanceChk_onclick('13');">&nbsp;&nbsp;&nbsp;&nbsp;���㱣֤��&nbsp;&nbsp;&nbsp;&nbsp;</html:button>
        </td>
        
        <td>
        	����ǰ��֤���׼,���¼����ѳɽ���ͬ��֤��.
    	</td>
        </tr>
        
        <tr height="20">
        <td>
        <html:button style="width:140px" property="ok14" styleClass="button" onclick="return balance()">&nbsp;&nbsp;&nbsp;��������ϵͳ����&nbsp;&nbsp;&nbsp;</html:button>
        </td>
        
        <td>
        	�ٴζԽ���ϵͳ���н��㴦��.
    	</td>
        </tr>
        
        <tr>
		<td >
			<html:button style="width:140px" property="ok16" styleClass="button" onclick="javascript:balanceChk_onclick('online');">
				���߸��½��׽�����
			</html:button>
		</td>
		
		<td>
			"���׽ڹ���"�еĽ��׽�����,�ڴ˴�����ʱ��Ч.
    	</td>
		</tr>

           <tr>
		<td >
			<html:button style="width:140px" property="ok20" styleClass="button" onclick="javascript:openingPrice();"disabled="${status != 0 && status != 3}" >
				�޸Ŀ���ָ����
			</html:button>
		</td>
		
		<td>
			������ڳ�ʼ�����״̬���޸ĵĻ����޸ĳɹ���Ҫ�������߸��½������ݰ�ťˢ���ڴ�
    	</td>
		</tr>

        <html:hidden property="marketStatus"/>
        <html:hidden property="type"/>

      </table>
  	</fieldset>
  	
  	<%
  	 String useDelay=(String)request.getAttribute("useDelay");
  	if("Y".equals(useDelay)){
  	%>
  	<fieldset  class="pickList">
	<legend class="common">���ڲ���</legend>
      <table border="1" align="center" cellpadding="5" cellspacing="5" class="commonTest"  width="500">
      
        <tr height="20">
        <td>
        <html:button style="width:140px" property="ok17" styleClass="button" onclick="javascript:balanceChk_onclick('recoverDelayTrade');">
        	���׽���ת�ָ����ڽ���
        </html:button>
        </td>
        
        <td>
        	���ڽ��׵�ʱ����,�ָ��ѽ��������ڽ���.
    	</td>
        </tr>
        
        <tr>
		<td >
			<html:button style="width:140px" property="ok18" styleClass="button" onclick="javascript:balanceChk_onclick('onlineDelay');">
				���߸������ڽ��׽�����
			</html:button>
		</td>
		
		<td>
			"���ڽ��׽ڹ���"�е����ڽ��׽�����,�ڴ˴�����ʱ��Ч.
    	</td>
		</tr>

      </table>
  	</fieldset>
  	<%
  	}
  	
  	%>
</html:form>
</td></tr>
</table>

<%@ include file="/timebargain/common/messages.jsp" %> 
</body>
</html>
<script type="text/javascript">
<%
 if("3".equals(status) || "10".equals(status))
  {
  %>
   document.getElementsByName('ok14').disabled = false;
  
  <%
  }else {
  %>
  document.getElementsByName('ok14').disabled = true;
  <%
  }
%>
</script>
