<%@ include file="/WEB-INF/jsp/includes.jspf" %>

<li id="task${task.id}">
	<input type="checkbox" class="_statusChange" ${task.status == "Completed" ? "checked='checked'" : ""} /> 
	<span ${task.status == "Completed" ? "class='completed'" : ""}>${task.description}</span>
	<c:choose>
		<c:when test="${task.assignee == null}">
			<img class="assignTask" src="/images/user_go.gif" title="Assign task" />
		</c:when>
		<c:otherwise>
			<img class="assignTask" src="/images/user_gray.gif" title="Assigned to ${task.assignee.fullname}" />
		</c:otherwise>
	</c:choose>
	<img class="setDeadline" src="/images/calendar_add.gif" title="Set or change a deadline for this task" />
	<span class="deadline">
	<c:choose>
		<c:when test="${task.deadline != null}">
			Due on: <fmt:formatDate type="date" value="${task.deadline}" />
		</c:when>
	</c:choose>
	</span>
</li>	