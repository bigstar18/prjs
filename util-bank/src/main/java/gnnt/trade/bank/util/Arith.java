package gnnt.trade.bank.util;


import java.math.BigDecimal;

/**
 * <p>
 * Title: 本工具类提供精确浮点数计算。
 * </p>
 * 
 * <p>
 * Description: 计算结果四舍五入保留两位小数
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * 
 * <p>
 * Company: GNNT
 * </p>
 * 
 * @author Lijiexiu
 * @version 1.0
 */

public class Arith {
    // 默认运算精度
    private static final int DEF_DIV_SCALE = 10;

    // 结果精度
    private static final int RESULT_DIV_SCALE = 10;

    private Arith() {
    }

    private static BigDecimal result(BigDecimal b) {
        return b.setScale(RESULT_DIV_SCALE, BigDecimal.ROUND_HALF_UP);
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

    public static void main(String[] args) {
        float v1 = 1800.01f;
        double v2 = 153846;
        float v3 = 153846f;
        v1 =(float) v2;
        System.out.println(v1+" "+v2+" "+v3);
        System.out.println(Float.MAX_VALUE+" "+Float.MIN_VALUE);
    }
}
