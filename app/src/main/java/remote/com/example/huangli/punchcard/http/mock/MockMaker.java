package remote.com.example.huangli.punchcard.http.mock;

import android.content.Context;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import remote.com.example.huangli.punchcard.dao.PlanEntity;
import remote.com.example.huangli.punchcard.dao.TaskEntity;
import remote.com.example.huangli.punchcard.dao.UserEntity;
import remote.com.example.huangli.punchcard.http.HttpProtocol;
import remote.com.example.huangli.punchcard.pojo.Pojo_Cur_Plan;
import remote.com.example.huangli.punchcard.pojo.Pojo_Plan;
import remote.com.example.huangli.punchcard.pojo.Pojo_Plan_List;
import remote.com.example.huangli.punchcard.pojo.Pojo_Result;
import remote.com.example.huangli.punchcard.pojo.Pojo_Task;
import remote.com.example.huangli.punchcard.pojo.Pojo_User;
import remote.com.example.huangli.punchcard.db.DbServer;
import remote.com.example.huangli.punchcard.utils.StringUtil;

/**
 * Created by wangjun on 16/3/28.
 */
public class MockMaker {
    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;

    private DbServer dbServer;

    public MockMaker(Context context){
        mContext = context.getApplicationContext();
        dbServer = new DbServer(context);
    }

    public String createMock(String url, Map<String, Object> params){
        return parse(url,params);
    }

    private String parse(String url,Map<String, Object> params){
        Gson gson = new Gson();
        if (url.equals(HttpProtocol.URLS.USER_SIGNIN)){
            String account = (String) params.get("account");
            String password = (String) params.get("password");
            String nickname = (String) params.get("nickname");
            boolean issucceed = dbServer.insertUserToDB(account,password,nickname,"",1,0);
            Pojo_User pojo_user = new Pojo_User(issucceed?0:1,"",account,0,nickname,"",password,0);
            return gson.toJson(pojo_user);
        }
        if (url.equals(HttpProtocol.URLS.USER_LOGIN)){
            String account = (String) params.get("account");
            String password = (String) params.get("password");
            UserEntity userEntity = dbServer.queryUserInDB(account,password);
            if (userEntity != null){
                Pojo_User pojo_user = new Pojo_User(0,"",userEntity.getAccount(),userEntity.getLv(),userEntity.getNickname(),userEntity.getSignature(),userEntity.getPassoword(),userEntity.getCurProgress());
                return gson.toJson(pojo_user);
            }
        }
        if (url.equals(HttpProtocol.URLS.REQUEST_PLANS)){
            String account = (String) params.get("account");
            List<PlanEntity> list = dbServer.queryPlanInDB(account);
            if (list != null){
                List<Pojo_Plan> list_plan = new ArrayList<>();
                for (PlanEntity planEntity : list){
                    list_plan.add(new Pojo_Plan(planEntity.getType(),planEntity.getDescribe(),planEntity.getNum()));
                }
                Pojo_Plan_List pojo_plan_list = new Pojo_Plan_List(0,"");
                pojo_plan_list.list = list_plan;
                return gson.toJson(pojo_plan_list);
            }
        }
        if (url.equals(HttpProtocol.URLS.ADD_PLAN)){
            String account = (String) params.get("account");
            String num = (String) params.get("num");
            String describe = (String) params.get("describe");
            int type = (int) params.get("type");
            boolean issucceed = dbServer.insertPlanToDB(account,num,describe,type);
            Pojo_Result pojo_result = new Pojo_Result(issucceed?0:1,"");
            return gson.toJson(pojo_result);
        }
        if (url.equals(HttpProtocol.URLS.ADD_TASK_LIST)){
            String num = (String) params.get("num");
            List<Pojo_Task> tasks = (List<Pojo_Task>) params.get("tasks");
        }
        if (url.equals(HttpProtocol.URLS.REQUEST_CUR_PLAN)){
            String account = (String) params.get("account");
            String plannum = (String) params.get("plannum");
            PlanEntity planEntity = dbServer.queryCurPlanInDB(account,plannum);
            if (planEntity != null){
                Pojo_Cur_Plan pojo_cur_plan = new Pojo_Cur_Plan(0,"",planEntity.getType(),planEntity.getDescribe(),planEntity.getNum());
                List<TaskEntity> taskEntities = dbServer.queryTasksInDB(planEntity.getNum());
                List<Pojo_Task> list = new ArrayList<>();
                for (TaskEntity taskEntity : taskEntities){
                    list.add(new Pojo_Task(taskEntity.getDescribe(),taskEntity.getIsComplated(), StringUtil.parseStrToArray(taskEntity.getRemindDays())));
                }
                pojo_cur_plan.tasks = list;
                return gson.toJson(pojo_cur_plan);
            }
        }
        Pojo_Result pojo_result = new Pojo_Result(1,"没有数据");
        return gson.toJson(pojo_result);
    }

    private String getFileName(String url){
        return url.substring(url.lastIndexOf("/")+1);
    }

    public static String inputStream2String(InputStream is) throws IOException{
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i=-1;
        while((i=is.read())!=-1){
            baos.write(i);
        }
        return baos.toString();
    }


}
