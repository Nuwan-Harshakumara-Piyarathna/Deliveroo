<!DOCTYPE html>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta http-equiv="Content-Type" CONTENT="test/html; charset=UTF-8">
    <title>index</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>
<body>
<div role="navigation">
    <div class="navbar navbar-inverse">
        <a href="#" class="navbar-brand">Deliveroo</a>
        <div class="navbar-collapse collapse">
            <ul class="nav navbar-nav">
                <li><a href="/add">Add Food</a></li>
                <li><a href="/">All Foods</a></li>
                <li><a href="/orders">Orders</a></li>
                <li><a href="/loginPage">Admin Login</a></li>
                <li><a href="/logOutPage">Log Out</a></li>
            </ul>
        </div>
    </div>
</div>

<div class="container" id="tasksDiv">
    <h3>All orders</h3>
    <hr>
    <div class="table-responsive">
        <table class="table table-stripped table-bordered" >
            <thead class="btn-success">
            <tr>
                <th>Id</th>
                <th>User</th>
                <th>pay_method</th>
                <th>latitude</th>
                <th>longitude</th>
                <th>address</th>
                <th>city</th>
                <th>date</th>
                <th>time</th>
                <th>total_price</th>
                <th>Foods-quantity</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="order" items="${orders}">
                <tr>
                    <td>${order.id}</td>
                    <td>${order.user}</td>
                    <td>${order.pay_method}</td>
                    <td>${order.latitude}</td>
                    <td>${order.longitude}</td>
                    <td>${order.address}</td>
                    <td>${order.city}</td>
                    <td>${order.date}</td>
                    <td>${order.time}</td>
                    <td>${order.total_price}</td>
                    <td>
                        <ul>
                            <c:forEach var="food" items="${order.list}">
                                <c:set var="foodId" scope="session" value="${food.food_id}"/>

                                    <li>${map[foodId]} => ${food.quantity}</li>
                            </c:forEach>
                        </ul>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>



</div>



<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>