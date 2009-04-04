<%@ include file="/WEB-INF/jsp/includes.jspf" %>

<h1>Tasks assigned to you</h1>
<ul id="tasksAssignedToUser">
	<c:forEach items="${tasksAssignedToUser}" var="task">
		<li>${task.description}</li>	
	</c:forEach>
</ul>