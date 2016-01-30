package gnnt.MEBS.bill.core.po;

public abstract class Clone implements java.lang.Cloneable {
	public Object clone() {
		Object o = null;
		try {
			o = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return o;
	}
}
