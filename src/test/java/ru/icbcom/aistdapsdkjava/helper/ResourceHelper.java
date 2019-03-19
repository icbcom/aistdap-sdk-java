package ru.icbcom.aistdapsdkjava.helper;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;

import java.io.StringWriter;
import java.nio.file.Files;

public final class ResourceHelper {
    @SneakyThrows
    public static String loadResource(String path) {
        ClassPathResource resource = new ClassPathResource(path);
        return new String(Files.readAllBytes(resource.getFile().toPath()));
    }

    @SneakyThrows
    public static String loadTemplatedResource(String path, Object scope) {
        MustacheFactory mustacheFactory = new DefaultMustacheFactory();
        Mustache mustache = mustacheFactory.compile(path);

        StringWriter writer = new StringWriter();
        mustache.execute(writer, scope).flush();
        return writer.toString();
    }
}
