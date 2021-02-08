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
    private ArrayList<Car> currentCarList;
    private ArrayList<Car> allCustomersList;

    Parking(float hourPrice, float dayPrice, float monthPrice)
    {
        pricePerHour=hourPrice;
        pricePerDay=dayPrice;
        pricePerMonth=monthPrice;
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

    public ArrayList<Car> getAllCustomersList() {
        return allCustomersList;
    }
    public ArrayList<Car> getCurrentCarList()
    {
        return currentCarList;
    }

    public void addCar(String driver, String number)
    {
        Car car = new Car(driver, number);
        if(!currentCarList.contains(car)) {
            addCar(car);
        }
        else {
            System.out.println("Ця машина вже припаркована на стоянці.");
        }
    }
    public void addCar(String driver, String number, Date begin)
    {
        Car car = new Car(driver, number, begin);
        if(!currentCarList.contains(car)) {
            addCar(car);
        }
        else {
            System.out.println("Ця машина вже припаркована на стоянці.");
        }
    }
    public void addCar(String driver, String number, Date begin, Date end)
    {
        Car car = new Car(driver, number, begin, end);
    }
    private void addCar(Car car)
    {
        currentCarList.add(car);
        allCustomersList.add(car);
    }

    public void removeCar(String num)
    {
        currentCarList.removeIf(car -> car.number.equals(num));
        allCustomersList.forEach(car -> {if(car.number.equals(num))car.setEndTime();});
    }
    public void removeCar(String num, Date endTime)
    {
        currentCarList.removeIf(car -> car.number.equals(num));
        allCustomersList.forEach(car -> {if(car.number.equals(num))car.setEndTime(endTime);});
    }

    public void deleteCustomer(String name)
    {
        currentCarList.removeIf(car -> car.ownerName.equals(name));
        allCustomersList.removeIf(car -> car.ownerName.equals(name));
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
    private ArrayList<Car> filter(ArrayList<Car> carList, Predicate<Car> filter)
    {
        ArrayList<Car> result = new ArrayList<>();
        for (Car car:
                carList) {
            if(filter.test(car)) result.add(car);
        }
        return result;
    }

    public ArrayList<Car> getListForPeriod(Date from, Date to)
    {
        return filter(car -> car.endTime!=null&&car.beginTime.after(from)&&car.endTime.before(to));
    }
    public ArrayList<Car> getListForPeriod(ArrayList<Car> carList, Date from, Date to)
    {
        return filter(carList, car -> car.beginTime.after(from)&&car.endTime.after(to));
    }

    public ArrayList<Car> getListForOwner(String name)
    {
        return filter(car -> car.ownerName==name);
    }
    public ArrayList<Car> getListForOwner(ArrayList<Car> carList, String name)
    {
        return filter(carList, car -> car.ownerName.equals(name));
    }

    public ArrayList<Car> getListForCar(String num)
    {
        return filter(car -> car.number==num);
    }
    public ArrayList<Car> getListForCar(ArrayList<Car> carList, String num)
    {
        return filter(carList, car -> car.number.equals(num));
    }

    public ArrayList<Car> getMonthList(String ownerName)
    {
        Date today = new Date();
        Date monthBefore = new Date(today.getTime()- 2592000000L);
        return getListForOwner(getListForPeriod(monthBefore, today), ownerName);
    }

    public String formReport(ArrayList<Car> list)
    {
        float total=0;
        int duration;
        StringBuilder report = new StringBuilder();
        for (var car:
             list) {
            report.append(car.toString()).append('\t');
            if(car.endTime!=null)
                duration = (int)((car.endTime.getTime()-car.beginTime.getTime())/3600000);
            else
                duration = (int)((new Date().getTime()-car.beginTime.getTime())/3600000);
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
        report.append("_________________________________\nВСЬОГО:\t\t\t").append(total).append('₴').append('\n');
        return report.toString();
    }
    public String formReport(ArrayList<Car> list, Comparator<Car> comparator)
    {
        list.sort(comparator);
        return formReport(list);
    }
    public String formReport()
    {
        return formReport(allCustomersList);
    }
    public String formReport(Comparator<Car> comparator)
    {
        allCustomersList.sort(comparator);
        return formReport(allCustomersList);
    }

    public String formList(ArrayList<Car> list)
    {
        StringBuilder result = new StringBuilder();
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
        return formList(allCustomersList);
    }
    public String formList(ArrayList<Car> list, Comparator<Car> comparator)
    {
        list.sort(comparator);
        return formList(list);
    }
    public String formList(Comparator<Car> comparator)
    {
        allCustomersList.sort(comparator);
        return formList(allCustomersList);
    }

    public String monthReport(String ownerName)
    {
        return formReport(getMonthList(ownerName), (car1, car2) -> car1.beginTime.compareTo(car2.beginTime));
    }

    public String reportForOwner(String name)
    {
        return formReport(getListForOwner(name),(car1, car2) -> car1.beginTime.compareTo(car2.beginTime));
    }

    public String reportForCar(String num)
    {
        return formReport(getListForCar(num), (car1, car2) -> car1.beginTime.compareTo(car2.beginTime));
    }
}
