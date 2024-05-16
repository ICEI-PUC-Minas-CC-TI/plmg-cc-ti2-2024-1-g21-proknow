package services;

import java.math.BigDecimal;
import java.util.UUID;
import dao.WalletDao;
import dao.DAO;
import models.Wallet;
import models.Transaction;
import models.Crypto;

public class WalletService {

	private final WalletDao walletDao;

	public WalletService(DAO dao) {
        this.walletDao = dao.getJdbiContext().onDemand(WalletDao.class);
        createTableIfNotExists();
    }

	private void createTableIfNotExists() {
		walletDao.createWalletTable();
		walletDao.createWalletBalancesTable();
	}

	public void createWallet(UUID userId) {
		Wallet wallet = new Wallet(userId);
		walletDao.insertWallet(wallet.getUserId(), userId);
		wallet.deposit(new Crypto(0, "Proknow Coin", BigDecimal.valueOf(150), "PKW", BigDecimal.ZERO, BigDecimal.ZERO,
				BigDecimal.ZERO, BigDecimal.ZERO, ""), BigDecimal.valueOf(150));
		walletDao.insertWalletBalance(wallet.getUserId(), "PKW", BigDecimal.valueOf(150));
	}

	public Wallet getWalletByUserId(UUID userId) {
		return walletDao.findWalletByUserId(userId);
	}

	public void deposit(UUID userId, String currency, double amount) throws Exception {
		Wallet wallet = getWalletByUserId(userId);
		if (wallet != null) {
			wallet.deposit(new Crypto(0, currency, BigDecimal.valueOf(amount), "", BigDecimal.ZERO, BigDecimal.ZERO,
					BigDecimal.ZERO, BigDecimal.ZERO, ""), BigDecimal.valueOf(amount));
			walletDao.updateWalletBalance(wallet.getUserId(), currency, wallet.getBalance(currency));
		} else {
			throw new Exception("Wallet not found");
		}
	}

	public void withdraw(UUID userId, String currency, double amount) throws Exception {
		Wallet wallet = getWalletByUserId(userId);
		if (wallet != null) {
			wallet.withdraw(new Crypto(0, currency, BigDecimal.valueOf(amount), "", BigDecimal.ZERO, BigDecimal.ZERO,
					BigDecimal.ZERO, BigDecimal.ZERO, ""), BigDecimal.valueOf(amount));
			walletDao.updateWalletBalance(wallet.getUserId(), currency, wallet.getBalance(currency));
		} else {
			throw new Exception("Wallet not found");
		}
	}
}