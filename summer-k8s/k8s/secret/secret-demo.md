kubectl create secret generic db-user-pass \
  --from-file=./username.txt \
  --from-file=./password.txt
  
  
kubectl create secret generic db-user-pass \
  --from-file=username=./username.txt \
  --from-file=password=./password.txt
  
kubectl create secret generic dev-db-secret \
  --from-literal=username=devuser \
  --from-literal=password='S!B\*d$zDsb='