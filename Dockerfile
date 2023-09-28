FROM amazoncorretto:17-alpine3.15-jdk

MAINTAINER Beta, <beta@syneticservers.com>

RUN apk add --no-cache --update curl ca-certificates openssl git tar bash sqlite fontconfig \
    && adduser --disabled-password --home /home/container container

USER container
ENV  USER=container HOME=/home/container

ADD build/libs/gitlab-monitor.jar /app/

COPY entrypoint.sh /entrypoint.sh
CMD ["/bin/bash", "/entrypoint.sh"]