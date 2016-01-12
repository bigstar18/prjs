<%@ page contentType="text/html;charset=GBK"%>
<%@ include file="/mgr/public/includefiles/allincludefiles.jsp"%>
<html>

	<link rel="stylesheet"
		href="${skinPath }/css/validationengine/validationEngine.jquery.css"
		type="text/css" />
	<link rel="stylesheet"
		href="${skinPath }/css/validationengine/template.css" type="text/css" />
	<script src="${publicPath }/js/jquery-1.6.min.js"
		type="text/javascript">
	
</script>
	<script
		src="${publicPath }/js/validationengine/languages/jquery.validationEngine-zh_CN.js"
		type="text/javascript" charset="GBK">
	
</script>
	<script
		src="${publicPath }/js/validationengine/jquery.validationEngine.js"
		type="text/javascript" charset="GBK"></script>
	<script>
	jQuery(document).ready(function() {
		  $("#frm").validationEngine('attach');
			$("#add").click(function(check) {
				if ($("#frm").validationEngine('validate')) {
					if(frm.result.value!=''){
						var vaild = affirm("您确定要操作吗？");
						if (vaild == true) {
							$("#frm").validationEngine('attach');
							//$('#frm').attr('action', 'action');
							getValueByRightSelect();//匹配仓单范围检查取值的方法。 
						    $("#frm").submit();
							document.getElementById("add").disabled=true;
						}
					}else{
						alert("没有可设置的属性！");
					}
			}})
	});
	var selectnums=[];//shixw 计数 
	//通过品名获取商品属性信息
	function getPropertyByBreedID(){
		var viewflag=false;
		$("#content").html("");//清空属性div内容
		var url = "${basePath}/timebargain/settleProps/jsonForSettleProps/getPropertyValueByBreedID.action?commodityId=${entity.commodityId}&breedId=${entity.breedId}&sortId=${entity.sortId}&t="+Math.random();//访问路径
		$.getJSON(url,null,function call(result){//通过品名编号ajax获取属性信息
				//通过品名编号ajax获取属性信息
				var table=jQuery("<table  border='0' cellspacing='4' cellpadding='0'width='100%'></table>"); //table定义
				var pronum=0;//属性编号
				if(result==''){
					frm.result.value=result;
					}
				$.each(result, function(i, breed){//遍历获取到的json串
					var row=jQuery("<tr height='20px'></tr>"); 
					var isMatch;//是否仓单匹配
					var selectedValues=[];//已选中的值  
					var notSelectedValues=[];//未选的值
					var name,type,value,nessagery,fieldType,oldValue,oldNote;//保存每个属性的 属性名、属性类型、字典值、必填项、数据类型、商品已设置的属性值
					$.each(breed, function(j, property){
						if(j==0){
							name=property;
						}else if(j==1){
							type=property;
						}else if(j==2){
							if(type=="N"){
								nessagery=property;
							}else{
								value=property;
							}
						}else if(j==3){
							if(type=="N"){
								fieldType=property;
							}else{
								nessagery=property;
							}
						}else if(j==4){
							if(type=="N"){
								oldValue=property;
							}else{
								fieldType=property;
							}	
						}else if(j==5){
							if(type=="N"){
								oldNote=property;
							}else{
								oldValue=property;
							}	
						}else{
							oldNote=property;
						}
						 
						if(j==breed.length-1){
							isMatch=property;
						}
						
					});
					var td=jQuery("<td align='right'></td>") 
					var classType="";//设置文本框样式
					var showRequired="Y";//是否显示必填标识
					if(nessagery=='Y'){
						if(fieldType=='1'){
							classType="validate[required,custom[number],maxSize[64]";
						}else{
							classType="validate[required,maxSize[64]]";
							}
					}else{
						showRequired="N";
						if(fieldType=='1'){
							classType="validate[custom[number],maxSize[64]]";
						}else{
							classType="validate[maxSize[64]]";
						}
					}
					
					td.append("<input id='goodsPropertys["+i+"].propertyName'  name='goodsPropertys["+i+"].propertyName' class='"+classType+"' type='hidden' value='"+name+"'/>");	
					if(showRequired=="Y" && isMatch!="N"){
						td.append("<span class='required'>*</span>"+name+"：");
						}else{
							td.append(name+"：");
						}
					row.append(td);
					viewflag=true;
					var td1=jQuery("<td></td>") 
					if(type=="Y"){  //选择框
						if(isMatch=="M"){
							selectnums.push(i);
							var selectLYTable01=jQuery("<TABLE WIDTH='210' BORDER=0 CELLSPACING=0 CELLPADDING=0 HEIGHT='100'></TABLE>");//table
							var selectLYTr01=jQuery("<tr></tr>");
							var selectLYTd01=jQuery("<TD HEIGHT=24 WIDTH=104 ROWSPAN=2 ALIGN=CENTER></TD>");
							var selectLYTd02=jQuery("<TD WIDTH=30% HEIGHT=17></TD>");
							var selectLYTd03=jQuery("<TD WIDTH=50></TD>");
							var selectLYTd04=jQuery("<TD ROWSPAN=2 CLASS=Tab_page_Head WIDTH=104 ALIGN=CENTER></TD>");
							var selectLYTd05=jQuery("<TD WIDTH=30%></TD>");
							var selectLYTd06=jQuery("<TD></TD>");
							selectLYTr01.append(selectLYTd01);
							selectLYTr01.append(selectLYTd02);
							selectLYTr01.append(selectLYTd03);
							selectLYTr01.append(selectLYTd04);
							selectLYTr01.append(selectLYTd05);
							selectLYTr01.append(selectLYTd06);
							
							var selectLYTr11=jQuery("<TR></TR>");
							
							var selectLYTd11=jQuery("<TD WIDTH=30% HEIGHT=7 CLASS=Tab_page_line></TD>");
							var selectLYTd12=jQuery("<TD></TD>");
							var selectLYTd13=jQuery("<TD WIDTH=30% CLASS=Tab_page_line></TD>");
							var selectLYTd14=jQuery("<TD></TD>");
							selectLYTr11.append(selectLYTd11);
							selectLYTr11.append(selectLYTd12);
							selectLYTr11.append(selectLYTd13);
							selectLYTr11.append(selectLYTd14);
							
							var selectLYTr21=jQuery("<TR></TR>");
							
							var selectLYTd21=jQuery("<TD COLSPAN=2></TD>");
							var selectLYSelect01=jQuery("<select size='6' style='height:100%;width=100%'  id='selectStuff"+i+"' ondblclick='UserMove(0,"+i+");' name='selectStuff"+i+"' size='5' multiple='multiple'> </select>");
							selectLYTd21.append(selectLYSelect01);
							var selectLYTd22=jQuery("<TD VALIGN=MIDDLE ALIGN=CENTER></TD>");
							var selectLYTable001=jQuery("<TABLE BORDER=0 CELLSPACING=4 CELLPADDING=0></TABLE>");//table
								
							var selectLYTr001=jQuery("<TR></TR>");
							var selectLYTd001=jQuery("<TD></TD>");
							var selectLYInput01=jQuery("<INPUT style='height:20px;' TYPE=button VALUE='-->' NAME=B1 ONCLICK='UserMove (0,"+i+")' />");
							selectLYTd001.append(selectLYInput01);
							selectLYTr001.append(selectLYTd001);
									
							var selectLYTr002=jQuery("<TR></TR>");
							var selectLYTd002=jQuery("<TD></TD>");
							var selectLYInput02=jQuery("<INPUT style='height:20px;' ONCLICK='UserMove (1,"+i+")' VALUE='<--' NAME=B2 TYPE=button />");
							selectLYTd002.append(selectLYInput02);
							selectLYTr002.append(selectLYTd002);
									
							var selectLYTr003=jQuery("<TR></TR>");
							var selectLYTd003=jQuery("<TD></TD>");
							var selectLYInput03=jQuery("<INPUT style='height:20px;' TYPE=button VALUE='==>' NAME=B3 ONCLICK='UserMove (2,"+i+")' />");
							selectLYTd003.append(selectLYInput03);
							selectLYTr003.append(selectLYTd003);
									
							var selectLYTr004=jQuery("<TR></TR>");
							var selectLYTd004=jQuery("<TD></TD>");
							var selectLYInput04=jQuery("<INPUT style='height:20px;' TYPE=button VALUE='<==' NAME=B4 ONCLICK='UserMove (3,"+i+")'  />");
							selectLYTd004.append(selectLYInput04);
							selectLYTr004.append(selectLYTd004);
									
							selectLYTable001.append(selectLYTr001);
							selectLYTable001.append(selectLYTr002);
							selectLYTable001.append(selectLYTr003);
							selectLYTable001.append(selectLYTr004);
							selectLYTd22.append(selectLYTable001);
						
							var selectLYTd23=jQuery("<TD COLSPAN=2></TD>");
							var selectLYSelect02=jQuery("<SELECT size='6' style='height:100%;width=100%'  ONDBLCLICK=UserMove(1,"+i+"); id='selectToStuff"+i+"'   MULTIPLE='MULTIPLE'  class='validate[required]'></SELECT>");
							selectLYTd23.append(selectLYSelect02);
						
							selectLYTr21.append(selectLYTd21);
							selectLYTr21.append(selectLYTd22);
							selectLYTr21.append(selectLYTd23);
							
							selectLYTable01.append(selectLYTr01);
							selectLYTable01.append(selectLYTr11);
							selectLYTable01.append(selectLYTr21);	
							 
							//数组value 所有属性值 
							 if(oldValue.length>0){
							 		if(oldValue.indexOf('|')<0){
							 			for(var ssk=0;ssk<value.length;ssk++){
													if(value[ssk]==oldValue){
														selectedValues.push(oldValue);
														value.baoremove(ssk); 
													 }
												}
										
									}else{
										var attrBySplits=oldValue.substr(0,oldValue.length-1).split('|');
											for(var abst=0;abst<attrBySplits.length;abst++){
												for(var k=0;k<value.length;k++){
													if(value[k]==attrBySplits[abst]){
														selectedValues.push(value[k]);
														value.baoremove(k); 
													 }
												}
											}
							  		}
							  }
						 
								 //left And right  values  
								 $.each(selectedValues, function (abc) {
								 	selectLYSelect02.append("<option selected='selected' value='" + selectedValues[abc] + "'>" + selectedValues[abc] + "</option>");
								 	
								 });
								 
								 $.each(value, function (notabc,shi) {
								 	selectLYSelect01.append("<option value='" + value[notabc] + "'>" + value[notabc] + "</option>");
								 });
								 
								td1.append(selectLYTable01);//将选项放到选择器中
								var td2=jQuery("<td ></td>") 
								td2.append("<div class='onfocus'>请选择"+name+"信息</div>");
							}else{
									if(isMatch=='N'){
										var propertySelect =jQuery("<select maxLength='64' style='width: 130px' id='goodsPropertys["+i+"].propertyValue'  name='goodsPropertys["+i+"].propertyValue'></select>");//document.createElement("select");
									}else{
										var propertySelect =jQuery("<select maxLength='64' style='width: 130px' id='goodsPropertys["+i+"].propertyValue'  name='goodsPropertys["+i+"].propertyValue'  class='select "+classType+"'></select>");//document.createElement("select");
								}
								propertySelect.append("<option value='' style='wzy:expression(void(this.title=this.innerText))'>--请选择"+name+"--</option>"); 
								$.each(value, function(k, propertyValue){//遍历属性值
									var option=document.createElement("option");//生成新的属性值选项
								    option.value=propertyValue;//属性值
									option.innerText=propertyValue;//属性显示信息
									option.title=propertyValue;//用保存title属性信息
									if(oldValue){
										if(oldValue==propertyValue){
											option.selected=true;
										}
									}
									propertySelect.append(option);//加入属性选择器
								});
									td1.append(propertySelect);//将选项放到选择器中
									var td2=jQuery("<td ></td>") 
									td2.append("<div class='onfocus'>请选择"+name+"信息</div>");
								}
					}else{
						var inputval = "";
						if(oldValue){
							inputval=oldValue;
						}
						var td2=jQuery("<td ></td>"); 
					if(isMatch=='N'){
						if(inputval=="[object Object]"){
							inputval="";
						}
						td1.append("<input maxSize[64] id='goodsPropertys["+i+"].propertyValue'  name='goodsPropertys["+i+"].propertyValue' value='"+inputval+"'/>");//生成输入框
						td2.append("<div class='onfocus'>请输入"+name+"信息</div>");
					}else{
					
						td1.append("<input maxSize[64] id='goodsPropertys["+i+"].propertyValue'  name='goodsPropertys["+i+"].propertyValue' class='"+classType+"'  value='"+inputval+"'/>");//生成输入框
						td2.append("<div class='onfocus'>请输入"+name+"信息</div>");
						
						}
					}
					var td3=jQuery("<td ></td>") ;
					var noteValue="";
					if(oldNote&&oldNote!="[object Object]"){
						noteValue=oldNote;
					}
					td3.append("备注：<input readonly='readonly' maxSize[64] id='goodsPropertys["+i+"].note'  name='goodsPropertys["+i+"].note' class='validate[maxSize[200]]'  value='"+isMatch+"' />");//生成输入框
					row.append(td1);
					row.append(td3);
					row.append(td2);
					table.append(row);
					pronum++;
				});
				$("#content").append(table);//将table加入div中
				$("#frm").validationEngine("attach");
			});
		}
		
		/**
		 *仓单匹配范围检查多值获取 	
		*/
		function getValueByRightSelect(){
			var hiddenInputBySelect="";
			for(var snums=0;snums<selectnums.length;snums++){
				if(document.getElementById("selectToStuff"+selectnums[snums])!=null){//判断获取的
					var str="";
					var valueOptions=document.getElementById("selectToStuff"+selectnums[snums]).options;
						for(var cdf=0;cdf<valueOptions.length;cdf++){
							str += valueOptions[cdf].value+"|";
						}
						var divid=document.getElementById("divid");
						hiddenInputBySelect=hiddenInputBySelect + "<input id='goodsPropertys["+selectnums[snums]+"].propertyValue' name='goodsPropertys["+selectnums[snums]+"].propertyValue' value='"+str+"' type='hidden'/>";
						divid.innerHTML=hiddenInputBySelect;
				}
			}
		}
					 
	   //从新定位错误提示信息
	function resetPromts(){
		$("#frm").validationEngine("updatePromptsPosition");
	}
	

	function getvalue(value){
		if(value && !(value instanceof Object)){
			return value;
		}
		return "";
	}
	
	//移动选中功能
	//nType = 0: 左边选中的一个移到右边
	//nType = 1: 右边选中的一个移到左边
	//nType = 2: 左边全部移到右边
	//nType = 3: 右边全部移到左边
	//iByBreed区分select相同的名字
	function UserMove (nType,iByBreed)
	{
	    if (nType == 0 && document.getElementById("selectStuff"+iByBreed).selectedIndex < 0)
	        return;
	    if (nType == 1 && document.getElementById("selectToStuff"+iByBreed).selectedIndex < 0)
	        return;
	
	    if (nType == 0) {
	            OrtSelectMove (document.getElementById("selectStuff"+iByBreed), document.getElementById("selectToStuff"+iByBreed), 0);
	    } else if (nType == 1) {
	            OrtSelectMove (document.getElementById("selectToStuff"+iByBreed), document.getElementById("selectStuff"+iByBreed), 0);
	    } else if (nType == 2) {
	            OrtSelectMoveAll (document.getElementById("selectStuff"+iByBreed), document.getElementById("selectToStuff"+iByBreed), 0);
	    } else {
	            OrtSelectMoveAll (document.getElementById("selectToStuff"+iByBreed), document.getElementById("selectStuff"+iByBreed), 0);
	    }
	}
	
	
	//将选中的一个进行移动
	function OrtSelectMove (Source, Target, Start)
	{
	    var nIndex;
	    var eItem;
	    if (Start < 0)
	        Start = 0;
	    nIndex = Source.selectedIndex;
	    if (nIndex < Start) return;
	    if (Target != null)
	    {
	        eItem = document.createElement ("OPTION");
	        Target.add (eItem);
	       	eItem.text = Source.item (nIndex).text;
	        eItem.value = Source.item (nIndex).value;
	        Target.selectedIndex = Target.length - 1;
	    }
	    Source.remove (nIndex);
	    if (nIndex >= Source.length)
	        nIndex = Source.length - 1;
	    Source.selectedIndex = nIndex;
	}
	
	//将所有的进行移动
	function OrtSelectMoveAll (Source, Target, Start)
	{
	    var eItem;
	    if (Start < 0)
	        Start = 0;
	    if (Source.length < Start)
	        return;
	    while (Source.length > Start)
	    {
	        if (Target != null)
	        { 
	            eItem = document.createElement ("OPTION");
	            Target.add (eItem);
	            eItem.text = Source.item (Start).text;
	            eItem.value = Source.item (Start).value;
	        }
	        Source.remove (Start);
	    }
	    Source.selectedIndex = -1;
	    if (Target != null)
	        Target.selectedIndex = Target.length - 1;
	}

