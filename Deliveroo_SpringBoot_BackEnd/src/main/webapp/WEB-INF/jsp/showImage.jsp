<!DOCTYPE html>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta http-equiv="Content-Type" CONTENT="test/html; charset=UTF-8">
    <title>show</title>
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


<div class="container text-center">
    <img src="data:image/jpg;base64,<c:out value='${image}'/>" width="600" height="400"/>
    <h1>${food.foodName}<br><small>(${food.category})</small></h1>
    <br>
    <h3>Price   =  ${food.price}Rs</h3>
</div>




<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>