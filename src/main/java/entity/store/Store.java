package entity.store;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Store {
    @Id
    private Long id;

}
