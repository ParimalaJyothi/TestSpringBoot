FROM openjdk:10-jre-slim

ENV ENV_USER_NAME mds_user
ENV ENV_APP_HOME /MDS/Test
#Exposed as build time argument, which is then set to environment variable
ARG ARG_APP_PORT=8080
ENV ENV_APP_PORT $ARG_APP_PORT
ARG ARG_SERVER_MEMORY=1536
ENV ENV_SERVER_MEMORY=$ARG_SERVER_MEMORY

RUN apt-get update

RUN useradd -ms /bin/bash -U $ENV_USER_NAME \
	&& mkdir -p $ENV_APP_HOME \
	&& chown -R $ENV_USER_NAME:$ENV_USER_NAME $ENV_APP_HOME
USER $ENV_USER_NAME
WORKDIR $ENV_APP_HOME

COPY --chown=mds_user:mds_user ./target/*SNAPSHOT.jar $ENV_APP_HOME

EXPOSE $ENV_APP_PORT

ENTRYPOINT java --add-modules java.xml.bind -Djava.security.egd=file:/dev/./urandom -classpath ./* -Xmx"$ENV_SERVER_MEMORY"M org.springframework.boot.loader.JarLauncher --server.port=$ENV_APP_PORT
