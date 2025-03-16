package org.sibsutis.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.sibsutis.store.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
}
