package me.mandaveiga.concretesolutions.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Date;
import java.util.UUID;

@MappedSuperclass
@Data
public abstract class AbstractModel {

    @Id
    private UUID id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private Date created;

    @LastModifiedDate
    @Column
    private Date modified;

    public AbstractModel() {
        this.id = UUID.randomUUID();
        this.created = new Date();
        this.modified = new Date();
    }
}
