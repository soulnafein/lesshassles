<%@ include file="/WEB-INF/jsp/includes.jspf" %>

<script type="text/javascript">
	
	<%@ include file="/WEB-INF/jsp/tasks/tasksWidgetsJs.jsp" %>
	
	$(document).ready(function() {
	    $.validator.addMethod('validTaskDescription', function (value) { 
		    return /^[a-zA-Z0-9.'\(\)\-\s]+$/.test(value); 
		}, 'Please enter a valid task name.');

		initTasklistUpdateWidget();
				
		initAssignTaskWidget();
		
		initTaskAddWidget();

		initTaskStatusChangeWidget();

		initSetDeadlineWidget();
		
	});
	
	function initTasklistUpdateWidget() {
		var form = "form#updateTaskList";
		var textbox = form + " input[name='taskListName']";
		var taskListTitle = "h1#taskListName";
		var cancelLink = form + " ._cancel"; 
		
		// Tasklist update 
		$("#taskListName").dblclick(function() {
			$(this).hide();
			$(form).show();
		});

	    $(form).validate({
			rules: {
				taskListName: {
					required: true,
					validTaskDescription: true
				}
			},
			messages: {
				taskListName: {
					required: "Please enter a valid name for your task list.",
					validTaskDescription: "The name you provided contains invalid characters (allowed characters: letters, digits, spaces and ().'-)"
				}
			}
		});

		$(form).ajaxForm({
			success: function(data) {
						var taskListName = $(textbox).val();
						$(taskListTitle).text(taskListName);
						$(cancelLink).click();
					 },
			error: processServerSideError,
			beforeSubmit: function() {
						 	return $(form).valid();
					 	  }
		});

		$(cancelLink).click(function() {
			$(taskListTitle).show();
			$(form).hide();
			$(textbox).val($(taskListTitle).text());
		});		
	}

	function initTaskAddWidget() {
		var form = "form#task";
		var taskList = "#tasks";
		var addTaskLink = "a#showAddTaskForm";
		var cancelLink = "a#hideAddTaskForm"; 
		 
	    $(addTaskLink).click(function() {
	    	$(this).hide();
	    	$(form).show(2);
	    });

	    $(form).ajaxForm({ 
	         success:   processAddTaskForm,
	         clearForm: true,
	         error: 	processServerSideError,
	         beforeSubmit: this.validateForm
	    });
	    
	    $(cancelLink).click(function() {
	    	$(addTaskLink).show();
	    	$(form).hide(2);
	    });
	    
	    $(form).validate({
			rules: {
				description: {
					required: true,
					validTaskDescription: true
				}
			},
			messages: {
				description: {
					required: "Please enter a description for your task.",
					validTaskListName: "The description you provided contains invalid characters (allowed characters: letters, digits, spaces and ().'-)"
				}
			}
		});

		function validateForm() {
			return $(taskList).valid();
		}

		function processAddTaskForm(data) {
			if (data.error) {
				alert(data.error.message);
			}

			$(taskList).append(data);
		    $(taskList).children("li:last")
		    		.effect("highlight", {}, 1000);
    		$(taskList).children("li:last > img.assign").tooltip({
    			showURL:false
    		});
		    		
		    $(cancelLink).click();
		}
	}	
</script> 
