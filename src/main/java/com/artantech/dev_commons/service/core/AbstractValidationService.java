package com.artantech.dev_commons.service.core;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Abstract service class that provides common validation methods for controllers.
 * Services can extend this class to inherit validation functionality.
 */
public abstract class AbstractValidationService<T, ID> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${validation.file.directory}")
    private String fileDir;

    @jakarta.annotation.PostConstruct
    public void init() {
        File directory = new File(fileDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    /**
     * Validates the authorization header against stored header file
     *
     * @param serviceName the name of the service (used for filename)
     * @param authHeader the authorization header to validate
     * @return true if valid, false otherwise
     */
    public Boolean validateAuthHeader(String serviceName, String authHeader) throws Exception {
        String entityName = getEntityClassName();
        String filename = fileDir + serviceName + "_" + entityName + "_header.json";

        try {
            Path path = Paths.get(filename);
            authHeader = getJsonAuthHeader(authHeader);
            createFileIfNotExist(authHeader, path);
            JsonNode savedHeaderNode = objectMapper.readTree(new String(Files.readAllBytes(path)));
            JsonNode authHeaderNode = objectMapper.readTree(authHeader);
            Iterator<String> fieldNames = savedHeaderNode.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                if (!authHeaderNode.has(fieldName)) {
                    throw new RuntimeException("Required field '" + fieldName + "' is missing in auth header");
                }
                if (!savedHeaderNode.get(fieldName).equals(authHeaderNode.get(fieldName))) {
                    throw new RuntimeException("Field '" + fieldName + "' has different value in auth header");
                }
            }
            return true;

        } catch (IOException e) {
            throw new RuntimeException("Error validating auth header", e);
        }
    }

    private String getJsonAuthHeader(String authHeader) throws Exception {
        String jsonString = "";
        if(Objects.isNull(authHeader) || authHeader.contains("{{authToken}}")) {
            return jsonString;
        }
        String[] parts = authHeader.split(" ", 2);
        if (parts.length != 2) {
            throw new IllegalArgumentException("String of authentication invalid.");
        }
        String authType = parts[0];
        String token = parts[1];
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode jsonNode = objectMapper.createObjectNode();
        jsonNode.put("authType", authType);
        jsonNode.put("token", token);
        try {
            jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e);
        }
        return jsonString;
    }

    /**
     * Validates URL path parameters
     *
     * @param serviceName the name of the service (used for filename)
     * @param urlParams the URL parameters to validate
     */
    public Boolean validateParams(String serviceName, Map<String, String> urlParams) {
        String entityName = getEntityClassName();
        String filename = fileDir + serviceName + "_" + entityName + "_params.json";

        try {
            Path path = Paths.get(filename);
            createFileIfNotExist(serviceName, path);
            JsonNode savedParamsNode = objectMapper.readTree(new String(Files.readAllBytes(path)));
            for (String fieldName : urlParams.keySet()) {
                if (!savedParamsNode.has(fieldName)) {
                    throw new RuntimeException("Required field '" + fieldName + "' is missing in url params");
                }
                if (!savedParamsNode.get(fieldName).toString().equals(urlParams.get(fieldName))) {
                    throw new RuntimeException("Field '" + fieldName + "' has different value in url params");
                }
            }
            return true;

        } catch (IOException e) {
            throw new RuntimeException("Error validating url params", e);
        }
    }

    /**
     * Validates query parameters
     *
     * @param serviceName the name of the service (used for filename)
     * @param query the query parameters to validate
     */
    public Boolean validateQuery(String serviceName, Map<String, String> query) {
        String entityName = getEntityClassName();
        String filename = fileDir + serviceName + "_" + entityName + "_query.json";

        try {
            Path path = Paths.get(filename);
            createFileIfNotExist(objectMapper.writeValueAsString(query), path);
            JsonNode savedParamsNode = objectMapper.readTree(new String(Files.readAllBytes(path)));
            for (String fieldName : query.keySet()) {
                if (!savedParamsNode.has(fieldName)) {
                    throw new RuntimeException("Required field '" + fieldName + "' is missing in query params");
                }
                if (!savedParamsNode.get(fieldName).toString().equals(query.get(fieldName))) {
                    throw new RuntimeException("Field '" + fieldName + "' has different value in query params");
                }
            }
            return true;

        } catch (IOException e) {
            throw new RuntimeException("Error validating query params", e);
        }
    }

    /**
     * Validates request body
     *
     * @param serviceName the name of the service (used for filename)
     * @param requestBody the request body to validate
     * @return true if valid, false otherwise
     */
    public boolean validateBody(String serviceName, JsonNode requestBody) {
        String entityName = getEntityClassName();
        String filename = fileDir + serviceName + "_" + entityName + "_body.json";

        try {
            Path path = Paths.get(filename);
            createFileIfNotExist(requestBody.toPrettyString(), path);
            JsonNode savedHeaderNode = objectMapper.readTree(new String(Files.readAllBytes(path)));

            Iterator<String> fieldNames = savedHeaderNode.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                if (!requestBody.has(fieldName)) {
                    throw new RuntimeException("Required field '" + fieldName + "' is missing in body");
                }
                if (!savedHeaderNode.get(fieldName).equals(requestBody.get(fieldName))) {
                    throw new RuntimeException("Field '" + fieldName + "' has different value in body");
                }
            }
            return true;

        } catch (IOException e) {
            throw new RuntimeException("Error validating body", e);
        }
    }

    /**
     * Gets the predefined response for the service
     *
     * @param serviceName the name of the service (used for filename)
     * @return the predefined response as a JsonNode
     */
    public JsonNode getResponsePayload(String serviceName) {
        String entityName = getEntityClassName();
        String filename = fileDir + serviceName + "_" + entityName + "_responsePayload.json";

        try {
            Path path = Paths.get(filename);
            createFileIfNotExist("", path);
            JsonNode returnObject = objectMapper.readTree(path.toFile());
            return Objects.isNull(returnObject) || returnObject.isEmpty() ? objectMapper.createObjectNode() : returnObject;
        } catch (IOException e) {
            throw new RuntimeException("Error getting response payload", e);
        }
    }

    public boolean validateRequestPayload(String serviceName, JsonNode requestPayload) {
        String entityName = getEntityClassName();
        String filename = fileDir + serviceName + "_" + entityName + "_requestPayload.json";

        try {
            Path path = Paths.get(filename);
            createFileIfNotExist(requestPayload.toPrettyString(), path);
            JsonNode savedHeaderNode = objectMapper.readTree(new String(Files.readAllBytes(path)));

            Iterator<String> fieldNames = savedHeaderNode.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                if (!requestPayload.has(fieldName)) {
                    throw new RuntimeException("Required field '" + fieldName + "' is missing in request payload");
                }
                if (!savedHeaderNode.get(fieldName).equals(requestPayload.get(fieldName))) {
                    throw new RuntimeException("Field '" + fieldName + "' has different value in request payload");
                }
            }
            return true;

        } catch (IOException e) {
            throw new RuntimeException("Error validating request payload", e);
        }
    }

    private void createInitialFile(Path path, String content) throws IOException {
        Files.createDirectories(path.getParent());
        Files.write(path, content.getBytes());
    }

    private String getEntityClassName() {
        Class<T> genericClassType = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        return genericClassType.getSimpleName();
    }

    private boolean createFileIfNotExist(String content, Path path) throws IOException {
        if (!Files.exists(path)) {
            if(Objects.isNull(content)){
                JsonNode emptyNode = objectMapper.createObjectNode();
                content = objectMapper.writeValueAsString(emptyNode);
            }
            createInitialFile(path, content);
            return true;
        }
        return false;
    }
}
