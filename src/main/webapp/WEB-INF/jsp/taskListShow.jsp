<%@ include file="/WEB-INF/jsp/includes.jspf" %>

<h1>${taskList.name}</h1>
<ul>
	<c:forEach items="${taskList.tasks}" var="task">
		<li>${task.name}</li>	
	</c:forEach>
	<form:form modelAttribute="task" action="/tasklists/${taskList.id}/tasks/new.htm">
	<p>
		<form:input path="name" />
	</p>
	<p><input id="submit" type="submit" value="Create this list" /> or <a href="#">cancel</a></p> 
</form:form>
</ul>
