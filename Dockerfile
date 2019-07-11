FROM alpine:3.9
# 使用阿里云镜像, 设置默认时区为Asia/Shanghai
RUN { \
        echo "http://mirrors.aliyun.com/alpine/v3.9/main/"; \
        echo "http://mirrors.aliyun.com/alpine/v3.9/community/"; \
        } > /etc/apk/repositories \
        && apk add --no-cache tzdata bash \
        && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

ENV LANG C.UTF-8

# add a simple script that can auto-detect the appropriate JAVA_HOME value
# based on whether the JDK or only the JRE is installed
RUN { \
		echo '#!/bin/sh'; \
		echo 'set -e'; \
		echo; \
		echo 'dirname "$(dirname "$(readlink -f "$(which javac || which java)")")"'; \
	} > /usr/local/bin/docker-java-home \
	&& chmod +x /usr/local/bin/docker-java-home
ENV JAVA_HOME /usr/lib/jvm/java-1.8-openjdk
ENV PATH $PATH:/usr/lib/jvm/java-1.8-openjdk/jre/bin:/usr/lib/jvm/java-1.8-openjdk/bin

ENV JAVA_VERSION 8u201
ENV JAVA_ALPINE_VERSION 8.201.08-r1

RUN set -x \
	&& apk add --no-cache \
		openjdk8="$JAVA_ALPINE_VERSION" tini curl \
	&& [ "$JAVA_HOME" = "$(docker-java-home)" ] \
	&& curl https://repo1.maven.org/maven2/io/prometheus/jmx/jmx_prometheus_javaagent/0.11.0/jmx_prometheus_javaagent-0.11.0.jar > /jmx_prometheus_javaagent-0.11.0.jar \
	&& echo ''>/etc/jmx_config.yaml

COPY docker-entrypoint.sh  /
ENTRYPOINT ["/sbin/tini", "--", "/docker-entrypoint.sh"]
