package Lab3;

import java.util.Date;

public class Program {
    public static void main(String[] args) {
        Parking p = new Parking(1f, 20f, 600f, 100);
        p.parkCar("Іваненко Іван Іванович", "BB4589AD");
        p.parkCar("Петренко Петро Петрович", "AA2398BD", new Date(120, 11, 15, 16, 0));
        p.parkCar("Степаненко Степан Степанович", "AB8768BD", new Date(121, 0, 15, 16, 0));
        p.parkCar("Федорчук Федір Федорович", "BA9731AB", new Date(120, 11, 27, 15, 30));
        p.parkCar("Федорчук Федір Федорович", "BE3456AC", new Date(121, 0, 25, 15, 30));
        p.parkCar("Федорчук Федір Федорович", "BA1256AB", new Date(121, 1, 2, 10, 45));
        p.parkCar("Федорчук Василь Федорович", "BA9731AB");
        p.parkCar("Артеменко Артем Артемович", "BA0987BA", new Date(121, 1, 1, 14, 40));
        p.leaveParking("AA2398BD");
        p.parkCar("Петренко Петро Петрович", "AA2398BD", new Date(121, 1, 12, 16, 40));
        p.leaveParking("AA2398BD");
        p.leaveParking("AB8768BD", new Date(121,1,10,17,15));
        p.parkCar("Степаненко Степан Степанович", "AB8768BD");
        p.leaveParking("BA9731AB");
        p.leaveParking("BE3456AC", new Date(121, 0, 28, 15, 30));
        p.leaveParking("BA1256AB", new Date(121, 1, 2, 15, 30));
        //System.out.println(p.formList());
        System.out.println(p.monthReport("Федорчук Федір Федорович"));
        System.out.println(p.formReport());
        System.out.println('\n'+p.formReport((Car car1, Car car2) -> car1.ownerName.compareTo(car2.ownerName), car -> car.visits.size()>1));
        p.parkCar("Федорчук Василь Федорович", "BA9731AB", new Date(121,1,5,18,10));
        p.leaveParking("BA9731AB");
        System.out.println('\n'+p.reportForOwner("Федорчук Федір Федорович"));
        System.out.println('\n'+p.reportForCar("BA9731AB"));
        System.out.println('\n'+p.carsNowAtParking());
        p.finishWork();
    }
}
