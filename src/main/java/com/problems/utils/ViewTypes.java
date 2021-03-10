package com.problems.utils;

import java.util.ArrayList;
import java.util.List;

import com.appslandia.common.models.SelectItem;
import com.appslandia.common.models.SelectItemImpl;
import com.appslandia.plum.base.Resources;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class ViewTypes {

	public static final int DEFAULT_VIEW = 1;
	public static final int SOLUTIONS_VIEW = 2;
	public static final int IMPLS_VIEW = 3;

	public static List<SelectItem> createList(Resources resources) {
		List<SelectItem> list = new ArrayList<>();

		list.add(new SelectItemImpl(DEFAULT_VIEW, resources.getOrDefault("viewTypes.default_view", "Default")));
		list.add(new SelectItemImpl(SOLUTIONS_VIEW, resources.getOrDefault("viewTypes.solutions_view", "Solutions")));
		list.add(new SelectItemImpl(IMPLS_VIEW, resources.getOrDefault("viewTypes.impls_view", "Implementations")));
		return list;
	}

	public static int toViewType(Integer value) {
		if (value != null) {
			if (value == DEFAULT_VIEW || value == SOLUTIONS_VIEW || value == IMPLS_VIEW) {
				return value;
			}
		}
		return DEFAULT_VIEW;
	}
}
