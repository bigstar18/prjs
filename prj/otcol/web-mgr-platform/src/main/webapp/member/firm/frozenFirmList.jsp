<%@ page contentType="text/html;charset=GBK" %>
<html>
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <title>交易商列表</title>
    <script language="javascript" src="<%=basePathF%>/public/jstools/tools.js"></script>
	<script language="javascript" src="<%=basePathF%>/public/jstools/common.js"></script>
	<script language="JavaScript">
	    function init(){
		//	  changeOrder(sign);  
		//	  initPar();
		//	  doQuery();
		}
		
		function doQuery(){
			frm_query.submit();
		}
		function resetForm(){
			document.getElementById("a.firmId").value=""; 
			frm_query.name.value = "";
			frm_query.type.value = "";
			frm_query.contactMan.value = "";
			frm_query.phone.value = "";
			frm_query.bankAccount.value = "";
			frm_query.zztype.value = "";
			
		}
		function editInfo(vCode){
			var returnValue = openDialog("<%=basePath%>/firm/editNewFirm.jsp?firmId="+vCode+"&date="+Date() ,"_blank",750,500);
			if(returnValue)
			    frm_query.submit();
				//window.location.reload();
			//window.open("<%=basePath%>/firm/editFirm.jsp?firmId="+vCode);
		}
		function setStatus(frm_delete,tableList,checkName)
		{
			var ak="";
		   if(isSelNothing(tableList,checkName) == -1)
			{
				alert ( "没有可以操作的数据！" );
				return;
			}
			if(isSelNothing(tableList,checkName))
			{
				alert ( "请选择需要操作的数据！" );
				return;
			}
			if(frm_delete.status.value=="")
			{
			    alert("未设置状态");
			    return;
			}else{
				if(frm_delete.status.value=="N"){
			//		ak="交易商每日最大转账金额将被不限制，";
				}
				if(frm_delete.status.value=="U"){
		//			ak="交易商下属交易员将被强制下线且交易商将无法出入金，";
				}
			}
			if(confirm(ak+"您确实要处理选中数据吗？"))
			{
				frm_delete.submit();
			}
		}
		function repairInfo(vCode){
			window.location="<%=basePath%>/firmController.mem?funcflg=traderList&firmId=" +vCode;
		}
	</script>
  </head>
  <body onload="init();">
  	<form id="frm_query" action="<%=basePath%>/firmController.mem?funcflg=frozenFirm" method="post">
		<input type="hidden" name="orderField" value="${orderFiled}">
		<input type="hidden" name="orderDesc" value="${orderType}">
		<input type="hidden" name="pageSize" value="<c:out value="${pageInfo.pageSize}"/>">
		<input type="hidden" id="pageNo" name="pageNo">
		<fieldset width="95%">
			<legend>交易商查询</legend>
			<table border="0" cellspacing="0" cellpadding="0" width="90%" height="35">
				<tr height="35">
					<td align="right"><%=FIRMID%>&nbsp;</td>
					<td align="left">
						<input id="a.firmId" name="_a.firmId[like]" value="<c:out value='${oldParams["a.firmId[like]"]}'/>" 
						onkeyup="autoComplete()" onblur="setDisplay();"
						type=text class="text" onkeydown="keyEnter();" style="width: 100px">
						<div id="divContent" style="display:none; position:absolute; background-color:#ffffff;border: solid 1px #9DCEE8 "></div>
					</td>
					<td align="right"><%=FIRMNAME%>&nbsp;</td>
					<td align="left">
						<input id="name" name="_name[like]" value="<c:out value='${oldParams["name[like]"]}'/>" type=text  class="text" style="width: 100px">
					</td>
					<td align="right">转账类型&nbsp;</td>
					<td align="left">
						<select id="zztype" name="_zztype[=]" class="normal" style="width: 80px">
							<OPTION value="all">全部</OPTION>
							<option value="01">委托代扣</option>
        		            <option value="02">银商转账</option>
           		            
						</select>
					</td>
					
					<td align="right">类型&nbsp;</td>
					<td align="left">
						<select id="type" name="_type[=]" class="normal" style="width: 60px">
							<OPTION value="">全部</OPTION>
							<option value="1">法人</option>
        		            <option value="2">代理</option>
           		            <option value="3">个人</option>
						</select>
					</td>
					<script>
						frm_query.type.value = "<c:out value='${oldParams["type[=]"]}'/>"
					</script>
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">查询</button>&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">重置</button>&nbsp;
					</td>
				</tr>
				<tr height="35">
					<td align="right">联系人&nbsp;</td>
					<td align="left">
						<input id="contactMan" name="_contactMan[like]" value="<c:out value='${oldParams["contactMan[like]"]}'/>" type=text class="text" style="width: 100px">
					</td>
					<td align="right">联系电话&nbsp;</td>
					<td align="left">
						<input id="phone" name="_phone[like]" value="<c:out value='${oldParams["phone[like]"]}'/>" type=text  class="text" style="width: 100px">
					</td>
					<td align="right">银行账号&nbsp;</td>
					<td colspan="4" align="left">
						<input id="bankAccount" name="_bankAccount[=]" value="<c:out value='${oldParams["bankAccount[=]"]}'/>" type=text  class="text" style="width: 200px">
					</td>
					
				</tr>
			</table>

		</fieldset>
	</form>
	<form id="frm_delete" name="frm_delete" action="<%=basePath%>/firmController.mem?funcflg=frozenFirmStatus" method="post" targetType="hidden" callback="doQuery();">
	  <table id="tableList" border="0" cellspacing="0" cellpadding="0" width="100%" height="400">
  		<tHead>
  			<tr height="25">
  				<td class="panel_tHead_LB">&nbsp;</td>
				<td class="panel_tHead_MB"><input type="checkbox" id="checkAll" onclick="selectAll(tableList,'delCheck')"></td>
				<td class="panel_tHead_MB" abbr="firmId"><%=FIRMID%></td>
				<td class="panel_tHead_MB" abbr="name"><%=FIRMNAME%></td>
				<td class="panel_tHead_MB" abbr="fullname"><%=FULLNAME%></td>
				<td class="panel_tHead_MB" abbr="contactMan">联系人</td>
				<td class="panel_tHead_MB" abbr="BrokerID">所属加盟商</td>
				<td class="panel_tHead_MB" abbr="createTime">创建时间</td>
				<!-- <td class="panel_tHead_MB" abbr="extractValue(xmlType(extenddata),'/root/corporate/text()')">法人</td>
				<td class="panel_tHead_MB" abbr="extractValue(xmlType(extenddata),'/root/code/text()')">企业编号</td> -->
				<td class="panel_tHead_MB" abbr="type">类型</td>
				<td class="panel_tHead_MB" abbr="status">状态</td>
				<td class="panel_tHead_RB">&nbsp;</td>
			</tr>
		</tHead>
		<tBody>
	  		<c:forEach items="${resultList}" var="result">
	  		<tr height="22" onclick="selectTr();">
	  			<td class="panel_tBody_LB">&nbsp;</td>
	  			<td class="underLine">
	  				<input name="delCheck" type="checkbox" value="<c:out value="${result.firmId}"/>">
	  			</td>
	  			<td class="underLine">
	  				<c:out value="${result.firmId}"/></td>
	  			<td class="underLine"><c:out value="${result.name}"/></td>
	  			<td class="underLine"><c:out value="${result.fullname}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.contactMan}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.BrokerID}"/>&nbsp;</td>
	  			<td class="underLine"><fmt:formatDate value="${result.createTime}" pattern="yyyy-MM-dd"/></td>
	  			<!-- <td class="underLine"><c:out value="${result.corporate}"/>&nbsp;</td>
	  			<td class="underLine"><c:out value="${result.code}"/>&nbsp;</td> -->
	  			<td class="underLine">
	  			<c:if test="${result.type==1}">法人</c:if>
			    <c:if test="${result.type==2}">代理</c:if>
			    <c:if test="${result.type==3}">个人</c:if>
	  			</td>
	  			<td class="underLine">
	  			<c:if test="${result.status=='N'}">正常</c:if>
	  			<c:if test="${result.status=='D'}">禁止</c:if>
			    <c:if test="${result.status=='U'}">冻结</c:if>
			    <c:if test="${result.status=='E'}">退市</c:if>
	  			</td>
	  			<td class="panel_tBody_RB">&nbsp;</td>
	  		</tr>
	  		</c:forEach>
	  	</tBody>
	  	<tFoot>
			<tr height="100%">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td colspan="10">&nbsp;</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tBody_LB">&nbsp;</td>
				<td class="pager" colspan="10">
					<%@ include file="../public/pagerInc.jsp" %>
				</td>
				<td class="panel_tBody_RB">&nbsp;</td>
			</tr>
			<tr height="22">
				<td class="panel_tFoot_LB">&nbsp;</td>
				<td class="panel_tFoot_MB" colspan="10"></td>
				<td class="panel_tFoot_RB">&nbsp;</td>
			</tr>
		</tFoot>
	</table>
	</from>
   	<table border="0" cellspacing="0" cellpadding="0" width="80%">
	    <tr height="40">
            <td><div align="right">
              <select name="status" onchange="selectStatusChange(this)">
              <option value="">请选择</option>
              <option value="U">禁止交易</option>
              <option value="N">恢复交易</option>
              </select>&nbsp;&nbsp;
              <button class="mdlbtn" type="button" id="clickTrue"  onclick="setStatus(frm_delete,tableList,'delCheck')">冻结交易商</button>&nbsp;&nbsp;
            </div></td>
        </tr>
    </table>
  </body>
