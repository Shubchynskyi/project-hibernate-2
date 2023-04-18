package entity.film;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "film_text")
@Getter
@Setter
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
