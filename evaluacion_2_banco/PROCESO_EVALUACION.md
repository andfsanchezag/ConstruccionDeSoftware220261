# PROCESO DE EVALUACION - EVALUACION 2

## Objetivo
Definir el flujo obligatorio de evaluacion de repositorios para el enunciado bancario, usando los archivos de esta carpeta y evaluando unicamente la rama con el commit mas reciente entre develop y main.

Alcance de etapa: en esta evaluacion solo se revisa la capa de dominio.

Archivos base:
- `contexto.md`
- `rubrica.md`
- `PROCESO_EVALUACION.md`

---

## 1. Preparacion por repositorio
Para cada repositorio de estudiante:
1. Verificar que el repo existe localmente y es un repositorio Git valido.
2. Actualizar referencias remotas.

Comandos sugeridos:

```bash
git -C <ruta-repo> fetch --all --prune
```

---

## 2. Pull obligatorio de develop y main
Se debe intentar actualizar ambas ramas antes de decidir cual evaluar.

```bash
git -C <ruta-repo> checkout develop
git -C <ruta-repo> pull --ff-only origin develop

git -C <ruta-repo> checkout main
git -C <ruta-repo> pull --ff-only origin main
```

Si una rama no existe, se registra la novedad y se continua con la rama disponible.

---

## 3. Seleccion de rama a evaluar (regla obligatoria)
Regla: evaluar solo la rama con el commit mas reciente entre develop y main.

### 3.1 Obtener timestamp de ultimo commit en cada rama

```bash
git -C <ruta-repo> log -1 --format=%ct develop
git -C <ruta-repo> log -1 --format=%ct main
```

- `%ct` devuelve epoch time del ultimo commit.
- Comparar ambos valores.

### 3.2 Decision
- Si `develop` tiene timestamp mayor: evaluar `develop`.
- Si `main` tiene timestamp mayor: evaluar `main`.
- Si hay empate: evaluar `develop` (criterio de desempate fijo).

Importante:
- No se evalua una segunda rama.
- No se mezcla evidencia entre ramas.
- El resultado final debe reflejar solo la rama seleccionada por recencia.

---

## 4. Revision tecnica sobre la rama seleccionada
1. Cambiar a la rama seleccionada.
2. Revisar unicamente codigo de dominio (ejemplo: `src/main/java/**/domain/**`).
3. Evaluar con base en `contexto.md` y `rubrica.md`.

Verificar especialmente:
- Modelado de entidades y enums del dominio bancario.
- Diseno de puertos por agregado y contratos semanticos.
- Diseno de servicios de dominio por caso de uso.
- Reglas de negocio criticas (prestamos, transferencias, cuentas).
- Trazabilidad en bitacora.

No evaluar en esta etapa:
- Controllers, filtros de seguridad, configuraciones de framework.
- Casos de uso de application si no afectan evidencia en domain.
- Adaptadores de persistencia, repositorios concretos e infraestructura.

---

## 5. Identificacion de estudiantes desde README.md (obligatorio)
Antes de cerrar la evaluacion de cada repositorio, se debe buscar en `README.md` el nombre de los estudiantes/integrantes del proyecto.

Reglas:
- Si `README.md` existe y contiene integrantes, registrarlos en el informe.
- Si `README.md` no existe o no incluye nombres, dejar trazabilidad explicita: `Integrantes no informados en README.md`.

Comando sugerido:

```bash
git -C <ruta-repo> show <rama-evaluada>:README.md
```

---

## 6. Generacion del informe
Generar `EVALUACION2.md` en la raiz del repo evaluado con:
- Informacion general (repo, rama evaluada, fecha).
- Nombres de estudiantes obtenidos desde `README.md`.
- Tabla de criterios con puntaje por rubrica.
- Penalizaciones/bonus.
- Nota final.
- Hallazgos concretos y recomendaciones.

Estructura minima recomendada:

```markdown
# EVALUACION - <Repositorio>

## Informacion general
- Estudiante(s): <nombre(s) en README.md | no informados>
- Rama evaluada: <develop|main>
- Commit evaluado: <hash>
- Fecha: YYYY-MM-DD

## Tabla de calificacion
...

## Hallazgos
...

## Recomendaciones
...

## Nota final
X.X / 5.0
```

---

## 7. Registro Git de la evaluacion
Confirmar cambios y versionar solo el archivo de evaluacion:

```bash
git -C <ruta-repo> status --short
git -C <ruta-repo> add EVALUACION2.md
git -C <ruta-repo> commit -m "evaluacion dominio banco"
git -C <ruta-repo> push
```

Nunca usar `git add .` para evitar incluir archivos del estudiante por error.

---

## 8. Resumen consolidado de evaluaciones y notas (obligatorio)
Al finalizar todos los repositorios, crear `RESUMEN_EVALUACIONES2.md` en la carpeta de evaluacion con:
- Lista de repositorios evaluados.
- Rama y commit evaluado por repositorio.
- Estudiante(s) detectados en `README.md`.
- Nota final por repositorio.
- Promedio general del grupo.
- Observaciones recurrentes (fortalezas y hallazgos comunes).

Estructura minima sugerida:

```markdown
# RESUMEN EVALUACIONES 2

| Repositorio | Estudiante(s) | Rama | Commit | Nota final |
|---|---|---|---|---|
| repo-a | ... | develop | abc1234 | 4.3 |

## Promedio general
...

## Hallazgos comunes
- ...
```

---

## 9. Resumen operativo
Flujo por repositorio:
1. Fetch.
2. Pull de develop y main.
3. Comparar ultimo commit de ambas ramas.
4. Evaluar solo la rama mas reciente.
5. Buscar estudiantes en `README.md`.
6. Generar `EVALUACION2.md`.
7. Commit/push de la evaluacion.

Flujo final de cierre:
1. Consolidar notas en `RESUMEN_EVALUACIONES2.md`.
2. Verificar que cada nota tenga rama, commit y estudiante(s).
