# 🏥 Dominio Completo — Sistema de Gestión Clínica

## 📌 Visión General del Dominio

El sistema modela el funcionamiento de una clínica, permitiendo gestionar:

- Usuarios (empleados)
- Pacientes
- Atención médica
- Órdenes clínicas
- Historia clínica (NoSQL)
- Facturación
- Pólizas de seguro
- Inventarios médicos

El dominio sigue principios de **DDD (Domain Driven Design)** y se estructura bajo **Arquitectura Hexagonal**.

---

# 🧱 1. Entidades Principales

## 👤 1.1 Person (Entidad Base)

Clase abstracta que representa cualquier persona en el sistema.

### Atributos:

- id: Long
- name: String
- document: String (único)
- phone: String
- email: String
- address: String
- birthDate: Date

---

## 👨‍⚕️ 1.2 User (Empleado)

Extiende `Person`.

### Atributos adicionales:

- username: String (único)
- password: String
- role: Role

### Roles posibles:

- HUMANRESOURCES
- DOCTOR
- NURSE
- ADMINISTRATIVE

### Responsabilidades:

- DOCTOR → crea órdenes y registros clínicos
- NURSE → registra visitas clínicas
- ADMINISTRATIVE → registra pacientes y facturación
- HUMANRESOURCES → gestiona usuarios

---

## 🧑‍🦱 1.3 Patient (Paciente)

Extiende `Person`.

### Atributos adicionales:

- gender: ENUM (MALE, FEMALE, OTHER)
- emergencyContact: EmergencyContact
- policy: Policy

---

## 🚨 1.4 EmergencyContact

Extiende `Person`.

### Atributos:

- relationship: String

---

## 🏢 1.5 Company (Aseguradora)

### Atributos:

- id: Long
- name: String

---

## 📄 1.6 Policy (Póliza)

### Atributos:

- id: Long
- company: Company
- policyNumber: String
- active: boolean
- expiryDate: Date

---

# 🧾 2. Facturación

## 💰 2.1 Invoice

Representa el cobro por servicios médicos.

### Atributos:

- id: Long
- patient: Patient
- doctor: User
- issueDate: Date
- totalAmount: double
- copayment: double
- insuranceCoverage: double
- patientPayment: double
- policyApplied: boolean
- policyNumber: String
- items: List\<InvoiceItem\>

---

## 📦 2.2 InvoiceItem

Detalle de cada cobro.

### Atributos:

- id: Long
- orderId: Long
- itemType: ItemType
- itemName: String
- quantity: int
- unitPrice: double
- totalPrice: double

---

## 📊 2.3 Reglas de Facturación (CRÍTICAS)

### 🧮 Cálculo:

1. totalAmount = suma de todos los ítems

2. Si póliza activa:
   - copayment = 50,000
   - insuranceCoverage = totalAmount - copayment
   - patientPayment = copayment

3. Si póliza inactiva:
   - patientPayment = totalAmount
   - insuranceCoverage = 0

4. Regla anual:
   - Si copagos acumulados del año > 1,000,000:
     - copayment = 0
     - insuranceCoverage = totalAmount

---

## 📈 2.4 PolicyHistory

Controla acumulado anual de copagos.

### Atributos:

- id: Long
- patient: Patient
- year: int
- accumulatedCopayment: double

---

# 🧠 3. Historia Clínica (NoSQL)

## 📘 3.1 ClinicalRecord

### Atributos:

- patientDocument: String
- date: Date
- doctorDocument: String
- reason: String
- symptoms: String
- diagnosis: String (opcional)
- orders: List\<Long\>

---

## ⚠️ Reglas:

- Si hay ayuda diagnóstica → NO hay diagnóstico
- Se debe crear un nuevo registro cuando se obtiene el diagnóstico

---

# 🏥 4. Órdenes Médicas

## 📄 4.1 Order

### Atributos:

- id: Long
- orderNumber: Long (máx 6 dígitos, único)
- patient: Patient
- doctor: User
- date: Date
- orderItems: List\<OrderItem\>

---

## 📦 4.2 OrderItem

### Atributos:

- id: Long
- item: Item
- itemType: ItemType
- itemNumber: int (único dentro de la orden)

---

## 🔖 Tipos de Item:

- MEDICINE
- PROCEDURE
- MEDICALSUPPORT

---

## ⚠️ Reglas de Negocio (Órdenes)

1. Debe existir paciente
2. El usuario debe ser DOCTOR
3. Debe haber al menos un ítem
4. NO se puede mezclar:
   - MEDICALSUPPORT con MEDICINE o PROCEDURE
5. itemNumber debe ser único dentro de la orden
6. orderNumber es único global

---

# 🧾 5. Detalle SQL de Órdenes

## 💊 OrderMedicine

- orderNumber
- itemNumber
- medicineName
- dose
- duration
- cost

---

## 🏥 OrderProcedure

- orderNumber
- itemNumber
- procedureName
- quantity
- frequency
- cost
- requiresSpecialist
- specialtyId

---

## 🔬 OrderDiagnostic

- orderNumber
- itemNumber
- diagnosticName
- quantity
- cost
- requiresSpecialist
- specialtyId

---

# 🏥 6. Visita Clínica

## 🩺 ClinicalVisit

### Atributos:

- id: Long
- patient: Patient
- nurse: User
- date: Date
- bloodPressure: String
- temperature: double
- pulse: int
- oxygenLevel: double
- observations: String
- orderId: Long

---

## ⚠️ Reglas:

- Solo NURSE puede registrar
- Debe existir orden asociada
- Debe registrar signos vitales obligatorios

---

# 💊 7. Inventarios

## 🧪 Medicine

- id
- name
- price

---

## 🏥 Procedure

