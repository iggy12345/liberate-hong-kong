package company.Entity.Abstract;

import java.util.UUID;

import org.json.JSONArray;

import company.Entity.Interface.IAccount;
import company.Entity.Interface.ISaveable;

public abstract class AAccount extends ASaveable implements IAccount
{
    protected long amount;

    public AAccount(){
        super();
        this.amount = 0L;
    }

    public static JSONArray loadAllBankAccounts(){
        JSONArray banks = ISaveable.loadAllAsJson("BankAccount");
        JSONArray credit = ISaveable.loadAllAsJson("CreditAccount");
        return ISaveable.add(banks, credit);
    }

    @Override
    public long getAmount()
    {
        return amount;
    }

    @Override
    public UUID setAmount(long amount)
    {
        this.amount = amount;
        return this.save();
    }
}
