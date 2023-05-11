package core.KV;

/**
 * 封装Key、Value的Node节点
 * @author lujiaxin
 * @date 2023/5/11
 */
public class KeyValueNode {

    private Key key;
    private Object value;

    public KeyValueNode(Key key, Object value) {
        this.key = key;
        this.value = value;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(Key key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
