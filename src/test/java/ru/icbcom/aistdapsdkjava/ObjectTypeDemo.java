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
    void demo() {
        Client client = Clients.builder()
                .setBaseUrl("http://127.0.0.1:8080/")
                .setLogin("Admin")
                .setPassword("newPassword2")
                .build();

        ObjectTypeCriteria criteria = ObjectTypes.criteria()
                .orderByName().ascending()
                .pageSize(1);
        ObjectTypeList objectTypeList = client.getObjectTypes(criteria);

//        ObjectTypeList objectTypeList = client.getObjectTypes();
        for (ObjectType objectType : objectTypeList) {
            log.info(objectType.toString());
        }
    }

}
