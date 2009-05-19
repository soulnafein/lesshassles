<%@ include file="/WEB-INF/jsp/includes.jspf" %>

<div class="span-7">
&nbsp;
</div>
<div id="taskLists" style="text-align:center" class="span-13 ui-accordion ui-widget ui-helper-reset">
	<h2 class="ui-accordion-header ui-helper-reset ui-state-active ui-corner-top"><a>Login</a></h2>
    <div class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom ui-accordion-content-active">

    <p>Valid users:</p>
    <ul>
      <li>username <strong>philippa@lesshassles.com</strong>, password <strong>testtest</strong></li>
      <li>username <strong>david@lesshassles.com</strong>, password <strong>testtest</strong></li>
    </ul>
    <c:if test="${not empty param.login_error}">
      <div class="error">
      <p>Your login attempt was not successful, try again.</p>
      <p>Reason: ${SPRING_SECURITY_LAST_EXCEPTION.message}.</p>
    </div>
    </c:if>

    <form name="f" action="<c:url value='/session/login.htm'/>" method="POST">
      <dl>
      <dt>
        <label for="j_username">User:</label>
      </dt>
      <dd>
        <input type='text' class="text" name='j_username' value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>' />
      </dd>
      <dt>
        <label for="password">Password:</label>
      </dt>
      <dd>
        <input type='password' class="text" name='j_password'>
      </dd>
      </dl>  
      <p>
        <input type="checkbox" name="_spring_security_remember_me">
        <label for="_spring_security_remember_me">Don't ask for my password for two weeks</label>
      </p>
      
      <p>
        <input name="submit" value="Sign in" type="submit" />
        <input name="reset" value="Reset fields" type="reset" />
      </p>
    </form>
  </div>
</div>
<div class="span-7 last">
  &nbsp;
</div>
