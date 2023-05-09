package core.KV.Values;

import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import core.KV.Key;

import java.util.HashMap;
import java.util.Map;

/**
 * String数据类型
 * @author lujiaxin
 * @date 2023/5/9
 */
public class StringValue implements Value{

    private Map<Key,Object> map;

    /**
     * 空参
     */
    public StringValue() {
    }

    /**
     * 带参
     * @param m
     */
    public StringValue(Map m) {
        this.map = m;
    }

    @Override
    public <V> V get(Key key) {
        //判断
        if( key == null || ObjectUtil.isNull(key) || StringUtil.isBlank(key.toString()) ){
            throw new RuntimeException();
        }
        //是否超时

        //查找
        if( map.get(key) == null || ObjectUtil.isNull(map.get(key)) ){
            throw new RuntimeException();
        }

        return (V) map.get(key);
    }

    @Override
    public Boolean set(Object key, Object value) {
        return null;
    }

    @Override
    public Boolean expire(Key key, long timeinMillons) {
        return null;
    }

    @Override
    public Boolean containsKey(Key key) {
        return null;
    }

    @Override
    public <V> V remove(Key key) {
        return null;
    }
}
