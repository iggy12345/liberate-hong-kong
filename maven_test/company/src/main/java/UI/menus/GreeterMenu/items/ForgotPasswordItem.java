package UI.menus.GreeterMenu.items;

import UI.AMenu;
import UI.AMenuItem;
import UI.controller.ITermController;
import UI.menus.ForgotPasswordMenu.ForgotPasswordMenu;
import company.Controller.EmployeeController;

public class ForgotPasswordItem extends AMenuItem {
    protected EmployeeController employeeController;

    public ForgotPasswordItem(ITermController parent, EmployeeController employeeController)
    {
        super(parent);
        this.employeeController = employeeController;
    }

    @Override
    public AMenu activate()
    {
        return new ForgotPasswordMenu(parent, this.employeeController);
    }

    @Override
    public String toString() { return "Forgot Password"; }
}