package ru.icbcom.aistdapsdkjava;

import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Link;
import ru.icbcom.aistdapsdkjava.api.client.Client;
import ru.icbcom.aistdapsdkjava.api.client.Clients;
import ru.icbcom.aistdapsdkjava.api.objecttype.*;

import java.util.List;

public class ObjectTypeDemo {

    @Test
    void demo() {
        Client client = Clients.builder()
                .setBaseUrl("http://127.0.0.1:8080/")
                .setLogin("Admin")
                .setPassword("newPassword")
                .build();

        ObjectTypeCriteria criteria = ObjectTypes.criteria()
                .orderByCaption().descending()
                .pageSize(100);
        ObjectTypeList objectTypeList = client.getObjectTypes(criteria);

        if (objectTypeList != null) {
            for (ObjectType objectType : objectTypeList) {
                System.out.println(objectType);
            }
        }
    }

}
