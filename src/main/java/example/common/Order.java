package example.common;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class Order implements Serializable {
    private static final long serialVersionUID = 707068266684342187L;
    public static final Direction DEFAULT_ORDER_DIRECTION = Direction.ASC;
    /**
     * 排序字段
     */
    private String property;
    /**
     * 排序方向
     */
    private Direction direction;
    public Order(String property, Direction direction) {
        super();
        this.property = property;
        this.direction = direction;
    }
    public Order(String property) {
        this(property, DEFAULT_ORDER_DIRECTION);
    }

    /**
     * 排序方向
     * @author 01266953
     */
    public enum Direction{
        /**
         * 升序
         */
        ASC,
        /**
         * 降序
         */
        DESC;
    }
}
