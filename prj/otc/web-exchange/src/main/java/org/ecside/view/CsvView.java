package org.ecside.view;

import gnnt.MEBS.base.util.ThreadStore;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.ecside.core.TableModel;
import org.ecside.core.bean.Column;
import org.ecside.core.bean.Export;
import org.ecside.core.context.ContextUtils;
import org.ecside.table.calc.CalcResult;
import org.ecside.table.calc.CalcUtils;
import org.ecside.table.handler.ColumnHandler;
import org.ecside.table.handler.ExportHandler;
import org.ecside.util.ExportViewUtils;
import org.ecside.util.ExtremeUtils;

public class CsvView
  implements View
{
  public static final String DELIMITER = "delimiter";
  static final String DEFAULT_DELIMITER = ",";
  private OutputStream outputStream;
  private OutputStream outputStreamOut;
  private StringBuffer rowBuffer = null;
  private PrintWriter out = null;
  private String delimiter;
  
  public void beforeBody(TableModel model)
  {
    this.outputStream = ContextUtils.getResponseOutputStream(model.getContext());
    
    this.outputStreamOut = null;
    if (this.outputStream == null)
    {
      this.outputStream = new ByteArrayOutputStream();
      this.outputStreamOut = this.outputStream;
    }
    this.out = new PrintWriter(this.outputStream);
    Export export = model.getExportHandler().getCurrentExport();
    this.delimiter = export.getAttributeAsString("delimiter");
    
    List columns = model.getColumnHandler().getHeaderColumns();
    ThreadStore.put("ecsideColumns", columns);
    if (StringUtils.isBlank(this.delimiter)) {
      this.delimiter = ",";
    }
    boolean isFirstColumn = true;
    
    this.rowBuffer = new StringBuffer();
    for (Iterator iter = columns.iterator(); iter.hasNext();)
    {
      Column column = (Column)iter.next();
      
      String value = ExportViewUtils.parseCSV(column.getCellDisplay());
      if (!isFirstColumn) {
        this.rowBuffer.append(this.delimiter);
      }
      this.rowBuffer.append(value);
      isFirstColumn = false;
    }
    if (columns.size() > 0)
    {
      this.rowBuffer.append("\r\n");
      writeToOutputStream(this.rowBuffer.toString());
    }
    ThreadStore.put("beforeBody_out", this.out);
  }
  
  public void body(TableModel model, Column column)
  {
    String value = ExportViewUtils.parseCSV(column.getCellDisplay());
    this.rowBuffer.append(value);
    if (column.isLastColumn())
    {
      this.rowBuffer.append("\r\n");
      writeToOutputStream(this.rowBuffer.toString());
    }
    else
    {
      this.rowBuffer.append(this.delimiter);
    }
  }
  
  public Object afterBody(TableModel model)
  {
    if ((ThreadStore.get("exportAll") == null) || (ThreadStore.get("reloadExport") == null))
    {
      totals(model);
      this.out.flush();
      this.out.close();
    }
    return this.outputStreamOut;
  }
  
  public void totals(TableModel model)
  {
    this.rowBuffer = new StringBuffer();
    
    Column firstCalcColumn = model.getColumnHandler().getFirstCalcColumn();
    if (firstCalcColumn != null)
    {
      int rows = firstCalcColumn.getCalc().length;
      for (int i = 0; i < rows; i++)
      {
        for (Iterator iter = model.getColumnHandler().getColumns().iterator(); iter.hasNext();)
        {
          Column column = (Column)iter.next();
          if (column.isFirstColumn())
          {
            String calcTitle = CalcUtils.getFirstCalcColumnTitleByPosition(model, i);
            this.rowBuffer.append(ExportViewUtils.parseCSV(calcTitle));
          }
          else
          {
            this.rowBuffer.append(this.delimiter);
            if (column.isCalculated())
            {
              CalcResult calcResult = CalcUtils.getCalcResultsByPosition(model, column, i);
              Number value = calcResult.getValue();
              if (value != null)
              {
                if (StringUtils.isNotBlank(column.getFormat())) {
                  this.rowBuffer.append(ExportViewUtils.parseCSV(ExtremeUtils.formatNumber(column.getFormat(), value, model.getLocale())));
                } else {
                  this.rowBuffer.append(ExportViewUtils.parseCSV(value.toString()));
                }
              }
              else {
                this.rowBuffer.append(ExportViewUtils.parseCSV(""));
              }
            }
            else
            {
              this.rowBuffer.append(ExportViewUtils.parseCSV(""));
            }
          }
        }
        this.rowBuffer.append("\r\n");
        writeToOutputStream(this.rowBuffer.toString());
      }
    }
  }
  
  public void writeToOutputStream(String rowContent)
  {
    this.out.print(rowContent);
    this.rowBuffer = new StringBuffer();
  }
}
