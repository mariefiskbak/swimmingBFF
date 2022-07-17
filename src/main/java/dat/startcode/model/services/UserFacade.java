package dat.startcode.model.services;

import dat.startcode.model.entities.User;
import dat.startcode.model.exceptions.DatabaseException;
import dat.startcode.model.persistence.ConnectionPool;
import dat.startcode.model.persistence.UserMapper;

import javax.xml.crypto.Data;

public class UserFacade
{
    public static User login(String email, String password, ConnectionPool connectionPool) throws DatabaseException
    {
        UserMapper userMapper = new UserMapper(connectionPool);
        return userMapper.login(email, password);
    }

    public static User createUser(String email, String phoneNo, String name, String role, int familyId, String password, ConnectionPool connectionPool) throws DatabaseException
    {
        UserMapper userMapper = new UserMapper(connectionPool);
        return userMapper.createUser(email, phoneNo, name, role, familyId, password);
    }
}
