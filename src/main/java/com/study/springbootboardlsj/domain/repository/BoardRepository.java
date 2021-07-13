package com.study.springbootboardlsj.domain.repository;

import com.study.springbootboardlsj.domain.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {


}
