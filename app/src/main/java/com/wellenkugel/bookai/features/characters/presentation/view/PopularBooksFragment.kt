package com.wellenkugel.bookai.features.characters.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.wellenkugel.bookai.databinding.PopularBooksFragmentBinding
import com.wellenkugel.bookai.features.characters.presentation.adapter.ChatMessagesAdapter
import com.wellenkugel.bookai.features.characters.presentation.viewmodel.PopularBooksViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PopularBooksFragment : Fragment() {

    private var _binding: PopularBooksFragmentBinding? = null
    private val binding get() = _binding!!
    private val popularBooksViewModel: PopularBooksViewModel by viewModels()
    private lateinit var chatMessagesAdapter: ChatMessagesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // todo move to Dagger
        chatMessagesAdapter = ChatMessagesAdapter()
        _binding = PopularBooksFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupMessagesListAdapter()


        popularBooksViewModel.searchPopularBooks()

        binding.voiceInput.setOnLongClickListener {
            Log.d("TAGGERR", "Long Clicked")
            true
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        characterSearchViewModel.getRecentCharacters()
        setupView()
        setupMessagesListAdapter()
    }

    private fun setupView() {

    }

    private fun setupMessagesListAdapter() {
        val textList = listOf<String>(
            "Hello!", "How are you doing?",
            "thank, I`m fine. How are you doing? hope you are well. I`m" +
                    " writing to tell you that I will resign from the company", "Ok", "Good luck!"
        )

        with(binding.messagesListRecyclerView) {
            setHasFixedSize(true)
            adapter = chatMessagesAdapter
            layoutManager = LinearLayoutManager(this@PopularBooksFragment.requireContext())
        }

        chatMessagesAdapter.addMessages(textList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        val TAG = "CharacterSearchFragment"
        val CHARACTER_DETAILS = "character_details"
    }
}
