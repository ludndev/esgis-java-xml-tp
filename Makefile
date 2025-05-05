# Makefile pour le projet de monitoring d'alertes

# Variables
GLASSFISH_HOME ?= /Users/ludndev/apps/glassfish7
SERVER_DIR = server
CLIENT_DIR = client

# Cibles principales
all: server client

# Construction du serveur
server:
	cd $(SERVER_DIR) && mvn clean package

# Déploiement du serveur
deploy:
	$(GLASSFISH_HOME)/bin/asadmin deploy $(SERVER_DIR)/target/*.war

# List applications
list-apps:
	$(GLASSFISH_HOME)/bin/asadmin list-applications

# Construction du client
client:
	cd $(CLIENT_DIR) && mvn clean package

# Nettoyage
clean:
	cd $(SERVER_DIR) && mvn clean
	cd $(CLIENT_DIR) && mvn clean

.PHONY: all server client deploy gen-ws-client clean