/*
 * Copyright 2019 The nity.io gRPC Spring Boot Project Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package study.yeshm.sample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import study.yeshm.DemoTransactionManager;

@Service
public class GreeterService {

    @Autowired
    private DemoTransactionManager demoTransactionManager;

    @Transactional(readOnly = true)
    public String sayHello(String name) {
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = demoTransactionManager.getTransaction(transactionDefinition);
        System.out.println("isNewTransaction:" + transactionStatus.isNewTransaction());

        return "Hello";
    }


    public String testNewTransaction() {
        TransactionDefinition transactionDefinition = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRED);

        TransactionStatus transactionStatus = demoTransactionManager.getTransaction(transactionDefinition);
        System.out.println("isNewTransaction:" + transactionStatus.isNewTransaction());

        return "Hello";
    }

}