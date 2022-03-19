package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {
        private CustomerService() {}
        static Map<String, Customer> mapOfCustomer = new HashMap<String, Customer>();
        public static void addCustomer (String email, String firstname, String lastname){
            //create a map abject to store Customer class by using email as key
            Customer customer = new Customer(firstname, lastname, email);
            mapOfCustomer.put(customer.getEmail(), customer);
        }

        public static Customer getCustomer (String customerEmail){
            return mapOfCustomer.get(customerEmail);
        }

        public static Collection<Customer> getAllCustomers() {
            return mapOfCustomer.values();
        }
    }

