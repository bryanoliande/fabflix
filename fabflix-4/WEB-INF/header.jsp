<!DOCTYPE html>
<html lang="en">
<head>
	<title> Fablix </title>
	<meta charset="utf-8"/>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" integrity="sha384-0mSbJDEHialfmuBBQP6A4Qrprq5OVfW37PRR3j5ELqxss1yVqOtnepnHVP9aJ7xS" crossorigin="anonymous"></script>	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">

	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ajax-dynamic-content.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ajax.js"></script>
	<script type="text/javascript" src="<%=request.getContextPath()%>/js/ajax-tooltip.js"></script>
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/ajax-tooltip.css" media="screen" type="text/css">

<script src="http://code.jquery.com/ui/1.10.2/jquery-ui.js" ></script>
<script type="text/javascript">
$(document).ready(function() {
    $("input#term").autocomplete({
        width: 300,
        max: 10,
        delay: 100,
        minLength: 1,
        autoFocus: true,
        cacheLength: 1,
        scroll: true,
        highlight: false,
        source: function(request, response){
            $.ajax({
                url: "<%=request.getContextPath()%>/ajaxSearch",
                dataType: "json",
                data: request,
                success: function(data, textStatus, jqXHR) {
                    console.log(data);
                    var items = data;
                    response(items);
                },
                
                error: function(jqXHR, textStatus, errorThrown){
                     console.log(textStatus);
                }
            });
        }

    });
});
   
</script>
</head>

<body>
<nav class="navbar navbar-default">
  	<div class="container-fluid">
	<!-- Brand and toggle get grouped for better mobile display -->
	<div class="navbar-header">
		<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
			<span class="sr-only">Toggle navigation</span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
			<span class="icon-bar"></span>
		</button>
	</div>

	<!-- Collect the nav links, forms, and other content for toggling -->
	<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		<ul class="nav navbar-nav navbar-right">
			<form class="navbar-form navbar-left" action = "<%=request.getContextPath()%>/movielist" role="search" method = "get">
				<div class="form-group">
					<input type="text" class="form-control" placeholder="Search" id = "term" name="title" />
					
					<input type="hidden" name="year" value="" />
					<input type="hidden" name="director" value="" />
					<input type="hidden" name="starFirstName" value="" />
					<input type="hidden" name="starLastName" value="" />
				
				</div>
				<button type="submit" class="btn btn-defualt" ><span class="glyphicon glyphicon-search" aria-hidden="true"></button>
			</form>
			<li><a href="<%=request.getContextPath()%>/home">Home</a></li>
			<li><a href="<%=request.getContextPath()%>/cart"><span class="glyphicon glyphicon-shopping-cart" aria-hidden="true"></span>Cart</a></li>
			<li><a href="<%=request.getContextPath()%>/checkout">Checkout</a></li>
		</ul>
	</div><!-- /.navbar-collapse -->
	</div><!-- /.container-fluid -->
</nav>