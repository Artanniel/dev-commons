# Core Services Package

This package contains abstract service classes that provide common functionality to be reused across concrete service implementations.

## AbstractValidationService

This abstract class provides common validation methods for services:

- `validateAuthHeader`: Validates authorization headers against stored validation files
- `validateParams`: Validates URL path parameters
- `validateQuery`: Validates query parameters
- `validateBody`: Validates request body JSON against stored validation files
- `getResponsePayload`: Retrieves predefined response payloads from files

### Usage Example

```java
@Service
public class MyService extends AbstractValidationService {

    public void processRequest(String serviceName, String authHeader, JsonNode requestBody) {
        // Validate authorization header
        if (!validateAuthHeader(serviceName, authHeader)) {
            throw new UnauthorizedException("Invalid authorization header");
        }

        // Validate request body
        if (!validateBody(serviceName, requestBody)) {
            throw new BadRequestException("Invalid request payload");
        }

        // Get predefined response if available
        JsonNode response = getResponsePayload(serviceName);
        if (!response.isEmpty()) {
            return response;
        }

        // Process request normally
        // ...
    }
}
```
