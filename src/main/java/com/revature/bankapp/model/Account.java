package com.revature.bankapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Table(name = "account")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;
    private String name;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private AccountType accountType;

    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;

    public Integer getBalance() {
        if (this.transactions != null) {
            int sum = 0;

            for (Transaction transaction : this.transactions) {
                sum += transaction.getAmount();
            }

            return sum;
        } else {
            return 0;
        }
    }
}
