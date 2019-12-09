FROM airhacks/java
LABEL maintainer="Adam Bien, adam-bien.com" description="Glassfish v5 release image"
ENV GLASSFISH_ARCHIVE glassfish5
ENV DOMAIN_NAME domain1
ENV INSTALL_DIR /opt
ENV GLASSFISH_HOME ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}/glassfish
ENV DEPLOYMENT_DIR ${GLASSFISH_HOME}/domains/domain1/autodeploy
ENV DRIVER_DIR ${GLASSFISH_HOME}/domains/domain1/lib/

#copy across postrges jdbc driver
COPY postgresql-42.2.8.jar ${DRIVER_DIR}

RUN useradd -b /opt -m -s /bin/sh -d ${INSTALL_DIR} serveradmin ;  echo serveradmin:serveradmin | chpasswd ; \ 
    curl -o ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}.zip -L https://download.oracle.com/glassfish/5.0/nightly/latest-glassfish.zip ; \ 
    unzip ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}.zip -d ${INSTALL_DIR} ; \ 
    rm ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}.zip ;\
    ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}/bin/asadmin start-database \
    && echo -e "AS_ADMIN_PASSWORD=\nAS_ADMIN_NEWPASSWORD=admin\n" > /tmp/pwd.txt; \
    ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}/bin/asadmin start-domain ${DOMAIN_NAME} \
    && ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}/bin/asadmin --user admin --passwordfile /tmp/pwd.txt change-admin-password ; \
    echo -e "AS_ADMIN_PASSWORD=admin\n" > /tmp/pwd.txt ; \
    ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}/bin/asadmin --user admin --passwordfile /tmp/pwd.txt  enable-secure-admin ; \
    ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}/bin/asadmin --user admin --passwordfile /tmp/pwd.txt create-jdbc-connection-pool --datasourceclassname org.postgresql.ds.PGConnectionPoolDataSource --restype javax.sql.ConnectionPoolDataSource --property User=admin:PortNumber=5100:Password=admin:ServerName=m56-docker1.dcs.aber.ac.uk:DatabaseName=message_store message_store_db_pool ; \
    ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}/bin/asadmin --user admin --passwordfile /tmp/pwd.txt create-jdbc-resource --connectionpoolid message_store_db_pool jdbc/messageStoreOffCampusDBRes ; \
    rm /tmp/pwd.txt ; \
    ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}/bin/asadmin stop-domain ${DOMAIN_NAME} \
    && ${INSTALL_DIR}/${GLASSFISH_ARCHIVE}/bin/asadmin stop-database \
    && chown -R serveradmin:serveradmin /opt ; \
    chmod -R a+rw /opt 
	

WORKDIR ${GLASSFISH_HOME}/bin

#move war to auto deploy directory
COPY MessageStore-1.0-SNAPSHOT.war ${DEPLOYMENT_DIR}

ENTRYPOINT asadmin start-database && asadmin start-domain --verbose ${DOMAIN_NAME}

USER serveradmin

#ENTRYPOINT ["/docker-entrypoint.sh"]

EXPOSE 4848 8009 8080 8181 1527
#CMD ["asadmin", "start-domain", "-v"]
