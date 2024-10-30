FROM ubuntu:latest
LABEL authors="guilherme"
ENTRYPOINT ["top", "-b"]

FROM postgres:latest
ENV POSTGRES_DB=mydatabase
ENV POSTGRES_PASSWORD=secret
ENV POSTGRES_USER=myuser
EXPOSE 5432
