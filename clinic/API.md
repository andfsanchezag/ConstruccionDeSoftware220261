# Documentación de la API — Clinic REST API

Base URL: `http://localhost:8081`

---

## Tabla de Contenidos

- [Autenticación](#autenticación)
- [Recursos Humanos — `/human-resources`](#recursos-humanos--human-resources)
- [Administrativo — `/administrative`](#administrativo--administrative)
- [Médico — `/doctor`](#médico--doctor)
- [Enfermería — `/nurse`](#enfermería--nurse)
- [Modelos de Datos](#modelos-de-datos)
- [Códigos de Error](#códigos-de-error)

---

## Autenticación

Todos los endpoints (excepto `/auth/login`) requieren el siguiente header:

```
Authorization: Bearer <JWT_TOKEN>
```

El token se obtiene del endpoint de login y tiene una validez configurada en `app.jwt.expiration`.

---

## Autenticación — `/auth`

### POST `/auth/login`

Autentica un usuario y retorna un JWT Bearer Token.

**Headers:**
```
Content-Type: application/json
```

**Request Body:**
```json
{
  "username": "dr.garcia",
  "password": "secreto123"
}
```

**Response 200 OK:**
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJkci5nYXJjaWEiLCJkb2N1bWVudCI6IjEwMjM0NTY3ODkiLCJyb2xlIjoiRE9DVE9SIiwiaWF0IjoxNzQzMDAwMDAwLCJleHAiOjE3NDMwMzYwMDB9.xxxx",
  "document": "1023456789",
  "role": "DOCTOR"
}
```

**cURL:**
```bash
curl -X POST http://localhost:8081/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "dr.garcia",
    "password": "secreto123"
  }'
```

---

## Recursos Humanos — `/human-resources`

> **Rol requerido:** `HUMANRESOURCES`

### POST `/human-resources/doctors`

Crea un nuevo usuario con rol **DOCTOR**.

**Headers:**
```
Content-Type: application/json
Authorization: Bearer <JWT_TOKEN>
```

**Request Body:**
```json
{
  "document": "1023456789",
  "name": "Carlos García Reyes",
  "username": "dr.garcia",
  "password": "secreto123",
  "phone": "3001234567",
  "email": "carlos.garcia@clinic.com",
  "address": "Calle 50 #40-20, Medellín",
  "birthDate": "1985-03-15"
}
```

**Response 201 Created:**
```json
{
  "id": 1,
  "document": "1023456789",
  "name": "Carlos García Reyes",
  "username": "dr.garcia",
  "role": "DOCTOR",
  "phone": "3001234567",
  "email": "carlos.garcia@clinic.com",
  "address": "Calle 50 #40-20, Medellín",
  "birthDate": "1985-03-15"
}
```

**cURL:**
```bash
curl -X POST http://localhost:8081/human-resources/doctors \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "document": "1023456789",
    "name": "Carlos García Reyes",
    "username": "dr.garcia",
    "password": "secreto123",
    "phone": "3001234567",
    "email": "carlos.garcia@clinic.com",
    "address": "Calle 50 #40-20, Medellín",
    "birthDate": "1985-03-15"
  }'
```

---

### POST `/human-resources/nurses`

Crea un nuevo usuario con rol **NURSE**.

**Headers:**
```
Content-Type: application/json
Authorization: Bearer <JWT_TOKEN>
```

**Request Body:**
```json
{
  "document": "98765432",
  "name": "María López Torres",
  "username": "enf.lopez",
  "password": "clave456",
  "phone": "3109876543",
  "email": "maria.lopez@clinic.com",
  "address": "Carrera 70 #10-15, Medellín",
  "birthDate": "1990-07-22"
}
```

**Response 201 Created:**
```json
{
  "id": 2,
  "document": "98765432",
  "name": "María López Torres",
  "username": "enf.lopez",
  "role": "NURSE",
  "phone": "3109876543",
  "email": "maria.lopez@clinic.com",
  "address": "Carrera 70 #10-15, Medellín",
  "birthDate": "1990-07-22"
}
```

**cURL:**
```bash
curl -X POST http://localhost:8081/human-resources/nurses \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "document": "98765432",
    "name": "María López Torres",
    "username": "enf.lopez",
    "password": "clave456",
    "phone": "3109876543",
    "email": "maria.lopez@clinic.com",
    "address": "Carrera 70 #10-15, Medellín",
    "birthDate": "1990-07-22"
  }'
```

---

### POST `/human-resources/administrative`

Crea un nuevo usuario con rol **ADMINISTRATIVE**.

**cURL:**
```bash
curl -X POST http://localhost:8081/human-resources/administrative \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "document": "55443322",
    "name": "Ana Martínez Soto",
    "username": "admin.martinez",
    "password": "admin789",
    "phone": "3204455667",
    "email": "ana.martinez@clinic.com",
    "address": "Avenida El Poblado #12-34, Medellín",
    "birthDate": "1992-11-05"
  }'
```

---

### POST `/human-resources/`

Crea un nuevo usuario con rol **HUMANRESOURCES**.

**cURL:**
```bash
curl -X POST http://localhost:8081/human-resources/ \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "document": "11223344",
    "name": "Jorge Herrera Cano",
    "username": "rrhh.herrera",
    "password": "rrhh2024",
    "phone": "3157788990",
    "email": "jorge.herrera@clinic.com",
    "address": "Calle 10 #30-50, Medellín",
    "birthDate": "1980-01-30"
  }'
```

---

### PUT `/human-resources/{document}`

Actualiza los datos de un usuario existente.

**Headers:**
```
Content-Type: application/json
Authorization: Bearer <JWT_TOKEN>
```

**Request Body:**
```json
{
  "document": "1023456789",
  "name": "Carlos García Reyes",
  "username": "dr.garcia",
  "password": "nuevaclave789",
  "phone": "3001234567",
  "email": "carlos.garcia.nuevo@clinic.com",
  "address": "Calle 80 #55-10, Medellín",
  "birthDate": "1985-03-15"
}
```

**Response 200 OK:**
```json
{
  "id": 1,
  "document": "1023456789",
  "name": "Carlos García Reyes",
  "username": "dr.garcia",
  "role": "DOCTOR",
  "phone": "3001234567",
  "email": "carlos.garcia.nuevo@clinic.com",
  "address": "Calle 80 #55-10, Medellín",
  "birthDate": "1985-03-15"
}
```

**cURL:**
```bash
curl -X PUT http://localhost:8081/human-resources/1023456789 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "document": "1023456789",
    "name": "Carlos García Reyes",
    "username": "dr.garcia",
    "password": "nuevaclave789",
    "phone": "3001234567",
    "email": "carlos.garcia.nuevo@clinic.com",
    "address": "Calle 80 #55-10, Medellín",
    "birthDate": "1985-03-15"
  }'
