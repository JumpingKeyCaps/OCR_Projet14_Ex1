package com.kirabium.relayance.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kirabium.relayance.databinding.CustomerItemBinding
import com.kirabium.relayance.domain.model.Customer

class CustomerAdapter(private var customers: MutableList<Customer>, private val onClick: (Customer) -> Unit) : RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {

    class CustomerViewHolder(private val binding: CustomerItemBinding, val onClick: (Customer) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        private var currentCustomer: Customer? = null

        init {
            binding.root.setOnClickListener {
                currentCustomer?.let {
                    onClick(it)
                }
            }
        }

        fun bind(customer: Customer) {
            currentCustomer = customer
            with(binding) {
                nameTextView.text = customer.name
                emailTextView.text = customer.email
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val binding = CustomerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomerViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val customer = customers[position]
        holder.bind(customer)
    }

    override fun getItemCount() = customers.size

    /**
     * Update the list of customers.
     * @param newCustomers The new list of customers.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun updateCustomers(newCustomers: List<Customer>) {
        customers.clear() // Efface l'ancienne liste
        customers.addAll(newCustomers) // Ajoute les nouveaux clients
        notifyDataSetChanged() // Notifie l'adaptateur que la liste a changé
    }
}
