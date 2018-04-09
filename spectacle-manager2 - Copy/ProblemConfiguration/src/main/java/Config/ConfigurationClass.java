package Config;

import Domain.Employee;
import Domain.Participation;
import Domain.Sale;
import Network.ClientProxy;
import Network.IClientProxy;
import Repository.*;
import Service.*;
import Utils.Validators.EmployeeValidator;
import Utils.Validators.ParticipationValidator;
import Utils.Validators.Validator;
import Utils.Validators.SaleValidator;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.sql.SQLException;

@Configuration
public class ConfigurationClass {

    @Bean
    public Validator<Employee> angajatValidator(){
        return new EmployeeValidator();
    }

    @Bean
    public Validator<Participation> participareValidator(){
        return new ParticipationValidator();
    }

    @Bean
    public Validator<Sale> vanzareValidator(){
        return new SaleValidator();
    }

    @Bean
    public IEmployeeRepository employeeRepository() throws SQLException, IOException, ClassNotFoundException {
        return new EmployeeRepository("/properties.prop");
    }

    @Bean
    public IParticipationRepository participationRepository() throws SQLException, IOException, ClassNotFoundException {
        return new ParticipationRepository("/properties.prop");
    }

    @Bean
    public ISaleRepository saleRepository() throws SQLException, IOException, ClassNotFoundException {

        return new SaleRepository("/properties.prop");
    }

    @Bean
    public ILoginService loginService() throws SQLException, IOException, ClassNotFoundException {
        return new LoginService(employeeRepository(),angajatValidator());
    }


    @Bean
    public IParticipationService participareService() throws SQLException, IOException, ClassNotFoundException {
        return new ParticipationService(participationRepository(),participareValidator());
    }

    @Bean
    public ISaleService vanzariService() throws SQLException, IOException, ClassNotFoundException {
        return new SaleService(saleRepository(),vanzareValidator());
    }

    @Bean
    public IClientProxy clientProxy() throws SQLException, IOException, ClassNotFoundException {
        return new ClientProxy("/properties.prop");
    }
}
