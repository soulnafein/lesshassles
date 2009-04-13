<%@ include file="/WEB-INF/jsp/includes.jspf" %>
<%@ page import="com.lesshassles.model.TaskStatus" %>

<h1 id="taskListName">${taskList.name}</h1>
<form id="updateTaskList" action="/tasklists/${taskList.id}-edit.htm" style="display:none">
	<input type="text"  name="taskListName" value="${taskList.name}" />
	<input type="submit" value="Save changes" />
	<a id="cancel" href="#">cancel</a>
</form>
<ul id="tasks">
	<c:forEach items="${taskList.tasks}" var="task">
		<li id="task${task.id}">
			<input type="checkbox" ${task.status == "Completed" ? "checked='checked'" : ""} /> 
			<span ${task.status == "Completed" ? "class='completed'" : ""}>${task.description}</span>
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
<form id="assignTask" action="/tasklists/${taskList.id}/tasks/0-assign.htm" style="display:none">
	<input id="searchBox" type="text" value="" />
	<input type="submit" value="Assign" />
	<input id="cancel" type="button" value="Cancel" />
	<input id="assignee" name="assignee" type="hidden" value="" />
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

