package com.sid.gl.manageemployee.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sid.gl.manageemployee.dto.EmployeeRequest;
import com.sid.gl.manageemployee.dto.EmployeeResponse;
import com.sid.gl.manageemployee.dto.FilterDTO;
import com.sid.gl.manageemployee.enums.EmployeeType;
import com.sid.gl.manageemployee.exceptions.ResourceNotFoundException;
import com.sid.gl.manageemployee.mappers.EmployeeMapper;
import com.sid.gl.manageemployee.models.Employee;
import com.sid.gl.manageemployee.models.Role;
import com.sid.gl.manageemployee.models.UserRoles;
import com.sid.gl.manageemployee.models.query.QuerySpecification;
import com.sid.gl.manageemployee.models.query.SearchCriteria;
import com.sid.gl.manageemployee.models.query.SearchOperation;
import com.sid.gl.manageemployee.repository.EmployeeRepository;
import com.sid.gl.manageemployee.repository.UserRolesRepository;
import com.sid.gl.manageemployee.security.UserPrincipal;
import com.sid.gl.manageemployee.service.IEmployee;
import com.sid.gl.manageemployee.tools.response.BasicResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmployeeImpl implements IEmployee, UserDetailsService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final WebClientService webClientService;

    private final UserRolesRepository userRolesRepository;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");



    @Override
    public Employee saveEmployee(EmployeeRequest employeeRequest) throws ResourceNotFoundException {
        //for to save employee
        log.info("adding employee body {}", employeeRequest);
        BasicResponse response =
                (BasicResponse) this.webClientService.findDepById(employeeRequest.getDepartmentId()).block();

        if (Objects.isNull(response.getData())) {
            log.error("Department with id {} not found ", employeeRequest.getDepartmentId());
            throw new ResourceNotFoundException("Department not found with id : " + employeeRequest.getDepartmentId());
        }

        return employeeRepository.save(employeeMapper.convertToEmployee(employeeRequest));
    }

    @Cacheable(value = "employee")
    @Override
    public List<EmployeeResponse> listEmployee() {
        log.info("list of employee....");
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::convertEmployeeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponse getEmployee(Long id) throws ResourceNotFoundException {
        Employee employee = employeeRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("employee with id not found"));
        return employeeMapper.convertEmployeeResponse(employee);
    }

    @Cacheable(value = "employee")
    @Override
    public Map<String, List<EmployeeResponse>> listEmployeeByType() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::convertEmployeeResponse)
                .filter(employeeResponse -> employeeResponse.getEmployeeType() != null)
                .collect(Collectors.groupingBy(EmployeeResponse::getEmployeeTypeString));

    }

    @Override
    public List<EmployeeResponse> listEmployeeByType(String employeeType) {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::convertEmployeeResponse)
                .filter(employeeResponse -> employeeResponse.getEmployeeType().name().equals(employeeType))
                .collect(Collectors.toList());

    }

    @Override
    @SneakyThrows
    public UserDetails loadUserByUsername(String username) {
        Optional<Employee> optionalEmployee =
                employeeRepository.findByUsername(username);
        if (optionalEmployee.isEmpty())
            throw new ResourceNotFoundException("Employee auth not found... ");
        Employee employee = optionalEmployee.get();
        employee.setRoles(getUserRoles(employee.getId()));

        return UserPrincipal.createUserPrincipal(employee);
    }

    @Override
    public Set<GrantedAuthority> getAllPermissionsAsGrantedAuthorities(Long userId) {
        Set<Role> roles = getUserRoles(userId);
        Set<GrantedAuthority> permissions =
                roles.stream().filter(Objects::nonNull)
                        .map(Role::getPermissions).flatMap(Collection::stream)
                        .map(permission -> new SimpleGrantedAuthority(permission.getLabel()))
                        .collect(Collectors.toSet());
        return permissions;
    }

    @Override
    @SneakyThrows
    public List<EmployeeResponse> searchEmployeeByCriteria(FilterDTO filterDTO) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = mapper.convertValue(filterDTO, Map.class);

        QuerySpecification<Employee> employeeQuerySpecification = new QuerySpecification<>();
        employeeQuerySpecification.add(new SearchCriteria("employeeType", EmployeeType.PERMANENT, SearchOperation.EQUAL));
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            SearchCriteria searchCriteria = null;
            if (entry.getValue() != null && entry.getValue() != "") {
                switch (entry.getKey()) {
                    case "employeeType":
                        searchCriteria = new SearchCriteria("employeeType",EmployeeType.valueOf(entry.getValue().toString()), SearchOperation.EQUAL);
                        break;
                    case "dateSignContract":
                        Date date = dateFormat.parse(entry.getValue().toString());
                        searchCriteria = new SearchCriteria("dateSignContract", date, SearchOperation.EQUAL);
                        break;

                    case "lastName":
                        searchCriteria = new SearchCriteria("lastName", entry.getValue().toString(), SearchOperation.EQUAL);
                        break;

                    case "firstName":
                        searchCriteria = new SearchCriteria("firstName", entry.getValue().toString(), SearchOperation.EQUAL);
                        break;
                    default:
                        throw new RuntimeException("param not found for criteria");

                }
                employeeQuerySpecification.add(searchCriteria);
            }
        }
        return employeeRepository.findAll(employeeQuerySpecification)
                .stream()
                .map(employeeMapper::convertEmployeeResponse)
                .collect(Collectors.toList());
    }


    @SneakyThrows
    private Set<Role> getUserRoles(Long userId) {
        Set<Role> roles;
        List<UserRoles> userRoles =
                userRolesRepository.findByEmployee_Id(userId);
        if (userRoles.isEmpty())
            throw new ResourceNotFoundException("no role affected to user");

        roles = userRoles.stream()
                .map(UserRoles::getRole)
                .collect(Collectors.toSet());
        return roles;
    }

}