```

---

### DELETE `/human-resources/{document}`

Elimina un usuario del sistema.

**Headers:**
```
Authorization: Bearer <JWT_TOKEN>
```

**Response 204 No Content**

**cURL:**
```bash
curl -X DELETE http://localhost:8081/human-resources/1023456789 \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

---

### GET `/human-resources/{document}`

Obtiene un usuario por su número de documento.

**Headers:**
```
Authorization: Bearer <JWT_TOKEN>
```

**Response 200 OK:**
```json
{
  "id": 1,
  "document": "1023456789",
  "name": "Carlos García Reyes",
  "username": "dr.garcia",
  "role": "DOCTOR",
  "phone": "3001234567",
  "email": "carlos.garcia@clinic.com",
  "address": "Calle 50 #40-20, Medellín",
  "birthDate": "1985-03-15"
}
```

**cURL:**
```bash
curl -X GET http://localhost:8081/human-resources/1023456789 \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

---

### GET `/human-resources/`

Lista todos los usuarios del sistema.

**Headers:**
```
Authorization: Bearer <JWT_TOKEN>
```

**Response 200 OK:**
```json
[
  {
    "id": 1,
    "document": "1023456789",
    "name": "Carlos García Reyes",
    "username": "dr.garcia",
    "role": "DOCTOR",
    "phone": "3001234567",
    "email": "carlos.garcia@clinic.com",
    "address": "Calle 50 #40-20, Medellín",
    "birthDate": "1985-03-15"
  },
  {
    "id": 2,
    "document": "98765432",
    "name": "María López Torres",
    "username": "enf.lopez",
    "role": "NURSE",
    "phone": "3109876543",
    "email": "maria.lopez@clinic.com",
    "address": "Carrera 70 #10-15, Medellín",
    "birthDate": "1990-07-22"
  }
]
```

**cURL:**
```bash
curl -X GET http://localhost:8081/human-resources/ \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

---

## Administrativo — `/administrative`

