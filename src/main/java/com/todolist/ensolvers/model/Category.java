package com.todolist.ensolvers.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Entity
@Table(name = "categories")
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique=true)
    private String name;

    @JsonBackReference
    @ManyToMany(mappedBy = "categories")
    private Set<Notes> notes;

    @ManyToOne()
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @NotNull(message = "")
    private User user;


    public Long getId(){
        return this.id;
    }

    public Long getUserId(){ return this.user.getId(); }

    public String getName(){
        return this.name;
    }

    public Set<Notes> getNotes(){return  this.notes;}

    public void removeNotes(long noteId) {
        Notes note = this.notes.stream().filter(t -> t.getId() == noteId).findFirst().orElse(null);
        if (note != null) {
            this.notes.remove(note);
            note.getCategories().remove(this);
        }
    }

}
