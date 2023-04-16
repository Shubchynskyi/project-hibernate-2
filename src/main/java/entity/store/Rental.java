package entity.store;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "rental")
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime rentalDate;

//    private Inventory inventory;
//    private Customer customer;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime returnDate;

//    private Staff staff;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastUpdate;
}
