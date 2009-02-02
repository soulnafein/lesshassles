<%@ include file="/WEB-INF/jsp/includes.jspf" %>

<h1>Create a new task list</h1>
<form:form modelAttribute="taskList">
	<p>
		<label for="taskListName">Name your new list (e.g. Things I need to do today)</label>
	</p>
	<p>
		<form:input path="name" />
	</p>
	<p><input id="submit" type="submit" value="Create this list" /> or <a href="#">cancel</a></p> 
</form:form>
