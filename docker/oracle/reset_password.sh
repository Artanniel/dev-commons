#!/bin/bash

# Nova senha para o usuário SYSTEM
NEW_PASSWORD="031210"

# Verifica se a senha já foi redefinida (opcional)
# Usa um arquivo de marcador para evitar redefinições redundantes
MARKER_FILE="/opt/oracle/oradata/.password_reset"

if [ ! -f "$MARKER_FILE" ]; then
  echo "Redefinindo a senha do usuário SYSTEM para: $NEW_PASSWORD"
  /opt/oracle/resetPassword "$NEW_PASSWORD"

  if [ $? -eq 0 ]; then
    echo "Senha redefinida com sucesso."
    # Cria o arquivo de marcador para evitar redefinições futuras
    touch "$MARKER_FILE"
  else
    echo "Erro ao redefinir a senha. Verifique os logs."
    exit 1
  fi
else
  echo "Senha já foi redefinida anteriormente. Pulando..."
fi