package com.mageflow.beanstalkd.interfaces;

import java.io.Serializable;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.resource.Referenceable;
import javax.resource.ResourceException;

@Remote
@Local
public interface BeanstalkdConnectionFactory extends Serializable, Referenceable {

    public BeanstalkdConnection getConnection() throws ResourceException;

}
