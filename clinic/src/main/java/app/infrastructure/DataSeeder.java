package app.infrastructure;

import app.application.adapters.persistence.sql.entities.DiagnosticSupportEntity;
import app.application.adapters.persistence.sql.entities.MedicineEntity;
import app.application.adapters.persistence.sql.entities.ProcedureEntity;
import app.application.adapters.persistence.sql.repositories.InventoryItemRepository;
import app.application.adapters.persistence.sql.repositories.UserRepository;
import app.domain.Exceptions.BusinessException;
import app.domain.models.billing.Invoice;
import app.domain.models.billing.InvoiceItem;
import app.domain.models.clinic.ClinicalRecord;
import app.domain.models.clinic.ClinicalVisit;
import app.domain.models.enums.Gender;
import app.domain.models.enums.ItemType;
import app.domain.models.enums.Role;
import app.domain.models.identity.EmergencyContact;
import app.domain.models.identity.User;
import app.domain.models.inventory.DiagnosticSupport;
import app.domain.models.inventory.InventoryItem;
import app.domain.models.inventory.Medicine;
import app.domain.models.inventory.Procedure;
import app.domain.models.orders.Order;
import app.domain.models.orders.OrderItem;
import app.domain.models.patient.Patient;
import app.domain.services.CreateClinicalRecord;
import app.domain.services.CreateClinicalVisit;
import app.domain.services.CreateEmergencyContact;
import app.domain.services.CreateInvoice;
import app.domain.services.CreateOrder;
import app.domain.services.CreateUser;
import app.domain.services.FindOrder;
import app.domain.services.FindPatient;
import app.domain.services.CreatePatient;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Component
public class DataSeeder implements ApplicationRunner {

