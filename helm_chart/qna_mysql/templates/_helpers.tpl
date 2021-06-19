{{- define "storage.deployment.name" -}}
{{ printf "%s-%s"  .Values.deployment.name .Release.Name  | trunc 63 }}
{{- end }}

{{- define "storage.pod.name" -}}
{{ printf "%s-%s"  .Values.pod.name .Release.Name  | trunc 63 }}
{{- end }}

{{- define "storage.container.name" -}}
{{ printf "%s-%s"  .Values.pod.containerName .Release.Name  | trunc 63 }}
{{- end }}

{{- define "storage.pvc.name" -}}
{{ printf "%s-%s"  .Values.pvc.name .Release.Name  | trunc 63 }}
{{- end }}

{{- define "storage.storageClass.name" -}}
{{ printf "%s-%s"  .Values.storageClass.name .Release.Name  | trunc 63 }}
{{- end }}

{{- define "storage.service.name" -}}
{{ printf "%s-%s"  .Values.service.name .Release.Name  | trunc 63 }}
{{- end }}

{{- define "storage.volumeMounts" }}
volumeMounts:
  {{- range  $index,$val := .Values.volumeMounts }}
- name: {{ $val.name }}
  mountPath: {{ $val.mountPath }}  
  {{- end }}
{{- end }}

{{- define "storage.volumes" }}
volumes:
  {{- range  $index,$val := .Values.volumeMounts }}
- name: {{ $val.name }}
  persistentVolumeClaim:
    claimName: {{ printf "%s-%s"  $.Values.pvc.name $.Release.Name | trunc 63 }}
  {{- end }}  
{{- end}}

{{- define "storage.deployment.selector" }}
{{- range  $key,$val := .Values.deployment.selectorLabels }}
{{ $key }}: {{ $val }} 
{{- end }}
{{- end }}

{{- define "storage.service.ports" }}
ports:
  {{- range  $index,$val := .Values.service.ports }}
  - port: {{ $val.port }}
    name: {{ $val.name }}  
  {{- end }}
{{- end }}