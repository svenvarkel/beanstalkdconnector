package com.mageflow.beanstalkd;

import com.mageflow.beanstalkd.interfaces.BeanstalkdConnection;
import com.mageflow.beanstalkd.interfaces.BeanstalkdConnectionFactory;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;

public class BeanstalkdConnectionFactoryImpl implements BeanstalkdConnectionFactory {

    private static final long serialVersionUID = 1L;

    private Reference reference;

    private final BeanstalkdManagedConnectionFactory mcf;

    private final ConnectionManager connectionManager;

    public BeanstalkdConnectionFactoryImpl(BeanstalkdManagedConnectionFactory mcf,
            ConnectionManager cxManager) {
        this.mcf = mcf;
        this.connectionManager = cxManager;
    }

    @Override
    public BeanstalkdConnection getConnection() throws ResourceException {

        return (BeanstalkdConnection) connectionManager.allocateConnection(mcf, null);

    }

    @Override
    public Reference getReference() throws NamingException {
        return reference;
    }

    @Override
    public void setReference(Reference reference) {
        this.reference = reference;
    }
}