- id
- name
- price
- requiresSpecialist
- specialtyId

---

## 🔬 DiagnosticSupport

- id
- name
- price
- requiresSpecialist
- specialtyId

---

## 👨‍⚕️ Specialty

- id
- name

---

# 🔗 8. Relaciones Clave

- Patient → 1 EmergencyContact
- Patient → 1 Policy
- Patient → N Orders
- Order → N Items
- Order → 1 Doctor
- Invoice → N Items
- ClinicalRecord → N Orders
- ClinicalVisit → 1 Order

---

# ⚙️ 9. Reglas Globales del Dominio

## 🔒 Integridad

- Documentos únicos
- Username único
- Número de orden único

## 📅 Restricciones

- Edad máxima: 150 años
- Teléfono: máximo 10 dígitos

## 🧠 Reglas médicas

- Diagnóstico posterior a ayuda diagnóstica
- Orden define tratamiento
- Visita ejecuta tratamiento

---

# 🧩 10. Separación Arquitectónica

## Dominio:
- Modelos
- Reglas de negocio
- Puertos

## Aplicación:
- Casos de uso

## Infraestructura:
- Base de datos SQL (órdenes, facturación)
- NoSQL (historia clínica)

---

# 🚨 11. Conclusión

Este dominio:

✅ Cumple completamente el enunciado  
✅ Permite escalar a microservicios  
✅ Separa correctamente responsabilidades  
✅ Modela reglas reales del negocio clínico  

---

Si quieres, el siguiente paso más potente sería:

# 🧩 Diagrama UML — Sistema de Gestión Clínica

## 📌 Notación
- `+` público
- `-` privado
- `#` protegido
- `<>` composición
- `--` asociación
- `--|>` herencia

---

## 🧠 Diagrama de Clases (Mermaid)

```mermaid
classDiagram

%% =========================
%% HERENCIA BASE
%% =========================
class Person {
  - Long id
  - String name
  - String document
  - String phone
  - String email
  - String address
  - Date birthDate
}

class User {
  - String username
  - String password
  - Role role
}

class Patient {
  - Gender gender
}

class EmergencyContact {
  - String relationship
}

Person <|-- User
Person <|-- Patient
Person <|-- EmergencyContact

%% =========================
%% ENUMS
%% =========================
class Role {
  <<enumeration>>
  HUMANRESOURCES
  DOCTOR
  NURSE
  ADMINISTRATIVE
}

class ItemType {
  <<enumeration>>
  MEDICINE
  PROCEDURE
  MEDICALSUPPORT
}

class Gender {
  <<enumeration>>
  MALE
  FEMALE
  OTHER
}

%% =========================
%% POLIZA
%% =========================
class Company {
  - Long id
  - String name
}

class Policy {
  - Long id
  - String policyNumber
  - boolean active
  - Date expiryDate
}

Patient --> Policy
Policy --> Company

%% =========================
%% FACTURACION
%% =========================
class Invoice {
  - Long id
  - Date issueDate
  - double totalAmount
  - double copayment
  - double insuranceCoverage
  - double patientPayment
  - boolean policyApplied
  - String policyNumber
}

class InvoiceItem {
  - Long id
  - Long orderId
  - ItemType itemType
  - String itemName
  - int quantity
  - double unitPrice
  - double totalPrice
}

Invoice "1" *-- "1..*" InvoiceItem
Invoice --> Patient
Invoice --> User

class PolicyHistory {
  - Long id
  - int year
  - double accumulatedCopayment
}

PolicyHistory --> Patient

%% =========================
%% ORDENES
%% =========================
class Order {
  - Long id
  - Long orderNumber
  - Date date
}

class OrderItem {
  - Long id
  - int itemNumber
  - ItemType itemType
}

class Item {
  - Long id
  - String name
  - double price
}

Order "1" *-- "1..*" OrderItem
OrderItem --> Item
Order --> Patient
Order --> User

%% =========================
%% DETALLE ORDEN (SQL)
%% =========================
class OrderMedicine {
  - Long orderNumber
  - int itemNumber
  - String medicineName
  - String dose
  - String duration
  - double cost
}

class OrderProcedure {
  - Long orderNumber
  - int itemNumber
  - String procedureName
  - int quantity
  - String frequency
  - double cost
  - boolean requiresSpecialist
  - Long specialtyId
}

class OrderDiagnostic {
  - Long orderNumber
  - int itemNumber
  - String diagnosticName
  - int quantity
  - double cost
  - boolean requiresSpecialist
  - Long specialtyId
}

%% =========================
%% HISTORIA CLINICA (NoSQL)
%% =========================
class ClinicalRecord {
  - String patientDocument
  - Date date
  - String doctorDocument
  - String reason
  - String symptoms
  - String diagnosis
}

ClinicalRecord --> Order

%% =========================
%% VISITA CLINICA
%% =========================
class ClinicalVisit {
  - Long id
  - Date date
  - String bloodPressure
  - double temperature
  - int pulse
  - double oxygenLevel
  - String observations
}

ClinicalVisit --> Patient
ClinicalVisit --> User
ClinicalVisit --> Order

%% =========================
%% INVENTARIO
%% =========================
class Medicine {
  - Long id
  - String name
  - double price
}

class Procedure {
  - Long id
  - String name
  - double price
  - boolean requiresSpecialist
  - Long specialtyId
}

class DiagnosticSupport {
  - Long id
  - String name
  - double price
  - boolean requiresSpecialist
  - Long specialtyId
}

class Specialty {
  - Long id
  - String name
}

Procedure --> Specialty
DiagnosticSupport --> Specialty

%% =========================
%% RELACIONES CLAVE
%% =========================
Patient --> EmergencyContact
Patient --> Order
Patient --> Invoice