package com.younchen.chat.model;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;

/**
 * @author younchen
 * @date 2015年11月6日
 * @description 通知中心，这里不是广播通知，而是观察者的模式去通知UI元素改变等。
 */
@SuppressLint("UseSparseArrays")
public class NotificationModel {

    private static int totalEvents = 1;

    public static final int updateMessage = totalEvents++;
    public static final int updateList = totalEvents++;
    public static final int clearUnread = totalEvents++;
    public static final int reciveImageResult = totalEvents++;
    public static final int reciveLocationResult = totalEvents++;

    final private HashMap<Integer, ArrayList<Object>> observers = new HashMap<Integer, ArrayList<Object>>();
    final private HashMap<Integer, Object> removeAfterBroadcast = new HashMap<Integer, Object>();
    final private HashMap<Integer, Object> addAfterBroadcast = new HashMap<Integer, Object>();

    private int broadcasting = 0;

    private static volatile NotificationModel Instance = null;

    public static NotificationModel getInstance() {
        NotificationModel localInstance = Instance;
        if (localInstance == null) {
            synchronized (NotificationModel.class) {
                localInstance = Instance;
                if (localInstance == null) {
                    Instance = localInstance = new NotificationModel();
                }
            }
        }
        return localInstance;
    }

    public void postNotificationName(int id, Object... args) {
        synchronized (observers) {
            broadcasting++;
            ArrayList<Object> objects = observers.get(id);
            if (objects != null) {
                for (Object obj : objects) {
                    ((NotificationCenterDelegate) obj).didReceivedNotification(
                            id, args);
                }
            }
            broadcasting--;
            if (broadcasting == 0) {
                if (!removeAfterBroadcast.isEmpty()) {
                    for (HashMap.Entry<Integer, Object> entry : removeAfterBroadcast
                            .entrySet()) {
                        removeObserver(entry.getValue(), entry.getKey());
                    }
                    removeAfterBroadcast.clear();
                }
                if (!addAfterBroadcast.isEmpty()) {
                    for (HashMap.Entry<Integer, Object> entry : addAfterBroadcast
                            .entrySet()) {
                        addObserver(entry.getValue(), entry.getKey());
                    }
                    addAfterBroadcast.clear();
                }
            }
        }
    }

    public void addObserver(Object observer, int id) {
        synchronized (observers) {
            if (broadcasting != 0) {
                addAfterBroadcast.put(id, observer);
                return;
            }
            ArrayList<Object> objects = observers.get(id);
            if (objects == null) {
                observers.put(id, (objects = new ArrayList<Object>()));
            }
            if (objects.contains(observer)) {
                return;
            }
            objects.add(observer);
        }
    }

    public void removeObserver(Object observer, int id) {
        synchronized (observers) {
            if (broadcasting != 0) {
                removeAfterBroadcast.put(id, observer);
                return;
            }
            ArrayList<Object> objects = observers.get(id);
            if (objects != null) {
                objects.remove(observer);
                if (objects.size() == 0) {
                    observers.remove(id);
                }
            }
        }
    }
}
