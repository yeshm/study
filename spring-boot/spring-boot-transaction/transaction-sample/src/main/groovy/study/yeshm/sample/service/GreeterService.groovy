package study.yeshm.sample.service

import groovy.transform.CompileStatic
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.TransactionDefinition
import org.springframework.transaction.TransactionStatus
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.support.DefaultTransactionDefinition
import study.yeshm.DemoTransactionManager

@CompileStatic
@Service
class GreeterService {

    @Autowired
    private DemoTransactionManager demoTransactionManager

    @Transactional(readOnly = true)
    String sayHello(String name) {
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED)

        TransactionStatus transactionStatus = demoTransactionManager.getTransaction(transactionDefinition)
        System.out.println("isNewTransaction:" + transactionStatus.isNewTransaction())

        return "Hello"
    }


    String testNewTransaction() {
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED)

        TransactionStatus transactionStatus = demoTransactionManager.getTransaction(transactionDefinition)
        System.out.println("isNewTransaction:" + transactionStatus.isNewTransaction())

        return "Hello"
    }

}