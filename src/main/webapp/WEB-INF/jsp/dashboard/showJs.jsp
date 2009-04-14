<script type="text/javascript">
	<%@ include file="/WEB-INF/jsp/tasks/tasksWidgetsJs.jsp" %>
	
	$(document).ready(function() {
		$("#sidebar").accordion();

		initAssignTaskWidget();
		
		initTaskStatusChangeWidget();
		
	});
	
</script>