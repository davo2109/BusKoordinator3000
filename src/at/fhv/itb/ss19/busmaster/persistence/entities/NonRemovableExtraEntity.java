package at.fhv.itb.ss19.busmaster.persistence.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("non_removable")
public class NonRemovableExtraEntity extends ExtraEntity{
}
