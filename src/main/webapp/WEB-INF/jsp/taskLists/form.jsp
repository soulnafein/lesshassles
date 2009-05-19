<%@ include file="/WEB-INF/jsp/includes.jspf" %>

<div id="taskLists" class="span-16 ui-accordion ui-widget ui-helper-reset">

	<h2 class="ui-accordion-header ui-helper-reset ui-state-active ui-corner-top"><a>Create a new task list</a></h2>
  <div class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom ui-accordion-content-active">
    <form:form modelAttribute="taskList">
      <p>
        <label for="name">Name your new list (e.g. Things I need to do today)</label>
      </p>
      <p>
        <form:input cssClass="text" path="name" />
      </p>
      <p><input id="submit" type="submit" value="Create this list" /> or <a href="/">cancel</a></p> 
    </form:form>
  </div>
</div>
