#!/bin/bash

NEW_PASSWORD="031210"
CONTAINER_NAME="dev-commons-oracle-1"
IS_NECESSARY_CHANGE_PASS=true

echo "Execute docker-compose up..."
docker-compose up --build -d
echo "Finished docker-compose up..."

echo "Waiting for container $CONTAINER_NAME to start..."
#echo $CONTAINER_NAME
while "$IS_NECESSARY_CHANGE_PASS"; do
  CONTAINER_RUNNING=$(docker ps --filter "name=$CONTAINER_NAME" --filter "status=running" --format '{{.Names}}')
  echo "Oracle Container: $CONTAINER_RUNNING"

  HEALTH_STATUS=$(docker inspect --format='{{if .State.Health}}{{.State.Health.Status}}{{else}}no-health-check{{end}}' "$CONTAINER_NAME")
  echo "Health Status: $HEALTH_STATUS"

  echo "Oracle Container is not ready yet. Waiting..."
  sleep 5

  #TEST_1=[ "$HEALTH_STATUS" == *"healthy"* ]
  #echo "$TEST_1"
  #if [ -n "$HEALTH_STATUS" ]; then
  #if [ -n "$HEALTH_STATUS" ] && [[ "$HEALTH_STATUS" == *"healthy"* ]]; then
  #if [ -n "$HEALTH_STATUS" ] && echo "$HEALTH_STATUS" | grep -q "healthy"; then
  if [ -n "$HEALTH_STATUS" ] ; then
    #echo "HEALTH_STATUS: $HEALTH_STATUS"
    #if [[ "$HEALTH_STATUS" == *"healthy"* ]] ; then
    #if test "$HEALTH_STATUS" = "healthy"; then
    if [ "$HEALTH_STATUS" = "healthy" ] ; then
      ORACLE_PASSWORD=$(docker inspect --format='{{range .Config.Env}}{{if eq (index (split . "=") 0) "ORACLE_PASSWORD"}}{{index (split . "=") 1}}{{end}}{{end}}' $CONTAINER_NAME)
      echo "Default Oracle password: $ORACLE_PASSWORD"
      echo "Resetting Oracle password to $NEW_PASSWORD"
      #docker exec oracle resetPassword 031210
      docker exec "$CONTAINER_RUNNING" resetPassword "$NEW_PASSWORD"
      echo "Oracle password reset successfully!"
      IS_NECESSARY_CHANGE_PASS=false
    fi
  fi


done

sleep 5
# Inicia a aplicação Spring Boot
echo "Iniciando Dev Commons..."
java -jar /app.jar
echo "Dev Commons iniciado com sucesso!"