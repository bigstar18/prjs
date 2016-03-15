package cn.com.agree.eteller.generic.utils;

import java.io.File;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExportExcel extends HibernateDaoSupport {
	public static final Log logger = LogFactory.getLog(ExportExcel.class);
	private String reportTitle;
	private String reportFilePath;
	private String columnNames;
	private String subColumnhead;
	private String columns;
	private String TotalColumns;
	private String reportHeader;
	private String reportFooter;
	private WritableWorkbook workbook;
	private WritableSheet sheet;
	private int curRowIndex = 0;
	private HashMap map;
	private HashMap map_value;
	private int rowCount = 0;
	private int sheetIndex = 0;
	private int sheetPageSize = 60000;
	private static final int GET_PAGE_SIZE = 2000;
	private boolean addXh = false;
	private long xh = 1L;

	public ExportExcel(String columnNames, String columns, String totalColumns, String reportFilePath, String reportTitle) {
		this.columnNames = columnNames;
		this.columns = columns;
		this.TotalColumns = totalColumns;
		this.reportFilePath = reportFilePath;
		this.reportTitle = reportTitle;
	}

	public boolean exportDataToExcel(Object objDao, String sql) {
		SessionFactory sessionFactory = null;
		Session session = null;
		Query query = null;
		List list = null;

		int pageIndex = 0;
		try {
			Class objclass = objDao.getClass();
			Method method = objclass.getMethod("getHibernateTemplate", null);
			HibernateTemplate template = (HibernateTemplate) method.invoke(objDao, null);
			sessionFactory = template.getSessionFactory();
			session = sessionFactory.openSession();

			query = session.createQuery(sql);
			if (!createExcel()) {
				return false;
			}
			for (;;) {
				query.setFirstResult(pageIndex * 2000);
				query.setMaxResults(2000);

				list = query.list();
				if (list.size() == 0) {
					break;
				}
				writeExcel(list);

				pageIndex++;
			}
			closeExcel();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		HibernateTemplate template;
		return true;
	}

	public boolean createExcel() {
		try {
			if (logger.isInfoEnabled()) {
				logger.info("setSheetHead() " + this.reportTitle);
			}
			this.workbook = Workbook.createWorkbook(new File(this.reportFilePath));
			createNewSheet();

			this.map = new HashMap();
			this.map_value = new HashMap();
			if ((this.TotalColumns != null) && (!this.TotalColumns.equals(""))) {
				String[] TotalColumn = this.TotalColumns.split("\\Q;\\E");
				BigDecimal[] totalAmount = new BigDecimal[TotalColumn.length];
				for (int n = 0; n < TotalColumn.length; n++) {
					String[] tmp = TotalColumn[n].split("\\Q.\\E");
					this.map.put(tmp[0], tmp[1]);
					totalAmount[n] = new BigDecimal("0");
					this.map_value.put(tmp[0], totalAmount[n]);
				}
			}
		} catch (Exception ex) {
			try {
				this.workbook.close();
			} catch (Exception e) {
				e.printStackTrace();
				if (logger.isInfoEnabled()) {
					logger.info("【" + this.reportTitle + "】关闭excel工作簿出错，错误信息：" + e.getMessage());
				}
				return false;
			}
			ex.printStackTrace();
			if (logger.isInfoEnabled()) {
				logger.info("setSheetHead()" + this.reportTitle + "出错,错误信息:" + ex.getMessage());
			}
			return false;
		}
		return true;
	}

	private boolean createNewSheet() {
		try {
			String[] strArr = this.reportTitle.split(",");
			if (strArr.length == 5) {
				this.sheet = this.workbook.createSheet(strArr[4] + (this.sheetIndex + 1), this.sheetIndex);
			} else {
				this.sheet = this.workbook.createSheet(strArr[2] + (this.sheetIndex + 1), this.sheetIndex);
			}
			this.sheetIndex += 1;
			this.curRowIndex = 0;
			this.rowCount = 0;
			Label labeltmp;
			if (strArr.length == 5) {
				WritableCellFormat format = new WritableCellFormat();
				format.setAlignment(Alignment.CENTRE);
				format.setVerticalAlignment(VerticalAlignment.CENTRE);

				this.sheet.mergeCells(Integer.parseInt(strArr[1]), Integer.parseInt(strArr[0]), Integer.parseInt(strArr[3]),
						Integer.parseInt(strArr[2]));
				labeltmp = new Label(Integer.parseInt(strArr[1]), Integer.parseInt(strArr[0]), strArr[4]);
				labeltmp.setCellFormat(format);

				this.sheet.addCell(labeltmp);
			} else {
				labeltmp = new Label(Integer.parseInt(strArr[1]), Integer.parseInt(strArr[0]), strArr[2]);

				this.sheet.addCell(labeltmp);
			}
			if ((getReportHeader() != null) && (!getReportHeader().equals(""))) {
				String[] headers = getReportHeader().split("\\Q;\\E");
				for (int i = 0; i < headers.length; i++) {
					strArr = headers[i].split(",");
					int rowNo = Integer.parseInt(strArr[0]);
					int colNo = Integer.parseInt(strArr[1]);
					if (rowNo >= this.curRowIndex) {
						this.curRowIndex = rowNo;
					}
					labeltmp = new Label(colNo, rowNo, strArr[2]);

					this.sheet.addCell(labeltmp);
				}
			}
			this.curRowIndex += 2;
			if (getSubColumnhead() != null) {
				String[] subCols = this.subColumnhead.split("\\Q;\\E");
				for (int i = 0; i < subCols.length; i++) {
					strArr = subCols[i].split("\\Q,\\E");

					int x1 = Integer.parseInt(strArr[1]);
					int y1 = Integer.parseInt(strArr[0]);
					int x2 = Integer.parseInt(strArr[3]);
					int y2 = Integer.parseInt(strArr[2]);

					WritableCellFormat format = new WritableCellFormat();
					format.setAlignment(Alignment.CENTRE);
					format.setVerticalAlignment(VerticalAlignment.CENTRE);
					format.setBorder(Border.ALL, BorderLineStyle.THIN);
					if (y1 > this.curRowIndex) {
						this.curRowIndex = y1;
					}
					if (y2 > this.curRowIndex) {
						this.curRowIndex = y2;
					}
					this.sheet.mergeCells(x1, y1, x2, y2);
					labeltmp = new Label(x1, y1, strArr[4]);
					labeltmp.setCellFormat(format);
					this.sheet.addCell(labeltmp);
				}
				this.curRowIndex += 1;
			}
			int colIndex = 0;
			String[] columnName = this.columnNames.split("\\Q;\\E");
			if (isAddXh()) {
				labeltmp = new Label(colIndex, this.curRowIndex, "序号");

				WritableCellFormat format = new WritableCellFormat();
				format.setBorder(Border.ALL, BorderLineStyle.THIN);
				labeltmp.setCellFormat(format);

				this.sheet.addCell(labeltmp);
				colIndex++;
			}
			for (int i = 0; i < columnName.length; i++) {
				WritableCellFormat format = new WritableCellFormat();
				format.setAlignment(Alignment.CENTRE);
				format.setVerticalAlignment(VerticalAlignment.CENTRE);
				format.setBorder(Border.ALL, BorderLineStyle.THIN);

				strArr = columnName[i].split("\\Q.\\E");
				if (strArr.length == 1) {
					labeltmp = new Label(colIndex, this.curRowIndex, strArr[0]);
					labeltmp.setCellFormat(format);
					this.sheet.addCell(labeltmp);
					colIndex++;
				} else {
					int n = Integer.parseInt(strArr[1]) - 1;
					this.sheet.mergeCells(colIndex, this.curRowIndex, colIndex + n, this.curRowIndex);
					labeltmp = new Label(colIndex, this.curRowIndex, strArr[0]);
					labeltmp.setCellFormat(format);
					this.sheet.addCell(labeltmp);
					colIndex = colIndex + 1 + n;
				}
			}
			this.curRowIndex += 1;
		} catch (Exception ex) {
			try {
				this.workbook.close();
			} catch (Exception e) {
				e.printStackTrace();
				if (logger.isInfoEnabled()) {
					logger.info("【" + this.reportTitle + "】关闭excel工作簿出错，错误信息：" + e.getMessage());
				}
				return false;
			}
			ex.printStackTrace();
			if (logger.isInfoEnabled()) {
				logger.info("createNewSheet()" + this.reportTitle + "出错,错误信息:" + ex.getMessage());
			}
			return false;
		}
		return true;
	}

	public boolean writeExcel(List list) {
		if (logger.isInfoEnabled()) {
			logger.info("run() 正在进行导出【" + this.reportTitle + "】excel 工作");
		}
		try {
			String[] column = this.columns.split("\\Q;\\E");
			int stratIndex = this.curRowIndex;
			int n = 0;
			for (int i = stratIndex; i < stratIndex + list.size(); i++) {
				Object objtmp = list.get(i - stratIndex);
				Class objclass = objtmp.getClass();
				if (isAddXh()) {
					Label label_value = new Label(0, this.curRowIndex, String.valueOf(this.xh));

					WritableCellFormat format = new WritableCellFormat();
					format.setBorder(Border.ALL, BorderLineStyle.THIN);
					label_value.setCellFormat(format);

					this.sheet.addCell(label_value);
					n = 1;
					this.xh += 1L;
				}
				for (int m = 0; m < column.length; m++) {
					String string = column[m];
					String str2 = string.substring(1);
					String str3 = string.substring(0, 1);
					str3 = str3.toUpperCase();
					String methodname = "get" + str3 + str2;
					Method method = objclass.getMethod(methodname, null);
					Object object = method.invoke(objtmp, null);
					String value;
					if (object != null) {
						value = object.toString();
					} else {
						value = "";
					}
					Label label_value = new Label(m + n, this.curRowIndex, value);

					WritableCellFormat format = new WritableCellFormat();
					format.setBorder(Border.ALL, BorderLineStyle.THIN);
					label_value.setCellFormat(format);

					this.sheet.addCell(label_value);
					if ((this.map.size() != 0) && (this.map.containsKey(string))) {
						BigDecimal BigDectmp = (BigDecimal) this.map_value.get(string);
						BigDectmp = BigDectmp.add(new BigDecimal(value.replaceAll("\\Q,\\E", "")));
						this.map_value.put(string, BigDectmp);
					}
				}
				this.rowCount += 1;
				this.curRowIndex += 1;
				if (this.rowCount >= this.sheetPageSize) {
					createNewSheet();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (logger.isInfoEnabled()) {
				logger.info("【" + this.reportTitle + "】生成 excel 工作出错1，错误信息：" + e.getMessage());
			}
			try {
				this.workbook.close();
			} catch (Exception ex) {
				ex.printStackTrace();
				if (logger.isInfoEnabled()) {
					logger.info("【" + this.reportTitle + "】关闭 excel 工作簿出错，错误信息：" + ex.getMessage());
				}
				return false;
			}
		}
		if (logger.isInfoEnabled()) {
			logger.info("run【" + this.reportTitle + "】excel 退出......");
		}
		return true;
	}

	public void closeExcel() {
		if (logger.isInfoEnabled()) {
			logger.info("closeExcel() " + this.reportTitle);
		}
		try {
			this.curRowIndex += 2;
			if (this.map.size() != 0) {
				Label label_value_total = new Label(0, this.curRowIndex, "汇总:");

				WritableCellFormat format = new WritableCellFormat();
				format.setBorder(Border.ALL, BorderLineStyle.THIN);
				label_value_total.setCellFormat(format);

				this.sheet.addCell(label_value_total);

				Set st = this.map.keySet();
				for (Iterator itr = st.iterator(); itr.hasNext();) {
					String str = (String) itr.next();

					int colNo = Integer.parseInt(this.map.get(str).toString());
					if (isAddXh()) {
						colNo++;
					}
					String colvalue = String.valueOf((BigDecimal) this.map_value.get(str));
					Label labe2_value_total = new Label(colNo, this.curRowIndex, ComFunction.formatNumber(colvalue));

					labe2_value_total.setCellFormat(format);

					this.sheet.addCell(labe2_value_total);
				}
			}
			this.curRowIndex += 2;
			if ((getReportFooter() != null) && (!getReportFooter().equals(""))) {
				String[] footers = getReportFooter().split("\\Q;\\E");
				for (int i = 0; i < footers.length; i++) {
					String[] strArr = footers[i].split(",");
					int rowNo = Integer.parseInt(strArr[0]);
					int colNo = Integer.parseInt(strArr[1]);
					Label labeltmp = new Label(colNo, this.curRowIndex + rowNo, strArr[2]);

					WritableCellFormat format = new WritableCellFormat();
					format.setBorder(Border.ALL, BorderLineStyle.THIN);
					labeltmp.setCellFormat(format);

					this.sheet.addCell(labeltmp);
				}
			}
			this.workbook.write();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info("closeExcel() " + this.reportTitle + "出错,错误信息:" + ex.getMessage());
			try {
				this.workbook.close();
			} catch (Exception e) {
				e.printStackTrace();
				if (!logger.isInfoEnabled()) {
				}
			}
		} finally {
			try {
				this.workbook.close();
			} catch (Exception e) {
				e.printStackTrace();
				if (logger.isInfoEnabled()) {
					logger.info("【" + this.reportTitle + "】exexl关闭工作簿出错，错误信息：" + e.getMessage());
				}
			}
		}
	}

	public String getReportFooter() {
		return this.reportFooter;
	}

	public void setReportFooter(String reportFooter) {
		this.reportFooter = reportFooter;
	}

	public String getReportHeader() {
		return this.reportHeader;
	}

	public void setReportHeader(String reportHeader) {
		this.reportHeader = reportHeader;
	}

	public String getSubColumnhead() {
		return this.subColumnhead;
	}

	public void setSubColumnhead(String subColumnhead) {
		this.subColumnhead = subColumnhead;
	}

	public int getSheetPageSize() {
		return this.sheetPageSize;
	}

	public void setSheetPageSize(int sheetPageSize) {
		if (sheetPageSize <= 0) {
			return;
		}
		if (sheetPageSize >= 60000) {
			sheetPageSize = 60000;
		}
		this.sheetPageSize = sheetPageSize;
	}

	public boolean isAddXh() {
		return this.addXh;
	}

	public void setAddXh(boolean addXh) {
		this.addXh = addXh;
	}
}
