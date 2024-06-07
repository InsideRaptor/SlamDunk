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

private lateinit var viewModel: MainActivityViewModel
private lateinit var rvBookmarks: RecyclerView
private lateinit var adapter: NbaAdapter

class BookmarkFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_bookmark, container, false)

        viewModel = ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]
        rvBookmarks = view.findViewById(R.id.rvBookmarks)
        rvBookmarks.layoutManager = LinearLayoutManager(requireContext())

        adapter = NbaAdapter()
        rvBookmarks.adapter = adapter

/*        viewModel.bookmarkedTeams.observe(viewLifecycleOwner) { bookmarkedTeams ->
            adapter.updateItems(bookmarkedTeams)
        }*/

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.bookmarkedTeams.observe(viewLifecycleOwner) { bookmarkedTeams ->
            adapter.updateItems(bookmarkedTeams)
        }
    }

}