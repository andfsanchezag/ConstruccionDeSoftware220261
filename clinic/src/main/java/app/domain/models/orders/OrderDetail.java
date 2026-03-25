package app.domain.models.orders;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Abstract base for the detailed line-items within a medical order.
 * Shares order reference, order item reference, and cost across all
 * concrete order detail types (medicine, procedure, diagnostic).
 */
@Setter
@Getter
@NoArgsConstructor
public abstract class OrderDetail {

    private Order order;

    private OrderItem orderItem;

    private double cost;
}
