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
		alert("Excel ActiveX �ؼ�δ���ã���Ҫʹ�ô˹����������²�����\r\n"
		+ "1.ѡ��IE������˵�-������-��Internetѡ�\r\n"
		+ "2.�ڵ����ĶԻ�����ѡ��-����ȫ-���Զ��弶��\r\n"
		+ "3.�ڵ����ĶԻ������ҵ�\r\n"
		+ "  ����û�б��Ϊ��ȫ��ActiveX �ؼ����г�ʼ���ͽű����С���\r\n"
		+ "  ��ѡ����ѡ���ȷ�ϡ�");
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
		alert("Excel ActiveX �ؼ�δ���ã���Ҫʹ�ô˹����������²�����\r\n"
		+ "1.ѡ��IE������˵�-������-��Internetѡ�\r\n"
		+ "2.�ڵ����ĶԻ�����ѡ��-����ȫ-���Զ��弶��\r\n"
		+ "3.�ڵ����ĶԻ������ҵ�\r\n"
		+ "  ����û�б��Ϊ��ȫ��ActiveX �ؼ����г�ʼ���ͽű����С���\r\n"
		+ "  ��ѡ����ѡ���ȷ�ϡ�");
		return;
	}
	//alert(path);
	f1 = fso.CreateTextFile(path, true); 
	if(objTbl.rows.length>1){
		var rowCount = objTbl.rows.length;
		var colCount = objTbl.rows[rowIndex].cells.length;
		var str="";
		
		var strs= new Array(); //����һ����
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
	alert("�����ɹ�");

}


Date.prototype.pattern=function(fmt) {
    var o = {    
    "M+" : this.getMonth()+1, //�·�    
    "d+" : this.getDate(), //��    
    "h+" : this.getHours()%12 === 0 ? 12 : this.getHours()%12, //Сʱ    
    "H+" : this.getHours(), //Сʱ    
    "m+" : this.getMinutes(), //��    
    "s+" : this.getSeconds(), //��    
    "q+" : Math.floor((this.getMonth()+3)/3), //����    
    "S" : this.getMilliseconds() //����    
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
//2009-9-9(������)
function getDate(datechick){
	return getDateTime(datechick,"?","?","S");
}
//19:05:30(ʱ����)
function getTime(timechick){
	return getDateTime("?","?",timechick,"?");
}
//19:05:30.35(ʱ�������)
function getTime_Msel(timechick){
	return getDateTime("?","?",timechick,"S");
}
//����
function getWeek(ee){
	return getDateTime("?",ee,"?","S");
}
//���ʺ�Ϊȡ���ַ���ʽ��ʱ��
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
		format=format+"HH"+timechick+"mm"+timechick+"ss";//СдHH��ʾ24����ʱ��
		//format=format+"hh"+timechick+"mm"+timechick+"ss";//Сдhh��ʾ12����ʱ��
		if(msel !== "" || msel !== "?"){
			format=format+".S";
		}
	}
	var time=(new Date()).pattern(format);
	return time;
}