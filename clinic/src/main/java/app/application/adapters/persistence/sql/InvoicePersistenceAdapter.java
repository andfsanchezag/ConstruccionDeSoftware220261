package app.application.adapters.persistence.sql;

import app.domain.models.billing.Invoice;
import app.domain.models.billing.InvoiceItem;
import app.domain.models.enums.ItemType;
import app.domain.models.identity.User;
import app.domain.models.orders.Order;
import app.domain.models.patient.Patient;
import app.domain.models.patient.Policy;
import app.domain.ports.InvoicePort;
import app.application.adapters.persistence.sql.entities.InvoiceEntity;
import app.application.adapters.persistence.sql.entities.InvoiceItemEntity;
import app.application.adapters.persistence.sql.entities.PatientEntity;
import app.application.adapters.persistence.sql.repositories.InvoiceRepository;
import app.application.adapters.persistence.sql.repositories.OrderRepository;
import app.application.adapters.persistence.sql.repositories.PatientRepository;
import app.application.adapters.persistence.sql.repositories.PolicyRepository;
import app.application.adapters.persistence.sql.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvoicePersistenceAdapter implements InvoicePort {

    private final InvoiceRepository invoiceRepository;
    private final PatientRepository patientRepository;
    private final UserRepository userRepository;
    private final PolicyRepository policyRepository;
    private final OrderRepository orderRepository;

    public InvoicePersistenceAdapter(InvoiceRepository invoiceRepository, PatientRepository patientRepository,
                                     UserRepository userRepository, PolicyRepository policyRepository,
                                     OrderRepository orderRepository) {
        this.invoiceRepository = invoiceRepository;
        this.patientRepository = patientRepository;
        this.userRepository = userRepository;
        this.policyRepository = policyRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public void save(Invoice invoice) {
        invoiceRepository.save(toEntity(invoice));
    }

    @Override
    public Invoice findById(long id) {
        return invoiceRepository.findById(id).map(this::toModel).orElse(null);
    }

    @Override
    public List<Invoice> findByPatient(Patient patient) {
        PatientEntity patientEntity = patientRepository.findByDocument(patient.getDocument());
        return invoiceRepository.findByPatient(patientEntity).stream()
                .map(this::toModel).collect(Collectors.toList());
    }

    private InvoiceEntity toEntity(Invoice invoice) {
        InvoiceEntity e = new InvoiceEntity();
        e.setIssueDate(invoice.getIssueDate());
        e.setTotalAmount(invoice.getTotalAmount());
        e.setCopayment(invoice.getCopayment());
        e.setInsuranceCoverage(invoice.getInsuranceCoverage());
        e.setPatientPayment(invoice.getPatientPayment());
        e.setPolicyApplied(invoice.isPolicyApplied());
        if (invoice.getPatient() != null) {
            e.setPatient(patientRepository.findByDocument(invoice.getPatient().getDocument()));
        }
        if (invoice.getDoctor() != null) {
            e.setDoctor(userRepository.findByDocument(invoice.getDoctor().getDocument()));
        }
        if (invoice.getPolicy() != null) {
            policyRepository.findById(invoice.getPolicy().getId()).ifPresent(e::setPolicy);
        }
        if (invoice.getItems() != null) {
            List<InvoiceItemEntity> items = invoice.getItems().stream().map(item -> {
                InvoiceItemEntity ii = new InvoiceItemEntity();
                ii.setItemType(item.getItemType() != null ? item.getItemType().toString() : null);
                ii.setItemName(item.getItemName());
                ii.setQuantity(item.getQuantity());
                ii.setUnitPrice(item.getUnitPrice());
                ii.setTotalPrice(item.getTotalPrice());
                if (item.getOrder() != null) {
                    orderRepository.findById(item.getOrder().getId()).ifPresent(ii::setOrder);
                }
                return ii;
            }).collect(Collectors.toList());
            e.setItems(items);
        }
        return e;
    }

    private Invoice toModel(InvoiceEntity e) {
        if (e == null) return null;
        Invoice invoice = new Invoice();
        invoice.setId(e.getId());
        invoice.setIssueDate(e.getIssueDate());
        invoice.setTotalAmount(e.getTotalAmount());
        invoice.setCopayment(e.getCopayment());
        invoice.setInsuranceCoverage(e.getInsuranceCoverage());
        invoice.setPatientPayment(e.getPatientPayment());
        invoice.setPolicyApplied(e.isPolicyApplied());
        if (e.getPatient() != null) {
            Patient p = new Patient();
            p.setId(e.getPatient().getId());
            p.setName(e.getPatient().getName());
            p.setDocument(e.getPatient().getDocument());
            invoice.setPatient(p);
        }
        if (e.getDoctor() != null) {
            User doctor = new User();
            doctor.setId(e.getDoctor().getId());
            doctor.setName(e.getDoctor().getName());
            doctor.setDocument(e.getDoctor().getDocument());
            invoice.setDoctor(doctor);
        }
        if (e.getPolicy() != null) {
            Policy policy = new Policy();
            policy.setId(e.getPolicy().getId());
            policy.setPolicyNumber(e.getPolicy().getPolicyNumber());
            invoice.setPolicy(policy);
        }
        if (e.getItems() != null) {
            List<InvoiceItem> items = e.getItems().stream().map(ii -> {
                InvoiceItem item = new InvoiceItem();
                item.setId(ii.getId());
                item.setItemType(ii.getItemType() != null ? ItemType.valueOf(ii.getItemType()) : null);
                item.setItemName(ii.getItemName());
                item.setQuantity(ii.getQuantity());
                item.setUnitPrice(ii.getUnitPrice());
                item.setTotalPrice(ii.getTotalPrice());
                if (ii.getOrder() != null) {
                    Order order = new Order();
                    order.setId(ii.getOrder().getId());
                    item.setOrder(order);
                }
                return item;
            }).collect(Collectors.toList());
            invoice.setItems(items);
        }
        return invoice;
    }
}
