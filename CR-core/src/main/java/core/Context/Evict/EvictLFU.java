package core.Context.Evict;

import core.KV.Key;
import core.KV.KeyValueNode;
import core.core.CRcontext;
import core.core.exception.CRRunTimeException;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import static core.constant.GlobalCode.*;
import static core.core.exception.ExceptionCode.LFU_MIN_NOTFIND;

/**
 * 淘汰策略-最小使用频次
 * @author lujiaxin
 * @date 2023/5/11
 */
public class EvictLFU implements Evict{

    private Map<Integer,LinkedHashSet<KeyValueNode>> map;

    private Integer min;

    public EvictLFU() {
        init();
    }

    private void init() {

        map = new HashMap<>();
        min = LFU_MIN;

    }

    @Override
    public void doEvict(Key key, Object value, CRcontext crc) throws CRRunTimeException {

        if(crc.getMap().size() >= CACHE_LIMIT_MAX){
            //超出限制，进行淘汰
            LinkedHashSet<KeyValueNode> minSet = map.get(min);
            //判断是否为空
            while(minSet.isEmpty()){
                min++;
                if(min>crc.getMap().size()) throw new CRRunTimeException(LFU_MIN_NOTFIND);
            }
            KeyValueNode next = minSet.iterator().next();
            crc.getMap().remove(next.getKey(),next.getValue());
        }
        //放入新元素
        LinkedHashSet<KeyValueNode> set = map.get(LFU_INIT_NUM);
        KeyValueNode node = new KeyValueNode(key,value);
        set.add(node);
        map.put(LFU_INIT_NUM,set);
        min = LFU_MIN;

    }

    /**
     * 频次自增操作
     * @param key
     * @param value
     */
    public void doIncrease(Key key,Object value) {

        //自增操作
        KeyValueNode node = new KeyValueNode(key, value);
        for(int i=0;i<map.size();i++){
            LinkedHashSet<KeyValueNode> set = map.get(i);
            if(set.contains(node)){
                set.remove(node);
                //获取下一个频次，没有就创建
                map.getOrDefault(i+1,new LinkedHashSet<>()).add(node);
            }
        }

    }
}
