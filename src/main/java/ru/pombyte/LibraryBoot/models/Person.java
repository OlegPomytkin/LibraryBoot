package ru.pombyte.LibraryBoot.models;



import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Имя не может быть пустым")
    @Size(min = 2, max = 40, message = "Имя должно быть в диапазоне от 2 до 40 символов")
    @Column(name="name")
    private String name;
    @Min(value = 1920, message = "Год рождения не может быть ранее 1920")
    @Max(value = 2017, message = "Год рождения не может быть позднее 2017")
    @Column(name="year")
    private int year;
    @OneToMany(mappedBy = "owner")
    private List<Book> books;

    public Person() {}

    public Person(int id, String name, int year) {
        this.id = id;
        this.name = name;
        this.year = year;

    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && year == person.year && Objects.equals(name, person.name);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id, name, year);
    }
}
