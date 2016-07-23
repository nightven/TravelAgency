<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="resources.text" var="rb" />
<footer>
    <div class="footer-content">
        <div class="copyright">&copy; <fmt:message key="footer.copyright" bundle="${ rb }" /></div>
    </div>
</footer>