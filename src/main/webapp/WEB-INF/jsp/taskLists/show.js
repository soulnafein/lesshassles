<script type="text/javascript"> 
	$(document).ready(function() { 
	    $('#task').ajaxForm({ 
	    	 dataType:  'json', 
	         success:   processAddTaskForm,
	         clearForm: true,
	         error: 	processServerSideError,
	         beforeSubmit: validateForm
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
		}, 'Please enter a valid task list name.');

		$("#task").validate({
			rules: {
				name: {
					required: true,
					validTaskDescription: true
				}
			},
			messages: {
				name: {
					required: "Please enter a description for your task.",
					validTaskListName: "The description you provided contains invalid characters (allowed characters: letters, digits, spaces and ().'-)"
				}
			}
		});
	});

	function validateForm() {
		alert("Experiment!");
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
</script> 
