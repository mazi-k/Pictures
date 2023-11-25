package com.example.pictures.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pictures.R
import com.example.pictures.databinding.PicturesFragmentBinding
import com.example.pictures.models.PictureModel
import com.example.pictures.network.PicturesResponseData
import com.example.pictures.network.PicturesRetrofitImpl
import com.example.pictures.ui.dialog.AlertDialogFragment
import com.example.pictures.ui.utils.isOnline
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class PicturesFragment: Fragment() {

    private lateinit var binding: PicturesFragmentBinding
    private lateinit var retrofitImpl: PicturesRetrofitImpl
    private val adapter: MainAdapter by lazy { MainAdapter() }
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pictures_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = PicturesFragmentBinding.bind(view)


        retrofitImpl = PicturesRetrofitImpl()
        layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter

        startLoadingOrShowError()

        binding.swipeRefreshLayout.setOnRefreshListener {
            startLoadingOrShowError()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }



    private fun getAllPictureList() {
        CoroutineScope(Dispatchers.IO ).launch {
            val result = retrofitImpl.getRetrofitImpl().getPictures()
            if (result.isSuccessful) {
                val pictures = getPictures(result)
                withContext(Dispatchers.Main){
                    adapter.setData(pictures)
                }
            } else {
                showErrorToast(result.code())
            }
        }
    }

    private fun showErrorToast(errorCode: Int) {
        Toast.makeText(requireContext(), "Что-то пошло не так: $errorCode", Toast.LENGTH_LONG).show()
    }

    private fun getPictures(result: Response<List<PicturesResponseData>>): MutableList<PictureModel> {
        val list: MutableList<PictureModel> = mutableListOf()
        for (picrd in result.body()?: emptyList()) {
            list.add(PictureModel(id = picrd.id.toString(), url = picrd.url))
        }
        return list
    }

    private fun startLoadingOrShowError() {
        if (isOnline(requireActivity().applicationContext)) {
            getAllPictureList()
            binding.swipeTv.visibility = View.GONE
            binding.loadingProgress.visibility = View.GONE
        } else {
            AlertDialogFragment.newInstance(
                getString(R.string.dialog_title_device_is_offline),
                getString(R.string.dialog_message_device_is_offline)
            ).show(
                requireActivity().supportFragmentManager,
                "DIALOG_FRAGMENT_TAG"
            )
            binding.swipeTv.visibility = View.VISIBLE
            binding.loadingProgress.visibility = View.VISIBLE
        }
    }
}