package com.uade.slamdunk.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uade.slamdunk.R
import com.uade.slamdunk.ui.adapter.NbaAdapter
import com.uade.slamdunk.ui.viewmodel.MainActivityViewModel
class HomeFragment : Fragment() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var rvMain: RecyclerView
    private lateinit var adapter: NbaAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        viewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]
        rvMain = view.findViewById(R.id.rvMain)
        rvMain.layoutManager = LinearLayoutManager(requireContext())

        adapter = NbaAdapter()
        rvMain.adapter = adapter

        viewModel.teams.observe(viewLifecycleOwner) { teams ->
            adapter.updateItems(teams)
        }

        return view
    }

}