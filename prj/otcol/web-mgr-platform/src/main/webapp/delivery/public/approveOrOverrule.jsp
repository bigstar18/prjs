<%@ page contentType="text/html;charset=GBK" %>
<table  width="100%" border="1" cellspacing="0" cellpadding="0" bordercolordark="#FFFFFF" bordercolorlight="#000000">
	<%
    	Map msgMap = (Map)request.getAttribute("msgMap");
		List<Map> transHandleList = (List)msgMap.get("transHandleList");//
		//boolean isNote = (boolean)msgMap.get("isNote");//是否录入驳回原因
    	if(transHandleList!=null&&transHandleList.size()>0){ 
     %>
     	<tr>
	     	<td class="td_center" width="15%">操作</td>
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
     	<td class="td_center" width="15%" height="22">备注</td>
    	<td colspan="5">
    	<input name="note" type="text" />
      	</td>
      </tr>
      <c:if test="${operateObj.note != null}">
      	<tr id="isNotLook" >
     	<td class="td_center" width="15%" height="22">备注</td>
    	<td colspan="5">
    	${operateObj.note}
      	</td>
      	</tr>
      </c:if>
</table>
<br>
<div align="center">
	<%
		int result = (Integer)msgMap.get("result");//页面是否有对这个仓单操作权限; 1  有    -1 没有

		if(result==1){
 			%>
 				<button type="button" class="smlbtn" onclick="doDealWith();">提交</button>&nbsp;&nbsp;
 			<%
		}
	 %>
	 <button type="button" class="smlbtn" onclick="doReturn();">返回</button>
</div>
<SCRIPT LANGUAGE="JavaScript">
function writeNote(str){// 是否让其看到 填写驳回原因
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
	    alert("请选择要执行的操作 !");
        return false;
	}
	<%}%>
	if(confirm("确认提交吗？")){
		frm.action="${submitAction}";
		frm.submit();
	}
}
function validate(){//验证 radio 的name 为 isDoSome 的是否选中一个
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