package Lab2.task2;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Food extends Goods {
    Date expirationDate;
    String group;

    public Food(String name, String group, String producer, Date date, Date expirationDate, float price) {
        super(name, producer, date, price);
        this.group = group;
        this.expirationDate = expirationDate;
    }

    public Food(String name, String group, String producer, Date expirationDate, float price) {
        super(name, producer, price);
        this.group = group;
        this.expirationDate = expirationDate;
    }

    public Food(String name, String group, String producer, Date date, int days, float price) {
        super(name, producer, date, price);
        this.group = group;
        Calendar c = new GregorianCalendar(date.getYear(), date.getMonth(), date.getDate());
        c.add(Calendar.DATE, days);
        this.expirationDate = c.getTime();
    }

    public Food(String name, String group, String producer, int days, float price) {
        super(name, producer, price);
        this.group = group;
        Calendar c = new GregorianCalendar(date.getYear() + 1900, date.getMonth(), date.getDate());
        c.add(Calendar.DATE, days);
        this.expirationDate = c.getTime();
    }

    public void checkDate() {
        if (expirationDate.before(new Date())) {
            System.out.printf("Shelf life of %s is up!\n", name);
            return;
        }
        if (!expirationDate.after(new Date())) {
            System.out.printf("Today is the last day of shelf life of %s\n", name);
            return;
        }
        System.out.printf(((expirationDate.getTime() - new Date().getTime()) / (1000 * 60 * 60 * 24)) + " days left until the expiration date of %s\n", name);
    }

    public void checkDate(Date date) {
        if (expirationDate.before(date)) {
            System.out.printf("Shelf life of %s will be up to this date!\n", name);
            return;
        }
        if (!expirationDate.after(date)) {
            System.out.printf("This date is the last day of shelf life of %s\n", name);
            return;
        }
        System.out.printf((expirationDate.getTime() - date.getTime()) / (1000 * 60 * 60 * 24) + " days left from this date to the expiration date of %s\n", name);
    }

    @Override
    public String toString() {
        return "Name: " + name + "\nGroup: " + group + "\nProducer: " + producer + "\nProducing date: " + date.toString()
                + "\nexpiration date: " + expirationDate.toString() + "\nPrice:" + price + "₴";
    }
}
