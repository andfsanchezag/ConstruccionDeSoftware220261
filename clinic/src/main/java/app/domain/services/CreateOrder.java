package app.domain.services;

import app.domain.Exceptions.BusinessException;
import app.domain.models.ItemType;
import app.domain.models.Role;
import app.domain.models.Order;
import app.domain.models.User;
import app.domain.models.Patient;
import app.domain.ports.PatientPort;
import app.domain.ports.UserPort;
import app.domain.models.OrderItem;
import java.sql.Date;


import app.domain.ports.OrderPort;

public class CreateOrder {

    private PatientPort patientPort;
    private UserPort userPort;
    private OrderPort orderPort;

    public void createOrder(Order order) throws BusinessException{
        Patient patient = patientPort.findByDocument(order.getPatient());
        if(patient == null){
            throw new BusinessException("No existe el paciente");
        }
        User doctor = userPort.findByDocument(order.getDoctor());
        if(doctor == null){
            throw new BusinessException("No existe el doctor");
        }
        if(!doctor.getRole().equals(Role.DOCTOR)){
            throw new BusinessException("El usuario no es un doctor");
        }
        if(order.getOrderItems() == null || order.getOrderItems().isEmpty()){
            throw new BusinessException("La orden debe tener al menos un item");
        }
       if(supportMixtedTypes(order)){
            throw new BusinessException("La orden de tipo ayuda diagnostica no puede tener items de tipo medicamento o procedimiento");
        }
        order.setDate(new Date(System.currentTimeMillis()));
        order.setPatient(patient);
        order.setDoctor(doctor);
        orderPort.save(order);
    }

    private boolean supportMixtedTypes(Order order){
        boolean hasMedicineOrProcedule = false;
        boolean hasSupport = false;

        for (OrderItem item : order.getOrderItems()) {
            if((item.getItemType().equals(ItemType.MEDICINE) ||
             item.getItemType().equals(ItemType.PROCEDURE))){
                hasMedicineOrProcedule = true;
            }
            if(item.getItemType().equals(ItemType.MEDICALSUPPORT) ){
                hasSupport=  true;
            }
            if(hasMedicineOrProcedule && hasSupport){
                return true;
            }
        }  
        return false;
    }
    
}
