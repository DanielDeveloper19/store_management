<img width="1408" height="768" alt="Gemini_Generated_Image_eqn1h6eqn1h6eqn1" src="https://github.com/user-attachments/assets/bab09439-78f5-493b-8de5-9667f62562a5" />
<img width="1408" height="768" alt="Gemini_Generated_Image_vcd3b1vcd3b1vcd3" src="https://github.com/user-attachments/assets/424baa01-f617-432b-ae7f-f6be7e0284cc" />

# 🛒 Store Management Microservice
> **High-Performance Inventory & Transactional Engine for Alive Systems.**

This repository contains the core business logic for the Store Management system. Built with a focus on **data integrity** and **transactional consistency**, this service handles complex retail workflows in a high-concurrency environment.

## 🛠️ Tech Stack
* **Language:** Java 17 (LTS)
* **Framework:** Spring Boot 3.x
* **Database:** MySQL 8.0 (Relational Data Integrity)
* **Persistence:** Spring Data JPA / Hibernate

## 🏗️ Domain Architecture
The service is designed around **Clean Architecture** principles to ensure the business rules are decoupled from external infrastructure.
* **Inventory Management:** Real-time stock tracking with ACID compliance.
* **Sales Processing:** Atomic transaction handling for retail orders.

## 🛡️ Quality & Security Pipeline (CI)
Every commit triggers an automated **GitHub Actions** workflow designed to meet enterprise standards:
1. **Build & Test:** Maven lifecycle execution with JUnit 5, Mockito and Integration Tests with Test containers.
2. **Static Analysis:** Code quality verification via **SonarQube**.
3. **Security Gate:** Container vulnerability scanning with **Trivy**.
4. **Automated Delivery:** Production-ready images are pushed to the **GitHub Container Registry (GHCR)** only if all gates pass.

## 🚀 Deployment
This application is part of a larger GitOps ecosystem. It is automatically deployed to an **AWS EKS** cluster whenever the `master` branch is updated.
* **Orchestration:** https://github.com/DanielDeveloper19/Store_Management_Terraform_EKS_kubernetes_infra_AWS.git
* **GitOps State:** https://github.com/DanielDeveloper19/Store_Management_KubernetesManifests.git

---
*Developed by Daniel Montoya under the Alive Systems Lab.*
