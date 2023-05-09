package core.KV.Values;

import core.KV.Key;

public class ListValue implements Value {
    @Override
    public <V> V get(Key key) {
        return null;
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
