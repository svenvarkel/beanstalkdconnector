/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mageflow.beanstalkd;

import javax.resource.ResourceException;
import javax.resource.spi.ManagedConnectionMetaData;

/**
 *
 * @author Sven Varkel <sven.varkel@gmail.com>
 */
public class BeanstalkdManagedConnectionMetaData implements ManagedConnectionMetaData {

    @Override
    public String getEISProductName() throws ResourceException {
        return "BeanstalkdConnector";
    }

    @Override
    public String getEISProductVersion() throws ResourceException {
        return "1.0";
    }

    @Override
    public int getMaxConnections() throws ResourceException {
        return 0;
    }

    @Override
    public String getUserName() throws ResourceException {
        return "N/A";
    }

}
