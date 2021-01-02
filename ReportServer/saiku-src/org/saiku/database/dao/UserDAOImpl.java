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
 
 
package org.saiku.database.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.saiku.database.dao.UserDAO;
import org.saiku.database.dto.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bugg on 01/05/14.
 */
public class UserDAOImpl implements UserDAO {

  private SessionFactory sessionFactory;

  private Session openSession() {
    return sessionFactory.getCurrentSession();
  }

  public User getUser(String login) {
    List<User> userList = new ArrayList<>();
    Query query = null;//openSession().createQuery("from User u where u.login = :login");
    query.setParameter("login", login);
    userList = query.list();
    if (userList.size() > 0)
      return userList.get(0);
    else
      return null;
  }

}
