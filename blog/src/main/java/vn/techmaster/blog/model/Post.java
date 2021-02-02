package vn.techmaster.blog.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "post")
@Table(name = "post")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Indexed
public class Post { 
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length=255)
    @FullTextField
    private String title;
    @Column(length=3000)
    @FullTextField
    private String content;
    private LocalDateTime lastUpdate;
    @Enumerated(EnumType.STRING)
    private StatusType status; // = StatusType.NOT_FIXED;
    @Enumerated(EnumType.STRING)
    private RatingPoint rating; // = RatingPoint.ONE;

    @PrePersist //Trước khi lưu khi khởi tạo record
    public void prePersist() {
        lastUpdate = LocalDateTime.now();
    }
    @PreUpdate //Khi cập nhật record
    public void preUpdate() {
        lastUpdate = LocalDateTime.now();
    }

    public Post(String title, String content) {
        this.title = title;
        this.content = content;
        // this.status = StatusType.NOT_FIXED;
        this.rating = RatingPoint.ONE;
    }
    //-------
    @OneToMany(
        cascade = CascadeType.ALL,
        orphanRemoval = true
    )
    @JoinColumn(name = "post_id")
    private List<Comment> comments = new ArrayList<>();
    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setPost(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setPost(null);
    }
    //------
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;  //Tác giả viết post

    //------------
    //Quan hệ nhiều nhiều:
    //- Một post được phân loại bởi 1 hay nhiều tag. 
    //- Ngược lại mỗi tag dùng để phân loại nhiều post

    // @ManyToMany
    // @JoinTable(
    //     name = "post_tag",
    //     joinColumns = @JoinColumn(name = "post_id"),
    //     inverseJoinColumns = @JoinColumn(name = "tag_id")
    // )
    // private Set<Tag> tags = new HashSet<>();
    
    // public void addTag(Tag tag) {
    //     tags.add(tag);
    //     tag.getPosts().add(this);        
    // }
 
    // public void removeTag(Tag tag) {
    //     tags.remove(tag);
    //     tag.getPosts().remove(this);
    // }
}
