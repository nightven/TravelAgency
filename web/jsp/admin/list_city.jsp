<%--
  Created by IntelliJ IDEA.
  User: Михаил
  Date: 2/16/2016
  Time: 10:57 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customtags"%>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="resources.text" var="rb" />
<html>
<head>
    <title><fmt:message key="title.admin.city-list" bundle="${ rb }" /></title>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <!-- Bootstrap -->
    <link href="css/bootstrap.min.css" rel="stylesheet">
    <link href='https://fonts.googleapis.com/css?family=Oswald' rel='stylesheet' type='text/css'>
    <link href='https://fonts.googleapis.com/css?family=Source+Sans+Pro' rel='stylesheet' type='text/css'>

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    <link href="/css/style.css" rel="stylesheet">
</head>
<body>
<%@ include file="/jsp/header.jsp"%>
<section>
    <div class="container-fluid">
        <span style="text-align: center;"><h1><fmt:message key="title.admin.city-list" bundle="${ rb }" /></h1></span>
        <ctg:before-grid />
        <ctg:vacation-table>
        <tr>
            <th><fmt:message key="label.admin.cities.edit.id" bundle="${ rb }" /></th>
            <th><fmt:message key="label.admin.cities.edit.name" bundle="${ rb }" /></th>
            <th><fmt:message key="label.admin.countries.edit.name" bundle="${ rb }" /></th>
        </tr>
        </thead>
        <tbody class="admin-table-list">
        <c:forEach var="city" items="${cities}">
        <tr onclick="window.location.href='travel?command=edit_city_page&id=<c:out value="${ city.id }" />'; return false">
            <td><c:out value="${ city.id }" /></td>
            <td><c:out value="${ city.name }" /></td>
            <td><c:out value="${ city.country.nameCountry }" /></td>
        </tr>
        </c:forEach>
        </ctg:vacation-table>
        <div class="after-grid">
            <hr>
        </div>
        <div class="col-sm-2 col-sm-offset-5 button-back">
            <button type="button" class="btn btn-default" onclick="history.back()"><fmt:message key="label.vacation.button.back" bundle="${rb}" /></button>
        </div>
    </div>
</section>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/js/bootstrap.min.js"></script>

<%@ include file="/jsp/footer.jsp"%>

</body></html>

