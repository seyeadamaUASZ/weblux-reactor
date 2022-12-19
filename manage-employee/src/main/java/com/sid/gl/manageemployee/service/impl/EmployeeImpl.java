package com.sid.gl.manageemployee.service.impl;

import com.sid.gl.manageemployee.dto.ApiResponse;
import com.sid.gl.manageemployee.dto.Department;
import com.sid.gl.manageemployee.dto.EmployeeRequest;
import com.sid.gl.manageemployee.dto.EmployeeResponse;
import com.sid.gl.manageemployee.exceptions.ResourceNotFoundException;
import com.sid.gl.manageemployee.mappers.EmployeeMapper;
import com.sid.gl.manageemployee.models.Employee;
import com.sid.gl.manageemployee.repository.EmployeeRepository;
import com.sid.gl.manageemployee.service.IEmployee;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeImpl implements IEmployee {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final WebClientService webClientService;

    @Override
    public Employee saveEmployee(EmployeeRequest employeeRequest) throws ResourceNotFoundException {
        //for to save employee
        ApiResponse response =
                (ApiResponse) this.webClientService.findDepById(employeeRequest.getDepartmentId()).block();

        System.out.println(" api response "+response);
        if(Objects.isNull(response.getData()))
            throw new ResourceNotFoundException("Department not found with id : "+employeeRequest.getDepartmentId());
        return employeeRepository.save(employeeMapper.convertToEmployee(employeeRequest));
    }

    @Override
    public List<EmployeeResponse> listEmployee() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::convertEmployeeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponse getEmployee(Long id) throws ResourceNotFoundException {
        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(()->new ResourceNotFoundException("employee with id not found"));
        return employeeMapper.convertEmployeeResponse(employee);
    }

    @Override
    public Map<String, List<EmployeeResponse>> listEmployeeByType() {
        Map<String,List<EmployeeResponse>> listMap=
                employeeRepository.findAll().stream()
                        .map(employeeMapper::convertEmployeeResponse)
                        .filter(employeeResponse -> employeeResponse.getEmployeeType()!=null)
                        .collect(Collectors.groupingBy(EmployeeResponse::getEmployeeTypeString));
        return listMap;

    }

    @Override
    public List<EmployeeResponse> listEmployeeByType(String employeeType) {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::convertEmployeeResponse)
                .filter(employeeResponse -> employeeResponse.getEmployeeType().name().equals(employeeType))
                .collect(Collectors.toList());

    }


}
