# DrinkMaster API

## Table of Contents
- [1. Introduction](#1-introduction)
- [2. Prerequisites](#2-prerequisites)
- [3. Getting Started](#3-getting-started)
  - [3.1 Maven Setup](#31-maven-setup)
  - [3.2 Running the Project](#32-running-the-project)
  - [3.3 Accessing API Documentation](#33-accessing-api-documentation)
  - [3.4 Setting up the Database](#34-setting-up-the-database)
    - [3.4.1 Using a Cloud Database](#341-using-a-cloud-database)
    - [3.4.2 Using a Local Database](#342-using-a-local-database)
- [4. Running Tests](#4-running-tests)

## 1. Introduction

DrinkMasterAPI is a Java Spring Boot project that provides an API for managing drink recipes and ingredients. This project utilizes Maven for building and managing dependencies, and it uses a MySQL database to store the data.

## 2. Prerequisites

Before running the project, make sure you have the following installed:

- Java JDK 1.8
- Maven
- MySQL Server version 5.7.42 (if using a local database)

## 3. Getting Started

### 3.1 Maven Setup

First, make sure Maven is installed on your system. You can verify the installation by running the following command:

```bash
mvn -v
```

### 3.2 Running the Project

To build and run the project, follow these steps:

1. Clone the repository to your local machine:

```bash
git clone https://github.com/UWDrinkMaster/drinkmaster-api.git
```

2. Navigate to the project directory:

```bash
cd DrinkMasterAPI
```

3. Build the project using Maven:

```bash
mvn clean install
```

4. Run the application

### 3.3 Accessing API Documentation

Once the application is running, you can access the API documentation through Swagger UI. Open your web browser and go to:

```
http://localhost:8498/swagger-ui/index.html
```

This will display a user-friendly interface with details about the available endpoints and how to interact with the API.

### 3.4 Setting up the Database

#### 3.4.1 Using a Cloud Database

If you want to use a cloud database (e.g., Amazon RDS), update the `src/main/resources/application.properties` file with the following configuration:

```properties
spring.datasource.url=jdbc:mysql://drinkmaster-db.cwsgunty2bey.us-east-2.rds.amazonaws.com:3306/drink_master
spring.datasource.username=admin
spring.datasource.password=drinkmasteruw
```

#### 3.4.2 Using a Local Database

If you prefer using a local database, follow these steps:

1. Install MySQL version 5.7.42 from the official website: [MySQL 5.7 Installer](https://dev.mysql.com/downloads/windows/installer/5.7.html).

2. After installing MySQL, open a MySQL command-line client or a MySQL GUI tool (e.g., MySQL Workbench).

3. Create a local database called "drink_master":

```sql
CREATE DATABASE drink_master;
```

4. Run the scripts to create the required tables and insert initial data. Locate the SQL scripts in the `src/main/java/ca/uwaterloo/drinkmasterapi/sql` directory. Execute each script in the following order:

- `01_drop_tables.sql`
- `01_create_tables.sql`

## 4. Running Tests

To run the tests, execute the following command:

```bash
mvn test
```

This will run all the tests located in the `src/test/java/ca/uwaterloo/drinkmasterapi` directory and show the test results in the console.

Now you're all set to use the DrinkMasterAPI project. Enjoy managing your drink recipes and ingredients through the provided API! 🍹🚀