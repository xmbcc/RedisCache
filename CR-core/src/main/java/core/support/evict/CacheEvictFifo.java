package core.support.evict;

import api.ICache;
import api.ICacheEvictContext;
import core.constant.enums.model.CacheEntry;
//import core.model.CacheEntry;

import java.util.LinkedList;
import java.util.Queue;

/**
 * 丢弃策略-先进先出
 * @author lujiaxin
 * @date 2023/5/8
 */
public class CacheEvictFifo<K,V> extends AbstractCacheEvict<K,V> {

    //双向链表  储存
    private final Queue<K> queue = new LinkedList<>();

    @Override
    public CacheEntry<K,V> doEvict(ICacheEvictContext<K, V> context) {
        CacheEntry<K,V> result = null;

        final ICache<K,V> cache = context.cache();
        // 超过限制，执行移除
        if(cache.size() >= context.size()) {
            K evictKey = queue.remove();
            // 移除最开始的元素
            V evictValue = cache.remove(evictKey);
            result = new CacheEntry<>(evictKey, evictValue);
        }

        // 将新加的元素放入队尾
        final K key = context.key();
        queue.add(key);

        return result;
    }

}
