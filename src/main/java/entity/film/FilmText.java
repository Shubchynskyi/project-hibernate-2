package entity.film;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "film_text")
@Data
public class FilmText {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Short id;

    private String title;
    private String description;

}
