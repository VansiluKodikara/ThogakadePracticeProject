package repository;

import repository.customer.impl.CustomerRepositoryImpl;
import repository.customer.impl.ItemRepositoryImpl;
import repository.customer.impl.OrderRepositoryImpl;
import util.RepositoryType;

public class RepositoryFactory {
    private static RepositoryFactory instance;
    private RepositoryFactory(){}

    public static RepositoryFactory getInstance() {
        return instance==null?instance=new RepositoryFactory():instance;
    }

    public <T extends SuperRepository>T getRepositoryType(RepositoryType repositoryType){
        switch (repositoryType){
            case CUSTOMER:return (T) new CustomerRepositoryImpl();
            case ITEM:return (T) new ItemRepositoryImpl();
            case ORDER:return (T) new OrderRepositoryImpl();
        }
        return null;

    }
}
