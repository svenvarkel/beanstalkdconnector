/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mageflow.beanstalkd.interfaces;

import com.surftools.BeanstalkClient.Client;
import java.net.Socket;
import javax.ejb.Local;
import javax.ejb.Remote;

/**
 *
 * @author Sven Varkel <sven.varkel@gmail.com>
 */
@Local
@Remote
public interface BeanstalkdConnection extends Client, AutoCloseable {

    @Override
    public void close();

    public Socket getSocket();

    public void setSocket(Socket socket);

    public Integer getConnectionId();

}
