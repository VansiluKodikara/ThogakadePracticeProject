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

    public <Type extends SuperService>Type getServiceType(ServiceType serviceType){
        switch(serviceType){
            case CUSTOMER:return (Type) new CustomerServiceImpl();
            case ITEM:return (Type) new ItemServiceImpl();
            case ORDER:return (Type) new OrderServiceImpl();
        }
        return null;
    }
}
