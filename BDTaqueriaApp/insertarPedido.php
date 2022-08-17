<?php
$server = "192.168.0.106";
$user = "root";
$pass ="";
$db ="tacoapp";


$conn = new mysqli($server, $user, $pass, $db);
if($conn->connect_error){
    die("error".$conn->connect_error);
}

$id = $_POST["id"];
$usuario = $_POST["usuario"];
$descripcion = $_POST["descripcion"];
$total = $_POST["total"];


$sql = "INSERT INTO pedidos (id, usuario, descripcion, total, fecha,estado) VALUES ('$id','$usuario','$descripcion', '$total',now(),'enviado')";
if(mysqli_query($conn, $sql)){
    // para aplicar los cambio en la bd 
    echo "inserto pedido";
}else{
    echo "Error: " . $sql . "<br>" . mysqli_error($conn);
}
?>