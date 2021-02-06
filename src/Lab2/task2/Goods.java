package Lab2.task2;

import java.util.Date;

public class Goods {
    String name;
    String producer;
    Date date;
    float price;

    public Goods(String name, String producer, Date date, float price) {
        this.name = name;
        this.producer = producer;
        this.date = date;
        this.price = price;
    }

    public Goods(String name, String producer, float price) {
        this.name = name;
        this.producer = producer;
        this.date = new Date();//встановлення поточної дати та часу
        this.price = price;
    }

    @Override
    public String toString() {
        return "Name" + name + "\nProducer: " + producer + "\nProducing date: " + date.toString() + "\nPrice: " + price + "₴";
    }
}
