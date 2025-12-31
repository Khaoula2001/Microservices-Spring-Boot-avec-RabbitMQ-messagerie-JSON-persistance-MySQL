# TP 31 : Microservices Spring Boot avec RabbitMQ

## Objectifs
Mise en place de deux microservices pour l'échange de messages via RabbitMQ.
1.  **JSON Messaging** : Communication simple Producer/Consumer.
2.  **MySQL Persistence** : Stockage des messages reçus en base de données.

---

## 1. Producer/Consumer (JSON)

**Configuration Initiale & Dépendances**
![Initializr](./screens/2025-12-31_00h34_56.png)
![Dependency](./screens/2025-12-31_00h36_15.png)

**Architecture des Projets**
![Structure Producer](./screens/2025-12-31_00h50_53.png)
![Structure Consumer](./screens/2025-12-31_00h52_32.png)

**Exécution**
Démarrage du Producer (8123) et Consumer (8223).
![Log Producer](./screens/2025-12-31_00h53_31.png)
![Log Consumer](./screens/2025-12-31_01h03_43.png)

**Test & Vérification**
Envoi d'un message JSON via Postman.
![Postman](./screens/2025-12-31_01h04_10.png)

Réception confirmée dans RabbitMQ et logs.
![RabbitMQ](./screens/2025-12-31_01h28_39.png)

---

## 2. Persistance MySQL

**Architecture**
Producer (8081) → RabbitMQ → Consumer (MySQL).
![Structure MySQL](./screens/2025-12-31_08h56_39.png)

**Démarrage des Services**
![Start Producer](./screens/2025-12-31_08h57_20.png)
![Logs](./screens/2025-12-31_08h58_09.png)

**Test de flux complet**
Envoi d'un utilisateur JSON.
![Postman Test](./screens/2025-12-31_08h58_34.png)

**Monitoring RabbitMQ**
Exchange `user.exchange` actif.
![RabbitMQ Exchange](./screens/2025-12-31_08h59_29.png)

**Résultats**
Les logs confirment la persistance.
![Logs Persistence](./screens/2025-12-31_09h00_35.png)

Données insérées en base MySQL.
![DB Check](./screens/2025-12-31_09h00_57.png)