package Lab5;

import java.sql.*;
import java.util.Date;

public class Car_Controller {
    Connection connection;
    DAO_Car dao_car = new DAO_Car();
    Connection getConnection() throws SQLException, ClassNotFoundException {
        Connection result= DriverManager.getConnection("jdbc:sqlite:D://МАКСИМ//Навчання//2020-2021//ІІ семестр//ООП//ParkingDataBase.db");
        return result;
    }
    public boolean addVisit(String number, Date begin){
        Boolean result = false;
        try {
            if(connection==null){
                connection=getConnection();
            }
            if(!dao_car.findById(number).isEmpty()) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Visits (`carNumber`, `beginTime`) VALUES (?, ?)");
                preparedStatement.setString(1, number);
                preparedStatement.setTime(2, new Time(begin.getTime()));
                result = preparedStatement.execute();
            }
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean setEndVisit(String number, Date end) {
        boolean result = false;
        try {
            if(connection==null){
                connection=getConnection();
            }
            if(!dao_car.findById(number).isEmpty()) {
                PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Visits SET endTime = ? WHERE carNumber = ?");
                preparedStatement.setTime(1, new Time(end.getTime()));
                preparedStatement.setString(2,number);
                result = preparedStatement.execute();
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }
}
