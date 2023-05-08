package api;

/**
 *
 * @author lujiaxin
 * @date 2023/5/8
 */
public interface ICacheEvictContext<K,V> {

    /**
     * 新加的 key
     * @return key
     * @since 0.0.2
     */
    K key();

    /**
     * cache 实现
     * @return map
     * @since 0.0.2
     */
    ICache<K, V> cache();

    /**
     * 获取大小
     * @return 大小
     * @since 0.0.2
     */
    int size();

}
