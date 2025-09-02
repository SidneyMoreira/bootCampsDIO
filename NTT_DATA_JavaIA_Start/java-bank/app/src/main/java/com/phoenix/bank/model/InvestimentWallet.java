package com.phoenix.bank.model;

import java.time.OffsetDateTime;
import java.util.UUID;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.ToString;
import static com.phoenix.bank.model.BankService.INVESTMENT;

@ToString
@Getter
public class InvestimentWallet extends Wallet {

    private final Investiment investiment;
    private final AccountWallet account;

    public InvestimentWallet(Investiment investiment, AccountWallet account, final long amount) {
        super(INVESTMENT);
        this.investiment = investiment;
        this.account = account;
        addMoney(account.reduceMoney(amount), getService(), "investiment");
    }

    public void updateAmount(final long percent) {
        var amount = getFunds() * percent / 100;
        var history = new MoneyAudit(UUID.randomUUID(), getService(), "investiment", OffsetDateTime.now());
        var money = Stream.generate(() -> new Money(history))
            .limit(amount)
            .toList();
        this.money.addAll(money);
    }

}
