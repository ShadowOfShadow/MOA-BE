package com.moa.finance.repository.dummy;

import com.moa.finance.repository.finance.UserAccountRepository;
import com.moa.finance.repository.finance.UserRepository;
import com.moa.finance.vo.dummy.BankSavingProducts;
import com.moa.finance.vo.finance.Account;
import com.moa.finance.vo.finance.UserAccount;
import com.moa.user.vo.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class BankSavingProductsRepositoryTest {

    @Autowired
    private BankSavingProductsRepository bankSavingProductsRepository;
    @Autowired
    private BankRepository bankRepository;
    @Autowired
    private UserRepository userRepository;

    @Mock
    private UserAccountRepository userAccountRepository;

    @DisplayName("1. 적금 상품 최고 금리 순으로 조회")
    @Test
    void findInHighestOrder(){
        List<BankSavingProducts> res = bankSavingProductsRepository.findProductsByHighest();
        res.forEach(System.out::println);
    }

    @DisplayName("2. 적금 상품 기본 금리 순으로 조회")
    @Test
    @Transactional
    void findInBasicOrder(){
        List<BankSavingProducts> res = bankSavingProductsRepository.findProductsByBasic();
        res.forEach(System.out::println);
    }

    @DisplayName("3. 적금 상품의 은행 조회")
    @Test
    void getBankOfSavingProducts(){
        BankSavingProducts bankSavingProducts = bankSavingProductsRepository.findById(1L).orElse(null);
        System.out.println(bankSavingProducts);
    }

    @DisplayName("4. 유저가 가지고 있지 않은 적금 상품만 조회")
    @Test
    void getSavingProductsUserDoesNotSignedFor(){
        // User에 신한 장병내일준비적금,
        when(userAccountRepository.findUserAccounts(1L))
                .thenReturn(this.getUserAccounts());
        List<UserAccount> accounts = userAccountRepository.findUserAccounts(1L);

        User user = userRepository.findById(1L).orElse(null);
        for (UserAccount account : accounts) {
            user.getUserAccount().add(account);
        }
        user.getUserAccount().forEach(System.out::println);




//        System.out.println(bankSavingProductsRepository.findSavingProducts(1L));
    }

    private List<UserAccount> getUserAccounts(){
        UserAccount userAccount1 = new UserAccount();
        Account account1 = new Account();
        account1.setProductName("장병내일준비적금");

        userAccount1.setAccount(account1);
        userAccount1.setBank(bankRepository.findById(1L).orElse(null));

        UserAccount userAccount2 = new UserAccount();
        Account account2 = new Account();
        account2.setProductName("장병내일준비적금");

        userAccount2.setAccount(account1);
        userAccount2.setBank(bankRepository.findById(1L).orElse(null));

        List<UserAccount> accounts = new ArrayList<>();
        accounts.add(userAccount1);
        accounts.add(userAccount2);
        return accounts;

    }
}