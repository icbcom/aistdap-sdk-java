package ru.icbcom.aistdapsdkjava.impl.resource;

import org.junit.jupiter.api.Test;
import ru.icbcom.aistdapsdkjava.api.lang.UnknownClassException;
import ru.icbcom.aistdapsdkjava.api.objecttype.Attribute;
import ru.icbcom.aistdapsdkjava.api.objecttype.EnumSetValue;
import ru.icbcom.aistdapsdkjava.api.objecttype.ObjectType;
import ru.icbcom.aistdapsdkjava.api.objecttype.Section;
import ru.icbcom.aistdapsdkjava.api.resource.Resource;
import ru.icbcom.aistdapsdkjava.api.resource.ResourceFactory;
import ru.icbcom.aistdapsdkjava.api.resource.VoidResource;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultAttribute;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultEnumSetValue;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultObjectType;
import ru.icbcom.aistdapsdkjava.impl.objectType.DefaultSection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DefaultResourceFactoryTest {

    private ResourceFactory resourceFactory = new DefaultResourceFactory();

    @Test
    void instantiateShouldWorkProperly() {
        assertEquals(DefaultEnumSetValue.class, resourceFactory.instantiate(EnumSetValue.class).getClass());
        assertEquals(DefaultAttribute.class, resourceFactory.instantiate(Attribute.class).getClass());
        assertEquals(DefaultSection.class, resourceFactory.instantiate(Section.class).getClass());
        assertEquals(DefaultObjectType.class, resourceFactory.instantiate(ObjectType.class).getClass());
        assertEquals(DefaultVoidResource.class, resourceFactory.instantiate(VoidResource.class).getClass());
    }

    @Test
    void instantiationShouldFailIfClassIsUnknown() {
        UnknownClassException exception = assertThrows(UnknownClassException.class, () -> resourceFactory.instantiate(UnknownResource.class));
        assertEquals("Implementation class not found for: " + UnknownResource.class.getName(), exception.getMessage());
    }

    @Test
    void instantiationShouldWorkWhenConcreteClassPassedAsArgument() {
        assertEquals(DefaultObjectType.class, resourceFactory.instantiate(DefaultObjectType.class).getClass());
    }

    private interface UnknownResource extends Resource {
    }

}