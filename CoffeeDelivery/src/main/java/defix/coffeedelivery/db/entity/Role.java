package defix.coffeedelivery.db.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "role")
@NoArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", length = 25)
    private String name;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<Account> users = new HashSet<>();

    @Override
    public String getAuthority() {
        return getName();
    }

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

}