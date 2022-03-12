package com.project.bankingapp.feature.transfer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.project.bankingapp.R
import com.project.bankingapp.base.ScreenState
import com.project.bankingapp.common.showToast
import com.project.bankingapp.databinding.FragmentPayeeListBinding
import com.project.bankingapp.feature.transfer.adapter.PayeeAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PayeeListDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "df_payee_list"
    }

    private var _binding: FragmentPayeeListBinding? = null
    private val binding: FragmentPayeeListBinding get() = _binding!!

    private lateinit var adapter: PayeeAdapter

    private val viewModel by activityViewModels<TransferVM>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.Theme_BankingApp_Dialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPayeeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupUIListener()
        setupDataObserver()
        viewModel.getPayeeList()
    }

    private fun setupUIListener() = with(binding) {
        ivBack.setOnClickListener { dismiss() }
        swipeRefresh.setOnRefreshListener {
            viewModel.getPayeeList()
            swipeRefresh.isRefreshing = false
        }
    }

    private fun setupUI() = with(binding) {
        adapter = PayeeAdapter { payee ->
            viewModel.selectPayee(payee)
            dismiss()
        }
        rvPayees.adapter = adapter
        rvPayees.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val topRowVerticalPosition =
                    if (recyclerView.childCount == 0) 0 else recyclerView.getChildAt(0).top
                binding.swipeRefresh.isEnabled = topRowVerticalPosition >= 0
            }
        })
    }

    private fun setupDataObserver() {
        viewModel.payeeList.observe(viewLifecycleOwner) {
            when (it) {
                is ScreenState.Loading -> binding.loading.visibility = View.VISIBLE
                is ScreenState.Success -> {
                    binding.loading.visibility = View.GONE
                    adapter.submitList(it.data)
                }
                is ScreenState.Error -> {
                    binding.loading.visibility = View.GONE
                    context?.showToast(it.exception.message.toString())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}