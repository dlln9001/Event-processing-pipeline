This is a distributed, 4-stage event processing pipeline designed to ingest, buffer, and process massive volumes of event data (like transaction events) with zero data loss, even during traffic spikes or database outages.

----------------------------------------------------------------------------------------------------------------------------------------------------------

🏗 The Architecture
The system is decoupled into five distinct stages and parts.

Ingest API (Go): A high-concurrency "doorway" that accepts JSON payloads and immediately hands them off to the message broker.

The Buffer (Kafka): A distributed queue that acts as a shock absorber, protecting downstream services from being overwhelmed.

The Brain (Java Worker): A heavy-duty processing service that batches records and ensures data integrity.

The Durable Store (PostgreSQL) – The final source of truth, optimized for bulk inserts and data integrity.

The Platform (AWS/Terraform/K8s): Automated infrastructure-as-code deployment.

----------------------------------------------------------------------------------------------------------------------------------------------------------


🛠 Tech Stack
Languages: Go (Ingestion), Java (Processing) 

Frameworks: Gin, Springboot

Message Broker: Apache Kafka

Database: PostgreSQL

Infrastructure: AWS, Terraform, Docker, Kubernetes

CI/CD: GitHub Actions

----------------------------------------------------------------------------------------------------------------------------------------------------------

 Getting Started (local)
 
 clone repo
 ```
git clone ...
 ```

fill out environment variables and put your .env in the root directory
```
DB_USER=
DB_PASSWORD=
DB_HOST=  # host will be docker compose service name
DB_PORT=
DB_NAME=
KAFKA_HOST=  # host will be docker compose service name
```

Make sure you have docker and docker compose installed and working
https://docs.docker.com/get-started/

Start the local infrastructure
```
docker compose up
```
