package by.kostopravov.polyclinic.dto;

import by.kostopravov.polyclinic.dto.enums.Gender;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "passport", schema = "polyclinic")
public class Passport implements Serializable {

    @Id
    @Column(name = "id_number")
    @NotEmpty(message = "ID Number is empty")
    private String idNumber;

    @Column(name = "first_name")
    @NotEmpty(message = "First Name is empty")
    private String firstName;

    @Column(name = "last_name")
    @NotEmpty(message = "Last Name is empty")
    private String lastName;

    @Column(name = "fathers_name")
    @NotEmpty(message = "Father's Name is empty")
    private String fathersName;

    @Column(name = "number")
    @NotEmpty
    private String number;

    @Column(name = "date_of_birth")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate dateOfBirth;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "date_of_issue")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private LocalDate dateOfIssue;

    @Column(name = "authority")
    @NotEmpty
    private String authority;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Passport() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(LocalDate dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Passport(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Passport{" +
                "idNumber='" + idNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fathersName='" + fathersName + '\'' +
                ", number='" + number + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                ", dateOfIssue=" + dateOfIssue +
                ", authority='" + authority + '\'' +
                '}';
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }
}
