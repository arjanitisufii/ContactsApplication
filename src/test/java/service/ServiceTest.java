package service;

import database.Datasource;
import junit.framework.Assert;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {
    private Service service;
    private Datasource datasource;

    @BeforeEach
    public void initialize() {
        service = new Service();
        datasource = Datasource.getInstance();
        datasource.open();
    }

    @Test
    void getContacts() {
        assertNotNull(service.getContacts());
    }

    @Test
    void editContact() {
        assertEquals(false, service.editContact("","Tim", 5532525, "Unknown"));
        assertEquals(false, service.editContact("Samm","", 5532525, "Unknown"));

    }

    @AfterEach
    void close() {
        datasource.close();
    }
}