package org.converter.controller.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class SignupForm {

    @NotEmpty(message = "We require your email. Not to spam you, but let you in.")
    @Email(message = "Sorry, we detected invalid email format")
    private String email;

    @Length(min = 6, message = "We require minimum 6 symbols to feel you are in safe.")
    @NotEmpty(message = "It's not secure not to have a password.")
    private String password;

    private String passwordConfirmation;

    @NotEmpty(message = "Please let us know country you live in.")
    private String country;

    @NotEmpty(message = "Please let us know city you live in.")
    private String city;

    @NotEmpty(message = "Please let us know street you live on.")
    private String street;

    @NotEmpty(message = "We really would like to know your ZIP code")
    private String zipCode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
