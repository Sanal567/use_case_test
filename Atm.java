package com.sanal.app;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Atm {

	private static Long totalAmount = 0L;
	private static Map<Long, Long> amountAndQuantity = new TreeMap<>(new SortByAmountDesc());

	// 20, 10, 5, and 1 dollar bills.
	static {
		amountAndQuantity.put(1L, 0L);
		amountAndQuantity.put(5L, 0L);
		amountAndQuantity.put(10L, 0L);
		amountAndQuantity.put(20L, 0L);
	}

	public static void main(String[] args) {

		Map<Long, Long> depositAmountAndQuantity = new HashMap<>();
		depositAmountAndQuantity.put(10L, -1L);
		deposit(depositAmountAndQuantity);

		depositAmountAndQuantity = new HashMap<>();
		depositAmountAndQuantity.put(20L, 0L);
		depositAmountAndQuantity.put(2L, 0L);
		depositAmountAndQuantity.put(10L, 0L);
		deposit(depositAmountAndQuantity);

		depositAmountAndQuantity = new HashMap<>();
		depositAmountAndQuantity.put(10L, 8L);
		depositAmountAndQuantity.put(5L, 20L);
		deposit(depositAmountAndQuantity);

		depositAmountAndQuantity = new HashMap<>();
		depositAmountAndQuantity.put(20L, 3L);
		depositAmountAndQuantity.put(5L, 18L);
		depositAmountAndQuantity.put(1L, 4L);
		deposit(depositAmountAndQuantity);

		withdraw(75);
		withdraw(122);
		withdraw(253);
		withdraw(0);
		withdraw(-25);

	}

	private static void deposit(Map<Long, Long> deposit) {
		boolean isAllDenominationsZero = true;

		for (Map.Entry<Long, Long> entry : deposit.entrySet()) {

			if (entry.getValue() < 0) {
				System.out.println("Incorrect deposit amount");
				return;
			} else if (entry.getValue() == 0)
				continue;

			else
				isAllDenominationsZero = false;

		}

		if (isAllDenominationsZero) {
			System.out.println("Deposit amount cannot be zero");
			return;
		}

		else { // update money and balance

			for (Map.Entry<Long, Long> entry : deposit.entrySet()) {

				if (amountAndQuantity.containsKey(entry.getKey())) {
					amountAndQuantity.put(entry.getKey(), amountAndQuantity.get(entry.getKey()) + entry.getValue());

					totalAmount += entry.getKey() * entry.getValue();

				} else if (entry.getKey() == 50 || entry.getKey() == 100) {
					amountAndQuantity.put(entry.getKey(), entry.getValue());
					totalAmount += entry.getKey() * entry.getValue();
				}
			}
		}

		printAmountAndQuantity();
	}

	private static Map<Long, Long> withdrawlDenominations = new TreeMap<>(new SortByAmountDesc());

	private static void printWithdrawlDenominations() {
		System.out.print("Dispensed: ");
		for (Map.Entry<Long, Long> entry : withdrawlDenominations.entrySet()) {
			System.out.print(entry.getKey() + "s=" + entry.getValue() + ",");

		}
		System.out.println();
		printAmountAndQuantity();

	}

	private static void printAmountAndQuantity() {
		System.out.print("Balance : ");

		for (Map.Entry<Long, Long> entry : amountAndQuantity.entrySet())
			System.out.print(entry.getKey() + "s=" + entry.getValue() + ",");

		System.out.print("Total =" + totalAmount);
		System.out.println();
	}

	private Long withdrawRecursion(Long denminations, Long quantity) {
		return null;
	}

	private static void withdraw(long withdrawlAmount) {
		// use switch
		if (withdrawlAmount == 0 || withdrawlAmount < 0 || totalAmount < withdrawlAmount)
			System.out.println("Incorrect or insufficient funds");

		else {
			long withDrawlAmountIn20$s = withdrawlAmount / 20;
			long remainingAmount = 0;

			if (withDrawlAmountIn20$s > 0) {
				long availabe20$s = amountAndQuantity.get(20L);

				if (availabe20$s > 0) {

					if (availabe20$s >= withDrawlAmountIn20$s) {
						amountAndQuantity.put(20L, amountAndQuantity.get(20L) - withDrawlAmountIn20$s);
						withdrawlDenominations.put(20L, withDrawlAmountIn20$s);
						totalAmount -= 20 * withDrawlAmountIn20$s;

						remainingAmount = withdrawlAmount % 20;

						if (remainingAmount > 0) {
							long withDrawlAmountIn10$s = remainingAmount / 10;

							if (withDrawlAmountIn10$s > 0) {
								long avaliable10$s = amountAndQuantity.get(10L);

								if (avaliable10$s > 0) {

									if (avaliable10$s >= withDrawlAmountIn10$s) {
										amountAndQuantity.put(10L, amountAndQuantity.get(10L) - withDrawlAmountIn10$s);
										withdrawlDenominations.put(10L, withDrawlAmountIn10$s);
										totalAmount -= 10 * withDrawlAmountIn10$s;

										remainingAmount = withdrawlAmount % 10;

										if (remainingAmount > 0) {

											long withDrawlAmountIn5$s = remainingAmount / 5;

											if (withDrawlAmountIn5$s > 0) {
												long avaliable5$s = amountAndQuantity.get(5L);

												if (avaliable5$s > 0) {

													if (avaliable5$s >= withDrawlAmountIn5$s) {
														amountAndQuantity.put(5L,
																amountAndQuantity.get(5L) - withDrawlAmountIn5$s);
														withdrawlDenominations.put(5L, withDrawlAmountIn5$s);
														totalAmount -= 5 * withDrawlAmountIn5$s;
														remainingAmount = withdrawlAmount % 5;

														if (remainingAmount > 0) {
															System.out.println(" ======= ");
														} else
															printWithdrawlDenominations();
													}

												}
											}

										}
									}
								}

							}
						}
					}
				} else {

					long withDrawlAmountIn10$s = withdrawlAmount / 10;

					if (withDrawlAmountIn10$s > 0) {
						long avaliable10$s = amountAndQuantity.get(10L);

						if (avaliable10$s > 0) {

							if (avaliable10$s >= withDrawlAmountIn10$s) {
								amountAndQuantity.put(10L, amountAndQuantity.get(10L) - withDrawlAmountIn10$s);
								withdrawlDenominations.put(10L, withDrawlAmountIn10$s);
								totalAmount -= 10 * withDrawlAmountIn10$s;

								remainingAmount = withdrawlAmount % 10;

								if (remainingAmount > 0) {

									long withDrawlAmountIn5$s = remainingAmount / 5;

									if (withDrawlAmountIn5$s > 0) {
										long avaliable5$s = amountAndQuantity.get(5L);

										if (avaliable5$s > 0) {

											if (avaliable5$s >= withDrawlAmountIn5$s) {
												amountAndQuantity.put(5L,
														amountAndQuantity.get(5L) - withDrawlAmountIn5$s);
												withdrawlDenominations.put(5L, withDrawlAmountIn5$s);
												totalAmount -= 5 * withDrawlAmountIn5$s;
												remainingAmount = withdrawlAmount % 5;

												if (remainingAmount > 0) {
													System.out.println(" ======= ");

													long withDrawlAmountIn1$s = remainingAmount;

													long avaliable1$s = amountAndQuantity.get(1L);

													if (avaliable1$s > 0) {

														if (avaliable1$s >= withDrawlAmountIn1$s) {
															amountAndQuantity.put(1L,
																	amountAndQuantity.get(1L) - withDrawlAmountIn1$s);
															withdrawlDenominations.put(1L, withDrawlAmountIn1$s);
															totalAmount -= withDrawlAmountIn1$s;

															printWithdrawlDenominations();
														}
													}
												} else
													printWithdrawlDenominations();
											}

										}
									}

								}
							} else {

							}
						}

					}

				}
			}
		}

	}

}
