package com.lizikj.cron.dto;

import java.io.Serializable;
/**
 * Created by Michael.Huang on 2016/8/3.
 */
public class CronClientDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String name;

    private String host;

    private Integer port;

    private Byte status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}