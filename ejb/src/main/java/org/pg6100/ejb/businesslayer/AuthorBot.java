package org.pg6100.ejb.businesslayer;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import javax.inject.Inject;

@Singleton
@Startup
@DependsOn("DatabaseInitializer")
public class AuthorBot {

    public static final String BAR = "Bar";
    public static final String MON = "Mon";
    public static final String SUN = "Sun";

    public static final String POST_CONSTRUCT = "AuthorBot_init()";


    @Inject
    private NewsEJB newsEJB;


    @PostConstruct
    private void init(){
        newsEJB.createNews(POST_CONSTRUCT, "@PostConstruct is called after a bean has been created");
    }

    /*
        Careful of default values, it is 0 for seconds/minutes/hours and not *
     */

    @Schedule(second = "*/2" , minute="*", hour="*", persistent=false)
    public void createBar(){
        if(canCreateNews()) {
            newsEJB.createNews(BAR, "Some text");
        }
    }


    @Schedule(second = "*/3", minute="*", hour="*", dayOfWeek = "Sun", persistent=false)
    public void createSun(){
        if(canCreateNews()) {
            newsEJB.createNews(SUN, "Some text");
        }
    }

    @Schedule(second = "*/2", minute="*", hour="*", dayOfWeek = "Mon", persistent=false)
    public void createMon(){
        if(canCreateNews()) {
            newsEJB.createNews(MON, "Some text");
        }
    }

    private boolean canCreateNews(){
        if(newsEJB.getAllNews().size() >= 10){
            return false;
        }
        return true;
    }
}
