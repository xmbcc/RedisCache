package core.support.interceptor.refresh;

import api.ICache;
import api.ICacheInterceptor;
import api.ICacheInterceptorContext;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;

/**
 * 刷新
 *
 * @author binbin.hou
 * @since 0.0.5
 */
public class CacheInterceptorRefresh<K,V> implements ICacheInterceptor<K, V> {

    private static final Log log = LogFactory.getLog(CacheInterceptorRefresh.class);

    @Override
    public void before(ICacheInterceptorContext<K,V> context) {
        log.debug("Refresh start");
        final ICache<K,V> cache = context.cache();
        cache.expire().refreshExpire(cache.keySet());
    }

    @Override
    public void after(ICacheInterceptorContext<K,V> context) {
    }

}
