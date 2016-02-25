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
	ExcelApp.UserControl = true; 	
}