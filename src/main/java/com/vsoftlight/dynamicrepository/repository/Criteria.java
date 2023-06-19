package com.vsoftlight.dynamicrepository.repository;

import com.vsoftlight.dynamicrepository.entity.AbstractAuditingEntity;
import lombok.Getter;

@Getter
public class Criteria {
    private Class<? extends AbstractAuditingEntity> type;
    private Column column;

    public Criteria(Class<? extends AbstractAuditingEntity> type) {
        this.type = type;
    }

    public Column columnBuilder() {
        this.column = new Column(type);
        return this.column;
    }
}
