#!/bin/bash
var=$(helm status qna_agr 2>&1)
errorMsg="Error: release: not found"
installCmd="helm install qna-agr \
            --set imageName=$imageName \
              qna_mysql.db_password=$dbPassword qna_mysql.db_name=$dbName"
updateCmd="helm update qna-agr \
            --set imageName=$imageName \
            qna_mysql.db_password=$dbPassword qna_mysql.db_name=$dbName"
echo "$var"
if [ "$var" = "$errorMsg" ]
then
    eval $installCmd
else
    eval $updateCmd
fi
