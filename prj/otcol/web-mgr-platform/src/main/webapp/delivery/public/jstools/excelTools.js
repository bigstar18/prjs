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
	ExcelApp.UserControl = true; 	
}