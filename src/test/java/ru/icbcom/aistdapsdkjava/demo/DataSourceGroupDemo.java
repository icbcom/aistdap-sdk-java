package ru.icbcom.aistdapsdkjava.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.api.client.Client;
import ru.icbcom.aistdapsdkjava.api.client.Clients;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSource;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSourceCriteria;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSourceList;
import ru.icbcom.aistdapsdkjava.api.datasource.DataSources;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroup;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroupCriteria;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroupList;
import ru.icbcom.aistdapsdkjava.api.datasourcegroup.DataSourceGroups;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypeList;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypes;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
@Disabled
public class DataSourceGroupDemo {

    private Client client;

    @BeforeEach
    void setup() {
        client = Clients.builder()
                .setBaseUrl("http://127.0.0.1:8080/")
                .setLogin("Admin")
                .setPassword("newPassword")
                .build();
    }

    @Test
    void getDataSourceGroupsForObjectType() {
        ObjectTypeList objectTypeList = client.objectTypes().getAll(ObjectTypes.criteria().orderById().ascending().pageSize(1));
        Stream<ObjectType> objectTypeStream = StreamSupport.stream(objectTypeList.spliterator(), false);
        ObjectType objectType = objectTypeStream.findAny().orElseThrow();
        log.info(objectType.toString());

        DataSourceGroupList dataSourceGroups = objectType.getDataSourceGroups();
        log.info("Total elements: {}", dataSourceGroups.getTotalElements());
        for (DataSourceGroup dataSourceGroup : dataSourceGroups) {
            log.info(dataSourceGroup.toString());
        }
    }

    @Test
    void getDataSourceGroupsWithCriteriaForObjectType() {
        ObjectTypeList objectTypeList = client.objectTypes().getAll(ObjectTypes.criteria().orderById().ascending().pageSize(1));
        Stream<ObjectType> objectTypeStream = StreamSupport.stream(objectTypeList.spliterator(), false);
        ObjectType objectType = objectTypeStream.findAny().orElseThrow();
        log.info(objectType.toString());

        DataSourceGroupCriteria criteria = DataSourceGroups.criteria()
                .orderByCaption().ascending()
                .pageSize(100);
        DataSourceGroupList dataSourceGroups = objectType.getDataSourceGroups();
        log.info("Total elements: {}", dataSourceGroups.getTotalElements());
        for (DataSourceGroup dataSourceGroup : dataSourceGroups) {
            log.info(dataSourceGroup.toString());
        }
    }

    @Test
    void getDataSourceGroupByIdShouldWorkProperly() {
        ObjectTypeList objectTypeList = client.objectTypes().getAll(ObjectTypes.criteria().orderById().ascending().pageSize(1));
        Stream<ObjectType> objectTypeStream = StreamSupport.stream(objectTypeList.spliterator(), false);
        ObjectType objectType = objectTypeStream.findAny().orElseThrow();
        log.info(objectType.toString());

        DataSourceGroup dataSourceGroup = objectType.getDataSourceGroupById(5L).orElseThrow();
        log.info(dataSourceGroup.toString());
    }

    @Test
    void createDataSourceGroupShouldWorkProperly() {
        ObjectTypeList objectTypeList = client.objectTypes().getAll(ObjectTypes.criteria().orderById().ascending().pageSize(1));
        Stream<ObjectType> objectTypeStream = StreamSupport.stream(objectTypeList.spliterator(), false);
        ObjectType objectType = objectTypeStream.findAny().orElseThrow();
        log.info(objectType.toString());

        DataSourceGroup dataSourceGroup = client.instantiate(DataSourceGroup.class)
                .setDataSourceGroupId(123L)
                .setCaption("Новая группа источников данных");
        DataSourceGroup createdDataSourceGroup = objectType.createDataSourceGroup(dataSourceGroup);
        log.info(createdDataSourceGroup.toString());
    }

    @Test
    void updateShouldWorkProperly() {
        ObjectTypeList objectTypeList = client.objectTypes().getAll();
        Stream<ObjectType> objectTypeStream = StreamSupport.stream(objectTypeList.spliterator(), false);
        ObjectType objectType = objectTypeStream.filter(ot -> ot.getId() == 1).findAny().orElseThrow();
        log.info(objectType.toString());

        DataSourceGroup dataSourceGroup = objectType.getDataSourceGroupById(1L).orElseThrow();
        dataSourceGroup.setCaption(RandomStringUtils.random(20, true, true));

        dataSourceGroup.save();
    }

    @Test
    void deleteShouldWorkProperly() {
        ObjectTypeList objectTypeList = client.objectTypes().getAll();
        Stream<ObjectType> objectTypeStream = StreamSupport.stream(objectTypeList.spliterator(), false);
        ObjectType objectType = objectTypeStream.filter(ot -> ot.getId() == 1).findAny().orElseThrow();
        log.info(objectType.toString());

        DataSourceGroup dataSourceGroup = objectType.getDataSourceGroupById(123L).orElseThrow();
        log.info(dataSourceGroup.toString());

        dataSourceGroup.delete();
    }

    @Test
    void getObjectType() {
        ObjectTypeList objectTypeList = client.objectTypes().getAll();
        Stream<ObjectType> objectTypeStream = StreamSupport.stream(objectTypeList.spliterator(), false);
        ObjectType objectType = objectTypeStream.filter(ot -> ot.getId() == 1).findAny().orElseThrow();
        log.info(objectType.toString());

        DataSourceGroup dataSourceGroup = objectType.getDataSourceGroupById(1L).orElseThrow();
        log.info(dataSourceGroup.getObjectType().toString());
    }

    @Test
    void getDataSources() {
        ObjectTypeList objectTypeList = client.objectTypes().getAll();
        Stream<ObjectType> objectTypeStream = StreamSupport.stream(objectTypeList.spliterator(), false);
        ObjectType objectType = objectTypeStream.filter(ot -> ot.getId() == 1).findAny().orElseThrow();
        log.info(objectType.toString());

        DataSourceGroup dataSourceGroup = objectType.getDataSourceGroupById(3L).orElseThrow();
        DataSourceList dataSources = dataSourceGroup.getDataSources(DataSources.criteria().orderByCaption().descending().pageSize(100));
        for (DataSource dataSource : dataSources) {
            log.info(dataSource.toString());
        }
    }

}
