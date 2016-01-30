<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<link rel="stylesheet"
		href="${skinPath }/css/validationengine/validationEngine.jquery.css" type="text/css" />
<link rel="stylesheet"
		href="${skinPath }/css/validationengine/template.css" type="text/css" />
<script 
        src="${publicPath }/js/jquery-1.6.min.js"
		type="text/javascript">
</script>
<script
		src="${basePath }/mgr/app/integrated/js/jquery.validationEngine-zh_CN.js"
		type="text/javascript" charset="GBK">
</script>
<script
		src="${publicPath }/js/validationengine/jquery.validationEngine.js"
		type="text/javascript" charset="GBK">
</script>
<script>
//将某个选框中选中的信息转移到另一个选框中
function se1tose2(se1Id,se2Id,isselected){
	if($("#"+se1Id+" option:selected").length>0){
		$("#"+se1Id+" option:selected").each(function(){
			$("#"+se2Id).append(getopt($(this),isselected));
			$(this).remove();
		});
	}
}
//将某个选框中所有信息转移到另一个选框中
function allse1tose2(se1Id,se2Id,isselected){
	if($("#"+se1Id+" option").length>0){
		$("#"+se1Id+" option").each(function(){
			$("#"+se2Id).append(getopt($(this),isselected));
			$(this).remove();
		});
	}
}

//通过 se1 获取 option
function getopt(se1,isselected){
	var propt="";
	var array = new Array();
	array[array.length]="id";
	array[array.length]="name";
	array[array.length]="value";
	array[array.length]="title";

	for(var i=0;i<array.length;i++){
		if(se1.attr(array[i])){
		propt+=" "+array[i]+"='"+se1.attr(array[i])+"'";
		}
	}
	if(!(isselected == false || isselected == 'false')){
		propt+=" selected='true'";
	}

	var result = "<option "+propt+">"+se1.text()+"</option>";
	return result;
}
	jQuery(document).ready(function() {
		if ("" != '${ReturnValue.info}' + "") {
			parent.document.frames('leftFrame').location.reload();
		}

		$("#frm").validationEngine('attach');
		$("#update").click(function(check) {
			if ($("#frm").validationEngine('validate')) {
				var vaild = affirm("您确定要操作吗？");
				if (vaild == true) {
					getnewoperatefirm();
					$("#frm").attr("action",'${basePath}'+$(this).attr("action"));
					$("#frm").validationEngine('detach');
					$('#frm').submit();
				}
			}
		});
	});
//遍历将可操作交易商代码放到提交文本框中
	function getnewoperatefirm(){
		var firms="";
		$("#operateId option").each(function(){
			if(firms.length>0){
				firms+=",";
			}
			firms+=$(this).val();
		});
		$("#operateFirm").attr("value",firms);
	}
</script>
<body>
	<head>
		<title>可操作交易商设置</title>
		<meta http-equiv="Pragma" content="no-cache">
	</head>
	<iframe id="hiddenframe" name="hiddenframe" width=0 height=0 style="display:none" src="" application="yes"></iframe>
    <form name="frm" id="frm" method="post" width="95%" heigh="60%" target="hiddenframe">
    <input name="entity.agentTraderId" value="${entity.agentTraderId}" type="hidden"/>
		<div class="st_title">
			<table border="0" width="90%" align="center">
				<tr>
					<td>
			            <div class="warning">
				            <div class="content">温馨提示 :可操作交易商设置</div>
			            </div>
			        </td>
		        </tr>
		        <tr>
				    <td>
			            
			                <div class="div_cxtj">
				   	            <div class="div_cxtjL"></div>
			                    <div class="div_cxtjC">可操作交易商设置</div>
			                    <div class="div_cxtjR"></div>
		                    </div>
			                <div style="clear: both;"></div>
					            <table border="0" cellspacing="0" cellpadding="0" width="100%" align="center" class="st_bor" height="40%">
						            <tr height="10">
						                <td></td>
						            </tr>
						            <tr height="35">
					                    <td colspan="4" align="center">
								        	<table>
								        		<tr>
									        	    <td align="center"  width="40%">
										                 <select id="unoperateId" in="unoperateFirm" class="normal" size=16 multiple style="text-align:right;width:150px;height:150px;">
											                 <c:forEach var="mfirm" items="${unoperateFirm}">
											    	             <option value="${mfirm.firmId}" title="${mfirm.firmId}">${mfirm.firmId}</option>
											                 </c:forEach>
											             </select><br/>
														 <font color="#FF0000">未用交易商</font>
										            </td>
								        			<td>
								        				<table border="0" height="150" width="20%" >
															<tr>
																<td align="center" valign="bottom" >
																	<input type="button" name="addsome" id="addsome" value="  >   "  onclick="se1tose2('unoperateId','operateId','true');" class="btn_sec" />
												                </td>
												            </tr>
												            <tr>
												                <td align="center" valign="top">
												                    <input type="button" name="addall" id="addall" value=" >> "  onclick="allse1tose2('unoperateId','operateId','true');" class="btn_sec" />
												                </td>
												            </tr>
												            <tr>
												                <td align="center" valign="bottom" >
												                    <input type="button" name="delsome" id="delsome" value="  <   "  onclick="se1tose2('operateId','unoperateId','true');" class="btn_sec" />
												                </td>
												            </tr>
												            <tr>
												                <td align="center" valign="top">
												                    <input type="button" name="delall" id="delall" value=" << "  onclick="allse1tose2('operateId','unoperateId','true');" class="btn_sec" />
												                </td>
												            </tr>
												            <tr height="20">
												                <td align="center" valign="top">
												                </td>
												            </tr>
												                <input type="hidden" id="operateFirm" name="entity.operateFirm" value="${entity.operateFirm}"/>
													    </table>
								        			</td>
									        		<td class="td_size" align="center"  width="40%">
										                <select id="operateId" in="operateFirm" class="normal" size=16 multiple style="text-align:left;width:150px;height:150px;">
											                <c:forEach var="mfirm" items="${operateFirm}">
											    	            <option value="${mfirm.firmId}" title="${mfirm.firmId}">${mfirm.firmId}</option>
											                </c:forEach>
											            </select><br/>
											            <font color="#0000FF">已用交易商</font>
										            </td>
								        	   </tr>
								           </table>
					                   </td>
						           </tr>
					           </table>
					           <div class="tab_pad">
			<table border="0" cellspacing="0" cellpadding="0" width="100%" align="center">
				<tr height="35">
					<td align="right">
						<rightButton:rightButton name="修改" onclick="" className="btn_sec" action="/trade/agenttrader/updateAgentTrader.action" id="update"></rightButton:rightButton>
					    &nbsp;&nbsp;
						<button class="btn_sec" onClick="window.close();">
						        关闭
						</button>
					</td>
				</tr>
			</table>
		</div>
				           </table>
				       </td>
		           </tr>
			   </table>
		   </div>
		
	</form>
</body>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>