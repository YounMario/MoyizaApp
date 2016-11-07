package com.younchen.chat.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import com.younchen.chat.entity.User;
import com.younchen.chat.entity.Friends;

import com.younchen.chat.dao.UserDao;
import com.younchen.chat.dao.FriendsDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig userDaoConfig;
    private final DaoConfig friendsDaoConfig;

    private final UserDao userDao;
    private final FriendsDao friendsDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        userDaoConfig = daoConfigMap.get(UserDao.class).clone();
        userDaoConfig.initIdentityScope(type);

        friendsDaoConfig = daoConfigMap.get(FriendsDao.class).clone();
        friendsDaoConfig.initIdentityScope(type);

        userDao = new UserDao(userDaoConfig, this);
        friendsDao = new FriendsDao(friendsDaoConfig, this);

        registerDao(User.class, userDao);
        registerDao(Friends.class, friendsDao);
    }
    
    public void clear() {
        userDaoConfig.getIdentityScope().clear();
        friendsDaoConfig.getIdentityScope().clear();
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public FriendsDao getFriendsDao() {
        return friendsDao;
    }

}