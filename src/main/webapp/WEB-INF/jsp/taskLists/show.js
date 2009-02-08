<script type="text/javascript"> 
	$(document).ready(function() { 
	    $('#task').ajaxForm({ 
	    	 dataType:  'json', 
	         success:   processAddTaskForm
	    }); 
	    
	    $('a#showAddTaskForm').click(function() {
	    	$(this).hide();
	    	$('form#task').show(2);
	    });
	    
	    $('a#hideAddTaskForm').click(function() {
	    	$('a#showAddTaskForm').show();
	    	$('form#task').hide(2);
	    });
	});

	function processAddTaskForm(data) { 
	    $('#tasks').append("<li>" + data.task.description + "</li>");
	    $('#tasks').children("li:last").effect("highlight", {}, 1000);
	    $('a#hideAddTaskForm').click();
	}
</script> 