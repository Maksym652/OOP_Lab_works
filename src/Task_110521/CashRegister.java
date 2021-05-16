package Task_110521;

import java.util.Arrays;
import java.util.HashMap;

public class CashRegister {
    private CashRegisterState state = CashRegisterState.OPERATION_SUCCESSFUL;
    public CashRegisterState getState(){
        return state;
    }
    public HashMap<Float, Integer> giveRest(float price, float money, HashMap<Float, Integer> banknotes)
    {
        if(money<0||price<0){
            state = CashRegisterState.INCORRECT_DATA;
            return null;
        }
        if(money<price){
            state = CashRegisterState.NOT_ENOUGH_MONEY_PAID;
            return null;
        }
        HashMap<Float, Integer> result = new HashMap<>();
        float rest = money - price;
        if(rest%0.1!=0){
            rest = roundMoney(rest);
        }
        float[] nominals = getNominals(banknotes);
        for(int i=nominals.length-1; i>=0; i--){
            if(rest/nominals[i]>0.9999&&banknotes.get(nominals[i])>0)
            {
                if (result.get(nominals[i]) == null) {
                    result.put(nominals[i], 1);
                } else {
                    result.put(nominals[i], result.get(nominals[i]) + 1);
                }
                banknotes.put(nominals[i], banknotes.get(nominals[i])-1);
                rest -= nominals[i];
                i++;
            }
        }
        if(rest>0.1){
            state=CashRegisterState.NO_REQUIRED_BANKNOTE;
            return null;
        }
        state = CashRegisterState.OPERATION_SUCCESSFUL;
        return result;
    }
    public float[] getNominals(HashMap <Float, Integer> countsOfBanknotes)
    {
        float[] result = new float[countsOfBanknotes.size()];
        Object[] keysArray = countsOfBanknotes.keySet().toArray();
        for(int i=0; i<result.length; i++)
            result[i]=(float)keysArray[i];
        Arrays.sort(result);
        return result;
    }
    //згідно з законодавством України решта, не кратна 10 копійкам, округлюється за математичними правилами
    float roundMoney(float money){
        return (float)((money%0.1<0.05)?money-money%0.1:money-money%0.1+0.1);
    }

    public HashMap <Float, Integer> getBanknotesCount(float[] nominals, int[] counts)
    {
        if(nominals.length>counts.length)
        {
            counts = Arrays.copyOf(counts, nominals.length);
        }
        HashMap<Float, Integer> result = new HashMap<Float, Integer>();
        for(int i=0; i<nominals.length; i++){
            result.put(nominals[i], counts[i]);
        }
        return result;
    }
}

enum CashRegisterState {
    OPERATION_SUCCESSFUL("Операція пройшла успішно."),
    NOT_ENOUGH_MONEY_PAID("Внесених коштів не достатньо для оплати!"),
    NO_REQUIRED_BANKNOTE("В касі нема купюри, необхідної для видачі решти!"),
    INCORRECT_DATA("Некоректні вхідні дані!");
    private String message;
    CashRegisterState(String message){
        this.message=message;
    }
    public String getMessage(){
        return this.message;
    }
}