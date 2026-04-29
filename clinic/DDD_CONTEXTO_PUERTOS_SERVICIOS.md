# Contexto DDD para Reutilizacion: Puertos y Servicios de Dominio

## 1. Objetivo del documento
Este documento resume las decisiones de diseno del dominio para la gestion de clinica, tomando como base:
- El enunciado funcional en `Ejemplo clinica.pdf`.
- La implementacion del dominio en `src/main/java/app/domain`.

Su proposito es servir como guia reutilizable para arrancar otro proyecto con enfoque DDD (Hexagonal/Clean), manteniendo reglas de negocio y separacion de responsabilidades.

## 2. Contexto funcional (Ubiquitous Language)
Del documento funcional se identifican conceptos y reglas centrales:
- Roles operativos: Recursos Humanos, Administrativo, Enfermeria, Medico, Soporte.
- Entidades de negocio: Usuario, Paciente, Contacto de emergencia, Poliza, Orden, Historia clinica, Visita clinica, Factura.
- Reglas clave:
  - Solo RRHH crea/elimina usuarios.
  - Enfermeria registra visitas; Medico registra historia clinica.
  - Ordenes con reglas de consistencia por tipo de item.
  - Facturacion con copago, cobertura por aseguradora y tope anual.

## 3. Decisiones arquitectonicas en el dominio
### 3.1 Dominio orientado a casos de uso
El proyecto modela la logica de negocio mediante servicios de dominio pequeños y especificos (`Create*`, `Update*`, `Delete*`, `Find*`).

Esto facilita:
- Trazabilidad de reglas por caso de uso.
- Bajo acoplamiento entre operaciones.
- Reutilizacion selectiva de servicios en otros contextos (API, jobs, mensajeria).

### 3.2 Puertos como contratos del dominio
Los puertos (`app.domain.ports`) son interfaces de salida para persistencia/consulta, sin dependencia de JPA, SQL o framework.

Principio aplicado:
- El dominio define que necesita (`save`, `findBy...`, `existsBy...`).
- Infraestructura decide como resolverlo (adaptadores).

### 3.3 Frontera clara de responsabilidades
- Dominio: reglas, validaciones, consistencia de negocio.
- Adaptadores: detalles tecnicos (ORM, consultas, transacciones, mapeo).
- Aplicacion/use cases: orquestacion entre controladores y dominio.

## 4. Consideraciones para crear puertos reutilizables
## 4.1 Disenar por agregado, no por tecnologia
En este dominio, los puertos se agrupan por capacidad del negocio:
- `PatientPort`, `UserPort`, `OrderPort`, `InvoicePort`, `PolicyPort`, `PolicyHistoryPort`, `ClinicalRecordPort`, `ClinicalVisitPort`, `ContactEmergencyPort`.

Recomendacion reutilizable:
- Crear 1 puerto por agregado o subdominio coherente.
- Evitar puertos genericos tipo `GenericRepository<T>` en el dominio.

## 4.2 Expresar intencion de negocio en la firma
Las firmas muestran intencion funcional y reglas de identidad:
- `existsByDocument`, `findByPatient`, `existsByUsernameAndDocumentNot`, etc.

Recomendacion reutilizable:
- Preferir metodos semanticos del dominio sobre consultas tecnicas.
- Mantener nombres alineados con el lenguaje ubicuo.

## 4.3 Separar comandos y consultas
En el diseno actual se distingue entre:
- Comandos: `save`, `update`, `deleteByDocument`.
- Consultas: `findById`, `findByDocument`, `findAll`, `findByPatient`.

Recomendacion reutilizable:
- Mantener esta separacion para facilitar evolucion a CQRS si se requiere.

## 4.4 Invariantes de unicidad via puerto
Las validaciones de duplicados (documento, username, email) se consultan por puerto antes de persistir.

Recomendacion reutilizable:
- Modelar las reglas de unicidad como operaciones explicitas del puerto.
- Respaldarlas tambien en infraestructura con constraints fisicos.

## 5. Consideraciones para crear servicios de dominio reutilizables
## 5.1 Un servicio = una responsabilidad
Cada clase ejecuta un caso de uso concreto:
- Creacion (`CreatePatient`, `CreateOrder`, `CreateInvoice`, ...)
- Actualizacion (`UpdateUser`, `UpdatePolicy`, ...)
- Eliminacion (`DeleteUser`, `DeletePatient`)
- Consulta (`FindUser`, `FindOrder`, ...)

