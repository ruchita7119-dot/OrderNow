
#!/bin/bash

SERVICE="$1"

case "$SERVICE" in
  user)
    JAR="/app/userservice.jar"
    PORT=8081
    ;;
  hotel)
    JAR="/app/hotelservice.jar"
    PORT=8082
    ;;
  order)
    JAR="/app/orderservice.jar"
    PORT=8083
    ;;
  payment)
    JAR="/app/paymentservice.jar"
    PORT=8084
    ;;
  gateway)
    JAR="/app/apigateway.jar"
    PORT=8080
    ;;
  *)
    echo "Unknown service: $SERVICE"
    exit 1
    ;;
esac

echo "Starting $SERVICE service on port $PORT"

exec java \
  -Xms32m \
  -Xmx96m \
  -XX:MaxMetaspaceSize=96m \
  -XX:+UseSerialGC \
  -jar "$JAR"
