package api;

/**
 *
 * @author lujiaxin
 * @date 2023/5/8
 */
public interface ICacheInterceptor<K,V> {

    /**
     * 方法执行之前
     * @param context 上下文
     * @since 0.0.5
     */
    void before(ICacheInterceptorContext<K,V> context);

    /**
     * 方法执行之后
     * @param context 上下文
     * @since 0.0.5
     */
    void after(ICacheInterceptorContext<K,V> context);

}
