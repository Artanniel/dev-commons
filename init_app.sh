#!/bin/bash

# --- Configurações ---
ORACLE_IMAGE="gvenzl/oracle-free"
ORACLE_SERVICE_NAME="oracle"
APP_SERVICE_NAME="app"
ORACLE_CONTAINER_NAME="dev-commons-oracle-1"
APP_CONTAINER_NAME="dev-commons-app"
# ---------------------

echo "#############################################################"
echo "Verificando a imagem do Oracle..."

# 1. Verificar se a imagem do Oracle já foi baixada
if docker images --format '{{.Repository}}:{{.Tag}}' | grep -q "$ORACLE_IMAGE"; then
    echo "Imagem '$ORACLE_IMAGE' já existe."
else
    echo "Imagem '$ORACLE_IMAGE' não encontrada. Baixando..."
    docker pull "$ORACLE_IMAGE"
    if [ $? -ne 0 ]; then
        echo "Erro: Falha ao baixar a imagem '$ORACLE_IMAGE'. Abortando."
        exit 1
    fi
    echo "Imagem '$ORACLE_IMAGE' baixada com sucesso!"
fi

echo "#############################################################"
echo "Verificando e gerenciando o serviço Docker do Oracle..."

# 2. Verificar se o contêiner Oracle existe e está rodando
ORACLE_RUNNING=$(docker ps --filter "name=${ORACLE_CONTAINER_NAME}" --filter "status=running" --format '{{.Names}}')

if [ -n "$ORACLE_RUNNING" ]; then
    echo "Contêiner '$ORACLE_CONTAINER_NAME' já está rodando."
else
    echo "Contêiner '$ORACLE_CONTAINER_NAME' não está rodando. Iniciando..."
    docker start "${ORACLE_CONTAINER_NAME}" 2>/dev/null || docker-compose up -d "${ORACLE_SERVICE_NAME}"
    if [ $? -ne 0 ]; then
        echo "Erro: Falha ao iniciar o serviço Oracle. Abortando."
        exit 1
    fi
    echo "Serviço Oracle está UP!"
fi

# Aguardar até que o Oracle esteja pronto para aceitar conexões
echo "Aguardando o Oracle ficar pronto..."
while ! docker exec "$ORACLE_CONTAINER_NAME" healthcheck.sh; do
    echo "Oracle ainda não está pronto. Aguardando 10 segundos..."
    sleep 10
done
echo "Oracle está pronto para uso!"

echo "#############################################################"
echo "Verificando e gerenciando o serviço Docker do App..."

# 3. Verificar se o contêiner do app existe e seu estado
APP_CONTAINER_EXISTS=$(docker ps -a --filter "name=${APP_CONTAINER_NAME}" --format '{{.Names}}')

if [ -n "$APP_CONTAINER_EXISTS" ]; then
    echo "Contêiner '$APP_CONTAINER_NAME' já existe."

    # Verificar se está rodando
    APP_CONTAINER_STATUS=$(docker inspect -f '{{.State.Status}}' "$APP_CONTAINER_NAME" 2>/dev/null)

    if [ "$APP_CONTAINER_STATUS" = "running" ]; then
        echo "Contêiner '$APP_CONTAINER_NAME' já está rodando. Continuando..."
    else
        echo "Contêiner '$APP_CONTAINER_NAME' existe mas não está rodando. Removendo e recriando..."
        docker rm "$APP_CONTAINER_NAME"
        echo "Realizando novo build do projeto..."
        mvn clean package
        if [ $? -ne 0 ]; then
            echo "Erro: A compilação do Maven falhou. Abortando."
            exit 1
        fi
        echo "Criando e iniciando o serviço do App..."
        #docker-compose up --build -d "${APP_SERVICE_NAME}"

        # Construir a imagem do app
        echo "Construindo a imagem do app 'dev-commons-app'..."
        docker build -t "$APP_CONTAINER_NAME" .

        # Executar o contêiner do app com as variáveis de ambiente e link para o Oracle
        echo "Iniciando o contêiner do app 'dev-commons-app'..."
        docker run -d --name "$APP_CONTAINER_NAME" -p 58080:58080 --link dev-commons-oracle-1:oracle "$APP_CONTAINER_NAME"
        #docker run -d --name dev-commons-app -p 58080:58080

        if [ $? -ne 0 ]; then
            echo "Erro: Falha ao criar/iniciar o serviço do App. Abortando 1."
            exit 1
        fi
        echo "Serviço do App foi criado e está UP!"
    fi
else
    echo "Contêiner '$APP_CONTAINER_NAME' não encontrado. Verificando se o Oracle está rodando para decidir o próximo passo..."
    if [ -n "$ORACLE_RUNNING" ]; then
        echo "Oracle está rodando. Iniciando apenas o serviço do App..."
        mvn clean package
        if [ $? -ne 0 ]; then
            echo "Erro: A compilação do Maven falhou. Abortando."
            exit 1
        fi
        #docker-compose up --build -d "${APP_SERVICE_NAME}"
        # Verificar se o contêiner do app já existe e pará-lo/removê-lo se necessário
        #if [ "$(docker ps -q -f name=dev-commons-app)" ]; then
        if [ "$(docker ps -q -f name=dev-commons-app)" ]; then
            echo "Parando o contêiner 'dev-commons-app' existente..."
            #docker stop dev-commons-app
            docker stop "$APP_CONTAINER_NAME"
        fi
        if [ "$(docker ps -a -q -f name=dev-commons-app)" ]; then
            echo "Removendo o contêiner 'dev-commons-app' existente..."
            #docker rm dev-commons-app
            docker rm "$APP_CONTAINER_NAME"
        fi

        # Construir a imagem do app
        echo "Construindo a imagem do app 'dev-commons-app'..."
        #docker build -t dev-commons-app .
        docker build -t "$APP_CONTAINER_NAME" .

        # Executar o contêiner do app com as variáveis de ambiente e link para o Oracle
        echo "Iniciando o contêiner do app 'dev-commons-app'..."
        #docker run -d --name dev-commons-app -p 58080:58080 --link dev-commons-oracle-1:oracle dev-commons-app
        docker run -d --name "$APP_CONTAINER_NAME" -p 58080:58080 --link dev-commons-oracle-1:oracle "$APP_CONTAINER_NAME"
        #docker run -d --name dev-commons-app -p 58080:58080

        if [ $? -ne 0 ]; then
            echo "Erro: Falha ao iniciar o contêiner do app. Abortando."
            exit 1
        fi

        echo "Contêiner do app 'dev-commons-app' está UP!"
        if [ $? -ne 0 ]; then
            echo "Erro: Falha ao criar/iniciar o serviço do App. Abortando 2."
            exit 1
        fi
        echo "Serviço do App foi criado e está UP!"
    else
        echo "Nem Oracle nem App estão rodando. Iniciando ambos os serviços..."
        mvn clean package
        if [ $? -ne 0 ]; then
            echo "Erro: A compilação do Maven falhou. Abortando."
            exit 1
        fi
        docker-compose up --build -d
        if [ $? -ne 0 ]; then
            echo "Erro: Falha ao criar/iniciar os serviços. Abortando."
            exit 1
        fi
        echo "Serviços Oracle e App foram criados e estão UP!"
    fi
fi

echo "#############################################################"
echo "Deploy e inicialização concluídos. Verifique os logs com 'docker-compose logs -f'."
echo "Acesse sua aplicação em http://localhost:58080."
echo "#############################################################"