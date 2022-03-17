package example.common;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@Getter
@Setter
@ToString
public class PageRequest implements Serializable {
    private static final long serialVersionUID = -5343123974218802336L;
    public static final int DEFAULT_PAGE = 1;
    public static final int DEFAULT_SIZE = 20;
    /**
     * 第几页，从1开始
     */
    private int page = DEFAULT_PAGE;
    /**
     * 每页数据量
     */
    private int size = DEFAULT_SIZE;
    private List<Order> orders = new ArrayList<>();
    public PageRequest() {
        this(DEFAULT_PAGE,DEFAULT_SIZE,new ArrayList<Order>(0));
    }
    public PageRequest(List<Order> orders) {
        this(DEFAULT_PAGE,DEFAULT_SIZE,orders== null ? new ArrayList<Order>():orders);
    }
    public PageRequest(int page, int size) {
        this(page,size,new ArrayList<Order>(0));
    }
    public PageRequest(int page, int size, List<Order> orders) {
        super();
        checkArgument(page>=1,"页号至少为1");
        this.page = page;
        this.size = size;
        this.orders = orders;
    }
    public int getOffset() {
        return (page-1) * size;
    }

    /**
     * 工厂方法创建分页请求对象
     * @param page
     * @param size
     * @return
     */
    public static PageRequest of(int page,int size){
        return new PageRequest(page,size);
    }
}

