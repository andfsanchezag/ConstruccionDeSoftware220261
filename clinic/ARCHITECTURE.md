# Documentación de Arquitectura — Clinic API

Modelo C4 completo del sistema de gestión clínica. Los diagramas siguen el estándar **C4 Model** (Context → Container → Component → Code) usando notación **Mermaid**.

---

## Tabla de Contenidos

- [Nivel 1 — Diagrama de Contexto del Sistema](#nivel-1--diagrama-de-contexto-del-sistema)
- [Nivel 2 — Diagrama de Contenedores](#nivel-2--diagrama-de-contenedores)
- [Nivel 3 — Diagrama de Componentes (API)](#nivel-3--diagrama-de-componentes-api)
- [Nivel 3 — Diagrama de Componentes (Dominio)](#nivel-3--diagrama-de-componentes-dominio)
- [Flujos de Secuencia](#flujos-de-secuencia)
  - [Autenticación JWT](#flujo-1-autenticación-jwt)
  - [Creación de Paciente](#flujo-2-creación-de-paciente)
  - [Creación de Orden Médica](#flujo-3-creación-de-orden-médica)
  - [Registro de Visita Clínica](#flujo-4-registro-de-visita-clínica)
- [Diagrama de Clases del Dominio](#diagrama-de-clases-del-dominio)
- [Flujo de Seguridad JWT](#flujo-de-seguridad-jwt)
- [Diagrama Entidad-Relación](#diagrama-entidad-relación)

---

## Nivel 1 — Diagrama de Contexto del Sistema

Muestra el sistema y las personas que interactúan con él desde una perspectiva de alto nivel.

```mermaid
C4Context
    title Sistema de Gestión Clínica — Diagrama de Contexto

    Person(hrPersona, "Recursos Humanos", "Gestiona el personal médico y administrativo del sistema")
    Person(adminPersona, "Administrativo", "Registra pacientes, pólizas, órdenes y facturas")
    Person(doctorPersona, "Médico", "Emite órdenes médicas y registros clínicos")
    Person(nursePersona, "Enfermera", "Realiza visitas clínicas y consulta historial")

    System(clinicSystem, "Clinic API", "Sistema REST de gestión clínica. Expone endpoints para el manejo integral de operaciones clínicas con autenticación JWT.")

    Rel(hrPersona, clinicSystem, "Crea y gestiona usuarios del sistema", "HTTPS/JSON")
    Rel(adminPersona, clinicSystem, "Administra pacientes, pólizas, órdenes y facturas", "HTTPS/JSON")
    Rel(doctorPersona, clinicSystem, "Gestiona órdenes médicas e historias clínicas", "HTTPS/JSON")
    Rel(nursePersona, clinicSystem, "Registra visitas y consulta historiales", "HTTPS/JSON")
```

---

## Nivel 2 — Diagrama de Contenedores

Muestra los contenedores tecnológicos que conforman el sistema.

```mermaid
C4Container
    title Clinic API — Diagrama de Contenedores

    Person(hrPersona, "Recursos Humanos", "")
    Person(adminPersona, "Administrativo", "")
    Person(doctorPersona, "Médico", "")
    Person(nursePersona, "Enfermera", "")

    System_Boundary(clinicSystem, "Clinic API") {
        Container(apiApp, "Clinic Spring Boot App", "Java 17 / Spring Boot 4", "Aplicación principal. Expone la API REST, aplica seguridad JWT y orquesta la lógica de negocio.")
        ContainerDb(mysqlDb, "MySQL 8", "Base de datos relacional", "Almacena entidades estructuradas: pacientes, usuarios, órdenes, facturas, registros clínicos.")
        ContainerDb(mongoDb, "MongoDB", "Base de datos documental", "Almacena datos de auditoría y documentos con esquema flexible.")
    }

    Rel(hrPersona, apiApp, "POST/PUT/DELETE/GET /human-resources/**", "HTTPS Bearer JWT")
    Rel(adminPersona, apiApp, "POST/PUT/DELETE/GET /administrative/**", "HTTPS Bearer JWT")
    Rel(doctorPersona, apiApp, "POST/GET /doctor/**", "HTTPS Bearer JWT")
    Rel(nursePersona, apiApp, "POST/GET /nurse/**", "HTTPS Bearer JWT")

    Rel(apiApp, mysqlDb, "Lee y escribe entidades del dominio", "JPA/Hibernate TCP:3306")
    Rel(apiApp, mongoDb, "Lee y escribe documentos", "Spring Data MongoDB TCP:27017")
```

---

## Nivel 3 — Diagrama de Componentes (API)

Detalla los componentes internos de la aplicación Spring Boot.

```mermaid
C4Component
    title Clinic Spring Boot App — Diagrama de Componentes

    Container_Boundary(apiApp, "Clinic Spring Boot App") {

        Component(securityFilter, "JwtAuthFilter", "Spring Security Filter", "Intercepta cada request, valida el JWT y establece el contexto de autenticación.")
        Component(securityConfig, "SecurityConfig", "Spring Security Config", "Define reglas de autorización por rol y endpoints públicos.")

        Component(authCtrl, "AuthController", "REST Controller /auth", "Login de usuario. Retorna JWT token.")
        Component(hrCtrl, "HumanResourceController", "REST Controller /human-resources", "CRUD de usuarios (médicos, enfermeras, administrativos, RRHH).")
        Component(adminCtrl, "AdministrativeController", "REST Controller /administrative", "CRUD de pacientes, pólizas, contactos de emergencia, órdenes y facturas.")
        Component(doctorCtrl, "DoctorController", "REST Controller /doctor", "Creación de órdenes médicas y registros clínicos.")
        Component(nurseCtrl, "NurseController", "REST Controller /nurse", "Registro de visitas clínicas y lectura de historiales.")

        Component(authUC, "AuthUseCase", "Application Use Case", "Valida credenciales y genera JWT.")
        Component(hrUC, "HumanResourcesUseCase", "Application Use Case", "Lógica de negocio para gestión de usuarios.")
        Component(adminUC, "AdministrativeUseCase", "Application Use Case", "Lógica de negocio para gestión administrativa.")
        Component(doctorUC, "DoctorUseCase", "Application Use Case", "Lógica de negocio para operaciones médicas.")
        Component(nurseUC, "NurseUseCase", "Application Use Case", "Lógica de negocio para operaciones de enfermería.")

        Component(domainServices, "Domain Services", "Domain Layer", "CreatePatient, CreateOrder, CreateClinicalRecord, etc.")
        Component(ports, "Ports (Interfaces)", "Domain Layer", "PatientPort, UserPort, OrderPort, InvoicePort, ClinicalRecordPort, etc.")
        Component(persistence, "Persistence Adapters", "Infrastructure Layer", "Implementaciones JPA y MongoDB de los puertos del dominio.")
    }

    Rel(securityFilter, securityConfig, "Aplica reglas de autorización")
    Rel(authCtrl, authUC, "Delega autenticación")
    Rel(hrCtrl, hrUC, "Delega operaciones de RRHH")
    Rel(adminCtrl, adminUC, "Delega operaciones administrativas")
    Rel(doctorCtrl, doctorUC, "Delega operaciones médicas")
    Rel(nurseCtrl, nurseUC, "Delega operaciones de enfermería")

    Rel(authUC, domainServices, "Invoca servicios de dominio")
    Rel(hrUC, domainServices, "Invoca servicios de dominio")
    Rel(adminUC, domainServices, "Invoca servicios de dominio")
    Rel(doctorUC, domainServices, "Invoca servicios de dominio")
    Rel(nurseUC, domainServices, "Invoca servicios de dominio")

    Rel(domainServices, ports, "Usa interfaces de puertos")
    Rel(ports, persistence, "Implementado por adaptadores de persistencia")
```

---

## Nivel 3 — Diagrama de Componentes (Dominio)

Muestra los modelos de dominio y sus relaciones.

```mermaid
C4Component
    title Clinic API — Capa de Dominio

    Container_Boundary(domain, "Domain Layer") {
        Component(patient, "Patient", "Domain Model", "Paciente registrado. Tiene contacto de emergencia y póliza.")
        Component(user, "User", "Domain Model", "Usuario del sistema: médico, enfermera, administrativo o RRHH.")
        Component(emergencyContact, "EmergencyContact", "Domain Model", "Contacto de emergencia del paciente.")
        Component(policy, "Policy", "Domain Model", "Póliza de seguro médico del paciente.")
        Component(company, "Company", "Domain Model", "Aseguradora asociada a la póliza.")
        Component(order, "Order", "Domain Model", "Orden médica emitida por un médico para un paciente.")
        Component(orderItem, "OrderItem", "Domain Model", "Ítem de una orden: medicamento, procedimiento o soporte diagnóstico.")
        Component(clinicalRecord, "ClinicalRecord", "Domain Model", "Historia clínica vinculada a una orden y un médico.")
        Component(clinicalVisit, "ClinicalVisit", "Domain Model", "Visita clínica registrada por una enfermera.")
        Component(invoice, "Invoice", "Domain Model", "Factura generada para un paciente incluyendo cobertura de seguro.")
        Component(invoiceItem, "InvoiceItem", "Domain Model", "Ítem de factura con descripción, cantidad y monto.")
        Component(inventoryItem, "InventoryItem (abstract)", "Domain Model", "Base: Medicine, Procedure, DiagnosticSupport.")
    }

    Rel(patient, emergencyContact, "tiene 1-a-1")
    Rel(patient, policy, "tiene 1-a-1")
    Rel(policy, company, "pertenece a N-a-1")
    Rel(order, patient, "pertenece a N-a-1")
    Rel(order, user, "emitida por N-a-1")
    Rel(order, orderItem, "contiene 1-a-N")
    Rel(orderItem, inventoryItem, "referencia N-a-1")
    Rel(clinicalRecord, patient, "del paciente N-a-1")
    Rel(clinicalRecord, user, "escrita por N-a-1")
    Rel(clinicalRecord, order, "basada en N-a-1")
    Rel(clinicalVisit, patient, "del paciente N-a-1")
    Rel(clinicalVisit, user, "registrada por N-a-1")
    Rel(clinicalVisit, order, "basada en N-a-1")
    Rel(invoice, patient, "del paciente N-a-1")
    Rel(invoice, user, "emitida por N-a-1")
    Rel(invoice, policy, "aplica póliza N-a-1")
    Rel(invoice, invoiceItem, "contiene 1-a-N")
```

---

## Flujos de Secuencia

### Flujo 1: Autenticación JWT

```mermaid
sequenceDiagram
    actor Cliente
    participant AuthController
    participant AuthUseCase
    participant UserPort
    participant JwtService

    Cliente->>AuthController: POST /auth/login {username, password}
    AuthController->>AuthUseCase: login(username, password)
    AuthUseCase->>UserPort: findByUsername(username)
    UserPort-->>AuthUseCase: User (con password BCrypt)
    AuthUseCase->>AuthUseCase: BCrypt.matches(password, hash)
    AuthUseCase->>JwtService: generateToken(username, document, role)
    JwtService-->>AuthUseCase: JWT String
    AuthUseCase-->>AuthController: LoginResponse(token, document, role)
    AuthController-->>Cliente: 200 OK {token, document, role}

    Note over Cliente,AuthController: En peticiones posteriores:<br/>Authorization: Bearer <token>
```

### Flujo 2: Creación de Paciente

```mermaid
sequenceDiagram
    actor Administrativo
    participant JwtAuthFilter
    participant AdministrativeController
    participant AdministrativeUseCase
    participant CreatePatient
    participant PatientPort

    Administrativo->>JwtAuthFilter: POST /administrative/patients + Bearer Token
    JwtAuthFilter->>JwtAuthFilter: Valida JWT + verifica rol ADMINISTRATIVE
    JwtAuthFilter-->>AdministrativeController: Request autorizado
    AdministrativeController->>AdministrativeUseCase: createPatient(patient)
    AdministrativeUseCase->>CreatePatient: execute(patient)
    CreatePatient->>PatientPort: save(patient)
    PatientPort-->>CreatePatient: Patient guardado
    CreatePatient-->>AdministrativeUseCase: void
    AdministrativeUseCase-->>AdministrativeController: void
    AdministrativeController-->>Administrativo: 201 Created + PatientResponse
```

### Flujo 3: Creación de Orden Médica

```mermaid
sequenceDiagram
    actor Medico
    participant JwtAuthFilter
    participant DoctorController
    participant DoctorUseCase
    participant CreateOrder
    participant OrderPort

    Medico->>JwtAuthFilter: POST /doctor/orders + Bearer Token + {patientDocument, orderItems}
    JwtAuthFilter->>JwtAuthFilter: Valida JWT + extrae document del médico
    JwtAuthFilter-->>DoctorController: Request con Authentication
    DoctorController->>DoctorController: Extrae doctorDocument del JWT
    DoctorController->>DoctorUseCase: createOrder(order)
    Note over DoctorController: doctorDocument viene del token,<br/>no del body del request
    DoctorUseCase->>CreateOrder: execute(order)
    CreateOrder->>OrderPort: save(order)
    OrderPort-->>CreateOrder: Order guardada
    CreateOrder-->>DoctorUseCase: void
    DoctorUseCase-->>DoctorController: void
    DoctorController-->>Medico: 201 Created + OrderResponse
```

### Flujo 4: Registro de Visita Clínica

```mermaid
sequenceDiagram
    actor Enfermera
    participant JwtAuthFilter
    participant NurseController
    participant NurseUseCase
    participant CreateClinicalVisit
    participant ClinicalVisitPort

    Enfermera->>JwtAuthFilter: POST /nurse/clinical-visits + Bearer Token
    JwtAuthFilter->>JwtAuthFilter: Valida JWT + verifica rol NURSE
    JwtAuthFilter-->>NurseController: Request con Authentication
    NurseController->>NurseController: Extrae nurseDocument del JWT
    NurseController->>NurseUseCase: createClinicalVisit(visit)
    NurseUseCase->>CreateClinicalVisit: execute(visit)
    CreateClinicalVisit->>ClinicalVisitPort: save(visit)
    ClinicalVisitPort-->>CreateClinicalVisit: ClinicalVisit guardada
    CreateClinicalVisit-->>NurseUseCase: void
    NurseUseCase-->>NurseController: void
    NurseController-->>Enfermera: 201 Created + ClinicalVisitResponse
```

---

## Diagrama de Clases del Dominio

```mermaid
classDiagram
    class Person {
        <<abstract>>
        +long id
        +String document
        +String name
        +String phone
        +String email
        +String address
        +Date birthDate
    }

    class Patient {
        +Gender gender
        +EmergencyContact emergencyContact
        +Policy policy
    }

    class User {
        +String username
        +String password
        +Role role
    }

    class EmergencyContact {
        +String relationship
    }

    class Policy {
        +long id
        +String policyNumber
        +boolean active
        +Date expiryDate
        +Company company
    }

    class Company {
        +long id
        +String name
    }

    class Order {
        +long id
        +Patient patient
        +User doctor
        +Date date
        +List~OrderItem~ orderItems
    }

    class OrderItem {
        +long id
        +InventoryItem inventoryItem
        +ItemType itemType
    }

    class InventoryItem {
        <<abstract>>
        +long id
        +String name
        +double price
    }

    class Medicine {
        +String dosage
        +String manufacturer
    }

    class Procedure {
        +String description
    }

    class DiagnosticSupport {
        +String description
    }

    class ClinicalRecord {
        +long id
        +Patient patient
        +User doctor
        +Date date
        +String reason
        +String symptoms
        +String diagnosis
        +Order order
    }

    class ClinicalVisit {
        +long id
        +Patient patient
        +User nurse
        +Date date
        +String bloodPressure
        +double temperature
        +int pulse
        +double oxygenLevel
        +String observations
        +Order order
    }

    class Invoice {
        +long id
        +Patient patient
        +User doctor
        +Date issueDate
        +double totalAmount
        +double copayment
        +double insuranceCoverage
        +double patientPayment
        +boolean policyApplied
        +Policy policy
        +List~InvoiceItem~ items
    }

    class InvoiceItem {
        +long id
        +String description
        +int quantity
        +double amount
        +String type
    }

    class Role {
        <<enumeration>>
        HUMANRESOURCES
        DOCTOR
        NURSE
        ADMINISTRATIVE
    }

    class Gender {
        <<enumeration>>
        MALE
        FEMALE
    }

    class ItemType {
        <<enumeration>>
        MEDICINE
        PROCEDURE
        MEDICALSUPPORT
    }

    Person <|-- Patient
    Person <|-- User
    Person <|-- EmergencyContact

    Patient "1" --> "1" EmergencyContact
    Patient "1" --> "1" Policy
    Policy "N" --> "1" Company

    Order "N" --> "1" Patient
    Order "N" --> "1" User
    Order "1" --> "N" OrderItem
    OrderItem "N" --> "1" InventoryItem

    InventoryItem <|-- Medicine
    InventoryItem <|-- Procedure
    InventoryItem <|-- DiagnosticSupport

    ClinicalRecord "N" --> "1" Patient
    ClinicalRecord "N" --> "1" User
    ClinicalRecord "N" --> "1" Order

    ClinicalVisit "N" --> "1" Patient
    ClinicalVisit "N" --> "1" User
    ClinicalVisit "N" --> "1" Order

    Invoice "N" --> "1" Patient
    Invoice "N" --> "1" User
    Invoice "N" --> "1" Policy
    Invoice "1" --> "N" InvoiceItem

    User --> Role
    Patient --> Gender
    OrderItem --> ItemType
```

---

## Flujo de Seguridad JWT

```mermaid
flowchart TD
    A([Request HTTP]) --> B{¿Es /auth/**?}
    B -- Sí --> C[AuthController\nSin autenticación]
    B -- No --> D[JwtAuthFilter]

    D --> E{¿Header\nAuthorization\npresente?}
    E -- No --> F[401 Unauthorized\nNo autenticado]
    E -- Sí --> G[Extrae Bearer Token]

    G --> H{¿Token\nválido?}
    H -- Expirado --> I[401 Unauthorized\nToken expirado]
    H -- Inválido --> J[401 Unauthorized\nToken inválido]
    H -- Válido --> K[Extrae claims:\nusername, document, role]

    K --> L[SecurityContext\nAuthentication establecida]
    L --> M{¿Rol autorizado\npara el endpoint?}

    M -- No --> N[403 Forbidden\nAcceso denegado]
    M -- Sí --> O[Controller procesa request]
    O --> P([Response HTTP])

    style F fill:#ff6b6b,color:#fff
    style I fill:#ff6b6b,color:#fff
    style J fill:#ff6b6b,color:#fff
    style N fill:#ff9f43,color:#fff
    style C fill:#54a0ff,color:#fff
    style P fill:#1dd1a1,color:#fff
```

---

## Diagrama Entidad-Relación

```mermaid
erDiagram
    PATIENT {
        bigint id PK
        varchar document UK
        varchar name
        varchar phone
        varchar email
        varchar address
        date birth_date
        varchar gender
        bigint emergency_contact_id FK
        bigint policy_id FK
    }

    USER {
        bigint id PK
        varchar document UK
        varchar name
        varchar phone
        varchar email
        varchar address
        date birth_date
        varchar username UK
        varchar password
        varchar role
    }

    EMERGENCY_CONTACT {
        bigint id PK
        varchar document
        varchar name
        varchar phone
        varchar email
        varchar address
        date birth_date
        varchar relationship
    }

    POLICY {
        bigint id PK
        varchar policy_number
        boolean active
        date expiry_date
        bigint company_id FK
    }

    COMPANY {
        bigint id PK
        varchar name
    }

    ORDER_TABLE {
        bigint id PK
        bigint patient_id FK
        bigint doctor_id FK
        date date
    }

    ORDER_ITEM {
        bigint id PK
        bigint order_id FK
        bigint inventory_item_id FK
        varchar item_type
    }

    CLINICAL_RECORD {
        bigint id PK
        bigint patient_id FK
        bigint doctor_id FK
        date date
        text reason
        text symptoms
        text diagnosis
        bigint order_id FK
    }

    CLINICAL_VISIT {
        bigint id PK
        bigint patient_id FK
        bigint nurse_id FK
        date date
        varchar blood_pressure
        double temperature
        int pulse
        double oxygen_level
        text observations
        bigint order_id FK
    }

    INVOICE {
        bigint id PK
        bigint patient_id FK
        bigint doctor_id FK
        date issue_date
        double total_amount
        double copayment
        double insurance_coverage
        double patient_payment
        boolean policy_applied
        bigint policy_id FK
    }

    INVOICE_ITEM {
        bigint id PK
        bigint invoice_id FK
        varchar description
        int quantity
        double amount
        varchar type
    }

    PATIENT ||--|| EMERGENCY_CONTACT : "tiene"
    PATIENT ||--o| POLICY : "tiene"
    POLICY }o--|| COMPANY : "emitida por"
    ORDER_TABLE }o--|| PATIENT : "para"
    ORDER_TABLE }o--|| USER : "emitida por"
    ORDER_TABLE ||--|{ ORDER_ITEM : "contiene"
    CLINICAL_RECORD }o--|| PATIENT : "del paciente"
    CLINICAL_RECORD }o--|| USER : "escrita por"
    CLINICAL_RECORD }o--|| ORDER_TABLE : "basada en"
    CLINICAL_VISIT }o--|| PATIENT : "del paciente"
    CLINICAL_VISIT }o--|| USER : "registrada por"
    CLINICAL_VISIT }o--|| ORDER_TABLE : "basada en"
    INVOICE }o--|| PATIENT : "del paciente"
    INVOICE }o--|| USER : "emitida por"
    INVOICE }o--o| POLICY : "aplica"
    INVOICE ||--|{ INVOICE_ITEM : "contiene"
```
