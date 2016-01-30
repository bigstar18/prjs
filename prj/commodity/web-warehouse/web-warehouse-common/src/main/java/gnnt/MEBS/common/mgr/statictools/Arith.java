package gnnt.MEBS.common.mgr.statictools;

import java.math.BigDecimal;

/**
 * 精确浮点数计算工具类.
 *
 * <p><a href="Arith.java.html"><i>View Source</i></a></p>
 *
 * @version 1.0.0.1
 * @author <a href="mailto:chenxc@gnnt.com.cn">chenxc</a>
 */
public class Arith {
    // 默认运算精度
    private static final int DEF_DIV_SCALE = 10;
    
  //  private static final long DEF_MUL_SCALE = 10000000000L;

    // 结果精度
    private static final int RESULT_DIV_SCALE = 10;

    private Arith() {
    }

    private static BigDecimal result(BigDecimal b) {
        return b.setScale(RESULT_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
    }
    
    /**
     * 是否整除, 当价格和最小变动价位为小数时，按照10位小数精度进行整除判断
     * @param d1
     * @param d2
     * @return
     */
    public static boolean divideExactly(double d1, double d2){
    	double v1 = format(d1,DEF_DIV_SCALE);
    	double v2 = format(d2,DEF_DIV_SCALE);
    	return (long)(div(v1,v2))== div(v1,v2);
    }
    
    /**
     * 舍弃价格小数位10位以后的部分
     * @param d1
     * @return
     */
    public static double priceFormat(double d1){
    	return format(d1,DEF_DIV_SCALE);
    }
    
    /**
     * 加法运算。
     * 
     * @param v1
     *            double
     * @param v2
     *            double
     * @return double
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        return result(b1.add(b2)).doubleValue();
    }

    /**
     * 加法运算。
     * 
     * @param v1
     *            float
     * @param v2
     *            float
     * @return double
     */
    public static double add(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        BigDecimal b2 = new BigDecimal(Float.toString(v2)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        return result(b1.add(b2)).doubleValue();
    }

    /**
     * 加法运算。
     * 
     * @param v1
     *            float
     * @param v2
     *            double
     * @return double
     */
    public static double add(float v1, double v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        return result(b1.add(b2)).doubleValue();
    }

    /**
     * 加法运算。
     * 
     * @param v1
     *            double
     * @param v2
     *            float
     * @return double
     */
    public static double add(double v1, float v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        BigDecimal b2 = new BigDecimal(Float.toString(v2)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        return result(b1.add(b2)).doubleValue();
    }

    /**
     * 减法运算。
     * 
     * @param v1
     *            double
     * @param v2
     *            double
     * @return double
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        return result(b1.subtract(b2)).doubleValue();
    }

    /**
     * 减法运算。
     * 
     * @param v1
     *            float
     * @param v2
     *            float
     * @return double
     */
    public static double sub(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        BigDecimal b2 = new BigDecimal(Float.toString(v2)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        return result(b1.subtract(b2)).doubleValue();
    }

    /**
     * 减法运算。
     * 
     * @param v1
     *            float
     * @param v2
     *            double
     * @return double
     */
    public static double sub(float v1, double v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        return result(b1.subtract(b2)).doubleValue();
    }

    /**
     * 减法运算。
     * 
     * @param v1
     *            double
     * @param v2
     *            float
     * @return double
     */
    public static double sub(double v1, float v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        BigDecimal b2 = new BigDecimal(Float.toString(v2)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        return result(b1.subtract(b2)).doubleValue();
    }

    /**
     * 乘法运算。
     * 
     * @param v1
     *            double
     * @param v2
     *            double
     * @return double
     */
    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);

        return result(result(b1.multiply(b2))).doubleValue();
    }

    /**
     * 乘法运算。
     * 
     * @param v1
     *            float
     * @param v2
     *            float
     * @return double
     */
    public static double mul(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        BigDecimal b2 = new BigDecimal(Float.toString(v2)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);

        return result(result(b1.multiply(b2))).doubleValue();
    }

    /**
     * 乘法运算。
     * 
     * @param v1
     *            float
     * @param v2
     *            double
     * @return double
     */
    public static double mul(float v1, double v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);

        return result(result(b1.multiply(b2))).doubleValue();
    }

    /**
     * 乘法运算。
     * 
     * @param v1
     *            double
     * @param v2
     *            float
     * @return double
     */
    public static double mul(double v1, float v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        BigDecimal b2 = new BigDecimal(Float.toString(v2)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);

        return result(result(b1.multiply(b2))).doubleValue();
    }

    /**
     * 除法运算。
     * 
     * @param v1
     * @param v2
     * @return double
     */
    public static double div(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        return result(b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP))
                .doubleValue();
    }

    /**
     * 除法运算。
     * 
     * @param v1
     * @param v2
     * @return double
     */
    public static double div(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        BigDecimal b2 = new BigDecimal(Float.toString(v2)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        return result(b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP))
                .doubleValue();
    }

    /**
     * 除法运算。
     * 
     * @param v1
     * @param v2
     * @return double
     */
    public static double div(float v1, double v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        return result(b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP))
                .doubleValue();
    }

