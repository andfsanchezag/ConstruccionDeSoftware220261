# Documentación del Proyecto Clinic

## Arquitectura y Tecnología

### Arquitectura: Hexagonal (Ports & Adapters)

El proyecto implementa la **Arquitectura Hexagonal** (también conocida como *Ports & Adapters*), cuyo objetivo es aislar la lógica de negocio del dominio de los detalles de infraestructura (base de datos, HTTP, etc.).

```
┌─────────────────────────────────────────────────────────────────┐
│                        ADAPTADORES                              │
│  ┌──────────────────┐              ┌──────────────────────────┐ │
│  │  Entrada (API)   │              │  Salida (Persistencia)   │ │
│  │  REST Controllers│              │  JPA / MySQL Repositories│ │
│  └────────┬─────────┘              └────────────┬─────────────┘ │
│           │                                     │               │
│  ┌────────▼─────────────────────────────────────▼─────────────┐ │
│  │                     APLICACIÓN                              │ │
│  │              Use Cases / Application Services               │ │
│  └────────────────────────┬────────────────────────────────────┘ │
│                           │                                      │
│  ┌────────────────────────▼────────────────────────────────────┐ │
│  │                       DOMINIO                               │ │
│  │         Models  ·  Ports (interfaces)  ·  Services         │ │
│  └─────────────────────────────────────────────────────────────┘ │
└─────────────────────────────────────────────────────────────────┘
```

#### Capas del proyecto

| Capa | Paquete | Descripción |
|------|---------|-------------|
| **Dominio** | `app.domain` | Modelos de negocio, interfaces de puertos y servicios de dominio. No depende de ninguna tecnología externa. |
| **Aplicación** | `app.application.usecases` | Casos de uso que orquestan los servicios de dominio. |
| **Adaptadores de entrada** | `app.application.adapters.api` | Controladores REST que reciben peticiones HTTP y las traducen al dominio. |
| **Adaptadores de salida** | `app.application.adapters.persistence.sql` | Implementaciones JPA que conectan los puertos de dominio con la base de datos. |

---

### Tecnología

| Componente | Detalle |
|-----------|---------|
| **Lenguaje** | Java 17 |
| **Framework principal** | Spring Boot 4.0.2 |
| **API REST** | Spring Web MVC (`spring-boot-starter-webmvc`) |
| **Persistencia** | Spring Data JPA (`spring-boot-starter-data-jpa`) |
| **Validación** | Spring Validation (`spring-boot-starter-validation`) |
| **Base de datos** | MySQL (driver `mysql-connector-j`) |
| **Gestión de dependencias** | Apache Maven |
| **Reducción de boilerplate** | Lombok (`@Getter`, `@Setter`, `@NoArgsConstructor`) |
| **ORM / DDL** | Hibernate (dialecto `MySQLDialect`, `ddl-auto=update`) |
| **Conexión BD** | `jdbc:mysql://localhost:3306/clinica` |

---

## Modelos de Dominio

### Jerarquía de herencia

```
Person (abstracta)
├── User
├── Patient
└── EmergencyContact
```

---

### `Person` *(abstracta)*
> Clase base para toda persona en el sistema.

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| `id` | `long` | Identificador único |
| `name` | `String` | Nombre completo |
| `document` | `String` | Número de documento de identidad |
| `phone` | `String` | Teléfono de contacto |
| `email` | `String` | Correo electrónico |
| `address` | `String` | Dirección |
| `birthDate` | `Date` | Fecha de nacimiento |

---

### `User`
> Representa un empleado del sistema (médico, enfermero, administrativo, recursos humanos). Extiende `Person`.

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| *(hereda de `Person`)* | | |
| `username` | `String` | Nombre de usuario para autenticación |
| `password` | `String` | Contraseña |
| `role` | `Role` | Rol asignado al usuario |

---

### `Patient`
> Representa un paciente de la clínica. Extiende `Person`.

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| *(hereda de `Person`)* | | |
| `gender` | `boolean` | Género del paciente |
| `emergencyContact` | `EmergencyContact` | Contacto de emergencia asociado |
| `policy` | `Policy` | Póliza de seguro del paciente |

---

### `EmergencyContact`
> Representa el contacto de emergencia de un paciente. Extiende `Person`.

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| *(hereda de `Person`)* | | |
| `relationship` | `String` | Parentesco o relación con el paciente |

---

### `Company`
> Representa una empresa aseguradora o proveedor de pólizas.

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| `id` | `long` | Identificador único |
| `name` | `String` | Nombre de la empresa |

---

### `Policy`
> Representa la póliza de seguro médico de un paciente.

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| `id` | `long` | Identificador único |
| `company` | `Company` | Empresa aseguradora que emite la póliza |
| `policyNumber` | `String` | Número de póliza |
| `active` | `boolean` | Indica si la póliza está vigente |
| `expiryDate` | `Date` | Fecha de vencimiento de la póliza |

---

