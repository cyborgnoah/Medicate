<?php
// just to automate and sync master branch with digital-diary.tk domain
$output1=`git reset --hard HEAD`;
echo"$output1";
$output2=`git pull 2>&1`;
echo"$output2";
$output2=`chmod 777 -R *`;
echo"$output2";
?>
