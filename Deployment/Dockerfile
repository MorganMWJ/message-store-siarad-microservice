FROM airhacks/java
LABEL maintainer="Adam Bien, adam-bien.com" description="Glassfish v5 release image"
ENV GLASSFISH_ARCHIVE glassfish5
ENV DOMAIN_NAME domain1
ENV INSTALL_DIR /opt
RUN useradd -b /opt -m -s /bin/sh -d ${INSTALL_DIR} serveradmin ;  echo serveradmin:serveradmin | chpasswd ; \ 
    curl -o ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}.zip -L http://download.oracle.com/glassfish/5.0/release/glassfish-5.0.zip ; \ 
    unzip ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}.zip -d ${INSTALL_DIR} ; \ 
    rm ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}.zip ;\
    ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}/bin/asadmin start-database \
    && echo -e "AS_ADMIN_PASSWORD=\nAS_ADMIN_NEWPASSWORD=admin\n" > /tmp/pwd.txt; \
    ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}/bin/asadmin start-domain ${DOMAIN_NAME} \
    && ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}/bin/asadmin --user admin --passwordfile /tmp/pwd.txt change-admin-password ; \
    echo -e "AS_ADMIN_PASSWORD=admin\n" > /tmp/pwd.txt ; \
    ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}/bin/asadmin --user admin --passwordfile /tmp/pwd.txt  enable-secure-admin ;\
    rm /tmp/pwd.txt ; \
    ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}/bin/asadmin stop-domain ${DOMAIN_NAME} \
    && ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}/bin/asadmin stop-database \
    && chown -R serveradmin:serveradmin /opt ; \
    chmod -R a+rw /opt 
	
ENV GLASSFISH_HOME ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}/glassfish
ENV DEPLOYMENT_DIR ${GLASSFISH_HOME}/domains/domain1/autodeploy
ENV JDBC_DRIVER_URL https://jdbc.postgresql.org/download/postgresql-42.2.5.jar
ENV DRIVER_DIR ${GLASSFISH_HOME}/domains/domain1/lib/

WORKDIR ${GLASSFISH_HOME}/bin

#download postgres jdbc drvier and add it to glassfish
RUN wget ${JDBC_DRIVER_URL}
RUN mv postgresql-42.2.5.jar ${DRIVER_DIR}

#move war to auto deploy directory
COPY MessageStore-1.0-SNAPSHOT.war ${DEPLOYMENT_DIR}

COPY docker-entrypoint.sh /
RUN chmod +x /docker-entrypoint.sh

USER serveradmin

ENTRYPOINT ["/docker-entrypoint.sh"]

EXPOSE 4848 8009 8080 8181 1527
CMD ["asadmin", "start-domain", "-v"]