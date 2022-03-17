package example.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    /**
     * 执行正确返回的错误码
     */
    public static final String CODE_SUCCESS = "200";
    /**
     * 执行失败错误码
     */
    public static final String CODE_ERROR = "-1";
    private T data;
    private Boolean success;
    private String code;
    private String error;

    /**
     * 返回执行成功的结果对象
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data){
        Result<T> result = new Result<T>();
        result.setSuccess(true);
        result.setData(data);
        result.setCode(CODE_SUCCESS);
        return result;
    }

    /**
     * 返回执行失败的对象
     * @param error
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(String error){
        Result<T> result = new Result<T>();
        result.setSuccess(false);
        result.setError(error);
        result.setCode(CODE_ERROR);
        return result;
    }

    /**
     * 返回执行失败的对象
     * @param error
     * @param <T>
     * @return
     */
    public static <T> Result<T> error(String error,String errorCode){
        Result<T> result = new Result<T>();
        result.setSuccess(false);
        result.setError(error);
        result.setCode(errorCode);
        return result;
    }
}
