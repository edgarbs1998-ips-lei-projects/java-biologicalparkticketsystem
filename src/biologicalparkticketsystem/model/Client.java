/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package biologicalparkticketsystem.model;

/**
 *
 * @author golds
 */
public class Client {
    
    private String name;
    private String nif;
    private Address address;
    
    public Client() { }
    
    public Client(String name, String nif) {
        this(name, nif, null);
    }
    
    public Client(String name, String nif, Address address) {
        this.name = name;
        this.nif = nif;
        this.address = address;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getNif() {
        return this.nif;
    }
    
    public void setNif(String nif) {
        this.nif = nif;
    }
    
    public Address getAddress() {
        return this.address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }
    
    public class Address {

        private String address;
        private String postalCode;
        private String location;
        private String country;

        public Address(String address, String postalCode, String location, String country) {
            this.address = address;
            this.postalCode = postalCode;
            this.location = location;
            this.country = country;
        }

        public String getAddress() {
            return this.address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPostalCode() {
            return this.postalCode;
        }

        public void setPostalCode(String postalCode) {
            this.postalCode = postalCode;
        }

        public String getLocation() {
            return this.location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getCountry() {
            return this.country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

    }
    
}
