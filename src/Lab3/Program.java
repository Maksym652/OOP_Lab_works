package Lab3;

import java.util.Date;

public class Program {
    public static void main(String[] args) {
        Parking p = new Parking(1f, 20f, 600f);
        p.addCar("Іваненко Іван Іванович", "BB4589AD");
        p.addCar("Петренко Петро Петрович", "AA2398BD", new Date(120, 11, 15, 16, 0));
        p.addCar("Степаненко Степан Степанович", "AB8768BD", new Date(121, 0, 15, 16, 0));
        p.addCar("Федорчук Федір Федорович", "BA9731AB", new Date(120, 11, 27, 15, 30));
        p.addCar("Федорчук Федір Федорович", "BE3456AC", new Date(121, 0, 25, 15, 30));
        p.addCar("Федорчук Федір Федорович", "BA1256AB", new Date(121, 1, 2, 10, 45));
        p.addCar("Федорчук Василь Федорович", "BA9731AB");
        p.removeCar("BA9731AB");
        p.removeCar("BE3456AC", new Date(121, 0, 28, 15, 30));
        p.removeCar("BA1256AB", new Date(121, 1, 2, 15, 30));
        System.out.println(p.formList()+'\n');
        System.out.println(p.monthReport("Федорчук Федір Федорович"));
        System.out.println(p.formReport(p.getCurrentCarList()));
        System.out.println('\n'+p.formReport(p.getAllCustomersList()));
        p.finishWork();
    }
}
