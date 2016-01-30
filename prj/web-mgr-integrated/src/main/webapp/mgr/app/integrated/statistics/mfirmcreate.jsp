<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>
	<head>
		<title>开户情况统计</title>
	</head>
	<body onload="change()">
		<div id="main_stats_body">
			<table class="table1_style" border="0" cellspacing="0"
				cellpadding="0" height="100%">
				<tr height="10%">
					<td>
						<div class="div_cx">
							<form name="frm"
								action="${basePath}/statisticsQuery/mfirmCreate/mfirmCreateForwardQuery.action"
								method="post" target="frame">
								<table border="0" cellpadding="0" cellspacing="0"
									class="table2_style">
									<tr>
										<td class="table2_td_width" style="width: 100%">
											<div class="div2_top">
												<table class="table3_style" border="0" cellspacing="0"
													cellpadding="0">
													<tr>
														<td class="table3_td_1" align="right">
															开始日期:&nbsp;
															<label>
																<input type="text" class="wdate" size="18"
																	id="startDate" name="startDate"
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />
															</label>
														</td>
														<td class="table3_td_1" align="right">
															结束日期:&nbsp;
															<label>
																<input type="text" class="wdate" size="18" id="endDate"
																	name="endDate"
																	onFocus="WdatePicker({el:this,dateFmt:'yyyy-MM-dd',skin:'whyGreen'})" />
															</label>
														</td>
														<td class="table3_td_anniu" align="left">
															<button class="btn_sec" onclick=select1();>
																显示图表
															</button>
															&nbsp;&nbsp;
															<button class="btn_sec" onclick=myReset();>
																重置
															</button>
														</td>
													</tr>
												</table>
											</div>
										</td>
									</tr>
								</table>
							</form>
						</div>
					</td>
				</tr>
				<tr height="85%" id="birtTRHeight">
					<td>
						<iframe name="frame"
							src="${basePath }/mgr/frame/rightframe_bottom.jsp"
							frameborder="0" scrolling="no" width="100%" height="99%"></iframe>
					</td>
				</tr>
				<tr height="*"></tr>
			</table>


			<SCRIPT type="text/javascript">
	function change(){
		if( frm.startDate.value==""&& frm.endDate.value==""){
			var d=new Date().getFullYear() +"-"+ new Date().getMonth() +"-"+ new Date().getDate();
			var fDate=new Date(Date.parse(d.replace(/-/g ,"/")));
			frm.startDate.value=fDate.format("yyyy-MM-dd");
			frm.endDate.value=new Date().format("yyyy-MM-dd");
		}
	}
	function select1() {
		var frameWidth = document.getElementById("frame").scrollWidth*0.746;
		var frameHeight = (document.getElementById("frame").scrollHeight-28)*0.746;
		//日期验证
		var sDate=frm.startDate.value;
		var eDate=frm.endDate.value;
 		var  reg=/^\d{4}[\/\-](0?[1-9]|1[012])[\/\-](0?[1-9]|[12][0-9]|3[01])$/;
	 	//验证通过后  执行查询
        if((reg.test(sDate)||sDate =="")&&(reg.test(eDate)||eDate =="")){    
			var action=frm.action;
			frm.action="${basePath}/frameset?__report=mgr/app/integrated/statistics/mfirmcreate.rptdesign&__clean=true&__showtitle=false&__title=&__fittopage=true&__isHostPage=true&__navigationbar=false&__format=HTML&frameWidth="+frameWidth+"&frameHeight="+frameHeight;
			checkQueryBirtDate(frm.startDate.value,frm.endDate.value);
			frm.action=action;
			return true;
         } 
		 //验证不通过 不执行查询
         else{    
             alert("不符合日期格式，验证失败！");    
             return false;    
         }    
	}
</SCRIPT>
	</body>
</html>