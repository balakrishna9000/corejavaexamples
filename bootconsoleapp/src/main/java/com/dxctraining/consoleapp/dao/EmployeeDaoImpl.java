package com.dxctraining.consoleapp.dao;

import com.dxctraining.consoleapp.entities.Employee;
import com.dxctraining.consoleapp.exceptions.EmployeeNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class EmployeeDaoImpl implements IEmployeeDao{

    //equivalent to @Autowired
    //@Autowired
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Employee findEmployeeById(int idArg) {
        Employee employee=entityManager.find(Employee.class,idArg);
        if(employee==null){
            throw new EmployeeNotFoundException("employee not found for id="+idArg);
        }
        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        employee=entityManager.merge(employee);
        return employee;
    }

    @Override
    public Employee save(Employee employee) {
        entityManager.persist(employee);//insertion
        return employee;
    }

    @Override
    public void remove(int id) {
     Employee employee= findEmployeeById(id) ;
     entityManager.remove(employee);
    }

    @Override
    public Employee findEmployeeByName(String empName){
        String jpaql="from Employee where name=:ename";
        Query query= entityManager.createQuery(jpaql);
        query.setParameter("ename",empName);
        List<Employee>list=query.getResultList();
        Employee employee=null;
        if(!list.isEmpty()){
           employee=list.get(0);
        }
        return employee;
    }

}
