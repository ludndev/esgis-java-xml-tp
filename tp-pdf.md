# TRAVAUX PRATIQUES XML & JAVA

**Contexte**

On souhaite mettre en place un système de monitoring des alertes générés par les plateformes d’une infrastructure informatique. Le système comporte un module serveur qui sert à maintenir l’état courant des alertes en cours ; et un module client qui collecte les alertes au niveau des plateformes et les transmet au serveur. Les deux modules échangent à travers les webservices SOAP

Le spécimen du message d’alerte se présente comme suit :

```xml
<?xml version="1.0" encoding="UTF-8"?>
<alert>
    <raisedAt> 2025 - 04 - 09T14:30:45Z</raisedAt>
    <platform>VOTING-SERVICE</platform>
    <code>DISK_FULL</code>
    <description>Disk usage exceeded 95% threshold</description>
    <status>OPEN</status>
    <lastUpdatedAt> 2025 - 04 - 09T14:35:00Z</lastUpdatedAt>
</alert>
```

### Le module serveur

Il s’agit d’un appli web qui offre deux services SOAP tels que décrits ci-après :

### **Get Alert**

* **Message reçu :**
  Message SOAP contenant l’alerte.

* **Traitement :**
  Ajoute, modifie ou retire une alerte selon la logique suivante :

  1. **Si le statut de l’alerte est `OPENED` :**

     * Si le code de l'alerte **n'existe pas** dans la liste pour la plateforme, alors **ajoute l'alerte**.
     * Sinon, **met à jour l'alerte** existante.
  2. **Si le statut est `CLOSED` :**

     * **Retire l'alerte** de la liste si elle existe.
     * Sinon, **ne fait rien**.

* **Message retourné :**
  Message SOAP retournant l’action effectuée :

  * `Created` si l’alerte est créée.
  * `Updated` si l’alerte est modifiée.
  * `Closed` si elle est fermée.


### **List Alert**

* **Message reçu :**
  Vide.

* **Traitement :**
  Retourne la liste des alertes en attente de résolution.

* **Message retourné :**
  Message SOAP contenant la liste des alertes.


### Module client

C’est un programme Java SE qui tourne en arrière plan. Il lit un fichier de configuration au démarrage et fonctionne comme suit :

- Scanne par intervalle régulier (par ex. 30 secondes), le contenu d’un répertoire, ou les plateformes supervisés déposent leur fichier d’alerte XML
- Pour chaque fichier trouvé il lit le message d’alerte, l’envoie au serveur et supprime le fichier
- Par intervalle de temps prédéfini (ex. 300 secondes), il extrait la liste des alertes en instance grâce au web service approprié et le sauvegarde sous forme de fichier XML dans un emplacement prédéfini.

## TAF

1. Développer le module serveur avec la plateforme Jakarta EE et JAXB
    a. Créer la classe mapping JAXB du message alerte
    b. Développer les webservices SOAP et déployer
2. Développer le module client
    a. Utiliser la commande _wsimport_ (Glassfish) pour générer les artefacts XML clients nécessaires
    b. Élaborer le fichier de configuration XML pour les variables prédéfinies : intervalle de scan, répertoire de collecte, emplacement de sauvegarde des alertes en cours, intervalle de sauvegarde.
    c. Implémenter la logique métier telle que décrite.

**Directives**

- Utiliser JAKARTA EE (SOAP, JAXP, Webservices) pour implémenter le serveur et le client
- Le serveur sera déployé avec la plateforme Glassfish
- Préparer la démo de votre travail dans une suite cohérente de fonctionnalités à présenter
- Préparer les fichiers alertes permettant de démontrer les fonctionnalités
- Utiliser la classe Thread pour les exécutions en boucle par intervalles réguliers.
