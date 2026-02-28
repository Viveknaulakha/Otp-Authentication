OTP Authentication System 
A secure, production-ready backend service built with Spring Boot to handle One-Time Password (OTP) generation, delivery, and validation.
FeaturesSecure Generation: Uses SecureRandom for cryptographically strong OTPs.TTL (Time-To-Live): Built-in expiration logic to ensure OTPs are only valid for a limited window.RESTful API: Clean endpoints for requesting and verifying OTPs.Global Exception Handling: Custom error responses for expired, invalid, or malformed requests.
Tech StackLanguage: Java 8/11/17Framework: Spring Boot 3.xBuild Tool: MavenSecurity: Java Cryptography Architecture (JCA) How It WorksRequest: User sends a request with their identifier (Email/Phone).Generate: The system generates a unique 6-digit code and stores it with a timestamp.Verify: User submits the code. The system checks if the code matches and if the time has not exceeded the limit (e.g., 5 minutes).
API EndpointsMethodEndpointDescriptionPOST/api/otp/generateGenerates a new OTP for a user.
POST/api/otp/validateValidates the OTP submitted by the user.
