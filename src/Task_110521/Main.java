package Task_110521;
//11.05.21
//Створити метод, який приймає ціну, сплачену готівку і кількість купюр різних номіналів в касі
//метод має повертати решту у вигляді кількостей купюр (монет) різних номіналів

import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        CashRegister cr = new CashRegister();
        float[] hryvnia_nominals =  {0.1f, 0.5f, 1f,2f,5f,10f,20f,50f,100f,200f,500f,1000f};
        int[] counts = {50,50,40,40,40, 30, 30, 25,25,20,5,10};
        HashMap<Float, Integer> banknotes = cr.getBanknotesCount(hryvnia_nominals, counts);
        HashMap<Float, Integer> rest = cr.giveRest(1234.56f, 3000f, banknotes);
        if(cr.getState()!=CashRegisterState.OPERATION_SUCCESSFUL){
            System.out.println(cr.getState().getMessage());
            return;
        }
        for(int i=0; i<hryvnia_nominals.length; i++){
            if(rest.get(hryvnia_nominals[i])!=null)System.out.println(hryvnia_nominals[i]+ "₴ - " +rest.get(hryvnia_nominals[i]));
        }
    }
}