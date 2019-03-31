package mao.com.mao_wanandroid_client.application;

import android.app.Activity;
import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import mao.com.mao_wanandroid_client.core.dao.DaoMaster;
import mao.com.mao_wanandroid_client.core.dao.DaoSession;

/**
 * @author maoqitian
 * @Description
 * @Time 2018/9/30 0030 16:39
 */
public class MyApplication extends DaggerApplication {

    /*@Inject
    DispatchingAndroidInjector<Activity> mAndroidInjector;*/


    //双重效验锁实现单例
    private static volatile MyApplication mInstance;

    private DaoSession mDaoSession;



    public static MyApplication getInstance() {
        if (mInstance == null) {
            synchronized (MyApplication.class) {
                if (mInstance == null) {
                    mInstance = new MyApplication();
                }
            }
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initGreenDao();

    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return null;
    }

    /*@Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }*/

    private void initGreenDao() {
        DaoMaster.DevOpenHelper devOpenHelper=new DaoMaster.DevOpenHelper(this,Constants.DB_NAME,null);
        SQLiteDatabase database = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster=new DaoMaster(database);
        mDaoSession=daoMaster.newSession();
    }

    public DaoSession getDaoSession(){
        return mDaoSession;
    }

   /* @Override
    public AndroidInjector<Activity> activityInjector() {
        return mAndroidInjector;
    }*/
}
