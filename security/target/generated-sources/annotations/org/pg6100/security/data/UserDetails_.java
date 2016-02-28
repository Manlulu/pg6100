package org.pg6100.security.data;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.0.v20130507-rNA", date="2016-02-26T08:58:38")
@StaticMetamodel(UserDetails.class)
public class UserDetails_ { 

    public static volatile SingularAttribute<UserDetails, String> salt;
    public static volatile SingularAttribute<UserDetails, String> userId;
    public static volatile SingularAttribute<UserDetails, String> hash;

}