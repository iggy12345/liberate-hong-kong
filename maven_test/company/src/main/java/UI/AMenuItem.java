package UI;

import UI.IMenuItem;

public abstract class AMenuItem implements IMenuItem {
    
    @Override
    public boolean is_available() { return true; }

    @Override
    public String toString()
    {
        return "No name was supplied.";
    }

}