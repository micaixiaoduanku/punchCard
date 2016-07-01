package remote.com.example.huangli.punchcard.db;

import android.content.Context;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import remote.com.example.huangli.punchcard.dao.CardEntityDao;
import remote.com.example.huangli.punchcard.dao.DaoSession;
import remote.com.example.huangli.punchcard.dao.PlanEntity;
import remote.com.example.huangli.punchcard.dao.PlanEntityDao;
import remote.com.example.huangli.punchcard.dao.TaskEntityDao;
import remote.com.example.huangli.punchcard.dao.UserEntity;
import remote.com.example.huangli.punchcard.dao.UserEntityDao;
import remote.com.example.huangli.punchcard.db.DaoProxy;
import remote.com.example.huangli.punchcard.utils.ToastUtils;

/**
 * Created by huangli on 16/6/26.
 * 模拟服务器操作（实际是本地数据库）
 */
public class DbServer {
    private Context mContext;
    private CardEntityDao cardEntityDao;
    private PlanEntityDao planEntityDao;
    private TaskEntityDao taskEntityDao;
    private UserEntityDao userEntityDao;

    public DbServer(Context context) {
        mContext = context;
        DaoSession daoSession = DaoProxy.getInstance(context).getDaoSession();
        if (daoSession != null) {
            cardEntityDao = daoSession.getCardEntityDao();
            planEntityDao = daoSession.getPlanEntityDao();
            taskEntityDao = daoSession.getTaskEntityDao();
            userEntityDao = daoSession.getUserEntityDao();
        }
    }

    public boolean insertUserToDB(String account, String passoword, String nickname, String signature, int lv, int curProgress){
        UserEntity userEntity = new UserEntity(account,passoword,nickname,signature,lv,curProgress);
        long id =  userEntityDao.insertOrReplace(userEntity);
        if (id == 0){
            return false;
        }
        return true;
    }

    public UserEntity queryUserInDB(String account, String passoword){
        QueryBuilder qb = userEntityDao.queryBuilder();
        List<UserEntity> userEntities = qb.where(UserEntityDao.Properties.Account.eq(account)).list();
        if (userEntities.size() > 0){
            return userEntities.get(0);
        }else {
            return null;
        }
    }

    public boolean insertPlanToDB(String account, String num, String describe, int type){
        PlanEntity planEntity = new PlanEntity(account,num,describe,type);
        long id = planEntityDao.insertOrReplace(planEntity);
        if (id == 0){
            return false;
        }
        return true;
    }

    public List<PlanEntity> queryPlanInDB(String account){
        QueryBuilder qb = planEntityDao.queryBuilder();
        List<PlanEntity> planEntities = qb.where(UserEntityDao.Properties.Account.eq(account)).list();
        return planEntities;
    }

//    public PlanEntity queryCurPlanInDB(String account,String num){
//        QueryBuilder qb = planEntityDao.queryBuilder();
//
//    }

}