    /**
     * 除法运算。
     * 
     * @param v1
     * @param v2
     * @return double
     */
    public static double div(double v1, float v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        BigDecimal b2 = new BigDecimal(Float.toString(v2)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        return result(b1.divide(b2, DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP))
                .doubleValue();
    }

    /**
     * 除法运算。
     * 
     * @param v1
     *            double
     * @param v2
     *            double
     * @param scale
     *            int
     * @param roundingMode
     *            int
     * @return double
     */
    public static double div(double v1, double v2, int scale, int roundingMode) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(
                DEF_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        return result(b1.divide(b2, scale, roundingMode)).doubleValue();
    }

    /**
     * 比较大小
     * 
     * @param v1
     * @param v2
     * @return 1：大于；0：等于；-1：小于
     */
    public static int compareTo(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(
                RESULT_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(
                RESULT_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        return b1.compareTo(b2);
    }

    /**
     * 比较大小
     * 
     * @param v1
     * @param v2
     * @return 1：大于；0：等于；-1：小于
     */
    public static int compareTo(float v1, float v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1)).setScale(
                RESULT_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        BigDecimal b2 = new BigDecimal(Float.toString(v2)).setScale(
                RESULT_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        return b1.compareTo(b2);
    }

    /**
     * 比较大小
     * 
     * @param v1
     * @param v2
     * @return 1：大于；0：等于；-1：小于
     */
    public static int compareTo(double v1, float v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1)).setScale(
                RESULT_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        BigDecimal b2 = new BigDecimal(Float.toString(v2)).setScale(
                RESULT_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        return b1.compareTo(b2);
    }

    /**
     * 比较大小
     * 
     * @param v1
     * @param v2
     * @return 1：大于；0：等于；-1：小于
     */
    public static int compareTo(float v1, double v2) {
        BigDecimal b1 = new BigDecimal(Float.toString(v1)).setScale(
                RESULT_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        BigDecimal b2 = new BigDecimal(Double.toString(v2)).setScale(
                RESULT_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
        return b1.compareTo(b2);
    }

    /**
     * 四舍五入格式化浮点数
     * 
     * @param v
     * @param scale
     *            小数位数
     * @return double
     */
    public static double format(double v, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(v)).setScale(scale,
                BigDecimal.ROUND_HALF_UP);
        return b1.doubleValue();
    }
    /**
     * 不四舍五入直接截断格式化浮点数
     * 
     * @param v
     * @param scale
     *            小数位数
     * @return double
     */
    public static double formatD(double v, int scale) {
        BigDecimal b1 = new BigDecimal(Double.toString(v)).setScale(scale,
                BigDecimal.ROUND_DOWN);  //by cxc 2009-07-28由BigDecimal.ROUND_HALF_DOWN改成BigDecimal.ROUND_DOWN实现对小数不进行四舍五入而直接截断
        return b1.doubleValue();
    }
    
    /**
     * 四舍五入格式化浮点数
     * 
     * @param v
     * @param scale
     *            小数位数
     * @return double
     */
    public static double format(float v, int scale) {
        BigDecimal b1 = new BigDecimal(Float.toString(v)).setScale(scale,
                BigDecimal.ROUND_HALF_UP);
        return b1.doubleValue();
    }
    /**
     * 不四舍五入直接截断格式化浮点数
     * 
     * @param v
     * @param scale
     *            小数位数
     * @return double
     */
    public static double formatD(float v, int scale) {
        BigDecimal b1 = new BigDecimal(Float.toString(v)).setScale(scale,
                BigDecimal.ROUND_DOWN);  //by cxc 2009-07-28由BigDecimal.ROUND_HALF_DOWN改成BigDecimal.ROUND_DOWN实现对小数不进行四舍五入而直接截断
        return b1.doubleValue();
    }

    /**
     * 获取小数位数
     * @param data
     * @return
     */
	public static int getDecimalDigits(double data)
	{
		int num = 0;
		String strData = String.valueOf(data);
		BigDecimal bd = new BigDecimal(strData); 
		num = bd.scale();
		if(num == 1)
		{
			if(strData.indexOf(".0") != -1)
			{
				num = 0;
			}
		}
		return num;
	} 
	
    public static void main(String[] args) {
    	/*
        float v1 = 1800.01f;
        double v2 = 153846;
        float v3 = 153846f;
        v1 =(float) v2;
        double d1 = 1800.02;
        double d2 = 0.07;
        System.out.println(v1+" "+v2+" "+v3);
        System.out.println(Float.MAX_VALUE+" "+Float.MIN_VALUE);
        System.out.println(d1*d2);
        System.out.println(d1+d2);
        long q = 10l;
		long cf = 100l;
		double p = 1200.5d;
		double mr = 0.07;
		System.out.println(Arith.format(q*cf*p*mr,4));
		System.out.println(q*cf*(Arith.mul(p,mr)));
		System.out.println(Arith.div(1d,3d));
		System.out.println(Arith.format(1973.89,0));
		*/
//		System.out.println(Arith.formatD(-1973.599,1));
//		System.out.println(Arith.formatD(-1973.450,1));
//		System.out.println(Arith.formatD(-1973.549,1));
//		
//		System.out.println(Arith.format(1973.5,0));
//		System.out.println(Arith.format(1973.4,0));
//		System.out.println(Arith.format(1973.6,0));
//		
//		System.out.println(Math.ceil(1973.01));
//		System.out.println(Math.floor((1973.99)));
		System.out.println(format(1200.0005525,3));
		
		/*
    	String str = "<RECORD><T_SECTION></T_SECTION><TRADER_NO>306</TRADER_NO><ORDER_NO>2933</ORDER_NO><TIME>2008-05-23 09:48:44</TIME><ORDER_TIME></ORDER_TIME><TYPE>2</TYPE><SETTLE_FLAG>2</SETTLE_FLAG><BROKER_REF></BROKER_REF><TRADER_ID></TRADER_ID><FIRM_ID></FIRM_ID><CUSTOMER_ID>1009</CUSTOMER_ID><COMMODITY_ID>01NG0809</COMMODITY_ID><PRICE>1931</PRICE><QTY>1</QTY><O_PRICE>2281</O_PRICE><LIQPL>-350</LIQPL><COMM>1</COMM><S_TRADER_NO>42339</S_TRADER_NO><S_DATE></S_DATE><S_TIME></S_TIME><S_BROKER_REF></S_BROKER_REF><S_PRICE></S_PRICE><S_ORDER_NO></S_ORDER_NO><HEDGEFLAG></HEDGEFLAG><A_TRADENO>56839</A_TRADENO><TRADETYPE>2</TRADETYPE></RECORD>";
    	System.out.println(str.length());
    	
    	//System.out.println("ds****".replace('*', ' ').trim());
    	System.out.println("" + (int)(1010009/10));
    	*/
    }
}
