<%@ include file="/WEB-INF/jsp/includes.jspf" %>

	function processServerSideError() {
		alert("A technical problem occured. Please try later or contact the Administrator");
	}

	function initTaskStatusChangeWidget() {
		var form = "form#assignTask"; 
		var checkbox = "input._statusChange";
		
		$(checkbox).live("change",function() {
			var taskDescription = $(this).siblings("span");
			var taskId = $(this).parent().attr("id").replace("task","");
			taskDescription.toggleClass("completed");

			var url = $(form).attr("action");
			url = url.replace(/tasks\/[^.]*/, "tasks/"+taskId+"-changeStatus");
			
			if ($(this).attr("checked") == false)  {
				$.get(url,{status: "Open"});
				
			} else {
				$.get(url,{status: "Completed"});
			}
			
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

		$(assignTaskButton).live("click",function () {
			$(this).after($(form).remove());
			$(form).show();
			rebindFormEvents();
		});	

		function rebindFormEvents() {
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
		}
		
		function processAssignTask(data) {
			var fullname = $(textbox).val();
			$(form).siblings(assignTaskButton)
						.attr("src", "/images/user_gray.gif")
						.attr("alt", "Assigned to " + fullname);

			$(cancelButton).click();
		}

		function processDeassignTask(data) {
			$(form).siblings(assignTaskButton)
						.attr("src", "/images/user_go.gif")
						.attr("alt", "Assign task");

			$(cancelButton).click();
		}
	}