> **Rol requerido:** `ADMINISTRATIVE`

### POST `/administrative/patients`

Registra un nuevo paciente en el sistema.

**Headers:**
```
Content-Type: application/json
Authorization: Bearer <JWT_TOKEN>
```

**Request Body:**
```json
{
  "document": "71234567",
  "name": "Pedro Ramírez Vélez",
  "phone": "3001112233",
  "email": "pedro.ramirez@email.com",
  "address": "Calle 30 #20-10, Bello",
  "birthDate": "1978-05-12",
  "gender": "MALE",
  "emergencyContact": {
    "document": "45678901",
    "name": "Lucía Ramírez Vélez",
    "phone": "3129988776",
    "email": "lucia.ramirez@email.com",
    "address": "Calle 30 #20-10, Bello",
    "birthDate": "1980-09-25",
    "relationship": "Hermana"
  }
}
```

**Response 201 Created:**
```json
{
  "id": 10,
  "document": "71234567",
  "name": "Pedro Ramírez Vélez",
  "phone": "3001112233",
  "email": "pedro.ramirez@email.com",
  "address": "Calle 30 #20-10, Bello",
  "birthDate": "1978-05-12",
  "gender": "MALE",
  "emergencyContact": {
    "id": 5,
    "document": "45678901",
    "name": "Lucía Ramírez Vélez",
    "phone": "3129988776",
    "relationship": "Hermana"
  },
  "policy": null
}
```

**cURL:**
```bash
curl -X POST http://localhost:8081/administrative/patients \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "document": "71234567",
    "name": "Pedro Ramírez Vélez",
    "phone": "3001112233",
    "email": "pedro.ramirez@email.com",
    "address": "Calle 30 #20-10, Bello",
    "birthDate": "1978-05-12",
    "gender": "MALE",
    "emergencyContact": {
      "document": "45678901",
      "name": "Lucía Ramírez Vélez",
      "phone": "3129988776",
      "email": "lucia.ramirez@email.com",
      "address": "Calle 30 #20-10, Bello",
      "birthDate": "1980-09-25",
      "relationship": "Hermana"
    }
  }'
```

---

### PUT `/administrative/patients/{document}`

Actualiza los datos de un paciente.

**Headers:**
```
Content-Type: application/json
Authorization: Bearer <JWT_TOKEN>
```

**Request Body:**
```json
{
  "document": "71234567",
  "name": "Pedro Ramírez Vélez",
  "phone": "3006665544",
  "email": "pedro.nuevo@email.com",
  "address": "Carrera 50 #10-20, Bello",
  "birthDate": "1978-05-12",
  "gender": "MALE"
}
```

**Response 200 OK:** _(mismo esquema que POST)_

**cURL:**
```bash
curl -X PUT http://localhost:8081/administrative/patients/71234567 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "document": "71234567",
    "name": "Pedro Ramírez Vélez",
    "phone": "3006665544",
    "email": "pedro.nuevo@email.com",
    "address": "Carrera 50 #10-20, Bello",
    "birthDate": "1978-05-12",
    "gender": "MALE"
  }'
```

---

### DELETE `/administrative/patients/{document}`

Elimina un paciente del sistema.

**Headers:**
```
Authorization: Bearer <JWT_TOKEN>
```

**Response 204 No Content**

**cURL:**
```bash
curl -X DELETE http://localhost:8081/administrative/patients/71234567 \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

---

### GET `/administrative/patients/{document}`

Obtiene un paciente por documento.

**Response 200 OK:**
```json
{
  "id": 10,
  "document": "71234567",
  "name": "Pedro Ramírez Vélez",
  "phone": "3001112233",
  "email": "pedro.ramirez@email.com",
  "address": "Calle 30 #20-10, Bello",
  "birthDate": "1978-05-12",
  "gender": "MALE",
  "emergencyContact": {
    "id": 5,
    "document": "45678901",
    "name": "Lucía Ramírez Vélez",
    "phone": "3129988776",
    "relationship": "Hermana"
  },
  "policy": {
    "id": 3,
    "policyNumber": "POL-2025-001",
    "active": true,
    "expiryDate": "2026-12-31",
    "company": "Sura"
  }
}
```

**cURL:**
```bash
curl -X GET http://localhost:8081/administrative/patients/71234567 \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

---

### GET `/administrative/patients`

Lista todos los pacientes.

