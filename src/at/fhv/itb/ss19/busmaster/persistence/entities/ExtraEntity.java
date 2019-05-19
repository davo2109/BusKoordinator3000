package at.fhv.itb.ss19.busmaster.persistence.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue(value = "extra")
@Table(name = "extra")
public class ExtraEntity {

    @Id
    @SequenceGenerator(name = "id_Sequence", sequenceName = "extra_extra_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @Column(name = "extra_id", unique = true, nullable = false)
    private int extraId;

    @Column(name = "description", nullable = false)
    private String description;


    @ManyToMany(mappedBy = "extras")
    private Set<CharterOfferEntity> charterOffers;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExtraEntity that = (ExtraEntity) o;
        return extraId == that.extraId &&
                description.equals(that.description) &&
                Objects.equals(charterOffers, that.charterOffers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(extraId, description, charterOffers);
    }

    public int getExtraId() {
        return extraId;
    }

    public void setExtraId(int extraId) {
        this.extraId = extraId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<CharterOfferEntity> getCharterOffers() {
        return charterOffers;
    }

    public void setCharterOffers(Set<CharterOfferEntity> charterOffers) {
        this.charterOffers = charterOffers;
    }
}



