package Lab2.task4.test3;
import Lab2.task4.test.Test;

class Test3 extends Test {
    void Method() {
        Test3 t = new Test3();
        //System.out.println(t.x); помилка - x доступний тільки в "своєму" пакеті
        System.out.println(t.y);
        System.out.println(t.z);
        //System.out.println(t.k); помилка - k доступний тільки в своєму класі
    }
}

class Test4{
    void Method() {
        Test t = new Test();
        //System.out.println(t.x); //помилка - x доступний тільки в "своєму" пакеті
        System.out.println(t.y);
        //System.out.println(t.z); //помилка - z доступний лише в пакеті свого класу або нащадкам свого класу
        //System.out.println(t.k); //помилка - k доступний тільки в своєму класі
    }
}