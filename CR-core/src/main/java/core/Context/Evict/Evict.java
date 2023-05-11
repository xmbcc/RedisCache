package core.Context.Evict;

import core.KV.Key;
import core.KV.Values.Value;
import core.core.CRcontext;
import core.core.exception.CRRunTimeException;

/**
 * 淘汰策略接口
 * @author lujiaxin
 * @date 2023/5/11
 */
public interface Evict {

    void doEvict(Key key, Object value, CRcontext crc) throws CRRunTimeException;

}
