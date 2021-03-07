<%@include file="../includes/libs.jsp"%>

<!-- @variables
  page.title=${ctx.esc('problem_index.page_title')}
  __layout=layout_admin
 -->

<div class="container-fluid mb-4">

  <div class="row justify-content-center">
    <div class="col">

      <div class="mb-4 p-4 border">

        <t:form action="index" method="GET">
          <t:hidden path="model.pageSize" />

          <div class="input-group">
            <div class="input-group-prepend">
              <t:select items="${model.viewTypes}" path="model.viewType" clazz="form-control rounded-0 px-2" triggerSubmit="true" />
            </div>
            <t:textBox path="model.query" clazz="form-control" />
            <div class="input-group-append">
              <t:submitButton id="btnSearch" clazz="btn btn-primary" labelKey="label.search" handleWait="false"></t:submitButton>
            </div>
          </div>

          <p class="mt-2 mb-0">
            <t:actionLink action="index" clazz="font-sl2 tag-link mr-3">X</t:actionLink>

            <t:iterate items="${algTagList}" var="tag">
              <a href="#" class="tag-opt tag-link mr-3">${fx:escCt(tag)}</a>
            </t:iterate>

            <button id="mytagsBtn" type="button" class="btn btn-light py-0">${ctx.escCt('label.tags')}</button>
          </p>
        </t:form>

      </div>
    </div>
  </div>

  <c:if test="${model.pagerModel.pageCount gt 1}">
    <div class="row">
      <div class="col">

        <t:pager model="${model.pagerModel}" tag="ul" clazz="pagination justify-content-center mb-4">
          <t:tpl type="page">
            <li class="page-item${selected ? ' active' : ''}">
              <t:actionLink clazz="page-link" action="index" __viewType="${model.viewType}" __query="${model.query}" __pageIndex="${item.index}"
                __recordCount="${model.pagerModel.recordCount}" __pageSize="${model.pageSize}">${label}</t:actionLink>
            </li>
          </t:tpl>
          <t:tpl type="dots">
            <li class="page-item">
              <a href="#" class="page-link">...</a>
            </li>
          </t:tpl>
        </t:pager>
      </div>
    </div>
  </c:if>

  <t:c t="div" clazz="row" render="${model.viewType eq 1}">
    <div class="col">

      <div class="table-responsive mb-4">
        <table class="table table-sm nowrap-head table-bordered table-hover">
          <thead>
            <tr>
              <th scope="col">${ctx.escCt('label.id')}</th>
              <th scope="col">${ctx.escCt('problem.titleText')}</th>
              <th scope="col">${ctx.escCt('problem.descText')}</th>
              <th scope="col">${ctx.escCt('problem.tags')}</th>
            </tr>
          </thead>
          <tbody>
            <t:iterate items="${model.problems}" var="item">
              <tr>
                <td>#${fx:escCt(item.problemId)}</td>
                <td>
                  <t:actionLink action="view" __problemId="${item.problemId}" target="_blank">
                     ${fx:escCt(item.titleText)}
                  </t:actionLink>
                </td>
                <td>${fx:escCt(item.descText)}</td>
                <td>
                  <t:iterate items="${item.tagList}" var="tag">
                    <t:actionLink action="index" __viewType="${model.viewType}" __query="${tag}" clazz="tag-link mr-3">
                       ${fx:escCt(tag)}
                    </t:actionLink>
                  </t:iterate>
                </td>
              </tr>
            </t:iterate>
            <t:c t="tr" render="${empty model.problems}">
              <td colspan="4">${ctx.escCt('record.no_records_found')}</td>
            </t:c>
          </tbody>
        </table>
      </div>
    </div>
  </t:c>

  <t:c t="div" clazz="row" render="${model.viewType eq 2}">
    <div class="col">
      <t:iterate items="${model.problems}" var="item">

        <div class="mb-4 p-3 shadow-sm">
          <form>

            <div class="form-group">
              <t:fieldLabel field="problemUrl" labelKey="problem.titleText" clazz="font-w6" />
              <p class="m-0 p-1 problem-box font-sl1 font-w6">
                <t:actionLink action="view" __problemId="${item.problemId}" target="_blank">${fx:escCt(item.titleText)}</t:actionLink>
              </p>
            </div>

            <div class="form-group">
              <t:fieldLabel field="tags" labelKey="problem.tags" clazz="font-w6" />
              <p class="m-0 p-1 problem-box">
                <t:iterate items="${item.tagList}" var="tag">
                  <t:actionLink action="index" __viewType="${model.viewType}" __query="${tag}" clazz="tag-link mr-3">
                       ${fx:escCt(tag)}
                    </t:actionLink>
                </t:iterate>
              </p>
            </div>

            <div class="form-group">
              <t:fieldLabel field="descText" labelKey="problem.descText" clazz="font-w6" />
              <pre class="m-0 p-1 problem-box border">${fx:escCt(item.descText)}</pre>
            </div>

            <div class="form-group mb-0">
              <t:fieldLabel field="solutions" labelKey="problem.solutions" clazz="font-w6" />
              <pre class="m-0 p-1 problem-box border">${fx:escCt(item.solutions)}</pre>
            </div>

          </form>
        </div>

      </t:iterate>
    </div>
  </t:c>

  <c:if test="${model.pagerModel.pageCount gt 1}">
    <div class="row">
      <div class="col">

        <t:pager model="${model.pagerModel}" tag="ul" clazz="pagination justify-content-center mb-4">
          <t:tpl type="page">
            <li class="page-item${selected ? ' active' : ''}">
              <t:actionLink clazz="page-link" action="index" __viewType="${model.viewType}" __query="${model.query}" __pageIndex="${item.index}"
                __recordCount="${model.pagerModel.recordCount}" __pageSize="${model.pageSize}">${label}</t:actionLink>
            </li>
          </t:tpl>
          <t:tpl type="dots">
            <li class="page-item">
              <a href="#" class="page-link">...</a>
            </li>
          </t:tpl>
        </t:pager>
      </div>
    </div>
  </c:if>
</div>

<%@ include file="../includes/mytags_dialog.jsp"%>

<!-- @jsSection begin -->
<script type="text/javascript">
  $(document).ready(function() {

    $(".tag-opt").click(function() {
      $("#query").val($(this).text());
      $("#btnSearch")[0].click();
    });

    $("#mytagsBtn").click(function() {

      ajax("GET", "<t:action action='mytags' />", null, null, false, function(res) {

        var dlg = $("#mytagsDlg");
        $(".modal-body", dlg).html(res);
        dlg.modal('show');

      }, function(err) {
      }, function() {
      });

    });

  });
</script>
<!-- @jsSection end -->