<%@ page contentType="text/html;charset=GBK" %>
<table  width="100%" border="1" cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" bordercolorlight="#000000">
	<%
    	Map msgMap = (Map)request.getAttribute("msgMap");
		List<Map> transHandleList = (List)msgMap.get("transHandleList");//
		//boolean isNote = (boolean)msgMap.get("isNote");//�Ƿ�¼�벵��ԭ��
    	if(transHandleList!=null&&transHandleList.size()>0){ 
     %>
     	<tr>
	     	<td class="td_center" width="15%">����</td>
	    	<td colspan="5">
	    	<%
			for(Map map : transHandleList){
			%>
			<input type="radio" name="isDoSome" value="<%=map.get("value") %>" onclick="writeNote('<%=map.get("isWriteNote")%>');"/>
	      	<span class="fontSize"><%=map.get("name") %></span>
		 	<%
		 	}
			%>
	      	</td>
      	</tr>
     <%
     	}
     %>
	 <tr id="isNotLook" style="display:none">
     	<td class="td_center" width="15%" height="22">��ע</td>
    	<td colspan="5">
    	<input name="note" type="text" />
      	</td>
      </tr>
      <c:if test="${operateObj.note != null}">
      	<tr id="isNotLook" >
     	<td class="td_center" width="15%" height="22">��ע</td>
    	<td colspan="5">
    	${operateObj.note}
      	</td>
      	</tr>
      </c:if>
</table>
<br>
<div align="center">
	<%
		int result = (Integer)msgMap.get("result");//ҳ���Ƿ��ж�����ֵ�����Ȩ��; 1  ��    -1 û��

		if(result==1){
 			%>
 				<button type="button" class="smlbtn" onclick="doDealWith();">�ύ</button>&nbsp;&nbsp;
 			<%
		}
	 %>
	 <button type="button" class="smlbtn" onclick="doReturn();">����</button>
</div>
<SCRIPT LANGUAGE="JavaScript">
function writeNote(str){// �Ƿ����俴�� ��д����ԭ��
	if(str=='true'){
		document.getElementById("isNotLook").style.display="";
	}else{
		document.getElementById("isNotLook").style.display="none";
	}
}
function doDealWith(){
	<%if(transHandleList!=null&&transHandleList.size()>0){ %>
	var ts =  validate();
	if(!ts){
	    alert("��ѡ��Ҫִ�еĲ��� !");
        return false;
	}
	<%}%>
	if(confirm("ȷ���ύ��")){
		frm.action="${submitAction}";
		frm.submit();
	}
}
function validate(){//��֤ radio ��name Ϊ isDoSome ���Ƿ�ѡ��һ��
	var resualt=false;
	<%if(transHandleList!=null&&transHandleList.size()>1){%>
	for(var i=0;i<document.frm.isDoSome.length;i++)
	{
	    if(document.frm.isDoSome[i].checked)
	    {
	      resualt=true;
	    }
	}
	<%}else if(transHandleList!=null&&transHandleList.size()==1){%>
		if(document.frm.isDoSome.checked)
	    {
	      resualt=true;
	    }
	<%}%>
	
	return resualt;
}
function doReturn(){
	frm.action="${returnAction}";
	frm.submit();
}
</SCRIPT>