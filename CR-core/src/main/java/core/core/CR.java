package core.core;

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
    private SetValue setValue;
    private ZSetValue zsetValue;

    /**
     * 构造
     */
    public CR() {
        init();
    }

    //初始化
    public void init() {
        //加载数据结构
        this.map = new HashMap<Key,Object>();
        this.sValue = new StringValue(map);
        this.listValue = new ListValue(map);
        this.hashValue = new HashValue(map);
        this.setValue = new SetValue(map);
        this.zsetValue = new ZSetValue(map);
        //设置过期策略

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

