<%@ include file="/WEB-INF/jsp/includes.jspf" %>
<h1>Login</h1>

<p>Valid users:</p>
<ul>
	<li>username <strong>david</strong>, password <strong>newyork</strong></li>
	<li>username <strong>alex</strong>, password <strong>newjersey</strong></li>
	<li>username <strong>tim</strong>, password <strong>illinois</strong></li>
</ul>
<c:if test="${not empty param.login_error}">
	<p>Your login attempt was not successful, try again.</p>
	<p>Reason: ${SPRING_SECURITY_LAST_EXCEPTION.message}.</p>
</c:if>

<form name="f" action="<c:url value='/session/login.htm'/>" method="POST">
	<p>
		<label for="j_username">User:</label>
		<input type='text' name='j_username' value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>' />
	</p>
	<p>
		<label for="password">Password:</label>
		<input type='password' name='j_password'>
	</p>
	<p>
		<input type="checkbox" name="_spring_security_remember_me">
		<label for="_spring_security_remember_me">Don't ask for my password for two weeks</label>
	</p>
	
	<p>
		<input name="submit" type="submit" />
		<input name="reset" type="reset" />
	</p>
</form>