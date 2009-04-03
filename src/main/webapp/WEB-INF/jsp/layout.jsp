<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
    "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" >
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
		<title><tiles:getAsString name="title"/></title>
		<link href="/yahoo-reset.css" rel="stylesheet" type="text/css" />
		<link href="/css/style.css" rel="stylesheet" type="text/css" />
		<link href="/css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
		<link href="/css/jquery.tooltip.css" rel="stylesheet" type="text/css" />
		<script src="/js/jquery.js" type="text/javascript"></script>
		<script src="/js/jquery.validate.pack.js" type="text/javascript"></script>
		<script src="/js/jquery.form.js" type="text/javascript"></script>
		<script src="/js/jquery.ui.all.js" type="text/javascript"></script>
		<script src="/js/jquery.autocomplete.js" type="text/javascript"></script>
		<script src="/js/jquery.tooltip.js" type="text/javascript"></script>
		<tiles:insertAttribute ignore="true" name="scripts" />
	</head>
	<body>
		<div id="content">
			<tiles:insertAttribute name="body" />
		</div>
	</body>
</html>