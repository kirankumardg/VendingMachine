package org.com;

import org.com.exceptionns.ChangeNotAvailableException;

public interface Machine {

	void buyItem(Item item, int money) throws ChangeNotAvailableException;

	void dispenseItem(Item newItem);

	void runMachine();

}