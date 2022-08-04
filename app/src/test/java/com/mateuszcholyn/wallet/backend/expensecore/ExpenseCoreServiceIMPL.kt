package com.mateuszcholyn.wallet.backend.expensecore

import com.mateuszcholyn.wallet.backend.events.ExpenseAddedEvent
import com.mateuszcholyn.wallet.backend.events.ExpenseRemovedEvent
import com.mateuszcholyn.wallet.backend.events.MiniKafka
import com.mateuszcholyn.wallet.randomUUID

interface ExpenseRepository {
    fun add(expense: Expense): Expense
    fun getAll(): List<Expense>
    fun getById(expenseId: ExpenseId): Expense?
    fun remove(expenseId: ExpenseId)
}

class InMemoryExpenseRepository : ExpenseRepository {
    private val storage: MutableMap<ExpenseId, Expense> = mutableMapOf()

    override fun add(expense: Expense): Expense {
        storage[expense.id] = expense
        return expense
    }

    override fun getAll(): List<Expense> =
        storage.values.toList()

    override fun getById(expenseId: ExpenseId): Expense? =
        getAll()
            .find { it.id == expenseId }

    override fun remove(expenseId: ExpenseId) {
        storage.remove(expenseId)
    }
}


interface ExpensePublisher {
    fun publishExpenseAddedEvent(expenseAddedEvent: ExpenseAddedEvent)
    fun publishExpenseRemovedEvent(expenseRemovedEvent: ExpenseRemovedEvent)
}

class MiniKafkaExpensePublisher(
    private val miniKafka: MiniKafka,
) : ExpensePublisher {
    override fun publishExpenseAddedEvent(expenseAddedEvent: ExpenseAddedEvent) {
        miniKafka.expenseAddedEventTopic.publish(expenseAddedEvent)
    }

    override fun publishExpenseRemovedEvent(expenseRemovedEvent: ExpenseRemovedEvent) {
        miniKafka.expenseRemovedEventTopic.publish(expenseRemovedEvent)
    }
}

class ExpenseCoreServiceIMPL(
    private val expenseRepository: ExpenseRepository,
    private val expensePublisher: ExpensePublisher,
) : ExpenseCoreServiceAPI {
    override fun add(addExpenseParameters: AddExpenseParameters): Expense =
        addExpenseParameters
            .toNewExpense()
            .let { expenseRepository.add(it) }
            .also { expensePublisher.publishExpenseAddedEvent(it.toExpenseAddedEvent()) }

    override fun remove(expenseId: ExpenseId) {
        val expense =
            expenseRepository.getById(expenseId) ?: throw ExpenseNotFoundException(expenseId)
        expenseRepository.remove(expenseId)
        expensePublisher.publishExpenseRemovedEvent(expense.toExpenseRemovedEvent())
    }

    override fun getAll(): List<Expense> =
        expenseRepository.getAll()

    private fun AddExpenseParameters.toNewExpense(): Expense =
        Expense(
            id = ExpenseId(randomUUID()),
            amount = amount,
            description = description,
            paidAt = paidAt,
            categoryId = categoryId,
        )

    private fun Expense.toExpenseAddedEvent(): ExpenseAddedEvent =
        ExpenseAddedEvent(
            expenseId = id,
            categoryId = categoryId,
            amount = amount,
        )

    private fun Expense.toExpenseRemovedEvent(): ExpenseRemovedEvent =
        ExpenseRemovedEvent(
            expenseId = id,
            categoryId = categoryId,
        )

}

class ExpenseNotFoundException(expenseId: ExpenseId) : RuntimeException(
    "Expense with id ${expenseId.id} does not exist"
)
