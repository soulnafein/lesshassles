<%@ include file="/WEB-INF/jsp/includes.jspf" %>

<h1 id="taskListName">${taskList.name}</h1>
<form id="updateTaskList" action="/tasklists/${taskList.id}-edit.htm" style="display:none">
	<input type="text"  name="taskListName" value="${taskList.name}" />
	<input type="submit" value="Save changes" />
	<a class="_cancel" href="#">cancel</a>
</form>
<ul id="tasks">
	<c:forEach items="${taskList.tasks}" var="task">
		<%@ include file="/WEB-INF/jsp/tasks/show.jsp" %>		
	</c:forEach>
</ul>
<form id="assignTask" action="/tasklists/${taskList.id}/tasks/0-assign.htm" style="display:none">
	<input id="searchBox" type="text" value="" />
	<input type="submit" value="Assign" />
	<input class="_cancel" type="button" value="Cancel" />
	<input id="assignee" name="assignee" type="hidden" value="" />
</form>
<form id="setDeadline" action="/tasklists/${taskList.id}/tasks/0-setDeadline.htm" style="display:none">
	<input id="datepicker" name="deadline" type="text" value="" />
	<input type="submit" value="Confirm deadline" />
	<a href="#" class="_cancel">cancel</a>
</form>
<a id="showAddTaskForm" href="#">Add another task</a>
<form:form modelAttribute="task" action="/tasklists/${taskList.id}/tasks/new.htm" cssStyle="display:none">
	<p>
		<form:input path="description" />
	</p>
	<p>
		<input id="submit" type="submit" value="Add this task" /> or <a id="hideAddTaskForm" href="#">close</a>
	</p> 
</form:form>

