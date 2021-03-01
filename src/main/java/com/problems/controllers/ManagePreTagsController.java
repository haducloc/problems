package com.problems.controllers;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletResponse;

import com.appslandia.common.logging.AppLogger;
import com.appslandia.plum.base.ActionResult;
import com.appslandia.plum.base.Authorize;
import com.appslandia.plum.base.Controller;
import com.appslandia.plum.base.ExceptionHandler;
import com.appslandia.plum.base.HttpGetPost;
import com.appslandia.plum.base.ModelBinder;
import com.appslandia.plum.base.RequestAccessor;
import com.appslandia.plum.results.JspResult;
import com.appslandia.plum.results.RedirectResult;
import com.problems.entities.PreTags;
import com.problems.services.PreTagsService;

/**
 *
 * @author <a href="mailto:haducloc13@gmail.com">Loc Ha</a>
 *
 */
@ApplicationScoped
@Controller(value = "manage-pretags")
@Authorize
public class ManagePreTagsController {

	@Inject
	protected AppLogger logger;

	@Inject
	protected ModelBinder modelBinder;

	@Inject
	protected ExceptionHandler exceptionHandler;

	@Inject
	protected PreTagsService preTagsService;

	@HttpGetPost
	public ActionResult index(RequestAccessor request, HttpServletResponse response) throws Exception {
		// GET
		if (request.isGetOrHead()) {
			PreTags model = this.preTagsService.findByPk(request.getUserId());
			if (model == null)
				model = new PreTags();

			request.storeModel(model);
			return JspResult.DEFAULT;
		}

		// POST
		PreTags model = new PreTags();
		modelBinder.bindModel(request, model);

		request.getModelState().remove("accountId");
		model.setAccountId(request.getUserId());

		if (!request.getModelState().isValid()) {
			request.storeModel(model);
			return JspResult.DEFAULT;
		}

		try {
			// Save
			this.preTagsService.save(model);
			request.getMessages().addNotice(request.res("record.saved_successfully", request.res("preTags")));

			return new RedirectResult("index");

		} catch (Exception ex) {
			logger.error(ex);
			request.getMessages().addError(this.exceptionHandler.getProblem(request, ex).getTitle());

			request.storeModel(model);
			return JspResult.DEFAULT;
		}
	}
}
