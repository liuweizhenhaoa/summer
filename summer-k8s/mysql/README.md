kubectl apply -f mysql-pv.yaml 
kubectl apply -f mysql-deployment.yaml 

#删除
kubectl delete deployment,svc mysql