<?php

$conn = mysqli_connect("localhost", "root", "root", "warehouse");

$serial = $_GET['PSerialNo'];

mysqli_query($conn, "DELETE FROM products WHERE PSerialNo = '$serial'");  


mysqli_close($conn);

?>