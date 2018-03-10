package seedu.address.model.transaction;

import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * Represents a Transaction in SmartSplit   .
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Transaction {

    private final Integer id;
    private final Date dateTime;
    private final Person payer;
    private final Double amount;
    private final String description;
    private final UniquePersonList payees;

    public Transaction(Person payer, Double amount, String description, UniquePersonList payees) {
        this.id = 0; // TODO: Ensure that this increments by 1 for each new transaction
        this.dateTime = Date.from(Instant.now(Clock.system(ZoneId.of("Asia/Singapore"))));
        this.payer = payer;
        this.amount = amount;
        this.description = description;
        this.payees = payees;
    }

    public Transaction(Person payer, Double amount, String description, Set<Person> payeesToAdd) {
        UniquePersonList payees = new UniquePersonList();
        for (Person p: payeesToAdd) {
            try {
                payees.add(p);
            } catch (DuplicatePersonException e) {
                System.out.println("Duplicate person" + p.getName() + " not added to list of payees");
            }
        }

        this.id = 0; // TODO: Ensure that this increments by 1 for each new transaction
        this.dateTime = Date.from(Instant.now(Clock.system(ZoneId.of("Asia/Singapore"))));
        this.payer = payer;
        this.amount = amount;
        this.description = description;
        this.payees = payees;
    }

    public Integer getId() {
        return id;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public Person getPayer() {
        return payer;
    }

    public Double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public UniquePersonList getPayees() {
        return payees;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Transaction)) {
            return false;
        }

        Transaction otherTransaction = (Transaction) other;
        return otherTransaction.getId().equals(this.getId())
                && otherTransaction.getDateTime().equals(this.getDateTime())
                && otherTransaction.getPayer().equals(this.getPayer())
                && otherTransaction.getAmount().equals(this.getAmount())
                && otherTransaction.getDescription().equals(this.getDescription())
                && otherTransaction.getPayees().equals(this.getPayees());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateTime, payer, amount, description, payees);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Transaction id: ")
                .append(getId())
                .append(" Created on: ")
                .append(getDateTime())
                .append(" Paid by: ")
                .append(getPayer())
                .append("\n Amount: ")
                .append(getAmount())
                .append("\n Description: ")
                .append(getDescription())
                .append("\n Payees: ")
                .append(getPayees().asObservableList().toString());
        return builder.toString();
    }

}
