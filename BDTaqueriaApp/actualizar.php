<?php
$server = "localhost";
$user = "root";
$pass ="";
$db ="tacoapp";


$conn = new mysqli($server, $user, $pass, $db);
if($conn->connect_error){
    die("error".$conn->connect_error);
}

$usuario= $_POST["usuario"];
$localidad = $_POST["localidad"];
$direccion= $_POST["direccion"];
$fotoRef= $_POST["fotoRef"];

$sql = "UPDATE Clientes SET localidad= '$localidad' WHERE usuario = '$usuario'";
if(mysqli_query($conn, $sql)){// para aplicar los cambio en la bd 
    echo "satisfactorio update local";
}else{
    echo "Error local: " . $sql . "<br>" . mysqli_error($conn);
}

$sql = "UPDATE Clientes SET direccion = '$direccion' WHERE usuario = '$usuario'";
if(mysqli_query($conn, $sql)){// para aplicar los cambio en la bd 
    echo "satisfactoria update direc";
}else{
    echo "Error direc: " . $sql . "<br>" . mysqli_error($conn);
}

$sql = "UPDATE Clientes SET fotoRef = '$fotoRef' WHERE usuario = '$usuario'";
if(mysqli_query($conn, $sql)){// para aplicar los cambio en la bd 
    echo "satisfactoria update foto";
}else{
    echo "Error foto: " . $sql . "<br>" . mysqli_error($conn);
}


?>