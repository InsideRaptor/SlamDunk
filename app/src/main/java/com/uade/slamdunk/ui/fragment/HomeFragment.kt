package com.uade.slamdunk.ui.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.uade.slamdunk.R
import com.uade.slamdunk.ui.activity.LoginActivity
import com.uade.slamdunk.ui.adapter.NbaAdapter
import com.uade.slamdunk.ui.viewmodel.MainActivityViewModel
class HomeFragment : Fragment() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var rvMain: RecyclerView
    private lateinit var adapter: NbaAdapter
    private lateinit var searchBar: SearchView
    private lateinit var btnLogout: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        viewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]
        rvMain = view.findViewById(R.id.rvMain)
        rvMain.layoutManager = LinearLayoutManager(requireContext())

        adapter = NbaAdapter(viewModel)
        rvMain.adapter = adapter

        searchBar = view.findViewById(R.id.searchView)
        setupSearchBar()

        // Logout functionality
        btnLogout = view.findViewById(R.id.btnLogout)
        btnLogout.setOnClickListener {
            logout()
        }

        viewModel.filteredTeams.observe(viewLifecycleOwner) { filteredTeams ->
            adapter.updateItems(filteredTeams)
        }

        return view
    }

    private fun logout() {
        FirebaseAuth.getInstance().signOut()

        // Navigate back to LoginActivity
        val intent = Intent(activity, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        activity?.finish()
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