package core.Context.Evict;

import core.KV.Key;
import core.KV.Values.Value;
import core.core.CRcontext;

import java.util.LinkedList;
import java.util.Queue;

import static core.constant.GlobalCode.CACHE_LIMIT_MAX;

/**
 * FIFO(先进先出)淘汰策略
 * @author lujiaxin
 * @date 2023/5/11
 */
public class EvictFIFO implements Evict{

    private Queue list = new LinkedList();

    @Override
    public void doEvict(Key key, Object value, CRcontext crc) {

        if(list.size() >= CACHE_LIMIT_MAX){
            //超出长度限制，进行淘汰
            crc.getMap().remove(list.poll());
        }
        list.offer(key);
        crc.getMap().put(key,value);

    }

}
