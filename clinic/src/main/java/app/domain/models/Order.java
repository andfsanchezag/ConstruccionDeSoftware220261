package app.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.sql.Date;

@Setter
@Getter
@NoArgsConstructor
public class Order {

        private long id;
        private Patient patient;
        private User doctor;
        private Date date;
        private OrderItem[] orderItems;
    
}
