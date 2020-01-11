package org.com;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.com.exceptionns.ChangeNotAvailableException; 

public class Inventory {

	private Map<String, Item> stock = new HashMap<String, Item>();
	private Map<Integer, Coin> coins = new TreeMap<Integer, Coin>(Collections.reverseOrder());
	

	public Map<String, Item> loadItems() {
		stock.put("cola", new Item(1, "cola", 2, 3));
		stock.put("pepsi", new Item(2, "pepsi", 5, 3));
		stock.put("mountaindew", new Item(3, "mountaindew", 12, 2));
		return stock;

	}

	public Map<Integer, Coin> loadCoins() {
		coins.put(1, new Coin(1, 5));
		coins.put(2, new Coin(2, 5));
		coins.put(5, new Coin(5, 5));
		coins.put(10, new Coin(10, 5));
		coins.put(50, new Coin(50, 5));
		coins.put(100, new Coin(100, 1));
		return coins;

	}

	public void getChange(int changeToBeReturned, Map<Integer, Integer> coinsToBeReturned) {
		Set<Integer> coinsSet = this.coins.keySet();
		Iterator<Integer> iter = coinsSet.iterator();		

		while (iter.hasNext()) {
			int denomination = iter.next();
			if (changeToBeReturned < denomination)
				continue;
			int count = Math.min(changeToBeReturned / denomination, this.coins.get(denomination).getQuantity());
			coinsToBeReturned.put(denomination, count);
			coins.get(denomination).setQuantity(coins.get(denomination).getQuantity() - count);

			int remainder = changeToBeReturned - (count * denomination);

			if (remainder == 0) {
				return;
			}
			
			if(remainder<0) {
				throw new ChangeNotAvailableException("Fund not avialble for change calculation or Exact Change could not be calculated");
			}

			changeToBeReturned = remainder;
			continue;
		}

	}

	public void removeItem(Item newItem) {
		this.stock.get(newItem.getName()).setQuantity(newItem.getQuantity()-1);
	}

}
