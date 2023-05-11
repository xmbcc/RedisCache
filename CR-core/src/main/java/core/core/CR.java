package core.core;

import core.Context.Evict.Evict;
import core.Context.Evict.EvictFIFO;
import core.Context.Expire.Expire;
import core.KV.Key;
import core.KV.Values.*;
import java.util.*;

/**
 * 核心
 * @author lujiaxin
 * @date 2023/5/9
 */
//缓存核心类
public class CR {

    //默认配置
    private Map<Key,Object> map;
    private StringValue sValue;
    private ListValue listValue;
    private HashValue hashValue;
    private Evict evict;
    private Expire expire;
    private SetValue setValue;
    private ZSetValue zsetValue;

    /**
     * 构造
     */
    public CR() {
        init();
    }

    /**
     * 指定过期策略构造
     * @param expire
     */
    public CR(Expire expire){
        this.expire = expire;
    }

    /**
     * 指定淘汰策略构造
     * @param evict
     */
    public CR(Evict evict){
        this.evict = evict;
    }

    /**
     * 指定过期、淘汰策略构造
     * @param expire
     * @param evict
     */
    public CR(Expire expire,Evict evict){
        this.evict = evict;
        this.expire = expire;
    }

    //初始化
    public void init() {
        //加载数据结构
        this.map = new HashMap<Key,Object>();
        CRcontext crc = new CRcontext(evict,expire,map);
        this.sValue = new StringValue(crc);
        this.listValue = new ListValue(crc);
        this.hashValue = new HashValue(crc);
        this.setValue = new SetValue(crc);
        this.zsetValue = new ZSetValue(crc);
        //设置淘汰、过期策略
        this.evict = new EvictFIFO();
        // 初始化持久化
    }

    public Value opsForString(){
        return sValue;
    }

    public Value opsForList(){
        return listValue;
    }

    public Value opsForHash(){
        return hashValue;
    }

    public Value opsForSet(){
        return setValue;
    }

    public Value opsForZset(){
        return zsetValue;
    }


}

