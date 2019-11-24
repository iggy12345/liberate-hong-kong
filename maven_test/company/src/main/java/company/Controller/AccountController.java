package company.Controller;

import company.Controller.Abstract.AAccountController;

public class AccountController extends AAccountController{

    private static AccountController controllerInstance = null;
    
    /**
     * Make this controller a singleton
     * @return
     */
    public static AccountController getInstance(){
        if (controllerInstance == null) {
            createTable();
            controllerInstance = new AccountController();
        }
        return controllerInstance;
    }

    
    //TODO fix
    // public void accrueInterest()
    // {
    //     for(IAccount acct : Vault.getInstance().getAccounts().values())
    //         if(ICreditAccount.class.isAssignableFrom(acct.getClass()))
    //             ((ICreditAccount)acct).accrueInterest();
    // }
}
