package api;

/**
 *
 * @author lujiaxin
 * @date 2023/5/8
 */
public interface ICacheRemoveListener<K,V> {

    /**
     * 监听
     * @param context 上下文
     * @since 0.0.6
     */
    void listen(final ICacheRemoveListenerContext<K,V> context);

}
