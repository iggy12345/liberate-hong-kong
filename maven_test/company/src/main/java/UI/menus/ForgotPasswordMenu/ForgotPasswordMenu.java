package UI.menus.ForgotPasswordMenu;

import java.util.Scanner;
import java.util.UUID;

import UI.AlignmentType;
import UI.IMenuItem;
import UI.UIUtil;
import UI.controller.ITermController;
import UI.global_menu_items.ExitItem;
import UI.menus.LoginMenu.LoginMenu;
import company.Controller.EmployeeController;
import company.Entity.Employee;
import company.exceptions.EmployeeNotFoundException;

public class ForgotPasswordMenu extends LoginMenu {
    protected UUID user_id;
    protected String security_question = new String();
    protected String security_answer = new String();
    protected String confirm_password = new String();
    protected int iteration = 0;

    public ForgotPasswordMenu(ITermController parent)
    {
        super(parent);
    }

    @Override
    public String get_display_string()
    {
        String result = new String();

        // Add vertical padding
        int v_pad = get_y_coord(), h_pad = get_x_coord();
        for(int i = 0; i < v_pad; i++)
            result += "\n";

        String new_prompt = new String();
        for(int i = 0; i < h_pad; i++)
            new_prompt += " ";
        prompt = new_prompt + ((iteration == 0) ? "Username: " :
                (iteration == 1) ? "Security Question: " + security_question :
                        (iteration == 2) ? "New Password: " : "Confirm Password: ");
        if(iteration++ == 3)
            iteration = 0;
        return result;
    }

    @Override
    public IMenuItem prompt()
    {
        if(ForgotPasswordMenu.inputScanner == null)
            ForgotPasswordMenu.inputScanner = new Scanner(System.in);

        if(!is_valid)
            display();

        int h_pad = get_x_coord();
        String padding = new String();
        for(int i = 0; i < h_pad; i++)
            padding += " ";

        try { 
            EmployeeController employeeController = EmployeeController.getInstance();
            username = UIUtil.get_input(ForgotPasswordMenu.inputScanner, username, padding + "Username: ", (String s) -> { return true; }); 
            Employee employee = employeeController.findByUsername(username);

            security_question = employee.getQuestion() + '\n' + padding + "Answer? "; // Shouldn't be null, so no need to check
            try { security_answer = UIUtil.get_input(ForgotPasswordMenu.inputScanner, security_answer, 
                                                    padding + "SecurityQuestion: " + security_question, (String s) -> { return true; }); }
            catch(Exception e) { e.printStackTrace(); }

            if(!employee.getAnswer().equals(security_answer))
            {
                System.out.println(UIUtil.pad_string("Account has been locked down due to suspicious activity, please contact your system administrator.", 
                                                    parent.get_term_width(), AlignmentType.center));
                System.out.println(UIUtil.pad_string("Press RETURN to continue", 
                                                    parent.get_term_width(), AlignmentType.center));
                try { UIUtil.get_input(ForgotPasswordMenu.inputScanner, new String(), "", (String s) -> { return true; }); }
                catch(Exception e) { e.printStackTrace(); }

                return new ExitItem(parent);
            }

            do
            {
                try { password = UIUtil.get_input(ForgotPasswordMenu.inputScanner, password, padding + "New Password: ", (String s) -> { return true; }); }
                catch(Exception e) { e.printStackTrace(); }

                try { confirm_password = UIUtil.get_input(ForgotPasswordMenu.inputScanner, confirm_password, padding + "Confirm Password: ", (String s) -> { return true; }); }
                catch(Exception e) { e.printStackTrace(); }

                if(!password.equals(confirm_password))
                    System.out.println(UIUtil.pad_string("Those passwords don't match, try again.", parent.get_term_width(), AlignmentType.center));

            } while(!password.equals(confirm_password));

            employeeController.getEmployee(user_id).setPassword(password);

            System.out.println(UIUtil.pad_string("Account password changed successfully.", 
                                                    parent.get_term_width(), AlignmentType.center));
            System.out.println(UIUtil.pad_string("Press RETURN to continue", 
                                                    parent.get_term_width(), AlignmentType.center));
            try { UIUtil.get_input(ForgotPasswordMenu.inputScanner, new String(), "", (String s) -> { return true; }); }
            catch(Exception e) { e.printStackTrace(); }
            
        }
        catch(EmployeeNotFoundException e) {
            System.out.println(UIUtil.pad_string("That user does not exist, please contact your system administrator to create a new account.", 
                                                 parent.get_term_width(), AlignmentType.center));
            System.out.println(UIUtil.pad_string("Press RETURN to continue", 
                                                 parent.get_term_width(), AlignmentType.center));
            try { UIUtil.get_input(ForgotPasswordMenu.inputScanner, new String(), "", (String s) -> { return true; }); }
            catch(Exception e1) { e1.printStackTrace(); }

            return new ExitItem(parent);
        }
        catch(Exception e) { e.printStackTrace(); }

        return new ExitItem(parent);
    }
}