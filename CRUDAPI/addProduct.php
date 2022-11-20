<?php

$conn = mysqli_connect("localhost", "root", "root", "warehouse");

$serial = $_GET['PSerialNo'];
$pname = $_GET['PName'];
$price = $_GET['Price'];
$qnt = $_GET['PQnt'];


$result = mysqli_query($conn, "SELECT count(*) as total FROM products WHERE PSerialNo = '$serial'");
while($row = mysqli_fetch_assoc($result))
{
	if ($row['total'] >= 1) {
		mysqli_query($conn,"UPDATE products SET PQnt = PQnt + '$qnt' WHERE PSerialNo = '$serial'");  
	} else {
		mysqli_query($conn,"INSERT INTO `products` (PSerialNo, PName, Price, PQnt) VALUES ('$serial','$pname','$price','$qnt')");  
	}
}

mysqli_close($conn);

?>