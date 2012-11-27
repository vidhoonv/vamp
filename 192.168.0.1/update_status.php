<?php
$current_location=$_POST['loc'];

//vehicle IP assigned here

$busip="192.168.0.1";

$station_coodsx=array();
$station_coodsy=array();
$station_region=array();
$station_zip=array();
$station_country=array();


$station_coodsx[0]="22.77";
$station_coodsx[1]="44.12";
$station_coodsx[2]="60.13";
$station_coodsx[3]="85.33";
$station_coodsx[4]="97.69";

$station_coodsy[0]="122.77";
$station_coodsy[1]="14.12";
$station_coodsy[2]="160.13";
$station_coodsy[3]="63.33";
$station_coodsy[4]="11.69";

$station_region[0]="chennai";
$station_region[1]="salem";
$station_region[2]="erode";
$station_region[3]="Tirupur";
$station_region[4]="Coimbatore";

$station_regioncode[0]="MAS";
$station_regioncode[1]="SLM";
$station_regioncode[2]="ERD";
$station_regioncode[3]="TUP";
$station_regioncode[4]="CBE";

$station_zip[0]="600025";
$station_zip[1]="631111";
$station_zip[2]="688721";
$station_zip[3]="699743";
$station_zip[4]="642128";

$station_country[0]="INDIA";
$station_country[1]="INDIA";
$station_country[2]="INDIA";
$station_country[3]="INDIA";
$station_country[4]="INDIA";

$station_countrycode[0]="IN";
$station_countrycode[1]="IN";
$station_countrycode[2]="IN";
$station_countrycode[3]="IN";
$station_countrycode[4]="IN";

//writes the result to shared resource
$fh1=fopen('bus_track_info.txt','w');
$trackcontent=$busip.",".$busip.",".$station_countrycode[$current_location-1].",".$station_country[$current_location-1].",".$station_region[$current_location-1].",".$station_regioncode[$current_location-1].",".$station_zip[$current_location-1].",,".$station_coodsx[$current_location-1].",".$station_coodsy[$current_location-1].",BCL SOUTH,BCL SOUTH\n";
fwrite($fh1,$trackcontent);
fclose($fh1);

//writes result to bus log for preserving tracking information
$fh=fopen('buslog.txt','a');
$content="\n".$busip.",".$busip.",".$station_countrycode[$current_location-1].",".$station_country[$current_location-1].",".$station_regioncode[$current_location-1].",".$station_region[$current_location-1].",".$station_zip[$current_location-1].",,".$station_coodsx[$current_location-1].",".$station_coodsy[$current_location-1].",BCL SOUTH,BCL SOUTH\n";
fwrite($fh,$content);
fclose($fh);

header("Location:http://localhost/mc/edit_status.html");

?>
