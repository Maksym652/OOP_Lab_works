package Lab3;

import java.io.Serializable;
import java.util.*;

public class Car implements Serializable{
    String ownerName;
    String number;
    ArrayList<TimeInterval> visits = new ArrayList<TimeInterval>();

    public String getOwnerName() {
        return ownerName;
    }
    public void setOwnerName(String name){ownerName=name;}
    public String getNumber() {
        return number;
    }
    public void setNumber(String number){this.number = number;}

    public ArrayList<TimeInterval> getVisits(){
        return visits;
    }

    public boolean addVisit(TimeInterval visit){
        return visits.add(visit);
    }

    public boolean isOnParking() {
        if(visits.isEmpty())
            return false;
        return visits.get(visits.size() - 1).includes(new TimeInterval(new Date(), new Date()));
    }

    public Car(String ownerName, String number)
    {
        this.ownerName = ownerName;
        this.number = number;
    }
    public Car(String ownerName, String number, Date beginTime, Date endTime) {
        this.ownerName = ownerName;
        this.number = number;
        visits.add(new TimeInterval(beginTime, endTime));
    }

    public ArrayList<TimeInterval> visitsForPeriod(Date from, Date to)
    {
        ArrayList<TimeInterval> filteredList = new ArrayList<TimeInterval>();
        for (TimeInterval visit:
             visits) {
            if(new TimeInterval(from, to).includes(visit))
            {
                filteredList.add(visit);
            }
        }
        return filteredList;
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
