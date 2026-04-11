package config;

import com.google.inject.AbstractModule;
import repository.customer.CustomerRepository;
import repository.customer.ItemRepository;
import repository.customer.OrderRepository;
import repository.customer.impl.CustomerRepositoryImpl;
import repository.customer.impl.ItemRepositoryImpl;
import repository.customer.impl.OrderRepositoryImpl;
import service.custom.impl.*;

public class AppModule extends AbstractModule {
    @Override
    protected void configure(){
        bind(CustomerService.class).to(CustomerServiceImpl.class);
        bind(CustomerRepository.class).to(CustomerRepositoryImpl.class);
    }
}
