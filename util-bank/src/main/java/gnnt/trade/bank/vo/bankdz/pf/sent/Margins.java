package gnnt.trade.bank.vo.bankdz.pf.sent;
import java.util.Date;
/**冻结解冻*/
public class Margins {
    /** ★业务流水号*/
    public String Serial_Id;
    /** ★成交合同号*/
    public String Bargain_Code;
    /** ★交易类型  1 冻结，2 解冻*/
    public String Trade_Type;
    /** ★交易金额*/
    public double Trade_Money;
    /** ★交易会员号*/
    public String Member_Code;
    /** 交易会员名称*/
    public String Member_Name;
    /** ★交易日期*/
    public Date Trade_Date;
    /** 货物编号*/
    public String Good_Code;
    /** 货物名称*/
    public String Good_Name;
    /** 货物数量*/
    public int Good_Quantity;
    /** 状态 N未发送 F银行处理失败 Y银行处理成功**/
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
    	sb.append("业务流水号[Serial_Id]("+Serial_Id+")"+str);
    	sb.append("成交合同号[Bargain_Code]("+Bargain_Code+")"+str);
    	sb.append("交易类型[Trade_Type]("+Trade_Type+")"+str);
    	sb.append("交易金额[Trade_Money]("+Trade_Money+")"+str);
    	sb.append("交易会员号[Member_Code]("+Member_Code+")"+str);
    	sb.append("交易会员名称[Member_Name]("+Member_Name+")"+str);
    	sb.append("交易日期[Trade_Date]("+Trade_Date+")"+str);
    	sb.append("货物编号[Good_Code]("+Good_Code+")"+str);
    	sb.append("货物名称[Good_Name]("+Good_Name+")"+str);
    	sb.append("货物数量[Good_Quantity]("+Good_Quantity+")"+str);
    	sb.append("状态[flag]("+flag+")"+str);
    	sb.append("备注信息[note]("+note+")"+str);
    	return sb.toString();
    }
}
