package com.phoenix.bank.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.phoenix.bank.repository.CommonsRepository.checkFundsForTransaction;

import com.phoenix.bank.exception.AccountNotFoundException;
import com.phoenix.bank.exception.PixInUseException;
import com.phoenix.bank.model.AccountWallet;
import com.phoenix.bank.model.MoneyAudit;
import java.time.OffsetDateTime;
import static java.time.temporal.ChronoUnit.SECONDS;

public class AccountRepository {

    private final List<AccountWallet> accounts = new ArrayList<>();

    public List<AccountWallet> list() {
        return this.accounts;
    }

    public AccountWallet findByPix(final String pix) {
        return accounts.stream()
                .filter(a -> a.getPix().contains(pix))
                .findFirst()
                .orElseThrow(() -> new AccountNotFoundException("Account not found. Account with PIX '" + pix + "' not exists or is inactive"));
    }

    public AccountWallet create(final List<String> pix, final long initialFunds) {
        if (!accounts.isEmpty()) {         
        
            var pixUse = accounts.stream()
                    .flatMap(a -> a.getPix().stream())
                    .toList();
            for (var p : pix) {
                if (pixUse.contains(p)) {
                    throw new PixInUseException("Account with PIX '" + p + "' already exists");
                }
            }
        }
        var newAccount = new AccountWallet(initialFunds, pix);
        accounts.add(newAccount);
        return newAccount;
    }

    public void deposit(final String pix, final long fundsAmount) {
        var target = findByPix(pix);
        target.addMoney(fundsAmount, "Deposit");
    }

    public long withdraw(final String pix, final long amount) {
        var source = findByPix(pix);
        checkFundsForTransaction(source, amount);
        source.reduceMoney(amount);
        return amount;
    }

    public void transferMoney(final String sourcePix, final String targetPix, final long amount) {
        var source = findByPix(sourcePix);
        var target = findByPix(targetPix);
        checkFundsForTransaction(source, amount);
        var message = "Transfer of " + amount + " from " + sourcePix + " to " + targetPix;
        target.addMoney(source.reduceMoney(amount), source.getService(), message);
    }

    public Map<OffsetDateTime, List<MoneyAudit>> getHistory(final String pix){
        var wallet = findByPix(pix);
        var audit = wallet.getFinancialTransactions();
        return audit.stream()
                .collect(Collectors.groupingBy(t -> t.createdAt().truncatedTo(SECONDS)));
    }

}
