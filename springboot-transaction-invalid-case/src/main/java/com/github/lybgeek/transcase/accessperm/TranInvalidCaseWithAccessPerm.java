package com.github.lybgeek.transcase.accessperm;

import com.github.lybgeek.user.entity.User;
import com.github.lybgeek.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TranInvalidCaseWithAccessPerm {

        @Autowired
        private UserService userService;

        //public 改为 private: 我们在开发过程中，把有某些事务方法，定义了错误的访问权限，就会导致事务功能出问题 
        // save 方法的访问权限被定义成了private，这样会导致事务失效，spring要求被代理方法必须是public的。
        //说白了，在AbstractFallbackTransactionAttributeSource类的computeTransactionAttribute方法中有个判断，如果目标方法不是public，则TransactionAttribute返回null，即不支持事务。
        @Transactional
        public boolean save(User user){
            boolean isSuccess = userService.save(user);
            try {
                int i = 1 % 0;
            } catch (Exception e) {
                throw new RuntimeException();
            }
            return isSuccess;
        }

}