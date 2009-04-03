<%@ include file="/WEB-INF/jsp/includes.jspf" %>

<script type="text/javascript"> 
	$(document).ready(function() { 
	    $('#task').ajaxForm({ 
	    	 dataType:  'json', 
	         success:   processAddTaskForm,
	         clearForm: true,
	         error: 	processServerSideError,
	         beforeSubmit: validateForm
	    });

		var users = [
		     		<c:forEach items="${users}" var="user">
		     			{id:"${user.id}",fullname:"${user.fullname}"},
		     		</c:forEach>
		     		{id:"0",fullname:""}];

		$('img.assignTask').tooltip({
			showURL:false
		});
	    
		$('img.assignTask').click(function () {
			$(this).after($('#searchUser').remove());
			$('#searchUser').show();
			$('#searchUser #cancel').click(function() {
				$(this).parent().hide();
			})
			$("#searchUser #searchBox").autocomplete(users, {
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

			$("#searchUser #searchBox").result(function(event, user, formatted) {
				$("#searchUser #assignee").val(user.id);
			});

			$('#assignTask').submit(function() {
				var options = 	{ 
						dataType:  'json', 
					   	success:   processAssignTask,
					   	clearForm: false,
					   	error: 	processServerSideError
				};
				var action = "assign";
				if ($("#searchUser #searchBox").val() == "") {
					action = "deassign";	
					options.success = processDeassignTask;	
				}

				var oldAction = $(this).attr("action");
				var taskId = $("#searchUser").parent().attr("id").replace("task","");
				var newAction = oldAction.replace(/tasks\/(\d)+-[^\.]*/, "tasks/"+taskId+"-"+action);
				$(this).attr("action", newAction);

				

				$(this).ajaxSubmit(options);

		        return false;
		    });
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
		})
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
		
	    $('#tasks').append("<li>" + data.task.description + "</li>");
	    $('#tasks').children("li:last").effect("highlight", {}, 1000);
	    $('a#hideAddTaskForm').click();
	}

	function processAssignTask(data) {
		var fullname = $("#searchUser #searchBox").val();
		$("div#searchUser").siblings("img.assignTask")
					.attr("src", "/images/user_gray.gif")
					.attr("alt", "Assigned to " + fullname)
					.tooltip({
						showURL:false
					});

		$("div#searchUser #cancel").click();
	}

	function processDeassignTask(data) {
		$("div#searchUser").siblings("img.assignTask")
					.attr("src", "/images/user_go.gif")
					.attr("alt", "Assign task")
					.tooltip({
						showURL:false
					});

		$("div#searchUser #cancel").click();
	}
</script> 
