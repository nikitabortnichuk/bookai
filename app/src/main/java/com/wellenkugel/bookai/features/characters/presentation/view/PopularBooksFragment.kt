package com.wellenkugel.bookai.features.characters.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.wellenkugel.bookai.databinding.PopularBooksFragmentBinding
import com.wellenkugel.bookai.features.characters.presentation.initRecyclerViewWithLineDecoration
import com.wellenkugel.bookai.features.characters.presentation.model.SCharacterPresentation
import com.wellenkugel.bookai.features.characters.presentation.viewmodel.PopularBooksViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class PopularBooksFragment : Fragment() {

    private var _binding: PopularBooksFragmentBinding? = null
    private val binding get() = _binding!!
    private val popularBooksViewModel: PopularBooksViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PopularBooksFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        popularBooksViewModel.searchPopularBooks()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        characterSearchViewModel.getRecentCharacters()
        setupView()
        setupListAdapter()
    }

    private fun setupView() {
//        characterSearchViewModel.openCharacterDetailsEvent.observe(viewLifecycleOwner, Observer {
//            val fm = activity?.supportFragmentManager
//            val characterDetailsFragment =
//                CharacterDetailsFragment()
//            characterDetailsFragment.arguments = bundleOf(
//                CHARACTER_DETAILS to it
//            )
//            fm?.beginTransaction()
//                ?.add(R.id.main_host, characterDetailsFragment, CharacterDetailsFragment.TAG)
//                ?.addToBackStack(CharacterDetailsFragment.TAG)?.commit()
//        })
    }

    private fun openCharacterDetails(character: SCharacterPresentation) {
//        val fm = activity?.supportFragmentManager
//        val characterDetailsFragment =
//            CharacterDetailsFragment()
//        characterDetailsFragment.arguments = bundleOf(
//            CHARACTER_DETAILS to character
//        )
//        fm?.beginTransaction()
//            ?.add(R.id.main_host, characterDetailsFragment, CharacterDetailsFragment.TAG)
//            ?.addToBackStack(CharacterDetailsFragment.TAG)?.commit()
    }

    private fun setupListAdapter() {
        context?.let {
            binding.charactersRecyclerView.initRecyclerViewWithLineDecoration(it)
        }
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
