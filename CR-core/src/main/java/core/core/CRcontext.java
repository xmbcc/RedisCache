package core.core;

import core.Context.Evict.Evict;
import core.Context.Expire.Expire;

import java.util.Map;

/**
 * 上下文配置信息
 * @author lujiaxin
 * @date 2023/5/10
 */
public class CRcontext {

    //驱除策略
    private Evict evict;
    //过期策略
    private Expire expire;

    //map
    private Map<Object,Object> map;

    public CRcontext(Evict evict, Expire expire,Map map) {
        this.evict = evict;
        this.expire = expire;
        this.map = map;
    }

    public Evict getEvict() {
        return evict;
    }

    public void setEvict(Evict evict) {
        this.evict = evict;
    }

    public Expire getExpire() {
        return expire;
    }

    public void setExpire(Expire expire) {
        this.expire = expire;
    }

    public Map<Object, Object> getMap() {
        return map;
    }

    public void setMap(Map<Object, Object> map) {
        this.map = map;
    }
}
