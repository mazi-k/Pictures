package com.example.pictures

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pictures.databinding.ActivityMainBinding
import com.example.pictures.models.PictureModel
import com.example.pictures.network.PicturesResponseData
import com.example.pictures.network.PicturesRetrofitImpl
import com.example.pictures.ui.AlertDialogFragment
import com.example.pictures.ui.isOnline
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var retrofitImpl: PicturesRetrofitImpl
    private val adapter: MainAdapter by lazy { MainAdapter() }
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        retrofitImpl = PicturesRetrofitImpl()
        layoutManager = LinearLayoutManager(this)
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
        Toast.makeText(this, "Что-то пошло не так: $errorCode", Toast.LENGTH_LONG).show()
    }

    private fun getPictures(result: Response<List<PicturesResponseData>>): MutableList<PictureModel> {
        val list: MutableList<PictureModel> = mutableListOf()
        for (picrd in result.body()?: emptyList()) {
            list.add(PictureModel(id = picrd.id.toString(), url = picrd.url))
        }
        return list
    }

    private fun startLoadingOrShowError() {
        if (isOnline(applicationContext)) {
            getAllPictureList()
            binding.swipeTv.visibility = View.GONE
        } else {
            AlertDialogFragment.newInstance(
                getString(R.string.dialog_title_device_is_offline),
                getString(R.string.dialog_message_device_is_offline)
            ).show(
                supportFragmentManager,
                "DIALOG_FRAGMENT_TAG"
            )
            binding.swipeTv.visibility = View.VISIBLE
        }
    }
}