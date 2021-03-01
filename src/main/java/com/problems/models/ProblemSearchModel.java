package com.problems.models;

import java.util.List;

import com.appslandia.common.base.NotBind;
import com.appslandia.common.base.Out;
import com.appslandia.common.models.SelectItem;
import com.appslandia.plum.base.PagerModel;
import com.problems.entities.Problem;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
public class ProblemSearchModel {

	private String query;

	private Integer viewType;

	private Integer pageIndex;

	@NotBind
	private Out<Integer> recordCount = new Out<>();

	@NotBind
	private PagerModel pagerModel;

	@NotBind
	private List<SelectItem> viewTypes;

	@NotBind
	private List<Problem> problems;

	public boolean isQueryTag() {
		return this.query != null && this.query.startsWith("#");
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public Integer getViewType() {
		return viewType;
	}

	public void setViewType(Integer viewType) {
		this.viewType = viewType;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Out<Integer> getRecordCount() {
		return recordCount;
	}

	public void setRecordCount(Out<Integer> recordCount) {
		this.recordCount = recordCount;
	}

	public PagerModel getPagerModel() {
		return pagerModel;
	}

	public void setPagerModel(PagerModel pagerModel) {
		this.pagerModel = pagerModel;
	}

	public List<SelectItem> getViewTypes() {
		return viewTypes;
	}

	public void setViewTypes(List<SelectItem> viewTypes) {
		this.viewTypes = viewTypes;
	}

	public List<Problem> getProblems() {
		return problems;
	}

	public void setProblems(List<Problem> problems) {
		this.problems = problems;
	}
}
