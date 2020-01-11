package org.com;

public class Coin {
	private int denomination;
	private int quantity;

	Coin(int denomination, int quantity) {
		this.denomination = denomination;
		this.quantity = quantity;
	}

	public int getDenomination() {
		return denomination;
	}

	public int getQuantity() {
		return denomination;
	}
	public void setQuantity(int quantity) {
		this.quantity=quantity;
	}
}
