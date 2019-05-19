package at.fhv.itb.ss19.busmaster.persistence.entities;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;


@Entity
@Table(name= "customer", schema = "public", catalog = "busdb")
public class CustomerEntity {

    @Id
    @SequenceGenerator(name = "id_Sequence", sequenceName = "customer_customer_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "id_Sequence")
    @Column(name = "customer_id", unique = true, nullable = false)

    private int customerId;

    @Column(name = "firstname", nullable = false)
    private String firstname;
    @Column(name = "lastname", nullable = false)
    private String lastname;
    @Column(name = "address")
    private String address;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "zipcode")
    private String zipCode;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<CharterOfferEntity> charterOffers;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerEntity that = (CustomerEntity) o;
        return customerId == that.customerId &&
                firstname.equals(that.firstname) &&
                lastname.equals(that.lastname) &&
                Objects.equals(address, that.address) &&
                Objects.equals(city, that.city) &&
                Objects.equals(state, that.state) &&
                Objects.equals(zipCode, that.zipCode) &&
                Objects.equals(email, that.email) &&
                Objects.equals(phone, that.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, firstname, lastname, address, city, state, zipCode, email, phone);
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<CharterOfferEntity> getCharterOffers() {
        return charterOffers;
    }

    public void setCharterOffers(Set<CharterOfferEntity> charterOffers) {
        this.charterOffers = charterOffers;
    }
}




