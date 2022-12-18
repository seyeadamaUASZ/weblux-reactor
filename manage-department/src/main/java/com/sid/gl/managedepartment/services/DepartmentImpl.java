package com.sid.gl.managedepartment.services;

import com.sid.gl.managedepartment.dto.DepartmentDTO;
import com.sid.gl.managedepartment.exceptions.DepartmentException;
import com.sid.gl.managedepartment.mappers.DepartmentMapper;
import com.sid.gl.managedepartment.models.Department;
import com.sid.gl.managedepartment.repositories.DepartmentRepository;
import com.sid.gl.managedepartment.response.DepartmentMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Slf4j
public class DepartmentImpl implements IDepartment{
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    @Override
    public Department addDepartment(DepartmentDTO request) {
        log.info(String.format("saved entity on database on body %s",request));
        return departmentRepository.save(departmentMapper.convertToDepartment(request));
    }

    @Override
    public List<DepartmentDTO> listDepartments() {
        return departmentRepository.findAll()
                .stream()
                .map(department -> departmentMapper.convertToDTO(department))
                .collect(Collectors.toList());
    }

    @Override
    public DepartmentDTO getDepartment(Long id) {
        Department department = departmentRepository
                .findById(id).orElseThrow(()->new DepartmentException(DepartmentMessage.CA_NOT_FOUND,"Department with id "+id));
        return departmentMapper.convertToDTO(department);
    }
}
