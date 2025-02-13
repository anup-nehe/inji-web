# Use OpenJDK 21 as the base image
FROM openjdk:21-slim

# Install required dependencies: Chrome, ChromeDriver, and kubectl
USER root

RUN apt-get update && \
    apt-get install -y wget curl unzip xvfb libxi6 libgconf-2-4 chromium chromium-driver && \
    rm -rf /var/lib/apt/lists/*

# Install kubectl
RUN curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl" && \
    chmod +x kubectl && mv kubectl /usr/local/bin/kubectl

# Define build-time arguments for metadata
ARG SOURCE
ARG COMMIT_HASH
ARG COMMIT_ID
ARG BUILD_TIME

LABEL source=${SOURCE}
LABEL commit_hash=${COMMIT_HASH}
LABEL commit_id=${COMMIT_ID}
LABEL build_time=${BUILD_TIME}

# Define user and group
ARG container_user=mosip
ARG container_user_group=mosip
ARG container_user_uid=1001
ARG container_user_gid=1001

ENV work_dir=/home/${container_user}/

# Create a non-root user
RUN groupadd -g ${container_user_gid} ${container_user_group} && \
    useradd -u ${container_user_uid} -g ${container_user_group} -s /bin/bash -m ${container_user} -d ${work_dir} && \
    chown -R ${container_user}:${container_user} ${work_dir}

# Switch to non-root user
USER ${container_user_uid}:${container_user_gid}

# Set working directory
WORKDIR ${work_dir}

# Copy application files
COPY --chown=${container_user}:${container_user} ./src/main/resources/ ${work_dir}/resources/
COPY --chown=${container_user}:${container_user} ./target/*.jar ${work_dir}/app.jar
COPY --chown=${container_user}:${container_user} ./entrypoint.sh ${work_dir}/entrypoint.sh

# Set BrowserStack credentials as environment variables
ENV BROWSERSTACK_USERNAME=${BROWSERSTACK_USERNAME}
ENV BROWSERSTACK_ACCESS_KEY=${BROWSERSTACK_ACCESS_KEY}

# Ensure entrypoint script is executable
RUN chmod +x ${work_dir}/entrypoint.sh

# Expose ports (if required by your application)
# EXPOSE 8080

# Define container startup command
CMD ["bash", "./entrypoint.sh"]
