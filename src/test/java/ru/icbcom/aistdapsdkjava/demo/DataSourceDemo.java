/*
 * Copyright © 2018-2019 Icbcom
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypeList;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectTypes;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Slf4j
@Disabled
public class DataSourceDemo {

    private Client client;

    @BeforeEach
    void setup() {
        client = Clients.builder()
                .setBaseUrl("http://127.0.0.1:8080/")
                .setLogin("Admin")
                .setPassword("Admin")
                .build();
    }

    @Test
    void getDataSourcesForObjectType() {
        ObjectTypeList objectTypeList = client.objectTypes().getAll(ObjectTypes.criteria().orderById().ascending().pageSize(1));
        Stream<ObjectType> objectTypeStream = StreamSupport.stream(objectTypeList.spliterator(), false);
        ObjectType objectType = objectTypeStream.findAny().orElseThrow();
        log.info(objectType.toString());

        DataSourceList dataSources = objectType.getDataSources();
        log.info("Total elements: {}", dataSources.getTotalElements());
        for (DataSource dataSource : dataSources) {
            log.info(dataSource.toString());
        }
    }

    @Test
    void getDataSourcesWithCriteriaForObjectType() {
        ObjectTypeList objectTypeList = client.objectTypes().getAll(ObjectTypes.criteria().orderById().ascending().pageSize(1));
        Stream<ObjectType> objectTypeStream = StreamSupport.stream(objectTypeList.spliterator(), false);
        ObjectType objectType = objectTypeStream.findAny().orElseThrow();
        log.info(objectType.toString());

        DataSourceCriteria criteria = DataSources.criteria()
                .orderByMeasureItem().ascending()
                .pageSize(100);
        DataSourceList dataSources = objectType.getDataSources(criteria);
        log.info("Total elements: {}", dataSources.getTotalElements());
        for (DataSource dataSource : dataSources) {
            log.info(dataSource.toString());
        }
    }

    @Test
    void creationShouldWorkProperly() {
        ObjectTypeList objectTypeList = client.objectTypes().getAll();
        Stream<ObjectType> objectTypeStream = StreamSupport.stream(objectTypeList.spliterator(), false);
        ObjectType objectType = objectTypeStream.filter(ot -> ot.getId() == 1).findAny().orElseThrow();
        log.info(objectType.toString());

        DataSource dataSource = client.instantiate(DataSource.class)
                .setDataSourceId(123L)
                .setCaption("Новый источник данных для счетчика Меркурий 230")
                .setMeasureItem("км/ч")
                .setDataSourceGroupId(1L);

        dataSource = objectType.createDataSource(dataSource);
        log.info(dataSource.toString());
    }

    @Test
    void updateShouldWorkProperly() {
        ObjectTypeList objectTypeList = client.objectTypes().getAll();
        Stream<ObjectType> objectTypeStream = StreamSupport.stream(objectTypeList.spliterator(), false);
        ObjectType objectType = objectTypeStream.filter(ot -> ot.getId() == 1).findAny().orElseThrow();
        log.info(objectType.toString());

        DataSourceList dataSources = objectType.getDataSources();
        Stream<DataSource> dataSourceStream = StreamSupport.stream(dataSources.spliterator(), false);
        DataSource dataSource = dataSourceStream.filter(ds -> ds.getDataSourceId() == 47).findAny().orElseThrow();
        System.out.println(dataSource);

        dataSource.setCaption(RandomStringUtils.random(20, true, true));
        dataSource.setMeasureItem(RandomStringUtils.random(5, true, true));

        dataSource.save();
    }

    @Test
    void deleteShouldWorkProperly() {
        ObjectTypeList objectTypeList = client.objectTypes().getAll();
        Stream<ObjectType> objectTypeStream = StreamSupport.stream(objectTypeList.spliterator(), false);
        ObjectType objectType = objectTypeStream.filter(ot -> ot.getId() == 1).findAny().orElseThrow();
        log.info(objectType.toString());

        DataSourceList dataSources = objectType.getDataSources();
        Stream<DataSource> dataSourceStream = StreamSupport.stream(dataSources.spliterator(), false);
        DataSource dataSource = dataSourceStream.filter(ds -> ds.getDataSourceId() == 123).findAny().orElseThrow();
        System.out.println(dataSource);

        dataSource.delete();
    }

    @Test
    void getDataSourceByIdShouldWorkProperly() {
        ObjectTypeList objectTypeList = client.objectTypes().getAll();
        Stream<ObjectType> objectTypeStream = StreamSupport.stream(objectTypeList.spliterator(), false);
        ObjectType objectType = objectTypeStream.filter(ot -> ot.getId() == 1).findAny().orElseThrow();
        log.info(objectType.toString());

        DataSource dataSource = objectType.getDataSourceById(40L).orElseThrow();
        log.info(dataSource.toString());
    }

    @Test
    void getObjectTypeShouldWorkProperly() {
        ObjectTypeList objectTypeList = client.objectTypes().getAll();
        Stream<ObjectType> objectTypeStream = StreamSupport.stream(objectTypeList.spliterator(), false);
        ObjectType objectType = objectTypeStream.filter(ot -> ot.getId() == 1).findAny().orElseThrow();
        log.info(objectType.toString());

        DataSource dataSource = objectType.getDataSourceById(40L).orElseThrow();
        log.info(dataSource.toString());

        ObjectType parentObjectType = dataSource.getObjectType();
        log.info(parentObjectType.toString());
    }

    @Test
    void getDataSourceGroupShouldWorkProperly() {
        ObjectTypeList objectTypeList = client.objectTypes().getAll();
        Stream<ObjectType> objectTypeStream = StreamSupport.stream(objectTypeList.spliterator(), false);
        ObjectType objectType = objectTypeStream.filter(ot -> ot.getId() == 1).findAny().orElseThrow();
        log.info(objectType.toString());

        DataSource dataSource = objectType.getDataSourceById(2L).orElseThrow();
        log.info(dataSource.toString());

        DataSourceGroup dataSourceGroup = dataSource.getDataSourceGroup();
        log.info(dataSourceGroup.toString());
    }

}