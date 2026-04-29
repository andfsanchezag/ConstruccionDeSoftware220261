# 🏦 CONTEXTO DE DOMINIO

## Sistema Bancario – Guía para Evaluación de Modelos (DDD)

---

## 🎯 Objetivo del Documento

Este documento resume el contexto del problema planteado en el enunciado del sistema bancario, con el fin de que una **IA evaluadora** o docente pueda:

* Comprender el dominio del negocio
* Identificar correctamente los modelos esperados
* Evaluar si el estudiante modeló adecuadamente entidades, enums y relaciones

⚠️ Este documento NO define implementación, sino **qué debe existir en el modelo de dominio**.

---

# 🧠 VISIÓN GENERAL DEL DOMINIO

El sistema representa el núcleo de gestión de un banco, permitiendo administrar:

* Clientes (personas y empresas)
* Productos bancarios
* Operaciones financieras
* Usuarios del sistema con roles específicos
* Auditoría mediante bitácora

El sistema debe respetar:

* Reglas de negocio estrictas
* Flujos de aprobación
* Restricciones por roles

---

# 🧩 ENTIDADES PRINCIPALES DEL DOMINIO

## 1. Cliente

Representa al titular de productos bancarios.

### Tipos:

* Cliente Persona Natural
* Cliente Empresa

### Características:

* Identificación única
* Información personal o empresarial
* Relación con cuentas y préstamos

---

## 2. Usuario

Representa el acceso al sistema.

### Características:

* Credenciales de acceso
* Rol dentro del sistema
* Asociación opcional a cliente

⚠️ IMPORTANTE:

* Usuario ≠ Cliente

---

## 3. Cuenta Bancaria

Representa un depósito de dinero.

### Atributos clave:

* Número de cuenta (único)
* Tipo de cuenta
* Saldo
* Moneda
* Estado
* Fecha de apertura

### Relación:

* Pertenece a un Cliente

---

## 4. Préstamo / Crédito

Representa una solicitud de financiamiento.

### Atributos clave:

* Monto solicitado y aprobado
* Tasa de interés
* Plazo
* Estado del préstamo
* Cuenta destino

### Flujo:

* En estudio → Aprobado / Rechazado → Desembolsado

---

## 5. Transferencia

Representa movimiento de dinero entre cuentas.

### Atributos clave:

* Cuenta origen
* Cuenta destino
* Monto
* Estado
* Usuario creador y aprobador

### Flujo:

* Pendiente → Ejecutada / Rechazada / Vencida

---

## 6. Producto Bancario (Catálogo)

Define los tipos de productos disponibles.

### Atributos:

* Código
* Nombre
* Categoría
* Requiere aprobación (booleano)

---

## 7. Bitácora de Operaciones

Registro histórico de eventos del sistema.

### Características:

* Inmutable
* Flexible (estructura tipo JSON / Map)
* No se usa para lógica transaccional

### Contiene:

* Tipo de operación
* Usuario
* Producto afectado
* Datos variables

---

# 🔗 RELACIONES CLAVE

* Cliente → Cuenta Bancaria (1:N)
* Cliente → Préstamo (1:N)
* Cuenta → Transferencias (1:N)
* Usuario → Cliente (opcional)
* Transferencia → Usuario (creador/aprobador)

---

# 🔒 ENUMS ESPERADOS (CRÍTICO)

Los siguientes conceptos deben modelarse como ENUMS (NO String):

* RolSistema
* EstadoUsuario
* TipoCuenta
* EstadoCuenta
* TipoPrestamo
* EstadoPrestamo
* EstadoTransferencia
* Moneda
* CategoriaProducto

---

# 🔄 ESTADOS DEL DOMINIO

## Préstamo

* En estudio
* Aprobado
* Rechazado
* Desembolsado

## Transferencia

* Pendiente / En espera de aprobación
* Ejecutada
* Rechazada
* Vencida

## Cuenta

* Activa
* Bloqueada
* Cancelada

---

# 📏 REGLAS DE NEGOCIO CLAVE

## Generales

* Identificación de cliente única
* Número de cuenta único

## Cuentas

* No operar si está bloqueada o cancelada

## Préstamos

* Solo analista puede aprobar/rechazar
* Solo se desembolsa si está aprobado
* Debe existir cuenta destino válida

## Transferencias

* Monto > 0
* Validar saldo suficiente
* Transferencias grandes requieren aprobación
* Pueden vencer si no son aprobadas

---

# 🧍 ROLES DEL SISTEMA

* Cliente Persona Natural
* Cliente Empresa
* Empleado de Ventanilla
* Empleado Comercial
* Empleado de Empresa
* Supervisor de Empresa
* Analista Interno

### Importancia:

Los roles definen:

* Permisos
* Acceso a información
* Capacidad de aprobación

---

# 🧱 EXPECTATIVAS DEL MODELO

La IA evaluadora debe verificar que:

## ✔ Exista:

* Modelos de entidades claras
* Uso de enums en catálogos
* Relaciones entre entidades
* Separación Usuario vs Cliente

## ❌ No debe existir:

* Uso de String para estados
* Modelos anémicos sin sentido de dominio
* Mezcla de conceptos (ej: Usuario = Cliente)

---

# ⚠️ ALCANCE DE LA EVALUACIÓN

✔ Se permite:

* Uso de setters
* Modelos simples

❌ No se exige:

* Lógica compleja de dominio
* Patrones avanzados (DDD completo)

---

# 🤖 USO PARA IA EVALUADORA

Este documento sirve como referencia para:

* Validar consistencia del modelo
* Detectar errores conceptuales
* Evaluar cumplimiento del dominio

### Prompt sugerido:

"Evalúa el modelo de dominio de este proyecto verificando si cumple con las entidades, enums, relaciones y reglas descritas en el contexto del sistema bancario."

---
