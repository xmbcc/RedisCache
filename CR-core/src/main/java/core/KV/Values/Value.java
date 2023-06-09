package core.KV.Values;

import core.KV.Key;
import core.core.exception.CRRunTimeException;

/**
 * 数据类型顶层接口，实现接口创造数据类型
 * @author lujiaxin
 * @date 2023/5/9
 */
public interface Value {

    <V> V get(Key key) throws CRRunTimeException;

    Boolean set(Key key,Object value) throws CRRunTimeException;

    void expire(Key key,long timeinMillons);

    Boolean containsKey(Key key);

    <V> V remove(Key key) throws CRRunTimeException;

}