</html>
<%@ include file="../public/footInc.jsp" %>
<script language="JavaScript">
function CreateAjaxObject(){
  if (window.XMLHttpRequest&&!(window.ActiveXObject)) 
	{ 	
		req = new XMLHttpRequest(); 
	}
	else if (window.ActiveXObject)
	{	
		req = new ActiveXObject("Microsoft.XMLHTTP"); 
	} 
   return req;
}
//输入信息的文本框
        var txtInput;
        //下拉表当前选中项的索引 
        var currentIndex = -1;
        var requestObj;
        var fId =[];
        //初始化参数,和下拉表位置
        function initPar()
        {
             txtInput = document.getElementById("firmId");
             //设置下拉表 相对于 文本输入框的位置 
             setPosition();
        } 
        
        //设置下拉表 相对于 文本输入框的位置
        function setPosition()
        {
            var width = txtInput.offsetWidth;
            var left = getLength("offsetLeft");
            var top = getLength("offsetTop") + txtInput.offsetHeight;
			divContent.style.left = left;
			divContent.style.top = top; 
			divContent.style.width = width + "px";
        } 
        
       //获取对应属性的长度 
        function getLength(attr)
        {
            var offset = 0;
            var item = txtInput;
            while (item)
            {
                offset += item[attr];
                item = item.offsetParent;
            } 
            return offset; 
        } 
        //自动完成
        function autoComplete()
        {	setPosition();
            //如果按下 向上, 向下 或 回车
            if (event.keyCode == 38 || event.keyCode == 40 || event.keyCode == 13)
            { 
                //选择当前项 
                selItemByKey();
            } 
            else //向服务器发送请求
            { 
                //如果值为空 
                if (txtInput.value == "")
                {
                    divContent.style.display='none'; 
                    return;
                } 
                //恢复下拉选择项为 -1 
                currentIndex = -1; 
                //开始请求
				var urlStr="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>/common/getFirmIdList.jsp?firmid="+txtInput.value+"&times="+Date();
                requestObj = CreateAjaxObject();
                requestObj.onreadystatechange = displayResult;
                requestObj.open('GET', urlStr, true);
                requestObj.send(null); 
            } 
        } 
        
        //显示结果 
        function displayResult()
        {
             if (requestObj.readyState == 4)
             {
                     showData();
                     divContent.style.display = "";
             } 
        } 
        
        //显示服务器返回的结果 ,并形成下拉表
        function showData()
        {
             //获取数据 
             var xmlData = requestObj.responseXML;
             //显示数据
            var pictures = null;
            	pictures = xmlData.getElementsByTagName("item");
			   for (var i=0;i<pictures.length;i++)
			   {
			     fId[i]= pictures[i].getElementsByTagName("fId")[0].firstChild.nodeValue;
			   }
			   var res='<table id="tblContent" width="100%" style="background-color:#ffffff">';
			   var txtValue = txtInput.value;
			   var len = pictures.length;
			   	for (var i=0;i<len;i++)
				   {	
				   		if(fId[i].substr(0,txtValue.length).toLowerCase()==txtValue.toLowerCase()){
				   			res+="<tr>";
						    res+="<td onclick='selValue()' style=cursor:hand onmouseover=\"clearColor();this.parentElement.style.backgroundColor='#ccc000';\" onmouseout=\"clearColor()\">"
						    res+=fId[i]+"</td></tr>";
					   }			
				   }
			   res+="</table>";
             //显示转后后的结果
             divContent.innerHTML = res;
        } 
        
        //通过键盘选择下拉项 
        function selItemByKey()
        {
            //下拉表 
            var tbl = document.getElementById("tblContent"); 
            if (!tbl)
            {
                return; 
            } 
            //下拉表的项数
            var maxRow = tbl.rows.length; 
            //向上 
            if (event.keyCode == 38 && currentIndex > 0)
            {
                 currentIndex--;
            } 
            //向下 
            else if (event.keyCode == 40 && currentIndex < maxRow-1)
            {
                 currentIndex++;
            }
            //回车 
            else if (event.keyCode == 13)
            {
                selValue();
                return;
            } 
            
            clearColor();
            txtInput.value = tbl.rows[currentIndex].innerText; 
            //设置当前项背景颜色为blue 标记选中 
            tbl.rows[currentIndex].style.backgroundColor = "#ccc000"; 
        } 
        
        //清除下拉项的背景颜色 
        function clearColor()
        {
             var tbl = document.getElementById("tblContent");
             for (var i = 0; i < tbl.rows.length; i++)
             {
                    tbl.rows[i].style.backgroundColor = ""; 
             } 
        } 
        function clearTab() 
        {
        	//清除数据
        	var tbl = document.getElementById("tblContent");
			for(var i=0;i<tbl.rows.length;i++)
			{						
				tbl.deleteRow(i);
			}
        }
        //选择下拉表中当前项的值 ,用于按回车或鼠标单击选中当前项的值
        function selValue()
        {
            if (event.keyCode != 13)
            { 
                var text = event.srcElement.innerText;
                txtInput.value = text; 
            } 
            initList(); 
        } 
        
        //文本框失去焦点时 设置下拉表可见性 
        function setDisplay()
        {
            //获取当前活动td的表格 
            if (document.activeElement.tagName == "TD")
            {
                 var tbl = document.activeElement.parentElement.parentElement.parentElement; 
                //如果不是下拉表,则隐藏 下拉表 
                if (tbl.id != "tblContent")
                {
                    initList();
                }
                return;
            } 
            
            initList();
            
        } 
       
        function initList()
        {
            divContent.style.display='none'; 
            divContent.innerHTML = "";
            currentIndex = -1;
        }
        function selectStatusChange(sel){
        	var value=sel.options[sel.selectedIndex].value;
        	if(value=="" || value=="U"){
        		document.getElementById("clickTrue").innerHTML="冻结交易商";
        	}else{
        		document.getElementById("clickTrue").innerHTML="恢复交易商";
        	}
		}
       
</script>