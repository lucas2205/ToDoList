package com.todolist.ensolvers.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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

        @ManyToOne()
        @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
        @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
        @NotNull(message = "")
        private User user;

        public Set<Category> getCategories(){
                return this.categories;
        }



}
