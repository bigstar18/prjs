package gnnt.trade.bank.vo;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 银行间资金划转流水对象
 * @author Administrator
 *
 */
public class BankTransferValue  implements Serializable {
	
	private static final long serialVersionUID = 1L;

	/**
	 * 记录流水号
	 */
	public long id;
	
	/**
	 * 市场业务流水号
	 */
	public long actionId;
	
	/**
	 * 银行业务流水号
	 */
	public String funId;
	
	/**
	 * 付款银行代码
	 */
	public String payBankId;
	
	/**
	 * 付款银行名称
	 */
	public String payBankName;
	
	/**
	 * 收款银行代码
	 */
	public String recBankId;
	
	/**
	 * 收款银行名称
	 */
	public String recBankName;
	
	/**
	 * 付款科目
	 */
	public String payAcc;
	
	/**
	 * 付款科目名称
	 */
	public String payAccName;
	
	/**
	 * 收款科目
	 */
	public String recAcc;
	
	/**
	 * 收款科目名称
	 */
	public String recAccName;
	
	/**
	 * 收款交易商代码
	 */
	public String recFirmId;
	
	/**
	 * 收款交易商名称
	 */
	public String recFirmName;
	
	/**
	 * 金额
	 */
	public double money;
	
	/**
	 * 创建时间
	 */
	public Timestamp createTime;
	
	/**
	 * 更新时间
	 */
	public Timestamp updateTime;
	
	/**
	 * 状态：0：成功 1：失败 2：待审核 3:待银行处理结果 4:审核拒绝
	 */
	public int status;
	
	/**
	 * 附言
	 */
	public String note;
	
	/**
	 * 出入金银行端扣除手续费时对应的业务流水号
	 */
	public long capitalActionId;
	
	/**
	 * 类型：0为不确定 1为出金手续费 2为利息入金 3市场出金 4为挂账匹配 5为挂账出金 6为银行间轧差划转
	 */
	public int type;
	
	/**
	 * 备注（操作员）
	 */
	public String info;
	
	
	/*----------工行跨行汇拨 zjj start 2012.09.27--------*/
	/**银行账号*/
	public String account;
	/**账号名称*/
	public String Name;
	/**有效日期*/
	public String VldDt;
	/**密码*/
	public String Pwd;
	/**开户日期*/
	public String RegDt;
	/**银行账号状态*/
	public int St;
	/**银行账户类型*/
	public int accountType;
	/**业务开通标识*/
	public String flow;
	/**银行号*/
	public String bankNum;
	/**
	 * 交易类型
	 */
    public String transType;
	/*----------工行跨行汇拨 end 2012.09.27--------*/
}
