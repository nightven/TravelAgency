<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="resources.text" var="rb" />
<header>
    <div class="header-content">
        <c:choose>
            <c:when test="${ not empty user }">
                <div class="header-logo"><a href="travel?command=tour_list" class="logo"><img src="/images/logo_gray-blue_epamtravel_80px.svg" width="170px" height="48px"></a></div>
            </c:when>
            <c:otherwise>
                <div class="header-logo"><a href="travel?command=forward&page=login" class="logo"><img src="/images/logo_gray-blue_epamtravel_80px.svg" width="170px" height="48px"></a></div>
            </c:otherwise>
        </c:choose>
        <!--<div class="header-logo"><a href="travel" class="logo"><img src="/images/logo_gray-blue_epamtravel_80px.svg" width="170px" height="48px"></a></div>-->
        <div class="header-menu">
            <nav>
                <ul class="title-menu">
                    <li class="menu-item"><a href="travel?command=tour_list" class="menu-link"><fmt:message key="menu.item1" bundle="${ rb }" /></a></li>
                    <li class="menu-item"><a href="travel?command=vacation_list" class="menu-link"><fmt:message key="menu.item2" bundle="${ rb }" /></a></li>
                    <li class="menu-item"><a href="travel?command=trip_list" class="menu-link"><fmt:message key="menu.item3" bundle="${ rb }" /></a></li>
                    <li class="menu-item"><a href="travel?command=shopping_list" class="menu-link"><fmt:message key="menu.item4" bundle="${ rb }" /></a></li>
                    <li class="menu-item"><a href="travel?command=forward&page=upload" class="menu-link"><fmt:message key="menu.item5" bundle="${ rb }" /></a></li>
                </ul>
            </nav>
            <div class="header-utility">
                <div class="localization">
                    <a data-toggle="dropdown" href="#"><img class="localization-icon" src="/images/globe.png" alt="Localization"></a>
                    <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
                        <li class="localization-item"><a href="travel?command=english_language" class="localization-link">English</a></li>
                        <li class="localization-item"><a href="travel?command=russian_language" class="localization-link">Русский</a></li>
                    </ul>
                </div>
                <div class="search"><img class="search-icon" src="/images/search.png" alt="Search"></div>
            </div>
            <div class="user">
            <c:choose>
            <c:when test="${ not empty user }">
                <a data-toggle="dropdown" href="#" class="user-link"><div class="username">${user}</div></a>
                <ul class="dropdown-menu" role="menu" aria-labelledby="dLabel">
                    <li class="usermenu-item"><a class="usermenu-link" style="background: #fff;">Пользователь</a></li>
                    <li class="usermenu-item divider"></li>
                    <c:if test="${ role==1 }"><li class="usermenu-item"><a href="travel?command=forward&page=admin_panel" class="usermenu-link">Администрирование</a></li></c:if>
                    <li class="usermenu-item"><a href="#" class="usermenu-link">Изменить пароль</a></li>
                </ul>
                <div class="log-action"><a class="menu-link" href="travel?command=logout"><fmt:message key="action.logout" bundle="${ rb }" /></a></div>
            </c:when>
            <c:otherwise>
                <div class="register-action"><a class="menu-link" href="travel?command=forward&page=register"><fmt:message key="action.registration" bundle="${ rb }" /></a></div>
                <div class="log-action"><a class="menu-link" href="travel?command=forward&page=login"><fmt:message key="action.login" bundle="${ rb }" /></a></div>

            </c:otherwise>
        </c:choose>
            </div>
        </div>
    </div>
</header>

