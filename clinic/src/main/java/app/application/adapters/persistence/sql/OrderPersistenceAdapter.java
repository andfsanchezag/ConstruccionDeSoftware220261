package app.application.adapters.persistence.sql;

import app.domain.models.enums.ItemType;
import app.domain.models.identity.User;
import app.domain.models.inventory.DiagnosticSupport;
import app.domain.models.inventory.InventoryItem;
import app.domain.models.inventory.Medicine;
import app.domain.models.inventory.Procedure;
import app.domain.models.inventory.Specialty;
import app.domain.models.orders.Order;
import app.domain.models.orders.OrderItem;
import app.domain.models.patient.Patient;
import app.domain.ports.out.OrderPort;
import app.application.adapters.persistence.sql.entities.DiagnosticSupportEntity;
import app.application.adapters.persistence.sql.entities.InventoryItemEntity;
import app.application.adapters.persistence.sql.entities.MedicineEntity;
import app.application.adapters.persistence.sql.entities.OrderEntity;
import app.application.adapters.persistence.sql.entities.OrderItemEntity;
import app.application.adapters.persistence.sql.entities.PatientEntity;
import app.application.adapters.persistence.sql.entities.ProcedureEntity;
import app.application.adapters.persistence.sql.entities.UserEntity;
import app.application.adapters.persistence.sql.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderPersistenceAdapter implements OrderPort {

    private final OrderRepository orderRepository;

    public OrderPersistenceAdapter(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void save(Order order) {
        orderRepository.save(toEntity(order));
    }

    @Override
    public Order findById(long id) {
        return orderRepository.findById(id).map(this::toModel).orElse(null);
    }

    @Override
    public List<Order> findByPatient(Patient patient) {
        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(patient.getId());
        return orderRepository.findByPatient(patientEntity).stream()
                .map(this::toModel).collect(Collectors.toList());
    }

    private OrderEntity toEntity(Order order) {
        OrderEntity e = new OrderEntity();
        e.setDate(order.getDate());
        if (order.getPatient() != null) {
            PatientEntity patientEntity = new PatientEntity();
            patientEntity.setId(order.getPatient().getId());
            e.setPatient(patientEntity);
        }
        if (order.getDoctor() != null) {
            UserEntity userEntity = new UserEntity();
            userEntity.setId(order.getDoctor().getId());
            e.setDoctor(userEntity);
        }
        if (order.getOrderItems() != null) {
            List<OrderItemEntity> items = order.getOrderItems().stream().map(item -> {
                OrderItemEntity oi = new OrderItemEntity();
                oi.setItemType(item.getItemType() != null ? item.getItemType().toString() : null);
                if (item.getInventoryItem() != null) {
                    oi.setInventoryItem(toInventoryItemStub(item.getInventoryItem()));
                }
                return oi;
            }).collect(Collectors.toList());
            e.setOrderItems(items);
        }
        return e;
    }

    private InventoryItemEntity toInventoryItemStub(InventoryItem item) {
        InventoryItemEntity stub;
        if (item instanceof Medicine) {
            stub = new MedicineEntity();
        } else if (item instanceof Procedure) {
            stub = new ProcedureEntity();
        } else {
            stub = new DiagnosticSupportEntity();
        }
        stub.setId(item.getId());
        return stub;
    }

    private Order toModel(OrderEntity e) {
        if (e == null) return null;
        Order order = new Order();
        order.setId(e.getId());
        order.setDate(e.getDate());
        if (e.getPatient() != null) {
            Patient p = new Patient();
            p.setId(e.getPatient().getId());
            p.setName(e.getPatient().getName());
            p.setDocument(e.getPatient().getDocument());
            order.setPatient(p);
        }
        if (e.getDoctor() != null) {
            User doctor = new User();
            doctor.setId(e.getDoctor().getId());
            doctor.setName(e.getDoctor().getName());
            doctor.setDocument(e.getDoctor().getDocument());
            order.setDoctor(doctor);
        }
        if (e.getOrderItems() != null) {
            List<OrderItem> items = e.getOrderItems().stream().map(oi -> {
                OrderItem orderItem = new OrderItem();
                orderItem.setId(oi.getId());
                orderItem.setItemType(oi.getItemType() != null ? ItemType.valueOf(oi.getItemType()) : null);
                if (oi.getInventoryItem() != null) {
                    orderItem.setInventoryItem(toInventoryItemModel(oi.getInventoryItem()));
                }
                return orderItem;
            }).collect(Collectors.toList());
            order.setOrderItems(items);
        }
        return order;
    }

    private InventoryItem toInventoryItemModel(InventoryItemEntity e) {
        InventoryItem item;
        if (e instanceof MedicineEntity) {
            item = new Medicine();
        } else if (e instanceof ProcedureEntity) {
            Procedure p = new Procedure();
            p.setRequiresSpecialist(e.getRequiresSpecialist() != null && e.getRequiresSpecialist());
            if (e.getSpecialty() != null) {
                Specialty s = new Specialty();
                s.setId(e.getSpecialty().getId());
                s.setName(e.getSpecialty().getName());
                p.setSpecialty(s);
            }
            item = p;
        } else {
            DiagnosticSupport ds = new DiagnosticSupport();
            ds.setRequiresSpecialist(e.getRequiresSpecialist() != null && e.getRequiresSpecialist());
            if (e.getSpecialty() != null) {
                Specialty s = new Specialty();
                s.setId(e.getSpecialty().getId());
                s.setName(e.getSpecialty().getName());
                ds.setSpecialty(s);
            }
            item = ds;
        }
        item.setId(e.getId());
        item.setName(e.getName());
        item.setPrice(e.getPrice());
        return item;
    }
}
