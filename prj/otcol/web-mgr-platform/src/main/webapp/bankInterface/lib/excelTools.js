function pagePrint(printData)
{
	var objArg = new Object();
	objArg.HTML = printData.outerHTML;
	var	sUrl = basePath + "public/print.jsp";
	openDialog(sUrl,objArg,800,600);
}

function exportToExcel( objTbl , rowIndex , colIndex )
{
	var ExcelApp, ExcelBook , ExcelSheet;	
	if(!rowIndex)
		rowIndex = 0 ;
	if(!colIndex)
		colIndex = 0;
	try{
		ExcelApp = new ActiveXObject("Excel.Application");
	} catch(e) {
		alert("Excel ActiveX 控件未启用，若要使用此功能请做以下操作：\r\n"
		+ "1.选择IE浏览器菜单-〉工具-〉Internet选项。\r\n"
		+ "2.在弹出的对话框中选择-〉安全-〉自定义级别。\r\n"
		+ "3.在弹出的对话框中找到\r\n"
		+ "  【对没有标记为安全的ActiveX 控件进行初始化和脚本运行】，\r\n"
		+ "  点选启用选项。点确认。");
		return;
	}
	ExcelBook = ExcelApp.Workbooks.Add();
	ExcelSheet = ExcelBook.ActiveSheet;
	
	var rowCount = objTbl.rows.length;
	var colCount = objTbl.rows[rowIndex].cells.length
	var excelRIndex = 1;
	
	for( var i = rowIndex ; i < rowCount ; i++ )
	{
		var excelCIndex = 1;
		for( var j = colIndex ; j < colCount ; j++ )
		{
			if(objTbl.rows(i).cells(j).colSpan > 1)
				break;
			if(objTbl.rows(i).cells(j).style.display == "")
			{
				ExcelSheet.Cells(excelRIndex,excelCIndex).value = objTbl.rows(i).cells(j).innerText; 
				excelCIndex++;
			}			
		}
		excelRIndex++
	}
	ExcelApp.Visible = true; 
	ExcelBook.SaveAS("print.xls");
	ExcelApp.UserControl = true; 	
}



function exportToTxt( objTbl , rowIndex , colIndex,path )
{
	var fso, f1; 
	if(!rowIndex)
		rowIndex = 1 ;
	if(!colIndex)
		colIndex = 0;
	try{
		fso = new ActiveXObject("Scripting.FileSystemObject"); 
	} catch(e) {
		alert("Excel ActiveX 控件未启用，若要使用此功能请做以下操作：\r\n"
		+ "1.选择IE浏览器菜单-〉工具-〉Internet选项。\r\n"
		+ "2.在弹出的对话框中选择-〉安全-〉自定义级别。\r\n"
		+ "3.在弹出的对话框中找到\r\n"
		+ "  【对没有标记为安全的ActiveX 控件进行初始化和脚本运行】，\r\n"
		+ "  点选启用选项。点确认。");
		return;
	}
	//alert(path);
	f1 = fso.CreateTextFile(path, true); 
	if(objTbl.rows.length>1){
		var rowCount = objTbl.rows.length;
		var colCount = objTbl.rows[rowIndex].cells.length;
		var str="";
		
		var strs= new Array(); //定义一数组
		f1.WriteLine("<begin>") ;
		for( var i = rowIndex ; i < rowCount ; i++ )
		{	
			str="";	
			for( var j = colIndex ; j < colCount ; j++ )
			{
				if(objTbl.rows(i).cells(j).colSpan > 1)
					break;
				if(objTbl.rows(i).cells(j).style.display == "")
				{
					if(j==0)
					    str=str+objTbl.rows(i).cells(j).innerText; 
					else 
						str=str+"|"+objTbl.rows(i).cells(j).innerText; 
				}			
			}
			strs=str.split("\|");
			if(strs[0]=="1")
				str=strs[0]+"|"+strs[1]+"|"+strs[3];
			f1.WriteLine(str) ;
		}
	}else{
		f1.WriteLine("<begin>") ;		
	}
	f1.Write("<end>") ;
	f1.Close();
	alert("导出成功");

}


Date.prototype.pattern=function(fmt) {
    var o = {    
    "M+" : this.getMonth()+1, //月份    
    "d+" : this.getDate(), //日    
    "h+" : this.getHours()%12 === 0 ? 12 : this.getHours()%12, //小时    
    "H+" : this.getHours(), //小时    
    "m+" : this.getMinutes(), //分    
    "s+" : this.getSeconds(), //秒    
    "q+" : Math.floor((this.getMonth()+3)/3), //季度    
    "S" : this.getMilliseconds() //毫秒    
    };    
    var week = {    
    "0" : "\u65e5",    
    "1" : "\u4e00",    
    "2" : "\u4e8c",    
    "3" : "\u4e09",    
    "4" : "\u56db",    
    "5" : "\u4e94",    
    "6" : "\u516d"   
    };    
    if(/(y+)/.test(fmt)){    
        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));    
    }    
    if(/(E+)/.test(fmt)){    
        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "\u661f\u671f" : "\u5468") : "")+week[this.getDay()+""]);    
    }    
    for(var k in o){    
        if(new RegExp("("+ k +")").test(fmt)){    
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));    
        }    
    }    
    return fmt;    
};
//2009-9-9(年月日)
function getDate(datechick){
	return getDateTime(datechick,"?","?","S");
}
//19:05:30(时分秒)
function getTime(timechick){
	return getDateTime("?","?",timechick,"?");
}
//19:05:30.35(时分秒毫秒)
function getTime_Msel(timechick){
	return getDateTime("?","?",timechick,"S");
}
//星期
function getWeek(ee){
	return getDateTime("?",ee,"?","S");
}
//疑问号为取消字符格式化时间
function getDateTime(datechick,ee,timechick,msel){
	var format="";
	if(datechick !="?"){
		format=format+"yyyy"+datechick+"MM"+datechick+"dd ";
	}
	if(ee !== "?"){
		if(ee.length==1){
			format=format+"E ";
		}else if(ee.length==2){
			format=format+"EE ";
		}else {
			format=format+"EEE ";
		}
	}
	if(timechick !== "?"){
		format=format+"HH"+timechick+"mm"+timechick+"ss";//小写HH表示24进制时间
		//format=format+"hh"+timechick+"mm"+timechick+"ss";//小写hh表示12进制时间
		if(msel !== "" || msel !== "?"){
			format=format+".S";
		}
	}
	var time=(new Date()).pattern(format);
	return time;
}