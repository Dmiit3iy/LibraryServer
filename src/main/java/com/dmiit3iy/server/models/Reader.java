package com.dmiit3iy.server.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Cascade;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "readers")
@Data
@NoArgsConstructor

public class Reader extends User {

    private int violationCount;
    @ToString.Exclude
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reader")
    @Cascade(value = org.hibernate.annotations.CascadeType.DELETE)
    private List<Order> orderList;

    public boolean addOrder(Order order) {
        return this.orderList.add(order);
    }

}
