package core.support.load;

import api.ICache;
import api.ICacheLoad;

/**
 * 加载策略-无
 * @author lujiaxin
 * @date 2023/5/8
 */
public class CacheLoadNone<K,V> implements ICacheLoad<K,V> {

    @Override
    public void load(ICache<K, V> cache) {
        //nothing...
    }

}
