# 🏦 RUBRICA DE EVALUACIÓN - PROYECTO SISTEMA BANCARIO

## 📌 Enfoque de Evaluación

* Modelado de dominio (NO CRUD)
* Uso correcto de ENUMS
* Buenas prácticas de código
* Gestión profesional de repositorio

---

## 🔢 Escala de Evaluación

| Puntaje | Nivel                       |
| ------- | --------------------------- |
| 5       | Cumple completamente        |
| 4       | Cumple con pequeños errores |
| 3       | Cumple parcialmente         |
| 2       | Cumple de forma mínima      |
| 1       | No cumple                   |

---

# 🧠 1. MODELADO DE DOMINIO (25%)

### Evalúa:

* Identificación de entidades:

  * Cliente (Persona / Empresa)
  * Usuario
  * CuentaBancaria
  * Prestamo
  * Transferencia
  * ProductoBancario
  * Bitacora

| Nivel | Descripción                                           |
| ----- | ----------------------------------------------------- |
| 5     | Todas las entidades correctamente definidas           |
| 4     | Pequeñas omisiones o confusiones                      |
| 3     | Algunas entidades faltantes o mezcladas               |
| 2     | Identificación mínima                                 |
| 1     | Modelo anémico (solo clases sin análisis del dominio) |

---

# 🔗 2. RELACIONES ENTRE ENTIDADES (15%)

| Nivel | Descripción                            |
| ----- | -------------------------------------- |
| 5     | Relaciones correctas y coherentes      |
| 4     | Relaciones correctas con leves errores |
| 3     | Relaciones incompletas                 |
| 2     | Relaciones mínimas                     |
| 1     | Relaciones incorrectas                 |

---

# 🔒 3. USO DE ENUMS (15%) ⚠️ CRÍTICO

### Regla:

* ❌ NO usar String para estados o catálogos

### Ejemplos esperados:

* RolSistema
* EstadoUsuario
* TipoCuenta
* EstadoCuenta
* EstadoPrestamo
* EstadoTransferencia
* Moneda

| Nivel | Descripción                    |
| ----- | ------------------------------ |
| 5     | Todos los catálogos como enums |
| 4     | Casi todos enums               |
| 3     | Mezcla enums + String          |
| 2     | Uso mínimo de enums            |
| 1     | Todo en String                 |

---

# 🔄 4. MANEJO DE ESTADOS (5%)

### Nota:

✔ Se permite uso de setters
✔ NO se exige lógica compleja

| Nivel | Descripción                      |
| ----- | -------------------------------- |
| 5     | Estados bien modelados con enums |
| 4     | Correcto con pequeños errores    |
| 3     | Parcial                          |
| 2     | Deficiente                       |
| 1     | Incorrecto                       |

---

# 🧱 5. TIPOS DE DATOS (5%)

| Nivel | Descripción                              |
| ----- | ---------------------------------------- |
| 5     | Tipos correctos (BigDecimal, Date, etc.) |
| 4     | Detalles menores                         |
| 3     | Algunos errores                          |
| 2     | Varios errores                           |
| 1     | Tipos incorrectos                        |

---

# 🧍 6. SEPARACIÓN USUARIO VS CLIENTE (10%)

| Nivel | Descripción       |
| ----- | ----------------- |
| 5     | Separación clara  |
| 4     | Leves confusiones |
| 3     | Parcial           |
| 2     | Muy confuso       |
| 1     | Mezclados         |

---

# 🗂️ 7. BITÁCORA (5%)

| Nivel | Descripción                                      |
| ----- | ------------------------------------------------ |
| 5     | Uso de estructura flexible (Map, JSON o similar) |
| 4     | Buena implementación con detalles                |
| 3     | Estructura rígida                                |
| 2     | Implementación pobre                             |
| 1     | No implementado                                  |

---

# 📏 8. REGLAS BÁSICAS DE NEGOCIO (5%)

| Nivel | Descripción                           |
| ----- | ------------------------------------- |
| 5     | Varias reglas correctamente aplicadas |
| 4     | Buen nivel de validación              |
| 3     | Algunas reglas                        |
| 2     | Pocas reglas                          |
| 1     | Ninguna                               |

---

# 🗂️ 9. ESTRUCTURA DEL PROYECTO (10%)

| Nivel | Descripción                                 |
| ----- | ------------------------------------------- |
| 5     | Paquetes organizados (domain, enums, model) |
| 4     | Buena organización                          |
| 3     | Parcial                                     |
| 2     | Desordenado                                 |
| 1     | Sin estructura                              |

---

# 📁 10. REPOSITORIO (10%)

## 10.1 Nombre del repositorio (2%)

* Formato correcto según reglas

## 10.2 README.md (3%)

Debe incluir:

* Materia
* Integrantes
* Tecnología
* Cómo ejecutar

## 10.3 Commits (2%)

* Uso de ADD / CHG

## 10.4 Ramas (2%)

* Uso de develop (y feature si aplica)

## 10.5 Tag de entrega (1%)

* Formato correcto

---

# 💻 11. PENALIZACIONES SOBRE NOTA FINAL

Se aplican DESPUÉS del cálculo total:

* Código no en inglés → -20%
* Variables mal nombradas → -10%
* Clases mal nombradas → -10%
* No aplica principios SOLID → -10%
* Mala tabulación → -20%
* Métodos con alta complejidad (>3 anidaciones) → -20%

⚠️ La nota final no puede ser menor a 0

---

# ⚠️ REGLAS IMPORTANTES

* ✔ Se permite uso de setters
* ❌ No se exige lógica compleja de dominio
* ❌ No usar Strings para estados
* ✔ Prioridad: MODELADO + ENUMS

---

# 🎯 BONUS (+5)

* +2: Uso correcto de herencia (Cliente → Persona / Empresa)
* +2: Código limpio
* +1: Nombres claros y consistentes

---


