package com.younchen.chat.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.younchen.chat.App;
import com.younchen.chat.dao.DaoMaster;
import com.younchen.chat.dao.UserDao;
import com.younchen.chat.entity.User;
import com.younchen.chat.thread.ThreadPool;
import com.younchen.utils.ThreadUtils;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by Administrator on 2016/1/3.
 */
public class UserModel {

    private static UserModel userModel;

    private Context context;
    private DaoMaster.DevOpenHelper helper;

    private DaoMaster daoMaster;
    private SQLiteDatabase db;
    private UserDao userDao;

    public UserModel(Context context) {
        this.context = context;
        helper = new DaoMaster.DevOpenHelper(context, "moyiza-db", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        userDao = daoMaster.newSession().getUserDao();
    }

    public static UserModel getInstance() {
        if (userModel == null) {
            synchronized (UserModel.class){
                userModel = new UserModel(App.getIns());
            }
        }
        return userModel;
    }

    public void saveUser(User user) {
        userDao.insert(user);
    }

    public void updateUser(User user) {
        userDao.update(user);
    }


    public void saveUsers(List<User> users) {
        userDao.insertInTx(users);
    }


    public User getUser(long userId) {
        QueryBuilder<User> qb = userDao.queryBuilder();
        qb.where(UserDao.Properties.Id.eq(userId));
        List<User> list = qb.list();
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public void getUser(final long userId, final UserLoadCallBack callBack) {
        ThreadPool.getInstance().execute(new Runnable() {
            @Override
            public void run() {
                final User user = getUser(userId);
                ThreadUtils.postOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callBack.onUserLoaded(user);
                    }
                });
            }
        });
    }


    public void deleteUser(User userNew) {
        userDao.delete(userNew);
    }

    static interface UserLoadCallBack {
        public void onUserLoaded(User user);
    }

    static class UserListLoadCallBack {
//        public void onUserLoaded(List<User> user);
    }
}
