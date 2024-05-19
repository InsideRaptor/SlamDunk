package com.uade.slamdunk.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
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
    private lateinit var searchBar: SearchView

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

        searchBar = view.findViewById(R.id.searchView)
        setupSearchBar()

        viewModel.filteredTeams.observe(viewLifecycleOwner) { filteredTeams ->
            adapter.updateItems(filteredTeams)
        }

        return view
    }

    private fun setupSearchBar() {
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.filterTeams(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    viewModel.filterTeams(newText)
                }
                return true
            }
        })
    }

}