package com.phoenix.bank.repository;

import java.util.List;
import java.util.ArrayList;

import com.phoenix.bank.exception.AccountWithInvestimentException;
import com.phoenix.bank.exception.InvestimentNotFoundException;
import com.phoenix.bank.exception.WalletNotFoundException;
import com.phoenix.bank.model.AccountWallet;
import com.phoenix.bank.model.Investiment;
import com.phoenix.bank.model.InvestimentWallet;
import static com.phoenix.bank.repository.CommonsRepository.checkFundsForTransaction;


public class InvestimentRepository {

    private long nextId = 0;
    private final List<Investiment> investments = new ArrayList<>();
    private final List<InvestimentWallet> wallets = new ArrayList<>();

    public List<Investiment> listInvestments() {
        return this.investments;
    }

    public List<InvestimentWallet> listWallets() {
        return this.wallets;
    }

    public InvestimentWallet findWalletByAccountPix(final String pix) {
        return this.wallets.stream()
                .filter(w -> w.getAccount().getPix().contains(pix))
                .findFirst()
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found for account: " + pix));
    }

    public Investiment findById(final long id) {
        return this.investments.stream()
                .filter(a -> a.id() == id)
                .findFirst()
                .orElseThrow(() -> new InvestimentNotFoundException("Investment not found for ID: " + id));
    }

    public void updateAmount() {
        wallets.forEach(w -> w.updateAmount(w.getInvestiment().tax()));
    }

    public InvestimentWallet withdraw(final String pix, final long funds) {
        var wallet = findWalletByAccountPix(pix);
        checkFundsForTransaction(wallet, funds);
        wallet.getAccount().addMoney(wallet.reduceMoney(funds), wallet.getService(), "Investiment withdraw");
        if (wallet.getFunds() == 0) {
            wallets.remove(wallet);            
        }
        return wallet;
    }

    public InvestimentWallet deposit(final String pix, final long funds) {
        var wallet = findWalletByAccountPix(pix);
        wallet.addMoney(wallet.getAccount().reduceMoney(funds), wallet.getService(), "Investiment deposit");
        return wallet;
    }

    public Investiment create(final long tax, final long initialFunds) {
        this.nextId ++;
        var investment = new Investiment(this.nextId, tax, initialFunds);
        investments.add(investment);
        return investment;
    }

    public InvestimentWallet initInvestment(final AccountWallet account, final long id) {
        if (!wallets.isEmpty()) {
        
            var accountInUse = wallets.stream()
                    .map(InvestimentWallet::getAccount)
                    .toList();
            if (accountInUse.contains(account)) {
                throw new AccountWithInvestimentException("Account '" + account + "' already exists with an investment");
            }
        }
        var investiment = findById(id);
        checkFundsForTransaction(account, investiment.initialFunds());
        var wallet = new InvestimentWallet(investiment, account, investiment.initialFunds());
        wallets.add(wallet);
        return wallet;
    }

}
