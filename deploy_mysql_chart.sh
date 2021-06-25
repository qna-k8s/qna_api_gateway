#!/bin/bash
var=$(helm status qna-msr 2>&1)
errorMsg="Error: release: not found"
installCmd="helm install qna-msr ./helm_chart/qna_mysql \
            --set DB_NAME=$dbName,DB_PASSWORD=$dbPassword"
updateCmd="helm upgrade qna-msr ./helm_chart/qna_mysql \
            --set DB_NAME=$dbName,DB_PASSWORD=$dbPassword"
echo "$var"
if [ "$var" = "$errorMsg" ]
then
    eval $installCmd
else
    eval $updateCmd
fi
