package com.initcloud.rocket23.file.repository;

import com.initcloud.rocket23.file.entity.FileList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileList,Long> {

}
