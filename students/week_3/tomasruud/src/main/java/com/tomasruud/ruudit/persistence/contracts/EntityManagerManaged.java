package com.tomasruud.ruudit.persistence.contracts;

import javax.persistence.EntityManager;

public interface EntityManagerManaged {

    void setManager( EntityManager manager );
}
