package com.souvik.StudentManagement.Implementation;

import com.souvik.StudentManagement.Repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentImpl {

    @Autowired
    private StudentRepository studentRepo;


}
