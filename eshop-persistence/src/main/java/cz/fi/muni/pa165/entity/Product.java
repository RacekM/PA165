package cz.fi.muni.pa165.entity;


import cz.fi.muni.pa165.enums.Color;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "ESHOP_PRODUCTS")
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false, unique = true)
    private String name;

    private Color color;

    @Temporal(TemporalType.DATE)
    private Date addedDate;

    public Product() {
    }

    public Product(String name, Color color, Date addedDate) {
        this.name = name;
        this.color = color;
        this.addedDate = addedDate;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public Date getAddedDate() {
        return addedDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setAddedDate(Date addedDate) {
        this.addedDate = addedDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
