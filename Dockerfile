FROM maven:3.6.3-openjdk-8-slim
WORKDIR /usr/app
COPY . /usr/app
RUN mvn clean install
ENTRYPOINT ["sh"]