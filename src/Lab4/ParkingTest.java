package Lab4;

import Lab3.Car;
import Lab3.Parking;
import Lab3.TimeInterval;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ParkingTest {

    Parking p;

    @BeforeAll
    static void deleteFiles()
    {
        new File("AllCustomersOfParking.dat").delete();
        new File("CarsOnParkingNow.dat").delete();
    }

    @BeforeEach
    void createParking()
    {
        p = new Parking(1f, 20f, 600f, 4);
        p.parkCar("Іваненко Іван Іванович", "BE1234AA", new Date(121, Calendar.JANUARY, 22, 15, 40), new Date(121, Calendar.MARCH, 1, 12, 0));
        p.parkCar("Петренко Петро Петрович", "BA4321AA", new Date(121, Calendar.FEBRUARY, 2, 11, 47));
        p.parkCar("Сидоренко Сидір Сидорович", "AB6789BD");
        p.leaveParking("BA4321AA", new Date(121, Calendar.FEBRUARY, 28, 11, 47));
    }

    @Test
    void setPricePerHour() {
        p.setPricePerHour(1.5f);
        assertEquals(1.5f, p.getPrices()[0]);
    }

    @Test
    void setPricePerDay() {
        p.setPricePerDay(15f);
        assertEquals(15f, p.getPrices()[1]);
    }

    @Test
    void setPricePerMonth() {
        p.setPricePerMonth(500f);
        assertEquals(500f, p.getPrices()[2]);
    }

    @Test
    void getPrices() {
        assertTrue(Arrays.equals(new float[]{1f, 20f, 600f}, p.getPrices()));
    }

    @Test
    void places() {
        assertEquals(4, p.placesCount());
    }

    @Test
    void parkCar() {
        p.parkCar("Василенко Василь Васильович", "AA1111AA");
        assertTrue(p.findCar("AA1111AA").getVisits().contains(new TimeInterval(new Date(), null)));
        assertTrue(p.getCarsOnParkingNow().contains(new Car("Василенко Василь Васильович", "AA1111AA")));
        assertTrue(p.getAllCars().contains(new Car("Василенко Василь Васильович", "AA1111AA")));

        p.parkCar("Степаненко Степан Степанович", "BB2222BB", new Date(121, 1, 1,1,1));
        assertTrue(p.getCarsOnParkingNow().contains(new Car("Степаненко Степан Степанович", "BB2222BB")));
        assertTrue(p.getAllCars().contains(new Car("Степаненко Степан Степанович", "BB2222BB")));
        assertTrue(p.findCar("BB2222BB").getVisits().contains(new TimeInterval(new Date(121, 1, 1,1,1), null)));

        //parkCar повертає true якщо для машини знайшлося місце
        assertTrue(p.parkCar("Максименко Максим Максимович", "CC3333CC", new Date(121, 1,1,1,1), new Date(221, 2,2,2,2)));
        assertTrue(p.getCarsOnParkingNow().contains(new Car("Максименко Максим Максимович", "CC3333CC")));
        assertTrue(p.getAllCars().contains(new Car("Максименко Максим Максимович", "CC3333CC")));
        assertTrue(p.findCar("CC3333CC").getVisits().contains(new TimeInterval(new Date(121, 1, 1,1,1), new Date(221, 2,2,2,2))));

        assertFalse(p.parkCar("Павленко Павло Павлович", "AB9876BA"));//parkCar повертає false якщо на парковці немає місця
    }

    @Test
    void leaveParking() {
        p.leaveParking("AB6789BD");
        assertEquals(new Date(), p.findCar("AB6789BD").getVisits().get(0).getEnd());
        assertFalse(p.getCarsOnParkingNow().contains(new Car ("Сидоренко Сидір Сидорович", "AB6789BD")));
        assertTrue(p.getAllCars().contains(new Car("Сидоренко Сидір Сидорович", "AB6789BD")));

        p.leaveParking("BA4321AA", new Date(121, Calendar.APRIL, 3, 3,3));
        assertEquals(new Date(121, Calendar.FEBRUARY, 28, 11, 47), p.findCar("BA4321AA").getVisits().get(0).getEnd());
        assertFalse(p.getCarsOnParkingNow().contains(new Car ("Петренко Петро Петрович", "BA4321AA")));
        assertTrue(p.getAllCars().contains(new Car("Петренко Петро Петрович", "BA4321AA")));

        p.leaveParking("BE1234AA");
        assertEquals(new Date(121, Calendar.MARCH, 1, 12, 0), p.findCar("BE1234AA").getVisits().get(0).getEnd());
        assertFalse(p.getCarsOnParkingNow().contains(new Car ("Петренко Петро Петрович", "BA4321AA")));
        assertTrue(p.getAllCars().contains(new Car("Петренко Петро Петрович", "BA4321AA")));
    }

    @Test
    void formReport() {
        String str = "Власник: Іваненко Іван Іванович, Номер: BE1234AA\n"+p.findCar("BE1234AA").getVisits().get(0).toString()+"\t1 місяць\t600.0₴\n" +
                "Власник: Петренко Петро Петрович, Номер: BA4321AA\n"+p.findCar("BA4321AA").getVisits().get(0).toString()+"\t26 днів\t520.0₴\n" +
                "Власник: Сидоренко Сидір Сидорович, Номер: AB6789BD\n"+p.findCar("AB6789BD").getVisits().get(0).toString()+"\t0 годин\t0.0₴\n" +
                "_________________________________\nВСЬОГО:\t\t\t1120.0₴\n";
        assertEquals(str, p.formReport());
        str = "Власник: Сидоренко Сидір Сидорович, Номер: AB6789BD\n"+p.findCar("AB6789BD").getVisits().get(0).toString()+"\t0 годин\t0.0₴\n"+
                "Власник: Петренко Петро Петрович, Номер: BA4321AA\n"+p.findCar("BA4321AA").getVisits().get(0).toString()+"\t26 днів\t520.0₴\n"+
                "Власник: Іваненко Іван Іванович, Номер: BE1234AA\n"+p.findCar("BE1234AA").getVisits().get(0).toString()+"\t1 місяць\t600.0₴\n"+
                "_________________________________\nВСЬОГО:\t\t\t1120.0₴\n";
        assertEquals(str, p.formReport((Car car1, Car car2) -> car1.getNumber().compareTo(car2.getNumber()), car -> true ));
        str = "Власник: Петренко Петро Петрович, Номер: BA4321AA\n"+p.findCar("BA4321AA").getVisits().get(0).toString()+"\t26 днів\t520.0₴\n"+
                "Власник: Іваненко Іван Іванович, Номер: BE1234AA\n"+p.findCar("BE1234AA").getVisits().get(0).toString()+"\t1 місяць\t600.0₴\n"+
                "_________________________________\nВСЬОГО:\t\t\t1120.0₴\n";
        assertEquals(str, p.formReport((Car car1, Car car2) -> car1.getNumber().compareTo(car2.getNumber()), car -> car.getVisits().stream().allMatch(time -> time.getDurationInHours()>0)));
    }

    @Test
    void formList() {
        String str = "Іваненко Іван Іванович BE1234AA\nПетренко Петро Петрович BA4321AA\nСидоренко Сидір Сидорович AB6789BD\n_________________________________\nКІЛЬКІСТЬ ЗАПИСІВ:\t\t3";
        assertEquals(str, p.formList());
        str = "Петренко Петро Петрович BA4321AA\nІваненко Іван Іванович BE1234AA\n_________________________________\nКІЛЬКІСТЬ ЗАПИСІВ:\t\t2";
        assertEquals(str, p.formList((car1, car2) -> car1.getNumber().compareTo(car2.getNumber()), car -> car.getVisits().stream().allMatch(time -> time.getDurationInHours()>0)));
    }

    @Test
    void carsNowAtParking() {
        String str = "Сидоренко Сидір Сидорович AB6789BD\n_________________________________\nКІЛЬКІСТЬ АВТОМОБІЛІВ:\t\t1/4";
        assertEquals(str, p.carsNowAtParking());
    }

    @Test
    void monthReport() {
        p.parkCar("Сергієнко Сергій Сергійович", "AB1357BC", new Date(new Date().getTime()-3000000000L), new Date(new Date().getTime()-1000000000L));
        p.parkCar("Сергієнко Сергій Сергійович", "AB1357BC", new Date());
        String str = "Власник: Сергієнко Сергій Сергійович, Номер: AB1357BC\n"+p.findCar("AB1357BC").getVisits().get(1).toString()+"\t0 годин\t0.0₴\n_________________________________\nВСЬОГО:\t\t\t0.0₴\n";
        assertEquals(str, p.monthReport("Сергієнко Сергій Сергійович"));
    }

    @Test
    void reportForOwner() {
        p.parkCar("Сергієнко Сергій Сергійович", "AB1357BC", new Date());
        String str = "Власник: Сергієнко Сергій Сергійович, Номер: AB1357BC\n"+p.findCar("AB1357BC").getVisits().get(0).toString()+"\t0 годин\t0.0₴\n_________________________________\nВСЬОГО:\t\t\t0.0₴\n";
        assertEquals(str, p.reportForOwner("Сергієнко Сергій Сергійович"));
    }

    @Test
    void reportForCar() {
        p.parkCar("Сергієнко Сергій Сергійович", "AB1357BC", new Date());
        String str = "Власник: Сергієнко Сергій Сергійович, Номер: AB1357BC\n"+p.findCar("AB1357BC").getVisits().get(0).toString()+"\t0 годин\t0.0₴\n_________________________________\nВСЬОГО:\t\t\t0.0₴\n";
        assertEquals(str, p.reportForCar("AB1357BC"));
    }

    @ParameterizedTest
    @MethodSource("getDataForCalculatePricesTest")
    void testCalculatePrice(Date begin, Date end, float expectedPrice) {
        assertEquals(expectedPrice, p.calculatePrice(begin, end));
    }

    static Stream<Arguments> getDataForCalculatePricesTest()
    {
        return Stream.of(
                Arguments.of(new Date(121, Calendar.JANUARY, 1, 12, 30), new Date(121, Calendar.JANUARY, 1, 12, 45), 0f),
                Arguments.of(new Date(121, Calendar.JANUARY, 1, 12, 30), new Date(121, Calendar.JANUARY, 1, 13, 30), 1f),
                Arguments.of(new Date(121, Calendar.JANUARY, 1, 12, 30), new Date(121, Calendar.JANUARY, 1, 18, 30), 6f),
                Arguments.of(new Date(121, Calendar.JANUARY, 1, 12, 30), new Date(121, Calendar.JANUARY, 2, 12, 30), 20f),
                Arguments.of(new Date(121, Calendar.JANUARY, 1, 12, 30), new Date(121, Calendar.JANUARY, 3, 15, 25), 40f),
                Arguments.of(new Date(121, Calendar.JANUARY, 1, 12, 30), new Date(121, Calendar.FEBRUARY, 1, 12, 30), 600f),
                Arguments.of(new Date(121, Calendar.JANUARY, 1, 12, 30), new Date(121, Calendar.APRIL, 1, 13, 30), 1800f)
                );
    }

}