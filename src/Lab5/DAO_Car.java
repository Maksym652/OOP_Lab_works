package Lab5;

import Lab3.Car;
import Lab3.TimeInterval;
import Lab5.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DAO_Car implements DAO<Car> {
    Connection connection;

    @Override
    public Car create(Car entity) {
        try {
            if(connection==null){
                connection=getConnection();
            }
            String sql="INSERT INTO Cars (`number`, `owner`)  VALUES ( ?,  ?)";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,entity.getNumber());
            preparedStatement.setString(2,entity.getOwnerName());
            preparedStatement.execute();
            if(!entity.getVisits().isEmpty())
            {
                preparedStatement = connection.prepareStatement("INSERT INTO Visits (`carNumber`, `beginTime`, `endTime`) VALUES (?, ?, ?)");
                for (var visit:entity.getVisits()) {
                    preparedStatement.setString(1,entity.getNumber());
                    preparedStatement.setTime(2, new Time(visit.getBegin().getTime()));
                    preparedStatement.setTime(3, (visit.getEnd().equals(new Date()))?null:new Time(visit.getEnd().getTime()));
                    preparedStatement.execute();
                }
            }
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return entity;
    }

    @Override
    public boolean update(Car entity) {
        Boolean result = false;
        try {
            if(connection==null){
                connection=getConnection();
            }
            String sql="UPDATE Cars   SET  owner = ? WHERE number = ?";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,entity.getOwnerName());
            preparedStatement.setString(2,entity.getNumber());
            result = preparedStatement.execute();
            if(!entity.getVisits().isEmpty())
            {
                Statement statement = connection.createStatement();
                result = statement.execute("DELETE FROM Visits WHERE carNumber = \""+entity.getNumber()+"\"");
                preparedStatement = connection.prepareStatement("INSERT INTO Visits (`carNumber`, `beginTime`, `endTime`) VALUES (?, ?, ?)");
                for (var visit:entity.getVisits()) {
                    preparedStatement.setString(1,entity.getNumber());
                    preparedStatement.setTime(2, new Time(visit.getBegin().getTime()));
                    preparedStatement.setTime(3, (visit.getEnd().equals(new Date()))?null:new Time(visit.getBegin().getTime()));
                    result = preparedStatement.execute();
                }
            }
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(String id) {
        boolean result=false;
        try {
            if(connection==null){
                connection=getConnection();
            }
            String sql="DELETE FROM Cars   WHERE number = ?";
            PreparedStatement preparedStatement=connection.prepareStatement(sql);
            preparedStatement.setString(1,id);
            result=preparedStatement.execute();
            preparedStatement=connection.prepareStatement("DELETE FROM Visits WHERE carNumber = ?");
            preparedStatement.setString(1,id);
            result=preparedStatement.execute();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return result;

    }

    @Override
    public List<Car> find(Car entity) {
        return findById(entity.getNumber());
    }

    @Override
    public List<Car> findById(String number) {
        List<Car> result=new ArrayList<>();
        try {
            if(connection==null){
                connection=getConnection();
            }
            String sql="SELECT *  FROM Cars WHERE number = \""+number+"\"";
            Statement statement=connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sql);
            while (resultSet.next()){
                String owner = resultSet.getString(2);
                Car car=new Car(owner,number);
                result.add(car);
            }
            if(!result.isEmpty())
            {
                sql = "SELECT *  FROM Visits WHERE carNumber = '"+number+"'";
                resultSet=statement.executeQuery(sql);
                while (resultSet.next())
                {
                    Date begin = resultSet.getTime(3);
                    Date end = resultSet.getTime(4);
                    result.get(0).addVisit(new TimeInterval(begin, end));
                }
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    @Override
    public ArrayList<Car> findAll() {
        ArrayList<Car> result=new ArrayList<Car>();
        try {
            if(connection==null){
                connection=getConnection();
            }
            String sql="SELECT *  FROM Cars ";
            Statement statement=connection.createStatement();
            Statement st2 = connection.createStatement();
            ResultSet resultSet=statement.executeQuery(sql);
            while (resultSet.next()){
                String number = resultSet.getString(1);
                String owner = resultSet.getString(2);
                Car car=new Car(owner,number);
                ResultSet visitSet=st2.executeQuery("SELECT *  FROM Visits  WHERE carNumber = '"+car.getNumber()+"'");
                while (visitSet.next()){
                    Date begin = visitSet.getTime(3);
                    Date end = visitSet.getTime(4);
                    car.addVisit(new TimeInterval(begin, end));
                }
                result.add(car);
            }
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public boolean addVisit(String number, Date begin, Date end){
        Boolean result = false;
        try {
            if(connection==null){
                connection=getConnection();
            }
            if(!findById(number).isEmpty()) {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO Visits (`carNumber`, `beginTime`, `endTime`) VALUES (?, ?, ?)");
                preparedStatement.setString(1, number);
                preparedStatement.setTime(2, new Time(begin.getTime()));
                preparedStatement.setTime(3, (end!=null)?new Time(end.getTime()):null);
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
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE Visits SET endTime = ? WHERE carNumber = ?");
            preparedStatement.setTime(1, new Time(end.getTime()));
            preparedStatement.setString(2,number);
            result = preparedStatement.execute();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
        return result;
    }

    public boolean isOnParkingNow(String number)
    {
        Boolean result = false;
        if(findById(number).isEmpty()) return result;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM Visits WHERE carNumber = ? AND (endTime=NULL OR (beginTime < ? AND endTime > ?))");
            preparedStatement.setString(1,number);
            preparedStatement.setTime(2, new Time(new Date().getTime()));
            preparedStatement.setTime(3, new Time(new Date().getTime()));
            ResultSet resultSet = preparedStatement.executeQuery();
            result = resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
