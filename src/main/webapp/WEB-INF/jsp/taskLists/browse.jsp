<%@ include file="/WEB-INF/jsp/includes.jspf" %>

<h1>Your task lists</h1>
<a href="/tasklists/new.htm">Create a new task list</a>
<ul id="taskLists">
	<c:forEach items="${taskLists}" var="taskList">
		<li><a href="/tasklists/${taskList.id}.htm">${taskList.name}</a></li>	
	</c:forEach>
</ul>


