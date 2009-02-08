<%@ include file="/WEB-INF/jsp/includes.jspf" %>

<h1>Create a new task list</h1>
<form:form modelAttribute="taskList">
	<p>
		<label for="taskListName">Name your new list (e.g. Things I need to do today)</label>
	</p>
	<p>
		<form:input path="description" />
	</p>
	<p><input id="submit" type="submit" value="Add task" /> or <a href="#">cancel</a></p> 
</form:form>
