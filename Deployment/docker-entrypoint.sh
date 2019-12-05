#!/bin/bash
if [[ -z $ADMIN_PASSWORD ]]; then
#ADMIN_PASSWORD=$(date| md5sum | fold -w 8 | head -n 1)
ADMIN_PASSWORD=secret
echo "##########GENERATED ADMIN PASSWORD: $ADMIN_PASSWORD
##########"
fi

#echo "AS_ADMIN_PASSWORD=" > /tmp/glassfishpwd
#echo "AS_ADMIN_NEWPASSWORD=${ADMIN_PASSWORD}" >> /tmp/glassfishpwd

#asadmin --user=admin --passwordfile=/tmp/glassfishpwd change-admin-password --domain_name domain1
#asadmin start-domain

#echo "AS_ADMIN_PASSWORD=${ADMIN_PASSWORD}" > /tmp/glassfishpwd

#asadmin --user=admin --passwordfile=/tmp/glassfishpwd enable-secure-admin

asadmin create-jdbc-connection-pool --datasourceclassname org.postgresql.ds.PGConnectionPoolDataSource
			--restype javax.sql.ConnectionPoolDataSource
			--property User=admin:PortNumber:5100:Password=admin:ServerName=m56-docker1.dcs.aber.ac.uk:DatabaseName=message_store
      message_store_db_pool

asadmin create-jdbc-resource --connectionpoolid message_store_db_pool jdbc/messageStoreOffCampusDBRes

#asadmin --user=admin --passwordfile=/tmp/glassfishpwd create-auth-realm --classname com.sun.enterprise.security.auth.realm.ldap.LDAPRealm \
#--property "jaas-context=ldapRealm:directory=ldap\://ldap.dcs.aber.ac.uk/:base-dn=ou\=People,dc\=dcs,dc\=aber,dc\=ac,dc\=uk:search-filter=uid\=\%s:group-base-dn=ou\=Group,dc\=dcs,dc\=aber,dc\=ac,dc\=uk:group-search-filter=memberUid\=\%s"\
# dcs_ldap_realm

#asadmin --user=admin stop-domain
#rm /tmp/glassfishpwd

exec "$@"
