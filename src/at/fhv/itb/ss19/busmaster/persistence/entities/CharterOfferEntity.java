package at.fhv.itb.ss19.busmaster.persistence.entities;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name= "charter_offer", schema = "public", catalog = "busdb")
public class CharterOfferEntity {

    @Id
    @SequenceGenerator(name = "id_Sequence", sequenceName = "charter_offer_charter_offer_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @Column(name = "charter_offer_id", unique = true, nullable = false)

    private int charterOfferId;
    @Column(name = "create_date", nullable = false)
    private Date createDate;
    @Column(name = "description")
    private String description;
    @Column(name = "place_of_departure", nullable = false)
    private String departure;
    @Column(name = "place_of_arrival", nullable = false)
    private String arrival;
    @Column(name = "start_date", nullable = false)
    private Date startDate;
    @Column(name = "end_date", nullable = false)
    private Date endDate;
    @Column(name = "start_time", nullable = false)
    private Time startTime;
    @Column(name = "end_time", nullable = false)
    private Time endTime;
    @Column(name = "passenger_count", nullable = false)
    private int passengerCount;
    @Column(name = "net_price", nullable = false)
    private double netPrice;
    @Column(name = "discount", nullable = false)
    private double discount;
    @Column(name = "tax", nullable = false)
    private double tax;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", nullable = false)
    private CustomerEntity customer;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(	name = "charter_offer_extra",
            joinColumns = { @JoinColumn(name = "charter_offer_id") },
            inverseJoinColumns = {@JoinColumn(name = "extra_id") })
    private Set<ExtraEntity> extras = new HashSet<>();


    public int getCharterOfferId() {
        return charterOfferId;
    }

    public void setCharterOfferId(int charterOfferId) {
        this.charterOfferId = charterOfferId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getArrival() {
        return arrival;
    }

    public void setArrival(String arrival) {
        this.arrival = arrival;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public void setPassengerCount(int passengerCount) {
        this.passengerCount = passengerCount;
    }

    public double getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(double netPrice) {
        this.netPrice = netPrice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public CustomerEntity getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerEntity customer) {
        this.customer = customer;
    }

    public Set<ExtraEntity> getExtras() {
        return extras;
    }

    public void setExtras(Set<ExtraEntity> extras) {
        this.extras = extras;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CharterOfferEntity that = (CharterOfferEntity) o;
        return charterOfferId == that.charterOfferId &&
                passengerCount == that.passengerCount &&
                Double.compare(that.netPrice, netPrice) == 0 &&
                Double.compare(that.discount, discount) == 0 &&
                Double.compare(that.tax, tax) == 0 &&
                createDate.equals(that.createDate) &&
                Objects.equals(description, that.description) &&
                departure.equals(that.departure) &&
                arrival.equals(that.arrival) &&
                startDate.equals(that.startDate) &&
                endDate.equals(that.endDate) &&
                startTime.equals(that.startTime) &&
                endTime.equals(that.endTime) &&
                customer.equals(that.customer) &&
                Objects.equals(extras, that.extras);
    }

    @Override
    public int hashCode() {
        return Objects.hash(charterOfferId, createDate, description, departure, arrival, startDate, endDate, startTime, endTime, passengerCount, netPrice, discount, tax, customer, extras);
    }
}

