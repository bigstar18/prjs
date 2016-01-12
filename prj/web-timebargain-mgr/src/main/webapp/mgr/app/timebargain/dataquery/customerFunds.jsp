<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
<head>
<link rel="stylesheet" href="${mgrPath}/skinstyle/default/css/app/report.css" type="text/css"/>
<style media=print>
    .Noprint{display:none;}<!--用本样式在打印时隐藏非打印项目-->
</style>
<title>资金情况表</title>
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
				
				<table align="center" height="400px" width="800px" border="0" id ="tableList">
					<tr>
						<td valign="top">
						  
						   <br><center class="reportHead">资金情况表</center><br>
                             <table align="center" class="reportTemp" width="600px">
                             
                             <c:forEach items="${result}" var="info">
                             
                             
                            
	                         <tr>
		                         <td class="td_reportMdHead">交易商代码</td>
		                         <td class="td_reportMdHead">${firmID }</td>
		                         <td class="td_reportMdHead">&nbsp;</td>
		                         <td class="td_reportMdHead">&nbsp;</td>
	                         </tr>
	                         <tr>
		                         <td class="td_reportMdHead">期初余额</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${info.LASTBALANCE }" pattern="#,##0.00" /></td>
		                         <td class="td_reportMdHead">&nbsp;</td>
		                         <td class="td_reportMdHead">&nbsp;</td>
	                         </tr>
	                         <tr>
		                         <td class="td_reportMdHead">+上日保证金</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${info.CLEARMARGIN }" pattern="#,##0.00" /></td>
		                         <td class="td_reportMdHead">-当日保证金</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${info.RUNTIMEMARGIN }" pattern="#,##0.00" /></td>
	                         </tr>
	                         <tr>
		                         <td class="td_reportMdHead">+上日浮亏</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${info.CLEARFL }" pattern="#,##0.00" /></td>
		                         <td class="td_reportMdHead">-当日浮亏</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${info.RUNTIMEFL }" pattern="#,##0.00" /></td>
	                         </tr>
	                         <tr>
	                            <c:set var="out_Amount" value="${info.INAMOUNT - info.OUTAMOUNT }"></c:set>
		                         <td class="td_reportMdHead">+出入金</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${out_Amount }" pattern="#,##0.00" /></td>
		                         <td class="td_reportMdHead">-委托冻结资金</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${info.ORDERFROZEN }" pattern="#,##0.00" /></td>
	                         </tr>
	                         <tr>
		                         <td class="td_reportMdHead">+当日转让盈亏</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${info.CLOSE_PL }" pattern="#,##0.00" /></td>
		                         <td class="td_reportMdHead">-其它冻结资金</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${info.OTHERFROZEN }" pattern="#,##0.00" /></td>
	                         </tr>
	                         <tr>
		                         <td class="td_reportMdHead">+其他变化</td>
		                         <td class="td_reportMdHead">
		                          <fmt:formatNumber value="${(info.USEFULFUND - info.LASTBALANCE) - (info.CLEARMARGIN + info.CLEARFL + out_Amount + info.CLOSE_PL - info.RUNTIMEMARGIN - info.RUNTIMEFL - info.ORDERFROZEN - info.TRADEFEE - info.OTHERFROZEN )}" pattern="#,##0.00" />
		                         </td>
		                         <td class="td_reportMdHead">-当日手续费</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${info.TRADEFEE }" pattern="#,##0.00" /></td>
	                         </tr>
	                         <tr>
		                         <td class="td_reportMdHead">当日可用资金</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${info.USEFULFUND }" pattern="#,##0.00" /></td>
		                         <td class="td_reportMdHead">质押资金</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${info.MAXOVERDRAFT }" pattern="#,##0.00" /></td>
	                         </tr>
	                         <tr>
		                         <td class="td_reportMdHead">订货盈亏</td>
		                         <td class="td_reportMdHead"><fmt:formatNumber value="${info.PL }" pattern="#,##0.00" /></td>
		                         <td class="td_reportMdHead">&nbsp;</td>
		                         <td class="td_reportMdHead">&nbsp;</td>
	                         </tr>
	                         <tr>
		                         <td class="td_reportMdHead">当前权益</td>
		                         <td class="td_reportMdHead">
		                           <fmt:formatNumber value="${info.BALANCE + info.CLEARMARGIN - info.CLEARASSURE + info.RUNTIMESETTLEMARGIN  + info.CLEARFL + info.PL + info.CLOSE_PL - info.TRADEFEE }" pattern="#,##0.00" />
		                         </td>
		                         <td class="td_reportMdHead">&nbsp;</td>
		                         <td class="td_reportMdHead">&nbsp;</td>
	                         </tr>
	                          </c:forEach>
	                       </table>
	                     
					  </td>
					 </tr>
					 
				</table>
				
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