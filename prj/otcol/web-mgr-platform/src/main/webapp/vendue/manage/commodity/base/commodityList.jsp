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
<!--操作-->
<c:if test='${not empty param.opt}'>
 <c:set var="modifyTime" value="<%=sqlDate%>"/>
 <c:set var="ids" value=""/>
 <!--返回不能删除商品信息-->
 <c:set var="returnInfo" value=""/>
 <!--是否有不能删除的商品,标志控制-->
 <c:set var="delFlag" value="true"/>
 <!--删除时判断是否已是前交易用户的标志变量-->
 <c:set var="tradeFlag" value="false"/>
 <!--判断本次是否最少删除一个商品,才好记日志-->
 <c:set var="globalFlag" value="false"/>
 <!--判断操作成交商品的数量-->
 <c:set var="operCnt" value="0"/>
 <!--只操作单个商品记录的标的号-->
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
           <c:when test="${rowColumn.status==2||rowColumn.status==5}"><!--判断商品状态是否可以删除-->
 	 	   	 	   <c:choose>
 	 	   	 	   <c:when test="${tradeFlag=='false'}"><!--判断是否可以删除-->
 	 	   	 	     <db:delCommodity id="${row}"/>
                     <c:set var="operCnt" value="${operCnt+1}"/>
					 <c:set var="operCode" value="${rowColumn.code}"/>
					 <c:set var="globalFlag" value="true"/>
 	 	   	 	   </c:when>
 	 	   	 	   <c:otherwise>
 	 	   	 	   	<c:set var="returnInfo" value="${returnInfo}${rowColumn.code}:商品正在交易,不能删除!\\r\\n"/>
 	 	   	 	   	<c:set var="tradeFlag" value="false"/>
 	 	   	 	   	<c:set var="delFlag" value="false"/><!--有些商品没有删除，置标志控制-->
 	 	   	 	   </c:otherwise>
 	 	   	 	   </c:choose>	 	
           </c:when>
           <c:otherwise>
 	 	   	 	   <c:set var="delFlag" value="false"/><!--有些商品没有删除，置标志控制-->
				   <c:choose>
				       <c:when test="${rowColumn.status==3}">
					       <c:set var="returnInfo" value="${returnInfo}${rowColumn.code}:已被拆标,不能删除!\r\n"/>
					   </c:when>
					   <c:when test="${rowColumn.status==4}">
					       <c:set var="returnInfo" value="${returnInfo}${rowColumn.code}:已被合标,不能删除!\r\n"/>
					   </c:when>
					   <c:when test="${rowColumn.status==1}">
					       <c:set var="returnInfo" value="${returnInfo}${rowColumn.code}:已经成交,不能删除!\r\n"/>
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
 	  		 	  alert("商品删除成功!");
 	  		 	  //-->
 	  		 </script>
 	    </c:otherwise>	
 	  </c:choose>
	  </c:catch>
      <c:if test="${not empty exceError}">
      <%
	       //异常处理
	       exceError=pageContext.getAttribute("exceError").toString();
		   log(request,exceError);
		   hintError(out);
	  %>
      </c:if>
