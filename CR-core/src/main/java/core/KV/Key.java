package core.KV;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Key为封装的String
 *
 * @author lujiaxin
 * @date 2023/5/9
 */
public class Key implements Comparable<Key> {

    static final Charset charset = StandardCharsets.UTF_8;
    private final byte[] content;
    private long expireSeconds;

    /**
     * 构造函数
     * @param bytes
     */
    public Key(byte[] bytes){
        this.content = bytes;
    }

    /**
     * 过期构造
     *
     * @param bytes
     * @param expireSeconds
     */
    public Key(byte[] bytes, long expireSeconds){
        this.content = bytes;
        this.expireSeconds = expireSeconds;
    }
    /**
     * 重写hashcode
     * @return
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(content);
    }

    /**
     * 转换为utf-8
     * @return
     */
    public String toUtf8(){
        return new String(content,charset);
    }

    /**
     * 重写equals判断key是否唯一
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if(getClass() != obj.getClass() || obj == null) return false;

        Key k = (Key) obj;

        return Arrays.equals(content,k.content);

    }

    @Override
    public int compareTo(Key o) {
        return 0;
    }
}
