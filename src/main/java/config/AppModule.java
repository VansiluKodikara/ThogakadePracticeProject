package config;

import com.google.inject.AbstractModule;
import repository.customer.CustomerRepository;
import repository.customer.impl.CustomerRepositoryImpl;
import service.custom.impl.CustomerService;
import service.custom.impl.CustomerServiceImpl;

public class AppModule extends AbstractModule {
    @Override
    protected void configure(){
        bind(CustomerService.class).to(CustomerServiceImpl.class);
        bind(CustomerRepository.class).to(CustomerRepositoryImpl.class);
    }
}
