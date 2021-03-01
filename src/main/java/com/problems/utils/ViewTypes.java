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

	public static final int LIST_VIEW = 1;
	public static final int DETAIL_VIEW = 2;

	public static List<SelectItem> createList(Resources resources) {
		List<SelectItem> list = new ArrayList<>();

		list.add(new SelectItemImpl(LIST_VIEW, resources.getOrDefault("viewTypes.list_view", "List View")));
		list.add(new SelectItemImpl(DETAIL_VIEW, resources.getOrDefault("viewTypes.detail_view", "Detail View")));
		return list;
	}

	public static int toViewType(Integer value) {
		if (value != null) {
			if (value == LIST_VIEW || value == DETAIL_VIEW) {
				return value;
			}
		}
		return LIST_VIEW;
	}
}
