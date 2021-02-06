package Lab1;

import java.util.Arrays;

public class Main {

    //task 11 Створіть метод, який знаходить суму цифр будь якого числа int.
    public int sumOfDigits(int x){
        if(x<0)
            x=-x;
        int sum = 0;
        while (x>=10)
        {
            sum+=x%10;
            x/=10;
        }
        sum+=x;
        return sum;
    }
    //task 30 Створіть метод, який приймає будь яку кількість елементів int[] та повертає масив,
    // який складається тільки з елементів, які є в одному екземплярі серед усіх масивів.
    int[] mergeArrays(int[]... arrays)
    {
        int length = 0;
        for (var array : arrays) {
            length += array.length;
        }
        int[] all = new int[length];
        int i = 0;
        for (var array : arrays) {
            for (var elem : array) {
                all[i] = elem;
                i++;
            }
        }
        return all;
    }
    int[] uniqueElements(int[] arr)
    {
        int[] result = new int[arr.length];
        int returnCount = 0;
        boolean isUnique;
        for (int i = 0; i < arr.length; i++) {
            isUnique = true;
            for (int j = 0; j < arr.length; j++) {
                if (j != i && arr[j] == arr[i]) {
                    isUnique = false;
                    break;
                }
            }
            if (isUnique) {
                result[returnCount] = arr[i];
                returnCount++;
            }
        }
        result = Arrays.copyOf(result, returnCount);
        return result;
    }
    public int[] uniqueElements(int[]... arrays) {
        return uniqueElements(mergeArrays(arrays));
    }

    //task 3 Створіть метод, який дозволяє вставляти в будь яку позицію масиву будь яке число.
    // Метод повинен повертати новий масив
    public int[] insert(int[] arr, int pos, int num)
    {
        if(pos<0||pos>=arr.length)
        {
            return arr;
        }
        int[] result = new int[arr.length+1];
        for(int i=0; i<pos; i++)
        {
            result[i]=arr[i];
        }
        result[pos] = num;
        for(int i=pos; i<arr.length; i++){
            result[i+1]=arr[i];
        }
        return result;
    }

    //task 2 Розробіть метод, який виводить на екран спільні елементи будь яких двох 2-вимірних масивів int[][].
    // Результат роботи методу не повинен залежати від того чи є масив регулярним
    public void commonElements(int[][] arr1, int[][] arr2){

        for(int i=0; i<arr1.length; i++){
            for (int j=0; j<arr1[i].length; j++){
                for (int k = 0; k<arr2.length; k++){
                    for(int l=0; l<arr2[k].length; l++){
                        if(arr1[i][j]==arr2[k][l]){
                            System.out.print(arr1[i][j]+" ");
                        }
                    }
                }
            }
        }
    }

    //task 13 Створіть метод, який сортує будь який масив int[] методом вибору
    public void selectionSort(int[] arr)
    {
        int p=0;
        int min;
        int tmp;
        for(p=0; p<arr.length; p++)
        {
            min = p;
            for(int i=p; i<arr.length; i++){
                if(arr[i]<arr[min]){
                    min = i;
                }
            }
            if(min!=p){
                tmp=arr[min];
                arr[min]=arr[p];
                arr[p]=tmp;
            }
        }
    }
    int maxSumOfDigits (int[] arr)
    {
        int indexOfResult=0;
        for (int i=0; i<arr.length; i++){
            if(sumOfDigits(arr[i])>sumOfDigits(arr[indexOfResult])){
                indexOfResult=i;
            }
        }
        return arr[indexOfResult];
    }
    public static void main(String[] args) {
        Main m = new Main();
        System.out.println("sum of digits 123456789: "+m.sumOfDigits(123456789));
        System.out.println("\ntask 30: ");
        int[] arr = m.uniqueElements(new int[]{1,2,6,3,1,8},new int[]{2,3,4,4,5,1}, new int[]{3,5,1,7,8,9,0});
        for (var item:
                arr) {
            System.out.print(item+" ");
        }
        System.out.println("\ntask 3: ");
        int[] arr2 = {1,2,3,4,5};
        int[] arr3 = m.insert(arr2,3,9);
        for (var item:
                arr3) {
            System.out.print(item+" ");
        }
        System.out.print("\ntask2: ");
        m.commonElements(new int[][] {{3,2,1},{8,7}}, new int[][]{{2,6},{3,7,9,8,12}});
        System.out.print("\ntask13: ");
        arr3 = new int[]{5,9,3,1,7,6,8,2,0,4,6,8,2,3,7,5,3,1,9,7};
        m.selectionSort(arr3);
        for (var item:
                arr3) {
            System.out.print(item+" ");
        }
        System.out.println();
        System.out.println(m.maxSumOfDigits(new int[] {265,321,712,525,99,957}));
    }
































}