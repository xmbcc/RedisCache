package core.bs;

import api.*;
import com.github.houbb.heaven.util.common.ArgUtil;
import core.core.Cache;
import core.support.evict.CacheEvicts;
import core.support.listener.remove.CacheRemoveListeners;
import core.support.listener.slow.CacheSlowListeners;
import core.support.load.CacheLoads;
import core.support.persist.CachePersists;
import core.support.proxy.CacheProxy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//介绍： 一个缓存引导类，便捷创建缓存
//作用： 将繁杂的初始化缓存过程封装
//使用： CacheBs<K,V>.newInstance()   创建对象
//      .load()                      设置加载模式
//      .persist()                   设置持久化策略
//      .map()                       设置指定数据集
//      .size()                      设置大小
//      .evict()                     设置驱逐策略
//      .addRemoveListener()         添加指定删除监听器
//      .addSlowListener()           添加指定慢日志监听器
//      .build()                     最后执行创建命令
/**
 *
 * @author lujiaxin
 * @date 2023/5/8
 */
public final class CacheBs<K,V> {
//aaa

    //默认设置
    private Map<K,V> map = new HashMap<>();
    private int size = Integer.MAX_VALUE;
    private ICacheEvict<K, V> evict = CacheEvicts.fifo();
    private final List<ICacheRemoveListener<K,V>> removeListeners = CacheRemoveListeners.defaults();
    private final List<ICacheSlowListener> slowListeners = CacheSlowListeners.none();
    private ICacheLoad<K, V> load = CacheLoads.none();
    private ICachePersist<K,V> persist = CachePersists.none();

    //自定义设置
    public CacheBs<K, V> map(Map<K, V> map) {
        ArgUtil.notNull(map, "map");

        this.map = map;
        return this;
    }
    public CacheBs<K, V> size(int size) {
        ArgUtil.notNegative(size, "size");

        this.size = size;
        return this;
    }
    public CacheBs<K, V> evict(ICacheEvict<K, V> evict) {
        ArgUtil.notNull(evict, "evict");

        this.evict = evict;
        return this;
    }
    public CacheBs<K, V> load(ICacheLoad<K, V> load) {
        ArgUtil.notNull(load, "load");

        this.load = load;
        return this;
    }
    public CacheBs<K, V> addRemoveListener(ICacheRemoveListener<K,V> removeListener) {
        ArgUtil.notNull(removeListener, "removeListener");

        this.removeListeners.add(removeListener);
        return this;
    }
    public CacheBs<K, V> addSlowListener(ICacheSlowListener slowListener) {
        ArgUtil.notNull(slowListener, "slowListener");

        this.slowListeners.add(slowListener);
        return this;
    }
    public CacheBs<K, V> persist(ICachePersist<K, V> persist) {
        this.persist = persist;
        return this;
    }

    //创建缓存
    public ICache<K,V> build() {
        Cache<K,V> cache = new Cache<>();
        cache.map(map);
        cache.evict(evict);
        cache.sizeLimit(size);
        cache.removeListeners(removeListeners);
        cache.load(load);
        cache.persist(persist);
        cache.slowListeners(slowListeners);

        // 初始化
        cache.init();
        return CacheProxy.getProxy(cache);
    }












    //构造函数
    private CacheBs(){}



    //流式编程     |      返回值        |    方法名   |
    public static  <K,V> CacheBs<K,V>  newInstance() {
        return new CacheBs<>();
    }

}
