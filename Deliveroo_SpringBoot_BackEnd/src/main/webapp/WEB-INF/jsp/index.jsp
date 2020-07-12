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
    <h3>Our Foods</h3>
    <hr>
    <div class="table-responsive">
        <table class="table table-stripped table-bordered" >
            <thead class="btn-primary">
            <tr>
                <th>Id</th>
                <th>Food_Name</th>
                <th>Category</th>
                <th>price</th>
                <th>food_image</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="food" items="${foods}">
                <tr data-href="/show/${food.foodId}" style="cursor: pointer">
                    <td>${food.foodId}</td>
                    <td>${food.foodName}</td>
                    <td>${food.category}</td>
                    <td>${food.price}</td>
                    <td>${food.foodImage}</td>
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