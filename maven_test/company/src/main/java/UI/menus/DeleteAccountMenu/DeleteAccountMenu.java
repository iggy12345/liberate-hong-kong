package UI.menus.DeleteAccountMenu;

import java.math.BigDecimal;
import java.util.Scanner;

import UI.AMenu;
import UI.AnsiUtil;
import UI.IMenuItem;
import UI.UIUtil;
import UI.global_menu_items.ExitItem;
import company.Controller.AccountController;
import company.Entity.Account;
import company.exceptions.BankLockedException;

public class DeleteAccountMenu extends AMenu {

    private String accountId = new String();

    @Override
    public String get_display_string() {
        String result = new String();

        String s = "";
        if (accountId.isEmpty()) {
            s = "Account id: ";
        }

        prompt = s;

        return result;
    }

    @Override
    public IMenuItem prompt() {
        Scanner sc = new Scanner(System.in);

        if (!is_valid)
            display();

        try {
            AnsiUtil.append_display_string(get_x_coord(), prompt);
            accountId = UIUtil.get_input(sc, accountId, "", (String s) -> {
                return true;
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        get_display_string();

        Account a;
        //try to get the account
        try {
            a = AccountController.getInstance().getAccount(accountId);
        }
        catch (BankLockedException e) {
            toast("Cannot get the bank account because the bank is locked.");
            return new ExitItem();
        }

        if (a == null) {
            toast("No account was found with the given account id.");
        } else {
            BigDecimal amt;
            try {
                amt = AccountController.getInstance().deleteAccount(a);
            }
            catch (BankLockedException e) {
                toast("Cannot delete the bank account because the bank is locked.");
                return new ExitItem();
            }

            toast("The account with the given account id has been closed. The balance remaining on the account was " + amt.toString());
        }

        return new ExitItem();
    }
}