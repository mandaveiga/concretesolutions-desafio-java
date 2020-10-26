package me.mandaveiga.concretesolutions.model.user.phone;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import me.mandaveiga.concretesolutions.model.AbstractModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "phones")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class Phone extends AbstractModel {

    @Column(nullable = false)
    private int number;

    @Column(nullable = false)
    private int ddd;

    @Column(name = "user_id")
    private UUID userId;

    public Phone(int number, int ddd) {
        this.number = number;
        this.ddd = ddd;
    }
}
