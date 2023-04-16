package entity.store;

import entity.adress.Address;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Byte id;
    private String firstName;
    private String lastName;
    @OneToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Lob
    @Column(columnDefinition = "blob")
//    @Type(type = "blob")
    private byte[] picture;

    private String email;

    @OneToOne
    @JoinColumn(name = "store_id")
    private Store store;

    private Byte active;

    private String username;
    private String password;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastUpdate;
}
