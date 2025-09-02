package com.phoenix.bank.repository;


import static com.phoenix.bank.model.BankService.ACCOUNT;

import com.phoenix.bank.exception.NotFoundsEnoughException;
import com.phoenix.bank.model.Money;
import com.phoenix.bank.model.MoneyAudit;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import java.time.OffsetDateTime;

import lombok.NoArgsConstructor;
import static lombok.AccessLevel.PRIVATE;
import com.phoenix.bank.model.Wallet;

@NoArgsConstructor(access = PRIVATE)
public final class CommonsRepository {

    public static void checkFundsForTransaction(final Wallet source, final long amount) {
        if (source.getFunds() < amount) {
            throw new NotFoundsEnoughException("Insufficient funds");
        }
    }

    public static List<Money> generateMoney(final UUID transactionId, final long funds, final String description) {
        // Implementation for generating money
        var history = new MoneyAudit(transactionId, ACCOUNT, description, OffsetDateTime.now());
        // checkFundsForTransaction(wallet, funds);
        // wallet.getAccount().addMoney(wallet.reduceMoney(funds), wallet.getService(), "Investiment withdraw");
        // if (wallet.getFunds() == 0) {
        //     wallet.remove(wallet);
        // }
        // return wallet;
        return Stream.generate(() -> new Money(history))
                .limit(funds)
                .toList();
    }

    

}
