package gnnt.MEBS.timebargain.server.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 成交信息
 *
 */
public class TradeInfo implements Serializable
{
	private static final long serialVersionUID = 3690197650654049823L;
	public long seqNum;

    /**
     *<TR_N>成交号</TR_N>
     */
    public Long A_TradeNo;
    
    /**
     * <A_TR_N>市场成交号</A_TR_N>
     */
    public Long M_TradeNo;
    
    /**
     * <OR_N>委托单号</OR_N>
     */
    public Long A_OrderNo;
    
    /**
     * <S_TR_N>被平仓成交单号</S_TR_N>
     */
    public Long A_TradeNo_Closed;
    
    /**
     * <TI>成交时间</TI>
     */
    public Date TradeTime;
    
    /**
     * <CU_I>交易用户ID </CU_I>
     */
    public String CustomerID;
    
    /**
     * <CO_I>商品代码</CO_I>
     */
    public String CommodityID;
    
    /**
     * <TY>买卖标志：1买；2卖</TY>
     */
    public Short BS_Flag;  
    
    /**
     * <SE_F>委托类型：1开仓；2平仓</SE_F>
     */
    public Short OrderType;
    
    /**
     * <FI_I>交易商ID</FI_I>
     */
    public String FirmID;
    
    /**
     * <PR>成交价格</PR>
     */
    public Double Price;
    
    /**
     * <QTY>成交数量</QTY>
     */
    public Long Quantity; 
    
    /**
     * <LIQPL>平仓盈亏</LIQPL>
     */
    public Double Close_PL;   
    
    /**
     * <COMM>交易手续费</COMM>
     */
    public Double TradeFee;    

    /**
     * <O_PR>转让价</O_PR>
     */
    public Double HoldPrice;
    
    /**
     *<TR_T>
	 *	成交类型：1正常交易（开仓，平仓）
	 *	2代理系统强平
	 *	3交易市场强平
	 *	4委托交易（开仓，平仓）
	 * </TR_T>	
     */
    public Integer TradeType;
    
    /**
     * 开仓时间
     */
    public Date HoldTime;
}
