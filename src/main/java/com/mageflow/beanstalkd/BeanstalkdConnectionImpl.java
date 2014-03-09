/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mageflow.beanstalkd;

import com.mageflow.beanstalkd.interfaces.BeanstalkdConnection;
import com.surftools.BeanstalkClientImpl.ClientImpl;
import java.net.Socket;
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

    public BeanstalkdConnectionImpl(BeanstalkdManagedConnection mc, BeanstalkdManagedConnectionFactory mcf) {
        super(mcf.getHostname(), mcf.getPort(), mcf.isUseBlockingIO());
        this.setUniqueConnectionPerThread(mcf.isUseUniqueConnectionPerThread());

        this.mc = mc;
        this.mcf = mcf;
//        if (socket == null || socket.isClosed() || !socket.isConnected()) {
//            try {
//                socket = new Socket(mcf.getHostname(), mcf.getPort());
//                socket.setKeepAlive(false);
//                LOG.info(String.format("ID: %s; Created socket %s:%s", socket.hashCode(), mcf.getHostname(), mcf.getPort()));
//            } catch (IOException ex) {
//                LOG.error("Error while connecting to socket", ex);
//            }
//        }
    }

    @Override
    public void close() {
        mc.closeHandle(this);
    }

    @Override
    public Socket getSocket() {
        return this.socket;
    }

    @Override
    public void setSocket(Socket socket) {
        this.socket = socket;
    }

}
