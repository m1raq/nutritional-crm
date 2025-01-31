FROM openjdk:17

WORKDIR /app
COPY build/libs/nutritionology-crm-0.0.1-SNAPSHOT.jar app.jar

RUN useradd -m appuser && \
    mkdir -p /app/uploaded-files && \
    chown -R appuser:appuser /app/uploaded-files
USER appuser

EXPOSE 8081
CMD ["java", "-jar", "app.jar", "--spring.profiles.active=prod"]