</c:if>
<!-废除商品-->
<c:if test="${param.opt=='nouse'}">
    <c:catch var="exceError">
	<!--判断本次是否最少废除一个商品,才好记日志-->
    <c:set var="globalFlag" value="false"/>
	<!--判断操作成交商品的数量-->
    <c:set var="operCnt" value="0"/>
    <!--只操作单个商品记录的标的号-->
    <c:set var="operCode" value=""/>
  	<c:forEach var="row" items="${paramValues.ck}">
	  <c:set var="ids" value="${ids}'${row}',"/>		
    <c:set var="ids" value="${ids}''"/>
 	  <db:select var="rowColumn" table="v_commodity" columns="<%=COLS_COMMODITY%>" where="id=${row}">
           <db:select var="curTrade" table="v_curcommodity" columns="<%=COLS_CURCOMMODITY%>" where="code='${rowColumn.code}'">
 	 	   	 	  <c:set var="tradeFlag" value="true"/>
 	 	   	 	 </db:select>
           <c:choose>
           <c:when test="${rowColumn.status==2}"><!--判断商品状态是否可以废除-->
 	 	   	 	   <c:choose>
 	 	   	 	   <c:when test="${tradeFlag=='false'}"><!--判断是否可以废除-->
 	 	   	 	     <db:update table="v_commodity"
 	 	   	 	     status="5"
 	 	   	 	     where="id=${row}"
 	 	   	 	     />
                     <c:set var="operCnt" value="${operCnt+1}"/>
					 <c:set var="operCode" value="${rowColumn.code}"/>
					 <c:set var="globalFlag" value="true"/>
 	 	   	 	   </c:when>
 	 	   	 	   <c:otherwise>
					<c:set var="returnInfo" value="${returnInfo}${rowColumn.code}:商品正在交易,不能废除!\\r\\n"/>
 	 	   	 	   	<c:set var="tradeFlag" value="false"/>
 	 	   	 	   	<c:set var="delFlag" value="false"/><!--有些商品没有删除，置标志控制-->
 	 	   	 	   </c:otherwise>
 	 	   	 	   </c:choose>	 	
           </c:when>
           <c:otherwise>
 	 	   	 	<c:set var="delFlag" value="false"/><!--有些商品没有删除，置标志控制-->
				<c:choose>
				     <c:when test="${rowColumn.status==3}">
					     <c:set var="returnInfo" value="${returnInfo}${rowColumn.code}:已被拆标,不能废除!\r\n"/>
					 </c:when>
					 <c:when test="${rowColumn.status==4}">
					     <c:set var="returnInfo" value="${returnInfo}${rowColumn.code}:已被合标,不能废除!\r\n"/>
					 </c:when>
					 <c:when test="${rowColumn.status==1}">
					     <c:set var="returnInfo" value="${returnInfo}${rowColumn.code}:已经成交,不能废除!\r\n"/>
					 </c:when>
					 <c:when test="${rowColumn.status==5}">
					     <c:set var="returnInfo" value="${returnInfo}${rowColumn.code}:已经废除！不能再次废除!\r\n"/>
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
 	  		 	  alert("商品废除成功!");
 	  		 	  //-->
 	  		 </script>
 	    </c:otherwise>	
 	  </c:choose>
	  </c:catch>
      <c:if test="${not empty exceError}">
      <%
	       //异常处理
	       exceError=pageContext.getAttribute("exceError").toString();
		   log(request,exceError);
		   hintError(out);
	   %>
       </c:if>
</c:if>
<!-恢复商品-->
<c:if test="${param.opt=='use'}">
    <c:catch var="exceError">
	<!--判断本次是否最少恢复一个商品,才好记日志-->
    <c:set var="globalFlag" value="false"/>
	<!--判断操作成交商品的数量-->
    <c:set var="operCnt" value="0"/>
    <!--只操作单个商品记录的标的号-->
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
           <c:when test="${rowColumn.status==5}"><!--判断商品状态是否可以删除-->
 	 	   	 	   <c:choose>
 	 	   	 	   <c:when test="${tradeFlag=='false'}"><!--判断是否可以删除-->
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
 	 	   	 	   	<c:set var="delFlag" value="false"/><!--有些商品没有删除，置标志控制-->
 	 	   	 	   </c:otherwise>
 	 	   	 	   </c:choose>	 	
           </c:when>
           <c:otherwise>
 	 	   	 	   <c:set var="delFlag" value="false"/><!--有些商品没有删除，置标志控制-->
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
 	  		 	  alert("${returnInfo}"+"不能恢复,商品正在交易或者商品的状态不允许恢复!");
 	  		 </script>
 	    </c:when>
 	    <c:otherwise>
 	    	 <script language="javascript">
 	    	 	  <!--
 	  		 	  alert("恢复成功!");
 	  		 	  //-->
 	  		 </script>
 	    </c:otherwise>	
 	  </c:choose>
	  </c:catch>
      <c:if test="${not empty exceError}">
      <%
	       //异常处理
	       exceError=pageContext.getAttribute("exceError").toString();
		   log(request,exceError);
		   hintError(out);
	  %>
      </c:if>
</c:if>
<!--添加到当前交易商品操作-->
<!--提示不能添加商品的信息-->
<c:set var="notAddCommodity" value=""/>
<!--提示不能添加商品的信息的标志控制变量-->
<c:set var="addFlag" value="false"/>
</c:if>

<!--变量定义-->
<c:set var="sqlFilter" value=""/>
<!--分页参数定义-->
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
<!--查询条件-->
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
   <!--判断是否是第一次点击浏览页面,如果是第一次点击浏览页面则默认为当天的时间-->
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
<!--第一次挂牌时间-->
<c:choose> 
 <c:when test="${empty param.firstTime}"> 
   <c:set var="firstTime" value=""/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="firstTime" value="${param.firstTime}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and u1.firstTime = to_date('${firstTime}','yyyy--mm-dd') "/>
 </c:otherwise> 
</c:choose>

