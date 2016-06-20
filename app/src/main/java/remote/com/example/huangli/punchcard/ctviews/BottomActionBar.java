package remote.com.example.huangli.punchcard.ctviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import remote.com.example.huangli.punchcard.R;

/**
 * Created by huangli on 16/6/19.
 */
public class BottomActionBar extends RelativeLayout{

    public static final int TYPE_BOTTOM_ACTION_TASK = 0;
    public static final int TYPE_BOTTOM_ACTION_FRIEND = 1;
    public static final int TYPE_BOTTOM_ACTION_WORLD = 2;
    public static final int TYPE_BOTTOM_ACTION_MINE = 3;


    private RelativeLayout layout_btn_task,layout_btn_friend,layout_btn_world,layout_btn_mine;
    private ImageView ivMine,ivTask,ivWorld,ivFriend;
    private OnBottomActionBarItemClickListener onBottomActionBarItemClickListener;

    public interface OnBottomActionBarItemClickListener{
        void selected(int type);
    }

    public BottomActionBar(Context context) {
        super(context);
        init(context);
    }

    public BottomActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setOnBottomActionBarItemClickListener(OnBottomActionBarItemClickListener onBottomActionBarItemClickListener){
        this.onBottomActionBarItemClickListener = onBottomActionBarItemClickListener;
    }

    private void init(Context context){
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.layout_bottom_actionbar, this);
        findviews();
        setListeners();
        selected(TYPE_BOTTOM_ACTION_TASK);
    }

    public void selected(int selected){
        resetSelectedState();
        switch (selected){
            case TYPE_BOTTOM_ACTION_TASK:
                ivTask.setImageResource(R.mipmap.ic_event_note_white_36dp);
                break;
            case TYPE_BOTTOM_ACTION_FRIEND:
                ivFriend.setImageResource(R.mipmap.ic_people_white_36dp);
                break;
            case TYPE_BOTTOM_ACTION_WORLD:
                ivWorld.setImageResource(R.mipmap.ic_public_white_36dp);
                break;
            case TYPE_BOTTOM_ACTION_MINE:
                ivMine.setImageResource(R.mipmap.ic_person_white_36dp);
                break;
        }
    }

    private void resetSelectedState(){
        ivMine.setImageResource(R.mipmap.ic_person_black_36dp);
        ivTask.setImageResource(R.mipmap.ic_event_note_black_36dp);
        ivWorld.setImageResource(R.mipmap.ic_public_black_36dp);
        ivFriend.setImageResource(R.mipmap.ic_people_black_36dp);
    }

    private void setListeners() {
        layout_btn_task.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBottomActionBarItemClickListener != null){
                    onBottomActionBarItemClickListener.selected(TYPE_BOTTOM_ACTION_TASK);
                }
                selected(TYPE_BOTTOM_ACTION_TASK);
            }
        });
        layout_btn_friend.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBottomActionBarItemClickListener != null){
                    onBottomActionBarItemClickListener.selected(TYPE_BOTTOM_ACTION_FRIEND);
                }
                selected(TYPE_BOTTOM_ACTION_FRIEND);
            }
        });
        layout_btn_world.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBottomActionBarItemClickListener != null){
                    onBottomActionBarItemClickListener.selected(TYPE_BOTTOM_ACTION_WORLD);
                }
                selected(TYPE_BOTTOM_ACTION_WORLD);
            }
        });
        layout_btn_mine.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBottomActionBarItemClickListener != null){
                    onBottomActionBarItemClickListener.selected(TYPE_BOTTOM_ACTION_MINE);
                }
                selected(TYPE_BOTTOM_ACTION_MINE);
            }
        });
    }


    private void findviews(){
        layout_btn_task = (RelativeLayout)findViewById(R.id.layout_btn_task);
        layout_btn_friend = (RelativeLayout)findViewById(R.id.layout_btn_friend);
        layout_btn_world = (RelativeLayout)findViewById(R.id.layout_btn_world);
        layout_btn_mine = (RelativeLayout)findViewById(R.id.layout_btn_mine);
        ivMine = (ImageView)findViewById(R.id.iv_mine);
        ivFriend = (ImageView)findViewById(R.id.iv_people);
        ivTask = (ImageView)findViewById(R.id.iv_tasks);
        ivWorld = (ImageView)findViewById(R.id.iv_cycle);
    }
}
