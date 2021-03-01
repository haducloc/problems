package com.problems.services;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.appslandia.common.jpa.EntityManagerAccessor;
import com.appslandia.common.utils.AssertUtils;
import com.appslandia.common.utils.CollectionUtils;
import com.appslandia.plum.caching.CacheChangeEvent;
import com.appslandia.plum.caching.CacheResult;
import com.problems.caching.Caches;
import com.problems.entities.PreTags;
import com.problems.models.PreTagsModel;
import com.problems.utils.PreTagUtils;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
public class PreTagsService {

	static final String CACHE_KEY_PRETAGS = "pre-tags-${0}";

	@Inject
	protected EntityManagerAccessor em;

	@Inject
	protected CacheChangeEvent cacheChangeEvent;

	public PreTags findByPk(int accountId) {
		return em.find(PreTags.class, accountId);
	}

	@Transactional
	public void save(PreTags preTags) throws Exception {
		PreTags managed = em.find(PreTags.class, preTags.getAccountId());
		if (managed == null) {

			PreTags obj = new PreTags();
			obj.setAccountId(preTags.getAccountId());
			obj.setTags(preTags.getTags());

			em.insert(obj);
		} else {
			managed.setTags(preTags.getTags());
			AssertUtils.assertTrue(managed.getAccountId() == preTags.getAccountId());
		}

		// Notify change
		cacheChangeEvent.fireAsync(Caches.DEFAULT, CACHE_KEY_PRETAGS, preTags.getAccountId());
	}

	@CacheResult(cacheName = Caches.DEFAULT, key = CACHE_KEY_PRETAGS)
	public PreTagsModel getPreTags(int accountId) {
		PreTags preTags = em.find(PreTags.class, accountId);
		if (preTags == null)
			return new PreTagsModel();

		String[] tags = PreTagUtils.toPreTags(preTags.getTags());
		return new PreTagsModel(CollectionUtils.toList(tags));
	}
}
