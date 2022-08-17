<?php

$server = "localhost";
$user = "root";
$pass ="";
$db ="tacoapp";


$conn = new mysqli($server, $user, $pass, $db);
if($conn->connect_error){
    die("error".$conn->connect_error);
}


$opcion = $_POST["opcion"];

if($opcion == 2){// para consultarsi existe el pedido con tal ID de algun usuario en la BD 
    $id= $_POST["id"];
    $usuario = $_POST["usuario"];

    $sql = "select * from pedidos where  ( id= '$id') AND (usuario = '$usuario') ";
    $res = mysqli_query($conn, $sql);
    if($res){
        // para aplicar la consulta en la BD
        $mostrar = mysqli_fetch_array($res);
        if( $mostrar['id'] !=""){
            echo $mostrar['id'];
            echo "/";
            echo $mostrar['descripcion'];
            echo "/";
            echo $mostrar['fecha'];
            echo "/";
            echo $mostrar['estado'];
        }else{
            echo "No existen pedidos con tal ID";
        }
    }else{
        echo "Error";
    }

}else if($opcion==3){
    //Para actualizar estado del pedido
    $id= $_POST["id"];
    $usuario = $_POST["usuario"];
    
    $sql = "UPDATE pedidos SET estado= 'recibido' WHERE usuario = '$usuario' AND id= '$id'";
    if(mysqli_query($conn, $sql)){// para aplicar los cambio en la bd 
        echo "Estado de su pedido actualizado";
    }else{
        echo "No se pudo actualizar";
    }
    

}else{ //para consulta de sesion si el usuario esta registrado en la BD Clientes))
    $usuario = $_POST["usuario"];
    $contrasena = $_POST["contrasena"];
    $sql = "select * from clientes where (usuario = '$usuario') AND (contrasena = '$contrasena')";
    $res = mysqli_query($conn, $sql);
    if($res){
        // para aplicar la consulta en la BD
        $mostrar = mysqli_fetch_array($res);
        if( $mostrar['usuario'] !="" && $mostrar['contrasena']!= ""){
            echo "Usuario existe en la BD";
        }else{
            echo "Usuario no registrado en la BD";
        }
    }else{
        echo "Error: " . $sql . "<br>" . mysqli_error($conn);
    }
}


//mysqli_close($conn, $sql); //cerrar conexion
?>