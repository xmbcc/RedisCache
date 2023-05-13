package core.KV.Values;

import core.KV.Key;
import core.core.CRcontext;

import java.util.Map;

public class ZSetValue implements Value {

    private Map<Object,Object> map;

    private CRcontext crc;

    public ZSetValue() {
    }

    public ZSetValue(CRcontext crc) {
        this.crc = crc;
        this.map = crc.getMap();
    }

    @Override
    public <V> V get(Key key) {
        return null;
    }

    @Override
    public Boolean set(Key key, Object value) {
        return null;
    }

    @Override
    public void expire(Key key, long timeinMillons) {

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
