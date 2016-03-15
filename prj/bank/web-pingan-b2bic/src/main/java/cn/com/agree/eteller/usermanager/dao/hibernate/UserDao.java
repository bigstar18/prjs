package cn.com.agree.eteller.usermanager.dao.hibernate;

import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import cn.com.agree.eteller.generic.utils.Base64Encoder;
import cn.com.agree.eteller.generic.utils.ConfigReaderTool;
import cn.com.agree.eteller.generic.vo.CommonDataNode;
import cn.com.agree.eteller.generic.vo.DataTree;
import cn.com.agree.eteller.generic.vo.LoginUser;
import cn.com.agree.eteller.usermanager.dao.IUserDao;
import cn.com.agree.eteller.usermanager.persistence.Appinfo;
import cn.com.agree.eteller.usermanager.persistence.EtellerSubappinfo;
import cn.com.agree.eteller.usermanager.persistence.Funclist;
import cn.com.agree.eteller.usermanager.persistence.Rolelist;
import cn.com.agree.eteller.usermanager.persistence.Userlist;

public class UserDao extends HibernateDaoSupport implements IUserDao {
	public void updateLoginUser(Object obj) {
		getHibernateTemplate().update(obj);
	}

	public Userlist[] getAllUser() {
		List ls = getHibernateTemplate().loadAll(Userlist.class);
		Userlist root = new Userlist();
		root.setUserId("root");
		ls.remove(root);
		return (Userlist[]) ls.toArray(new Userlist[0]);
	}

	public void login(LoginUser user) throws Exception {
		List list = getHibernateTemplate().find("from Userlist teller where teller.userId=? ", user.getUserId());
		if (list.isEmpty()) {
			if ((user.getUserId().equals("root")) && (user.getUserPwd().equals("21218cca77804d2ba1922c33e0151105"))) {
				Userlist bu = new Userlist();
				bu.setUserId(user.getUserId());
				bu.setDepartmentId(" ");
				bu.setTellerName("root");
				bu.setTellerPasswd(Base64Encoder.encode(user.getUserPwd()));
				bu.setError("0");
				bu.setTellerState("0");
				bu.setRemark("");
				bu.setEncryptMethod("0");
				bu.setResetLogin("0");
				bu.setLoginStatus("1");
				bu.setPwdInit("1");
				getHibernateTemplate().save(bu);
				user.setUsername("root");
				return;
			}
			throw new Exception("用户ID:" + user.getUserId() + "可能错误,系统找不到指定的用户信息!");
		}
		Userlist teller = (Userlist) list.get(0);
		if (teller.getTellerState().equals("1")) {
			throw new Exception("该用户已被冻结!");
		}
		String addUserChangePassword = ConfigReaderTool.getSingleConfigMsg("loginInit", "addUserChangePassword");
		if ((addUserChangePassword.equals("0")) && (teller.getPwdInit() == null)) {
			throw new Exception("提示：该用户为新用户,请即刻修改密码!");
		}
		String resetPassword = ConfigReaderTool.getSingleConfigMsg("loginInit", "resetPassword");
		if ((resetPassword.equals("0")) && (teller.getPwdInit().equals("1"))) {
			throw new Exception("提示：该用户为新用户或已重置密码,请即刻修改密码!");
		}
		if ((teller.getEncryptMethod() == null) || (teller.getEncryptMethod().equals("1"))) {
			throw new Exception("提示：该系统已经更改密码策略，请您先修改密码!");
		}
		String pwd = user.getUserPwd();

		System.out.println(teller.getTellerPasswd());
		if (!pwd.equals(teller.getTellerPasswd())) {
			teller.setError(String.valueOf(Integer.parseInt(teller.getError()) + 1));
			String pwd_error_times = ConfigReaderTool.getSingleConfigMsg("loginInit", "pwd_error_times");
			if (Integer.parseInt(teller.getError()) >= Integer.parseInt(pwd_error_times)) {
				teller.setTellerState("1");
			}
			getHibernateTemplate().update(teller);
			if (teller.getError().equals(pwd_error_times)) {
				throw new Exception("该用户已被冻结!");
			}
			throw new Exception("用户密码错误,请重新登录!");
		}
		if (!teller.getError().equals("0")) {
			teller.setError("0");
			getHibernateTemplate().update(teller);
		}
		teller.setResetLogin("0");
		getHibernateTemplate().update(teller);

		user.setUsername(teller.getTellerName());

		user.setUserType(teller.getRemark());
	}

