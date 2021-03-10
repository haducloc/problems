package com.problems.entities;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Locale;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.NotNull;

import com.appslandia.common.base.Bind;
import com.appslandia.common.formatters.Formatter;
import com.appslandia.common.jpa.EntityBase;
import com.appslandia.common.utils.NormalizeUtils;
import com.appslandia.common.utils.StringUtils;
import com.appslandia.common.utils.TagUtils;
import com.appslandia.common.validators.PathComponent;
import com.problems.formatters.Formatters;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@Entity
@NamedQuery(name = "Problem.queryCount", query = "SELECT COUNT(e) FROM Problem e WHERE (e.accountId=:accountId) AND ((:query IS NULL) OR (:query LIKE '#%' AND e.tags LIKE :wtag) OR (e.titleText LIKE :titleText))")
@NamedQuery(name = "Problem.query", query = "SELECT e FROM Problem e WHERE (e.accountId=:accountId) AND ((:query IS NULL) OR (:query LIKE '#%' AND e.tags LIKE :wtag) OR (e.titleText LIKE :titleText)) ORDER BY e.timeCreated DESC")
@NamedQuery(name = "Problem.queryDbTags", query = "SELECT e.tags FROM Problem e WHERE e.accountId=:accountId")
@NamedQuery(name = "Problem.checkTitlePath", query = "SELECT 1 FROM Problem e WHERE (:problemId IS NULL OR e.problemId != :problemId) AND e.title_path=:title_path")
public class Problem extends EntityBase {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer problemId;

	@NotNull
	private String titleText;

	private String problemUrl;

	@Bind(fmt = Formatters.ALG_DB_TAGS)
	@NotNull
	private String tags;

	@Bind(fmt = Formatter.TEXT_ML)
	@NotNull
	@Column(length = 3000)
	private String descText;

	@Bind(fmt = Formatter.TEXT_ML)
	@NotNull
	@Column(length = 8000)
	private String solutions;

	@Bind(fmt = Formatter.TEXT_ML)
	@Column(columnDefinition = "TEXT")
	private String impls;

	@NotNull
	@Column(updatable = false)
	private OffsetDateTime timeCreated;

	@NotNull
	@Column(updatable = false)
	private Integer accountId;

	@NotNull
	@PathComponent
	@Column(unique = true)
	private String title_path;

	@Override
	public Serializable getPk() {
		return this.problemId;
	}

	public String[] getTagList() {
		return TagUtils.toTags(this.tags);
	}

	public Integer getProblemId() {
		return problemId;
	}

	public void setProblemId(Integer problemId) {
		this.problemId = problemId;
	}

	public String getTitleText() {
		return titleText;
	}

	public void setTitleText(String titleText) {
		this.titleText = StringUtils.firstUpperCase(titleText, Locale.ROOT);
		this.title_path = NormalizeUtils.unaccent(NormalizeUtils.normalizeLabel(titleText));
	}

	public String getProblemUrl() {
		return problemUrl;
	}

	public void setProblemUrl(String problemUrl) {
		this.problemUrl = problemUrl;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getDescText() {
		return descText;
	}

	public void setDescText(String descText) {
		this.descText = StringUtils.firstUpperCase(descText, Locale.ROOT);
	}

	public String getSolutions() {
		return solutions;
	}

	public void setSolutions(String solutions) {
		this.solutions = solutions;
	}

	public String getImpls() {
		return impls;
	}

	public void setImpls(String impls) {
		this.impls = impls;
	}

	public OffsetDateTime getTimeCreated() {
		return timeCreated;
	}

	public void setTimeCreated(OffsetDateTime timeCreated) {
		this.timeCreated = timeCreated;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getTitle_path() {
		return title_path;
	}

	public void setTitle_path(String title_path) {
		this.title_path = title_path;
	}
}
