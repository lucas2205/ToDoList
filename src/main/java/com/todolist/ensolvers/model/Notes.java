package com.todolist.ensolvers.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;

@Entity
@Table(name = "notes")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notes {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotNull(message = "title must not be null")
        private String title;

        @NotNull(message = "content must not be null")
        @Column(columnDefinition = "text")
        private String content;

        @NotNull
        private boolean archive;

        @ManyToMany(cascade = {
                CascadeType.ALL
        })
        @JoinTable(
                name = "categories_notes",
                joinColumns = {@JoinColumn(name = "categories_id")},
                inverseJoinColumns = {@JoinColumn(name = "notes_id")})
        private Set<Category> categories;

        public Set<Category> getCategories(){
                return this.categories;
        }



}