**cURL:**
```bash
curl -X GET http://localhost:8081/administrative/patients \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

---

### POST `/administrative/emergency-contacts`

Crea un contacto de emergencia para un paciente existente.

**Headers:**
```
Content-Type: application/json
Authorization: Bearer <JWT_TOKEN>
```

**Request Body:**
```json
{
  "patientDocument": "71234567",
  "document": "88991122",
  "name": "Rosa Ramírez",
  "phone": "3145566778",
  "email": "rosa.ramirez@email.com",
  "address": "Calle 50 #30-10, Medellín",
  "birthDate": "1955-03-10",
  "relationship": "Madre"
}
```

**Response 201 Created:**
```json
{
  "id": 6,
  "document": "88991122",
  "name": "Rosa Ramírez",
  "phone": "3145566778",
  "relationship": "Madre"
}
```

**cURL:**
```bash
curl -X POST http://localhost:8081/administrative/emergency-contacts \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "patientDocument": "71234567",
    "document": "88991122",
    "name": "Rosa Ramírez",
    "phone": "3145566778",
    "email": "rosa.ramirez@email.com",
    "address": "Calle 50 #30-10, Medellín",
    "birthDate": "1955-03-10",
    "relationship": "Madre"
  }'
```

---

### PUT `/administrative/emergency-contacts/{patientDocument}`

Actualiza el contacto de emergencia de un paciente.

**cURL:**
```bash
curl -X PUT http://localhost:8081/administrative/emergency-contacts/71234567 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "document": "88991122",
    "name": "Rosa Ramírez Pérez",
    "phone": "3200011223",
    "email": "rosa.nueva@email.com",
    "address": "Carrera 20 #45-30, Medellín",
    "birthDate": "1955-03-10",
    "relationship": "Madre"
  }'
```

---

### POST `/administrative/patients/{patientDocument}/policies`

Asigna una póliza de seguro a un paciente.

**Headers:**
```
Content-Type: application/json
Authorization: Bearer <JWT_TOKEN>
```

**Request Body:**
```json
{
  "policyNumber": "POL-2025-001",
  "active": true,
  "expiryDate": "2026-12-31",
  "company": "Sura"
}
```

**Response 201 Created:**
```json
{
  "id": 3,
  "policyNumber": "POL-2025-001",
  "active": true,
  "expiryDate": "2026-12-31",
  "company": "Sura"
}
```

**cURL:**
```bash
curl -X POST http://localhost:8081/administrative/patients/71234567/policies \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "policyNumber": "POL-2025-001",
    "active": true,
    "expiryDate": "2026-12-31",
    "company": "Sura"
  }'
```

---

### PUT `/administrative/policies/{id}`

Actualiza una póliza existente.

**cURL:**
```bash
curl -X PUT http://localhost:8081/administrative/policies/3 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "policyNumber": "POL-2025-001",
    "active": false,
    "expiryDate": "2025-06-30",
    "company": "Sura"
  }'
```

---

### POST `/administrative/orders`

Crea una orden médica en nombre de un médico (el campo `doctorDocument` es obligatorio).

**Headers:**
```
Content-Type: application/json
Authorization: Bearer <JWT_TOKEN>
```

**Request Body:**
```json
{
  "patientDocument": "71234567",
  "doctorDocument": "1023456789",
  "orderItems": [
    {
      "inventoryItemId": 5,
      "itemType": "MEDICINE"
    },
    {
      "inventoryItemId": 12,
      "itemType": "PROCEDURE"
    }
  ]
}
```

**Response 201 Created:**
```json
{
  "id": 20,
  "patientDocument": "71234567",
  "patientName": "Pedro Ramírez Vélez",
  "doctorDocument": "1023456789",
  "doctorName": "Carlos García Reyes",
  "date": "2026-03-25",
  "items": [
    {
      "id": 30,
      "inventoryItemId": 5,
      "itemType": "MEDICINE",
      "itemName": "Amoxicilina 500mg"
    },
    {
      "id": 31,
      "inventoryItemId": 12,
      "itemType": "PROCEDURE",
      "itemName": "Electrocardiograma"
    }
  ]
}
```

**cURL:**
```bash
curl -X POST http://localhost:8081/administrative/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "patientDocument": "71234567",
    "doctorDocument": "1023456789",
    "orderItems": [
      { "inventoryItemId": 5, "itemType": "MEDICINE" },
      { "inventoryItemId": 12, "itemType": "PROCEDURE" }
    ]
  }'
