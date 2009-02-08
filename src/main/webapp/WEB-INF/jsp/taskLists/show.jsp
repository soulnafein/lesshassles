<%@ include file="/WEB-INF/jsp/includes.jspf" %>

<h1>${taskList.name}</h1>
<ul id="tasks">
	<c:forEach items="${taskList.tasks}" var="task">
		<li>${task.description}</li>	
	</c:forEach>
</ul>
<a id="showAddTaskForm" href="#">Add another task</a>
<form:form modelAttribute="task" action="/tasklists/${taskList.id}/tasks/new.htm" cssStyle="display:none">
	<p>
		<form:input path="description" />
	</p>
	<p>
		<input id="submit" type="submit" value="Add this task" /> or <a id="hideAddTaskForm" href="#">close</a>
	</p> 
</form:form>

