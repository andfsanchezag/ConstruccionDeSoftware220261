# 📋 PROCESO DE EVALUACIÓN - CONSTRUCCIÓN DE SOFTWARE II

---

## 1. ARCHIVOS DE CONTEXTO UTILIZADOS

El proceso de evaluación se apoya en dos documentos ubicados en la raíz de la carpeta de repositorios:

| Archivo | Propósito |
|---|---|
| `contexto.md` | Define el dominio del negocio: entidades esperadas, tipos de cliente, usuario vs cliente, enums, relaciones y reglas de negocio del sistema bancario. Sirve como referencia de qué **debe existir** en un modelo correcto. |
| `rubrica.md` | Define los criterios de evaluación, su peso porcentual, la escala 1–5 por criterio, las penalizaciones y los puntos bonus. |

---

## 2. ESTRUCTURA DE CARPETAS

Cada subcarpeta en el directorio raíz corresponde a un repositorio Git de un estudiante o grupo, clonado localmente. La evaluación se realiza sobre los archivos fuente Java presentes en la rama activa del repositorio.

```
./
├── contexto.md               ← dominio del problema  
├── rubrica.md                ← criterios y pesos de evaluación  
├── PROCESO_EVALUACION.md     ← este archivo  
├── RESUMEN_EVALUACIONES.md   ← tabla resumen de todas las notas  
├── <Repositorio-Estudiante-1>/
│   └── EVALUACION.md         ← evaluación detallada generada
├── <Repositorio-Estudiante-2>/
│   └── EVALUACION.md
└── ...
```

---

## 3. PROCESO DE SELECCIÓN DE RAMA

### Paso 1 — Verificar rama `develop`

```bash
git -C <ruta-repo> branch -a
git -C <ruta-repo> checkout develop
```

- Si existe la rama `develop` y contiene código fuente Java → **evaluar sobre `develop`**.
- Si `develop` no existe o está vacía → continuar al paso 2.

### Paso 2 — Fallback a rama `main` (o `master`)

```bash
git -C <ruta-repo> checkout main
# o si no existe main:
git -C <ruta-repo> checkout master
```

- Si `main`/`master` contiene código Java → **evaluar sobre esta rama**.
- Si ninguna rama contiene código → **calificación mínima (1.0)** por entrega vacía.

### Criterio de decisión

```
¿Tiene develop código Java?
    ├── SÍ → evaluar develop
    └── NO → ¿Tiene main/master código Java?
                  ├── SÍ → evaluar main/master (nota: si la nota < 3.0 en develop, re-evaluar en main)
                  └── NO → Nota 1.0 (repositorio vacío)
```

> **Regla adicional:** Si un repositorio obtuvo una calificación inferior a **3.0** en la rama `develop`, se realiza una segunda evaluación sobre la rama `main` para determinar si existe trabajo adicional no integrado. La nota final refleja la rama con mejor contenido evaluable.

---

## 4. CRITERIOS DE EVALUACIÓN

Los criterios se toman directamente de `rubrica.md`. Cada uno se califica en escala **1 a 5**:

| # | Criterio | Peso |
|---|---|---|
| 1 | Modelado de dominio | 25% |
| 2 | Relaciones entre entidades | 15% |
| 3 | Uso de Enums ⚠️ crítico | 15% |
| 4 | Manejo de estados | 5% |
| 5 | Tipos de datos | 5% |
| 6 | Separación Usuario vs Cliente | 10% |
| 7 | Bitácora | 5% |
| 8 | Reglas básicas de negocio | 5% |
| 9 | Estructura del proyecto | 10% |
| 10 | Repositorio (nombre, README, commits, ramas, tag) | 10% |

### Fórmula de cálculo

$$\text{Nota base} = \sum_{i=1}^{10} \left( \frac{\text{puntaje}_i}{5} \times \text{peso}_i \right) \times 5$$

### Penalizaciones (aplicadas sobre la nota base)

| Penalización | Descuento |
|---|---|
| Código no en inglés | -20% |
| Variables mal nombradas | -10% |
| Clases mal nombradas | -10% |
| No aplica principios SOLID | -10% |
| Mala tabulación / formato | -20% |
| Alta complejidad ciclomática (>3 anidaciones) | -20% |

> La nota final **no puede ser menor a 0.0**.

### Puntos bonus

| Bonus | Puntos |
|---|---|
| Herencia correcta (Cliente → PersonaNatural / Empresa) | +2 |
| Código limpio | +2 |
| Nombres claros y consistentes | +1 |

