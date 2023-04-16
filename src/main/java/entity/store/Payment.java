package entity.store;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Helper;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Byte id;

    private Customer customer;
    private Staff staff;
    private Rental rental;

    private BigDecimal amount;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime paymentDate;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastUpdate;
}
