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
    <title><fmt:message key="title.admin.edit-shopping" bundle="${ rb }" /></title>
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
        <span style="text-align: center;"><h1><fmt:message key="title.admin.edit-shopping" bundle="${ rb }" /></h1></span>
        <div class="before-grid">
            <hr>
        </div>
        <form id="edit-vacation-form" method="post" action="travel" enctype="multipart/form-data" accept-charset="utf-8">
            <input type="hidden" name="command" value="edit_shopping" />
            <input type="hidden" name="id" value="<c:out value="${ shopping.id }" />" />
            <div class="row">
                <div class="col-sm-3 col-sm-offset-3">
                    <div class="row">
                        <div class="form-group col-sm-12">
                            <label for="name"><fmt:message key="label.admin.create-tour.tour.name" bundle="${ rb }" /></label>
                            <input type="text" class="form-control" id="name" name="name" value="<c:out value="${ shopping.name }" />">
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-12">
                            <label for="summary"><fmt:message key="label.admin.create-tour.tour.summary" bundle="${ rb }" /></label>
                            <input type="text" class="form-control" id="summary" name="summary" value="<c:out value="${ shopping.summary }" />">
                        </div>
                    </div>
                    <div class="row">
                        <div class="form-group col-sm-12">
                            <label for="img"><fmt:message key="label.admin.create-tour.tour.image" bundle="${ rb }" /></label>
                            <input type="file" class="form-control" id="img" accept="image/jpeg" name="img">
                        </div>
                    </div>
                </div>
                <div class="col-sm-3">
                    <img id="img-preview" src="<c:out value="${ shopping.pathImage }" />" width="276px" height="207px" class="img-rounded pre-image">
                </div>
            </div>
            <div class="row">
                <div class="col-sm-6 col-sm-offset-3">
                    <a href="#" id="reset-img-preview"><button type="button" class="btn btn-danger del-button"><fmt:message key="label.admin.create-tour.tour.delete-img" bundle="${ rb }" /></button></a>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-sm-3 col-sm-offset-3">
                    <label for="departure-date"><fmt:message key="label.admin.create-tour.tour.departure-date" bundle="${ rb }" /></label>
                    <input type="date" class="form-control" id="departure-date" name="departure-date" value="<c:out value="${ shopping.departureDate }" />">
                </div>
                <div class="form-group col-sm-3">
                    <label for="arrival-date"><fmt:message key="label.admin.create-tour.tour.arrival-date" bundle="${ rb }" /></label>
                    <input type="date" class="form-control" id="arrival-date" name="arrival-date" value="<c:out value="${ shopping.arrivalDate }" />">
                </div>
            </div>
            <div class="row">
                <div class="form-group col-sm-3 col-sm-offset-3">
                    <label for="destination-country"><fmt:message key="label.admin.create-tour.tour.destination-country" bundle="${ rb }" /></label>
                    <input type="text" class="form-control" id="destination-country" name="destination-country" value="<c:out value="${ shopping.destinationCountry }" />">
                </div>
                <div class="form-group col-sm-3">
                    <label for="destination-city"><fmt:message key="label.admin.create-tour.tour.destination-city" bundle="${ rb }" /></label>
                    <input type="text" class="form-control" id="destination-city" name="destination-city" value="<c:out value="${ shopping.destinationCity }" />">
                </div>
            </div>
            <div class="row">
                <div class="form-group col-sm-3 col-sm-offset-3">
                    <label for="transport"><fmt:message key="label.admin.create-tour.tour.transport" bundle="${ rb }" /></label>
                    <select class="form-control" id="transport" name="transport">
                        <c:choose>
                            <c:when test="${ 'PLANE' == (shopping.transport) }"><option selected value="PLANE"><fmt:message key="label.transport.plane" bundle="${ rb }" /></option></c:when>
                            <c:otherwise><option value="PLANE"><fmt:message key="label.transport.plane" bundle="${ rb }" /></option></c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${ 'BOAT' == (shopping.transport) }"><option selected value="BOAT"><fmt:message key="label.transport.boat" bundle="${ rb }" /></option></c:when>
                            <c:otherwise><option value="BOAT"><fmt:message key="label.transport.boat" bundle="${ rb }" /></option></c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${ 'TRAIN' == (shopping.transport) }"><option selected value="TRAIN"><fmt:message key="label.transport.train" bundle="${ rb }" /></option></c:when>
                            <c:otherwise><option value="TRAIN"><fmt:message key="label.transport.train" bundle="${ rb }" /></option></c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${ 'BUS' == (shopping.transport) }"><option selected value="BUS"><fmt:message key="label.transport.bus" bundle="${ rb }" /></option></c:when>
                            <c:otherwise><option value="BUS"><fmt:message key="label.transport.bus" bundle="${ rb }" /></option></c:otherwise>
                        </c:choose>
                    </select>
                </div>
                <div class="form-group col-sm-3">
                    <label for="last-minute"><fmt:message key="label.admin.create-tour.tour.last-minute" bundle="${ rb }" /></label>
                    <c:choose>
                        <c:when test="${ shopping.lastMinute }"><input checked type="checkbox" class="form-control form-checkbox" id="last-minute" name="last-minute"></c:when>
                        <c:otherwise><input type="checkbox" class="form-control form-checkbox" id="last-minute" name="last-minute"></c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-sm-6 col-sm-offset-3">
                    <label for="price"><fmt:message key="label.admin.create-tour.tour.price" bundle="${ rb }" /></label>
                    <input type="number" class="form-control" id="price" name="price" value="<c:out value="${ shopping.price }" />">
                </div>
            </div>
            <div class="row">
                <div class="form-group col-sm-6 col-sm-offset-3">
                    <label for="shops"><fmt:message key="label.admin.create-tour.shopping.shops" bundle="${ rb }" /></label>
                    <textarea class="form-control" rows="5" id="shops" name="shops"><c:out value="${ shopping.shops }" /></textarea>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-sm-6 col-sm-offset-3">
                    <label for="services"><fmt:message key="label.admin.create-tour.tour.services" bundle="${ rb }" /></label>
                    <textarea class="form-control" rows="5" id="services" name="services"><c:out value="${ shopping.services }" /></textarea>
                </div>
            </div>
            <div class="row">
                <div class="form-group col-sm-6 col-sm-offset-3">
                    <label for="description"><fmt:message key="label.admin.create-tour.tour.description" bundle="${ rb }" /></label>
                    <textarea class="form-control" rows="6" id="description" name="description"><c:out value="${ shopping.description }" /></textarea>
                </div>
            </div>
            <div class="row">
                <div class="col-sm-2 col-sm-offset-5">
                    <c:choose>
                        <c:when test="${ not empty errorEditShoppingPassMessage }">
                            <br/>
                            <span style="color: #ff0000;">${errorEditShoppingPassMessage}</span>
                            <br/>
                            <br/>
                        </c:when>
                        <c:otherwise>
                            <br/>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${ not empty wrongAction }">
                            <br/>
                            ${wrongAction}
                            <br/>
                        </c:when>
                        <c:otherwise>
                        </c:otherwise>
                    </c:choose>
                    <c:choose>
                        <c:when test="${ not empty nullPage }">
                            <br/>
                            ${nullPage}
                            <br/>
                        </c:when>
                        <c:otherwise>
                        </c:otherwise>
                    </c:choose>
                    <div class="control-buttons">
                        <button type="submit" class="btn btn-success form-button"><fmt:message key="action.edit" bundle="${ rb }" /></button>
                        <button type="button" class="btn btn-default form-button" onclick="history.back()"><fmt:message key="action.cancel" bundle="${ rb }" /></button>
                    </div>
                </div>
            </div>
        </form>
        <div class="after-grid">
            <hr>
        </div>
    </div>
</section>

<%@ include file="/jsp/footer.jsp"%>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="/js/bootstrap.min.js"></script>
<script>
    $('#img').change(function () {
        var input = $(this)[0];
        if (input.files && input.files[0]) {
            if (input.files[0].type.match('image.jpeg')) {
                var reader = new FileReader();
                reader.onload = function (e) {
                    $('#img-preview').attr('src', e.target.result);
                }
                reader.readAsDataURL(input.files[0]);
            } else {
                console.log('Error, not image!');
            }
        } else {
            console.log('Error.');
        }
    });

    $('#reset-img-preview').click(function() {
        $('#img').val('');
        $('#img-preview').attr('src', '<c:out value="${ shopping.pathImage }" />');
    });

    $('#form').bind('reset', function () {
        $('#img-preview').attr('src', '<c:out value="${ shopping.pathImage }" />');
    });
</script>
</body></html>

