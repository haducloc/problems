<%@include file="../includes/libs.jsp"%>

<!-- @variables
  page.title=${fx:escCt(model.titleText)}
  __layout=layout_admin
 -->

<!-- @cssSection begin -->
<link href="@(contextPathExpr)/static/prism/prism.css" rel="stylesheet" />
<!-- @cssSection end -->

<div class="container-fluid mb-4">
  <div class="row">
    <div class="col">

      <form>
        <t:c t="div" clazz="form-group" render="${not empty model.problemUrl}">
          <t:fieldLabel field="titleText" clazz="font-w6">
            <t:actionLink action="edit" __problemId="${model.problemId}">${ctx.escCt('problem.titleText')}</t:actionLink>
          </t:fieldLabel>
          <p class="m-0 p-1 problem-box">
            <a href="${fx:esc(model.problemUrl)}" target="_blank">${fx:escCt(model.titleText)}</a>
          </p>
        </t:c>

        <t:c t="div" clazz="form-group" render="${empty model.problemUrl}">
          <t:fieldLabel field="titleText" clazz="font-w6">
            <t:actionLink action="edit" __problemId="${model.problemId}">${ctx.escCt('problem.titleText')}</t:actionLink>
          </t:fieldLabel>
          <p class="m-0 p-1 problem-box">${fx:escCt(model.titleText)}</p>
        </t:c>

        <div class="form-group">
          <t:fieldLabel field="tags" labelKey="problem.tags" clazz="font-w6" />
          <p class="m-0 p-1 problem-box">
            <t:iterate items="${model.tagList}" var="tag">
              <t:actionLink action="index" __query="${tag}" clazz="tag-link mr-3">
                         ${fx:escCt(tag)}
                      </t:actionLink>
            </t:iterate>
          </p>
        </div>

        <div class="form-group">
          <t:fieldLabel field="descText" labelKey="problem.descText" clazz="font-w6" />
          <pre class="m-0 p-1 problem-box border">${fx:escCt(model.descText)}</pre>
        </div>

        <div class="form-group">
          <t:fieldLabel field="solutions" labelKey="problem.solutions" clazz="font-w6" />
          <pre class="m-0 p-1 problem-box border">${fx:escCt(model.solutions)}</pre>
        </div>

        <t:c t="div" clazz="form-group" render="${not empty model.impls}">
          <t:fieldLabel field="impls" labelKey="problem.impls" clazz="font-w6" />

          <pre class="m-0 p-1 border font-sm1"><code class="language-${model.language}">${fx:escCt(model.impls)}</code></pre>
        </t:c>
      </form>

    </div>
  </div>
</div>

<!-- @jsSection begin -->
<script src="@(contextPathExpr)/static/prism/prism.js"></script>

<script type="text/javascript">
  $(document).ready(function() {

    initButtons("#btnSave");
  });
</script>
<!-- @jsSection end -->