<!--商品分类-->
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
<!--所属交易商-->
<c:choose> 
 <c:when test="${empty param.userId}"> 
   <c:set var="userId" value=""/> 
 </c:when> 
 <c:otherwise> 
   <c:set var="userId" value="${param.userId}"/> 
   <c:set var="sqlFilter" value="${sqlFilter} and u1.userId like '${userId}' "/>
 </c:otherwise> 
</c:choose>
<!--交易模式-->
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
	<title>商品信息查询</title>
</head>
<body onload="init();">
<form name=frm id=frm action="" method="post">
		<input type="hidden" id="orderField" name="orderField" value="<%=orderField %>">
		<input type="hidden" id="orderDesc" name="orderDesc" value="<%=orderType %>">
		<fieldset width="100%">
		<legend>商品信息查询</legend>
		<span>
			<table border="0" cellspacing="0" cellpadding="0" width="100%">
			  <tr height="35">
            	<td align="right"> 标的号：</td>
                <td align="left">
                	<input name="codeQ" type="text" class="text" style="width: 120px;" value="${param.codeQ}"onkeypress="notSpace()">
                </td>
                <td align="right"> 录入时间：</td>
                <td align="left">
				<c:set var="createTimeAgr" value=""/>
				<!--如果查询时录入时间为空,则默认为当天的时间-->
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
                <td align="right"> 第一次挂牌日期：</td>
                <td align="left">
                	<MEBS:calendar eltID="firstTime" eltName="firstTime" eltCSS="date" eltStyle="width:80px" eltImgPath="<%=skinPath%>/images/" eltValue="${firstTime}"/>
                </td>
                <td align="left">状态：</td>
                <td align="left">
                	<select name="statusQ">
                		<option value="" <c:if test="${empty param.statusQ}"><c:out value="selected"/></c:if>>全部</option>
                		 <option value="2" <c:if test="${param.statusQ==2}"><c:out value="selected"/></c:if>>未成交</option>
                		 <option value="1" <c:if test="${param.statusQ==1}"><c:out value="selected"/></c:if>>成交</option>
                		 <option value="5" <c:if test="${param.statusQ==5}"><c:out value="selected"/></c:if>>废除</option>
						 <option value="6" <c:if test="${param.statusQ==6}"><c:out value="selected"/></c:if>>已投标</option>
                  </select>	
                </td>
				</tr>
              <tr>
                <td align="right"> 商品分类：</td>
                <td align="left">
                	<select name="category">
						 <option value=""  <c:if test="${empty param.category}">selected</c:if>>全部</option>
                		<db:select var="row" table="v_commoditytype" columns="ID,name">
						 <option value="${row.id}" <c:if test="${param.category==row.id}">selected</c:if>>${row.name}</option>
						</db:select>
                  </select>
                </td>
				<td align="right">所属交易商：</td>
                <td align="left">
                <input name="userId" type="text" class="text" style="width: 120px;" value="${param.userId}"onkeypress="notSpace()">
                </td>
				<td align="right">交易模式：</td>
                <td align="left">
                <select name="tradeMode"> 
				<db:select var="row" table="v_syspartition" columns="partitionid,description,trademode" where="validflag=1">
				 <option value="${row.trademode}" <c:if test="${param.tradeMode==row.trademode}">selected</c:if>>${row.description}</option>
				</db:select>
				<option value=""  <c:if test="${empty param.tradeMode}">selected</c:if>>全部</option>
                </select>
                </td>
                <td align="left" colspan="2">
                <input type="button" onclick="queryBtn('commodityList.jsp');" class="btn" value="查询">&nbsp;
                <!-- add by yangpei 2011-11-22 增加重置功能 -->
            	<input type="button" onclick="resetForm();" class="btn" value="重置">&nbsp;
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
			    <td class="panel_tHead_MB" abbr="code">标的号 </td>
			    <td class="panel_tHead_MB" abbr="name">品种</td>
			    <td class="panel_tHead_MB" abbr="firsttime">第一次挂标日期 </td>
			    <td class="panel_tHead_MB" abbr="status">商品状态</td>
			    <td class="panel_tHead_MB" abbr="beginprice">起拍价</td>
				<td class="panel_tHead_MB" abbr="amount">商品数量</td>
			    <td class="panel_tHead_MB" abbr="userid">所属交易商</td>
				<td class="panel_tHead_MB" abbr="trademode">交易模式</td>
			    <td class="panel_tHead_MB" abbr="createtime">录入时间</td>
				<td class="panel_tHead_RB">&nbsp;</td>
				</tr>
			</tHead>
			<tBody>
			    <db:selectPG_Commodity var="row" orderBy="${a}" pageIndex="${pageIndex}" pageSize="${pageSize}" where="u1.id=u2.commid${sqlFilter} and u2.str5=u3.id">
				    <tr onclick="selectTr();" align=center height="25">
				    <td class="panel_tBody_LB">&nbsp;</td>
		  			<td class="underLine">
		  			<c:if test="${row.status==1}"><font color="#00ff00">●</font></c:if>
		  			<c:if test="${row.status==2}"><font color="#FFBB0D">●</font></c:if>	
		  			<c:if test="${row.status==3}"><font color="#0000ff">○</font></c:if>
		  			<c:if test="${row.status==4}"><font color="#0000ff">●</font></c:if>
		  			<c:if test="${row.status==5}"><font color="#0000ff">Ｘ</font></c:if>
					<c:if test="${row.status==6}"><font color="#00AAff">●</font></c:if>
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
					  <c:if test="${row.status==1}">成交</c:if>
					  <c:if test="${row.status==2}">未成交</c:if>
					  <c:if test="${row.status==3}">被拆标</c:if>
					  <c:if test="${row.status==4}">被合标</c:if>
					  <c:if test="${row.status==5}">废除</c:if>
					  <c:if test="${row.status==6}">已投标</c:if>
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
				  <c:if test="${row.trademode==0}">竞买</c:if>
				  <c:if test="${row.trademode==1}">竞卖</c:if>
				  <c:if test="${row.trademode==2}">招标</c:if>
				  </td>
				  <td class="underLine"><fmt:formatDate value="${row.createtime}" pattern="<%=DATEPATTERN%>"/></td>
		  		  <td class="panel_tBody_RB">&nbsp;</td>
		  		</tr>
				</db:selectPG_Commodity>
		  	</tBody>
			<!--计算总页数-->
			<db:cnt_Commodity var="c" where="u1.id=u2.commid and u2.str5=u3.id${sqlFilter} ">
			 <c:set var="totalCnt" value="${c.n}"/>
			</db:cnt_Commodity>
			<!--计算商品总数量在页面中显示-->
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

            
	      <input type="button" onclick="gotoUrl('commodityAdd.jsp')" class="btn" value="添加">&nbsp;&nbsp;
		  <input type="button" onclick="addCurCommodify(frm,tb,'ck')" class="bigbtn" value="加至当前交易商品">&nbsp;&nbsp;
  		  <input type="button" onclick="return deleteRec(frm,tb,'ck');" class="btn" value="删除">&nbsp;&nbsp;
  		  <input type="button" onclick="return deleteRecExt(frm,tb,'ck',4);" class="btn" value="废除商品">&nbsp;&nbsp;
  		  <input type="button" onclick="return deleteRecExt(frm,tb,'ck',5);" class="btn" value="恢复商品">&nbsp;&nbsp;
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
//修改商品信息
function editUserInfo(id,status){
	var result = window.showModalDialog("commodityMod.jsp?flag=query&id="+id,"", "dialogWidth=800px; dialogHeight=600px; status=yes;scroll=yes;help=no;");
	if(result){
		queryBtn('commodityList.jsp');
		}
}

