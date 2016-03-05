package pg6100.entity.enums;

import pg6100.entity.News;

import javax.persistence.PrePersist;
import java.util.Date;

public class NewsListener {
    @PrePersist
    public void newsPrePersist(News ob) {
        ob.setUpdatedAt(new Date());
    }

}