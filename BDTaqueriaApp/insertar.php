<?php
$server = "192.168.0.106";
$user = "root";
$pass ="";
$db ="tacoapp";


$conn = new mysqli($server, $user, $pass, $db);
if($conn->connect_error){
    die("error".$conn->connect_error);
}

$usuario = $_POST["usuario"];
$nombre = $_POST["nombre"];
$contrasena = $_POST["contrasena"];
$telefono = $_POST["telefono"];

$sql = "INSERT INTO clientes (usuario, nombre, contrasena, telefono, localidad, direccion, fotoRef) VALUES ('$usuario','$nombre','$contrasena', '$telefono', NULL, NULL, NULL)";
if(mysqli_query($conn, $sql)){// para aplicar los cambio en la bd 
    echo "insercion satisfactoria";
}else{
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}
?>