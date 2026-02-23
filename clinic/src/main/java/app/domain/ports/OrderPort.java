package app.domain.ports;
import app.domain.models.Order;

public interface OrderPort {
    public void save(Order order);
}
