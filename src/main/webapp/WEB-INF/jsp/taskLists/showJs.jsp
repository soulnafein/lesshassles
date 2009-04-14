<%@ include file="/WEB-INF/jsp/includes.jspf" %>

<script type="text/javascript"> 
	$(document).ready(function() { 
	    $('#task').ajaxForm({ 
	         success:   processAddTaskForm,
	         clearForm: true,
	         error: 	processServerSideError,
	         beforeSubmit: validateForm
	    });

		$('img.assignTask').tooltip({
			showURL:false
		});
	    
		$('img.assignTask').live("click",function () {
			$(this).after($('#assignTask').remove());
			$('#assignTask').show();
			initAssignTaskWidget();
		});	
		
	    $('a#showAddTaskForm').click(function() {
	    	$(this).hide();
	    	$('form#task').show(2);
	    });
	    
	    $('a#hideAddTaskForm').click(function() {
	    	$('a#showAddTaskForm').show();
	    	$('form#task').hide(2);
	    });
	    
	    $.validator.addMethod('validTaskDescription', function (value) { 
		    return /^[a-zA-Z0-9.'\(\)\-\s]+$/.test(value); 
		}, 'Please enter a valid task name.');
	    
	    $("#task").validate({
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

		$("#tasks input[type=checkbox]").live("change",function() {
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
	});

	function validateForm() {
		return $("#task").valid();
	}
	
	function processServerSideError() {
		alert("A technical problem occured. Please try later or contact the Administrator");
	}
	
	function processAddTaskForm(data) {
		if (data.error) {
			alert(data.error.message);
		}
		
	    $('#tasks').append(data);
	    $('#tasks').children("li:last").effect("highlight", {}, 1000);
	    $('a#hideAddTaskForm').click();
	}

	function processAssignTask(data) {
		var fullname = $("#searchUser #searchBox").val();
		$("#assignTask").siblings("img.assignTask")
					.attr("src", "/images/user_gray.gif")
					.attr("alt", "Assigned to " + fullname)
					.tooltip({
						showURL:false
					});

		$("#assignTask #cancel").click();
	}

	function processDeassignTask(data) {
		$("#assignTask").siblings("img.assignTask")
					.attr("src", "/images/user_go.gif")
					.attr("alt", "Assign task")
					.tooltip({
						showURL:false
					});

		$("#assignTask #cancel").click();
	}

	function initAssignTaskWidget() {
		$('#assignTask #cancel').click(function() {
			$(this).parent().hide();
		});

		var users = [
			     		<c:forEach items="${users}" var="user">
			     			{id:"${user.id}",fullname:"${user.fullname}"},
			     		</c:forEach>
			     		{id:"0",fullname:""}];
 		
		$("#assignTask #searchBox").autocomplete(users, {
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

		$("#assignTask #searchBox").result(function(event, user, formatted) {
			$("#assignTask #assignee").val(user.id);
		});

		$('#assignTask').submit(function() {
			var options = 	{ 
					dataType:  'json', 
				   	success:   processAssignTask,
				   	clearForm: false,
				   	error: 	processServerSideError
			};
			var action = "assign";
			if ($("#assignTask #searchBox").val() == "") {
				action = "deassign";	
				options.success = processDeassignTask;	
			}

			var oldAction = $(this).attr("action");
			var taskId = $("#assignTask").parent().attr("id").replace("task","");
			var newAction = oldAction.replace(/tasks\/(\d)+-[^\.]*/, "tasks/"+taskId+"-"+action);
			$(this).attr("action", newAction);

			

			$(this).ajaxSubmit(options);

	        return false;
	    });
	}
</script> 
