<script type="text/javascript">
$().ready(function() {
	$.validator.addMethod('validTaskListName', function (value) { 
	    return /^[a-zA-Z0-9.'\(\)\-\s]+$/.test(value); 
	}, 'Please enter a valid task list name.');

	$("#taskList").validate({
		rules: {
			name: {
				required: true,
				validTaskListName: true
			}
		},
		messages: {
			name: {
				required: "Please enter a name for your new list.",
				validTaskListName: "The names you have chosen contains invalid characters (allowed characters: letters, digits, spaces and ().'-)"
			}
		}
	});
});
</script>