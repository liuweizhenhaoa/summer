#生成token  
1、kubectl -n kubernetes-dashboard get secret $(kubectl -n kubernetes-dashboard get sa/kubernetes-dashboard -o jsonpath="{.secrets[0].name}") -o go-template="{{.data.token | base64decode}}"  
2、kubectl describe secret kubernetes-dashboard-token-nsx75 -n  kubernetes-dashboard

# 输入下面命令查询kube-system命名空间下的所有secret
kubectl get secret -n kube-system

# 然后获取token。只要是type为service-account-token的secret的token都可以使用。
# 比如我们获取replicaset-controller-token-wsv4v的touken
kubectl -n kubernetes-dashboard describe kubernetes-dashboard-token-nsx75


本地token:  
eyJhbGciOiJSUzI1NiIsImtpZCI6IkhEN01pY1FRSHIyMk1oNkVFN0NIdkIwc3VHbzJjV1J3Wll1b3JVTHBmMncifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlcm5ldGVzLWRhc2hib2FyZCIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJrdWJlcm5ldGVzLWRhc2hib2FyZC10b2tlbi0yOXB6aCIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50Lm5hbWUiOiJrdWJlcm5ldGVzLWRhc2hib2FyZCIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50LnVpZCI6ImE1OWZmNjBkLTcxODYtNGY2NC05MWZmLTQzMGYyMzhiYzhjNCIsInN1YiI6InN5c3RlbTpzZXJ2aWNlYWNjb3VudDprdWJlcm5ldGVzLWRhc2hib2FyZDprdWJlcm5ldGVzLWRhc2hib2FyZCJ9.FYFhh0ciGF_oRWPf5MfR26fkkEyCAuO7NvNlu3h-FWzSaC6OK70CTsgK4VibACaiAuRlzdhSzbpKL0wEkg9KtX4hrWGBQY-wr8vks4gxMdY7iEVj4JMRllOI2cE_4JXYTogSlJtbNzig9MREo_ISO-SX_OagjAeiAoyiBoUlNNkaReLPyHT9gaZDI-OOAeg1hrQBR3oM-oPczZkcRsz9un8xcVjmLVjzLqlOLF3AXDFoqgktGnt5TnDHM1OREfVGK3RLpyyMGpIddfU8mTZKEr_8JmZ_9CJQxBs8MIjxhwzljSDcDt4YtH-8j5A8m5o-MDaW2mlcaOH3nbvT1mQhHw
eyJhbGciOiJSUzI1NiIsImtpZCI6ImxxYV9ST00tSWthdUVJaWdJOUhQTmhGQjVuQUtLaHhnc0FTMUR0M0VjdTQifQ.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJrdWJlcm5ldGVzLWRhc2hib2FyZCIsImt1YmVyb
mV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VjcmV0Lm5hbWUiOiJrdWJlcm5ldGVzLWRhc2hib2FyZC10b2tlbi1uc3g3NSIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50Lm5hbWUiOiJrdWJlcm5ldGVzLWRhc2hib2FyZCIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291b
nQvc2VydmljZS1hY2NvdW50LnVpZCI6IjJjNmI3OWEyLTgxMWMtNDQ2Ni05YmZhLTA4OGQ4Njg5NzFmNyIsInN1YiI6InN5c3RlbTpzZXJ2aWNlYWNjb3VudDprdWJlcm5ldGVzLWRhc2hib2FyZDprdWJlcm5ldGVzLWRhc2hib2FyZCJ9.KBvS2sfMQWIqV_VpMq756UbyYtgtNsnbUJR2cactF8_ZamUrpeVp
ljcauRCxtDzrbvdilSKNt7sfus1EZPHxlHjOG-G9c3gkyFtPDLYTeHixzq6Hal0B6sW5p1BnbPLykzG1j7LfkdLaUKi3E4B40TnxL_Fdzz5B6d4w3NZLrg9UthZFvytEMQl4cKo0OVb0-cuNTydLG0K3z0EzxYOvgysAmA2kLuNPq9wov8YbvSWAj0KRgVj9zSFr50obzJslKBsF6kC1mq6qcRPyurgYj0X9unpR
AtUWXIdg55ESHR5rPPiqWgCbjdPN2oRj21SWPuncO7JwjXlpO1Abnbop2w