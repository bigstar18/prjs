<%@ page contentType="text/html;charset=GBK" %>
<html xmlns:MEBS>
<%java.sql.Date sqlDate=new java.sql.Date(System.currentTimeMillis());%>
<%@ include file="../../globalDef.jsp"%>
<%
String orderField = request.getParameter("orderField");
String orderType= request.getParameter("orderDesc");
if (orderField == null || orderField.equals("")) {
	orderField = "code";
}
if (orderType == null || orderType.equals("")) {
	orderType = "false";
}
String order = "";
if (orderType.equals("false")) {
	order = "asc";
}
if (orderType.equals("true")){
	order = "desc";
}
String a = orderField+" "+order;
request.setAttribute("a", a);
%>
<%
try
{
String exceError="";
%>
<!--����-->
<c:if test='${not empty param.opt}'>
 <c:set var="modifyTime" value="<%=sqlDate%>"/>
 <c:set var="ids" value=""/>
 <!--���ز���ɾ����Ʒ��Ϣ-->
 <c:set var="returnInfo" value=""/>
 <!--�Ƿ��в���ɾ������Ʒ,��־����-->
 <c:set var="delFlag" value="true"/>
 <!--ɾ��ʱ�ж��Ƿ�����ǰ�����û��ı�־����-->
 <c:set var="tradeFlag" value="false"/>
 <!--�жϱ����Ƿ�����ɾ��һ����Ʒ,�źü���־-->
 <c:set var="globalFlag" value="false"/>
 <!--�жϲ����ɽ���Ʒ������-->
 <c:set var="operCnt" value="0"/>
 <!--ֻ����������Ʒ��¼�ı�ĺ�-->
 <c:set var="operCode" value=""/>
  <c:if test="${param.opt=='delete'}">
    <c:catch var="exceError">
  	<c:forEach var="row" items="${paramValues.ck}">
	  <c:set var="ids" value="${ids}'${row}',"/>		
    <c:set var="ids" value="${ids}''"/>
 	  <db:select var="rowColumn" table="v_commodity" columns="<%=COLS_COMMODITY%>" where="id=${row}">
           <db:select var="curTrade" table="v_curcommodity" columns="<%=COLS_CURCOMMODITY%>" where="code='${rowColumn.code}'">
 	 	   	 	  <c:set var="tradeFlag" value="true"/>
 	 	   	</db:select>
           <c:choose>
           <c:when test="${rowColumn.status==2||rowColumn.status==5}"><!--�ж���Ʒ״̬�Ƿ����ɾ��-->
 	 	   	 	   <c:choose>
 	 	   	 	   <c:when test="${tradeFlag=='false'}"><!--�ж��Ƿ����ɾ��-->
 	 	   	 	     <db:delCommodity id="${row}"/>
                     <c:set var="operCnt" value="${operCnt+1}"/>
					 <c:set var="operCode" value="${rowColumn.code}"/>
					 <c:set var="globalFlag" value="true"/>
 	 	   	 	   </c:when>
 	 	   	 	   <c:otherwise>
 	 	   	 	   	<c:set var="returnInfo" value="${returnInfo}${rowColumn.code}:��Ʒ���ڽ���,����ɾ��!\\r\\n"/>
 	 	   	 	   	<c:set var="tradeFlag" value="false"/>
 	 	   	 	   	<c:set var="delFlag" value="false"/><!--��Щ��Ʒû��ɾ�����ñ�־����-->
 	 	   	 	   </c:otherwise>
 	 	   	 	   </c:choose>	 	
           </c:when>
           <c:otherwise>
 	 	   	 	   <c:set var="delFlag" value="false"/><!--��Щ��Ʒû��ɾ�����ñ�־����-->
				   <c:choose>
				       <c:when test="${rowColumn.status==3}">
					       <c:set var="returnInfo" value="${returnInfo}${rowColumn.code}:�ѱ����,����ɾ��!\r\n"/>
					   </c:when>
					   <c:when test="${rowColumn.status==4}">
					       <c:set var="returnInfo" value="${returnInfo}${rowColumn.code}:�ѱ��ϱ�,����ɾ��!\r\n"/>
					   </c:when>
					   <c:when test="${rowColumn.status==1}">
					       <c:set var="returnInfo" value="${returnInfo}${rowColumn.code}:�Ѿ��ɽ�,����ɾ��!\r\n"/>
					   </c:when>
					   <c:otherwise>
					   </c:otherwise>
				   </c:choose>
 	 	   </c:otherwise>	
          </c:choose>
 	  </db:select>
 	  </c:forEach>
	  <c:if test="${globalFlag=='true'}">
	  
	  </c:if>
 	  <c:choose>
 	  	<c:when test="${delFlag=='false'}">
 	  		 <script language="javascript">
 	  		 	  alert("${returnInfo}");
 	  		 </script>
 	    </c:when>
 	    <c:otherwise>
 	    	 <script language="javascript">
 	    	 	  <!--
 	  		 	  alert("��Ʒɾ���ɹ�!");
 	  		 	  //-->
 	  		 </script>
 	    </c:otherwise>	
 	  </c:choose>
	  </c:catch>
      <c:if test="${not empty exceError}">
      <%
	       //�쳣����
	       exceError=pageContext.getAttribute("exceError").toString();
		   log(request,exceError);
		   hintError(out);
	  %>
      </c:if>
</c:if>
<!-�ϳ���Ʒ-->
<c:if test="${param.opt=='nouse'}">
    <c:catch var="exceError">
	<!--�жϱ����Ƿ����ٷϳ�һ����Ʒ,�źü���־-->
    <c:set var="globalFlag" value="false"/>
	<!--�жϲ����ɽ���Ʒ������-->
    <c:set var="operCnt" value="0"/>
    <!--ֻ����������Ʒ��¼�ı�ĺ�-->
    <c:set var="operCode" value=""/>
  	<c:forEach var="row" items="${paramValues.ck}">
	  <c:set var="ids" value="${ids}'${row}',"/>		
    <c:set var="ids" value="${ids}''"/>
 	  <db:select var="rowColumn" table="v_commodity" columns="<%=COLS_COMMODITY%>" where="id=${row}">
           <db:select var="curTrade" table="v_curcommodity" columns="<%=COLS_CURCOMMODITY%>" where="code='${rowColumn.code}'">
 	 	   	 	  <c:set var="tradeFlag" value="true"/>
 	 	   	 	 </db:select>
           <c:choose>
           <c:when test="${rowColumn.status==2}"><!--�ж���Ʒ״̬�Ƿ���Էϳ�-->
 	 	   	 	   <c:choose>
 	 	   	 	   <c:when test="${tradeFlag=='false'}"><!--�ж��Ƿ���Էϳ�-->
 	 	   	 	     <db:update table="v_commodity"
 	 	   	 	     status="5"
 	 	   	 	     where="id=${row}"
 	 	   	 	     />
                     <c:set var="operCnt" value="${operCnt+1}"/>
					 <c:set var="operCode" value="${rowColumn.code}"/>
					 <c:set var="globalFlag" value="true"/>
 	 	   	 	   </c:when>
 	 	   	 	   <c:otherwise>
					<c:set var="returnInfo" value="${returnInfo}${rowColumn.code}:��Ʒ���ڽ���,���ܷϳ�!\\r\\n"/>
 	 	   	 	   	<c:set var="tradeFlag" value="false"/>
 	 	   	 	   	<c:set var="delFlag" value="false"/><!--��Щ��Ʒû��ɾ�����ñ�־����-->
 	 	   	 	   </c:otherwise>
 	 	   	 	   </c:choose>	 	
           </c:when>
           <c:otherwise>
 	 	   	 	<c:set var="delFlag" value="false"/><!--��Щ��Ʒû��ɾ�����ñ�־����-->
				<c:choose>
				     <c:when test="${rowColumn.status==3}">
					     <c:set var="returnInfo" value="${returnInfo}${rowColumn.code}:�ѱ����,���ܷϳ�!\r\n"/>
					 </c:when>
					 <c:when test="${rowColumn.status==4}">
					     <c:set var="returnInfo" value="${returnInfo}${rowColumn.code}:�ѱ��ϱ�,���ܷϳ�!\r\n"/>
					 </c:when>
					 <c:when test="${rowColumn.status==1}">
					     <c:set var="returnInfo" value="${returnInfo}${rowColumn.code}:�Ѿ��ɽ�,���ܷϳ�!\r\n"/>
					 </c:when>
					 <c:when test="${rowColumn.status==5}">
					     <c:set var="returnInfo" value="${returnInfo}${rowColumn.code}:�Ѿ��ϳ��������ٴηϳ�!\r\n"/>
					 </c:when>
					 <c:otherwise>
					 </c:otherwise>
				</c:choose>
 	 	   </c:otherwise>	
          </c:choose>
 	  </db:select>
 	  </c:forEach>
	  <c:if test="${globalFlag=='true'}">
	  
	  </c:if>
 	  <c:choose>
 	  	<c:when test="${delFlag=='false'}">
 	  		 <script language="javascript">
 	  		 	  alert("${returnInfo}");
 	  		 </script>
 	    </c:when>
 	    <c:otherwise>
 	    	 <script language="javascript">
 	    	 	  <!--
 	  		 	  alert("��Ʒ�ϳ��ɹ�!");
 	  		 	  //-->
 	  		 </script>
 	    </c:otherwise>	
 	  </c:choose>
	  </c:catch>
      <c:if test="${not empty exceError}">
      <%
	       //�쳣����
	       exceError=pageContext.getAttribute("exceError").toString();
		   log(request,exceError);
		   hintError(out);
	   %>
       </c:if>
</c:if>
<!-�ָ���Ʒ-->
<c:if test="${param.opt=='use'}">
    <c:catch var="exceError">
	<!--�жϱ����Ƿ����ٻָ�һ����Ʒ,�źü���־-->
    <c:set var="globalFlag" value="false"/>
	<!--�жϲ����ɽ���Ʒ������-->
    <c:set var="operCnt" value="0"/>
    <!--ֻ����������Ʒ��¼�ı�ĺ�-->
    <c:set var="operCode" value=""/>
  	<c:forEach var="row" items="${paramValues.ck}">
  	  <c:set var="tradeFlag" value="false"/>
	  <c:set var="ids" value="${ids}'${row}',"/>
    <c:set var="ids" value="${ids}''"/>
 	  <db:select var="rowColumn" table="v_commodity" columns="<%=COLS_COMMODITY%>" where="id=${row}">
           <db:select var="curTrade" table="v_curcommodity" columns="<%=COLS_CURCOMMODITY%>" where="code='${rowColumn.code}'">
 	 	   	 	  <c:set var="tradeFlag" value="true"/>
 	 	   </db:select>
           <c:choose>
           <c:when test="${rowColumn.status==5}"><!--�ж���Ʒ״̬�Ƿ����ɾ��-->
 	 	   	 	   <c:choose>
 	 	   	 	   <c:when test="${tradeFlag=='false'}"><!--�ж��Ƿ����ɾ��-->
 	 	   	 	     <db:update table="v_commodity"
 	 	   	 	     status="2"
 	 	   	 	     where="id=${row}"
 	 	   	 	     />
                     <c:set var="operCnt" value="${operCnt+1}"/>
					 <c:set var="operCode" value="${rowColumn.code}"/>
					 <c:set var="globalFlag" value="true"/>
 	 	   	 	   </c:when>
 	 	   	 	   <c:otherwise>
 	 	   	 	   	<c:set var="returnInfo" value="${returnInfo}${rowColumn.code},"/>
 	 	   	 	   	<c:set var="tradeFlag" value="false"/>
 	 	   	 	   	<c:set var="delFlag" value="false"/><!--��Щ��Ʒû��ɾ�����ñ�־����-->
 	 	   	 	   </c:otherwise>
 	 	   	 	   </c:choose>	 	
           </c:when>
           <c:otherwise>
 	 	   	 	   <c:set var="delFlag" value="false"/><!--��Щ��Ʒû��ɾ�����ñ�־����-->
 	 	   	     <c:set var="returnInfo" value="${returnInfo}${rowColumn.code},"/>
 	 	   	   </c:otherwise>	
          </c:choose>
 	  </db:select>
 	  </c:forEach>
	  <c:if test="${globalFlag=='true'}">
	  
	  </c:if>
 	  <c:choose>
 	  	<c:when test="${delFlag=='false'}">
 	  		 <script language="javascript">
 	  		 	  alert("${returnInfo}"+"���ָܻ�,��Ʒ���ڽ��׻�����Ʒ��״̬������ָ�!");
 	  		 </script>
 	    </c:when>
 	    <c:otherwise>
 	    	 <script language="javascript">
 	    	 	  <!--
 	  		 	  alert("�ָ��ɹ�!");
 	  		 	  //-->
 	  		 </script>
 	    </c:otherwise>	
 	  </c:choose>
	  </c:catch>
      <c:if test="${not empty exceError}">
      <%
	       //�쳣����
	       exceError=pageContext.getAttribute("exceError").toString();
		   log(request,exceError);
		   hintError(out);
	  %>
      </c:if>
</c:if>
<!--��ӵ���ǰ������Ʒ����-->
<!--��ʾ���������Ʒ����Ϣ-->
<c:set var="notAddCommodity" value=""/>
<!--��ʾ���������Ʒ����Ϣ�ı�־���Ʊ���-->
<c:set var="addFlag" value="false"/>
</c:if>

<!--��������-->
<c:set var="sqlFilter" value=""/>
<!--��ҳ��������-->
<c:choose> 
 <c:when test="${empty param.pageIndex}"> 
   <c:set var="pageIndex" value="1"/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="pageIndex" value="${param.pageIndex}"/> 
 </c:otherwise> 
</c:choose>
<c:choose>
  <c:when test="${empty param.pageSize}">
  	 <c:set var="pageSize" value="${PAGESIZE}"/>
  </c:when>
  <c:otherwise>
  	 <c:set var="pageSize" value="${param.pageSize}"/>
  </c:otherwise>
</c:choose>
<!--��ѯ����-->
<c:choose> 
 <c:when test="${empty param.codeQ}"> 
   <c:set var="code" value=""/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="code" value="${param.codeQ}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and u1.code like '${code}' "/>
 </c:otherwise> 
</c:choose>
<c:choose> 
 <c:when test="${empty param.createTime}"> 
   <!--�ж��Ƿ��ǵ�һ�ε�����ҳ��,����ǵ�һ�ε�����ҳ����Ĭ��Ϊ�����ʱ��-->
   <c:choose> 
      <c:when test="${not empty param.funcflg}">
        <c:set var="createTime" value="<%=sqlDate.toString()%>"/> 
        <c:set var="sqlFilter" value="${sqlFilter} and u1.createtime = to_date('${createTime}','yyyy--mm-dd') "/>
	  </c:when>
      <c:otherwise>
         <c:set var="createTime" value=""/> 
      </c:otherwise> 
    </c:choose>
 </c:when> 
 <c:otherwise> 
   <c:set var="createTime" value="${param.createTime}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and u1.createtime = to_date('${createTime}','yyyy--mm-dd') "/>
 </c:otherwise> 
</c:choose>
<!--��һ�ι���ʱ��-->
<c:choose> 
 <c:when test="${empty param.firstTime}"> 
   <c:set var="firstTime" value=""/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="firstTime" value="${param.firstTime}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and u1.firstTime = to_date('${firstTime}','yyyy--mm-dd') "/>
 </c:otherwise> 
</c:choose>

<!--��Ʒ����-->
<c:choose> 
 <c:when test="${empty param.category}"> 
   <c:set var="category" value=""/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="category" value="${param.category}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and u2.str5 = ${category} "/>
 </c:otherwise> 
</c:choose>
<c:choose> 
 <c:when test="${empty param.statusQ}"> 
   <c:set var="status" value=""/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="status" value="${param.statusQ}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and u1.status =${status} "/>
 </c:otherwise> 
</c:choose>
<!--����������-->
<c:choose> 
 <c:when test="${empty param.userId}"> 
   <c:set var="userId" value=""/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="userId" value="${param.userId}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and u1.userId like '${userId}' "/>
 </c:otherwise> 
</c:choose>
<!--����ģʽ-->
<c:choose> 
 <c:when test="${empty param.tradeMode}"> 
   <c:set var="tradeMode" value=""/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="tradeMode" value="${param.tradeMode}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and u1.trademode=${tradeMode} "/>
 </c:otherwise> 
</c:choose>
  <head>
	<title>��Ʒ��Ϣ��ѯ</title>
</head>
<body onload="init();">
<form name=frm id=frm action="" method="post">
		<input type="hidden" id="orderField" name="orderField" value="<%=orderField %>">
		<input type="hidden" id="orderDesc" name="orderDesc" value="<%=orderType %>">
		<fieldset width="100%">
		<legend>��Ʒ��Ϣ��ѯ</legend>
		<span>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
            	<td align="right"> ��ĺţ�</td>
                <td align="left">
                	<input name="codeQ" type="text" class="text" style="width: 120px;" value="${param.codeQ}"onkeypress="notSpace()">
                </td>
                <td align="right"> ¼��ʱ�䣺</td>
                <td align="left">
				<c:set var="createTimeAgr" value=""/>
				<!--�����ѯʱ¼��ʱ��Ϊ��,��Ĭ��Ϊ�����ʱ��-->
				<c:choose>
					<c:when test="${not empty param.funcflg}">
					<c:set var="createTimeAgr" value="<%=sqlDate.toString()%>"/>
					</c:when>
					<c:otherwise>
					<c:set var="createTimeAgr" value="${param.createTime}"/>
					</c:otherwise>
				</c:choose>
                	<MEBS:calendar eltID="createTime" eltName="createTime" eltCSS="date" eltStyle="width:80px;" eltImgPath="<%=skinPath%>/images/" eltValue="${createTimeAgr}"/>
                </td>
                <td align="right"> ��һ�ι������ڣ�</td>
                <td align="left">
                	<MEBS:calendar eltID="firstTime" eltName="firstTime" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="${firstTime}"/>
                </td>
                <td align="left">״̬��</td>
                <td align="left">
                	<select name="statusQ">
                		<option value="" <c:if test="${empty param.statusQ}"><c:out value="selected"/></c:if>>ȫ��</option>
                		 <option value="2" <c:if test="${param.statusQ==2}"><c:out value="selected"/></c:if>>δ�ɽ�</option>
                		 <option value="1" <c:if test="${param.statusQ==1}"><c:out value="selected"/></c:if>>�ɽ�</option>
                		 <option value="5" <c:if test="${param.statusQ==5}"><c:out value="selected"/></c:if>>�ϳ�</option>
						 <option value="6" <c:if test="${param.statusQ==6}"><c:out value="selected"/></c:if>>��Ͷ��</option>
                  </select>	
                </td>
				</tr>
              <tr>
                <td align="right"> ��Ʒ���ࣺ</td>
                <td align="left">
                	<select name="category">
						 <option value=""  <c:if test="${empty param.category}">selected</c:if>>ȫ��</option>
                		<db:select var="row" table="v_commoditytype" columns="ID,name">
						 <option value="${row.id}" <c:if test="${param.category==row.id}">selected</c:if>>${row.name}</option>
						</db:select>
                  </select>
                </td>
				<td align="right">���������̣�</td>
                <td align="left">
                <input name="userId" type="text" class="text" style="width: 120px;" value="${param.userId}"onkeypress="notSpace()">
                </td>
				<td align="right">����ģʽ��</td>
                <td align="left">
                <select name="tradeMode"> 
				<db:select var="row" table="v_syspartition" columns="partitionid,description,trademode" where="validflag=1">
				 <option value="${row.trademode}" <c:if test="${param.tradeMode==row.trademode}">selected</c:if>>${row.description}</option>
				</db:select>
				<option value=""  <c:if test="${empty param.tradeMode}">selected</c:if>>ȫ��</option>
                </select>
                </td>
                <td align="left" colspan="2">
                <input type="button" onclick="queryBtn('commodityList.jsp');" class="btn" value="��ѯ">&nbsp;
                <!-- add by yangpei 2011-11-22 �������ù��� -->
            	<input type="button" onclick="resetForm();" class="btn" value="����">&nbsp;
            	<script>
            		function resetForm(){
            			frm.codeQ.value="";
            			frm.createTime.value="";
            			frm.firstTime.value="";
            			frm.statusQ.value="";
            			frm.category.value="";
            			frm.userId.value="";
            			frm.tradeMode.value="";
            		}
            	</script>
                </td>
              </tr>
        	</table>
        </span>  
		</fieldset>
		<br>
		<table id="tb" border="0" cellspacing="0" cellpadding="0" width="100%" height="300">
	  		<tHead>
	  			<tr height="25"  align=center>
	  			<td class="panel_tHead_LB">&nbsp;</td>
	  			<td class="panel_tHead_MB">&nbsp;</td>
					<td class="panel_tHead_MB" align=left><input type="checkbox" id="checkAll" onclick="selectAll(tb,'ck')"></td>
			    <td class="panel_tHead_MB" abbr="code">��ĺ� </td>
			    <td class="panel_tHead_MB" abbr="name">Ʒ��</td>
			    <td class="panel_tHead_MB" abbr="firsttime">��һ�ιұ����� </td>
			    <td class="panel_tHead_MB" abbr="status">��Ʒ״̬</td>
			    <td class="panel_tHead_MB" abbr="beginprice">���ļ�</td>
				<td class="panel_tHead_MB" abbr="amount">��Ʒ����</td>
			    <td class="panel_tHead_MB" abbr="userid">����������</td>
				<td class="panel_tHead_MB" abbr="trademode">����ģʽ</td>
			    <td class="panel_tHead_MB" abbr="createtime">¼��ʱ��</td>
				<td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody>
			    <db:selectPG_Commodity var="row" orderBy="${a}" pageIndex="${pageIndex}" pageSize="${pageSize}" where="u1.id=u2.commid${sqlFilter} and u2.str5=u3.id">
				    <tr onclick="selectTr();" align=center height="25">
				    <td class="panel_tBody_LB">&nbsp;</td>
		  			<td class="underLine">
		  			<c:if test="${row.status==1}"><font color="#00ff00">��</font></c:if>
		  			<c:if test="${row.status==2}"><font color="#FFBB0D">��</font></c:if>	
		  			<c:if test="${row.status==3}"><font color="#0000ff">��</font></c:if>
		  			<c:if test="${row.status==4}"><font color="#0000ff">��</font></c:if>
		  			<c:if test="${row.status==5}"><font color="#0000ff">��</font></c:if>
					<c:if test="${row.status==6}"><font color="#00AAff">��</font></c:if>
		  			</td>
		  			<td class="underLine" align=left><input name="ck" type="checkbox" value='${row.id}'></td>
		  			<td class="underLine">
		  			<a href="javascript:editUserInfo('${row.id}','${row.status}');" class="normal">${row.code}</a>
		  			<input type="hidden" name="code" value="${row.code}">
		  			</td>
		  			<td class="underLine">
					<c:choose>
		  			<c:when test="${not empty row.str1}">
		  				${row.name}
		  			</c:when>
		  			<c:otherwise>
		  				 &nbsp;
		  			</c:otherwise>
		  			</c:choose>
		  			<c:set var="des" value=""/>
		  			</td>
		  			<td class="underLine">
		  			<fmt:formatDate value="${row.firsttime}" pattern="<%=DATEPATTERN%>"/>
		  			</td>
					  <td class="underLine">
					  <input type="hidden" name="status" value="${row.status}">
					  <c:if test="${row.status==1}">�ɽ�</c:if>
					  <c:if test="${row.status==2}">δ�ɽ�</c:if>
					  <c:if test="${row.status==3}">�����</c:if>
					  <c:if test="${row.status==4}">���ϱ�</c:if>
					  <c:if test="${row.status==5}">�ϳ�</c:if>
					  <c:if test="${row.status==6}">��Ͷ��</c:if>
					  </td>
					  <td class="underLine" style="text-align:right;">
					  <c:choose>
		  		  <c:when test="${not empty row.beginprice}">
		  		  <fmt:formatNumber value="${row.beginprice}" pattern="<%=FUNDPATTERN%>"/>
		  		  </c:when>
		  		  <c:otherwise>
		  		  &nbsp;
		  		  </c:otherwise>
		  		  </c:choose>	
				  </td>
				  <td class="underLine">
				  <c:choose>
		  		  <c:when test="${not empty row.amount}">
				  ${row.amount}
		  		  </c:when>
		  		  <c:otherwise>
		  		  &nbsp;
		  		  </c:otherwise>
		  		  </c:choose>	
				  </td>
				  <td class="underLine">
				  ${row.userid}
				  </td>
				  <td class="underLine">
				  <c:if test="${row.trademode==0}">����</c:if>
				  <c:if test="${row.trademode==1}">����</c:if>
				  <c:if test="${row.trademode==2}">�б�</c:if>
				  </td>
				  <td class="underLine"><fmt:formatDate value="${row.createtime}" pattern="<%=DATEPATTERN%>"/></td>
		  		  <td class="panel_tBody_RB">&nbsp;</td>
		  		</tr>
				</db:selectPG_Commodity>
		  	</tBody>
			<!--������ҳ��-->
			<db:cnt_Commodity var="c" where="u1.id=u2.commid and u2.str5=u3.id${sqlFilter} ">
			 <c:set var="totalCnt" value="${c.n}"/>
			</db:cnt_Commodity>
			<!--������Ʒ��������ҳ������ʾ-->
			<c:set var="amountTotal" value="0"/>
			<db:sum_CommAmout var="s" where="u1.id=u2.commid and u2.str5=u3.id${sqlFilter} ">
               <c:set var="amountTotal" value="${s.n}"/>
			</db:sum_CommAmout>
			<c:choose> 
			 <c:when test="${totalCnt%pageSize==0}"> 
			   <c:set var="pageCnt" value="${totalCnt/pageSize}"/> 
			 </c:when> 
			 <c:otherwise>
			   <c:choose> 
				 <c:when test="${(totalCnt%pageSize)*10>=5*pageSize}"> 
				   <c:set var="pageCnt" value="${totalCnt/pageSize}"/> 
				 </c:when> 
				 <c:otherwise>
				   <c:set var="pageCnt" value="${totalCnt/pageSize+1}"/>
				 </c:otherwise> 
				</c:choose>
			 </c:otherwise> 
			</c:choose>
			<jsp:include page="../../public/pageTurnComm.jsp">
				<jsp:param name="colspan" value="11"/>
				<jsp:param name="pageIndex" value="${pageIndex}"/>
				<jsp:param name="totalCnt" value="${totalCnt}"/>
				<jsp:param name="pageCnt" value="${pageCnt}"/>
				<jsp:param name="pageSize" value="${pageSize}"/>
				<jsp:param name="amountTotal" value="${amountTotal}"/>
			</jsp:include>
		</table>
     <table border="0" cellspacing="0" cellpadding="0" width="100%">
          <tr height="35">
          	<td width="40%">
          	<div align="center">

            
	      <input type="button" onclick="gotoUrl('commodityAdd.jsp')" class="btn" value="���">&nbsp;&nbsp;
		  <input type="button" onclick="addCurCommodify(frm,tb,'ck')" class="bigbtn" value="������ǰ������Ʒ">&nbsp;&nbsp;
  		  <input type="button" onclick="return deleteRec(frm,tb,'ck');" class="btn" value="ɾ��">&nbsp;&nbsp;
  		  <input type="button" onclick="return deleteRecExt(frm,tb,'ck',4);" class="btn" value="�ϳ���Ʒ">&nbsp;&nbsp;
  		  <input type="button" onclick="return deleteRecExt(frm,tb,'ck',5);" class="btn" value="�ָ���Ʒ">&nbsp;&nbsp;
  		  </div>
        <input type="hidden" name="opt">
        <input type="hidden" name="lpFlag">
            </td>
          </tr>
     </table>
</form>
</body>
</html>
<%@ include file="../../public/pageTurn2.jsp"%>
<SCRIPT LANGUAGE="JavaScript">
<!--
function winOpen(url)
{
	window.showModalDialog(url,"", "dialogWidth=420px; dialogHeight=280px; status=yes;scroll=yes;help=no;"); 
}
function winOpen1(url)
{
	window.showModalDialog(url,"", "dialogWidth=420px; dialogHeight=400px; status=yes;scroll=yes;help=no;"); 
}
//-->
//�޸���Ʒ��Ϣ
function editUserInfo(id,status){
	var result = window.showModalDialog("commodityMod.jsp?flag=query&id="+id,"", "dialogWidth=800px; dialogHeight=600px; status=yes;scroll=yes;help=no;");
	if(result){
		queryBtn('commodityList.jsp');
		}
}

//��תҳ��
function pageTo(){
  if(event.keyCode==13){
     agr=document.frm.pageJumpIdx.value;
     document.frm.pageIndex.value=agr;
     alert(document.frm.pageIndex.value);
     document.frm.submit();	
   }
}
 
//�����Ʒ

function addRec(frm_delete,tableList,checkName)
{
	if(isSelNothing(tableList,checkName) == -1)
	{
		alert ( "û�п��Բ��������ݣ�" );
		return false;
	}
	if(isSelNothing(tableList,checkName))
	{
		alert ( "��ѡ����Ҫ���������ݣ�" );
		return false;
	}
	if(confirm("�Ƿ������ļ��뵽��ǰ������Ʒ�б�"))
	{ 
		if(confirm("ȷʵҪ������Щ����?")){
		  frm.opt.value="��ӵ���ǰ������Ʒ";
		  frm.lpFlag.value="1";
	    frm.submit();
	  }else{
	    return false;	
	  }
	}
	else
	{ if(confirm("ȷʵҪ������Щ����?")){
		  frm.opt.value="��ӵ���ǰ������Ʒ";
		  frm.lpFlag.value="0";
		  frm.submit();
	  }
	  else{
	    return false;	
	  }
	}
}

//ͨ���Ի���ķ�ʽ�����Ʒ
function addCurCommodify(frm_delete,tableList,checkName){
	if(isSelNothing(tableList,checkName) == -1)
	{
		alert ( "û�п��Բ��������ݣ�" );
		return false;
	}
	else if(isSelNothing(tableList,checkName))
	{
		alert ( "��ѡ����Ҫ���������ݣ�" );
		return false;
	}else{
		var ids=getValues(tableList,checkName);
		var a=window.showModalDialog("addCurCommodity.jsp?ids="+ids+"","", "dialogWidth=420px; dialogHeight=400px; status=yes;scroll=yes;help=no;");
	    if(a)
      window.location="commodityList.jsp";
	    frm.submit();
	}
}

</SCRIPT>
<%
}
catch(Exception e)
{
	System.out.println(e.toString());
}
%>