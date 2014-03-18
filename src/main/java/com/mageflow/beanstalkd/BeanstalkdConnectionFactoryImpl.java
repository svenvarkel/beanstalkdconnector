/*
 * The MIT License
 *
 * Copyright 2014 Sven Varkel <sven@mageflow.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.mageflow.beanstalkd;

import com.mageflow.beanstalkd.interfaces.BeanstalkdConnection;
import com.mageflow.beanstalkd.interfaces.BeanstalkdConnectionFactory;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;

/**
 *
 * @author sven
 */
public class BeanstalkdConnectionFactoryImpl implements BeanstalkdConnectionFactory {

    private static final long serialVersionUID = 1L;

    private Reference reference;

    private final BeanstalkdManagedConnectionFactory mcf;

    private final ConnectionManager connectionManager;

    /**
     *
     * @param mcf
     * @param cxManager
     */
    public BeanstalkdConnectionFactoryImpl(BeanstalkdManagedConnectionFactory mcf,
            ConnectionManager cxManager) {
        this.mcf = mcf;
        this.connectionManager = cxManager;
    }

    /**
     *
     * @return
     * @throws ResourceException
     */
    @Override
    public BeanstalkdConnection getConnection() throws ResourceException {

        return (BeanstalkdConnection) connectionManager.allocateConnection(mcf, null);

    }

    @Override
    public Reference getReference() throws NamingException {
        return reference;
    }

    /**
     *
     * @param reference
     */
    @Override
    public void setReference(Reference reference) {
        this.reference = reference;
    }
}
