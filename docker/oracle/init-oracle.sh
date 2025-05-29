#!/bin/bash

echo "Running custom Oracle init script..."

echo "Waiting for Oracle database to be ready for connection..."
until echo "exit" | sqlplus -S system/"$ORACLE_PASSWORD"@//localhost:1521/FREEPDB1; do
  echo "Oracle is not ready yet. Retrying in 5 seconds..."
  sleep 5
done

echo "Oracle database is ready. Attempting to change SYSTEM password..."

#resetPassword "$APP_USER_PASSWORD" "$APP_USER"
if [ "$APP_USER" != "system" ]; then
    resetPassword "$APP_USER_PASSWORD" "$APP_USER"
    echo "Password for $APP_USER changed."
fi
echo "Password change script finished."