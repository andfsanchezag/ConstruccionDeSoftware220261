package app.domain.models;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.sql.Date;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class Order {

        private long id;
        private Patient patient;
        private User doctor;
        private Date date;
        private List<OrderItem> orderItems;
    
}
