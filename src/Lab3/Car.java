package Lab3;

import javax.swing.text.DateFormatter;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Objects;

public class Car implements Serializable{
    String ownerName;
    String number;
    Date beginTime;
    Date endTime;

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }
    public void setBeginTime() {
        this.beginTime = new Date();
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public void setEndTime() {
        this.endTime = new Date();
    }


    public Car(String ownerName, String number, Date beginTime, Date endTime) {
        this.ownerName = ownerName;
        this.number = number;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }
    public Car(String ownerName, String number, Date beginTime) {
        this.ownerName = ownerName;
        this.number = number;
        this.beginTime = beginTime;
    }

    public Car(String ownerName, String number)
    {
        this.ownerName = ownerName;
        this.number = number;
        this.setBeginTime();
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("[dd.MM.yy HH:mm]");
        if(endTime==null)
        {
            return "Власник: '" + ownerName + '\'' +
                    ", Номерний знак: '" + number + '\'' +
                    ", Початок стоянки: " + df.format(beginTime);
        }
        else
        {
            return "Власник: '" + ownerName + '\'' +
                    ", Номерний знак: '" + number + '\'' +
                    ", Час на стоянці: з: " + df.format(beginTime) +
                    ", до: " + df.format(endTime);
        }
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
