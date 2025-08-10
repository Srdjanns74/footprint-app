package com.example.footprintapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.lifecycleScope
import com.example.footprintapp.databinding.FragmentSearchBinding
import com.example.footprintapp.models.ChatMessage
import com.example.footprintapp.network.ChatRequest
import com.example.footprintapp.network.Message
import com.example.footprintapp.network.OpenRouterApi
import com.example.footprintapp.adapters.ChatAdapter
import kotlinx.coroutines.launch

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var adapter: ChatAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ChatAdapter(mutableListOf())
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.sendButton.setOnClickListener {
            val userInput = binding.inputEditText.text.toString().trim()
            if (userInput.isNotEmpty()) {
                sendMessage(userInput)
                binding.inputEditText.setText("")
            }
        }
    }

    private fun sendMessage(userInput: String) {
        val userMessage = ChatMessage(userInput, isUser = true)
        val currentList = adapter.getMessages().toMutableList()
        currentList.add(userMessage)
        adapter.submitList(currentList.toList())

        lifecycleScope.launch {
            try {
                val request = ChatRequest(
                    model = "mistral/mistral-7b-instruct",
                    messages = listOf(
                        Message(role = "system", content = "You are a helpful assistant."),
                        Message(role = "user", content = userInput)
                    )
                )

                val response = OpenRouterApi.sendMessage(request)
                val botResponse = response.choices.firstOrNull()?.message?.content ?: "No response"
                val botMessage = ChatMessage(botResponse, isUser = false)

                val updatedList = adapter.getMessages().toMutableList()
                updatedList.add(botMessage)
                adapter.submitList(updatedList.toList())

            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Error: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
