package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContactsTest {
    private Contacts contact;
   @BeforeEach
   public void initialize() {
      contact =  new Contacts("tim", 50220);
   }

    @Test
    void getName() {
        Contacts temp = new Contacts(contact.getName(),contact.getPhone());
        temp.setName("olivia");
        assertEquals("Tim", contact.getName());
        assertNotEquals("tim", contact.getName());
        assertEquals("Olivia",temp.getName());
        assertNotEquals("olivia",temp.getName());
    }

    @Test
    void getPhone() {
        Contacts temp = new Contacts(contact.getName(),contact.getPhone());;
        temp.setPhone(1234);
        assertEquals(50220, contact.getPhone());
        assertEquals(1234,temp.getPhone());
        assertNotEquals(578, contact.getPhone());
        assertNotEquals(50220,temp.getPhone());
    }

    @Test
    void testHashCode() {
       Contacts contacts = new Contacts("Tim",50220);
       Contacts compareContact = new Contacts(contact.getName(),contact.getPhone());;
       assertEquals(true,contacts.hashCode() == compareContact.hashCode());
        assertNotEquals(true,contacts.hashCode() != compareContact.hashCode());
    }

    @Test
    void testEquals() {
        Contacts contacts = new Contacts("Tim",50220);
        Contacts compareContact = new Contacts(contact.getName(),contact.getPhone());;
        assertEquals(true, contacts.equals(compareContact));
        assertEquals(false, !contacts.equals(compareContact));
    }
}