package entity.film;


import entity.film.converters.RatingConverter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Entity
@Table(name = "film")
@Getter
@Setter
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "film_id")
    private Short id;
    private String title;
    @Column(columnDefinition = "text")
    private String description;
    @Column(columnDefinition = "year")
    private Year releaseYear;
    @ManyToOne
    @JoinColumn(name = "language_id")
    private Language language;
    @ManyToOne
    @JoinColumn(name = "original_language_id")
    private Language originalLanguage;
    private Byte rentalDuration;
    private BigDecimal rentalRate;
    private Short length;
    private BigDecimal replacementCost;

    @Column(columnDefinition = "enum('G', 'PG', 'PG-13', 'R', 'NC-17')")
    @Convert(converter = RatingConverter.class)
    private Rating rating;

    @Column(columnDefinition = "set('Trailers', 'Commentaries', 'Deleted Scenes', 'Behind the Scenes')")
    private String specialFeatures;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime lastUpdate;


    @ManyToMany
    @JoinTable(name = "film_actor", joinColumns = @JoinColumn(name = "film_id", referencedColumnName = "film_id"), inverseJoinColumns = @JoinColumn(name = "actor_id", referencedColumnName = "actor_id"))
    private List<Actor> actors;
    @ManyToMany
    @JoinTable(name = "film_category", joinColumns = @JoinColumn(name = "film_id", referencedColumnName = "film_id"), inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "category_id"))
    private List<Category> categories;

    public void setSpecialFeatures(Set<Feature> features) {
        if (isNull(features)) {
            specialFeatures = null;
        } else {
            specialFeatures = features.stream().map(Feature::getValue).collect(Collectors.joining(","));
        }
    }

    public Set<Feature> getSpecialFeatures() {
        if (isNull(specialFeatures) || specialFeatures.isEmpty()) {
            return null;
        }
        Set<Feature> result = new HashSet<>();
        String[] split = specialFeatures.split(",");
        for (String s : split) {
            if (!isNull(s)) {
                result.add(Feature.getFutureByValue(s));
            }
        }
        return result;
    }
}
