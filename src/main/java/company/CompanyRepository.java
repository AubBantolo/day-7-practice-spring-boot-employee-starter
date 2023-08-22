package company;

import com.thoughtworks.springbootemployee.controller.Employee;
import com.thoughtworks.springbootemployee.controller.EmployeeNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CompanyRepository {

    private static final List<Company> companies = new ArrayList<>();

    static {
        companies.add(new Company(1L, "Spring"));
        companies.add(new Company(2L, "Autumn"));
        companies.add(new Company(3L, "Summer"));
        companies.add(new Company(4L, "Winter"));
    }


}


