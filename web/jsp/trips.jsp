<%--
  Created by IntelliJ IDEA.
  User: Михаил
  Date: 2/16/2016
  Time: 10:57 AM
  To change this template use File | Settings | File Templates.
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${lang}" scope="session"/>
<fmt:setBundle basename="resources.text" var="rb" />
<html>
<head>
    <title><fmt:message key="title.trip" bundle="${ rb }" /></title>
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
    <script src="/js/epamtravel.js"></script>
    <![endif]-->
    <link href="/css/style.css" rel="stylesheet">
</head>
<body>
<%@ include file="/jsp/header.jsp"%>
<section>
    <span style="text-align: center;"><h1><fmt:message key="label.trip.list" bundle="${rb}" /></h1></span>
    <div class="before-grid">
    <hr>
    </div>
    <div class="grid">
        <c:forEach var="trip" items="${trips}" varStatus="status">
        <figure class="effect-milo">
            <img src="${trip.pathImage}" alt="img"/>
            <figcaption>
                <h2><span><c:out value="${ trip.name }" /></span></h2>
                <h3><c:out value="${ trip.price }" />$</h3>
                <p><c:out value="${ trip.summary }" /></p>
                <a href="travel?command=trip_full&id=<c:out value="${ trip.id }" />"></a>
            </figcaption>
        </figure>
            </c:forEach>
    </div>
    <div class="after-grid">
    <hr>
    </div>
</section>

<%@ include file="/jsp/footer.jsp"%>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/js/bootstrap.min.js"></script>
</body></html>

