//ВАРІАНТ №14, НОМЕР ЗАВДАННЯ - 60 "ПРИВАТНА АВТОСТОЯНКА"
//Створіть наступну модель: є приватна автостоянка. Кожного місяця, водіям, які паркували авто, видається рахунок за
// використання послугами стоянки. Звіт можливо сортувати або по авто, або по власникам. Можлива ситуація коли 1 власник
// має декілько авто. Повинна бути можливість отримати список всіх авто які є на стоянці зараз, та журнал обліку
// виїздів-заїздів за період. Потрібна можливість формування звіту по окремому авто або власнику
package Lab3;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.function.Predicate;

//вважатимемо, що ціни за паркування формуються наступним шляхом:
//якщо автомобіль стоїть менше дня - плата береться за годину
//якщо більше дня але менше місяця - за день
//якщо більше місяця - за місяць (місяцем вважаються 30 днів)

public class Parking {
    private float pricePerDay;
    private float pricePerMonth;
    private float pricePerHour;
    private int countOfPlaces;
    private ArrayList<Car> currentCarList;
    private ArrayList<Car> allCustomersList;

    Parking(float hourPrice, float dayPrice, float monthPrice, int countOfPlaces)
    {
        pricePerHour=hourPrice;
        pricePerDay=dayPrice;
        pricePerMonth=monthPrice;
        this.countOfPlaces=countOfPlaces;
        currentCarList = new ArrayList<>();
        allCustomersList = new ArrayList<>();
        //дані про автомобілі на стоянці записуються з файлів
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("CarsOnParkingNow.dat")))
        {

            currentCarList=((ArrayList<Car>)ois.readObject());
        }
        catch(Exception ex){
            System.out.println("Список машин на стоянці порожній: відповідний файл не знайдено.");
        }
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("AllCustomersOfParking.dat")))
        {

            allCustomersList= (ArrayList<Car>)ois.readObject();
        }
        catch(Exception ex){
            System.out.println("Список клієнтів порожній: відповідний файл не знайдено.");
        }
    }

    void finishWork()
    {//метод, який перезаписує дані в файли
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("CarsOnParkingNow.dat")))
        {
            oos.writeObject(currentCarList);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("AllCustomersOfParking.dat")))
        {
            oos.writeObject(allCustomersList);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void setPricePerHour(float pricePerHour) {
        this.pricePerHour = pricePerHour;
    }
    public void setPricePerDay(float pricePerDay) {
        this.pricePerDay = pricePerDay;
    }
    public void setPricePerMonth(float pricePerMonth) {
        this.pricePerMonth = pricePerMonth;
    }
    public int places()
    {
        return countOfPlaces;
    }

    private boolean isOnParking(String number)
    {
        for (Car car:
             currentCarList) {
            if(car.number.equals(number))
                return true;
        }
        return false;
    }

    private Car findCar(String number)
    {
        for (Car car:
             allCustomersList) {
            if(car.number.equals(number))
                return car;
        }
        return null;
    }

    private ArrayList<Car> filter(Predicate<Car> filter)
    {
        ArrayList<Car> result = new ArrayList<>();
        for (Car car:
                allCustomersList) {
            if(filter.test(car)) result.add(car);
        }
        return result;
    }
    public void parkCar(String ownerName, String number)
    {
        if(currentCarList.size()>countOfPlaces)
        {
            System.out.println("Вільних місць немає!");
            return;
        }
        if(!isOnParking(number)) {
            if(findCar(number)!=null)findCar(number).visits.add(new TimeInterval());
            else {
                Car car = new Car(ownerName, number);
                allCustomersList.add(car);
                currentCarList.add(car);
            }
        }
        else {
            System.out.println("Ця машина вже припаркована.");
        }
    }
    public void parkCar(String ownerName, String number, Date begin)
    {
        if(currentCarList.size()>countOfPlaces)
        {
            System.out.println("Вільних місць немає!");
            return;
        }
        if(!isOnParking(number)) {
            if(findCar(number)!=null)findCar(number).visits.add(new TimeInterval(begin));
            else {
                Car car = new Car(ownerName, number, begin);
                allCustomersList.add(car);
                currentCarList.add(car);
            }
        }
        else {
            System.out.println("Ця машина вже припаркована.");
        }
    }
    public void parkCar(String ownerName, String number, Date begin, Date end)
    {
        if(currentCarList.size()>countOfPlaces)
        {
            System.out.println("Вільних місць немає!");
            return;
        }
        if(!isOnParking(number)) {
            if(findCar(number)!=null)findCar(number).visits.add(new TimeInterval(begin, end));
            else {
                Car car = new Car(ownerName, number, begin, end);
                allCustomersList.add(car);
                currentCarList.add(car);
            }
        }
        else {
            System.out.println("Ця машина вже припаркована.");
        }
    }
    public void leaveParking(String number)
    {
        if(isOnParking(number)) {
            if(findCar(number)!=null) {
                currentCarList.remove(findCar(number));
            }
        }
        else{
            System.out.println("Ця машина відсутня на парковці");
        }
    }
    public void leaveParking(String number, Date end)
    {
        if(isOnParking(number)) {
            if(findCar(number)!=null) {
                currentCarList.remove(findCar(number));
            }
        }
        else{
            System.out.println("Ця машина відсутня на парковці.");
        }
    }
    public String formReport(Comparator<Car> comparator, Predicate<Car> filter)
    {
        float total = 0;
        int duration;
        StringBuilder report = new StringBuilder();
        ArrayList<Car> list = filter(filter);
        list.sort(comparator);
        for(Car car: list)
        {
            report.append("Власник: ").append(car.ownerName).append(", Номер: ").append(car.number).append("\n");
            for(TimeInterval visit : car.visits)
            {
                report.append(visit.toString());
                duration = (int) visit.getDuration();
                if(duration>=24)
                {
                    duration/=24;
                    if(duration>=30)
                    {
                        duration/=30;
                        report.append(duration);
                        switch (duration) {
                            case 1 -> report.append(" місяць");
                            case 2, 3, 4 -> report.append(" місяці");
                            default -> report.append(" місяців");
                        }
                        report.append('\t').append(duration * pricePerMonth).append("₴");
                        total+=duration*pricePerMonth;
                    }
                    else
                    {
                        report.append(duration);
                        switch (duration) {
                            case 1, 21 -> report.append(" день");
                            case 2, 3, 4, 22, 23, 24 -> report.append(" дні");
                            default -> report.append(" днів");
                        }
                        report.append('\t').append(duration * pricePerDay).append("₴");
                        total+=duration*pricePerDay;
                    }
                }
                else {
                    report.append(duration);
                    switch (duration) {
                        case 1, 21 -> report.append(" година");
                        case 2, 3, 4, 22, 23 -> report.append(" години");
                        default -> report.append(" годин");
                    }
                    report.append('\t').append(duration * pricePerHour).append("₴");
                    total+=duration*pricePerHour;
                }
                report.append('\n');
            }
        }
        report.append("_________________________________\nВСЬОГО:\t\t\t").append(total).append('₴').append('\n');
        return report.toString();
    }
    public String formReport()
    {
        return formReport((Car car1, Car car2) -> car1.ownerName.compareTo(car2.ownerName), car -> true);
    }
    public String formReport(Predicate<Car> filter)
    {
        return formReport((Car car1, Car car2) -> car1.ownerName.compareTo(car2.ownerName), filter);
    }
    public String formList(Comparator<Car> comparator, Predicate<Car> filter)
    {
        StringBuilder result = new StringBuilder();
        ArrayList<Car> list = filter(filter);
        list.sort(comparator);
        for (var car:
             list) {
            result.append(car.ownerName).append(" ").append(car.number).append("\n");
        }
        result.append("_________________________________\nКІЛЬКІСТЬ ЗАПИСІВ:\t\t");
        result.append(list.toArray().length);
        return result.toString();
    }
    public String formList()
    {
        return formList((Car car1, Car car2) -> car1.ownerName.compareTo(car2.ownerName), car -> true);
    }
    public String currentCarList()
    {
        StringBuilder result = new StringBuilder();
        ArrayList<Car> list = currentCarList;
        for (var car:
                list) {
            result.append(car.ownerName).append(" ").append(car.number).append("\n");
        }
        result.append("_________________________________\nКІЛЬКІСТЬ АВТОМОБІЛІВ:\t\t");
        result.append(list.toArray().length).append('/').append(countOfPlaces);
        return result.toString();
    }
    public String monthReport(String ownerName)
    {
        Date monthBefore = new Date(new Date().getTime()-2592000000L);
        float total = 0;
        int duration;
        StringBuilder report = new StringBuilder();
        ArrayList<Car> list = filter(car -> car.ownerName.equals(ownerName));
        list.sort((Car car1, Car car2) -> car1.visits.size()-car2.visits.size());
        for(Car car: list)
        {
            if(car.filter(monthBefore, new Date(new Date().getTime()+1000)).size()>0) {
                report.append("Власник: ").append(car.ownerName).append(", Номер: ").append(car.number).append("\n");
            }
            for(TimeInterval visit : car.filter(monthBefore, new Date(new Date().getTime()+1000)))
            {
                report.append(visit.toString());
                duration = (int) visit.getDuration();
                if(duration>=24)
                {
                    duration/=24;
                    if(duration>=30)
                    {
                        duration/=30;
                        report.append(duration);
                        switch (duration) {
                            case 1 -> report.append(" місяць");
                            case 2, 3, 4 -> report.append(" місяці");
                            default -> report.append(" місяців");
                        }
                        report.append('\t').append(duration * pricePerMonth).append("₴");
                        total+=duration*pricePerMonth;
                    }
                    else
                    {
                        report.append(duration);
                        switch (duration) {
                            case 1, 21 -> report.append(" день");
                            case 2, 3, 4, 22, 23, 24 -> report.append(" дні");
                            default -> report.append(" днів");
                        }
                        report.append('\t').append(duration * pricePerDay).append("₴");
                        total+=duration*pricePerDay;
                    }
                }
                else {
                    report.append(duration);
                    switch (duration) {
                        case 1, 21 -> report.append(" година");
                        case 2, 3, 4, 22, 23 -> report.append(" години");
                        default -> report.append(" годин");
                    }
                    report.append('\t').append(duration * pricePerHour).append("₴");
                    total+=duration*pricePerHour;
                }
                report.append('\n');
            }
        }
        report.append("_________________________________\nВСЬОГО:\t\t\t").append(total).append('₴').append('\n');
        return report.toString();
    }

    public String reportForOwner(String name)
    {
        return formReport(car -> car.ownerName.equals(name));
    }

    public String reportForCar(String num)
    {

        return formReport(car -> car.number.equals(num));
    }
}
