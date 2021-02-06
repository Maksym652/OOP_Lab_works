package Lab2;

import Lab2.task2.Food;
import Lab2.task3.Student;

import java.util.*;

public class Main {

    public static void main(String[] args) {
        int[] arr = {8, 6, 2, 9, 1, 3, 7, 5, 0, 4};
        //ЗАВД.40 ДЕМОНСТРАЦІЯ РОБОТИ МЕТОДІВ КЛАСУ Arrays
        //1)метод sort() - сортує масив у порядку зростання
        Arrays.sort(arr);
        //2)метод toString() - повертає рядок, що містить значення елементів масиву
        System.out.println(Arrays.toString(arr));
        //3)метод fill() - заповнює масив вказаним значенням
        Arrays.fill(arr, 1);
        System.out.println(Arrays.toString(arr));
        //4)метод copyOfRange() - повертає масив, що містить частину елементів масиву
        int[] arr1 = Arrays.copyOfRange(arr, 2, 4);
        int[] arr2 = Arrays.copyOfRange(arr, 5, 7);
        //5) метод equals() - повертає true, якщо 2 вказані масиви рівні між собою
        if (Arrays.equals(arr1, arr2)) {
            System.out.println(Arrays.toString(arr1));
        }
        //6) метод deepEquals() може порівнювати багатовимірні масиви
        int[][] arr3 = {{1, 2, 3}, {4, 5, 6}};
        int[][] arr4 = arr3;
        System.out.println(Arrays.deepEquals(arr3, arr4));
        //7)метод deepToString() повертає рядок, що представляє собою багатовимірний масив
        System.out.println(Arrays.deepToString(arr3));
        //8)метод copyOf() повертає копію масиву
        arr = new int[] {1, 2, 3, 4, 5};
        int[] arr_copy = Arrays.copyOf(arr, 5);
        System.out.println(Arrays.toString(arr_copy));
        //9)метод mismatch() повертає індекс першої розбіжності у значеннях 2-х масивів або -1 якщо розбіжності немає
        arr_copy[3]=9;
        System.out.println(Arrays.mismatch(arr, arr_copy));
        //10)метод binarySearch() - шукає елемент у відсортованому масиві та повертає його індекс
        System.out.println(Arrays.binarySearch(arr, 3));
        //11)метод compare() лексикографічно порівнює два масиви (тобто повертає -1, 0 або 1)
        System.out.println(Arrays.compare(arr, arr_copy));
        //12)метод compareUnsigned() лексикографічно порівнює два масиви, обробляючи їхні значення як беззнакові
        arr2 = new int[] {1, 2, 3, 4, 5};
        System.out.println(Arrays.compareUnsigned(arr, arr2));
        //13)метод setAll() встановлює всі значення масиву відповідно до заданої функції, яка приймає індекс елемента
        Arrays.setAll(arr, i -> i*i);
        System.out.println(Arrays.toString(arr));
        //14)метод hashCode() - повертає хеш-код, який залежить від вмісту масиву. Однакові масиви матимуть однакові хешкоди
        System.out.println(Arrays.hashCode(arr));
        System.out.println(Arrays.hashCode(new int[] {0,1,4,9,16}));
        //15)метод aslist формує список на основі масиву. При цьому зберігається зв'язок між об'єктами в списку і в масиві
        //тобто зміни в списку впливають на масив і навпаки
        List<String> list = Arrays.asList(new String[] {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"});
        System.out.println(list.get(2));//виведення третього елементу в списку рядків
        System.out.println("\n");
        //перевірка роботи класів (завд.2(45))
        Food f1 = new Food("хліб \"Сімейний\"", "хлібобулочні вироби", "Тернівський хлібзавод", new Date(121, 0,23), 11.5f);
        Food f2 = new Food("молоко \"Первомайське\"", "молочні продукти", "Первомайський молокозавод", 5, 15.9f);
        System.out.println(f2);
        f1.checkDate();
        f1.checkDate(new Date(121, 2, 1));
        f2.checkDate(new Date(121, 0, 21));

        //завд.3(36)
        Student[] students = new Student[] {new Student("Petrenko", 18, 111, 81.4f),
                new Student("Sydorenko", 20, 432, 77.7f),
                new Student("Fedorchuk", 17, 123, 71.5f),
                new Student("Melnyk", 19, 222, 88.8f),
                new Student("Ivanenko",18, 123, 67.8f)
        };
        Main m = new Main();
        //сортування за іменем в алфавітному порядку
        m.sortStudents(students, (Student st1, Student st2) -> st1.getName().compareTo(st2.getName()));
        System.out.println("Sorted by name:\n"+Arrays.toString(students));
        //сортування за зростанням віку
        m.sortStudents(students, (Student st1, Student st2) -> st1.getAge()-st2.getAge());
        System.out.println("Sorted by age:\n"+Arrays.toString(students));
        //сортування за номером групи за зростанням
        m.sortStudents(students, (Student st1, Student st2) -> st1.getGroup()-st2.getGroup());
        System.out.println("Sorted by group:\n"+Arrays.toString(students));
        //сортування за спаданням середнього балу
        m.sortStudents(students, (Student st1, Student st2) -> {if(st2.getAveragePoint()>st1.getAveragePoint()) return 1; else return -1;});
        System.out.println("Sorted by Average point in descending order:\n"+Arrays.toString(students));

        //task 5(37)
        //тут може бути згенеровано 3 виключення : ArithmeticException (ділення на 0), ArrayIndexOutOfBoundsException
        //(вихід за межі масива), і власноруч згенероване за допомогою ключового слова throw
        //перехоплюється 3 виключення: ArithmeticException, RuntimeException, Exception, при чому перше є нащадком другого,
        //а друге - нащадком третього.
        //перший catch-блок перехоплює ділення на нуль, якщо воно не відбувається. Другий - вихід за межі масиву
        //(ArrayIndexOutOfBoundsException, яке є нащадком RuntimeException, вказаного в блоці catch). Третій перехоплює
        //будь-які виключення-нащадки класу Exception (тобто якби не було перших двох catch-ів, він міг би перехопити і
        // ділення на 0, і вихід за межі масиву). Якби не було першого catch-блоку, то виключення ділення на 0 міг би
        //перехопити другий catch, адже ArithmeticException є нащадком RuntimeException

        //Таким чином catch-блоки перехоплюють вказані виключення або їхніх нащадків, тому не можна вказати catch-блок,
        //який перехоплює виключення похідного класу після catch-блоку, який перехоплює виключення базового класу
        try{
            Scanner in = new Scanner(System.in);
            System.out.print("Input a >>> ");
            int a=in.nextInt();
            System.out.print("Input b >>> ");
            int b=in.nextInt();
            System.out.println(a/b);
            int[] array = new int[10];
            array[a]=10;
            throw new Exception();
        }
        catch (ArithmeticException e)
        {
           System.out.println(e);
        }
        catch (RuntimeException e)
        {
            System.out.println("RuntimeException");
        }
        catch (Exception e)
        {
            System.out.println("Exception");
        }
    }

    public void sortStudents(Student[] Students, Comparator<Student> funcCompare)
    {//метод для сортування масиву студентів за заданим критерієм бульбашковим методом
        //funcCompare - функція для порівняння об'єктів. За допомогою неї можна задати критерій і порядок сортування
        Student tmp;
        for(int i=Students.length-1; i>=0; i--){
            for(int j=0; j<i; j++){
                if(funcCompare.compare(Students[j], Students[j+1])>0){
                    tmp=Students[j];
                    Students[j]=Students[j+1];
                    Students[j+1]=tmp;
                }
            }
        }
    }
}



