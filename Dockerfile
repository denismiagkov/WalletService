FROM openjdk:17
COPY ./src /
WORKDIR /
CMD ["java", "Main"]
