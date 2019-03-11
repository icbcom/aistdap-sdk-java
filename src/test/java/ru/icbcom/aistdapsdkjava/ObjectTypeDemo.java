package ru.icbcom.aistdapsdkjava;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.api.client.Client;
import ru.icbcom.aistdapsdkjava.api.client.Clients;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypeCriteria;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypeList;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypes;

@Slf4j
public class ObjectTypeDemo {

    @Test
    void demo() throws InterruptedException {
        Client client = Clients.builder()
                .setBaseUrl("http://127.0.0.1:8080/")
                .setLogin("Admin")
                .setPassword("newPassword")
                .build();

        ObjectTypeCriteria criteria = ObjectTypes.criteria()
                .orderById().ascending()
                .pageSize(200);
        ObjectTypeList objectTypeList = client.getObjectTypes(criteria);
        for (ObjectType objectType : objectTypeList) {
            log.info(objectType.toString());
        }
    }

}
