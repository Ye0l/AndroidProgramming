<?

$data = $_POST["data"];

echo $data,"<br>";

$arr = split("/",$data);

$arr[0] = strtotime($arr[0]);

$arr[0] = date("Y-m-d", $arr[0]);

$arr[1] = (int)$arr[1];

$connect= mysql_connect("localhost:3306","root","apmsetup");
mysql_select_db("company",$connect);

$insertSQL="insert into typetest values('$arr[0]', '$arr[1]', '$arr[2]')";

echo $data;
echo $insertSQL;

mysql_query($insertSQL, $connect);

mysql_close();

?>
