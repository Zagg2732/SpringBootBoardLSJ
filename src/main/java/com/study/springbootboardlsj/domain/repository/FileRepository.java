package com.study.springbootboardlsj.domain.repository;

import com.study.springbootboardlsj.domain.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
