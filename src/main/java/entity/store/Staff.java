package entity.store;

import entity.address.Address;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "staff")
public class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    private Byte id;
    private String firstName;
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    @Lob
    @Column(columnDefinition = "BLOB")
//    @Type(type = "BLOB")
    private byte[] picture;

    private String email;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(columnDefinition = "BIT")
    @Convert(converter = org.hibernate.type.NumericBooleanConverter.class)
    private Boolean active;

    private String username;
    private String password;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastUpdate;
}
