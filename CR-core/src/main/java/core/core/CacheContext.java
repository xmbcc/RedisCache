package core.core;

import api.ICacheContext;
import api.ICacheEvict;

import java.util.Map;


//介绍： 缓存上下文
//内容： map             数据
//      size            大小
//      cacheEvict      驱逐策略
/**
 *
 * @author lujiaxin
 * @date 2023/5/8
 */
public class CacheContext<K,V> implements ICacheContext<K, V> {

    //map数据
    private Map<K, V> map;


    //大小限制
    private int size;

    //驱逐策略
    private ICacheEvict<K,V> cacheEvict;

    //获取上下文map数据
    @Override
    public Map<K, V> map() {
        return map;
    }

    //设置上下文map数据
    public CacheContext<K, V> map(Map<K, V> map) {
        this.map = map;
        return this;
    }

    //获取上下文数据长度
    @Override
    public int size() {
        return size;
    }

    //设置上下文数据长度
    public CacheContext<K, V> size(int size) {
        this.size = size;
        return this;
    }

    //获取上下文驱逐策略
    @Override
    public ICacheEvict<K, V> cacheEvict() {
        return cacheEvict;
    }

    //设置上下文驱逐策略
    public CacheContext<K, V> cacheEvict(ICacheEvict<K, V> cacheEvict) {
        this.cacheEvict = cacheEvict;
        return this;
    }

}
