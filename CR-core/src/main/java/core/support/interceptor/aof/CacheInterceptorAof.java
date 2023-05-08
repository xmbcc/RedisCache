package core.support.interceptor.aof;

import api.ICache;
import api.ICacheInterceptor;
import api.ICacheInterceptorContext;
import api.ICachePersist;
import com.alibaba.fastjson.JSON;
import com.github.houbb.log.integration.core.Log;
import com.github.houbb.log.integration.core.LogFactory;
import core.constant.enums.model.PersistAofEntry;
import core.support.persist.CachePersistAof;

/**
 * 顺序追加模式
 *
 * AOF 持久化到文件，暂时不考虑 buffer 等特性。
 * @author lujiaxin
 * @date 2023/5/8
 */
public class CacheInterceptorAof<K,V> implements ICacheInterceptor<K, V> {

    private static final Log log = LogFactory.getLog(CacheInterceptorAof.class);

    @Override
    public void before(ICacheInterceptorContext<K,V> context) {
    }

    @Override
    public void after(ICacheInterceptorContext<K,V> context) {
        // 持久化类
        ICache<K,V> cache = context.cache();
        ICachePersist<K,V> persist = cache.persist();

        if(persist instanceof CachePersistAof) {
            CachePersistAof<K,V> cachePersistAof = (CachePersistAof<K,V>) persist;

            String methodName = context.method().getName();
            PersistAofEntry aofEntry = PersistAofEntry.newInstance();
            aofEntry.setMethodName(methodName);
            aofEntry.setParams(context.params());

            String json = JSON.toJSONString(aofEntry);

            // 直接持久化
            log.debug("AOF 开始追加文件内容：{}", json);
            cachePersistAof.append(json);
            log.debug("AOF 完成追加文件内容：{}", json);
        }
    }

}
