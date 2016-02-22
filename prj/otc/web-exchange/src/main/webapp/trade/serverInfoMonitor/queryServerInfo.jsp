<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

<html>
	<head>
		<title>行情数据源监控</title>
		<script language="javascript" type="text/javascript"
			src="${basePath}/js/jquery-1.5.2.min.js"></script>
			
		<style type="text/css">
			body {background:#fde6a3; margin:0px; font-size:14px;text-align: center; color:#542e00; font-weight:bold;}
			.bor_980{width:1000px; height:auto; margin:10px auto;}
			.bor_450{width:448px; height:auto; float:left; margin:0px 20px 20px 20px; border:1px solid #d0b475;}
			.line{ border-bottom:1px solid #d0b475; border-right:1px solid #d0b475;}
			.line1{ border-bottom:1px solid #d0b475;}
			.clear{clear:both;}
			.font_14B_G{}
		</style>
		<script type="text/javascript">
	
		function play(id,isMusic){
			var yinyue=document.getElementById("yinyue"+id); 
			if(isMusic){
				yinyue.innerHTML='<object data="'+id+'" type="application/x-mplayer2" width="0" height="0"> <param name="src" value="../../trade/serverInfoMonitor/'+id+'.mp3"><param name="autostart'+id+'" value="1"><param name="playcount" value="100000000"></object>';
				}else{
				yinyue.innerHTML='<object data="'+id+'" type="application/x-mplayer2" width="0" height="0"> <param name="src" value="../../trade/serverInfoMonitor/default.mp3"><param name="autostart'+id+'" value="1"><param name="playcount" value="100000000"></object>';
			}
			
			}
		function stop(id){
			var yinyue=document.getElementById("yinyue"+id); 
			yinyue.innerHTML='';
			}
		//ajax 获取行情信息
		function getServerInfo(){
			var url = "${basePath}/tradeManage/serverInfoForAjax/queryInfoPrice.action?t="+Math.random();
			var isMusic=false;
			$.ajaxSetup({
                error: function (x, e) {
                    if(x.responseText!=''){
						document.write(x.responseText);
						return false;
                    };
                    $("#errorMsg").css("display","");
                    return false;
                }
            });
			$.getJSON(url,function(data){
				$("#errorMsg").css("display","none");
				$.each(data,function(i,item){
					$.each(item,function(i,date){
						var serverId=i;
						$.each(date,function(i,price){
							var value="";
							if(i=="isExistMusic"){
								if(price==true){
									isMusic=true;
								}else{
									isMusic=false;
								}
							}else if(i=="status"){
								if(price=="0"){
									if($("#"+serverId+i).html().indexOf("断开")>0||$("#"+serverId+i).html()==""){
										stop(serverId);
									}
									$("#tab"+serverId).css("background","#FFFFFF");
									value="<font color='green'>正常</font>";
								}else{
									if(document.getElementById("yinyue"+serverId).innerHTML==''){
										play(serverId,isMusic);
									}
									$("#tab"+serverId).css("background","#FF2D2D");
									value="<font color='red'>断开</font>";
								}
								$("#"+serverId+i).html(value);
							}else if(i=="isUsed"){
								var obj = document.getElementById("cellsA"+serverId);
								if(price=='Y'){
									obj.style.backgroundImage = 'url(${basePath }/common/skinstyle/default/common/commoncss/images2/bg.gif)';
								}else{
									obj.style.backgroundImage = 'url(${basePath }/common/skinstyle/default/common/commoncss/images2/tb_bt.gif)';
								}
							}else if(i=="commodityMap"){
								$.each(price,function(s,priceMap){
									$.each(priceMap,function(d,data){
										if("price"==d){
											value=parseFloat(data).toFixed(4);
											$("#"+serverId+s+"_"+d).html(value);
										}else if("date"==d){
											$("#"+serverId+s+"_"+d).html(data);
										}
									});
									
								});
							}
						})
					})
				})
			});
		}
		getServerInfo();
		setInterval("getServerInfo()",1000);
		</script>
	</head>

	<body>
	<div class="bor_980">
	<c:forEach var="info" items="${serverInfoList}" varStatus="s">
	<span id="yinyue${info.id}"></span>
	  <div class="bor_450">
	    <table id="tab${info.id }" width="448" border="0" cellpadding="0" cellspacing="0" bgcolor="#FFFFFF">
	      <tr>
	        <td id="cellsA${info.id }" height="40" colspan="3" align="center" background="${basePath }/common/skinstyle/default/common/commoncss/images2/tb_bt.gif" class="font_14B_G">
	       		<c:if test="${s.index==0 }">
				<img  src="${basePath }/common/skinstyle/default/common/commoncss/images1/import.png" style="width: 16px;height: 15px;"/></c:if>${info.serverName }
	        </td>
	      </tr>
	      <tr>
	        <td width="33%" height="40" align="center" bordercolor="#0000FF" class="line">商品代码</td>
	        <td  width="33%" align="center" bordercolor="#0000FF" class="line">价格</td>
	        <td  width="*" align="center" bordercolor="#0000FF" class="line1">更新时间</td>
	      </tr>
	      <c:forEach items="${commodityList}" var="com">
	      <tr>
	        <td  height="40" align="center" bordercolor="#0000FF" class="line">${com.id }</td>
	        <td  align="center" bordercolor="#0000FF" style="font-size: 20px;font-weight: bold;color:#3C3C3C;" id="${info.id }${com.id}_price" class="line">0.00</td>
	        <td align="center" bordercolor="#0000FF" id="${info.id }${com.id}_date" class="line1">0.00</td>
	      </tr>
	      </c:forEach>
	      <tr>
	        <td  height="40" align="center" bgcolor="#edde9a">服务器信息：${info.serverAddr }</td>
	        <td  height="40" align="center" bgcolor="#EDDE9A">端口（${info.serverPort}）</td>
	        <td  height="40" align="center" bgcolor="#EDDE9A">连接状态（<span id="${info.id}status"></span>）</td>
	      </tr>
	    </table>
	  </div>
  	</c:forEach>
  	<div style="width:448px; height:auto; float:left; margin:0px 20px 20px 20px;text-align:left;">
	    <font color="red" size="2" align="left" >
	    	注：<img  src="${basePath }/common/skinstyle/default/common/commoncss/images1/import.png" style="width: 16px;height: 15px;"/>
	    	是主行情源，当主行情源更换时需刷新页面<br/>
	    	&nbsp;&nbsp;&nbsp;表头为绿色时表示当前使用行情源<br/>
	    	&nbsp;&nbsp;&nbsp;背景为红色时表示行情源异常
	    </font>
	</div>
  </div>
	<br/>
	<span id="errorMsg" style="color:red;font-size:20px;display:none;">本机网络连接中断！</span>
	</body>
</html>
