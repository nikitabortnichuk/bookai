package com.wellenkugel.bookai.features.characters.presentation.view

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
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
import com.wellenkugel.bookai.R
import com.wellenkugel.bookai.core.audio.PlayController
import com.wellenkugel.bookai.core.audio.RecordController
import com.wellenkugel.bookai.core.ext.onTextChange
import com.wellenkugel.bookai.databinding.PopularBooksFragmentBinding
import com.wellenkugel.bookai.features.characters.presentation.adapter.ChatMessagesAdapter
import com.wellenkugel.bookai.features.characters.presentation.model.messages.BotTextMessageItem
import com.wellenkugel.bookai.features.characters.presentation.model.messages.MessageListItem
import com.wellenkugel.bookai.features.characters.presentation.model.messages.UserTextMessageItem
import com.wellenkugel.bookai.features.characters.presentation.viewmodel.PopularBooksViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*
import kotlin.math.min


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
    private var speechRecognizer: SpeechRecognizer? = null
    private lateinit var speechRecognizerIntent: Intent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // todo move to Dagger
        speechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireContext())

        speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechRecognizerIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        speechRecognizer?.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(params: Bundle?) {
                Log.d("TAGGERR", "sddasdas-2")
            }

            override fun onBeginningOfSpeech() {
                Log.d("TAGGERR", "sddasdas-1")

            }

            override fun onRmsChanged(rmsdB: Float) {
                Log.d("TAGGERR", "sddasdas0")

            }

            override fun onBufferReceived(buffer: ByteArray?) {
                Log.d("TAGGERR", "sddasdas1")

            }

            override fun onEndOfSpeech() {
                Log.d("TAGGERR", "sddasdas2")

            }

            override fun onError(error: Int) {
                Log.d("TAGGERR", "sddasdas3 $error error")
            }

            override fun onResults(results: Bundle?) {
                val data = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                Log.d("TAGGERR", data!![0].toString())
            }

            override fun onPartialResults(partialResults: Bundle?) {
                Log.d("TAGGERR", "sddasdas4")

            }

            override fun onEvent(eventType: Int, params: Bundle?) {
                Log.d("TAGGERR", "sddasdas5")

            }

        })

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
            arrayOf(
                android.Manifest.permission.RECORD_AUDIO,
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

    private fun setupMessageInputTextChangeListener() {
        binding.inputMessage.onTextChange {
            with(binding) {
                if (it.isEmpty()) {
                    voiceInput.visibility = View.VISIBLE
                    sendButton.visibility = View.GONE
                } else {
                    voiceInput.visibility = View.GONE
                    sendButton.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.voiceInput.setOnClickListener {
            onVoiceButtonClicked()
        }
        binding.sendButton.setOnClickListener {
            sendUserMessage()
        }
//        binding.indicator.setOnClickListener {
//            if (playController?.isAudioPlaying() == true) {
//                playController?.stop()
//            } else {
//                Log.d("TAGGERR", filePath)
//                playController?.start(filePath)
//            }
//        }
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
        popularBooksViewModel.getBookSubjectByQuestion(binding.inputMessage.text.toString())
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

    private var filePath = ""

    private fun onVoiceButtonClicked() {
        if (recordController?.isAudioRecording() == true) {
            setInputMessageTextAfterRecordingAudio()
            recordController?.stop()
            countDownTimer?.cancel()
            binding.indicator.visibility = View.GONE
            countDownTimer = null
        } else {
            // todo returns path
            binding.indicator.visibility = View.VISIBLE
            filePath = recordController?.start() ?: ""
            setInputMessageTextDuringRecordingAudio()
            countDownTimer = object : CountDownTimer(60_000, VOLUME_UPDATE_DURATION) {
                override fun onTick(p0: Long) {
                    val volume = recordController?.getVolume()
                    handleVolume(volume ?: return)
                }

                override fun onFinish() {}
            }.apply {
                start()
            }
        }
    }

    private fun handleVolume(volume: Int) {
        val scale = min(8.0, volume / MAX_RECORD_AMPLITUDE + 1.0).toFloat()
        binding.indicator.animate()
            .scaleX(scale)
            .scaleY(scale)
            .setInterpolator(interpolator)
            .duration = VOLUME_UPDATE_DURATION
    }

    private fun setInputMessageTextDuringRecordingAudio() {
        binding.voiceInput.setImageResource(R.drawable.ic_micro_recording)
        binding.inputMessage.hint = "Listening..."
        binding.inputMessage.isFocusable = false
    }

    private fun setInputMessageTextAfterRecordingAudio() {
        binding.voiceInput.setImageResource(R.drawable.ic_voice)
        binding.inputMessage.hint = "Message"
        binding.inputMessage.isFocusableInTouchMode = true
        binding.inputMessage.requestFocus()
    }

    companion object {
        val TAG = "ChatFragment"
        private const val MAX_RECORD_AMPLITUDE = 32768.0
        private const val VOLUME_UPDATE_DURATION = 100L
        private val interpolator = OvershootInterpolator()
    }
}
