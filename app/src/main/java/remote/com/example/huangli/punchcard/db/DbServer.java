package remote.com.example.huangli.punchcard.db;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;
import remote.com.example.huangli.punchcard.R;
import remote.com.example.huangli.punchcard.dao.CardEntity;
import remote.com.example.huangli.punchcard.dao.CardEntityDao;
import remote.com.example.huangli.punchcard.dao.DaoSession;
import remote.com.example.huangli.punchcard.model.Card;
import remote.com.example.huangli.punchcard.model.Task;

/**
 * Created by huangli on 16/6/26.
 */
public class DbServer {
    private static DbServer dbServer;
    private Context mContext;

    public static DbServer getInstance(Context context){
        if (dbServer == null){
            dbServer = new DbServer(context);
        }
        return dbServer;
    }

    private CardEntityDao cardEntityDao;

    public DbServer(Context context){
        mContext = context;
        DaoSession daoSession = DaoProxy.getInstance(context).getDaoSession();
        if (daoSession != null) {
            cardEntityDao = daoSession.getCardEntityDao();
        }
    }

    public void insertCardToDb(Card card) {
        StringBuilder sb = new StringBuilder();
        for (Task task : card.getTasks()) {
            sb.append(mContext.getString(R.string.task_tips) + " : " + task.getDescribe() + "\n");
        }
        CardEntity cardEntity = new CardEntity("world", card.getType(), card.getDescribe(), sb.toString(), card.getNickName());
        cardEntityDao.insert(cardEntity);
    }

    public List<Card> loadCardsFromDb(){
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
            }

            @Override
            protected void onProgressUpdate(Object[] values) {
                super.onProgressUpdate(values);
            }
        };
        asyncTask.execute();



        List<Card> cards = new ArrayList<>();
        QueryBuilder qb = cardEntityDao.queryBuilder();
        List<CardEntity> cardEntities = qb.where(CardEntityDao.Properties.Key.in("world")).limit(100).list();
        for (CardEntity cardEntity : cardEntities) {
            Card card = new Card(cardEntity.getNickName(),cardEntity.getType(),cardEntity.getDescribe());
            cards.add(card);
        }
        return cards;
    }
}
