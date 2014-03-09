/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mageflow.beanstalkd;

import com.surftools.BeanstalkClient.Client;
import com.surftools.BeanstalkClientImpl.ClientImpl;
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
 * @author Sven Varkel <sven.varkel@gmail.com>
 */
public class BeanstalkdManagedConnection extends ClientImpl implements javax.resource.spi.ManagedConnection, Client {

    private static final Logger LOG = Logger.getLogger(BeanstalkdManagedConnection.class);

    private BeanstalkdManagedConnectionFactory mcf;

    private PrintWriter logWriter;

    private List<ConnectionEventListener> listeners;

    private Object connection;

    public BeanstalkdManagedConnection(BeanstalkdManagedConnectionFactory mcf) {
        this.mcf = mcf;
        this.logWriter = null;
        this.listeners = new ArrayList<>(1);
        this.connection = null;
    }

    @Override
    public Object getConnection(Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
        connection = new BeanstalkdConnectionImpl(this, mcf);
        return connection;
    }

    @Override
    public void destroy() throws ResourceException {
        this.connection = null;
    }

    @Override
    public void cleanup() throws ResourceException {
//        if (this.connection != null && ((BeanstalkdConnectionImpl) this.connection).getSocket() != null) {
//            int hash = ((BeanstalkdConnectionImpl) this.connection).getSocket().hashCode();
//            LOG.debug("Closed connection " + hash);
//            ((BeanstalkdConnectionImpl) this.connection).close();
//        }
//        this.connection = null;
//        ((BeanstalkdConnectionImpl) this.connection).close();

    }

    @Override
    public void associateConnection(Object connection) throws ResourceException {
        this.connection = connection;

    }

    @Override
    public void addConnectionEventListener(ConnectionEventListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener is null");
        }
        listeners.add(listener);
    }

    @Override
    public void removeConnectionEventListener(ConnectionEventListener listener) {
        if (listener == null) {
            throw new IllegalArgumentException("Listener is null");
        }
        listeners.remove(listener);
    }

    @Override
    public XAResource getXAResource() throws ResourceException {
        throw new NotSupportedException("getXAResource not supported");
    }

    @Override
    public LocalTransaction getLocalTransaction() throws ResourceException {
        throw new NotSupportedException("Local transactions not supported");
    }

    @Override
    public ManagedConnectionMetaData getMetaData() throws ResourceException {
        return new BeanstalkdManagedConnectionMetaData();
    }

    @Override
    public void setLogWriter(PrintWriter out) throws ResourceException {
        logWriter = out;
    }

    @Override
    public PrintWriter getLogWriter() throws ResourceException {
        return logWriter;
    }

    public void closeHandle(BeanstalkdConnectionImpl handle) {
//        try {
//            if (handle.getSocket() instanceof Socket) {
//                handle.getSocket().close();
//            }
//        } catch (IOException ex) {
//            LOG.error("Error while closing connection", ex);
//        }
        ConnectionEvent event = new ConnectionEvent(this, ConnectionEvent.CONNECTION_CLOSED);
        event.setConnectionHandle(handle);
        for (ConnectionEventListener cel : listeners) {
            cel.connectionClosed(event);
        }
    }

}
