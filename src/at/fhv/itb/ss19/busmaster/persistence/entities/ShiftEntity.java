package at.fhv.itb.ss19.busmaster.persistence.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "shift", schema = "public", catalog = "busdb")
public class ShiftEntity {
    private int shiftId;

    @Id
    @Column(name = "shift_id")
    public int getShiftId() {
        return shiftId;
    }

    public void setShiftId(int shiftId) {
        this.shiftId = shiftId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShiftEntity that = (ShiftEntity) o;
        return shiftId == that.shiftId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(shiftId);
    }
}
