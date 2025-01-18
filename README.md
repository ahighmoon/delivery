# delivery

[![Build Status](https://github.com/ahighmoon/delivery/actions/workflows/ci.yml/badge.svg)](https://github.com/ahighmoon/delivery/actions/workflows/ci.yml)
[![codecov](https://codecov.io/gh/ahighmoon/delivery/graph/badge.svg?token=0LAITG3F2C)](https://codecov.io/gh/ahighmoon/delivery)

## Project Setup

- Prerequisites

Intellij Idea, Docker Desktop

- Clone the repository into your local machine and navigate to the root directory:

```bash
git clone https://github.com/ahighmoon/delivery.git
cd delivery
```

- Manually create a file named `.env` in the root dir with the following content:

```
MYSQL_ROOT_PASSWORD=secret
MYSQL_DATABASE=delivery_system
MYSQL_USER=admin
MYSQL_PASSWORD=secret12345678
MYSQL_DATABASE_HOST=localhost
MYSQL_DATABASE_PORT=3310
DATABASE_INIT=always
```

- Start Docker Desktop on your local machine and run the command:

```bash
docker-compose up -d
```

- Open the root directory with Intellij Idea, navigate to src/main/java/com/laioffer/delivery/DeliveryApplication.java and run the main method. 

If you want to configure the data source with Intellij Idea Ultimate, the URL to the database is `jdbc:mysql://localhost:3310/delivery_system?createDatabaseIfNotExist=true`
1