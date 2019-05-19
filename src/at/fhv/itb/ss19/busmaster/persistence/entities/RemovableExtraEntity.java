package at.fhv.itb.ss19.busmaster.persistence.entities;

import javax.persistence.*;

@Entity
@DiscriminatorValue("removable")
public class RemovableExtraEntity extends ExtraEntity{

}