//跳转页面
function pageTo(){
  if(event.keyCode==13){
     agr=document.frm.pageJumpIdx.value;
     document.frm.pageIndex.value=agr;
     alert(document.frm.pageIndex.value);
     document.frm.submit();	
   }
}
 
//添加商品

function addRec(frm_delete,tableList,checkName)
{
	if(isSelNothing(tableList,checkName) == -1)
	{
		alert ( "没有可以操作的数据！" );
		return false;
	}
	if(isSelNothing(tableList,checkName))
	{
		alert ( "请选择需要操作的数据！" );
		return false;
	}
	if(confirm("是否以流拍加入到当前交易商品列表？"))
	{ 
		if(confirm("确实要操作这些数据?")){
		  frm.opt.value="添加到当前交易商品";
		  frm.lpFlag.value="1";
	    frm.submit();
	  }else{
	    return false;	
	  }
	}
	else
	{ if(confirm("确实要操作这些数据?")){
		  frm.opt.value="添加到当前交易商品";
		  frm.lpFlag.value="0";
		  frm.submit();
	  }
	  else{
	    return false;	
	  }
	}
}

//通过对话框的方式添加商品
function addCurCommodify(frm_delete,tableList,checkName){
	if(isSelNothing(tableList,checkName) == -1)
	{
		alert ( "没有可以操作的数据！" );
		return false;
	}
	else if(isSelNothing(tableList,checkName))
	{
		alert ( "请选择需要操作的数据！" );
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