package ru.icbcom.aistdapsdkjava.impl.physicalstructure;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObject;
import ru.icbcom.aistdapsdkjava.api.physicalstructure.PhysicalStructureObjectList;
import ru.icbcom.aistdapsdkjava.impl.datastore.DataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.DummyDataStore;
import ru.icbcom.aistdapsdkjava.impl.datastore.objectmapper.DefaultObjectMapperFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static ru.icbcom.aistdapsdkjava.helper.ResourceHelper.loadResource;

class DefaultPhysicalStructureObjectListDeserializationTest {

    private DataStore dataStore;
    private ObjectMapper objectMapper;

    public DefaultPhysicalStructureObjectListDeserializationTest() {
        this.dataStore = new DummyDataStore();
        this.objectMapper = new DefaultObjectMapperFactory().create(dataStore);
    }

    @Test
    void deserializationShouldWorkProperly() throws IOException {
        String json = loadResource("deserialization/physicalStructureObjectList.json");

        PhysicalStructureObjectList physicalStructureObjectList = objectMapper.readValue(json, DefaultPhysicalStructureObjectList.class);

        assertThat(physicalStructureObjectList, allOf(
                hasProperty("size", is(20L)),
                hasProperty("totalElements", is(2L)),
                hasProperty("totalPages", is(1L)),
                hasProperty("number", is(0L)),
                hasProperty("links", contains(
                        allOf(
                                hasProperty("rel", is("self")),
                                hasProperty("href", is("http://127.0.0.1:8080/physicalStructure?page=0&size=20")),
                                hasProperty("templated", is(false))
                        ),
                        allOf(
                                hasProperty("rel", is("curies")),
                                hasProperty("href", is("http://127.0.0.1:8080/documentation/{rel}.html")),
                                hasProperty("templated", is(true))
                        )
                )),
                hasProperty("dataStore", sameInstance(dataStore))
        ));
        List<PhysicalStructureObject> physicalStructureObjectsInCurrentPortion = new ArrayList<>((int) physicalStructureObjectList.getSize());
        Iterator<PhysicalStructureObject> iterator = physicalStructureObjectList.iterator();
        for (int i = 0; i < physicalStructureObjectList.getSize(); i++) {
            if (iterator.hasNext()) {
                PhysicalStructureObject physicalStructureObject = iterator.next();
                physicalStructureObjectsInCurrentPortion.add(physicalStructureObject);
            }
        }
        assertThat(physicalStructureObjectsInCurrentPortion, contains(
                allOf(
                        hasProperty("id", is(10007L)),
                        hasProperty("objectTypeId", is(41L)),
                        hasProperty("name", is("Система АСКУЭ")),
                        hasProperty("attributeValues", allOf(
                                hasEntry("Host", "localhost"),
                                hasEntry("Port", "1433"),
                                hasEntry("Database", "aeinfov2_mts"),
                                hasEntry("Login", "sa"),
                                hasEntry("Password", "a2lsa2Fib3Q="),
                                hasEntry("MeasurementsPollRate", "10"),
                                hasEntry("EnergyProfilesPollRate", "3600")
                        )),
                        hasProperty("descendantsCount", is(1L)),
                        hasProperty("devicesCount", is(0L)),
                        hasProperty("links", contains(
                                allOf(
                                        hasProperty("rel", is("self")),
                                        hasProperty("href", is("http://127.0.0.1:8080/physicalStructure/10007")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:physicalStructureObject")),
                                        hasProperty("href", is("http://127.0.0.1:8080/physicalStructure/10007")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:objectType")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/41")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:descendants")),
                                        hasProperty("href", is("http://127.0.0.1:8080/physicalStructure/10007/descendants{?page,size,sort}")),
                                        hasProperty("templated", is(true))
                                )
                        )),
                        hasProperty("dataStore", sameInstance(dataStore))
                ),
                allOf(
                        hasProperty("id", is(10027L)),
                        hasProperty("objectTypeId", is(41L)),
                        hasProperty("name", is("New ASKUE system")),
                        hasProperty("attributeValues", allOf(
                                hasEntry("Host", "localhost"),
                                hasEntry("Port", "1433"),
                                hasEntry("Database", "aeinfov2_mts"),
                                hasEntry("Login", "sa"),
                                hasEntry("Password", "pswd"),
                                hasEntry("MeasurementsPollRate", "10"),
                                hasEntry("EnergyProfilesPollRate", "3600")
                        )),
                        hasProperty("descendantsCount", is(0L)),
                        hasProperty("devicesCount", is(0L)),
                        hasProperty("links", contains(
                                allOf(
                                        hasProperty("rel", is("self")),
                                        hasProperty("href", is("http://127.0.0.1:8080/physicalStructure/10027")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:physicalStructureObject")),
                                        hasProperty("href", is("http://127.0.0.1:8080/physicalStructure/10027")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:objectType")),
                                        hasProperty("href", is("http://127.0.0.1:8080/objectTypes/41")),
                                        hasProperty("templated", is(false))
                                ),
                                allOf(
                                        hasProperty("rel", is("dap:descendants")),
                                        hasProperty("href", is("http://127.0.0.1:8080/physicalStructure/10027/descendants{?page,size,sort}")),
                                        hasProperty("templated", is(true))
                                )
                        )),
                        hasProperty("dataStore", sameInstance(dataStore))
                )
        ));
    }

    @Test
    void emptyCollectionDeserializationShouldWorkProperly() throws IOException {
        String json = loadResource("deserialization/emptyPhysicalStructureObjectList.json");

        PhysicalStructureObjectList physicalStructureObjectList = objectMapper.readValue(json, DefaultPhysicalStructureObjectList.class);

        assertThat(physicalStructureObjectList, allOf(
                hasProperty("size", is(20L)),
                hasProperty("totalElements", is(0L)),
                hasProperty("totalPages", is(0L)),
                hasProperty("number", is(0L)),
                hasProperty("links", contains(
                        allOf(
                                hasProperty("rel", is("self")),
                                hasProperty("href", is("http://127.0.0.1:8080/physicalStructure?page=0&size=20")),
                                hasProperty("templated", is(false))
                        )
                )),
                hasProperty("dataStore", sameInstance(dataStore))
        ));
        assertFalse(physicalStructureObjectList.iterator().hasNext());
    }

}