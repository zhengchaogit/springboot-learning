package com.github.lybgeek.transcase.callbyself;


import com.github.lybgeek.user.entity.User;
import com.github.lybgeek.user.service.UserService;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TranInvalidCaseByCallMethodSelf {


    @Autowired
    private UserService userService;

    @Autowired
    @Lazy
    private TranInvalidCaseByCallMethodSelf tranInvalidCaseByCallMethodSelf;



    public boolean save(User user) {
        //0、调用本类方法
        //this.saveUser(user);
        //1、注入自己来调用。
        //return tranInvalidCaseByCallMethodSelf.saveUser(user);
        //2、使用@EnableAspectJAutoProxy(exposeProxy = true) + AopContext.currentProxy()
        return ((TranInvalidCaseByCallMethodSelf)AopContext.currentProxy()).saveUser(user);
    }

    @Transactional
    public boolean saveUser(User user) {
        boolean isSuccess = userService.save(user);
        try {
            int i = 1 % 0;
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return isSuccess;
    }
}
