package org.takim2.insan_kaynaklari_api.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.takim2.insan_kaynaklari_api.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment , Long> {

    Page<Comment> findAll(Pageable pageable);
}
