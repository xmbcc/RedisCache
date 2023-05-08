package core.support.evict;

import api.ICache;
import api.ICacheEntry;
import api.ICacheEvictContext;
//import core.model.CacheEntry;
import core.support.struct.lru.ILruMap;
import core.support.struct.lru.impl.LruMapCircleList;
import api.ICache;
import api.ICacheEntry;
import api.ICacheEvictContext;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import core.constant.enums.model.CacheEntry;
import core.support.struct.lru.ILruMap;
import core.support.struct.lru.impl.LruMapCircleList;

/**
 * 淘汰策略-clock 算法
 * @author lujiaxin
 * @date 2023/5/8
 */
public class CacheEvictClock<K,V> extends AbstractCacheEvict<K,V> {

    private static final Log log = LogFactory.getLog(CacheEvictClock.class);

    /**
     * 循环链表
     * @since 0.0.15
     */
    private final ILruMap<K,V> circleList;

    public CacheEvictClock() {
        this.circleList = new LruMapCircleList<>();
    }

    @Override
    protected ICacheEntry<K, V> doEvict(ICacheEvictContext<K, V> context) {
        ICacheEntry<K, V> result = null;
        final ICache<K,V> cache = context.cache();
        // 超过限制，移除队尾的元素
        if(cache.size() >= context.size()) {
            ICacheEntry<K,V>  evictEntry = circleList.removeEldest();;
            // 执行缓存移除操作
            final K evictKey = evictEntry.key();
            V evictValue = cache.remove(evictKey);

            log.debug("基于 clock 算法淘汰 key：{}, value: {}", evictKey, evictValue);
            result = new CacheEntry<>(evictKey, evictValue);
        }

        return result;
    }


    /**
     * 更新信息
     * @param key 元素
     * @since 0.0.15
     */
    @Override
    public void updateKey(final K key) {
        this.circleList.updateKey(key);
    }

    /**
     * 移除元素
     *
     * @param key 元素
     * @since 0.0.15
     */
    @Override
    public void removeKey(final K key) {
        this.circleList.removeKey(key);
    }

}