```

---

### GET `/administrative/orders/{id}`

Obtiene una orden médica por ID.

**cURL:**
```bash
curl -X GET http://localhost:8081/administrative/orders/20 \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

---

### GET `/administrative/patients/{patientDocument}/orders`

Lista todas las órdenes de un paciente.

**cURL:**
```bash
curl -X GET http://localhost:8081/administrative/patients/71234567/orders \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

---

### POST `/administrative/invoices`

Genera una factura para un paciente.

**Headers:**
```
Content-Type: application/json
Authorization: Bearer <JWT_TOKEN>
```

**Request Body:**
```json
{
  "patientDocument": "71234567",
  "policyId": 3,
  "items": [
    {
      "description": "Consulta médica general",
      "quantity": 1,
      "amount": 80000.00,
      "type": "CONSULTATION"
    },
    {
      "description": "Amoxicilina 500mg x 10",
      "quantity": 10,
      "amount": 5000.00,
      "type": "MEDICINE"
    }
  ]
}
```

**Response 201 Created:**
```json
{
  "id": 8,
  "patientDocument": "71234567",
  "patientName": "Pedro Ramírez Vélez",
  "doctorDocument": "1023456789",
  "doctorName": "Carlos García Reyes",
  "issueDate": "2026-03-25",
  "totalAmount": 130000.00,
  "copayment": 13000.00,
  "insuranceCoverage": 104000.00,
  "patientPayment": 26000.00,
  "policyApplied": true,
  "policy": {
    "id": 3,
    "policyNumber": "POL-2025-001",
    "active": true,
    "expiryDate": "2026-12-31",
    "company": "Sura"
  },
  "items": [
    {
      "id": 15,
      "description": "Consulta médica general",
      "quantity": 1,
      "amount": 80000.00,
      "type": "CONSULTATION"
    },
    {
      "id": 16,
      "description": "Amoxicilina 500mg x 10",
      "quantity": 10,
      "amount": 5000.00,
      "type": "MEDICINE"
    }
  ]
}
```

**cURL:**
```bash
curl -X POST http://localhost:8081/administrative/invoices \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "patientDocument": "71234567",
    "policyId": 3,
    "items": [
      { "description": "Consulta médica general", "quantity": 1, "amount": 80000.00, "type": "CONSULTATION" },
      { "description": "Amoxicilina 500mg x 10", "quantity": 10, "amount": 5000.00, "type": "MEDICINE" }
    ]
  }'
```

---

### GET `/administrative/invoices/{id}`

Obtiene una factura por ID.

**cURL:**
```bash
curl -X GET http://localhost:8081/administrative/invoices/8 \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

---

### GET `/administrative/patients/{patientDocument}/invoices`

Lista todas las facturas de un paciente.

**cURL:**
```bash
curl -X GET http://localhost:8081/administrative/patients/71234567/invoices \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

---

## Médico — `/doctor`

> **Rol requerido para escritura:** `DOCTOR`  
> **Rol requerido para lectura (GET):** `DOCTOR`, `NURSE`, `ADMINISTRATIVE`

> **Nota:** El `doctorDocument` se extrae automáticamente del JWT; no debe enviarse en el body.

### GET `/doctor/patients/{document}`

Obtiene un paciente por documento.

**Headers:**
```
Authorization: Bearer <JWT_TOKEN>
```

**Response 200 OK:** _(mismo esquema que el del administrativo)_

**cURL:**
```bash
curl -X GET http://localhost:8081/doctor/patients/71234567 \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

---

### GET `/doctor/patients`

Lista todos los pacientes.

**cURL:**
```bash
curl -X GET http://localhost:8081/doctor/patients \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

---

### POST `/doctor/orders`

Crea una orden médica. El médico es tomado automáticamente del token JWT.

**Headers:**
```
Content-Type: application/json
Authorization: Bearer <JWT_TOKEN>
```

**Request Body:**
```json
{
  "patientDocument": "71234567",
  "orderItems": [
    {
      "inventoryItemId": 5,
      "itemType": "MEDICINE"
    },
    {
      "inventoryItemId": 8,
      "itemType": "MEDICALSUPPORT"
    }
  ]
}
```

> `doctorDocument` se ignora; se obtiene del token JWT.

**Response 201 Created:**
```json
{
  "id": 21,
  "patientDocument": "71234567",
  "patientName": "Pedro Ramírez Vélez",
  "doctorDocument": "1023456789",
  "doctorName": "Carlos García Reyes",
  "date": "2026-03-25",
  "items": [
    {
      "id": 32,
      "inventoryItemId": 5,
      "itemType": "MEDICINE",
      "itemName": "Amoxicilina 500mg"
    },
    {
      "id": 33,
      "inventoryItemId": 8,
      "itemType": "MEDICALSUPPORT",
      "itemName": "Radiografía de tórax"
    }
  ]
}
```

**cURL:**
```bash
curl -X POST http://localhost:8081/doctor/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "patientDocument": "71234567",
    "orderItems": [
      { "inventoryItemId": 5, "itemType": "MEDICINE" },
      { "inventoryItemId": 8, "itemType": "MEDICALSUPPORT" }
    ]
  }'
