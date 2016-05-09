 package gnnt.MEBS.bank.front.model;


 import gnnt.MEBS.common.front.model.StandardModel;
 import gnnt.MEBS.common.front.model.StandardModel.PrimaryInfo;
 import gnnt.MEBS.common.front.model.translate.ClassDiscription;


 public class TransMoneyObj extends StandardModel
 {
	 private static final long serialVersionUID = 3621463934608097984L;
	
	 @ClassDiscription(name = "序号 ", description = "")
	 private Integer id;
	
	 @ClassDiscription(name = "业务实现类", description = "")
	 private String className;
	
	 @ClassDiscription(name = "备注", description = "")
	 private String note;

	
	 public Integer getId()
	 {
		/* 44 */ return this.id;
		 }

	
	 public void setId(Integer id)
	 {
		/* 54 */ this.id = id;
		 }

	
	 public String getClassName()
	 {
		/* 64 */ return this.className;
		 }

	
	 public void setClassName(String className)
	 {
		/* 74 */ this.className = className;
		 }

	
	 public String getNote()
	 {
		/* 84 */ return this.note;
		 }

	
	 public void setNote(String note)
	 {
		/* 94 */ this.note = note;
		 }

	
	 public StandardModel.PrimaryInfo fetchPKey()
	 {
		/* 99 */ return new StandardModel.PrimaryInfo("id", this.id);
		 }
	 }