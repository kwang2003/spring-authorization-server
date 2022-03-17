package example.common;

import com.google.common.collect.Maps;
import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class PageableParamFactory {
    /**
     * 将分页请求对象转化成Map参数结构
     * @param pageable
     * @return
     */
    public static Map<String, Object> params(PageRequest pageable) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("pageable", pageable);
        return params;
    }
}
