<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/public/session.jsp"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" import="java.util.*" pageEncoding="GBK"%>
<html>
	<head>
	<title></title>
		<script language="JavaScript" src="<%=basePath%>/public/global.js"></script>
		<script language="JavaScript" src="<%=basePath%>/public/open.js"></script>
		<SCRIPT type="text/javascript" src="<%=basePath%>/js/jquery-1.5.2.min.js"></SCRIPT>
		<style type="text/css">
			<!--
			.yin {
				visibility: hidden;
				position: absolute;
			}
			
			.xian {
				visibility: visible;
			}
			-->
		</style>
		<SCRIPT type="text/javascript">
			var day = <s:property value='#day'/>
			var checkinfo = {};
			function initNum()
			{
				<s:iterator value="#info" var="temp">
					checkinfo["<s:property value='#temp.id'/>"]=true;
				</s:iterator>
				
			}
			window.onload = function(){
				initNum();
			}
			function change(event)
			{
				 var evt = window.event||event;   
				 var ele = evt.target||evt.srcElement;//�����¼���Ԫ�ض���  
				 checkinfo[ele.value]=(!checkinfo[ele.value])
			}
			function save_onclick()
			{
				mdata = ''
				var flag=false;
				for (var one in checkinfo) 
				{
					if(!checkinfo[one])
					{
						flag = true;
						if(!$('#'+one).attr("checked"))
						{
							var temp = 'del='+one+'&';
							mdata+=temp;
						}
						else
						{
							var temp = 'save='+one+'&';
							mdata+=temp;
						}
					}
				}
				mdata=mdata+'tradeTimeSequenct='+day;
				if(flag)
				{
					if(affirm("��ȷ��Ҫ�ύ��"))
					{
						
						$.ajax({
						   type: "GET",
						   url: "${basePath}/tradeManage/commodityTradingParameter/handleCommodityChange.action",
						   data: mdata,
						   success: function(msg){
						     alert(msg);
						     window.close();
						   }
						});
						

					}
				}
				else
				{
					alert('��Ʒδ�ı�');
				}
		
			}
		</SCRIPT>
	</head>
	<body>
		<table border="0" height="400" width="600" align="center">
			<tr>
				<td>
					<form action="" id="Myform" method="post" class="form" targetType="hidden">
						<fieldset class="pickList">
							<legend class="common">
								<b>���׽���Ʒ����</b>
							</legend>
							<table width="100%" border="0" align="center" class="common">
								<tr>
									<td>���׽�����</td>
									<td><s:property value='#name'/></td>
								</tr>
								<s:iterator value="#info" var="temp">
									<tr>
										<td><s:property value="#temp.name"/></td>
										<td>
											<s:if test="#temp.flag">
												<input id="<s:property value='#temp.id'/>" onclick="change()" type="checkbox" checked="checked" value="<s:property value='#temp.id'/>">
											</s:if>
										 	<s:else>
										 		<input id="<s:property value='#temp.id'/>" onclick="change()" type="checkbox" value="<s:property value='#temp.id'/>">
										 	</s:else>
										</td>
									</tr>
								</s:iterator>
								<tr>
									<td>
										<input name="update" type="button" class="btn_sec"  onclick="save_onclick();" value="�ύ">
									</td>
									<td>
										<input type="button" class="btn_sec"  onclick="window.close()" value="�ر�">
									</td>
								</tr>
							</table>
						</fieldset>
					</form>
				</td>
			</tr>
		</table>
	
	</body>