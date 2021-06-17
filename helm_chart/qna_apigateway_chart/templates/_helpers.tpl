{{- define "api_gateway.deployment.name" }}
name: {{ printf "%s-%s"  .Values.api_gateway.deployment.name .Release.Name  | trunc 63 }}
{{- end }}

{{- define "api_gateway.deployment.selector" }}
{{- range  $key,$val := .Values.api_gateway.deployment.selectorLabel }}
{{ $key }}: {{ $val }} 
{{- end }}
{{- end }}

{{- define "api_gateway.deployment.containerName" -}}
{{ printf "%s-%s"  .Values.api_gateway.deployment.containerName .Release.Name | trunc 63 }}
{{- end }}

{{- define "api_gateway.service.name" }}
name: {{ printf "%s-%s"  .Values.api_gateway.service.name .Release.Name  | trunc 63 }}
{{- end }}

{{- define "api_gateway.service.selector" }}
{{- range  $key,$val := .Values.api_gateway.service.selectorLabel }}
{{ $key }}: {{ $val }}
{{- end }}
{{- end }}  

{{- define "api_gateway.service.ports" }}
ports:
  {{- range  $index,$val := .Values.api_gateway.service.ports }}
  - port: {{ $val.port }}
    name: {{ $val.name }}  
  {{- end }}
{{- end }}


