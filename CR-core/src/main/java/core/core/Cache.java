package core.core;

import annotation.CacheInterceptor;
import api.*;
import core.KV.Values.*;
import core.constant.enums.CacheRemoveType;
import core.core.exception.CacheRuntimeException;
import core.support.evict.CacheEvictContext;
import core.support.expire.CacheExpire;
import core.support.listener.remove.CacheRemoveListenerContext;
import core.support.persist.InnerCachePersist;
import core.support.proxy.CacheProxy;
import com.github.houbb.heaven.util.lang.ObjectUtil;

import java.util.*;
/**
 *
 * @author lujiaxin
 * @date 2023/5/8
 */
//缓存核心类
public class Cache<K,V> implements ICache<K,V> {

    //初始化
    public void init() {
        //加载数据结构
        this.sValue = new StringValue();
        this.listValue = new ListValue();
        this.hashValue = new HashValue();
        this.setValue = new SetValue();
        this.zsetValue = new ZSetValue();
        //设置过期策略
        this.expire = new CacheExpire<>(this);
        this.load.load(this);

        // 初始化持久化
        if(this.persist != null) {
            new InnerCachePersist<>(this, persist);
        }
    }

    public Value opsForString(){
        return sValue;
    }

    public Value opsForList(){
        return listValue;
    }

    public Value opsForHash(){
        return hashValue;
    }

    public Value opsForSet(){
        return setValue;
    }

    public Value opsForZset(){
        return zsetValue;
    }


    //方法： get
    //同时刷新清除过期数据
    @Override
    @CacheInterceptor(evict = true)
    @SuppressWarnings("unchecked")
    public V get(Object key) {
        //1. 刷新所有过期信息
        K genericKey = (K) key;
        this.expire.refreshExpire(Collections.singletonList(genericKey));

        return map.get(key);
    }

    //方法： put
    @Override
    @CacheInterceptor(aof = true, evict = true)
    public V put(K key, V value) {
        //1.1 尝试驱除
        CacheEvictContext<K,V> context = new CacheEvictContext<>();
        context.key(key).size(sizeLimit).cache(this);

        ICacheEntry<K,V> evictEntry = evict.evict(context);

        // 添加拦截器调用
        if(ObjectUtil.isNotNull(evictEntry)) {
            // 执行淘汰监听器
            ICacheRemoveListenerContext<K,V> removeListenerContext = CacheRemoveListenerContext.<K,V>newInstance().key(evictEntry.key())
                    .value(evictEntry.value())
                    .type(CacheRemoveType.EVICT.code());
            for(ICacheRemoveListener<K,V> listener : context.cache().removeListeners()) {
                listener.listen(removeListenerContext);
            }
        }

        //2. 判断驱除后的信息
        if(isSizeLimit()) {
            throw new CacheRuntimeException("当前队列已满，数据添加失败！");
        }

        //3. 执行添加
        return map.put(key, value);
    }

    //1.1方法：设置过期时间
    @Override
    @CacheInterceptor
    public ICache<K, V> expire(K key, long timeInMills) {
        long expireTime = System.currentTimeMillis() + timeInMills;

        // 使用代理调用
        Cache<K,V> cachePoxy = (Cache<K, V>) CacheProxy.getProxy(this);
        return cachePoxy.expireAt(key, expireTime);
    }

    //1.1方法： 指定时间过期
    @Override
    @CacheInterceptor(aof = true)
    public ICache<K, V> expireAt(K key, long timeInMills) {
        this.expire.expire(key, timeInMills);
        return this;
    }

    //方法： 获取过期策略
    @Override
    @CacheInterceptor
    public ICacheExpire<K, V> expire() {
        return this.expire;
    }

    //方法： 获取长度
    @Override
    @CacheInterceptor(refresh = true)
    public int size() {
        return map.size();
    }

    //方法： 判断是否为空
    @Override
    @CacheInterceptor(refresh = true)
    public boolean isEmpty() {
        return map.isEmpty();
    }

    //方法： 是否包含key
    @Override
    @CacheInterceptor(refresh = true, evict = true)
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    //方法： 是否包含value
    @Override
    @CacheInterceptor(refresh = true)
    public boolean containsValue(Object value) {
        return map.containsValue(value);
    }

    //方法: 判断是否数据满了
    private boolean isSizeLimit() {
        final int currentSize = this.size();
        return currentSize >= this.sizeLimit;
    }

    //方法： 移除key
    @Override
    @CacheInterceptor(aof = true, evict = true)
    public V remove(Object key) {
        return map.remove(key);
    }

    //方法： 一次性put多个
    @Override
    @CacheInterceptor(aof = true)
    public void putAll(Map<? extends K, ? extends V> m) {
        map.putAll(m);
    }

    //方法： 清除所有
    @Override
    @CacheInterceptor(refresh = true, aof = true)
    public void clear() {
        map.clear();
    }

    //方法： 取出key的set集合
    @Override
    @CacheInterceptor(refresh = true)
    public Set<K> keySet() {
        return map.keySet();
    }

    //方法： 取出value的集合
    @Override
    @CacheInterceptor(refresh = true)
    public Collection<V> values() {
        return map.values();
    }

    //方法： 取出entry的set集合
    @Override
    @CacheInterceptor(refresh = true)
    public Set<Entry<K, V>> entrySet() {
        return map.entrySet();
    }







    //默认配置
    private Map<K,V> map;
    private int sizeLimit;
    private ICacheEvict<K,V> evict;
    private ICacheExpire<K,V> expire;
    private List<ICacheRemoveListener<K,V>> removeListeners;
    private List<ICacheSlowListener> slowListeners;
    private ICacheLoad<K,V> load;
    private ICachePersist<K,V> persist;
    private StringValue sValue;
    private ListValue listValue;
    private HashValue hashValue;
    private SetValue setValue;
    private ZSetValue zsetValue;

    //自定义配置
    public Cache<K, V> map(Map<K, V> map) {
        this.map = map;
        return this;
    }
    public Cache<K, V> sizeLimit(int sizeLimit) {
        this.sizeLimit = sizeLimit;
        return this;
    }
    public Cache<K, V> evict(ICacheEvict<K, V> cacheEvict) {
        this.evict = cacheEvict;
        return this;
    }
    @Override
    public ICachePersist<K, V> persist() {
        return persist;
    }
    @Override
    public ICacheEvict<K, V> evict() {
        return this.evict;
    }
    public void persist(ICachePersist<K, V> persist) {
        this.persist = persist;
    }
    @Override
    public List<ICacheRemoveListener<K, V>> removeListeners() {
        return removeListeners;
    }
    public Cache<K, V> removeListeners(List<ICacheRemoveListener<K, V>> removeListeners) {
        this.removeListeners = removeListeners;
        return this;
    }
    @Override
    public List<ICacheSlowListener> slowListeners() {
        return slowListeners;
    }
    public Cache<K, V> slowListeners(List<ICacheSlowListener> slowListeners) {
        this.slowListeners = slowListeners;
        return this;
    }
    @Override
    public ICacheLoad<K, V> load() {
        return load;
    }
    public Cache<K, V> load(ICacheLoad<K, V> load) {
        this.load = load;
        return this;
    }

}
