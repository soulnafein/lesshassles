<%@ include file="/WEB-INF/jsp/includes.jspf" %>

<script type="text/javascript">
	function processServerSideError() {
		alert("A technical problem occured. Please try later or contact the Administrator");
	}

	$(document).ready(function() {
	    $.validator.addMethod('validTaskDescription', function (value) { 
		    return /^[a-zA-Z0-9.'\(\)\-\s]+$/.test(value); 
		}, 'Please enter a valid task name.');

		initTasklistUpdateWidget();
				
		initAssignTaskWidget();
		
		initTaskAddWidget();

		initTaskStatusChangeWidget();
		
	});

	function initTaskStatusChangeWidget() {
		$("#tasks input[type=checkbox]").change(function() {
			var taskDescription = $(this).siblings("span");
			var taskId = $(this).parent().attr("id").replace("task","");
			taskDescription.toggleClass("completed");

			var url = $("form#assignTask").attr("action");
			url = url.replace(/tasks\/[^.]*/, "tasks/"+taskId+"-changeStatus");
			
			if ($(this).attr("checked") == false)  {
				$.get(url,{status: "Open"});
				
			} else {
				$.get(url,{status: "Completed"});
			}
			
		});
	}
	
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

	function initAssignTaskWidget() {
		var form = "form#assignTask";
		var textbox = form + " #searchBox"; 
		var assignTaskButton = "img.assignTask";
		var cancelButton =  form + " ._cancel";
		var assignee = form + " #assignee";  
		
		$(assignTaskButton).tooltip({
			showURL:false
		});

		$(assignTaskButton).click(function () {
			$(this).after($(form).remove());
			$(form).show();
			initAssignTaskWidget();
		});	
		
		$(cancelButton).click(function() {
			$(this).parent().hide();
		});

		var users = [
			     		<c:forEach items="${users}" var="user">
			     			{id:"${user.id}",fullname:"${user.fullname}"},
			     		</c:forEach>
			     		{id:"0",fullname:""}];
 		
		$(textbox).autocomplete(users, {
			minChars: 0,
			width: 310,
			matchContains: true,
			autoFill: false,
			mustMatch: true,
			formatItem: function(row, i, max) {
				return row.fullname;
			},
			formatMatch: function(row, i, max) {
				return row.fullname;
			},
			formatResult: function(row) {
				return row.fullname;
			}
		});

		$(textbox).result(function(event, user, formatted) {
			$(assignee).val(user.id);
		});

		$(form).submit(function() {
			var options = 	{ 
					dataType:  'json', 
				   	success:   processAssignTask,
				   	clearForm: false,
				   	error: 	processServerSideError
			};
			var action = "assign";
			if ($(textbox).val() == "") {
				action = "deassign";	
				options.success = processDeassignTask;	
			}

			var oldAction = $(this).attr("action");
			var taskId = $(form).parent().attr("id").replace("task","");
			var newAction = oldAction.replace(/tasks\/(\d)+-[^\.]*/, "tasks/"+taskId+"-"+action);
			$(this).attr("action", newAction);

			$(this).ajaxSubmit(options);

	        return false;
	    });

		function processAssignTask(data) {
			var fullname = $(textbox).val();
			$(form).siblings(assignTaskButton)
						.attr("src", "/images/user_gray.gif")
						.attr("alt", "Assigned to " + fullname)
						.tooltip({
							showURL:false
						});

			$(cancelButton).click();
		}

		function processDeassignTask(data) {
			$(form).siblings(assignTaskButton)
						.attr("src", "/images/user_go.gif")
						.attr("alt", "Assign task")
						.tooltip({
							showURL:false
						});

			$(cancelButton).click();
		}
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
	    	 dataType:  'json', 
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
			
		    $(taskList).append("<li>" + data.task.description + "</li>");
		    $(taskList).children("li:last").effect("highlight", {}, 1000);
		    $(cancelLink).click();
		}
	}	
</script> 