</script>
	<head>
		<title>交收属性设置</title>
	</head>
	<body onload="getPropertyByBreedID()">
		<form id="frm" name="frm" method="post" enctype="multipart/form-data"
			action="${basePath}/timebargain/settleProps/addSettleProps.action?entity.commodityId=${entity.commodityId}"
			targetType="hidden">
			<div class="div_cx">
				<table border="0" width="100%">
					<tr>
						<td>
							<div class="warning">
								<div class="content">
									温馨提示：交收属性设置&nbsp;&nbsp;&nbsp;<br/>
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;M 代表范围检查,N 代表不检查,Y 代表检查
								</div>
							</div>
							 
							<table border="0" width="100%">
								<input type="hidden" id="result" value="1" />
								<tr>
									<td>
										<div class="div_tj">
											<div style="clear: both;"></div>
											<div
												style="overflow-x: hidden; position: absolute; z-index: 1; overflow: auto; height: 300px; width: 100%;">
												<table border="0" cellspacing="0" cellpadding="4"
													width="100%" class="table2_style">
													<tr>
														<div class="div_cxtj">
															<div class="div_cxtjL"></div>
															<div class="div_cxtjC">
																基本信息
															</div>
															<div class="div_cxtjR"></div>
														</div>
													</tr>

													<tr height="20">
														<td align="center" colspan="2">
															商品代码：${entity.commodityId }
														</td>
														<td align="left">
															商品名称： ${entity.name }
														</td>
													</tr>
													<tr>
														<td id="content" colspan="3">
														</td>
													</tr>
													<tr>
														<td align="center" colspan="4">
															<span class="progressBar" id="pb1"></span>
															<div id="divid"></div>
															<span id="pb2"><rightButton:rightButton name="添加"
																	onclick="" className="btn_sec"
																	action="/timebargain/settleProps/addSettleProps.action"
																	id="add"></rightButton:rightButton> </span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
														</td>
													</tr>
												</table>
											</div>
										</div>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>
<%@ include file="/mgr/public/jsp/footinc.jsp"%>