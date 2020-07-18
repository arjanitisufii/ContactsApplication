package service;

import database.Datasource;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ServiceUtilityTest {
    private ServiceUtility serviceUtility;
    private Datasource datasource;

    @BeforeEach
    public void initialize() {
        serviceUtility = ServiceUtility.getInstance();
        datasource = Datasource.getInstance();
        datasource.open();
    }

    @Test
    void phoneNumbersRegex() {
        assertEquals("USA",serviceUtility.phoneNumbersRegex("15548577894"));
        assertEquals("Spain",serviceUtility.phoneNumbersRegex("34555444555"));
        assertEquals("France",serviceUtility.phoneNumbersRegex("335566556655"));
        assertEquals("Germany",serviceUtility.phoneNumbersRegex("494433346665"));
        assertEquals("Kosovo",serviceUtility.phoneNumbersRegex("38344878986"));
        assertEquals("Albania",serviceUtility.phoneNumbersRegex("3556664444333"));
        assertEquals("Australia",serviceUtility.phoneNumbersRegex("61666445522"));
        assertEquals("Turkey",serviceUtility.phoneNumbersRegex("9024544646646"));
        assertEquals("United Kingdom",serviceUtility.phoneNumbersRegex("445545852245"));
        assertEquals("Italy",serviceUtility.phoneNumbersRegex("396664444664"));
        assertNull(serviceUtility.phoneNumbersRegex("test"));
        assertNull(serviceUtility.phoneNumbersRegex("552442"));
    }

    @Test
    void isPhoneNumber() {
        assertEquals(true, serviceUtility.isPhoneNumber("554"));
        assertEquals(true, serviceUtility.isPhoneNumber("66234444234"));
        assertEquals(false, serviceUtility.isPhoneNumber("nope"));
        assertEquals(false, serviceUtility.isPhoneNumber(""));
        assertEquals(false, serviceUtility.isPhoneNumber(null));
        assertEquals(false, serviceUtility.isPhoneNumber("54a4"));
    }

    @Test
    void checkIfPhoneNumberExits() {
        Service service = new Service();
        service.insertContact("Test 1", 543, "Unknown");
        assertEquals(false,ServiceUtility.getInstance().checkIfPhoneNumberExits(543));
        assertEquals(true,ServiceUtility.getInstance().checkIfPhoneNumberExits(5432));
    }

    @Test
    void checkIfNameExits() {
        Service service = new Service();
        service.insertContact("Test 12", 889, "Unknown");
        assertEquals(false, ServiceUtility.getInstance().checkIfNameExits("Test 12"));
        assertEquals(true, ServiceUtility.getInstance().checkIfNameExits("Test 13"));
    }


    @Test
    void checkPhoneNumberLength() {
        assertEquals(true, serviceUtility.checkPhoneNumberLength("57845"));
        assertEquals(true, serviceUtility.checkPhoneNumberLength("789456416"));
        assertEquals(false, serviceUtility.checkPhoneNumberLength("73473473222474323424"));
    }

    @Test
    void testParameters() {
        assertFalse(ServiceUtility.getInstance().testParameters("test"));
        assertTrue(ServiceUtility.getInstance().testParameters(""));
        assertTrue(ServiceUtility.getInstance().testParameters(null));
        assertEquals(4,ServiceUtility.getInstance().testParameters(-4));
        assertEquals(6,ServiceUtility.getInstance().testParameters(6));
    }

    @Test
    void testNegativePhoneNumbers() {
        assertNull(ServiceUtility.getInstance().testNegativePhoneNumbers(""));
        assertNull(ServiceUtility.getInstance().testNegativePhoneNumbers(null));
        assertEquals("15", ServiceUtility.getInstance().testNegativePhoneNumbers("15"));
        assertEquals("15", ServiceUtility.getInstance().testNegativePhoneNumbers("-15"));
    }

    @AfterEach
    void close() {
        datasource.close();
    }
}