package com.sid.gl.manageemployee.models.query;

import lombok.SneakyThrows;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuerySpecification<T> implements Specification<T> {
    private List<SearchCriteria> list = new ArrayList<>();

    public void add(SearchCriteria criteria) {
        list.add(criteria);
    }

    @SneakyThrows
    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates=new ArrayList<>();
        SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
        for(SearchCriteria searchCriteria:list){
           switch (searchCriteria.getSearchOperation()){
               case EQUAL:
                   predicates.add(criteriaBuilder.equal(root.get(searchCriteria.getKey()), searchCriteria.getValue()));
                   break;

               case LIKE:
                   predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(searchCriteria.getKey())), "%" + searchCriteria.getValue().toString().toLowerCase() + "%"));
                   break;
               case GREATER_THAN_EQUAL:

                   Date dateSign = dateFormat.parse(searchCriteria.getValue().toString());
                   System.out.println(dateSign);
                   predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(searchCriteria.getKey()), dateSign));
                   break;
               case LESS_THAN_EQUAL:

                   Date endDate = dateFormat.parse(searchCriteria.getValue().toString());
                   predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.getKey()), endDate));
                   break;
           }
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