---

## 5. REVISIÓN DEL CÓDIGO FUENTE

Al inspeccionar cada repositorio se revisan los archivos en:

```
src/main/java/
```

Se verifican:

1. **Entidades esperadas** según `contexto.md`:
   - `Cliente` (con subtipo `PersonaNatural` y `Empresa`)
   - `Usuario` (separado de `Cliente`)
   - `CuentaBancaria`
   - `Prestamo`
   - `Transferencia`
   - `ProductoBancario`
   - `Bitacora`

2. **Enums esperados**:
   - `TipoCuenta`, `EstadoCuenta`
   - `EstadoPrestamo`, `EstadoTransferencia`
   - `RolSistema`, `EstadoUsuario`
   - `Moneda`

3. **Relaciones** entre entidades (atributos de referencia entre clases).

4. **Tipos de datos** utilizados (`BigDecimal` para montos, `LocalDate`/`LocalDateTime` para fechas).

5. **Bitácora**: estructura flexible (Map, JSON campos, campo `detalles`).

6. **Estructura de paquetes**: separación en `domain`, `model`, `enums`, etc.

7. **Repositorio**: nombre de repo, contenido de README, mensajes de commit, uso de ramas y tag de entrega.

---

## 6. GENERACIÓN DEL ARCHIVO `EVALUACION.md`

Por cada repositorio evaluado se genera un archivo `EVALUACION.md` en la raíz del repositorio con la siguiente estructura:

```markdown
# EVALUACIÓN - <Nombre del Repositorio>

## Información General
- Estudiante(s): ...
- Rama evaluada: develop | main | master
- Fecha de evaluación: YYYY-MM-DD

## Tabla de Calificación
| # | Criterio | Peso | Puntaje (1-5) | Nota ponderada |
|---|---|---|---|---|
| 1 | Modelado de dominio | 25% | X | X.XX |
...
| **TOTAL** | | **100%** | | **X.XX** |

## Penalizaciones
- [descripción]: -X%

## Bonus
- [descripción]: +X

## Nota Final: X.X / 5.0

## Análisis por Criterio
### 1. Modelado de dominio
[Análisis detallado...]
...

## Fortalezas
- ...

## Oportunidades de mejora
- ...
```

---

## 7. COMMIT Y PUSH DEL ARCHIVO DE EVALUACIÓN

Una vez generado el `EVALUACION.md`, se registra en el historial del repositorio:

```bash
# Verificar que solo EVALUACION.md está pendiente
git -C <ruta-repo> status --short

# Agregar solo el archivo de evaluación (nunca git add .)
git -C <ruta-repo> add EVALUACION.md

# Commit con mensaje estándar
git -C <ruta-repo> commit -m "evaluacion modelos dominio"

# Push a la rama activa
git -C <ruta-repo> push
```

> ⚠️ **Precaución:** siempre usar `git add EVALUACION.md` explícitamente, nunca `git add .`, para evitar incluir en el commit archivos eliminados o modificados del estudiante.

Si el push falla con error **403 (Forbidden)**, significa que el docente no tiene permisos de escritura en el repositorio del estudiante. En ese caso el commit queda registrado localmente y se notifica al estudiante para que otorgue acceso de colaborador.

---

## 8. RESUMEN CONSOLIDADO

Al finalizar la evaluación de todos los repositorios se genera `RESUMEN_EVALUACIONES.md` en la carpeta raíz con:

- Tabla de notas finales ordenada por calificación
- Tabla detallada de puntaje por criterio
- Distribución de notas
- Patrones de error comunes
- Observaciones generales del grupo

---

## 9. FLUJO COMPLETO (DIAGRAMA)

```
Para cada repositorio de estudiante:
│
├── 1. git checkout develop
│       ├── Tiene código Java → EVALUAR
│       └── Sin código → git checkout main/master
│                               ├── Tiene código Java → EVALUAR
│                               └── Sin código → NOTA 1.0
│
├── 2. Leer todos los .java en src/main/java/
│
├── 3. Comparar contra contexto.md (entidades y enums esperados)
│
├── 4. Aplicar rubrica.md (10 criterios, escala 1-5, pesos)
│
├── 5. Aplicar penalizaciones y bonus
│
├── 6. Generar EVALUACION.md en la raíz del repo
│
├── 7. git add EVALUACION.md && git commit -m "evaluacion modelos dominio"
│
└── 8. git push (ignorar si falla 403)
│
└── 9. Actualizar RESUMEN_EVALUACIONES.md con la nota final
```
