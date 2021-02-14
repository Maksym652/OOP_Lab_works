package Lab3;

import javax.swing.text.DateFormatter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Car implements Serializable{
    String ownerName;
    String number;
    ArrayList<TimeInterval> visits = new ArrayList<TimeInterval>();


    public Car(String ownerName, String number, Date beginTime, Date endTime) {
        this.ownerName = ownerName;
        this.number = number;
        visits.add(new TimeInterval(beginTime, endTime));
    }
    public Car(String ownerName, String number, Date beginTime) {
        this.ownerName = ownerName;
        this.number = number;
        visits.add(new TimeInterval(beginTime));
    }

    public Car(String ownerName, String number)
    {
        this.ownerName = ownerName;
        this.number = number;
        visits.add(new TimeInterval());
    }

    public ArrayList<TimeInterval> filter(Date from, Date to)
    {
        ArrayList<TimeInterval> filteredList = new ArrayList<TimeInterval>();
        for (TimeInterval visit:
             visits) {
            if(visit.getBegin().after(from)&&visit.getEnd().before(to))
            {
                filteredList.add(visit);
            }
        }
        return filteredList;
    }

    public String visitList()
    {
        StringBuilder result = new StringBuilder();
        for (var visit:
             visits) {
            result.append(visit.toString()).append('\n');
        }
        return result.toString();
    }

    @Override
    public String toString() {
        return "Власник: "+ownerName
                +", номер: "+number
                +"\tВідвідування автостоянки:\n"
                +visitList()+' '+'\n';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return number.equals(car.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
