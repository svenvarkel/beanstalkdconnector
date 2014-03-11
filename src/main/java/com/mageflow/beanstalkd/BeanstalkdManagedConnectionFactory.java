/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mageflow.beanstalkd;

import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Set;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import javax.resource.spi.ConnectionRequestInfo;
import javax.resource.spi.ManagedConnection;
import javax.resource.spi.ManagedConnectionFactory;
import javax.resource.spi.ResourceAdapter;
import javax.resource.spi.ResourceAdapterAssociation;
import javax.security.auth.Subject;
import org.apache.log4j.Logger;

/**
 *
 * @author Sven Varkel <sven.varkel@gmail.com>
 */
public class BeanstalkdManagedConnectionFactory implements ManagedConnectionFactory, ResourceAdapterAssociation {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = Logger.getLogger(BeanstalkdManagedConnectionFactory.class);

    private ResourceAdapter resourceAdapter;

    private PrintWriter logWriter;

    private String hostname = "queue01.mageflow";
    private Integer port = 11300;
    private Boolean useBlockingIO = false;
    private Boolean useUniqueConnectionPerThread = false;
    private String userName;
    private String password;
    private String user;

    public BeanstalkdManagedConnectionFactory() {
        this.resourceAdapter = null;
        this.logWriter = null;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public Object createConnectionFactory(ConnectionManager connectionManager) throws ResourceException {
        return new BeanstalkdConnectionFactoryImpl(this, connectionManager);
    }

    @Override
    public Object createConnectionFactory() throws ResourceException {
        throw new ResourceException("This resource adapter doesn't support non-managed environments");
    }

    @Override
    public ManagedConnection createManagedConnection(Subject subject, ConnectionRequestInfo cxRequestInfo) throws ResourceException {
        LOG.debug(subject.toString());
        LOG.info("Created managed beanstalkd connection");
        return new BeanstalkdManagedConnection(this);
    }

    @Override
    public ManagedConnection matchManagedConnections(Set connectionSet, Subject subject,
            ConnectionRequestInfo cxRequestInfo) throws ResourceException {

        ManagedConnection managedConnection = null;

        Iterator it = connectionSet.iterator();
        while (managedConnection == null && it.hasNext()) {
            ManagedConnection mc = (ManagedConnection) it.next();
            if (mc instanceof BeanstalkdManagedConnection) {
                BeanstalkdManagedConnection hwmc = (BeanstalkdManagedConnection) mc;
                managedConnection = hwmc;
            }
        }
        return managedConnection;
    }

    @Override
    public void setLogWriter(PrintWriter out) throws ResourceException {
        this.logWriter = out;
    }

    @Override
    public PrintWriter getLogWriter() throws ResourceException {
        return this.logWriter;
    }

    @Override
    public ResourceAdapter getResourceAdapter() {
        return this.resourceAdapter;
    }

    @Override
    public void setResourceAdapter(ResourceAdapter resourceAdapter) throws ResourceException {
        this.resourceAdapter = resourceAdapter;
    }

    @Override
    public int hashCode() {
        int result = 17;
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (!(other instanceof BeanstalkdManagedConnectionFactory)) {
            return false;
        }
        BeanstalkdManagedConnectionFactory obj = (BeanstalkdManagedConnectionFactory) other;
        boolean result = true;
        return result;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Boolean isUseBlockingIO() {
        return useBlockingIO;
    }

    public void setUseBlockingIO(Boolean useBlockingIO) {
        this.useBlockingIO = useBlockingIO;
    }

    public Boolean isUseUniqueConnectionPerThread() {
        return useUniqueConnectionPerThread;
    }

    public void setUseUniqueConnectionPerThread(Boolean useUniqueConnectionPerThread) {
        this.useUniqueConnectionPerThread = useUniqueConnectionPerThread;
    }

}
