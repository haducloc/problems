package com.problems.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.appslandia.common.base.Bind;
import com.appslandia.common.jpa.EntityBase;
import com.problems.formatters.Formatters;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@Entity
public class PreTags extends EntityBase {
	private static final long serialVersionUID = 1L;

	@Id
	private Integer accountId;

	@Bind(fmt = Formatters.PRE_TAGS)
	@Column(length = 2500)
	private String tags;

	@Override
	public Serializable getPk() {
		return this.accountId;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}
}
