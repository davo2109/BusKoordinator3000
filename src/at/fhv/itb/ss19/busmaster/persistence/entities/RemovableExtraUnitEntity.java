package at.fhv.itb.ss19.busmaster.persistence.entities;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="removable_extra_unit")
public class RemovableExtraUnitEntity {
    @Id
    @GeneratedValue
    private int removableExtraUnitId;

    @ManyToOne
    @JoinColumn(name = "unit_id", referencedColumnName = "extra_id", nullable = false)
    public RemovableExtraEntity removableExtraType;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RemovableExtraUnitEntity that = (RemovableExtraUnitEntity) o;
        return removableExtraUnitId == that.removableExtraUnitId &&
                Objects.equals(removableExtraType, that.removableExtraType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(removableExtraUnitId, removableExtraType);
    }

    public int getRemovableExtraUnitId() {
        return removableExtraUnitId;
    }

    public void setRemovableExtraUnitId(int removableExtraUnitId) {
        this.removableExtraUnitId = removableExtraUnitId;
    }

    public RemovableExtraEntity getRemovableExtraType() {
        return removableExtraType;
    }

    public void setRemovableExtraType(RemovableExtraEntity removableExtraType) {
        this.removableExtraType = removableExtraType;
    }
}
