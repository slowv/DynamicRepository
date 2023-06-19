package com.vsoftlight.dynamicrepository.repository;

import com.vsoftlight.dynamicrepository.entity.AbstractAuditingEntity;
import com.vsoftlight.dynamicrepository.utils.TupleUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Table;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MasterRepository<T extends AbstractAuditingEntity, ID> {
    private final EntityManager entityManager;

    public <D> List<D> findAll(String sql, Class<D> type) {
        return entityManager.createQuery(sql, type).getResultList();
    }

    public List<T> findAll(Class<T> type) {
        return findAll("select * from " + getTable(type), type);
    }

    public List<T> findAll(Criteria criteria) {
        final Column column = criteria.getColumn().columns();
        final List<Field> fields = column.getColumns();
        String sql = "select ";
        sql += fields.stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));
        sql += " from" + getTable(criteria.getType());
        return findAll(sql, (Class<T>) criteria.getType());
    }

    // Tuple
    private <D> List<D> findAll(String sql, Supplier<D> supplier) {
        final List<Tuple> resultList = entityManager.createQuery(sql, Tuple.class).getResultList();
        return TupleUtils.map(resultList, supplier);
    }

    private String getTable(Class<?> type) {
        return type.getAnnotation(Table.class).name();
    }


}