Beneficio:
- Facil testeo unitario y reemplazo por otro flujo sin impacto global.

## 5.2 Validar primero, mutar despues
Patron repetido en servicios:
1. Verificar existencia y precondiciones.
2. Validar rol o regla de negocio.
3. Completar datos de sistema (fecha, relaciones consolidadas).
4. Persistir por puerto.

Este orden evita estados intermedios inconsistentes.

## 5.3 Reglas de autorizacion funcional en dominio
Aunque la seguridad tecnica este en otra capa, el dominio valida reglas de rol:
- `CreateOrder` exige `Role.DOCTOR`.
- `CreateClinicalVisit` exige `Role.NURSE`.
- `CreateClinicalRecord` exige `Role.DOCTOR`.

Esto protege el core incluso si cambia el canal de entrada.

## 5.4 Excepciones orientadas a negocio
Se usan dos excepciones de dominio:
- `BusinessException`: violaciones de regla o precondicion.
- `NotFoundException`: ausencia de entidad consultada.

Recomendacion reutilizable:
- Mantener taxonomia de errores de dominio estable para mapearlos en API/mensajeria.

## 5.5 Reglas complejas encapsuladas
`CreateInvoice` concentra reglas de copago:
- Copago fijo de 50.000.
- Tope anual de 1.000.000.
- Cobertura por aseguradora segun estado de poliza.
- Uso de `PolicyHistory` para acumulado anual.

Leccion reutilizable:
- Las reglas monetarias y de cobertura deben vivir en un servicio de dominio dedicado, no en controlador ni repositorio.

## 6. Alineacion entre enunciado y dominio implementado
Cobertura claramente implementada:
- Validaciones de existencia e identidad principal por documento.
- Restricciones de rol en operaciones clinicas.
- Regla de mezcla de tipos en orden diagnostica vs medicamento/procedimiento.
- Regla de facturacion con copago y tope anual.

Aspectos a reforzar al reutilizar:
- Varias restricciones de formato del PDF (longitudes, regex, edad maxima) no estan formalizadas en entidades/VO.
- Regla de unicidad global de numero de orden y de item por orden debe asegurarse tambien en persistencia.
- Separacion SQL/NoSQL indicada en el documento funcional debe quedar explicita en adaptadores e infraestructura.

## 7. Plantilla reutilizable para otro proyecto
## 7.1 Pasos sugeridos
1. Definir lenguaje ubicuo y tabla de reglas por subdominio.
2. Identificar agregados raiz y sus invariantes.
3. Crear puertos por agregado con metodos semanticos.
4. Implementar servicios de dominio por caso de uso (comando/consulta).
5. Agregar adaptadores (JPA, Mongo, API externa) sin contaminar el dominio.
6. Probar reglas con tests de dominio (sin framework).

## 7.2 Checklist de calidad
- [ ] Ningun servicio de dominio depende de framework de persistencia.
- [ ] Todo acceso a datos del dominio pasa por puertos.
- [ ] Reglas criticas estan en dominio (no en controlador).
- [ ] Existen validaciones de unicidad en dominio y en BD.
- [ ] Existen pruebas de reglas de negocio de alto impacto (ordenes, polizas, facturacion).

## 8. Mapa rapido de puertos y su responsabilidad
- `PatientPort`: ciclo de vida y consulta de pacientes.
- `UserPort`: gestion de usuarios y validaciones de identidad/autenticacion.
- `OrderPort`: persistencia y consulta de ordenes clinicas.
- `InvoicePort`: persistencia y consulta de facturas.
- `PolicyPort`: gestion de polizas.
- `PolicyHistoryPort`: acumulado anual de copago.
- `ClinicalRecordPort`: almacenamiento de historia clinica.
- `ClinicalVisitPort`: almacenamiento de visitas de enfermeria.
- `ContactEmergencyPort`: gestion de contactos de emergencia.

## 9. Conclusiones de reutilizacion
El dominio actual ya tiene una base DDD/hexagonal util para migrar a otro proyecto: contratos claros (puertos), casos de uso atomicos (servicios) y reglas sensibles en el core. Para una reutilizacion robusta, el siguiente salto es convertir validaciones de formato y restricciones operativas en Value Objects/politicas explicitas y reforzar invariantes en infraestructura.
