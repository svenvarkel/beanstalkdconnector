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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.resource.NotSupportedException;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionEvent;
import javax.resource.spi.ConnectionEventListener;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.LocalTransaction;
import javax.resource.spi.ManagedConnectionMetaData;
import javax.security.auth.Subject;
import javax.transaction.xa.XAResource;
import org.apache.log4j.Logger;

/**
 *
 * @author Sven Varkel <sven@mageflow.com>
 */
public class BeanstalkdManagedConnection implements javax.resource.spi.ManagedConnection {

    private static final Logger LOG = Logger.getLogger(BeanstalkdManagedConnection.class);

    private final BeanstalkdManagedConnectionFactory mcf;

    private PrintWriter logWriter;

    private final List<ConnectionEventListener> listeners;

    private Object connection;

    /**
     *
     * @param mcf
     */
    public BeanstalkdManagedConnection(BeanstalkdManagedConnectionFactory mcf) {
        this.mcf = mcf;
        this.logWriter = null;
        this.listeners = new ArrayList<>(1);
        this.connection = null;
        LOG.debug(String.format("Created new managed beanstalkd connection to %s:%s", mcf.getHostname(), mcf.getPort()));
    }

    /**
     *
     * @param subject
     * @param cxRequestInfo
     * @return
     * @throws ResourceException
     */
    @Override
    public Object getConnection(Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
        LOG.debug(String.format("Connecting to %s:%s", mcf.getHostname(), mcf.getPort()));
        connection = new BeanstalkdConnectionImpl(this, mcf);
        return connection;
    }

    /**
     *
     * @throws ResourceException
     */
    @Override
    public void destroy() throws ResourceException {
        if (connection != null) {
            ((BeanstalkdConnection) connection).close();
            ((BeanstalkdConnection) connection).setManagedConnection(null);
            LOG.debug("Closed connection #" + ((BeanstalkdConnectionImpl) connection).getConnectionId());
            connection = null;
        }
    }

    /**
     *
     * @throws ResourceException
     */
    @Override
    public void cleanup() throws ResourceException {
//        ((BeanstalkdConnectionImpl) this.connection).quit();
    }

    /**
     *
     * @param connection
     * @throws ResourceException
     */
    @Override
    public void associateConnection(Object connection) throws ResourceException {
        this.connection = connection;

    }

    /**
     *
     * @param listener
     */
    @Override
    public void addConnectionEventListener(ConnectionEventListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener is null");
        }
        listeners.add(listener);
    }

    /**
     *
     * @param listener
     */
    @Override
    public void removeConnectionEventListener(ConnectionEventListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener is null");
        }
        listeners.remove(listener);
    }

    /**
     *
     * @return @throws ResourceException
     */
    @Override
    public XAResource getXAResource() throws ResourceException {
        throw new NotSupportedException("getXAResource not supported");
    }

    /**
     *
     * @return @throws ResourceException
     */
    @Override
    public LocalTransaction getLocalTransaction() throws ResourceException {
        throw new NotSupportedException("Local transactions not supported");
    }

    /**
     *
     * @return @throws ResourceException
     */
    @Override
    public ManagedConnectionMetaData getMetaData() throws ResourceException {
        return new BeanstalkdManagedConnectionMetaData();
    }

    /**
     *
     * @param out
     * @throws ResourceException
     */
    @Override
    public void setLogWriter(PrintWriter out) throws ResourceException {
        logWriter = out;
    }

    /**
     *
     * @return @throws ResourceException
     */
    @Override
    public PrintWriter getLogWriter() throws ResourceException {
        return logWriter;
    }

    /**
     *
     * @param handle
     */
    public void closeHandle(BeanstalkdConnectionImpl handle) {
        try {
            ConnectionEvent event = new ConnectionEvent(this, ConnectionEvent.CONNECTION_CLOSED);
            event.setConnectionHandle(handle);
            for (ConnectionEventListener cel : listeners) {
                cel.connectionClosed(event);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace(System.err);
            LOG.error("Error while closing connection", ex);
        }
    }

}
