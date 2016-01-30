<%@ page contentType="text/html;charset=GBK" %>
<%@ include file="common/util.jsp" %>
<html>
<head>
<style media=print>
    .Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style>
<title>金网安泰 - 成交记录表</title>
</head>
<body>
	<table align="center" width="600px" border="0">
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td>
				<table align="right" width="10%" border="0">
					<tr>
					<td align="right">
						<div align="right" id="butDivModUp" name="butDivModUp" class="Noprint">
		     		<input type="submit" onclick="javascript:window.print();" class="button" value="打印">
		     		 
						</div>
					</td>
					</tr>
			</table>
			</td>
		</tr>
		<tr>
			<td>
				<table align="center" width="600px">
					<tr>
						<td>
					  </td>
					 </tr>
				</table>
			</td>
			</tr>
		<tr>
			<td>
				<div id = ediv>
				<table align="center" height="400px" width="800px" border="0" id ="tableList">
					<tr>
						<td valign="top">
<%
	String startFirmID = request.getParameter("startFirmID");
	String endFirmID = request.getParameter("endFirmID");
	String startdate = request.getParameter("startdate");
	String enddate = request.getParameter("enddate");
	String firmcategory = request.getParameter("firmcategory");
	String brokerageID = request.getParameter("brokerageId");
	User user=(User)request.getSession().getAttribute("CurrentUser");
	String filter = " 1=1 and t.firmid in ("+ user.getSql() +") ";
	if(chcekNull(startFirmID)){
		filter += " and t.firmid>='"+startFirmID +"'";
	}
	if(chcekNull(endFirmID)){
		filter += " and t.firmid<='"+endFirmID +"'";
	}
	if(chcekNull(startdate)){
		filter += " and t.cleardate>=to_date('"+startdate +"','yyyy-MM-dd')";
	}
	if(chcekNull(enddate)){
		filter += " and t.cleardate<=to_date('"+enddate +"','yyyy-MM-dd')";
	}
	if(chcekNull(firmcategory)){
		filter += " and m.firmcategoryid = '"+firmcategory+"'";
	}
	if(user.getType().equals("0")&&chcekNull(brokerageID)&&!"".equals(brokerageID)){
		//会员添加居间条件
		filter += " and t.firmid in (select t.firmId from BR_BrokerAgeAndFirm t where t.brokerageid='"+brokerageID+"')";
	}
	//query data
	String sql = " select t.firmId,m.name from t_h_trade t,m_firm m where "+filter+" and t.firmid=m.firmid group by t.firmid,m.name order by t.firmid,m.name";
	DaoHelper dao = (DaoHelper)SysData.getBean("daoHelper");
    List list=dao.queryBySQL(sql);
    if(list.size() == 0){
    %>
    	<div align="center"><b><font size="3px">无符合条件信息。</font></b></div>
    <%
    }
    for(int a = 0 ; a < list.size() ; a ++){
    	Map firmIDMap = (Map)list.get(a);
    	String getFirmId = (String)firmIDMap.get("firmId");
    	String innerSql = " select a_tradeno,customerid,commodityid, "+
    	" (case when bs_flag=1 then '买进' else '卖出' end)||(case when ordertype=1 then '订立' else '转让' end) type, "+
		" price,quantity,to_char(tradetime,'hh24:mi:ss') tradetime, "+
		" tradefee from "+
		" (select t.a_tradeno,t.customerid,t.commodityid,t.bs_flag,t.ordertype,t.price,t.quantity,t.tradetime,t.tradefee "+
		" from t_h_trade t where t.cleardate>=to_date('"+startdate+"','yyyy-MM-dd') and t.cleardate<=to_date('"+enddate +"','yyyy-MM-dd')"+
		" and t.FirmID='"+getFirmId +"' order by t.commodityid,a_tradeno )";
    	
	   	BigDecimal sumQuantity = new BigDecimal(0);
	   	BigDecimal sumTradefee = new BigDecimal(0.0);
	   	
		%>      
	<br><center class="reportHead">成交记录表</center><br>	
	<table align="center" width="600px" border="0">
	<tr><td colspan="7"></td></tr>
	<tr>
		<td  class="reportLeft">交易商代码:&nbsp;<%=turnToStr(firmIDMap.get("firmId")) %></td>
		<td  class="reportLeft">交易商名称:&nbsp;<%=turnToStr(firmIDMap.get("name")) %></td>
		<td  class="reportRight" colspan="5">日期:&nbsp;<%=startdate %>至<%=enddate %></td>
	</tr>
	</table>
	<table align="center" class="reportTemp" width="600px" border="0">
	<tr>
	<td class="td_reportMdHead">成交序号</td>
	<td class="td_reportMdHead">二级代码</td>
	<td class="td_reportMdHead">商品代码</td>
	<td class="td_reportMdHead">交易类型</td>
	<td class="td_reportMdHead">成交价格</td>
	<td class="td_reportMdHead">数量(批)</td>
	<td class="td_reportMdHead">成交时间</td>
	<td class="td_reportRdHead">交易手续费</td>
	</tr>
		<%
    	List innerlist=dao.queryBySQL(innerSql);
    	for(int b = 0 ; b < innerlist.size() ; b ++){
    	Map innerMap = (Map)innerlist.get(b);
		%> 	
	<tr>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("a_tradeno")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("customerid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("commodityid")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("type")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum2(innerMap.get("price")) %></td>
	<td class="td_reportMd1">&nbsp;<%=turnToNum(innerMap.get("quantity")) %></td>
	<td class="td_reportMd">&nbsp;<%=turnToStr(innerMap.get("tradetime")) %></td>
	<td class="td_reportRd1">&nbsp;<%=turnToNum2(innerMap.get("tradefee")) %></td>
	</tr>
	<%
		sumQuantity = sumQuantity.add(turnToNum(innerMap.get("quantity")));
		sumTradefee = sumTradefee.add(turnToNum(innerMap.get("tradefee")));
	}
	
	if(innerlist.size()>0){
	
	%>
	<tr>
	<td class="td_reportMd"><b>合计</b></td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportMd1"><b>&nbsp;<%=sumQuantity %></b></td>
	<td class="td_reportMd">&nbsp;</td>
	<td class="td_reportRd1"><b>&nbsp;<fmt:formatNumber value="<%=sumTradefee %>" pattern="#,##0.00"/></b></td>
	</tr>
	<%
	}else{
	%>
		<tr>
			<td class="td_reportRd" colspan="8">
				无符合条件信息。
			</td>
		</tr>
	<%
	}
	%>
	</table>
<br>
<br>
<br>
<br>
<br>
<br>
	<%
	}
	%>
					  </td>
					 </tr>
					 <tr><td></td></tr>
				</table>
				</div>
		<tr>
			<td>
				<table align="right" width="10%" border="0">
						<tr>
						<td align="right">
						<div align="right" id="butDivModDown" name="butDivModDown" class="Noprint">
						     <input type="submit" onclick="javascript:window.print();" class="button" value="打印"> 
						</div>
						</td>
						</tr>
				</table>
			</td>	
		</tr>
	</table>
</body>
</html>
<SCRIPT LANGUAGE="JavaScript">
function replaceHtml(replacedStr,repStr,endStr){   
    var replacedStrF = "";   
    var replacedStrB = "";   
    var repStrIndex = replacedStr.indexOf(repStr);   
    while(repStrIndex != -1){   
        replacedStrF = replacedStr.substring(0,repStrIndex);   
        replacedStrB = replacedStr.substring(repStrIndex,replacedStr.length);   
        replacedStrB = replacedStrB.substring(replacedStrB.indexOf(endStr)+1,replacedStrB.length);   
        replacedStr = replacedStrF + replacedStrB;   
        repStrIndex = replacedStr.indexOf(repStr);   
    }   
    return replacedStr;   
}   
//elTalbeOut 这个为导出内容的外层表格，主要是设置border之类的样式，elDiv则是整个导出的html部分   
function htmlToExcel(elTableOut,elDiv){   
    try{   
        //设置导出前的数据，为导出后返回格式而设置   
        var elDivStrBak = elDiv.innerHTML;   
        //设置table的border=1，这样到excel中就有表格线 ps:感谢双面提醒   
        elTableOut.border=1;   
        //过滤elDiv内容   
        var elDivStr = elDiv.innerHTML;   
        elDivStr = replaceHtml(elDivStr,"<A",">");   
        elDivStr = replaceHtml(elDivStr,"</A",">");   
        elDiv.innerHTML=elDivStr;      
           
        var oRangeRef = document.body.createTextRange();   
        oRangeRef.moveToElementText( elDiv );   
        oRangeRef.execCommand("Copy");   
           
        //返回格式变换以前的内容   
        elDiv.innerHTML = elDivStrBak;   
        //内容数据可能很大，所以赋空   
        elDivStrBak = "";   
        elDivStr = "";   
           
        var oXL = new ActiveXObject("Excel.Application")   
        var oWB = oXL.Workbooks.Add ;   
        var oSheet = oWB.ActiveSheet ;   
        oSheet.Paste();   
        oSheet.Cells.NumberFormatLocal = "@";   
        oSheet.Columns("D:D").Select   
        oXL.Selection.ColumnWidth = 20  
        oXL.Visible = true;        
        oSheet = null;   
        oWB = null;   
        appExcel = null;   
    }catch(e){   
        alert(e.description)   
    }   
}
function method1(tableid) {//整个表格拷贝到EXCEL中 
    var curTbl = document.getElementById(tableid); 
    var oXL = new ActiveXObject("Excel.Application"); 
    //创建AX对象excel 
    var oWB = oXL.Workbooks.Add(); 
    //获取workbook对象 
        var oSheet = oWB.ActiveSheet; 
    //激活当前sheet 
    var sel = document.body.createTextRange(); 
    sel.moveToElementText(curTbl); 
    //把表格中的内容移到TextRange中 
    sel.select(); 
    //全选TextRange中内容 
    sel.execCommand("Copy"); 
    //复制TextRange中内容  
    oSheet.Paste(); 
    //粘贴到活动的EXCEL中       
    oXL.Visible = true; 
    //设置excel可见属性 
} 
</SCRIPT>