/*    */ package gnnt.MEBS.bank.front.statictools;

/*    */
/*    */ public class Util
/*    */ {
	/*    */ public static boolean isContentsBank(String bankIDs, String bankID)
	/*    */ {
		/* 27 */ if ((bankIDs == null) || (bankID == null)) {
			/* 28 */ return false;
			/*    */ }
		/* 30 */ if (bankIDs.trim().equals(bankID.trim())) {
			/* 31 */ return true;
			/*    */ }
		/* 33 */ if (bankIDs.trim().startsWith(bankID.trim() + ",")) {
			/* 34 */ return true;
			/*    */ }
		/* 36 */ if (bankIDs.trim().endsWith("," + bankID.trim())) {
			/* 37 */ return true;
			/*    */ }
		/* 39 */ if (bankIDs.contains("," + bankID.trim() + ",")) {
			/* 40 */ return true;
			/*    */ }
		/* 42 */ return false;
		/*    */ }
	/*    */ }