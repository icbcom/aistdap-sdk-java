package ru.icbcom.aistdapsdkjava;

import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypeCriteria;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypes;

public class ObjectTypeDemo {

    @Test
    void demo() {
        ObjectTypeCriteria criteria = ObjectTypes.criteria()
                .orderByCaption()
                .descending();

        System.out.println(criteria);
    }

}
