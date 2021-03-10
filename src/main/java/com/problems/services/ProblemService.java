package com.problems.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import com.appslandia.common.base.Out;
import com.appslandia.common.jpa.EntityManagerAccessor;
import com.appslandia.common.utils.AssertUtils;
import com.appslandia.common.utils.CollectionUtils;
import com.appslandia.common.utils.ModelUtils;
import com.appslandia.common.utils.TagUtils;
import com.appslandia.plum.caching.CacheChangeEvent;
import com.appslandia.plum.caching.CacheResult;
import com.problems.caching.Caches;
import com.problems.entities.Problem;
import com.problems.models.PreTagsModel;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
public class ProblemService {

	static final String CACHE_KEY_TAGS = "tags-${0}";

	@Inject
	protected EntityManagerAccessor em;

	@Inject
	protected PreTagsService preTagsService;

	@Inject
	protected CacheChangeEvent cacheChangeEvent;

	public Problem findByPk(int pk) {
		return em.find(Problem.class, pk);
	}

	@Transactional
	public void remove(int accountId, int problemId) throws Exception {
		Problem managed = em.find(Problem.class, problemId);

		AssertUtils.assertNotNull(managed);
		AssertUtils.assertTrue(managed.getAccountId() == accountId);

		em.remove(managed);

		// Notify change
		cacheChangeEvent.fireAsync(Caches.DEFAULT, CACHE_KEY_TAGS, accountId);
	}

	@Transactional
	public void save(Problem obj) throws Exception {
		if (obj.getPk() == null) {
			em.insert(obj);

		} else {
			Problem managed = em.find(Problem.class, obj.getPk());
			AssertUtils.assertNotNull(managed);
			AssertUtils.assertTrue(managed.getAccountId() == obj.getAccountId());

			ModelUtils.copy(managed, obj, "titleText", "problemUrl", "tags", "descText", "solutions", "impls");
		}

		// Notify change
		cacheChangeEvent.fireAsync(Caches.DEFAULT, CACHE_KEY_TAGS, obj.getAccountId());
	}

	public List<Problem> query(int accountId, String query, int pageIndex, int pageSize, Out<Integer> recordCount) {
		if (recordCount.value == null || recordCount.value <= 0) {
			recordCount.value = em.createNamedQuery("Problem.queryCount").setParameter("accountId", accountId).setParameter("query", query)
					.setLikeTag("wtag", query).setLikePattern("titleText", query).getCount();
		}

		final int startPos = (pageIndex - 1) * pageSize;

		return em.createNamedQuery("Problem.query", Problem.class).setParameter("accountId", accountId).setParameter("query", query).setLikeTag("wtag", query)
				.setLikePattern("titleText", query).setStartPos(startPos).setMaxResults(pageSize).asReadonly().getResultList();
	}

	@CacheResult(cacheName = Caches.DEFAULT, key = CACHE_KEY_TAGS)
	public List<String> getProblemTags(int accountId) throws Exception {
		List<String> list = em.createNamedQuery("Problem.queryDbTags").setParameter("accountId", accountId).getList();
		Set<String> tags = new HashSet<>();

		for (String dbTags : list) {
			CollectionUtils.toSet(tags, TagUtils.toTags(dbTags));
		}

		List<String> sortedTags = new ArrayList<>(tags);
		PreTagsModel preTags = preTagsService.getPreTags(accountId);

		Collections.sort(sortedTags, (t1, t2) -> Integer.compare(preTags.getIndex(t1, 1000), preTags.getIndex(t2, 2000)));
		return Collections.unmodifiableList(sortedTags);
	}

	public boolean hasTitlePath(String titlePath, Integer problemId) {
		return em.createNamedQuery("Problem.checkTitlePath").setParameter("problemId", problemId).setParameter("title_path", titlePath)
				.getFirstOrNull() != null;
	}
}
