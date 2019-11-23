<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<style type="text/css"> 
<%@include file = "css/styles.css" %></style>
<script src="js/script.js"></script>
<meta charset="ISO-8859-1">
<title>Store</title>
</head>
<body>
		<!--Navigation bar-->
		<div class="navbar">
			<button class="navtab" onclick="openStore(event, 'Home')" id="defaultOpen">HOME</button>
			<button class="navtab" onclick="openStore(event, 'Target')">TARGET</button>
			<button class="navtab" onclick="openStore(event, 'Meijer')">MEIJER</button>
			<button class="navtab" onclick="openStore(event, 'Menards')">MENARDS</button>
		</div>

		<!--Content associated with each button is contained here-->
		<div id="Home" class="tabcontent" style="display: grid">
			<div class="maparea">
				<h1> Shopping Simulator 900</h1>
				<h5> Select your desired store and enter desired items on form </h5>
				<img src="http://www.naturalconcreteproducts.com/wp-content/uploads/2019/02/menards-logo-vector.png" width="150" height="33">
  				<img src='https://i.postimg.cc/7fnrGrCq/target.png' border='0'width="150" height="33" alt='target'/>
				<img src='https://i.postimg.cc/4Y1VnPRY/costco.png' border='0' width="150" height="33" alt='costco'/>

			</div>
			
		<!--Content displayed to the side of the map-->

		</div>

		<div id="Target" class="tabcontent">
			
			<div class="maparea">
				<a href = "https://www.target.com"><img src ="https://i.postimg.cc/255f8bZ9/targetmap.png" width="600" height="500"></a>
				<br>
			</div>
			
			<div class="tabside">
			<h1> Enter Items Here </h1>
				<!-----form------>
				<form action ="results" method="POST">
				<script src="/wp-includes/js/addInput.js" language="Javascript" type="text/javascript"></script>

    				<div id="dynamicInput">
    				Store:<input type ="text" name="storeID">
    				<br>
          			Item:<input type="text" name = "products">
     				</div>
     				<br/>
    				<input type="button" value="Add Item" onClick="addInput('dynamicInput');">
    				<br/>
    				<input type = "submit" value = "Submit"/>
    			</form>
				
			</div>
			
		</div>

		<div id="Meijer" class="tabcontent">
			<div class="maparea">
				<a href = "https://www.meijer.com"><img src ="https://i.postimg.cc/SNWYvtt6/Meijers-Map.jpg" width="600" height="500"></a>
			</div>

			<div class="tabside">
				<h1> Enter Items Here </h1>
				<!-----form------>
				<script src="/wp-includes/js/addInput.js" language="Javascript" type="text/javascript"></script>
				<form action ="results" method="POST">
    				<div id="dynamicInput2">
    				Store:<input type ="text" name="storeID">
    				<br>
          			Item:<input type="text" name="products">
     				</div>
     				<br>
    				<input type="button" value="Add Item" onClick="addInput('dynamicInput2');">
    				<br>
    				<input type = "submit" value = "Submit" />
				</form>
		
			</div>
		</div>


		<div id="Menards" class="tabcontent">
			<div class="maparea">
				<a href = "https://www.menards.com"><img src ="https://hw.menardc.com/main/assets/images5/maps/master/3323-1.png" width="600" height="500"></a>
			</div>

		
			<div class="tabside">
				<h1> Enter Items Here </h1>
				<!-----form------>
				<script src="/wp-includes/js/addInput.js" language="Javascript" type="text/javascript"></script>
				<form action ="results" method="POST">
    				<div id="dynamicInput3">
    				Store:<input type ="text" name="storeID">
    				<br>
          			Item:<input type="text" name="products">
     				</div>
     				<br>
    				<input type="button" value="Add Item" onClick="addInput('dynamicInput3');">
    				<br>
    				<input type = "submit" value = "Submit" />
				</form>
		
			</div>
		</div>
		

</body>
</html>