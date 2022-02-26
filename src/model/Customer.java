package model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {
    private String firstname;
    private String lastname;
    private String email;
    private final String emailRegex = "^(.+)@(.+).com$";
    private final Pattern pattern = Pattern.compile(emailRegex);
    public Customer(String firstname,String lastname, String email) {
        if(!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Error, invalid email! Please enter in a format: name@domain.com");
        }
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        if(!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Error, invalid email! Please enter in a format: name@domain.com");
        }
        this.email = email;
    }

    @Override
    public String toString() {
        return "First Name: "+firstname+"  Last Name: "+lastname+"  Email: "+email;
    }

    //https://www.liaoxuefeng.com/wiki/1252599548343744/1265117217944672
    @Override
    public int hashCode() {
        return Objects.hash(firstname,lastname,email);
    }

    //https://www.liaoxuefeng.com/wiki/1252599548343744/1265117217944672
    @Override
    public boolean equals(Object o) {
        if (o instanceof Customer) {
            Customer customer = (Customer) o;
            return this.firstname.equals(customer.firstname) && this.lastname.equals(customer.lastname) && this.email.equals(customer.email);
        }
        return false;
    }
}