```

---

### GET `/doctor/orders/{id}`

Obtiene una orden médica por ID.

**cURL:**
```bash
curl -X GET http://localhost:8081/doctor/orders/21 \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

---

### GET `/doctor/patients/{patientDocument}/orders`

Lista todas las órdenes de un paciente.

**cURL:**
```bash
curl -X GET http://localhost:8081/doctor/patients/71234567/orders \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

---

### POST `/doctor/clinical-records`

Crea un registro clínico (historia clínica). El médico es tomado del token JWT.

**Headers:**
```
Content-Type: application/json
Authorization: Bearer <JWT_TOKEN>
```

**Request Body:**
```json
{
  "patientDocument": "71234567",
  "orderId": 21,
  "reason": "Consulta por dolor de garganta persistente hace 3 días",
  "symptoms": "Odinofagia, fiebre 38.5°C, inflamación de amígdalas, ganglios inflamados",
  "diagnosis": "Amigdalitis bacteriana aguda. Se prescribe Amoxicilina 500mg cada 8 horas por 7 días."
}
```

**Response 201 Created:**
```json
{
  "id": 15,
  "patientDocument": "71234567",
  "patientName": "Pedro Ramírez Vélez",
  "doctorDocument": "1023456789",
  "doctorName": "Carlos García Reyes",
  "date": "2026-03-25",
  "reason": "Consulta por dolor de garganta persistente hace 3 días",
  "symptoms": "Odinofagia, fiebre 38.5°C, inflamación de amígdalas, ganglios inflamados",
  "diagnosis": "Amigdalitis bacteriana aguda. Se prescribe Amoxicilina 500mg cada 8 horas por 7 días.",
  "orderId": 21
}
```

**cURL:**
```bash
curl -X POST http://localhost:8081/doctor/clinical-records \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "patientDocument": "71234567",
    "orderId": 21,
    "reason": "Consulta por dolor de garganta persistente hace 3 días",
    "symptoms": "Odinofagia, fiebre 38.5°C, inflamación de amígdalas, ganglios inflamados",
    "diagnosis": "Amigdalitis bacteriana aguda. Se prescribe Amoxicilina 500mg cada 8 horas por 7 días."
  }'
```

---

### GET `/doctor/patients/{patientDocument}/clinical-records`

Lista todos los registros clínicos de un paciente.

**Response 200 OK:**
```json
[
  {
    "id": 15,
    "patientDocument": "71234567",
    "patientName": "Pedro Ramírez Vélez",
    "doctorDocument": "1023456789",
    "doctorName": "Carlos García Reyes",
    "date": "2026-03-25",
    "reason": "Consulta por dolor de garganta persistente hace 3 días",
    "symptoms": "Odinofagia, fiebre 38.5°C, inflamación de amígdalas, ganglios inflamados",
    "diagnosis": "Amigdalitis bacteriana aguda.",
    "orderId": 21
  }
]
```

**cURL:**
```bash
curl -X GET http://localhost:8081/doctor/patients/71234567/clinical-records \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

---

## Enfermería — `/nurse`

> **Rol requerido para escritura:** `NURSE`  
> **Rol requerido para lectura (GET):** `NURSE`, `DOCTOR`

> **Nota:** El `nurseDocument` se extrae automáticamente del JWT.

### GET `/nurse/patients/{document}`

Obtiene un paciente por documento.

**cURL:**
```bash
curl -X GET http://localhost:8081/nurse/patients/71234567 \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

---

### GET `/nurse/patients`

Lista todos los pacientes.

**cURL:**
```bash
curl -X GET http://localhost:8081/nurse/patients \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

---

### GET `/nurse/orders/{id}`

Obtiene una orden médica por ID.

