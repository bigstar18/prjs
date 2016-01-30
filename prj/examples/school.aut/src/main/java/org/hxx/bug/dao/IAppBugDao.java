package org.hxx.bug.dao;

import java.util.List;

import org.hxx.bug.model.AppBug;

public interface IAppBugDao {
	void add(AppBug appBug) throws Exception;

	List<AppBug> load(String phone);
}
