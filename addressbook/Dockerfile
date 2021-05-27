FROM registry.gitlab.com/armdev/dockers/openjdk15
COPY target/addressbook.jar /opt/addressbook.jar
RUN useradd -ms /bin/sh mphic2050 \
    && echo "root:Docker!" | chpasswd \
    && chown -R mphic2050 /opt/
USER mphic2050
ENTRYPOINT ["java", "-jar","/opt/addressbook.jar"]

EXPOSE 2021



