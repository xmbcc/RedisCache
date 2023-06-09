package core.KV.Values;

import core.Context.Evict.Evict;
import core.Context.Evict.EvictLFU;
import core.KV.Key;
import core.core.CRcontext;
import core.core.exception.CRRunTimeException;
import core.core.util.GlobalUtils;

import java.util.Map;

import static core.core.exception.ExceptionCode.*;

/**
 * String数据类型
 * @author lujiaxin
 * @date 2023/5/9
 */
public class StringValue implements Value{

    private CRcontext crc;

    private Map<Object,Object> map;

    /**
     * 空参
     */
    public StringValue() {
    }

    /**
     * 带参
     * @param crc
     */
    public StringValue(CRcontext crc) {
        this.crc = crc;
        this.map = crc.getMap();
    }

    /**
     * get方法
     * @param key
     * @return
     * @param <V>
     * @throws CRRunTimeException
     */
    @Override
    public <V> V get(Key key) throws CRRunTimeException {
        //参数校验
        if( !GlobalUtils.pc(key) ){
            //error：key格式错误
            throw new CRRunTimeException(KEY_FORMAT_ERROR);
        }
        //判断Key是否存在
        if(!map.containsKey(key))
            //error：key不存在
            throw new CRRunTimeException(KEY_NOTFIND_ERROR);
        //超时、策略逻辑
        //LFU 自增操作
        GlobalUtils.doIncrease(key,crc,map);
        //查找并校验value
        if( !GlobalUtils.pc(map.get(key)) ){
            //error：value格式错误
            throw new CRRunTimeException(VALUE_FORMAT_ERROR);
        }

        return (V) map.get(key);
    }

    /**
     * set方法
     * @param key
     * @param value
     * @return
     */
    @Override
    public Boolean set(Key key, Object value) throws CRRunTimeException {
        //参数校验
        if(!GlobalUtils.pc(key,value)) throw new CRRunTimeException(KEY_VALUE_FORMAT_ERROR);
        //判断key是否存在
        if(!containsKey(key)) throw new CRRunTimeException(KEY_NOTFIND_ERROR);
        //开始存入，判断是否触发容量限制
        map.put(key,value);
        //封装方法：超过限制/未超过
        //淘汰
        crc.getEvict().doEvict(key,value,crc);
        return true;
    }

    private void dosomething() {
    }


    /**
     * 给Key设置过期时间
     * @param key
     * @param timeinMillons
     * @return
     */
    @Override
    public void expire(Key key, long timeinMillons) {

        //创建key，附加时间
        long time = System.currentTimeMillis() + timeinMillons;
        Key k = new Key(key.toString().getBytes(),time);
        //触发使用频次++
        GlobalUtils.doIncrease(key,crc,map);
        //塞入
        map.put(key,map.get(key));

    }

    @Override
    public Boolean containsKey(Key key) {
        if(map.containsKey(key)) return true;
        return false;
    }

    @Override
    public Object remove(Key key) throws CRRunTimeException {
        //判断传入参数是否符合格式
        if(!GlobalUtils.pc(key)) throw new CRRunTimeException(KEY_FORMAT_ERROR);
        //判断Key是否存在
        if(!containsKey(key)) throw new CRRunTimeException(KEY_NOTFIND_ERROR);
        //dosomething

        //删除Key
        return map.remove(key);
    }
}
