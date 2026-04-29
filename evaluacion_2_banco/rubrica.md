# RUBRICA DE EVALUACION - EVALUACION 2 (BANCO)

## Alcance obligatorio de esta etapa
- Esta etapa evalua unicamente la capa de dominio.
- Se ignoran por completo las capas application, adapters, infrastructure, controllers y configuracion tecnica.
- Ningun puntaje se debe asignar por implementaciones fuera de domain.

## Escala
- 5: Cumplimiento completo
- 4: Cumplimiento alto con fallas menores
- 3: Cumplimiento parcial
- 2: Cumplimiento minimo
- 1: No cumple

---

## 1. Modelado de dominio (20%)
Evalua existencia y calidad de las entidades centrales del banco.
- 5: Todas las entidades del contexto estan bien modeladas.
- 3: Faltan entidades o hay confusiones.
- 1: Modelo insuficiente.

## 2. Modelado de puertos (20%)
Evalua diseno de contratos de salida del dominio.
- 5: Puertos por agregado, firmas semanticas, sin acoplamiento tecnologico.
- 3: Puertos incompletos o demasiado genericos.
- 1: No hay puertos o son repositorios tecnologicos en domain.

## 3. Modelado de servicios de dominio (20%)
Evalua casos de uso y reglas en servicios.
- 5: Servicios pequenos por caso de uso con reglas del enunciado.
- 3: Servicios presentes pero con reglas dispersas/incompletas.
- 1: Sin servicios de dominio o con logica en controllers.

## 4. Enums y estados (10%)
Evalua el uso de enums para catalogos y estados.
- 5: Estados y catalogos modelados como enum, transiciones claras.
- 3: Mezcla enum/String.
- 1: Todo con String.

## 5. Reglas de negocio criticas (10%)
Evalua reglas del enunciado de prestamos, transferencias y cuentas.
- 5: Reglas completas y consistentes.
- 3: Reglas parciales.
- 1: Reglas ausentes.

## 6. Bitacora y trazabilidad (5%)
Evalua registro de eventos de negocio relevantes.
- 5: Bitacora inmutable con datos de detalle variables.
- 3: Registro parcial.
- 1: No hay bitacora.

## 7. Estructura interna de dominio (10%)
Evalua organizacion dentro de domain (modelos, enums, puertos, servicios).
- 5: Dominio bien organizado y coherente.
- 3: Organizacion parcial del dominio.
- 1: Dominio desordenado o inconsistente.

## 8. Calidad tecnica base en domain (5%)
Evalua nomenclatura, legibilidad y consistencia solo en codigo de dominio.
- 5: Codigo claro y consistente.
- 3: Problemas menores de calidad.
- 1: Baja legibilidad.

---

## Formula de nota
Nota final en escala 0.0 a 5.0:

Nota = sum((puntaje_i / 5) * peso_i)

Donde los pesos suman 100.

---

## Penalizaciones (aplican despues del calculo)
- Logica de negocio critica fuera de domain: -20%
- Estados en String donde debian ser enum: -10%
- Acoplamiento del dominio a clases de infraestructura/framework: -25%
- Nomenclatura deficiente o inconsistente en domain: -5%
- Codigo en español : -20%

Nota minima final: 0.0

---

## Bonus (maximo +0.5)
- +0.2: Buen diseno de puertos reutilizables
- +0.2: Buen diseno de servicios con alta cohesion
- +0.1: Excelente trazabilidad en bitacora
