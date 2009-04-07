<%@ include file="/WEB-INF/jsp/includes.jspf" %>

<h1>${taskList.name}</h1>
<form id="assignTask" action="/tasklists/${taskList.id}/tasks/0-assign.htm">
	<ul id="tasks">
		<c:forEach items="${taskList.tasks}" var="task">
			<li id="task${task.id}">
				<input type="checkbox" ${task.isCompleted ? "checked='checked'" : ""} /> 
				${task.description}
				<c:choose>
					<c:when test="${task.assignee == null}">
						<img class="assignTask" src="/images/user_go.gif" title="Assign task" />
					</c:when>
					<c:otherwise>
						<img class="assignTask" src="/images/user_gray.gif" title="Assigned to ${task.assignee.fullname}" />
					</c:otherwise>
				</c:choose>
			</li>	
		</c:forEach>
	</ul>
	<div id="searchUser" style="display:none">
		<input id="searchBox" type="text" value="" />
		<input type="submit" value="Assign" />
		<input id="cancel" type="button" value="Cancel" />
		<input id="assignee" name="assignee" type="hidden" value="" />
	</div>
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

