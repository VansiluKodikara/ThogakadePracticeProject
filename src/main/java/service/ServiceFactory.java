package service;

import service.custom.impl.CustomerServiceImpl;
import service.custom.impl.ItemServiceImpl;
import service.custom.impl.OrderServiceImpl;
import util.ServiceType;

public class ServiceFactory {
    private static ServiceFactory instance;

    private ServiceFactory(){}

    public static ServiceFactory getInstance(){
        return instance==null?instance=new ServiceFactory():instance;
    }

    public <T extends SuperService>T getServiceType(ServiceType serviceType){
        switch(serviceType){
            case CUSTOMER:return (T) new CustomerServiceImpl();
            case ITEM:return (T) new ItemServiceImpl();
            case ORDER:return (T) new OrderServiceImpl();
        }
        return null;
    }
}
