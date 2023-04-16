package entity.film;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "film_text")
@Data
public class FilmText {
    @Id
    @Column(name = "film_id")
    private Short id;

    private String title;
    @Column(columnDefinition = "text")
//    @Type(type = "text")
    private String description;
    @OneToOne
    @JoinColumn(name = "film_id")
    private Film film;

}
