package org.pg6100.ejb.businesslayer;

import org.pg6100.ejb.datalayer.News;

import javax.ejb.Remote;
import java.util.List;

@Remote
public interface RemoteNews {

    List<News> getAllNews();
}
