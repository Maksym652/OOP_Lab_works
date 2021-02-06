package Lab2.task4.test;

public class Test
{
    int x;
    public int y;
    protected int z;
    private int k;

    void Method(){
        System.out.println(x);
        System.out.println(y);
        System.out.println(z);
        System.out.println(k);
    }
}

class Test2{
    void Method(){
        Test t = new Test();
        System.out.println(t.x);
        System.out.println(t.y);
        System.out.println(t.z);
        //System.out.println(t.k); помилка - k доступний тільки в своєму класі
    }
}

class Test0 extends Test{
    void Method2() {
        Test t = new Test();
        System.out.println(t.x);
        System.out.println(t.y);
        System.out.println(t.z);
        //System.out.println(t.k); //помилка - k доступний тільки в своєму класі
    }
}
