package company.Entity.Interface;

import company.Entity.Interface.IEmployee;
import company.Entity.Interface.IPerson;

public interface IHRManager {
    IEmployee Hire(IPerson p);
}