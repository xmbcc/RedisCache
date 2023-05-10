package core.core.util;

import com.github.houbb.heaven.util.lang.ObjectUtil;
import com.github.houbb.heaven.util.lang.StringUtil;
import core.KV.Key;

/**
 * 参数校验工具类
 * @author lujiaxin
 * @date 2023/5/10
 */
public class ParameterCheck {

    /**
     * 单个参数校验
     * @param o1
     * @return
     */
    public static boolean pc(Object o1){
        if( o1 == null || ObjectUtil.isNull(o1) || StringUtil.isBlank(o1.toString()) ){
            return false;
        }
        return true;
    }

    /**
     * 双参数校验
     * @param o1
     * @param o2
     * @return
     */
    public static boolean pc(Object o1,Object o2){
        if( o1 == null || ObjectUtil.isNull(o1) || o1.getClass() == null ||
            o2 == null || ObjectUtil.isNull(o2) || o2.getClass() == null){
            return false;
        }
        return true;
    }

}
