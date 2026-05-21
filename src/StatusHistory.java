import java.time.LocalDateTime;

public class StatusHistory {

    private LocalDateTime changedAt;
    private OrderStatus status;

    public StatusHistory(OrderStatus status) {
        this.changedAt = LocalDateTime.now();
        this.status = status;
    }

    public LocalDateTime getChangedAt() {
        return changedAt;
    }

    public OrderStatus getStatus() {
        return status;
    }




}