/*
 *  ReportServer
 *  Copyright (c) 2007 - 2020 InfoFabrik GmbH
 *  http://reportserver.net/
 *
 *
 * This file is part of ReportServer.
 *
 * ReportServer is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
 
 
package org.saiku.repository;

import org.saiku.datasources.datasource.SaikuDatasource;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * DataSource Object
 */

@XmlRootElement
public class DataSource {

  private String advanced;
  private String securitymapping;
  private String securitytype;
  private String securityenabled;
  private String encryptpassword;
    private String id;
    private String type;
    private String name;
    private String driver;
    private String location;
    private String username;
    private String password;
    private String path;
    private String schema;
  private String propertyKey;

  public DataSource(SaikuDatasource datasource) {
        this.type = datasource.getType().toString();
        this.name = datasource.getName();
        this.driver = datasource.getProperties().getProperty("driver");
        this.location = datasource.getProperties().getProperty("location");
        this.username = datasource.getProperties().getProperty("username");
        this.password = datasource.getProperties().getProperty("password");
        this.id = datasource.getProperties().getProperty("id");
        this.encryptpassword = datasource.getProperties().getProperty("encrypt.password");
        if(datasource.getProperties().containsKey("security.enabled")){
          this.securityenabled = datasource.getProperties().getProperty("security.enabled");
        } else if(datasource.getProperties().containsKey("security.type") &&
                  datasource.getProperties().getProperty("security.type")!=null){
          this.securityenabled = "true";
        }
        else {
          this.securityenabled = "false";
        }
        this.securitytype = datasource.getProperties().getProperty("security.type");
        this.securitymapping = datasource.getProperties().getProperty("security.mapping");
        this.advanced = datasource.getProperties().getProperty("advanced");
        if(datasource.getProperties().containsKey("schema")) {
          this.schema = datasource.getProperties().getProperty("schema");
        }

        if(datasource.getProperties().containsKey("propertyKey")){
          this.propertyKey = datasource.getProperties().getProperty("propertyKey");
        }

    }

    public DataSource(){

    }
    public String getPassword() {
        return password;
    }

    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    @XmlElement
    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    @XmlElement
    public void setLocation(String location) {
        this.location = location;
    }

    public String getDriver() {
        return driver;
    }

    @XmlElement
    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getName() {
        return name;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    @XmlElement
    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    @XmlElement
    public void setId(String id) {
        this.id = id;
    }

    @XmlElement
    public void setPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    @XmlElement
    public String getEncryptpassword() {
        return encryptpassword;
    }

    public void setEncryptpassword(String encryptpassword) {
        this.encryptpassword = encryptpassword;
    }

  @XmlElement
  public String getSecuritymapping() {
    return securitymapping;
  }

  public void setSecuritymapping(String securitymapping) {
    this.securitymapping = securitymapping;
  }

  @XmlElement
  public String getSecuritytype() {
    return securitytype;
  }

  public void setSecuritytype(String securitytype) {
    this.securitytype = securitytype;
  }

  @XmlElement
  public String getSecurityenabled() {
    return securityenabled;
  }

  public void setSecurityenabled(String securityenabled) {
    this.securityenabled = securityenabled;
  }

  @XmlElement
  public String getAdvanced() {
    return advanced;
  }

  public void setAdvanced(String advanced) {
    this.advanced = advanced;
  }

  public String getSchema() {
    return schema;
  }

  public void setSchema(String schema) {
    this.schema = schema;
  }


  public void setPropertyKey(String propertyKey) {
    this.propertyKey = propertyKey;
  }

  public String getPropertyKey() {
    return propertyKey;
  }
}