	public DataTree getFunctionTree(final String userId) throws Exception {
		DataTree tree = new DataTree();
		final CommonDataNode root = new CommonDataNode("-1", "管理系统", " ");

		final Set treeLeaf = new HashSet();
		tree.setTreeLeaf(treeLeaf);

		tree.setRootNode(root);
		try {
			getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException {
					List list = UserDao.this.getHibernateTemplate().find("from Userlist teller where teller.userId=?", userId);
					Rolelist role = null;
					if (!list.isEmpty()) {
						List s = UserDao.this.getHibernateTemplate().find("from Appinfo");
						Userlist teller = (Userlist) list.get(0);

						Map map = new HashMap();
						Map subappmap = new HashMap();

						role = UserDao.this.getRole(teller.getRoleId());
						Set funcs = role.getFunctions();
						for (Iterator iter = funcs.iterator(); iter.hasNext();) {
							Funclist element = (Funclist) iter.next();

							treeLeaf.add(element.getFuncAddress());
							if (!element.getRunflg().equals("0")) {
								String appid = element.getAppid();
								if ((appid == null) || ("".equals(appid))) {
									map.put(element, element);
								} else {
									Appinfo app = getAppInfo(s, appid);

									List subapplist = UserDao.this.getHibernateTemplate().find("FROM EtellerSubappinfo t WHERE t.comp_id.appid=?",
											appid);
									int k = 0;
									for (Iterator subiter = subapplist.iterator(); subiter.hasNext();) {
										EtellerSubappinfo subappinfo = (EtellerSubappinfo) subiter.next();
										if (map.containsKey(app)) {
											HashSet set = (HashSet) map.get(app);
											set.add(subappinfo);
										} else {
											HashSet set = new HashSet();
											set.add(subappinfo);
											map.put(app, set);
										}
									}
									if ((element.getSubappid() != null)
											&& ((element.getSubappid().equals("00000")) || (element.getSubappid().equals("")))) {
										if (map.containsKey(app)) {
											HashSet set = (HashSet) map.get(app);
											set.add(element);
										} else {
											HashSet set = new HashSet();
											set.add(element);
											map.put(app, set);
										}
									}
								}
							}
						}
						Set applist = map.keySet();
						for (Iterator e = applist.iterator(); e.hasNext();) {
							Object element = e.next();
							if ((element instanceof Appinfo)) {
								Appinfo app = (Appinfo) element;
								CommonDataNode nd = new CommonDataNode(app.getAppid(), app.getAppname(), app.getAppadress(), 2);
								HashSet list1 = (HashSet) map.get(app);
								for (Iterator f = list1.iterator(); f.hasNext();) {
									Object value = f.next();
									if ((value instanceof Funclist)) {
										Funclist func = (Funclist) value;
										if (!func.getRunflg().equals("0")) {
											CommonDataNode fnd = new CommonDataNode(func.getFuncId().toString(), func.getFuncName(),
													func.getFuncAddress(), 3);
											nd.addChild(fnd);
										}
									} else if ((value instanceof EtellerSubappinfo)) {
										EtellerSubappinfo subapp = (EtellerSubappinfo) value;
										CommonDataNode subappnd = new CommonDataNode(subapp.getComp_id().getAppid(), subapp.getAppname(), "", 4);
										List funclist1 = UserDao.this.getHibernateTemplate().find("FROM Funclist t WHERE t.subappid=?",
												subapp.getComp_id().getSubappid());
										for (Iterator t = funclist1.iterator(); t.hasNext();) {
											Funclist func = (Funclist) t.next();
											if (!func.getRunflg().equals("0")) {
												Set roles = func.getRoles();
												for (Iterator iter = roles.iterator(); iter.hasNext();) {
													Rolelist elementr = (Rolelist) iter.next();
													if (role.getRoleId().equals(elementr.getRoleId())) {
														CommonDataNode fnd1 = new CommonDataNode(func.getFuncId().toString(), func.getFuncName(),
																func.getFuncAddress(), 3);
														subappnd.addChild(fnd1);
													}
												}
											}
										}
										nd.addChild(subappnd);
									}
								}
								root.addChild(nd);
							} else if ((element instanceof Funclist)) {
								Funclist func = (Funclist) element;
								CommonDataNode fnd = new CommonDataNode(func.getFuncId().toString(), func.getFuncName(), func.getFuncAddress(), 3);
								root.addChild(fnd);
							}
						}
					}
					return null;
				}

				private Appinfo getAppInfo(List s, String appId) {
					for (Iterator i = s.iterator(); i.hasNext();) {
						Appinfo app = (Appinfo) i.next();
						if (app.getAppid().equals(appId)) {
							return app;
						}
					}
					return null;
				}
			});
		} catch (Exception ex) {
			if (ex.getMessage().indexOf("SQLSTATE: 22001") > -1) {
				this.logger.warn("数据库相关字段长度设置过短!建议加长字段长度! error:" + ex.getMessage());
			} else {
				ex.printStackTrace();
				throw ex;
			}
		}
		treeSort(tree);

		return tree;
	}

	public DataTree getFunctionTreeByLevel(final String level) {
		DataTree tree = new DataTree();
		final CommonDataNode root = new CommonDataNode("-1", "管理系统", " ");

		tree.setRootNode(root);
		try {
			getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException {
					Rolelist role = null;

					List s = UserDao.this.getHibernateTemplate().find("from Appinfo");

					Map map = new HashMap();
					Map subappmap = new HashMap();

					role = UserDao.this.getRole(new Long(level));
					Set funcs = role.getFunctions();
					for (Iterator iter = funcs.iterator(); iter.hasNext();) {
						Funclist element = (Funclist) iter.next();
						if (!element.getRunflg().equals("0")) {
							String appid = element.getAppid();
							if ((appid == null) || ("".equals(appid))) {
								map.put(element, element);
							} else {
								Appinfo app = getAppInfo(s, appid);

								List subapplist = UserDao.this.getHibernateTemplate().find("FROM EtellerSubappinfo t WHERE t.comp_id.appid=?", appid);
								int k = 0;
								for (Iterator subiter = subapplist.iterator(); subiter.hasNext();) {
									EtellerSubappinfo subappinfo = (EtellerSubappinfo) subiter.next();
									if (map.containsKey(app)) {
										HashSet set = (HashSet) map.get(app);
										set.add(subappinfo);
									} else {
										HashSet set = new HashSet();
										set.add(subappinfo);
										map.put(app, set);
									}
								}
								if ((element.getSubappid() != null)
										&& ((element.getSubappid().equals("00000")) || (element.getSubappid().equals("")))) {
									if (map.containsKey(app)) {
										HashSet set = (HashSet) map.get(app);
										set.add(element);
									} else {
										HashSet set = new HashSet();
										set.add(element);
										map.put(app, set);
									}
								}
							}
						}
					}
					Set applist = map.keySet();
					for (Iterator e = applist.iterator(); e.hasNext();) {
						Object element = e.next();
						if ((element instanceof Appinfo)) {
							Appinfo app = (Appinfo) element;
							CommonDataNode nd = new CommonDataNode(app.getAppid(), app.getAppname(), app.getAppadress(), 2);
							HashSet list1 = (HashSet) map.get(app);
							for (Iterator f = list1.iterator(); f.hasNext();) {
								Object value = f.next();
								if ((value instanceof Funclist)) {
									Funclist func = (Funclist) value;
									if (!func.getRunflg().equals("0")) {
										CommonDataNode fnd = new CommonDataNode(func.getFuncId().toString(), func.getFuncName(),
												func.getFuncAddress(), 3);
										nd.addChild(fnd);
									}
								} else if ((value instanceof EtellerSubappinfo)) {
									EtellerSubappinfo subapp = (EtellerSubappinfo) value;
									CommonDataNode subappnd = new CommonDataNode(subapp.getComp_id().getAppid(), subapp.getAppname(), "", 4);
									List funclist1 = UserDao.this.getHibernateTemplate().find("FROM Funclist t WHERE t.subappid=?",
											subapp.getComp_id().getSubappid());
									for (Iterator t = funclist1.iterator(); t.hasNext();) {
										Funclist func = (Funclist) t.next();
										if (!func.getRunflg().equals("0")) {
											Set roles = func.getRoles();
											for (Iterator iter = roles.iterator(); iter.hasNext();) {
												Rolelist elementr = (Rolelist) iter.next();
												if (role.getRoleId().equals(elementr.getRoleId())) {
													CommonDataNode fnd1 = new CommonDataNode(func.getFuncId().toString(), func.getFuncName(),
															func.getFuncAddress(), 3);
													subappnd.addChild(fnd1);
												}
											}
										}
									}
									nd.addChild(subappnd);
								}
							}
							root.addChild(nd);
						} else if ((element instanceof Funclist)) {
							Funclist func = (Funclist) element;
							CommonDataNode fnd = new CommonDataNode(func.getFuncId().toString(), func.getFuncName(), func.getFuncAddress(), 3);
							root.addChild(fnd);
						}
					}
					return null;
				}

				private Appinfo getAppInfo(List s, String appId) {
					for (Iterator i = s.iterator(); i.hasNext();) {
						Appinfo app = (Appinfo) i.next();
						if (app.getAppid().equals(appId)) {
							return app;
						}
					}
					return null;
				}
			});
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		treeSort(tree);

		return tree;
	}

	private void treeSort(DataTree tree) {
		CommonDataNode root = tree.getRootNode();
		nodeSort(root);
	}

	private void nodeSort(CommonDataNode node) {
		CommonDataNode[] children = (CommonDataNode[]) node.getChildren().toArray(new CommonDataNode[0]);
		sort(children);
		node.setChildren(Arrays.asList(children));
		for (int i = 0; i < children.length; i++) {
			nodeSort(children[i]);
		}
	}

	private void sort(CommonDataNode[] nodes) {
		Arrays.sort(nodes, new Comparator<CommonDataNode>() {
			public int compare(CommonDataNode o1, CommonDataNode o2) {
				return o1.getNodeId().compareTo(o2.getNodeId());
			}
		});
	}

	private String fill(String name) {
		int len = name.length();
		for (int i = 0; i < 8 - len; i++) {
			name = "0" + name;
		}
		return name;
	}

	public boolean resetPassword(final Userlist user, final String npsw) {
		boolean isModified = false;
		try {
			getHibernateTemplate().execute(new HibernateCallback() {
				public Object doInHibernate(Session session) throws HibernateException {
					List ls = UserDao.this.getHibernateTemplate().find("from Userlist teller where teller.userId=?", user.getUserId());
					Userlist teller = (Userlist) ls.get(0);

					teller.setTellerPasswd(npsw);

					teller.setError("0");
					teller.setPwdInit("0");
					teller.setEncryptMethod("0");
					session.update(teller);
					return null;
				}
			});
			isModified = true;
		} catch (Exception ex) {
			ex.printStackTrace();
			isModified = false;
		}
		return isModified;
	}

	public Userlist getUser(String userid) {
		List l = getHibernateTemplate().find("from Userlist cu where cu.userId=?", userid);
		if ((l == null) || (l.size() == 0)) {
			return null;
		}
		return (Userlist) l.get(0);
	}

	public String getMaxUserId() {
		List list = getHibernateTemplate().find("select max(u.userId) from Userlist u where u.userId != 'root'");

		String userid = (String) list.get(0);
		if (userid == null) {
			return "A0001";
		}
		int userno = Integer.parseInt(userid.substring(1));
		userno++;
		String usernostr = String.valueOf(userno);
		int len = usernostr.length();
		for (int i = 0; i < 4 - len; i++) {
			usernostr = "0" + usernostr;
		}
		return "A" + usernostr;
	}

	public Userlist[] getUsersbyMap(final Map map) {
		final List list = new ArrayList();
		getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				Criteria criteria = session.createCriteria(Userlist.class);
				if (map.get("userId") != null) {
					if (((List) map.get("userId")).isEmpty()) {
						return null;
					}
					criteria.add(Expression.in("userId", (List) map.get("userId")));
				}
				if (map.get("usertype") != null) {
					criteria.add(Expression.eq("usertype", map.get("usertype")));
				}
				if (map.get("workstatus") != null) {
					if (map.get("workstatus").equals("3")) {
						criteria.add(Expression.not(Expression.eq("workstatus", "2")));
					} else {
						criteria.add(Expression.eq("workstatus", map.get("workstatus")));
					}
				}
				if (map.get("degree") != null) {
					criteria.add(Expression.eq("degree", map.get("degree")));
				}
				criteria.add(Expression.between("starttime", map.get("startdate"), map.get("enddate")));

				criteria.add(Expression.not(Expression.eq("userId", "root")));
				list.addAll(criteria.list());
				return null;
			}
		});
		return (Userlist[]) list.toArray(new Userlist[0]);
	}

	public Userlist[] getAllUserFormDB() {
		List ls = getHibernateTemplate().loadAll(Userlist.class);
		Userlist root = new Userlist();
		root.setUserId("root");
		ls.remove(root);
		return (Userlist[]) ls.toArray(new Userlist[0]);
	}

	private DecimalFormat serialFmt = new DecimalFormat("000");

	public void addUser(Object obj) {
		Userlist ur = (Userlist) obj;

		getHibernateTemplate().save(obj);
	}

	public synchronized String generateUserId(String depno) {
		if ((depno == null) || ("".equals(depno.trim()))) {
			throw new IllegalArgumentException("生成用户ID时机构代码为空");
		}
		List results = getHibernateTemplate().find("select max(user.userId) from Userlist user where user.userId like '" + depno + "%'");
		if ((results == null) || (results.size() <= 0) || (results.get(0) == null) || ("".equals(results.get(0)))) {
			return depno + "001";
		}
		String maxId = (String) results.get(0);
		int serial = Integer.parseInt(maxId.substring(maxId.length() - 3, maxId.length())) + 1;
		return depno + this.serialFmt.format(serial);
	}

	public Rolelist getRole(Long roleId) {
		List list = getHibernateTemplate().find("FROM Rolelist role WHERE role.roleId=" + roleId);
		if (list.size() == 0) {
			return null;
		}
		return (Rolelist) list.get(0);
	}

	public Userlist[] getUserBydptid(String dptid) {
		List list = new ArrayList();
		try {
			list = getHibernateTemplate().find("FROM Userlist user WHERE user.departmentId=" + dptid);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return (Userlist[]) list.toArray(new Userlist[0]);
	}

	public Userlist getUserlistBysql(String sql) {
		List list = new ArrayList();
		System.out.println("sql=" + sql);
		list = getHibernateTemplate().find(sql);
		return (Userlist) list.get(0);
	}

	public Userlist getUserByName(String name) throws Exception {
		return (Userlist) getSession().createCriteria(Userlist.class).add(Restrictions.eq("tellerName", name)).uniqueResult();
	}

	public Userlist[] getUserlistBysql2(String string) {
		return (Userlist[]) getSession().createQuery(string).list().toArray(new Userlist[0]);
	}
}
