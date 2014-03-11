/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mageflow.beanstalkd;

import com.mageflow.beanstalkd.interfaces.BeanstalkdConnection;
import com.surftools.BeanstalkClientImpl.ClientImpl;
import java.net.Socket;
import java.util.Random;
import org.apache.log4j.Logger;

/**
 *
 * @author Sven Varkel <sven.varkel@gmail.com>
 */
public class BeanstalkdConnectionImpl extends ClientImpl implements BeanstalkdConnection {

    /**
     * The logger
     */
    private static final Logger LOG = Logger.getLogger(BeanstalkdConnectionImpl.class);

    /**
     * ManagedConnection
     */
    private BeanstalkdManagedConnection mc;

    /**
     * ManagedConnectionFactory
     */
    private BeanstalkdManagedConnectionFactory mcf;
    Socket socket = null;
    private Integer connectionId = 0;

    public BeanstalkdConnectionImpl(BeanstalkdManagedConnection mc, BeanstalkdManagedConnectionFactory mcf) {
//        super(mcf.getHostname(), mcf.getPort(), mcf.isUseBlockingIO());
        super("10.0.2.4", 11300, false);
        this.setUniqueConnectionPerThread(mcf.isUseUniqueConnectionPerThread());
        this.mc = mc;
        this.mcf = mcf;
        Random r = new Random();
        connectionId = r.nextInt((9999 - 1000) + 1) + 1000;
        LOG.debug(String.format("Created connection #%s", connectionId));
    }

    @Override
    public void close() {
        mc.closeHandle(this);
        super.close();
    }

    @Override
    public Socket getSocket() {
        return this.socket;
    }

    @Override
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    @Override
    public Integer getConnectionId() {
        return connectionId;
    }

}
