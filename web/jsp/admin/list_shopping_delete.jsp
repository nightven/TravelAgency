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
    <title><fmt:message key="title.admin.delete-shopping" bundle="${ rb }" /></title>
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
        <span style="text-align: center;"><h1><fmt:message key="title.admin.delete-shopping" bundle="${ rb }" /></h1></span>
        <div class="before-grid">
            <hr>
        </div>
        <div class="table-responsive">
            <table class="table table-condensed table-hover">
                <thead class="admin-table-list">
                <tr>
                    <th><fmt:message key="label.admin.tour-id" bundle="${ rb }" /></th>
                    <th><fmt:message key="label.admin.create-tour.tour.name" bundle="${ rb }" /></th>
                    <th><fmt:message key="label.admin.create-tour.tour.departure-date" bundle="${ rb }" /></th>
                    <th><fmt:message key="label.admin.create-tour.tour.arrival-date" bundle="${ rb }" /></th>
                    <th><fmt:message key="label.admin.create-tour.tour.destination-city" bundle="${ rb }" /></th>
                    <th><fmt:message key="label.admin.create-tour.tour.destination-country" bundle="${ rb }" /></th>
                    <th><fmt:message key="label.admin.create-tour.tour.transport" bundle="${ rb }" /></th>
                    <th><fmt:message key="label.admin.create-tour.tour.last-minute" bundle="${ rb }" /></th>
                    <th><fmt:message key="label.admin.tour-image-path" bundle="${ rb }" /></th>
                </tr>
                </thead>
                <tbody class="admin-table-list">
                <c:forEach var="shopping" items="${shoppings}">
                    <tr data-toggle="modal" data-target="#delete-modal-<c:out value="${ shopping.id }" />">
                        <td><c:out value="${ shopping.id }" /></td>
                        <td><c:out value="${ shopping.name }" /></td>
                        <td><c:out value="${ shopping.departureDate }" /></td>
                        <td><c:out value="${ shopping.arrivalDate }" /></td>
                        <td><c:out value="${ shopping.destinationCity }" /></td>
                        <td><c:out value="${ shopping.destinationCountry }" /></td>
                        <td><c:out value="${ shopping.transport }" /></td>
                        <td><c:choose><c:when test="${ shopping.lastMinute }">&#10004;</c:when><c:otherwise>&#10008;</c:otherwise></c:choose></td>
                        <td><c:out value="${ shopping.pathImage }" /></td>
                        <div class="modal fade" id="delete-modal-<c:out value="${ shopping.id }" />" tabindex="-1" role="dialog" aria-labelledby="delete-modal-label" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                        <h4 class="modal-title" id="delete-modal-label"><fmt:message key="label.admin.modal-delete-label" bundle="${ rb }" /></h4>
                                    </div>
                                    <div class="modal-body">
                                        <fmt:message key="label.admin.modal-delete-text" bundle="${ rb }" /> "<c:out value="${ shopping.name }" />" (ID: <c:out value="${ shopping.id }" />)?
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-default" data-dismiss="modal"><fmt:message key="label.admin.modal-cancel" bundle="${ rb }" /></button>
                                        <a href="travel?command=delete_shopping&id=<c:out value="${ shopping.id }" />"><button type="button" class="btn btn-danger"><fmt:message key="label.admin.modal-delete" bundle="${ rb }" /></button></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div class="after-grid">
            <hr>
        </div>
        <div class="col-sm-2 col-sm-offset-5 button-back">
            <button type="button" class="btn btn-default" onclick="history.back()"><fmt:message key="label.vacation.button.back" bundle="${rb}" /></button>
        </div>
    </div>
</section>

<%@ include file="/jsp/footer.jsp"%>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/js/bootstrap.min.js"></script>
</body></html>

