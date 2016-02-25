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
  name="在线更新交易数据";
  }
 
  if (id == '12') {
  	name="恢复交易";
  }
  if (id == '13') {
  	name="重算保证金";
  }
  if (id == '14') {
  	name="重做交易系统结算";
  }
  if (id == 'online') {
  	name = "在线更新交易节设置";
  }
  if (id == 'recoverDelayTrade') {
  	name = "交易结束转恢复延期交易";
  }
  if (id == 'onlineDelay') {
  	name = "在线更新延期交易节设置";
  }
  if (confirm("您确定要" + name + "吗？"))
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
	if (confirm("您确定要重做结算吗？")) {
		if (('3' == '<%=status%>') || '10' == '<%=status%>') {
			parent.HiddFrame.location.href = "<c:url value="/timebargain/balance/balance.do?funcflg=balanceChkFroenFundEXC"/>";
		}else {
			alert("系统不是结算完成状态，不能重做！");
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

function openingPrice(){//开盘价
	pTop("<c:url value="/timebargain/xtgl/agency_openingPrice.jsp"/>",600,600);
}

</script>
</head>
<body  leftmargin="0" topmargin="0" onLoad="" onkeypress="keyEnter(event.keyCode);">

<table border="0" height="300" align="center" >
<tr><td>
<html:form method="post" action="/timebargain/xtgl/agency.do?funcflg=operate" target="HiddFrame">
	<fieldset  class="pickList">
	<legend class="common">普通操作</legend>
      <table border="1" align="center" cellpadding="5" cellspacing="5" class="commonTest" height="220" width="500">
      
    	<tr>
    		<td>
    			操作
    		</td>
    		<td>
    			说明
    		</td>
    	</tr>
        <tr height="20">
        <td>
        <html:button style="width:140px" property="ok3" styleClass="button" onclick="javascript:balanceChk_onclick('04');">&nbsp;在线更新交易数据&nbsp;</html:button>
        </td>
        
        <td>
        	"交易参数设置"菜单下的"交易市场参数"和"商品管理"模块改动;"交易商设置"菜单下的"特殊设置"改动,即时生效.
    	</td>
        </tr> 
        
        <!--  
        因为不去掉会造成恢复交易时在交易结束时自动撤单的委托，撮合队列中还存在；
如果需要此功能可以人工改db中的状态，并重启timebargain，就能保证内存和db一致。
        <tr height="20">
        <td>
        <html:button style="width:140px" property="ok12" styleClass="button" onclick="javascript:balanceChk_onclick('12');">交易结束转恢复交易</html:button>
        </td>
        
        <td>
        	交易时间内,恢复已结束的交易.
    	</td>
        </tr>
        -->
        <tr height="20">
        <td>
        <html:button style="width:140px" property="ok15" styleClass="button" onclick="javascript:operateMargin();">&nbsp;&nbsp;在线设置保证金&nbsp;&nbsp;</html:button>
        </td>
        
        <td>
        	本日交易将使用新的保证金标准.操作顺序"暂停交易"-->"在线设置保证金"-->"在线更新交易数据"-->"恢复交易"
    	</td>
        </tr>
        
        <tr height="20">
        <td>
        <html:button style="width:140px" property="ok15" styleClass="button" onclick="javascript:operateSpacMargin();">&nbsp;&nbsp;在线设置特殊保证金&nbsp;&nbsp;</html:button>
        </td>
        
        <td>
        	本日交易将使用新的特殊保证金标准.操作顺序"暂停交易"-->"在线设置特殊保证金"-->"在线更新交易数据"-->"恢复交易"
    	</td>
        </tr>
        
        <tr height="20">
        <td>
        <html:button style="width:140px" property="ok13" styleClass="button" onclick="javascript:balanceChk_onclick('13');">&nbsp;&nbsp;&nbsp;&nbsp;重算保证金&nbsp;&nbsp;&nbsp;&nbsp;</html:button>
        </td>
        
        <td>
        	按当前保证金标准,重新计算已成交合同保证金.
    	</td>
        </tr>
        
        <tr height="20">
        <td>
        <html:button style="width:140px" property="ok14" styleClass="button" onclick="return balance()">&nbsp;&nbsp;&nbsp;重做交易系统结算&nbsp;&nbsp;&nbsp;</html:button>
        </td>
        
        <td>
        	再次对交易系统进行结算处理.
    	</td>
        </tr>
        
        <tr>
		<td >
			<html:button style="width:140px" property="ok16" styleClass="button" onclick="javascript:balanceChk_onclick('online');">
				在线更新交易节设置
			</html:button>
		</td>
		
		<td>
			"交易节管理"中的交易节设置,在此触发即时生效.
    	</td>
		</tr>

           <tr>
		<td >
			<html:button style="width:140px" property="ok20" styleClass="button" onclick="javascript:openingPrice();"disabled="${status != 0 && status != 3}" >
				修改开盘指导价
			</html:button>
		</td>
		
		<td>
			如果是在初始化完成状态下修改的话，修改成功需要触发在线更新交易数据按钮刷新内存
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
	<legend class="common">延期操作</legend>
      <table border="1" align="center" cellpadding="5" cellspacing="5" class="commonTest"  width="500">
      
        <tr height="20">
        <td>
        <html:button style="width:140px" property="ok17" styleClass="button" onclick="javascript:balanceChk_onclick('recoverDelayTrade');">
        	交易结束转恢复延期交易
        </html:button>
        </td>
        
        <td>
        	延期交易的时间内,恢复已结束的延期交易.
    	</td>
        </tr>
        
        <tr>
		<td >
			<html:button style="width:140px" property="ok18" styleClass="button" onclick="javascript:balanceChk_onclick('onlineDelay');">
				在线更新延期交易节设置
			</html:button>
		</td>
		
		<td>
			"延期交易节管理"中的延期交易节设置,在此触发即时生效.
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
