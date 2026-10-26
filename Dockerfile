
# =====================================================
# STAGE 1: BUILD ALL FIVE SPRING BOOT SERVICES
# =====================================================
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /build

# Copy independent Maven projects
COPY userservice ./userservice
COPY hotelservice ./hotelservice
COPY orderservice ./orderservice
COPY paymentservice ./paymentservice
COPY apigateway ./apigateway

# Build User Service
RUN cd userservice && mvn clean package -DskipTests

# Build Hotel Service
RUN cd hotelservice && mvn clean package -DskipTests

# Build Order Service
RUN cd orderservice && mvn clean package -DskipTests

# Build Payment Service
RUN cd paymentservice && mvn clean package -DskipTests

# Build API Gateway
RUN cd apigateway && mvn clean package -DskipTests


# =====================================================
# STAGE 2: RUNTIME IMAGE
# =====================================================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Install Redis and Supervisor
RUN apt-get update \
    && apt-get install -y --no-install-recommends \
       redis-server \
       supervisor \
    && rm -rf /var/lib/apt/lists/*

# Copy executable JARs
COPY --from=builder /build/userservice/target/userservice-*.jar /app/userservice.jar
COPY --from=builder /build/hotelservice/target/hotelservice-*.jar /app/hotelservice.jar
COPY --from=builder /build/orderservice/target/orderservice-*.jar /app/orderservice.jar
COPY --from=builder /build/paymentservice/target/paymentservice-*.jar /app/paymentservice.jar
COPY --from=builder /build/apigateway/target/apigateway-*.jar /app/apigateway.jar

# Copy process management configuration
COPY supervisord.conf /etc/supervisor/conf.d/supervisord.conf
COPY start-services.sh /app/start-services.sh

RUN sed -i 's/\r$//' /app/start-services.sh \
    && chmod +x /app/start-services.sh
# Gateway port
EXPOSE 8080

# Start Redis and all Spring Boot services
CMD ["supervisord", "-n"]
