package org.com;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.com.exceptionns.ChangeNotAvailableException;

public class VendingMachine implements Machine {

	private static Map<String, Item> stock;
	private static Map<Integer, Coin> coins;
	private HashMap<Integer, String> menu;

	static Inventory inventory = new Inventory();

	private int userMoney;

	public VendingMachine() {
		stock = new HashMap<String, Item>();
		coins = new HashMap<Integer, Coin>();
		menu = new HashMap<Integer, String>();
		userMoney = 0;
	}

	public static void main(String[] args) {
		Machine vendingMachine = new VendingMachine();
		stock = inventory.loadItems();
		coins = inventory.loadCoins();

		vendingMachine.runMachine();

	}


	public void buyItem(Item item, int money) throws ChangeNotAvailableException {

		int expected = item.getPrice();
		if (money > expected) {
			Map<Integer, Integer> coinsToBeReturned = new HashMap<Integer, Integer>();
			inventory.getChange(money - expected, coinsToBeReturned);
			dispenseItem(item);
			collectChange(coinsToBeReturned);

		}

	}

	private void collectChange(Map<Integer, Integer> coinsToBeReturned) {
		for (Integer key : coinsToBeReturned.keySet()) {
			System.out.println("Please collect change " + Integer.toString(key) + " quantity "
					+ Integer.toString(coinsToBeReturned.get(key)));
		}

	}

	/* (non-Javadoc)
	 * @see org.com.Machine#dispenseItem(org.com.Item)
	 */
	public void dispenseItem(Item newItem) {
		inventory.removeItem(newItem);
		System.out.println("Please take the " + newItem.getName() + " from the drawer");
	}

	/* (non-Javadoc)
	 * @see org.com.Machine#runMachine()
	 */
	public void runMachine() {

		while (true) {

			this.userMoney = 0;

			for (String itemName : stock.keySet()) {
				Item item = stock.get(itemName);
				if (item.getQuantity() > 0) {
					this.menu.put(item.getId(), item.getName());
				}

			}

			System.out.println("#######################################");
			System.out.println("SELECTION MENU");

			for (Integer number : this.menu.keySet()) {
				String name = this.menu.get(number);
				if (this.stock.get(name) != null && this.stock.get(name).getQuantity() != 0) {
					System.out.println("Press " + (int) number + " followed by # for " + name + " price "
							+ this.stock.get(name).getPrice());
				}
			}
			System.out.println("Press 0 followed by # to exit" );
			System.out.println("#######################################");
			int itemSelectedid = 0;

			Scanner input = new Scanner(System.in);
			while (input.hasNext()) {
				if (input.hasNextInt())
					itemSelectedid = input.nextInt();
				else if (input.next().equals("#"))
					break;
			}
		
			
			if(itemSelectedid==0) {
				break;
			}

			if (itemSelectedid <= 0 && itemSelectedid >= 4) {
				System.out.println("Enter a valid menu item");
				continue;

			}

			
			
			Item item = null;

			if (this.menu.containsKey(itemSelectedid)) {
				item = this.stock.get(this.menu.get(itemSelectedid));
			}

			System.out.println("Item selected is " + item.getName());
			System.out.println("Please enter coins followed by # to start selection: ");
			input = new Scanner(System.in);

			while (input.hasNext()) {

				if (input.hasNextInt())
					this.userMoney = this.userMoney + input.nextInt();
				else if (input.next().equals("#")) {
					if (this.userMoney <= item.getPrice()) {
						System.out.println(
								"Entered amount is less than item price. Please enter more coins to match remaining amount "
										+ Integer.toString(item.getPrice() - this.userMoney));
						continue;
					} else {
						break;
					}
				}
			}

			System.out.println("Amount credited: " + this.userMoney);

			try {
				buyItem(item, this.userMoney);
			} catch (Exception ChangeNotAvailableException) {
				System.out.println("Exception occured while computing the change.");
				continue;
			}

		}
	}

}