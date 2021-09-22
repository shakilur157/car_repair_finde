package car.repair.finder.models;

import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.BatchSize;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;

@Entity
@Table(name = "services")
@Validated
@Getter
@Setter
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotNull
    @BatchSize(size = 500)
    private  String name;

    public Services(String name) {
        this.name = name;
    }
    public Services(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public  Services(){}

}