### `Order`
> Representa una orden médica generada por un doctor para un paciente.

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| `id` | `long` | Identificador único |
| `patient` | `Patient` | Paciente al que va dirigida la orden |
| `doctor` | `User` | Doctor que emite la orden |
| `date` | `Date` | Fecha de creación de la orden |
| `orderItems` | `List<OrderItem>` | Ítems incluidos en la orden |

---

### `OrderItem`
> Representa un ítem individual dentro de una orden médica.

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| `id` | `long` | Identificador único |
| `item` | `Item` | Ítem (medicamento, procedimiento, etc.) |
| `itemType` | `ItemType` | Tipo de ítem |

---

### `Item`
> Representa un elemento prescribible (medicamento, procedimiento o apoyo médico).

| Atributo | Tipo | Descripción |
|----------|------|-------------|
| `id` | `long` | Identificador único |
| `name` | `String` | Nombre del ítem |
| `price` | `double` | Precio del ítem |

---

### `Role` *(enumeración)*
> Define los roles disponibles para los usuarios del sistema.

| Valor | Descripción |
|-------|-------------|
| `HUMANRESOURCES` | Recursos humanos |
| `DOCTOR` | Médico |
| `NURSE` | Enfermero/a |
| `ADMINISTRATIVE` | Personal administrativo |

---

### `ItemType` *(enumeración)*
> Define los tipos de ítems que pueden incluirse en una orden médica.

| Valor | Descripción |
|-------|-------------|
| `MEDICINE` | Medicamento |
| `PROCEDURE` | Procedimiento médico |
| `MEDICALSUPPORT` | Ayuda diagnóstica / apoyo médico |

---

## Puertos de Dominio

Los puertos son **interfaces** que el dominio define para comunicarse con el mundo exterior. Las implementaciones concretas se ubican en los adaptadores de persistencia.

---

### `UserPort`
> Puerto para operaciones de persistencia sobre usuarios.

| Método | Retorno | Descripción |
|--------|---------|-------------|
| `existsByDocument(String cedula)` | `boolean` | Verifica si existe un usuario con el documento dado |
| `existsByUsername(String username)` | `boolean` | Verifica si existe un usuario con el username dado |
| `save(User user)` | `void` | Persiste un nuevo usuario |
| `findByDocument(User user)` | `User` | Busca y retorna un usuario por su documento |

---

### `PatientPort`
> Puerto para operaciones de persistencia sobre pacientes.

| Método | Retorno | Descripción |
|--------|---------|-------------|
| `existsByDocument(String cedula)` | `boolean` | Verifica si existe un paciente con el documento dado |
| `save(Patient patient)` | `void` | Persiste un nuevo paciente |
| `findByDocument(Patient patient)` | `Patient` | Busca y retorna un paciente por su documento |

---

### `ContactEmergencyPort`
> Puerto para operaciones de persistencia sobre contactos de emergencia.

| Método | Retorno | Descripción |
|--------|---------|-------------|
| `existsByDocument(String cedula)` | `boolean` | Verifica si existe un contacto de emergencia con el documento dado |
| `save(EmergencyContact emergencyContact)` | `void` | Persiste un nuevo contacto de emergencia |

---

### `OrderPort`
> Puerto para operaciones de persistencia sobre órdenes médicas.

| Método | Retorno | Descripción |
|--------|---------|-------------|
| `save(Order order)` | `void` | Persiste una nueva orden médica |

---

## Servicios de Dominio

Los servicios de dominio encapsulan las **reglas de negocio** y hacen uso de los puertos para interactuar con la infraestructura.

| Servicio | Método principal | Reglas de negocio aplicadas |
|----------|-----------------|------------------------------|
| `CreateUser` | `createUser(User)` | No duplicar documento ni username |
| `CreatePatient` | `createPatient(Patient)` | No duplicar paciente; crear contacto de emergencia si no existe |
| `CreateEmergencyContact` | `createEmergencyContact(EmergencyContact)` | No duplicar contacto por documento |
| `CreateOrder` | `createOrder(Order)` | Validar existencia de paciente y doctor; verificar rol DOCTOR; requerir al menos un ítem; prohibir mezcla de ayuda diagnóstica con otros tipos |

---

## Casos de Uso (Application Layer)

### `HumanResourcesUseCase`

Orquesta la creación de usuarios según su rol. Delega la lógica al servicio `CreateUser`.

| Método | Rol asignado |
|--------|-------------|
| `createHumanResources(User)` | `HUMANRESOURCES` |
| `createDoctor(User)` | `DOCTOR` |
| `createNurse(User)` | `NURSE` |
| `createAdministrative(User)` | `ADMINISTRATIVE` |

---

## API REST — Endpoints Implementados

Base path: `/human_resources`

| Método HTTP | Endpoint | Descripción |
|-------------|----------|-------------|
| `POST` | `/doctor` | Crea un nuevo doctor |
| `POST` | `/nurse` | Crea un nuevo enfermero/a |
| `POST` | `/administrative` | Crea un nuevo administrativo |
| `POST` | `/human_resources` | Crea un nuevo usuario de recursos humanos |
| `GET` | `/` | Health check del controlador |
