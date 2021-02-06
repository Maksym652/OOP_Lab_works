package Lab2.task2;

import java.util.Date;

public class RadioElektronics extends Goods {
    String type;
    float warranty;

    public RadioElektronics(String name, String type, String producer, Date date, float warranty, float price) {
        super(name, producer, date, price);
        this.type = type;
        this.warranty = warranty;
    }

    public RadioElektronics(String name, String type, String producer, Date date, float price) {
        super(name, producer, date, price);
        this.type = type;
        this.warranty = 1f;
    }

    public RadioElektronics(String name, String type, String producer, float warranty, float price) {
        super(name, producer, price);
        this.type = type;
        this.warranty = warranty;
    }

    public RadioElektronics(String name, String type, String producer, float price) {
        super(name, producer, price);
        this.type = type;
        this.warranty = 1f;
    }

    @Override
    public String toString() {
        return "Name: " + name + "Type: " + type + "\nProducer: " + producer + "\nProducing date :" + date.toString() + "Warranty term:" + warranty + "year(s)" + "\nPrice:" + price + "₴";
    }
}
