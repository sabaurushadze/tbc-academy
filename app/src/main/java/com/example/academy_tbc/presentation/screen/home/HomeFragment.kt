package com.example.academy_tbc.presentation.screen.home

import android.view.inputmethod.EditorInfo
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.databinding.FragmentHomeBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.dp
import com.example.academy_tbc.presentation.extension.hideKeyboard
import com.example.academy_tbc.presentation.extension.lifecycleCollect
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.extension.showSnackBar
import com.example.academy_tbc.presentation.screen.home.adapter.PcPartsAdapter
import com.example.academy_tbc.presentation.screen.home.adapter.VerticalSpaceDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::inflate
) {
    private val viewModel: HomeViewModel by viewModels()
    private val pcPartsAdapter by lazy {
        PcPartsAdapter(onClick = {})
    }

    override fun bind() {
        setUpPcPartsAdapter()
    }

    override fun listeners() {
        viewModel.onEvent(HomeEvent.GetParts)
        observeSideEffects()
        observeState()
        search()
        signOut()
    }

    private fun setUpPcPartsAdapter() {
        binding.rvParts.apply {
            adapter = pcPartsAdapter
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            addItemDecoration(VerticalSpaceDecoration(4.dp))
        }
    }

    private fun observeSideEffects() {
        lifecycleCollectLatest(viewModel.effect) { effect ->
            when (effect) {
                is HomeSideEffect.ShowError -> binding.root.showSnackBar(effect.message)
                HomeSideEffect.NavigateToSignIn -> findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToSignInFragment()
                )
            }
        }
    }

    private fun observeState() {
        lifecycleCollect(viewModel.state) { state ->
            pcPartsAdapter.submitList(state.pcParts)

        }
    }

    private fun signOut() {
        binding.btnSort.setOnClickListener {
            viewModel.onEvent(HomeEvent.SignOut)
        }
    }

    private fun search() = with(binding) {
        etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val query = etSearch.text.toString()
                viewModel.onEvent(HomeEvent.Search(query))
                etSearch.hideKeyboard()
                true
            } else false
        }
    }
}