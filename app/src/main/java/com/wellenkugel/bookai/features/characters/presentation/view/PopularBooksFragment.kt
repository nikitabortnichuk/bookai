package com.wellenkugel.bookai.features.characters.presentation.view

import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.wellenkugel.bookai.core.audio.PlayController
import com.wellenkugel.bookai.core.audio.RecordController
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
    private var recordController: RecordController? = null
    private var playController: PlayController? = null
    private var countDownTimer: CountDownTimer? = null
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // todo move to Dagger
        recordController = RecordController(requireContext())
        playController = PlayController()
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

    override fun onStart() {
        super.onStart()
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            ),
            777,
        )
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
        var filePath = ""
        binding.voiceInput.setOnClickListener {
            if (recordController?.isAudioRecording() == true) {
                recordController?.stop()
            } else {
                filePath = recordController?.start() ?: ""
            }
        }
        binding.sendButton.setOnClickListener {
            sendUserMessage()
        }
        binding.indicator.setOnClickListener {
            if (playController?.isAudioPlaying() == true) {
                playController?.stop()
            } else {
                Log.d("TAGGERR", filePath)
                playController?.start(filePath)
            }
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

//    private fun onButtonClicked() {
//        if (recordController?.isAudioRecording() == true) {
//            recordController?.stop()
//            countDownTimer?.cancel()
//            countDownTimer = null
//        } else {
//            countDownTimer = object : CountDownTimer(60_000, VOLUME_UPDATE_DURATION) {
//                override fun onTick(p0: Long) {
//                    val volume = recordController?.getVolume()
//                    Log.d(TAG, "Volume = $volume")
//                    handleVolume(volume!!)
//                }
//
//                override fun onFinish() {
//                }
//            }.apply {
//                start()
//            }
//        }
//    }
//
//    private fun handleVolume(volume: Int) {
//        val scale = min(8.0, volume / MAX_RECORD_AMPLITUDE + 1.0).toFloat()
//        Log.d(TAG, "Scale = $scale")
//
//        binding.indicator.animate()
//            .scaleX(scale)
//            .scaleY(scale)
//            .setInterpolator(interpolator)
//            .duration = VOLUME_UPDATE_DURATION
//    }

    companion object {
        val TAG = "CharacterSearchFragment"
        val CHARACTER_DETAILS = "character_details"
        private const val MAX_RECORD_AMPLITUDE = 32768.0
        private const val VOLUME_UPDATE_DURATION = 100L
        private val interpolator = OvershootInterpolator()
    }
}
