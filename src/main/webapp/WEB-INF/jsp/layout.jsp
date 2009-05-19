<%@ taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles" %>

<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
    "http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" >
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1" />
		<title><tiles:getAsString name="title"/></title>
	  <link rel="stylesheet" href="/css/blueprint/screen.css" type="text/css" media="screen, projection">
    <link rel="stylesheet" href="/css/blueprint/print.css" type="text/css" media="print"/> 
    <!--[if IE]>
      <link rel="stylesheet" href="/css/blueprint/ie.css" type="text/css" media="screen, projection">
    <![endif]-->	
	  <link rel="stylesheet" href="/css/style.css" type="text/css"> 
		
		<script type="text/javascript" src="/js/jquery-1.3.2.min.js"></script>
		<link type="text/css" href="/css/cupertino/jquery-ui-1.7.1.custom.css" rel="Stylesheet" />
		<script type="text/javascript" src="/js/jquery-ui-1.7.1.custom.min.js"></script>
		
		<script src="/js/jquery.validate.pack.js" type="text/javascript"></script>
		<script src="/js/jquery.form.js" type="text/javascript"></script>
		
		<link href="/css/jquery.autocomplete.css" rel="stylesheet" type="text/css" />
		<script src="/js/jquery.autocomplete.js" type="text/javascript"></script>
		
		<link href="/css/jquery.tooltip.css" rel="stylesheet" type="text/css" />
		<script src="/js/jquery.tooltip.js" type="text/javascript"></script>
		<tiles:insertAttribute ignore="true" name="scripts" />
	</head>
	<body>
    <div id="header" class="box-1"> 
      <ul id="mainNavigation"> 
        <li><a href="/">Dashboard</a></li>
        <li><a href="/tasklists/new.htm">Create a new List</a></li>
        <li><a href="/session/logout.htm">Logout</a></li>
      </ul>
    </div>
		<div class="container">
      <div class="span-24 last">
			  <tiles:insertAttribute name="body" />
      </div>
		</div>
	</body>
</html>
