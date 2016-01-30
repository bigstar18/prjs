package gnnt.MEBS.finance.front.model;

import gnnt.MEBS.common.front.model.StandardModel;
import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
import gnnt.MEBS.common.front.model.translate.ClassDiscription;
import java.util.Date;

public class ClientLedger extends StandardModel {
	private static final long serialVersionUID = 487654776733350247L;

	@ClassDiscription(name = "结算日期", description = "")
	private Date b_Date;

	@ClassDiscription(name = "交易商", description = "")
	private String firmId;

	@ClassDiscription(name = "总账字段代码", description = "")
	private String code;

	@ClassDiscription(name = "字段值", description = "")
	private Double value;
	private LedgerField ledgerField;

	public Date getB_Date() {
		return this.b_Date;
	}

	public void setB_Date(Date paramDate) {
		this.b_Date = paramDate;
	}

	public String getFirmId() {
		return this.firmId;
	}

	public void setFirmId(String paramString) {
		this.firmId = paramString;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String paramString) {
		this.code = paramString;
	}

	public Double getValue() {
		return this.value;
	}

	public void setValue(Double paramDouble) {
		this.value = paramDouble;
	}

	public LedgerField getLedgerField() {
		return this.ledgerField;
	}

	public void setLedgerField(LedgerField paramLedgerField) {
		this.ledgerField = paramLedgerField;
	}

	public StandardModel.PrimaryInfo fetchPKey() {
		return null;
	}
}