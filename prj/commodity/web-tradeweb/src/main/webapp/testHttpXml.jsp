<%@ include file="/common/taglibs.jsp"%>
<%@ page pageEncoding="GBK" %>

<html>
<head>
<title>测试查询</title>

<script language="JavaScript" src="<c:url value="/scripts/global.js"/>"></script>
<LINK REL="stylesheet" type="text/css" href="<c:url value="/styles/common.css"/>"/>
<script type="text/javascript"> 
var xmlHttp;
var url;
var name;
function window_onload()
{
	url = "<c:url value="/testHttpXmlServlet"/>";
}
function createXMLHttpRequest() {
	if (window.XMLHttpRequest) {
		xmlHttp = new XMLHttpRequest();
	} else {
		if (window.ActiveXObject) {
			xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
	}
}
function sendXml(xml) {
	createXMLHttpRequest();
	xmlHttp.onreadystatechange = handleStateChange;
	xmlHttp.open("POST", url);
	xmlHttp.setRequestHeader("Content-Type", "text/xml");
	xmlHttp.send(xml);
}
function handleStateChange() {
    if(xmlHttp.readyState == 4) {
        if(xmlHttp.status == 200) {  
        	document.getElementById("response").innerHTML = xmlHttp.responseText;  
			alert("切换XML");
            var xmlDoc = xmlHttp.responseXML; 
            var out = "";
            var retCode = xmlDoc.getElementsByTagName("RETCODE");
           	out = out + "<br/>&ltRETCODE&gt" + retCode[0].childNodes[0].nodeValue + "&lt/RETCODE&gt";
            var message = xmlDoc.getElementsByTagName("MESSAGE");
            if(message[0].childNodes.length == 1)
            {
              out = out + "<br/>&ltMESSAGE&gt" + message[0].childNodes[0].nodeValue + "&lt/MESSAGE&gt";
            }
            var record = new DataSet(xmlDoc,"REC"); 
            var resultXml = "";
            if(name == "market_query")
            {
            	resultXml = responseMarket_query(record);
            }
            else if(name == "tradequery")
            {
            	resultXml = responseTradequery(record);
            }
            else if(name == "commodity_query")
            {
            	resultXml = responseCommodity_query(record);
            }
            else if(name == "my_order_query")
            {
            	resultXml = responseMy_order_query(record);
            }            
            else if(name == "my_weekorder_query")
            {
            	resultXml = responseMy_weekorder_query(record);
            }
            else if(name == "holding_query")
            {
            	resultXml = responseHolding_query(record);
            }
            else if(name == "commodity_data_query")
            {
            	resultXml = responseCommodity_data_query(record);
            }
            else if(name == "sys_time_query")
            {
            	resultXml = responseSys_time_query(record);
            }
            out = out + resultXml;
            var query_time = xmlDoc.getElementsByTagName("query_time");
            if(query_time[0].childNodes.length == 1)
            {
              out = out + "<br/>&lt 查询数据库时间 &gt" + query_time[0].childNodes[0].nodeValue + "&lt/ 查询数据库时间 &gt";
            }
            var parse_time = xmlDoc.getElementsByTagName("parse_time");
            if(parse_time[0].childNodes.length == 1)
            {
              out = out + "<br/>&lt 产生XML时间 &gt" + parse_time[0].childNodes[0].nodeValue + "&lt/ 产生XML时间 &gt";
            }
            var request_time = xmlDoc.getElementsByTagName("request_time");
            if(request_time[0].childNodes.length == 1)
            {
              out = out + "<br/>&lt 请求时间 &gt" + request_time[0].childNodes[0].nodeValue + "&lt/ 请求时间 &gt";
            }
            document.getElementById("response").innerHTML = out;
        }
    }
}

//-----------------start JS中封装解析xml的代码-------------------------------
//类的构造，传入xml文档和需要处理的标签名称
function DataSet(xmldoc, tagLabel) {
 this.rootObj = xmldoc.getElementsByTagName(tagLabel)
 
//3个方法
 this.getCount = getCount
 this.getData = getData
 this.getAttribute = getAttribute
} 
function getCount(){
 return this.rootObj.length
}
function getData(index, tagName){
 if (index >= this.count) return "index overflow"
 var node = this.rootObj[index]
 var str = node.getElementsByTagName(tagName)[0].firstChild.data
 return str
}

function getAttribute(index, tagName) {
 if (index >= this.count) return "index overflow"
 var node = this.rootObj[index]
 var str = node.getAttribute(tagName)
 return str
}
//--------------------end JS中封装解析xml的代码-------------------------------------------------


function commodity_query() {
	var xml = "<GNNT><REQ name=\"commodity_query\"><TRADER_ID>";
	xml += customerID.value;
	xml += "</TRADER_ID><CUSTOMER_ID></CUSTOMER_ID><COMMODITY_ID></COMMODITY_ID><SESSION_ID>1</SESSION_ID></REQ></GNNT>";
	name = "commodity_query";
	sendXml(xml);
}
function responseCommodity_query(record) {
	var xml = "<br/>&ltREC&gt" + record.getCount() + "条记录&lt/REC&gt";
    return xml;   
}

function tradequery() {
	var xml = "<GNNT><REQ name=\"tradequery\"><TRADER_ID>";
	xml += customerID.value;
	xml += "</TRADER_ID><CUSTOMER_ID></CUSTOMER_ID><MARKET_ID>";
	xml += marketID.value;
	xml += "</MARKET_ID><SESSION_ID>1</SESSION_ID></REQ></GNNT>";
    name = "tradequery";
	sendXml(xml);
}
function responseTradequery(record) {
	var xml = "<br/>&ltREC&gt" + record.getCount() + "条记录&lt/REC&gt";
    return xml;   
}

function my_order_query() {
	var xml = "<GNNT><REQ name=\"my_order_query\"><TRADER_ID>";
	xml += customerID.value;
	xml += "</TRADER_ID><TYPE></TYPE><BUY_SELL></BUY_SELL><ORDER_NO></ORDER_NO><CUSTOMER_ID></CUSTOMER_ID><MARKET_ID>";
	xml += marketID.value;
	xml += "</MARKET_ID><COMMODITY_ID></COMMODITY_ID><SESSION_ID>1</SESSION_ID></REQ></GNNT>";
	name = "my_order_query";
	sendXml(xml);
}
function responseMy_order_query(record) {
	var xml = "<br/>&ltREC&gt" + record.getCount() + "条记录&lt/REC&gt";
    return xml;   
}

function my_weekorder_query() {
	var xml = "<GNNT><REQ name=\"my_weekorder_query\"><TRADER_ID>";
	xml += customerID.value;
	xml += "</TRADER_ID><BUY_SELL></BUY_SELL><ORDER_NO></ORDER_NO><CUSTOMER_ID></CUSTOMER_ID><MARKET_ID>";
	xml += marketID.value;
	xml += "</MARKET_ID><COMMODITY_ID></COMMODITY_ID><SESSION_ID>1</SESSION_ID></REQ></GNNT>";
	name = "my_weekorder_query";
	sendXml(xml);
}
function responseMy_weekorder_query(record) {
	var xml = "<br/>&ltREC&gt" + record.getCount() + "条记录&lt/REC&gt";
    return xml;   
}

function holding_query() {
	var xml = "<GNNT><REQ name=\"holding_query\"><TRADER_ID>";
	xml += customerID.value;
	xml += "</TRADER_ID><CUSTOMER_ID></CUSTOMER_ID><MARKET_ID>";
	xml += marketID.value;
	xml += "</MARKET_ID><COMMODITY_ID></COMMODITY_ID><SESSION_ID>1</SESSION_ID></REQ></GNNT>";
	name = "holding_query";
	sendXml(xml);
}
function responseHolding_query(record) {
	var xml = "<br/>&ltREC&gt" + record.getCount() + "条记录&lt/REC&gt";
    return xml;   
}

function market_query() {
	var xml = "<GNNT><REQ name=\"market_query\"><TRADER_ID>";
	xml += customerID.value;
	xml += "</TRADER_ID><CUSTOMER_ID></CUSTOMER_ID><MARKET_ID>";
	xml += marketID.value;
	xml += "</MARKET_ID><SESSION_ID>1</SESSION_ID></REQ></GNNT>";
	name = "market_query";
	sendXml(xml);
}
function responseMarket_query(record) {
	var xml = "";
    var count = record.getCount()
    for(i=0;i<count;i++) 
    {
    	xml += "<br/>&ltREC&gt";
    	xml += "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&lt MA_I &gt" + record.getData(i,"MA_I") + "&lt /MA_I &gt";
    	xml += "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&lt MA_N &gt" + record.getData(i,"MA_N") + "&lt /MA_N &gt";    	
    	xml += "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&lt STA &gt" + record.getData(i,"STA") + "&lt /STA &gt";		
		//xml += "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&lt FI_I &gt" + record.getData(i,"FI_I") + "&lt /FI_I &gt";
    	xml += "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&lt MAR &gt" + record.getData(i,"MAR") + "&lt /MAR &gt";    	
    	xml += "<br/>&nbsp;&nbsp;&nbsp;&nbsp;&lt SH_N &gt" + record.getData(i,"SH_N") + "&lt /SH_N &gt";
    	xml += "<br/>&lt/REC&gt";
    }
    return xml;   
}

function commodity_data_query() {
	var xml = "<GNNT><REQ name=\"commodity_data_query\"><TRADER_ID>";
	xml += customerID.value;
	xml += "</TRADER_ID><CUSTOMER_ID></CUSTOMER_ID><COMMODITY_ID></COMMODITY_ID><SESSION_ID>1</SESSION_ID></REQ></GNNT>";
	name = "commodity_data_query";
	sendXml(xml);
}
function responseCommodity_data_query(record) {
	var xml = "<br/>&ltREC&gt" + record.getCount() + "条记录&lt/REC&gt";
    return xml;   
}

function sys_time_query() {
	var xml = "<GNNT><REQ name=\"sys_time_query\"><TRADER_ID>";
	xml += customerID.value;
	xml += "</TRADER_ID><CUSTOMER_ID></CUSTOMER_ID><LAST_ID></LAST_ID><SESSION_ID>1</SESSION_ID></REQ></GNNT>";
	name = "sys_time_query";
	sendXml(xml);
}
function responseSys_time_query(record) {
	var xml = "<br/>&ltREC&gt" + record.getCount() + "条记录&lt/REC&gt";
    return xml;   
}
</script>
</head>
<body  leftmargin="0" topmargin="0" onload="window_onload();" onkeypress="keyEnter(event.keyCode);">
<table border="0" height="300" align="center" class="common">
<tr>
	<td height="40">
			
	</td>
</tr>
<tr>
	<td>
		<input type="hidden" name="marketID" value="">		
	</td>
</tr>
<tr>
	<td>
		交易员ID：<input name="customerID" value="00011">		
	</td>
</tr>
<tr>
	<td>
		<input type="button"  name="commodity_query" class="button" onclick="javascript:commodity_query();" value="测试商品查询">
	</td>
</tr>
<tr>
	<td>
		<input type="button"  name="tradequery" class="button" onclick="javascript:tradequery();" value="测试成交查询">
	</td>
</tr>
<tr>
	<td>
		<input type="button"  name="my_order_query" class="button" onclick="javascript:my_order_query();" value="测试未成交查询">
	</td>
</tr>
<tr>
	<td>
		<input type="button"  name="my_weekorder_query" class="button" onclick="javascript:my_weekorder_query();" value="测试委托指令查询">
	</td>
</tr>
<tr>
	<td>
		<input type="button"  name="holding_query" class="button" onclick="javascript:holding_query();" value="测试持仓查询">
	</td>
</tr>
<tr>
	<td>
		<input type="button"  name="market_query" class="button" onclick="javascript:market_query();" value="测试市场查询">
	</td>
</tr>
<tr>
	<td>
		<input type="button"  name="commodity_data_query" class="button" onclick="javascript:commodity_data_query();" value="测试行情查询">
	</td>
</tr>
<tr>
	<td>
		<input type="button"  name="sys_time_query" class="button" onclick="javascript:sys_time_query();" value="测试查系统时间">
	</td>
</tr>
<tr>
	<td>
		<div id="response"></div>
	</td>
</tr>
</table>

</body>
</html>

