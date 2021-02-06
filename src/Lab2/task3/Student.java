package Lab2.task3;

public class Student {
    String name;
    int age;
    int group;
    float averagePoint;

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getGroup() {
        return group;
    }

    public float getAveragePoint() {
        return averagePoint;
    }

    public Student(String name, int age, int group, float averagePoint) {
        this.name = name;
        this.age = age;
        this.group = group;
        this.averagePoint = averagePoint;
    }

    @Override
    public String toString() {
        return name+", "+age+" years, "+group+" group, Average point: "+averagePoint+"\n";
    }
}