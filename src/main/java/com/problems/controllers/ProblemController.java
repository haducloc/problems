package com.problems.controllers;

import java.util.List;
import java.util.function.Supplier;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import com.appslandia.common.logging.AppLogger;
import com.appslandia.common.utils.TagList;
import com.appslandia.common.utils.ValueUtils;
import com.appslandia.plum.base.ActionResult;
import com.appslandia.plum.base.Authorize;
import com.appslandia.plum.base.Controller;
import com.appslandia.plum.base.EnableEtag;
import com.appslandia.plum.base.ExceptionHandler;
import com.appslandia.plum.base.Home;
import com.appslandia.plum.base.HttpGet;
import com.appslandia.plum.base.HttpGetPost;
import com.appslandia.plum.base.Model;
import com.appslandia.plum.base.ModelBinder;
import com.appslandia.plum.base.PagerModel;
import com.appslandia.plum.base.RequestAccessor;
import com.appslandia.plum.base.TagCookieHandler;
import com.appslandia.plum.results.JspResult;
import com.appslandia.plum.results.RedirectResult;
import com.problems.entities.Problem;
import com.problems.models.MyTagsModel;
import com.problems.models.ProblemSearchModel;
import com.problems.services.PreTagsService;
import com.problems.services.ProblemService;
import com.problems.utils.TimeUtils;
import com.problems.utils.ViewTypes;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
@Controller
@Authorize
@Home
public class ProblemController {

	public static final String ALG_TAG_LIST = "algTagList";

	@Inject
	protected AppLogger logger;

	@Inject
	protected ModelBinder modelBinder;

	@Inject
	protected ExceptionHandler exceptionHandler;

	@Inject
	protected ProblemService problemService;

	@Inject
	protected PreTagsService preTagsService;

	@Inject
	protected TagCookieHandler tagCookieHandler;

	Supplier<TagList> tagListNewer(RequestAccessor request) {
		return () -> new TagList(this.preTagsService.getPreTags(request.getUserId()).getTags());
	}

	void prepareIndex(RequestAccessor request, ProblemSearchModel model) {
		model.setViewTypes(ViewTypes.createList(request.getResources()));
		tagCookieHandler.getTagList(request, ALG_TAG_LIST, tagListNewer(request));
	}

	@HttpGet
	@EnableEtag
	public ActionResult index(RequestAccessor request, HttpServletResponse response, @Model ProblemSearchModel model) throws Exception {
		model.setPageIndex(ValueUtils.valueOrMin(model.getPageIndex(), 1));
		model.setPageSize(ValueUtils.valueOrMin(model.getPageSize(), 25));
		model.setViewType(ViewTypes.toViewType(model.getViewType()));

		request.getModelState().clearErrors();

		// Problems
		List<Problem> list = this.problemService.query(request.getUserId(), model.getQuery(), model.getPageIndex(), model.getPageSize(),
				model.getRecordCount());
		model.setProblems(list);

		model.setPagerModel(new PagerModel(model.getPageIndex(), model.getRecordCount().val(), model.getPageSize()));

		// Active Tags
		if (!list.isEmpty() && model.isQueryTag()) {
			tagCookieHandler.saveTag(request, response, model.getQuery(), ALG_TAG_LIST, tagListNewer(request));
		}

		prepareIndex(request, model);
		request.storeModel(model);
		return JspResult.DEFAULT;
	}

	void prepareEdit(RequestAccessor request) {
		tagCookieHandler.getTagList(request, ALG_TAG_LIST, tagListNewer(request));
	}

	@HttpGetPost
	public ActionResult edit(RequestAccessor request, HttpServletResponse response, Integer problemId) throws Exception {
		// GET
		if (request.isGetOrHead()) {
			Problem model = null;

			if (problemId == null) {
				model = new Problem();
			} else {
				model = this.problemService.findByPk(problemId);
				request.assertNotNull(model);
				request.assertForbidden(model.getAccountId() == request.getUserId());
			}

			request.storeModel(model);
			prepareEdit(request);
			return JspResult.DEFAULT;
		}

		// POST
		Problem model = new Problem();
		modelBinder.bindModel(request, model);

		request.getModelState().remove("accountId", "timeCreated");
		model.setAccountId(request.getUserId());

		if (!request.getModelState().isValid()) {
			request.storeModel(model);
			prepareEdit(request);
			return JspResult.DEFAULT;
		}

		try {
			// Remove?
			if (request.isRemoveAction()) {
				request.assertNotNull(model.getProblemId());
				problemService.remove(request.getUserId(), model.getProblemId());

				request.getMessages().addNotice(request.res("record.deleted_successfully", request.res("problem")));
				return new RedirectResult("index");
			}

			if (model.getProblemId() == null) {
				model.setTimeCreated(TimeUtils.nowAtGMT7());
			}

			// Active Tags
			tagCookieHandler.saveDbTags(request, response, model.getTags(), ALG_TAG_LIST, tagListNewer(request));

			// Save
			this.problemService.save(model);
			request.getMessages().addNotice(request.res("record.saved_successfully", request.res("problem")));

			return new RedirectResult("view").query("problemId", model.getProblemId());

		} catch (Exception ex) {
			logger.error(ex);
			request.getMessages().addError(this.exceptionHandler.getProblem(request, ex).getTitle());

			request.storeModel(model);
			prepareEdit(request);
			return JspResult.DEFAULT;
		}
	}

	@HttpGet
	@EnableEtag
	public ActionResult view(RequestAccessor request, HttpServletResponse response, Integer problemId) throws Exception {
		Problem model = this.problemService.findByPk(problemId);
		request.storeModel(model);
		return JspResult.DEFAULT;
	}

	@HttpGet
	@EnableEtag
	public ActionResult mytags(RequestAccessor request, HttpServletResponse response, @Model MyTagsModel model) throws Exception {
		List<String> tags = this.problemService.getProblemTags(request.getUserId());
		model.setMytags(tags);

		request.storeModel(model);
		return JspResult.DEFAULT;
	}
}
