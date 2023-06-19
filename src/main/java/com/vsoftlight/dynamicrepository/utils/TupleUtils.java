package com.vsoftlight.dynamicrepository.utils;

import com.vsoftlight.dynamicrepository.repository.TupleField;
import jakarta.persistence.Tuple;
import lombok.SneakyThrows;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class TupleUtils {

    private TupleUtils() {}

    public static <U> U map(Tuple tuple, U u) {
        final Class<?> type = u.getClass();
        List<Field> fields = new ArrayList<>();
        ReflectionUtils.doWithFields(type, fields::add);
        fields.removeIf(field -> !field.isAnnotationPresent(TupleField.class));
        fields.forEach(field -> {
            final String name = field.getName();
            try {
                final TupleField annotation = field.getAnnotation(TupleField.class);
                final String alias = annotation.alias();
                final Class<?> resultType = annotation.type();
                final Method method = type.getDeclaredMethod("set" + StringUtils.capitalize(name));
                method.invoke(u, tuple.get(alias, resultType));
            } catch (IllegalArgumentException ignore) {
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        return u;
    }

    public static <U> List<U> map(List<Tuple> tuples, Supplier<U> supplier) {
        return tuples.stream()
                .map(tuple -> map(tuple, supplier.get()))
                .toList();
    }
}
