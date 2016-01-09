/*    */ package gnnt.MEBS.bank.front.core;

/*    */
/*    */ import gnnt.trade.bank.util.ErrorCode;
/*    */ import java.util.Hashtable;

/*    */
/*    */ public class BankCoreCode
/*    */ {
	/*    */ private static ErrorCode ec;

	/*    */
	/*    */ public static String getCode(long code)
	/*    */ {
		/* 25 */ if (ec == null) {
			/* 26 */ synchronized (BankCoreCode.class) {
				/* 27 */ if (ec == null) {
					/* 28 */ ec = new ErrorCode();
					/* 29 */ ec.load();
					/*    */ }
				/*    */ }
			/*    */ }
		/* 33 */ String result = null;
		/* 34 */ if (ec != null) {
			/* 35 */ result = (String) ErrorCode.error.get(Long.valueOf(code));
			/*    */ }
		/* 37 */ if ((result == null) || (result.trim().length() <= 0)) {
			/* 38 */ result = "银行核心返回错误码：" + code;
			/*    */ }
		/* 40 */ return result;
		/*    */ }
	/*    */ }