    private final UserRepository userRepository;
    private final InventoryItemRepository inventoryItemRepository;
    private final CreateUser createUser;
    private final CreatePatient createPatient;
    private final CreateEmergencyContact createEmergencyContact;
    private final CreateOrder createOrder;
    private final CreateClinicalVisit createClinicalVisit;
    private final CreateClinicalRecord createClinicalRecord;
    private final CreateInvoice createInvoice;
    private final FindPatient findPatient;
    private final FindOrder findOrder;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository,
                      InventoryItemRepository inventoryItemRepository,
                      CreateUser createUser,
                      createPatient createPatient,
                      CreateEmergencyContact createEmergencyContact,
                      CreateOrder createOrder,
                      CreateClinicalVisit createClinicalVisit,
                      CreateClinicalRecord createClinicalRecord,
                      CreateInvoice createInvoice,
                      FindPatient findPatient,
                      FindOrder findOrder,
                      PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.inventoryItemRepository = inventoryItemRepository;
        this.createUser = createUser;
        this.createPatient = createPatient;
        this.createEmergencyContact = createEmergencyContact;
        this.createOrder = createOrder;
        this.createClinicalVisit = createClinicalVisit;
        this.createClinicalRecord = createClinicalRecord;
        this.createInvoice = createInvoice;
        this.findPatient = findPatient;
        this.findOrder = findOrder;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.count() > 0) {
            return;
        }
        List<User> users = seedUsers();
        List<InventoryItem> items = seedInventoryItems();
        seedPatients(users, items);
    }

    // -------------------------------------------------------------------------
    // Users
    // -------------------------------------------------------------------------

    private List<User> seedUsers() throws BusinessException {
        String encoded = passwordEncoder.encode("admin");
        Date birthDate = Date.valueOf(LocalDate.of(1985, 1, 1));

        User hr = buildUser("1000000001", "Admin RRHH", "humanresources", encoded,
                Role.HUMANRESOURCES, "hr@clinic.com", birthDate);
        User doctor = buildUser("1000000002", "Admin Doctor", "doctor", encoded,
                Role.DOCTOR, "doctor@clinic.com", birthDate);
        User nurse = buildUser("1000000003", "Admin Enfermera", "nurse", encoded,
                Role.NURSE, "nurse@clinic.com", birthDate);
        User admin = buildUser("1000000004", "Admin Administrativo", "administrative", encoded,
                Role.ADMINISTRATIVE, "administrative@clinic.com", birthDate);

        createUser.createUser(hr);
        createUser.createUser(doctor);
        createUser.createUser(nurse);
        createUser.createUser(admin);

        return List.of(hr, doctor, nurse, admin);
    }

    private User buildUser(String document, String name, String username, String password,
                           Role role, String email, Date birthDate) {
        User u = new User();
        u.setDocument(document);
        u.setName(name);
        u.setUsername(username);
        u.setPassword(password);
        u.setRole(role);
        u.setEmail(email);
        u.setPhone("3001234567");
        u.setAddress("Calle 1 # 1-1");
        u.setBirthDate(birthDate);
        return u;
    }

    // -------------------------------------------------------------------------
    // Inventory items (no domain service exists — saved directly)
    // -------------------------------------------------------------------------

    private List<InventoryItem> seedInventoryItems() {
        MedicineEntity m1 = new MedicineEntity();
        m1.setName("Ibuprofeno 400mg");
        m1.setPrice(8000);

        MedicineEntity m2 = new MedicineEntity();
        m2.setName("Amoxicilina 500mg");
        m2.setPrice(12000);

        ProcedureEntity p1 = new ProcedureEntity();
        p1.setName("Radiografía de Tórax");
        p1.setPrice(80000);
        p1.setRequiresSpecialist(false);

        ProcedureEntity p2 = new ProcedureEntity();
        p2.setName("Electrocardiograma");
        p2.setPrice(60000);
        p2.setRequiresSpecialist(false);

        DiagnosticSupportEntity d1 = new DiagnosticSupportEntity();
        d1.setName("Hemograma Completo");
        d1.setPrice(35000);
        d1.setRequiresSpecialist(false);

        inventoryItemRepository.save(m1);
        inventoryItemRepository.save(m2);
        inventoryItemRepository.save(p1);
        inventoryItemRepository.save(p2);
        inventoryItemRepository.save(d1);

        Medicine med1 = new Medicine(); med1.setId(m1.getId()); med1.setName(m1.getName()); med1.setPrice(m1.getPrice());
        Medicine med2 = new Medicine(); med2.setId(m2.getId()); med2.setName(m2.getName()); med2.setPrice(m2.getPrice());
        Procedure proc1 = new Procedure(); proc1.setId(p1.getId()); proc1.setName(p1.getName()); proc1.setPrice(p1.getPrice());
        Procedure proc2 = new Procedure(); proc2.setId(p2.getId()); proc2.setName(p2.getName()); proc2.setPrice(p2.getPrice());
        DiagnosticSupport diag1 = new DiagnosticSupport(); diag1.setId(d1.getId()); diag1.setName(d1.getName()); diag1.setPrice(d1.getPrice());

        return List.of(med1, med2, proc1, proc2, diag1);
    }

    // -------------------------------------------------------------------------
    // Patients + all related records
    // -------------------------------------------------------------------------

    private void seedPatients(List<User> users, List<InventoryItem> items) throws BusinessException {
        User doctor = users.get(1);
        User nurse = users.get(2);

        String[][] patientData = {
            {"1100000001", "Carlos Gómez",     "3101111001", "cgomez@mail.com",    "Carrera 10 # 20-30", "1990-05-15", "MALE"},
            {"1100000002", "María Rodríguez",  "3101111002", "mrodriguez@mail.com","Calle 45 # 12-10",   "1985-08-22", "FEMALE"},
            {"1100000003", "Luis Martínez",    "3101111003", "lmartinez@mail.com", "Avenida 30 # 5-60",  "1978-11-03", "MALE"},
            {"1100000004", "Ana Torres",       "3101111004", "atorres@mail.com",   "Calle 80 # 40-20",   "1995-02-28", "FEMALE"},
            {"1100000005", "Jorge Herrera",    "3101111005", "jherrera@mail.com",  "Carrera 50 # 10-15", "2000-07-10", "MALE"},
        };

        for (int i = 0; i < patientData.length; i++) {
            String[] d = patientData[i];
            Patient patient = buildPatient(d[0], d[1], d[2], d[3], d[4],
                    Date.valueOf(d[5]), Gender.valueOf(d[6]), i);
            createPatient.createPatient(patient);

            Patient saved = findPatient.findByDocument(d[0]);

            Order order = seedOrder(saved, doctor, items, i);
            Order savedOrder = findOrder.findById(order.getId()) != null
                    ? findOrder.findById(order.getId())
                    : buildOrderRef(saved, doctor, order);

            seedClinicalVisit(saved, nurse, savedOrder);
            seedClinicalRecord(saved, doctor, savedOrder);
            seedInvoice(saved, doctor, savedOrder, items, i);
        }
    }

    private Patient buildPatient(String document, String name, String phone, String email,
                                  String address, Date birthDate, Gender gender, int idx) {
        EmergencyContact ec = new EmergencyContact();
        ec.setDocument("9900000" + (idx + 1));
        ec.setName("Contacto de " + name);
        ec.setPhone("3209990" + (idx + 1));
        ec.setEmail("ec" + idx + "@mail.com");
        ec.setAddress(address);
        ec.setBirthDate(Date.valueOf(LocalDate.of(1960, 1 + idx, 10)));
        ec.setRelationship("Familiar");

        Patient p = new Patient();
        p.setDocument(document);
        p.setName(name);
        p.setPhone(phone);
        p.setEmail(email);
        p.setAddress(address);
        p.setBirthDate(birthDate);
        p.setGender(gender);
        p.setEmergencyContact(ec);
        return p;
    }

    private Order seedOrder(Patient patient, User doctor, List<InventoryItem> items, int idx)
            throws BusinessException {
        // Alternate between medicine/procedure orders and diagnostic support orders
        Order order = new Order();
        order.setPatient(patient);
        order.setDoctor(doctor);

        OrderItem item1 = new OrderItem();
        OrderItem item2 = new OrderItem();

        if (idx % 2 == 0) {
            // Medicine + Procedure order
            item1.setInventoryItem(items.get(0)); // Medicine
            item1.setItemType(ItemType.MEDICINE);
            item2.setInventoryItem(items.get(2)); // Procedure
            item2.setItemType(ItemType.PROCEDURE);
        } else {
            // Diagnostic support order
            item1.setInventoryItem(items.get(4)); // DiagnosticSupport
            item1.setItemType(ItemType.MEDICALSUPPORT);
            item2.setInventoryItem(items.get(1)); // Medicine
            item2.setItemType(ItemType.MEDICINE);
        }

        order.setOrderItems(List.of(item1, item2));
        createOrder.createOrder(order);

        // Retrieve saved order to get its DB-assigned id
        List<app.domain.models.orders.Order> orders = findOrder.findByPatient(patient.getDocument());
        return orders.get(orders.size() - 1);
    }

    private Order buildOrderRef(Patient patient, User doctor, Order fallback) {
        Order ref = new Order();
        ref.setId(fallback.getId());
        ref.setPatient(patient);
        ref.setDoctor(doctor);
        return ref;
    }

    private void seedClinicalVisit(Patient patient, User nurse, Order order) throws BusinessException {
        ClinicalVisit visit = new ClinicalVisit();
        visit.setPatient(patient);
        visit.setNurse(nurse);
        visit.setOrder(order);
        visit.setDate(new Date(System.currentTimeMillis()));
        visit.setBloodPressure("120/80");
        visit.setTemperature(36.5);
        visit.setPulse(72);
        visit.setOxygenLevel(98.0);
        visit.setObservations("Paciente en buen estado general.");
        createClinicalVisit.createClinicalVisit(visit);
    }

    private void seedClinicalRecord(Patient patient, User doctor, Order order) throws BusinessException {
        ClinicalRecord record = new ClinicalRecord();
        record.setPatient(patient);
        record.setDoctor(doctor);
        record.setOrder(order);
        record.setDate(new Date(System.currentTimeMillis()));
        record.setReason("Consulta de control");
        record.setSymptoms("Dolor leve, malestar general");
        record.setDiagnosis("Estado general estable, se indica tratamiento ambulatorio.");
        createClinicalRecord.createClinicalRecord(record);
    }

    private void seedInvoice(Patient patient, User doctor, Order order,
                              List<InventoryItem> items, int idx) throws BusinessException {
        InvoiceItem ii1 = new InvoiceItem();
        ii1.setOrder(order);
        ii1.setItemType(idx % 2 == 0 ? ItemType.MEDICINE : ItemType.MEDICALSUPPORT);
        ii1.setItemName(idx % 2 == 0 ? items.get(0).getName() : items.get(4).getName());
        ii1.setQuantity(1);
        ii1.setUnitPrice(idx % 2 == 0 ? items.get(0).getPrice() : items.get(4).getPrice());
        ii1.setTotalPrice(ii1.getUnitPrice() * ii1.getQuantity());

        Invoice invoice = new Invoice();
        invoice.setPatient(patient);
        invoice.setDoctor(doctor);
        invoice.setItems(List.of(ii1));
        createInvoice.createInvoice(invoice);
    }
}
