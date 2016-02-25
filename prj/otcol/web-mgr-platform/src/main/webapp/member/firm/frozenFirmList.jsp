<%@ page contentType="text/html;charset=GBK" %>
<html>
  <head>
    <%@ include file="../public/headInc.jsp" %>
    <title>�������б�</title>
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
				alert ( "û�п��Բ��������ݣ�" );
				return;
			}
			if(isSelNothing(tableList,checkName))
			{
				alert ( "��ѡ����Ҫ���������ݣ�" );
				return;
			}
			if(frm_delete.status.value=="")
			{
			    alert("δ����״̬");
			    return;
			}else{
				if(frm_delete.status.value=="N"){
			//		ak="������ÿ�����ת�˽��������ƣ�";
				}
				if(frm_delete.status.value=="U"){
		//			ak="��������������Ա����ǿ�������ҽ����̽��޷������";
				}
			}
			if(confirm(ak+"��ȷʵҪ����ѡ��������"))
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
			<legend>�����̲�ѯ</legend>
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
					<td align="right">ת������&nbsp;</td>
					<td align="left">
						<select id="zztype" name="_zztype[=]" class="normal" style="width: 80px">
							<OPTION value="all">ȫ��</OPTION>
							<option value="01">ί�д���</option>
        		            <option value="02">����ת��</option>
           		            
						</select>
					</td>
					
					<td align="right">����&nbsp;</td>
					<td align="left">
						<select id="type" name="_type[=]" class="normal" style="width: 60px">
							<OPTION value="">ȫ��</OPTION>
							<option value="1">����</option>
        		            <option value="2">����</option>
           		            <option value="3">����</option>
						</select>
					</td>
					<script>
						frm_query.type.value = "<c:out value='${oldParams["type[=]"]}'/>"
					</script>
					<td align="left">
						<button type="button" class="smlbtn" onclick="doQuery();">��ѯ</button>&nbsp;
						<button type="button" class="smlbtn" onclick="resetForm();">����</button>&nbsp;
					</td>
				</tr>
				<tr height="35">
					<td align="right">��ϵ��&nbsp;</td>
					<td align="left">
						<input id="contactMan" name="_contactMan[like]" value="<c:out value='${oldParams["contactMan[like]"]}'/>" type=text class="text" style="width: 100px">
					</td>
					<td align="right">��ϵ�绰&nbsp;</td>
					<td align="left">
						<input id="phone" name="_phone[like]" value="<c:out value='${oldParams["phone[like]"]}'/>" type=text  class="text" style="width: 100px">
					</td>
					<td align="right">�����˺�&nbsp;</td>
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
				<td class="panel_tHead_MB" abbr="contactMan">��ϵ��</td>
				<td class="panel_tHead_MB" abbr="BrokerID">����������</td>
				<td class="panel_tHead_MB" abbr="createTime">����ʱ��</td>
				<!-- <td class="panel_tHead_MB" abbr="extractValue(xmlType(extenddata),'/root/corporate/text()')">����</td>
				<td class="panel_tHead_MB" abbr="extractValue(xmlType(extenddata),'/root/code/text()')">��ҵ���</td> -->
				<td class="panel_tHead_MB" abbr="type">����</td>
				<td class="panel_tHead_MB" abbr="status">״̬</td>
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
	  			<c:if test="${result.type==1}">����</c:if>
			    <c:if test="${result.type==2}">����</c:if>
			    <c:if test="${result.type==3}">����</c:if>
	  			</td>
	  			<td class="underLine">
	  			<c:if test="${result.status=='N'}">����</c:if>
	  			<c:if test="${result.status=='D'}">��ֹ</c:if>
			    <c:if test="${result.status=='U'}">����</c:if>
			    <c:if test="${result.status=='E'}">����</c:if>
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
              <option value="">��ѡ��</option>
              <option value="U">��ֹ����</option>
              <option value="N">�ָ�����</option>
              </select>&nbsp;&nbsp;
              <button class="mdlbtn" type="button" id="clickTrue"  onclick="setStatus(frm_delete,tableList,'delCheck')">���ύ����</button>&nbsp;&nbsp;
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
//������Ϣ���ı���
        var txtInput;
        //������ǰѡ��������� 
        var currentIndex = -1;
        var requestObj;
        var fId =[];
        //��ʼ������,��������λ��
        function initPar()
        {
             txtInput = document.getElementById("firmId");
             //���������� ����� �ı�������λ�� 
             setPosition();
        } 
        
        //���������� ����� �ı�������λ��
        function setPosition()
        {
            var width = txtInput.offsetWidth;
            var left = getLength("offsetLeft");
            var top = getLength("offsetTop") + txtInput.offsetHeight;
			divContent.style.left = left;
			divContent.style.top = top; 
			divContent.style.width = width + "px";
        } 
        
       //��ȡ��Ӧ���Եĳ��� 
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
        //�Զ����
        function autoComplete()
        {	setPosition();
            //������� ����, ���� �� �س�
            if (event.keyCode == 38 || event.keyCode == 40 || event.keyCode == 13)
            { 
                //ѡ��ǰ�� 
                selItemByKey();
            } 
            else //���������������
            { 
                //���ֵΪ�� 
                if (txtInput.value == "")
                {
                    divContent.style.display='none'; 
                    return;
                } 
                //�ָ�����ѡ����Ϊ -1 
                currentIndex = -1; 
                //��ʼ����
				var urlStr="<%=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()%>/common/getFirmIdList.jsp?firmid="+txtInput.value+"&times="+Date();
                requestObj = CreateAjaxObject();
                requestObj.onreadystatechange = displayResult;
                requestObj.open('GET', urlStr, true);
                requestObj.send(null); 
            } 
        } 
        
        //��ʾ��� 
        function displayResult()
        {
             if (requestObj.readyState == 4)
             {
                     showData();
                     divContent.style.display = "";
             } 
        } 
        
        //��ʾ���������صĽ�� ,���γ�������
        function showData()
        {
             //��ȡ���� 
             var xmlData = requestObj.responseXML;
             //��ʾ����
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
             //��ʾת���Ľ��
             divContent.innerHTML = res;
        } 
        
        //ͨ������ѡ�������� 
        function selItemByKey()
        {
            //������ 
            var tbl = document.getElementById("tblContent"); 
            if (!tbl)
            {
                return; 
            } 
            //�����������
            var maxRow = tbl.rows.length; 
            //���� 
            if (event.keyCode == 38 && currentIndex > 0)
            {
                 currentIndex--;
            } 
            //���� 
            else if (event.keyCode == 40 && currentIndex < maxRow-1)
            {
                 currentIndex++;
            }
            //�س� 
            else if (event.keyCode == 13)
            {
                selValue();
                return;
            } 
            
            clearColor();
            txtInput.value = tbl.rows[currentIndex].innerText; 
            //���õ�ǰ�����ɫΪblue ���ѡ�� 
            tbl.rows[currentIndex].style.backgroundColor = "#ccc000"; 
        } 
        
        //���������ı�����ɫ 
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
        	//�������
        	var tbl = document.getElementById("tblContent");
			for(var i=0;i<tbl.rows.length;i++)
			{						
				tbl.deleteRow(i);
			}
        }
        //ѡ���������е�ǰ���ֵ ,���ڰ��س�����굥��ѡ�е�ǰ���ֵ
        function selValue()
        {
            if (event.keyCode != 13)
            { 
                var text = event.srcElement.innerText;
                txtInput.value = text; 
            } 
            initList(); 
        } 
        
        //�ı���ʧȥ����ʱ ����������ɼ��� 
        function setDisplay()
        {
            //��ȡ��ǰ�td�ı�� 
            if (document.activeElement.tagName == "TD")
            {
                 var tbl = document.activeElement.parentElement.parentElement.parentElement; 
                //�������������,������ ������ 
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
        		document.getElementById("clickTrue").innerHTML="���ύ����";
        	}else{
        		document.getElementById("clickTrue").innerHTML="�ָ�������";
        	}
		}
       
</script>