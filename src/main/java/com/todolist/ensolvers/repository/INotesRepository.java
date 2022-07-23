package com.todolist.ensolvers.repository;

import com.todolist.ensolvers.model.Notes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface INotesRepository extends JpaRepository<Notes, Long> {

    Set<Notes> findByArchive(Boolean archive);

    List<Notes> findByCategories_Notes_Id(Long id);
}
