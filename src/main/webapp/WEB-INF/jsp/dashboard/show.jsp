<%@ include file="/WEB-INF/jsp/includes.jspf" %>

<div id="taskLists">
	<h1>Your task lists</h1>
	<a href="/tasklists/new.htm">Create a new task list</a>
	<ul>
		<c:forEach items="${taskLists}" var="taskList">
			<li>
				<form action="/tasklists/${taskList.id}-delete.htm" method="get">
					<a href="/tasklists/${taskList.id}.htm">${taskList.name}</a>
					<input type="image" src="/images/cross.gif" title="Delete" />
				</form>
			</li>	
		</c:forEach>
	</ul>
</div>

<div id="sidebar">
	<h2><a href="#">Tasks assigned to you</a></h2>
	<div>
		<ul id="tasksAssignedToUser">
			<c:forEach items="${tasksAssignedToUser}" var="task">
				<%@ include file="/WEB-INF/jsp/tasks/show.jsp" %>	
			</c:forEach>
		</ul>
	</div>
	
	<h2><a href="#">Tasks that you assigned to others</a></h2>
	<div>
		<ul id="tasksAssignedToOtherUsers">
			<c:forEach items="${tasksAssignedToOtherUsers}" var="task">
				<%@ include file="/WEB-INF/jsp/tasks/show.jsp" %>	
			</c:forEach>
		</ul>
	</div>
	<form id="assignTask" action="/tasklists/0/tasks/0-assign.htm" style="display:none">
		<input id="searchBox" type="text" value="" />
		<input type="submit" value="Assign" />
		<input class="_cancel" type="button" value="Cancel" />
		<input id="assignee" name="assignee" type="hidden" value="" />
	</form>
</div>
