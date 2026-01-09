package com.example.challenge.presentation.screen.connection

import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.challenge.databinding.FragmentConnectionsBinding
import com.example.challenge.presentation.common.BaseFragment
import com.example.challenge.presentation.extension.lifecycleCollectLatest
import com.example.challenge.presentation.extension.showSnackBar
import com.example.challenge.presentation.screen.connection.adapter.ConnectionsRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ConnectionsFragment : BaseFragment<FragmentConnectionsBinding>(
    FragmentConnectionsBinding::inflate
) {
    private val viewModel: ConnectionsViewModel by viewModels()
//    private lateinit var connectionsRecyclerAdapter: ConnectionsRecyclerAdapter
    private val connectionsRecyclerAdapter by lazy { ConnectionsRecyclerAdapter() }


    override fun listeners() {
        onButtonLogOutClick()
        observeState()
        observeSideEffects()
    }

    override fun bind() {
//        connectionsRecyclerAdapter = ConnectionsRecyclerAdapter()
        binding.apply {
            recyclerConnections.layoutManager = LinearLayoutManager(context)
            recyclerConnections.setHasFixedSize(true)
            recyclerConnections.adapter = connectionsRecyclerAdapter
        }
        viewModel.onEvent(ConnectionEvent.FetchConnections)
    }

    private fun onButtonLogOutClick() {
        binding.btnLogOut.setOnClickListener {
            viewModel.onEvent(ConnectionEvent.LogOut)
        }
    }


    private fun observeState() {
        lifecycleCollectLatest(viewModel.connectionState) { state ->
            handleConnectionState(state = state)
        }
    }

    private fun observeSideEffects() {
        lifecycleCollectLatest(viewModel.sideEffect) { effect ->
            when (effect) {
                ConnectionSideEffect.NavigateToLogIn -> {
                    findNavController()
                        .navigate(ConnectionsFragmentDirections.actionConnectionsFragmentToLogInFragment())
                }
            }
        }
    }

    private fun handleConnectionState(state: ConnectionState) {
        binding.loaderInclude.loaderContainer.visibility =
            if (state.isLoading) View.VISIBLE else View.GONE

        state.connections?.let {
            connectionsRecyclerAdapter.submitList(it)
        }

        state.errorMessage?.let {
            binding.root.showSnackBar(message = it)
            viewModel.onEvent(ConnectionEvent.ResetErrorMessage)
        }
    }
}

