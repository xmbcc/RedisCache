package api;

/**
 *
 * @author lujiaxin
 * @date 2023/5/8
 */
public interface ICacheEntry<K, V> {

    /**
     * @since 0.0.11
     * @return key
     */
    K key();

    /**
     * @since 0.0.11
     * @return value
     */
    V value();

}
