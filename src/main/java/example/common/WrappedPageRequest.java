package example.common;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@ToString
public class WrappedPageRequest implements Serializable{
    private static final long serialVersionUID = 4792763875409689039L;
    @Getter
    @Setter
    private PageRequest pageRequest;
    @Getter
    private Map<String, Object> params = new HashMap<>();
    public WrappedPageRequest(PageRequest pageRequest){
        this.pageRequest = pageRequest;
    }
    /**
     * 添加参数
     * @param key
     * @param value
     * @return
     */
    public Map<String, Object> addParam(String key,Object value){
        this.params.put(key, value);
        return this.params;
    }
    /**
     * 添加所有的请求参数
     * @param params
     * @return
     */
    public Map<String, Object> addAllParams(Map<String, Object> params){
        this.params.putAll(params);
        return  this.params;
    }
}