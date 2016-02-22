package gnnt.trade.bank.vo.bankdz.pf.sent;
import java.util.Date;
/**交易明细*/
public class TradeList {
	/** ★交易金额*/
    public double Trade_Money;
    /** ★交易类型(1 盈亏、货款等；2 交易手续费；3 交收手续费；4 浮亏；5 其他资金)*/
    public String Trade_Type;
    /** ★买方会员号*/
    public String B_Member_Code;
    /** 买方会员名称*/
    public String B_Member_Name;
    /** ★卖方会员号*/
    public String S_Member_Code;
    /** 卖方会员名称*/
    public String S_Member_Name;
    /** ★交易日期*/
    public Date Trade_Date;
    /** ★成交合同号*/
    public String Bargain_Code;
    /** ★成交流水号*/
    public String Serial_Id;
    /** 货物编号*/
    public String Good_Code;
    /** 货物名称*/
    public String Good_Name;
    /** 货物数量*/
    public int Good_Quantity;
    /** 状态 N未发送 F银行处理失败 Y银行处理成功*/
    public String flag;
    /**备注信息*/
    public String note;
    /**
     * 本类信息
     */
    public String toString(){
    	StringBuilder sb = new StringBuilder();
    	String str = "\n";
    	sb.append("---"+this.getClass().getName()+"---"+str);
    	sb.append("交易金额[Trade_Money]("+Trade_Money+")"+str);
    	sb.append("交易类型[Trade_Type]("+Trade_Type+")"+str);
    	sb.append("买方会员号[B_Member_Code]("+B_Member_Code+")"+str);
    	sb.append("买方会员名称[B_Member_Name]("+B_Member_Name+")"+str);
    	sb.append("卖方会员号[S_Member_Code]("+S_Member_Code+")"+str);
    	sb.append("卖方会员名称[S_Member_Name]("+S_Member_Name+")"+str);
    	sb.append("交易日期[Trade_Date]("+Trade_Date+")"+str);
    	sb.append("成交合同号[Bargain_Code]("+Bargain_Code+")"+str);
    	sb.append("成交流水号[Serial_Id]("+Serial_Id+")"+str);
    	sb.append("货物编号[Good_Code]("+Good_Code+")"+str);
    	sb.append("货物名称[Good_Name]("+Good_Name+")"+str);
    	sb.append("货物数量[Good_Quantity]("+Good_Quantity+")"+str);
    	sb.append("状态[flag]("+flag+")"+str);
    	sb.append("备注信息[note]("+note+")"+str);
    	return sb.toString();
    }
}
