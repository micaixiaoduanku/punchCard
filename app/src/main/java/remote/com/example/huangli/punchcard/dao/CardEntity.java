package remote.com.example.huangli.punchcard.dao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table CARD_ENTITY.
 */
public class CardEntity {

    private String key;
    /** Not-null value. */
    private String type;
    /** Not-null value. */
    private String describe;
    /** Not-null value. */
    private String taskscontent;
    /** Not-null value. */
    private String nickName;

    public CardEntity() {
    }

    public CardEntity(String key) {
        this.key = key;
    }

    public CardEntity(String key, String type, String describe, String taskscontent, String nickName) {
        this.key = key;
        this.type = type;
        this.describe = describe;
        this.taskscontent = taskscontent;
        this.nickName = nickName;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    /** Not-null value. */
    public String getType() {
        return type;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setType(String type) {
        this.type = type;
    }

    /** Not-null value. */
    public String getDescribe() {
        return describe;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setDescribe(String describe) {
        this.describe = describe;
    }

    /** Not-null value. */
    public String getTaskscontent() {
        return taskscontent;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setTaskscontent(String taskscontent) {
        this.taskscontent = taskscontent;
    }

    /** Not-null value. */
    public String getNickName() {
        return nickName;
    }

    /** Not-null value; ensure this value is available before it is saved to the database. */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

}