function pagePrint(printData)
{
	var objArg = new Object();
	objArg.HTML = printData.outerHTML;
	var	sUrl = basePath + "public/print.jsp";
	openDialog(sUrl,objArg,800,600);
}

function exportToExcel( objTbl , rowIndex1 , colIndex , rowIndex)
{
	var ExcelApp, ExcelBook , ExcelSheet;	
	if(!rowIndex1)
		rowIndex1 = 0 ;
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
	//alert("rowCount:"+rowCount);
	var colCount = objTbl.rows[rowIndex1].cells.length;
	//alert("colCount:"+colCount);
	var excelRIndex = 1;
	
	for( var i = rowIndex ; i < rowCount ; i++ )
	{
		var excelCIndex = 1;
		for( var j = colIndex ; j < colCount ; j++ )
		{
			if(objTbl.rows[i].cells.length-1==j&&j!=0)
				{
				  	break;
				}
			if(objTbl.rows(i).cells(j).colSpan > 1)
			{
				var length=objTbl.rows(i).cells(j).colSpan;
				var a=objTbl.rows(i).cells(j).innerText;
				ExcelSheet.Cells(excelRIndex,excelCIndex).value = objTbl.rows(i).cells(j).innerText;
				ExcelSheet.Range(ExcelSheet.cells(excelRIndex,excelCIndex),ExcelSheet.Cells(excelRIndex,excelCIndex+objTbl.rows(i).cells(j).colSpan-1)).Select;
        ExcelApp.Selection.Merge();
        ExcelApp.Selection.Font.bold= true;
        var xlCenter=-4108;
        ExcelApp.Selection.HorizontalAlignment = xlCenter;
				//ExcelSheet.Cells(excelRIndex,excelCIndex).ColumnWidth = objTbl.rows(i).cells(j).colSpan;
				//j=j+length-1;
				
				excelCIndex=excelCIndex+length;
				//excelCIndex++;
				if(i==3)
				{
				//alert("j:"+j);
				//alert("excelCIndex:"+excelCIndex);
				//alert(a);
			  }
				//break;
				continue;
			}
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
	//ExcelApp.Quit();
	ExcelSheet.Close(savechanges=true);
}