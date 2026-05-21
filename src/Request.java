import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Request {

    private LocalDateTime createdAt;

    private List<RequestItem> items = new ArrayList<>();

    private List<StatusHistory> history = new ArrayList<>();

    private OrderStatus status;

    public Request() {
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.PENDING_PAYMENT;

        history.add(new StatusHistory(status));
    }

    public void addItem(RequestItem item) {
        items.add(item);
    }

    public OrderStatus getStatus() {
        return status;
    }

    public boolean updateStatus(OrderStatus newStatus) {


        if (this.status == OrderStatus.CANCELLED) {
            System.out.println("Cannot change a CANCELLED order!");
            return false;
        }

        if (this.status == OrderStatus.DELIVERED) {
            System.out.println("Cannot change a DELIVERED order!");
            return false;
        }

        if (this.status == newStatus) {
            System.out.println("Order already has this status!");
            return false;
        }

        if (newStatus == OrderStatus.CANCELLED) {
            this.status = OrderStatus.CANCELLED;
            history.add(new StatusHistory(newStatus));
            return true;
        }

        if (this.status == OrderStatus.PENDING_PAYMENT && newStatus == OrderStatus.PREPARING) {
            this.status = newStatus;
            history.add(new StatusHistory(newStatus));
            return true;
        }

        if (this.status == OrderStatus.PREPARING && newStatus == OrderStatus.SHIPPED) {
            this.status = newStatus;
            history.add(new StatusHistory(newStatus));
            return true;
        }

        if (this.status == OrderStatus.SHIPPED && newStatus == OrderStatus.DELIVERED) {
            this.status = newStatus;
            history.add(new StatusHistory(newStatus));
            return true;
        }

        System.out.println("Invalid status transition!");
        return false;
    }

    public double calculateTotal() {

        double total = 0;

        for (RequestItem item : items) {
            total += item.calculateTotal();
        }

        return total;
    }

    public List<RequestItem> getItems() {
        return items;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<StatusHistory> getHistory() {
        return history;
    }


}