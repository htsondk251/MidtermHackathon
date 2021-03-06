package vn.techmaster.blog.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "tag")
@Table(name = "tag")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Tag {
    @Id private long id;
    private String name;

    // @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    // List<Post> posts = new ArrayList<>();

    // public Tag(String name) {
    //     this.name = name;
    // }
}
