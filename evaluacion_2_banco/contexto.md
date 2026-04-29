# CONTEXTO DE DOMINIO - EVALUACION 2

## Objetivo
Este documento define el contexto de evaluacion para el enunciado bancario y, en especial, como debieron modelarse los puertos y servicios de dominio en un enfoque DDD/arquitectura hexagonal.

Se usa para verificar diseno de dominio, no para calificar detalles de framework.

Alcance de esta etapa:
- Solo se evalua la capa de dominio.
- Se ignoran capas application, adapters, infrastructure y aspectos de despliegue.

---

## Vision del dominio bancario
El sistema debe cubrir:
- Gestion de clientes (persona natural y empresa).
- Gestion de cuentas bancarias.
- Gestion de prestamos/creditos.
- Gestion de transferencias (incluyendo aprobacion para alto monto empresarial).
- Catalogo de productos bancarios.
- Bitacora de operaciones para trazabilidad y auditoria (modelo documento/NoSQL).

Reglas centrales del enunciado:
- Identificacion de cliente unica.
- Numero de cuenta unico.
- No operar cuentas bloqueadas o canceladas.
- Prestamo: En estudio -> Aprobado/Rechazado -> Desembolsado.
- Transferencias con monto > 0 y saldo suficiente.
- Transferencias de alto monto: aprobacion por supervisor; vencimiento en 60 minutos.

---

## Entidades y enums esperados
Entidades principales:
- Cliente (PersonaNatural, Empresa)
- Usuario
- CuentaBancaria
- Prestamo
- Transferencia
- ProductoBancario
- BitacoraOperacion

Enums criticos (no String):
- RolSistema
- EstadoUsuario
- TipoCuenta
- EstadoCuenta
- TipoPrestamo
- EstadoPrestamo
- EstadoTransferencia
- Moneda
- CategoriaProducto

---

## Como se debieron crear los puertos (salida del dominio)

### Principios
- Un puerto por agregado/subdominio coherente.
- Firmas semanticas del negocio (no metodos tecnicos genericos).
- Sin dependencias de JPA/Spring en la capa domain.
- Separacion entre comandos (save/update/delete) y consultas (find/exist).

### Puertos recomendados
- `ClientePort`
  - `boolean existsByIdentification(String identification)`
  - `Cliente findByIdentification(String identification)`
  - `void save(Cliente cliente)`
  - `void update(Cliente cliente)`
- `UsuarioPort`
  - `Usuario findByUsername(String username)`
  - `boolean existsByUsername(String username)`
  - `void save(Usuario usuario)`
- `CuentaBancariaPort`
  - `boolean existsByAccountNumber(String accountNumber)`
  - `CuentaBancaria findByAccountNumber(String accountNumber)`
  - `List<CuentaBancaria> findByCliente(Cliente cliente)`
  - `void save(CuentaBancaria cuenta)`
  - `void update(CuentaBancaria cuenta)`
- `PrestamoPort`
  - `Prestamo findById(Long id)`
  - `List<Prestamo> findByCliente(Cliente cliente)`
  - `void save(Prestamo prestamo)`
  - `void update(Prestamo prestamo)`
- `TransferenciaPort`
  - `Transferencia findById(Long id)`
  - `List<Transferencia> findPendingApprovalOlderThanMinutes(int minutes)`
  - `void save(Transferencia transferencia)`
  - `void update(Transferencia transferencia)`
- `ProductoBancarioPort`
  - `ProductoBancario findByCode(String code)`
  - `List<ProductoBancario> findAll()`
- `BitacoraPort`
  - `void append(BitacoraOperacion event)`
  - `List<BitacoraOperacion> findByProductId(String productId)`

### Que se evalua en puertos
- Si expresan reglas del negocio en sus firmas.
- Si los puertos no filtran por tecnologia.
- Si soportan reglas del enunciado (aprobacion, vencimiento, auditoria).

---

## Como se debieron crear los servicios de dominio

### Principios
- Servicio pequeno por caso de uso (single responsibility).
- Orden correcto: validar -> aplicar regla -> persistir -> auditar.
- Reglas de autorizacion funcional en el dominio (por rol).
- Excepciones de negocio explicitas (`BusinessException`, `NotFoundException`).

### Servicios necesarios (obligatorios para evaluar)

Servicios de clientes:
- `CreateClienteService`
- `UpdateClienteService`
- `FindClienteService`

Servicios de cuentas:
- `OpenBankAccountService`
- `BlockBankAccountService`
- `CancelBankAccountService`
- `FindBankAccountService`

Servicios de prestamos:
- `CreateLoanRequestService`
- `ApproveLoanService`
- `RejectLoanService`
- `DisburseLoanService`
- `FindLoanService`

