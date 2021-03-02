<%@include file="../includes/libs.jsp"%>

<!-- @variables
  page.title=${ctx.esc('problem_edit.page_title')}
  __layout=layout_admin
 -->

<div class="container-fluid mb-4">
  <div class="row">
    <div class="col">

      <t:form id="form1" action="edit" __problemId="${model.problemId}" autocomplete="off" actionType="true">
        <t:formErrors clazz="px-4 py-2" modelLevelOnly="true" />

        <div class="form-group">
          <t:fieldLabel field="titleText" clazz="font-w6" required="true" render="${not empty model.problemId}">
            <t:actionLink action="view" __problemId="${model.problemId}">${ctx.escCt('problem.titleText')}</t:actionLink>
          </t:fieldLabel>
          <t:fieldLabel field="titleText" labelKey="problem.titleText" required="true" render="${empty model.problemId}" />

          <t:textBox path="model.titleText" clazz="form-control problem-box" />
        </div>

        <div class="form-group">
          <t:fieldLabel field="problemUrl" labelKey="problem.problemUrl" />
          <t:textBox path="model.problemUrl" clazz="form-control problem-box" />
        </div>

        <div class="form-group">
          <t:fieldLabel field="tags" labelKey="problem.tags" required="true" />
          <t:textBox path="model.tags" clazz="form-control problem-box" fmt="DbTags" />

          <p class="mt-1 mb-0">
            <t:iterate items="${algTagList}" var="tag">
              <a href="#" class="tag-opt tag-link${lastIndex ? '' : ' mr-3'}">${fx:escCt(tag)}</a>
            </t:iterate>
          </p>
        </div>

        <div class="form-group">
          <t:fieldLabel field="descText" labelKey="problem.descText" required="true" />
          <t:textArea path="model.descText" clazz="form-control problem-box" rows="8" />
        </div>

        <div class="form-group">
          <t:fieldLabel field="solutions" labelKey="problem.solutions" required="true" />
          <t:textArea path="model.solutions" clazz="form-control problem-box" rows="16" />
        </div>

        <div class="form-group">
          <t:fieldLabel field="impls" labelKey="problem.impls" />
          <t:textArea path="model.impls" clazz="form-control problem-box" rows="4" />
        </div>

        <div class="form-row">
          <div class="col-auto mr-auto">
            <t:submitButton id="btnSave" clazz="btn btn-primary px-4" labelKey="label.save"></t:submitButton>
          </div>
          <div class="col-auto">
            <t:submitButton id="btnDel" actionType="remove" clazz="btn btn-danger px-4" labelKey="label.remove" render="${not empty model.pk}"></t:submitButton>
          </div>
        </div>

      </t:form>
    </div>
  </div>
</div>

<!-- @jsSection begin -->
<script type="text/javascript">
  $(document).ready(function() {

    $(".tag-opt").click(function() {
      var jqTags = $("#tags");
      jqTags.val(updateTags(jqTags.val(), $(this).text()));
      jqTags.focus();
    });

    initButtons("#btnSave");
  });
</script>
<!-- @jsSection end -->