**cURL:**
```bash
curl -X GET http://localhost:8081/nurse/orders/21 \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

---

### GET `/nurse/patients/{patientDocument}/orders`

Lista todas las órdenes de un paciente.

**cURL:**
```bash
curl -X GET http://localhost:8081/nurse/patients/71234567/orders \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

---

### POST `/nurse/clinical-visits`

Registra una visita clínica. La enfermera es tomada del token JWT.

**Headers:**
```
Content-Type: application/json
Authorization: Bearer <JWT_TOKEN>
```

**Request Body:**
```json
{
  "patientDocument": "71234567",
  "orderId": 21,
  "bloodPressure": "120/80",
  "temperature": 38.5,
  "pulse": 92,
  "oxygenLevel": 97.5,
  "observations": "Paciente consciente y orientado. Refiere dolor moderado al deglutir. Se administró Acetaminofén 500mg. Temperatura en leve descenso respecto a visita anterior."
}
```

**Response 201 Created:**
```json
{
  "id": 9,
  "patientDocument": "71234567",
  "patientName": "Pedro Ramírez Vélez",
  "nurseDocument": "98765432",
  "nurseName": "María López Torres",
  "date": "2026-03-25",
  "bloodPressure": "120/80",
  "temperature": 38.5,
  "pulse": 92,
  "oxygenLevel": 97.5,
  "observations": "Paciente consciente y orientado. Refiere dolor moderado al deglutir.",
  "orderId": 21
}
```

**cURL:**
```bash
curl -X POST http://localhost:8081/nurse/clinical-visits \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <JWT_TOKEN>" \
  -d '{
    "patientDocument": "71234567",
    "orderId": 21,
    "bloodPressure": "120/80",
    "temperature": 38.5,
    "pulse": 92,
    "oxygenLevel": 97.5,
    "observations": "Paciente consciente y orientado. Refiere dolor moderado al deglutir."
  }'
```

---

### GET `/nurse/patients/{patientDocument}/clinical-records`

Lista todos los registros clínicos de un paciente (solo lectura).

**cURL:**
```bash
curl -X GET http://localhost:8081/nurse/patients/71234567/clinical-records \
  -H "Authorization: Bearer <JWT_TOKEN>"
```

---

## Modelos de Datos

### Enumeraciones

#### `Gender`
| Valor | Descripción |
|-------|-------------|
| `MALE` | Masculino |
| `FEMALE` | Femenino |

#### `Role`
| Valor | Descripción |
|-------|-------------|
| `HUMANRESOURCES` | Recursos humanos |
| `DOCTOR` | Médico |
| `NURSE` | Enfermera/o |
| `ADMINISTRATIVE` | Administrativo |

#### `ItemType`
| Valor | Descripción |
|-------|-------------|
| `MEDICINE` | Medicamento |
| `PROCEDURE` | Procedimiento médico |
| `MEDICALSUPPORT` | Soporte diagnóstico (ej. rayos X, laboratorio) |

---

## Códigos de Error

| Código HTTP | Causa | Mensaje típico |
|-------------|-------|----------------|
| `400 Bad Request` | Datos de entrada inválidos (validación fallida) | `{"field": "document", "message": "El documento es obligatorio"}` |
| `401 Unauthorized` | Token ausente, expirado o inválido | `{"error": "Token expirado"}` / `{"error": "No autenticado: se requiere un token válido"}` |
| `403 Forbidden` | Rol sin permisos para el endpoint | `{"error": "Acceso denegado: no tiene permisos para este recurso"}` |
| `404 Not Found` | Recurso no encontrado | `{"error": "Paciente no encontrado"}` |
| `422 Unprocessable Entity` | Regla de negocio violada | `{"error": "El paciente ya tiene una póliza activa"}` |
| `500 Internal Server Error` | Error interno del servidor | `{"error": "Error interno del servidor"}` |

**Ejemplo de error de validación (400):**
```json
{
  "timestamp": "2026-03-25T12:00:00",
  "status": 400,
  "errors": [
    { "field": "document", "message": "El documento es obligatorio" },
    { "field": "email", "message": "Debe ser una dirección de correo válida" }
  ]
}
```

**Ejemplo de error de autenticación (401):**
```json
{
  "error": "No autenticado: se requiere un token válido"
}
```

**Ejemplo de error de autorización (403):**
```json
{
  "error": "Acceso denegado: no tiene permisos para este recurso"
}
```
