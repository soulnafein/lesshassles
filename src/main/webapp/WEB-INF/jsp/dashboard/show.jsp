<%@ include file="/WEB-INF/jsp/includes.jspf" %>

<h1>Your task lists</h1>
<a href="/tasklists/new.htm">Create a new task list</a>
<ul id="taskLists">
	<c:forEach items="${taskLists}" var="taskList">
		<li>
			<form action="/tasklists/${taskList.id}-delete.htm" method="get">
				<a href="/tasklists/${taskList.id}.htm">${taskList.name}</a>
				<input type="image" src="/images/cross.gif" title="Delete" />
			</form>
		</li>	
	</c:forEach>
</ul>

<h1>Tasks assigned to you</h1>
<ul id="tasksAssignedToUser">
	<c:forEach items="${tasksAssignedToUser}" var="task">
		<li>${task.description}</li>	
	</c:forEach>
</ul>

<h1>Tasks that you assigned to others</h1>
<ul id="tasksAssignedToOtherUsers">
	<c:forEach items="${tasksAssignedToOtherUsers}" var="task">
		<li>${task.description}</li>	
	</c:forEach>
</ul>