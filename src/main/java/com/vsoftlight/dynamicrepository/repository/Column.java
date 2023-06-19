package com.vsoftlight.dynamicrepository.repository;

import com.vsoftlight.dynamicrepository.entity.AbstractAuditingEntity;
import lombok.Getter;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Column extends Criteria {
    private Class<? extends AbstractAuditingEntity> type;
    @Getter
    private List<Field> columns = new ArrayList<>();

    protected Column(Class<? extends AbstractAuditingEntity> type) {
        super(type);
        this.type = type;

    }

    public Column columns() {
        ReflectionUtils.doWithFields(type, columns::add);
        columns.removeIf(field -> Modifier.isTransient(field.getModifiers()));
        return this;
    }

    public Column exclude(String... columnName) {
        Arrays.stream(columnName).forEach(name -> columns.removeIf(field -> field.getName().equals(name)));
        return this;
    }

    public Column include(String... columnName) {
        columns = columns.stream()
                .filter(field -> Arrays.stream(columnName).toList().contains(field.getName()))
                .toList();
        return this;
    }
}
