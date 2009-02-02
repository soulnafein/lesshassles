<%@ include file="/WEB-INF/jsp/includes.jspf" %>

<h1>${taskList.name}</h1>
<ul>
	<c:forEach items="${taskList.tasks}" var="task">
		<li>${task.name}</li>	
	</c:forEach>
</ul>
