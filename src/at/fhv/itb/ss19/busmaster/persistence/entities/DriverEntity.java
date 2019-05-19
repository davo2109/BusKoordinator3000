package at.fhv.itb.ss19.busmaster.persistence.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "driver", schema = "public", catalog = "busdb")
public class DriverEntity {
    private int driverId;
    private String address;
    private Date birthday;
    private String email;
    private Integer employmentType;
    private String firstname;
    private String lastname;
    private String jobDescription;
    private String telephonenumber;

    @Id
    @Column(name = "driver_id")
    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "birthday")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Basic
    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Basic
    @Column(name = "employment_type")
    public Integer getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(Integer employmentType) {
        this.employmentType = employmentType;
    }

    @Basic
    @Column(name = "firstname")
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Basic
    @Column(name = "lastname")
    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    @Basic
    @Column(name = "job_description")
    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    @Basic
    @Column(name = "telephonenumber")
    public String getTelephonenumber() {
        return telephonenumber;
    }

    public void setTelephonenumber(String telephonenumber) {
        this.telephonenumber = telephonenumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DriverEntity that = (DriverEntity) o;
        return driverId == that.driverId &&
                Objects.equals(address, that.address) &&
                Objects.equals(birthday, that.birthday) &&
                Objects.equals(email, that.email) &&
                Objects.equals(employmentType, that.employmentType) &&
                Objects.equals(firstname, that.firstname) &&
                Objects.equals(lastname, that.lastname) &&
                Objects.equals(jobDescription, that.jobDescription) &&
                Objects.equals(telephonenumber, that.telephonenumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driverId, address, birthday, email, employmentType, firstname, lastname, jobDescription, telephonenumber);
    }
}
