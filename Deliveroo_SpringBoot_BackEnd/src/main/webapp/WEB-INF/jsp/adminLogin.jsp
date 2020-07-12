<!DOCTYPE html>

<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta http-equiv="Content-Type" CONTENT="test/html; charset=UTF-8">
    <title>Login</title>
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
    <h3>Admin Login</h3>
     <h4>${msg} </h4>
    <hr>
    <form action="/login" class="form-horizontal"  method="post" enctype="multipart/form-data" >

        <div class="form-group">
            <label class="control-label col-md-3">Mobile Number</label>
            <div class="col-md-7">
                <input type="text" class="form-control" name="number" value="">
            </div>
        </div>
        <div class="form-group">
            <label class="control-label col-md-3">Password</label>
            <div class="col-md-7">
                <input type="password" class="form-control" name="password" value="">
            </div>
        </div>
        
        
        <div class="form-group">
            <input type="submit" class="btn btn-primary" value="Login">
        </div>
    </form>



</div>



<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>