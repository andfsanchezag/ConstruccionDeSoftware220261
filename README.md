# Clinic API — Sistema de Gestión Clínica

API REST para la gestión integral de una clínica médica, desarrollada con **Spring Boot 4** bajo el patrón de arquitectura hexagonal (Ports & Adapters). El sistema gestiona pacientes, usuarios médicos, órdenes, registros clínicos, visitas, facturas y pólizas de seguro.

---

## Tabla de Contenidos

- [Descripción General](#descripción-general)
- [Tecnologías](#tecnologías)
- [Arquitectura](#arquitectura)
- [Requisitos Previos](#requisitos-previos)
- [Configuración e Instalación](#configuración-e-instalación)
- [Ejecución](#ejecución)
- [Autenticación](#autenticación)
- [Roles y Permisos](#roles-y-permisos)
- [Documentación de la API](#documentación-de-la-api)
- [Documentación C4](#documentación-c4)
- [Contribuciones](#contribuciones)

---

## Descripción General

El sistema expone una API RESTful que modela el flujo operativo de una clínica:

1. **Recursos Humanos** crea y gestiona los usuarios del sistema (médicos, enfermeras, administrativos).
2. **Administrativos** registran pacientes, pólizas de seguro, órdenes médicas y facturas.
3. **Médicos** emiten órdenes clínicas y registros médicos (historias clínicas).
4. **Enfermeras** realizan visitas clínicas y consultan historial de órdenes y registros.

Toda la comunicación está protegida por **JWT Bearer Tokens**; cada rol tiene acceso únicamente a los recursos que le corresponden.

---

## Tecnologías

| Categoría | Tecnología |
|-----------|------------|
| Framework | Spring Boot 4.0.2 |
| Lenguaje | Java 17 |
| ORM | Spring Data JPA / Hibernate |
| Base de datos relacional | MySQL 8 |
| Base de datos documental | MongoDB |
| Seguridad | Spring Security + JWT (jjwt 0.12.6) |
| Validación | Jakarta Bean Validation |
| Utilidades | Lombok |
| Build | Maven (Maven Wrapper incluido) |

---

## Arquitectura

El proyecto implementa **Arquitectura Hexagonal (Ports & Adapters)**:

```
┌──────────────────────────────────────────────────────────┐
│  Capa de API (Adaptadores de entrada)                    │
│  REST Controllers → DTOs (Request / Response)            │
└──────────────────┬───────────────────────────────────────┘
                   │
┌──────────────────▼───────────────────────────────────────┐
│  Capa de Aplicación (Casos de Uso)                       │
│  AuthUseCase | AdministrativeUseCase | DoctorUseCase     │
│  NurseUseCase | HumanResourcesUseCase                    │
└──────────────────┬───────────────────────────────────────┘
                   │ (Puertos / Interfaces)
┌──────────────────▼───────────────────────────────────────┐
│  Capa de Dominio                                         │
│  Modelos: Patient, User, Order, Invoice, ClinicalRecord  │
│  Servicios de dominio: CreatePatient, CreateOrder, ...   │
│  Puertos: PatientPort, UserPort, OrderPort, ...          │
└──────────────────┬───────────────────────────────────────┘
                   │ (Adaptadores de salida)
┌──────────────────▼───────────────────────────────────────┐
│  Capa de Infraestructura                                 │
│  Repositorios JPA → MySQL                                │
│  Repositorios MongoDB                                    │
│  Configuración de seguridad JWT                          │
└──────────────────────────────────────────────────────────┘
```

Ver diagrama completo en [clinic/ARCHITECTURE.md](clinic/ARCHITECTURE.md).

---

## Requisitos Previos

- **Java 17** o superior
- **Maven 3.8+** (o usar el wrapper incluido `./mvnw`)
- **MySQL 8** corriendo en `localhost:3306`
- **MongoDB** corriendo en `localhost:27017`

---

## Configuración e Instalación

1. Clonar el repositorio:
   ```bash
   git clone <url-del-repositorio>
   cd ConstruccionDeSoftware220261/clinic
   ```

2. Configurar las credenciales en `src/main/resources/application.properties`:
   ```properties
   # Base de datos MySQL
   spring.datasource.url=jdbc:mysql://localhost:3306/clinica?createDatabaseIfNotExist=true
   spring.datasource.username=root
   spring.datasource.password=tu_contraseña

   # MongoDB
   spring.data.mongodb.uri=mongodb://localhost:27017/clinica

   # JWT — cambiar en producción
   app.jwt.secret=clinicAppSecretKeyForJWTTokenGeneration2026012345
   app.jwt.expiration=86400000
   ```

3. Compilar el proyecto:
   ```bash
   ./mvnw clean compile
   ```

---

## Ejecución

```bash
./mvnw spring-boot:run
```

La API estará disponible en: **`http://localhost:8081`**

> La base de datos `clinica` se crea automáticamente en MySQL si no existe (`createDatabaseIfNotExist=true`). El esquema de tablas se actualiza automáticamente con `spring.jpa.hibernate.ddl-auto=update`.

---

## Autenticación

La API usa **JWT Bearer Token**. Para acceder a los endpoints protegidos:

1. Obtener un token mediante `POST /auth/login`
2. Incluir el token en el header de cada petición:
   ```
   Authorization: Bearer <token>
   ```

El token contiene: `username`, `document` (documento de identidad) y `role`.

---

## Roles y Permisos

| Rol | Descripción | Acceso |
|-----|-------------|--------|
| `HUMANRESOURCES` | Recursos Humanos | `/human-resources/**` (todos los métodos) |
| `ADMINISTRATIVE` | Administrativo | `/administrative/**` (todos los métodos) |
| `DOCTOR` | Médico | `POST /doctor/**` + `GET /doctor/**`, `GET /nurse/**` |
| `NURSE` | Enfermera | `POST /nurse/**` + `GET /nurse/**`, `GET /doctor/**` |

---

## Documentación de la API

Ver ejemplos completos de `curl`, request y response en: **[clinic/API.md](clinic/API.md)**

---

## Documentación C4

Ver diagramas de arquitectura C4 con Mermaid en: **[clinic/ARCHITECTURE.md](clinic/ARCHITECTURE.md)**

---

## Contribuciones

Las contribuciones son bienvenidas. Por favor abre un *issue* describiendo el cambio propuesto o envía un *pull request* con los cambios y sus respectivas pruebas.