Servicios de transferencias:
- `CreateTransferService`
- `ApproveTransferService`
- `RejectTransferService`
- `ExecuteTransferService`
- `ExpirePendingTransfersService`
- `FindTransferService`

Servicios de catalogo y bitacora:
- `FindProductCatalogService`
- `RegisterAuditEventService` (o equivalente dentro de cada servicio transaccional)

### Reglas minimas a evaluar por servicio

`CreateClienteService`
- Debe validar unicidad de identificacion del cliente.
- Debe rechazar datos obligatorios incompletos.

`UpdateClienteService`
- Debe actualizar solo clientes existentes.
- Debe preservar unicidad de identificacion si cambia el dato.

`FindClienteService`
- Debe retornar cliente por identificacion.
- Debe lanzar `NotFoundException` cuando no exista.

`OpenBankAccountService`
- Debe validar numero de cuenta unico.
- Debe validar cliente existente y activo.
- Debe validar tipo de cuenta permitido por catalogo.

`BlockBankAccountService`
- Debe permitir cambio de estado a bloqueada para cuenta existente.
- Debe registrar evento de bitacora.

`CancelBankAccountService`
- Debe permitir cambio de estado a cancelada para cuenta existente.
- Debe impedir operaciones posteriores sobre esa cuenta.

`FindBankAccountService`
- Debe consultar por numero de cuenta y/o por cliente.
- Debe manejar cuenta inexistente con `NotFoundException`.

`CreateLoanRequestService`
- Debe asociar prestamo a cliente valido y activo.
- Debe inicializar estado en `EN_ESTUDIO`.
- Debe validar monto solicitado mayor que cero.

`ApproveLoanService`
- Debe permitir aprobacion solo por rol analista interno.
- Debe permitir transicion solo desde `EN_ESTUDIO` a `APROBADO`.
- Debe registrar evento de bitacora.

`RejectLoanService`
- Debe permitir rechazo solo por rol analista interno.
- Debe permitir transicion solo desde `EN_ESTUDIO` a `RECHAZADO`.
- Debe registrar evento de bitacora.

`DisburseLoanService`
- Debe permitir desembolso solo desde estado `APROBADO`.
- Debe validar cuenta destino definida, existente y activa.
- Debe validar monto aprobado mayor que cero.
- Debe aumentar saldo de cuenta destino por monto aprobado.
- Debe cambiar estado a `DESEMBOLSADO` y registrar bitacora.

`FindLoanService`
- Debe consultar por id y/o por cliente.
- Debe manejar no encontrados con `NotFoundException`.

`CreateTransferService`
- Debe validar monto mayor que cero.
- Debe validar cuentas origen/destino existentes y operativas.
- Si supera umbral empresarial, debe crear en `PENDIENTE_APROBACION`.
- Si no supera umbral, debe pasar a ejecucion directa.

`ApproveTransferService`
- Debe permitir aprobacion solo por supervisor de empresa.
- Debe aplicar solo a transferencias en `PENDIENTE_APROBACION`.
- Debe delegar/activar ejecucion financiera al aprobar.

`RejectTransferService`
- Debe permitir rechazo solo por supervisor de empresa.
- Debe aplicar solo a transferencias en `PENDIENTE_APROBACION`.
- Debe cambiar estado a `RECHAZADA` y registrar bitacora.

`ExecuteTransferService`
- Debe validar saldo suficiente en cuenta origen.
- Debe debitar origen y acreditar destino de forma consistente.
- Debe cambiar estado final a `EJECUTADA`.
- Debe registrar evento de bitacora.

`ExpirePendingTransfersService`
- Debe encontrar transferencias pendientes con mas de 60 minutos.
- Debe cambiar estado a `VENCIDA`.
- No debe mover fondos.
- Debe registrar motivo de vencimiento en bitacora.

`FindTransferService`
- Debe consultar por id y por filtros de cliente/empresa.
- Debe respetar reglas de acceso funcional por rol en dominio.

`FindProductCatalogService`
- Debe consultar catalogo vigente para validar tipos de producto.
- Debe evitar codigos de producto inexistentes en operaciones.

`RegisterAuditEventService`
- Debe registrar eventos inmutables con fecha/hora, usuario, rol, tipo de operacion y datos detalle.
- Debe soportar estructura flexible (`Datos_Detalle` tipo documento/mapa).

---

## Criterios de aceptacion de arquitectura
Se considera alineado al contexto cuando:
- La capa domain contiene entidades, enums, puertos y servicios.
- Los servicios de dominio dependen de interfaces (puertos), no de adapters.
- Las reglas del enunciado estan en servicios de dominio y no solo en controller.
- Existe evidencia de trazabilidad con bitacora en operaciones relevantes.
