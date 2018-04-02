package seedu.address.model;

import java.util.HashMap;

import seedu.address.model.person.Balance;
import seedu.address.model.person.Person;

/**
 * DebtsList of a Person, contains every person matched with the amount is owed or owes.
 */
public class DebtsList extends HashMap<Person, Balance> {
    public DebtsList() {
        super();
    }

    /**
     * Updates the debt with a person. If the person has no previous debt, then the person
     * is added to the HashMap.
     * @param person that owes or is owed money.
     * @param debt to add to the old debt.
     */
    public void updateDebt(Person person, Balance debt) {
        if (!this.containsKey(person)) {
            this.put(person, new Balance("0.00"));
        }
        Balance oldDebts = this.get(person);
        this.replace(person, oldDebts.add(debt));
    }

    /**
     * Displays the content of DebtsList in the terminal.
     */
    public void display() {
        System.out.print("dl =");
        this.forEach(((person, balance) -> System.out.print(person.getName().fullName
            + " : " + balance.toString())));
    }
}
