package com.example.academy_tbc.presentation.screen.profile

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.academy_tbc.databinding.FragmentHomeBinding
import com.example.academy_tbc.databinding.FragmentHomeBinding.inflate
import com.example.academy_tbc.databinding.FragmentProfileBinding
import com.example.academy_tbc.presentation.common.view.BaseFragment
import com.example.academy_tbc.presentation.extension.lifecycleCollectLatest
import com.example.academy_tbc.presentation.screen.home.HomeViewModel
import com.example.academy_tbc.presentation.screen.home.adapter.UsersAdapter
import com.example.academy_tbc.presentation.screen.home.adapter.UsersLoadStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    FragmentProfileBinding::inflate
) {

    private val viewModel: HomeViewModel by viewModels()


    override fun listeners() {
        observeLoadState()
    }



    private fun observeLoadState() = with(binding) {

    }


}