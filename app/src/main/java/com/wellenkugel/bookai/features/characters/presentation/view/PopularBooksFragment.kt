package com.wellenkugel.bookai.features.characters.presentation.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.wellenkugel.bookai.databinding.PopularBooksFragmentBinding
import com.wellenkugel.bookai.features.characters.presentation.adapter.ChatMessagesAdapter
import com.wellenkugel.bookai.features.characters.presentation.model.messages.BotTextMessageItem
import com.wellenkugel.bookai.features.characters.presentation.model.messages.MessageListItem
import com.wellenkugel.bookai.features.characters.presentation.model.messages.UserTextMessageItem
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
        setupClickListeners()
        setupMessagesListAdapter()
        setupMessageInputOnKeyListener()
        setupMessageInputTextChangeListener()

        popularBooksViewModel.searchPopularBooks()


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
        val textList = listOf<MessageListItem>(
            BotTextMessageItem("Hello!"),
            BotTextMessageItem("How are you doing?"),
            UserTextMessageItem(
                "thank, I`m fine. How are you doing? hope you are well. I`m" +
                        " writing to tell you that I will resign from the company"
            ),
            BotTextMessageItem("Ok"),
            UserTextMessageItem("Good luck!"),
            BotTextMessageItem("Hello!"),
            BotTextMessageItem("How are you doing?"),
            UserTextMessageItem(
                "thank, I`m fine. How are you doing? hope you are well. I`m" +
                        " writing to tell you that I will resign from the company"
            ),
            BotTextMessageItem("Ok"),
            UserTextMessageItem("Good luck!"),
            BotTextMessageItem("Hello!"),
            BotTextMessageItem("How are you doing?"),
            UserTextMessageItem(
                "thank, I`m fine. How are you doing? hope you are well. I`m" +
                        " writing to tell you that I will resign from the company"
            ),
            BotTextMessageItem("Ok"),
            UserTextMessageItem("Good luck!")
        )

        with(binding.messagesListRecyclerView) {
            setHasFixedSize(true)
            adapter = chatMessagesAdapter
            layoutManager = LinearLayoutManager(this@PopularBooksFragment.requireContext()).apply {
                reverseLayout = true
            }
        }

        chatMessagesAdapter.addMessages(textList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    // todo extract to another file
    private fun setupMessageInputTextChangeListener() {
        binding.inputMessage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(
                sequence: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                with(binding) {
                    if (sequence.isNullOrEmpty()) {
                        voiceInput.visibility = View.VISIBLE
                        sendButton.visibility = View.GONE
                    } else {
                        voiceInput.visibility = View.GONE
                        sendButton.visibility = View.VISIBLE
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun setupClickListeners() {
        binding.voiceInput.setOnLongClickListener {
            Log.d("TAGGERR", "Long Clicked")
            true
        }
        binding.sendButton.setOnClickListener {
            sendUserMessage()
        }
    }

    private fun setupMessageInputOnKeyListener() {
        binding.inputMessage.setOnKeyListener { v, keyCode, event ->
            if ((event.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                sendUserMessage()
                return@setOnKeyListener true
            }
            false
        }
    }

    private fun sendUserMessage() {
        val canScrollVertically = binding.messagesListRecyclerView.canScrollVertically(1)
        chatMessagesAdapter.addLastMessage(
            UserTextMessageItem(binding.inputMessage.text.toString()),
            canScrollVertically
        )
        if (canScrollVertically) {
            binding.messagesListRecyclerView.smoothScrollToPosition(0)
        } else {
            binding.messagesListRecyclerView.scrollToPosition(0)
        }
        binding.inputMessage.setText("")
    }

    companion object {
        val TAG = "CharacterSearchFragment"
        val CHARACTER_DETAILS = "character_details"
    }
}
