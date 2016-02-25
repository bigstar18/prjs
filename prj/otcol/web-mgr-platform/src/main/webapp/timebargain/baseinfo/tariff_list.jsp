<%@ include file="/timebargain/common/taglibs.jsp"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Map"%>
<%@ page pageEncoding="GBK" %>

<html>
<head>
<%@ include file="/timebargain/common/ecside_head.jsp" %>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/global.js"/>"></script>
<script language="JavaScript" src="<c:url value="/timebargain/scripts/open.js"/>"></script>
<title>
default
</title>
<script type="text/javascript">
<!--
var selTariffid;
function Change(){

selTariffid=document.getElementById("selTariffID").value;
}
function add_onclick()
{
    selTariffid=document.getElementById("selTariffID").value;
    document.location.href = "<c:url value="/timebargain/baseinfo/tariff.do?funcflg=addTariff&tariffID="/>"+selTariffid;
   
}

//queryCommodity
function queryCommodity(breedID)
{	
    parent.location.href = "<c:url value="/timebargain/baseinfo/commodity.jsp?condition=and a.status <> 1&breedID="/>" + breedID;
}
//cmdtySort_onclick()
function cmdtySort_onclick()
{	
    //window.open("<c:url value="/baseinfo/cmdtySort.jsp"/>",620,600)
    pTop("<c:url value="/timebargain/baseinfo/cmdtySort.jsp"/>",450,400);
}
function reset()
{	
    document.getElementById("tariffID").value="";
    //document.getElementById("oldDate").value="0";

}
function doQuery()
{	
    var tariffID=document.getElementById("tariffID").value;
    var status=0;//document.getElementById("oldDate").value;
    document.location.href = "<c:url value="/timebargain/baseinfo/tariff.do?funcflg=getTariffListQuery&tariffID="/>"+tariffID+"&status="+status;
}
// -->
</script>
</head>
<body>
<fieldset class="pickList" >
							<legend class="common">
								<b>基本信息
								</b>
							</legend>
							<table width="100%" border="0"  cellpadding="5" cellspacing="0"
								class="common">

								<tr>
								<td align="right">套餐代码:</td><td align="left"><input id="tariffID" name="tariffID" type="text" value="${oldtariffID }" styleClass="text"/></td>
								<!-- <td align="center">套餐状态：
									<select id="oldDate" name="oldDate" style="width:80" >											    
									<option value="0" <c:if test="${oldStatus==0 }">selected</c:if>>正常套餐</option>
									<option value="1" <c:if test="${oldStatus==1 }">selected</c:if>>过期套餐</option>
							  	    </select> 
								</td>
								-->
								<td align="right"><input type="button" class="button" value="查询" onclick="doQuery()"></td> <td align="left"><input type="button" class="button" value="重置" onclick="reset()"></td>    
						        </tr></table></fieldset>  
<table width="100%">
  <tr><td>
	<ec:table items="tariffList" var="tariff" 
			action="${pageContext.request.contextPath}/timebargain/baseinfo/tariff.do?funcflg=getTariffList"	
			autoIncludeParameters="${empty param.autoInc}"
			xlsFileName="tariff.xls" 
			csvFileName="tariff.csv"
			showPrint="true" 
			listWidth="100%"
			title=""			  
			rowsDisplayed="20"
			minHeight="300"
	>
		<ec:row>
			<ec:column cell="checkbox" headerCell="checkbox" alias="itemlist" columnId="itemlist" value="${tariff.tariffID}" viewsAllowed="html" style="text-align:center;padding-left:7px;width:3%;"/>				            	
            <ec:column property="tariffid" title="套餐代码" width="20%" style="text-align:center;">
            <a href="<c:out value="${ctx}"/>/timebargain/baseinfo/tariff.do?funcflg=getCommodityTariffList&isFirm=true&tariffID=<c:out value="${tariff.tariffID}"/>" title="查看"><c:out value="${tariff.tariffID}"/></a> 
            </ec:column>
            <ec:column property="tariffname" title="套餐名称" width="20%" style="text-align:center;"/>
            <ec:column property="CreateTime" title="创建时间" width="30%" cell="date" format="datetime" style="text-align:center;"/>
            <ec:column property="CreateUser" title="创建人" width="20%" style="text-align:center;"/>

            		
		</ec:row>
		<ec:extend>
<table>			
<td><select id="selTariffID" name="selTariffID" class="normal"  onchange="Change()" style="height:40px; width: 80px">
  <c:forEach items="${allList}" var="tariff">
  <c:if test="${tariff.tariffID=='T0'}">
  <%request.setAttribute("temp1",true); %>
  </c:if>
  </c:forEach>
    <c:if test="${!temp1}">
              <option value="T0">加收0%</option>
              </c:if>
              <%boolean flag=false;
              List list=(List)request.getAttribute("allList");
              for(int i=10;i<31;i++){
      if(list!=null){
      for(int j=0;j<list.size();j++){      
      Map tar=(Map)list.get(j);
      if(("T"+i+"0").equals(tar.get("tariffID"))){
      flag=true;
      break;
      }
      else{
      flag=false;
      }
  }
}
 request.setAttribute("temp2",flag);
%>
 

  <c:if test="${!temp2}">
                  <option value="T<%=i%>0">加收<%=i%>0%</option>
                  </c:if>
<%}%></select></td>
<td><a href="#" onclick="add_onclick()"><img src="<c:url value="/timebargain/images/girdadd.gif"/>"></a></td>
			<td width="30"/><td  class="separatorTool" >&#160;</td><td width="30"/><td><a href="#" onclick="javascript:batch_do('删除','<c:url value="/timebargain/baseinfo/tariff.do?funcflg=deleteTariff"/>');"><img src="<c:url value="/timebargain/images/girddel.gif"/>"></a></td>
			</table>
		</ec:extend>		
	</ec:table>
</td></tr>
</table>		
	<!-- 编辑和过滤所使用的 通用的文本框模板 -->
	<textarea id="ecs_t_input" rows="" cols="" style="display:none">
		<input type="text" class="inputtext" value="" onblur="ECSideUtil.updateEditCell(this)" 
		 style="width:100%;" name="" />
	</textarea>	
	

<%@ include file="/timebargain/common/messages.jsp" %>
</body>

</html>
  
