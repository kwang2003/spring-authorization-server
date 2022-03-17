package example.common;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class Page<T> implements Serializable {
    private static final long serialVersionUID = 2663872327758935651L;
    private PageRequest pageRequest;
    /**
     * 当前页返回的数据
     */
    private List<T> content;
    /**
     * 累计查询数据条数
     */
    private Integer totalElements;
    /**
     * 数据总页数
     */
    private Integer totalPages;
    public Page() {
        this(new ArrayList<T>(), 0);
    }
    public Page(List<T> content, Integer totalElements) {
        this(null, content, totalElements);
    }
    public Page(PageRequest pageRequest, List<T> content, Integer totalElements) {
        super();
        this.pageRequest = pageRequest;
        this.content = content;
        this.totalElements = totalElements;

        if(this.pageRequest == null){
            this.totalPages = 1;
        }else{
            this.totalPages = (int)(Math.ceil((double) totalElements / (double)pageRequest.getSize()));
        }
    }

}
