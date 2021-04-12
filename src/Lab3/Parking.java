//ВАРІАНТ №14, НОМЕР ЗАВДАННЯ - 60 "ПРИВАТНА АВТОСТОЯНКА"
//Створіть наступну модель: є приватна автостоянка. Кожного місяця, водіям, які паркували авто, видається рахунок за
// використання послугами стоянки. Звіт можливо сортувати або по авто, або по власникам. Можлива ситуація коли 1 власник
// має декілько авто. Повинна бути можливість отримати список всіх авто які є на стоянці зараз, та журнал обліку
// виїздів-заїздів за період. Потрібна можливість формування звіту по окремому авто або власнику
package Lab3;

import Lab5.DAO_Car;

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
    DAO_Car dao = new DAO_Car();

    public Parking(float hourPrice, float dayPrice, float monthPrice, int countOfPlaces)
    {
        pricePerHour=hourPrice;
        pricePerDay=dayPrice;
        pricePerMonth=monthPrice;
        this.countOfPlaces=countOfPlaces;
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

    public ArrayList<Car> getCarsOnParkingNow() {
        ArrayList<Car> result = new ArrayList<Car>();
        for (Car car:dao.findAll()) {
            if(car.isOnParking())
                result.add(car);
        }
        return result;
    }
    public ArrayList<Car> getAllCars() {
        return (ArrayList<Car>) dao.findAll();
    }

    public float[] getPrices()
    {
        return new float[] {pricePerHour, pricePerDay, pricePerMonth};
    }
    public int placesCount()
    {
        return countOfPlaces;
    }


    public Car findCar(String number)
    {
        return dao.findById(number).isEmpty()?null:dao.findById(number).get(0);
    }

    private ArrayList<Car> filtration(Predicate<Car> filter)
    {
        ArrayList<Car> result = new ArrayList<>();
        for (Car car : getAllCars()) {
            if(filter.test(car)) result.add(car);
        }
        return result;
    }

    public boolean parkCar(String ownerName, String number)
    {
        return parkCar(ownerName, number, new Date(), null);
    }

    public boolean parkCar(String ownerName, String number, Date begin)
    {
        return parkCar(ownerName, number, begin, null);
    }
    public boolean parkCar(String ownerName, String number, Date begin, Date end)
    {
        if(getCarsOnParkingNow().size()==countOfPlaces)
        {
            return false;
        }
        Car car = findCar(number);
        if(car!=null)
        {
            if(!car.isOnParking())
                return dao.addVisit(number,begin,end);
            else return false;
        }
        else
        {
            car = new Car(ownerName, number, begin, end);
            dao.create(car);
            return true;
        }
    }

    public boolean leaveParking(String number)
    {
        return leaveParking(number, new Date());
    }
    public boolean leaveParking(String number, Date end)
    {
        if(findCar(number)==null) return false;
        return dao.setEndVisit(number,end);
    }

    public float calculatePrice(Date begin, Date end)
    {
        TimeInterval ti = new TimeInterval(begin, end);
        long duration = ti.getDurationInHours();
        if(duration<24)
        {
            return duration*pricePerHour;
        }
        else
        {
            duration/=24;
            if(duration<30)
            {
                return duration*pricePerDay;
            }
            else
            {
                duration/=30;
                return duration*pricePerMonth;
            }
        }
    }

    public String formReport(Comparator<Car> comparator, Predicate<Car> filter)
    {
        float total = 0;
        int duration;
        StringBuilder report = new StringBuilder();
        ArrayList<Car> list = filtration(filter);
        list.sort(comparator);
        for(Car car: list)
        {
            report.append("Власник: ").append(car.ownerName).append(", Номер: ").append(car.number).append("\n");
            for(TimeInterval visit : car.visits)
            {
                report.append(visit.toString()).append('\t');
                duration = (int) visit.getDurationInHours();
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
        ArrayList<Car> list = filtration(filter);
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
    public String carsNowAtParking()
    {
        StringBuilder result = new StringBuilder();
        ArrayList<Car> list = getCarsOnParkingNow();
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
        ArrayList<Car> list = filtration(car -> car.ownerName.equals(ownerName));
        list.sort((Car car1, Car car2) -> car1.visits.size()-car2.visits.size());
        for(Car car: list)
        {
            if(car.visitsForPeriod(monthBefore, new Date(new Date().getTime()+1000)).size()>0) {
                report.append("Власник: ").append(car.ownerName).append(", Номер: ").append(car.number).append("\n");
            }
            for(TimeInterval visit : car.visitsForPeriod(monthBefore, new Date(new Date().getTime()+1000)))
            {
                report.append(visit.toString()).append("\t");
                duration = (int) visit.getDurationInHours();
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
