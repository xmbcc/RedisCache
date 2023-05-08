package core.support.evict;

import api.ICacheEntry;
import api.ICacheEvictContext;

/**
 * 丢弃策略
 * @author lujiaxin
 * @date 2023/5/8
 */
public class CacheEvictNone<K,V> extends AbstractCacheEvict<K,V> {

    @Override
    protected ICacheEntry<K, V> doEvict(ICacheEvictContext<K, V> context) {
        return null;
    }

}
