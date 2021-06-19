#!/bin/bash
var=$(helm status qna-agr 2>&1)
errorMsg="Error: release: not found"
installCmd="helm install qna-agr ./helm_chart/qna_apigateway_chart \
            --set imageName=$imageName,DB_PASSWORD=$dbPassword"
updateCmd="helm upgrade qna-agr ./helm_chart/qna_apigateway_chart \
            --set imageName=$imageName,DB_PASSWORD=$dbPassword"
echo "$var"
if [ "$var" = "$errorMsg" ]
then
    eval $installCmd
else
    eval $updateCmd
fi
