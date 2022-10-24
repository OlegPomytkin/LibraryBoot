package ru.pombyte.LibraryBoot.models;

import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name="book")
public class Book {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Название не может быть пустым")
    @Size(min = 1, max = 120, message = "Название должно быть в диапазоне от 1 до 120 символов")
    @Column(name="name")
    private String name;
    @NotEmpty(message = "Автор не может быть пустым")
    @Size(min = 2, max = 40, message = "Название должно быть в диапазоне от 2 до 40 символов")
    @Column(name="author")
    private String author;
    @Min(value = 1820, message = "Год издания не может быть ранее 1820")
    @Max(value = 2022, message = "Год издания не может быть позднее 2022")
    @Column(name="year")
    private int year;
    @ManyToOne
    @JoinColumn(name = "owner", referencedColumnName = "id")
    private Person owner;
    @Column(name="date_of_borrow")
    @Temporal(TemporalType.DATE)
    private Date dateOfBorrow;
    @Transient
    private Boolean tooLate;

    public Book() {}


    public Boolean getTooLate() {
        return tooLate;
    }
    public void setTooLate(Boolean tooLate) {
        this.tooLate = tooLate;
    }

    public Book(String name, String author, int year) {
        this.name = name;
        this.author = author;
        this.year = year;
    }
    public Date getDateOfBorrow() {
        return dateOfBorrow;
    }
    public void setDateOfBorrow(Date dateOfBorrow) {
        this.dateOfBorrow = dateOfBorrow;
    }
    public Person getOwner() {
        return owner;
    }
    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
