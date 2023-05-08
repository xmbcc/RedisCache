package core.support.interceptor.evict;

import api.ICacheEvict;
import api.ICacheInterceptor;
import api.ICacheInterceptorContext;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;

import java.lang.reflect.Method;

/**
 * 驱除策略拦截器
 * @author lujiaxin
 * @date 2023/5/8
 */
public class CacheInterceptorEvict<K,V> implements ICacheInterceptor<K, V> {

    private static final Log log = LogFactory.getLog(CacheInterceptorEvict.class);

    @Override
    public void before(ICacheInterceptorContext<K,V> context) {
    }

    @Override
    @SuppressWarnings("all")
    public void after(ICacheInterceptorContext<K,V> context) {
        ICacheEvict<K,V> evict = context.cache().evict();

        Method method = context.method();
        final K key = (K) context.params()[0];
        if("remove".equals(method.getName())) {
            evict.removeKey(key);
        } else {
            evict.updateKey(key);
        }
    }

}
